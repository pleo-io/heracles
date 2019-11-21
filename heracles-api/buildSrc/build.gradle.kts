plugins {
    `kotlin-dsl`
    id("org.openapi.generator") version "4.0.3"
}
repositories {
    gradlePluginPortal()
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.openapitools:openapi-generator-gradle-plugin:4.0.3")
}
