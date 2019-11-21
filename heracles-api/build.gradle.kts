import com.example.gradle.tasks.GenerateModelTask
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
	id("org.springframework.boot") version "2.2.0.RELEASE"
	id("io.spring.dependency-management") version "1.0.7.RELEASE"
	id("org.openapi.generator")
	kotlin("jvm") version "1.3.50"
	kotlin("plugin.spring") version "1.3.41"
	id("org.jmailen.kotlinter") version "2.1.0"
	jacoco
}

apply(plugin = "kotlin")

group = "kotlin-spring-example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

val ktlintOnly: Configuration by configurations.creating

val developmentOnly: Configuration by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

buildscript {
	repositories {
		gradlePluginPortal()
		mavenCentral()
		jcenter()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin")
		classpath("org.jetbrains.kotlin:kotlin-gradle-plugin")
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// base dependencies
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude(module ="spring-boot-starter-logging")
	}

	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	compileOnly("org.projectlombok:lombok:1.18.8")
	annotationProcessor("org.projectlombok:lombok:1.18.8")

	implementation("org.springframework.boot:spring-boot-starter-security:2.2.0.RELEASE") {
		exclude(module ="spring-boot-starter-logging")
	}

	// aop
	implementation("org.aspectj:aspectjtools:1.9.4")

	// logging
	implementation("org.springframework.boot:spring-boot-starter-log4j2:2.1.6.RELEASE")

	// infrastructure: json
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.9.5")

	// infrastructure: open api generator
	implementation("org.openapitools:openapi-generator-gradle-plugin:4.0.3")
	implementation("com.squareup.moshi:moshi:1.8.0")

	// dev tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// test dependencies
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(module ="spring-boot-starter-logging")
		exclude(module = "mockito-core")
	}
	testImplementation("io.mockk:mockk:1.9.1")
	testImplementation("io.kotlintest:kotlintest-runner-junit5:3.3.2")
	testImplementation("org.testcontainers:testcontainers:1.12.0")
}

project.ext.set("generatedFileNames", mutableListOf<String>())

// Open API Generator options
val generatorConfigOptions = mapOf(
		"dateLibrary" to "java8",
		"useBeanValidation" to "false",
		"hideGenerationTimestamp" to "true"
)

val generatorTypeMappings = mapOf(
		"java.time.LocalDate" to "java.util.Date",
		"java.time.LocalDateTime" to "java.time.OffsetDateTime"
)

val headerImport = "io.pleo.heracles.infrastructure.api.common.dto.v1.Header"
val amountImport = "io.pleo.heracles.infrastructure.api.common.dto.v1.Amount"
val batchImport = "io.pleo.heracles.infrastructure.api.common.dto.v1.Batch"
val statusImport = "io.pleo.heracles.infrastructure.api.common.dto.v1.Status"

val generatorImportMappings = mapOf(
		"Header" to headerImport,
		headerImport to headerImport,
		"Amount" to amountImport,
		amountImport to amountImport,
		"Batch" to batchImport,
		batchImport to batchImport,
		"Status" to statusImport,
		statusImport to statusImport
)

var generateMappings = listOf(
		mapOf(
				"name" to "generateCommonDTOs",
				"swaggerPath" to "$rootDir/definitions/common/v1/common_1.yml",
				"packageName" to "io.pleo.heracles.infrastructure.api.common.dto.v1"
		),
		mapOf(
				"name" to "generateApiErrorResponseDTO",
				"swaggerPath" to "$rootDir/definitions/common/v1/api_error_response_1.yml",
				"packageName" to "io.pleo.heracles.infrastructure.api.common.dto.v1",
				"importMappings" to generatorImportMappings
		),
		mapOf(
				"name" to "generateFormatAmountDTO",
				"swaggerPath" to "$rootDir/definitions/api/v1/format_amount_1.yml",
				"packageName" to "io.pleo.heracles.infrastructure.api.formatamount.v1",
				"importMappings" to generatorImportMappings
		)
)

var generatorNames = mutableListOf<String>()
generateMappings.forEach {

	val name: String = it["name"] as String
	val swaggerPath: String = it["swaggerPath"] as String
	val packageName: String = it["packageName"] as String
	var importMapping: Map<String, String>? = mutableMapOf();

	if (it["importMappings"] != null) {
		importMapping = it["importMappings"] as Map<String, String>
	}

    tasks.register<GenerateModelTask>(name) {
        generatorName.value("kotlin")
        inputSpec.value(swaggerPath)
        outputDir.value("$rootDir")
        modelPackage.value(packageName)
        generateApiTests.value(false)
        generateModelTests.value(false)
        generateApiDocumentation.value(false)
        generateModelDocumentation.value(false)
        validateSpec.value(false)
        configOptions.value(generatorConfigOptions)
        logToStderr.value(false)
        importMappings.value(importMapping)
        typeMappings.value(generatorTypeMappings)
        generateAliasAsModel.value(false)
        doLast {
            (project.ext.get("generatedFileNames") as MutableList<String>)
                .addAll((this as GenerateModelTask).generatedFileNames)
        }
    }

	generatorNames.add(name)
}

tasks.register("generateDTO") {
	dependsOn(generatorNames)
}

tasks.test {
	useJUnitPlatform()

	// exclude integration tests as these are purely unit tests
	exclude("io/pleo/heracles/integration/**")

	testLogging {
		exceptionFormat = TestExceptionFormat.FULL
	}
}

sourceSets {
	create("integrationTest") {
		withConvention(KotlinSourceSet::class) {
			kotlin.srcDir("$rootDir/src/test/kotlin/io/pleo/heracles/integration")
			resources.srcDir("$rootDir/src/test/resources")
			compileClasspath += sourceSets["main"].output + configurations["testRuntimeClasspath"]
			runtimeClasspath += output + compileClasspath + sourceSets["test"].runtimeClasspath
		}
	}
}

task<Test>("integrationTest") {
	description = "Runs the integration tests"
	group = "verification"
	testClassesDirs = sourceSets["integrationTest"].output.classesDirs
	classpath = sourceSets["integrationTest"].runtimeClasspath

	useJUnitPlatform()

	testLogging {
		exceptionFormat = TestExceptionFormat.FULL
	}
}

tasks {
	"lintKotlinMain"(LintTask::class) {
		dependsOn("generateDTO")
		doFirst {

			// exclude generated files
			val list: MutableList<String> = (project.ext.get("generatedFileNames") as MutableList<String>)
			val sourceFiles: MutableList<String> = mutableListOf()
			source.forEach {
				if (!list.contains(it.absolutePath)) {
					sourceFiles.add(it.absolutePath)
				}
			}
			setSource(files(sourceFiles))
		}
	}
}

jacoco {
	toolVersion = "0.8.4"
}

tasks.jacocoTestReport {
	doFirst {

		val list: MutableList<String> = (project.ext.get("generatedFileNames") as MutableList<String>)
		sourceDirectories.setFrom(
				sourceSets.main.get().output.asFileTree.matching {
					// exclude main()
					exclude(list)
				}
		)

		// exclude generated classed
		val excluded: MutableList<String> = mutableListOf()
		list.forEach {
			excluded.add(it.substring(it.indexOf("io/pleo")).replace(".kt", "**"))
		}

		classDirectories.setFrom(
				sourceSets.main.get().output.asFileTree.matching {
					// exclude main()
					exclude(excluded)
				}
		)
	}

	reports {
		val mainSrc = "$rootDir/src/main/kotlin"
		val tree = fileTree("$rootDir/build/classes")

		sourceDirectories.setFrom(mainSrc)
		classDirectories.setFrom(files(tree))
		xml.isEnabled = true
		csv.isEnabled = false
		html.isEnabled = true
		xml.destination = file("$buildDir/reports/coverage/build.xml")
		html.destination = file("$buildDir/reports/coverage")
	}
}

tasks.jacocoTestCoverageVerification {
	doFirst {
		val list: MutableList<String> = (project.ext.get("generatedFileNames")
				as MutableList<String>)
		sourceDirectories.setFrom(
				sourceSets.main.get().output.asFileTree.matching {
					// exclude main()
					exclude(list)
				}
		)

		// exclude generated classed
		val excluded: MutableList<String> = mutableListOf<String>()
		list.forEach {
			excluded.add(it.substring(it.indexOf("io/pleo")).replace(".kt", "**"))
		}

		classDirectories.setFrom(
				sourceSets.main.get().output.asFileTree.matching {
					// exclude main()
					exclude(excluded)
				}
		)
	}
	violationRules {
		rule {
			limit {
				minimum = "0.7".toBigDecimal()
			}
		}
	}
}

val testCoverage by tasks.registering {
	group = "verification"
	description = "Runs the unit tests with coverage."

	dependsOn(":generateDTO", ":test", ":jacocoTestReport", ":jacocoTestCoverageVerification")
	val jacocoTestReport = tasks.findByName("jacocoTestReport")
	jacocoTestReport?.mustRunAfter(tasks.findByName("test"))
	tasks.findByName("jacocoTestCoverageVerification")?.mustRunAfter(jacocoTestReport)
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
