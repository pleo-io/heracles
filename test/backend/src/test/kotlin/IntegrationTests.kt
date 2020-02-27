package tests
import io.restassured.RestAssured
import io.restassured.RestAssured.*
import io.restassured.matcher.RestAssuredMatchers.*
import org.hamcrest.Matchers.*
import org.junit.*

class IntegrationTests() {
    @Before
    fun setup() {
        RestAssured.baseURI = System.getenv("BACKEND_SERVICE_URL") ?: "http://localhost"
        RestAssured.port = 8080
    }

    @Test
    fun validateStatusCode200() {
        get("format?amount=1").then().statusCode(200)
    }

    @Test
    fun validateStatus0() {
        get("format?amount=1").then().body("status", equalTo(0))
    }

    @Test
    fun validateStatus400() {
        get("format?amount=aaa").then().body("status", equalTo(400))
    }

    @Test
    fun validateStatus101() {
        get("format?amount=111111111111111111111111111111").then().body("status", equalTo(101))
    }

    @Test
    fun validateSmallNumber() {
        get("format?amount=1").then().body("moneyFormatted", equalTo("1.00"))
    }

    @Test
    fun validateLongNumber() {
        get("format?amount=11111111").then().body("moneyFormatted", equalTo("11 111 111.00"))
    }

    @Test
    fun roundingNumber() {
        get("format?amount=1.999").then().body("moneyFormatted", equalTo("2.00"))
    }

    @Test
    fun notRoundingNumber() {
        get("format?amount=1.99").then().body("moneyFormatted", equalTo("1.99"))
    }

    @Test
    fun removingLastDigit() {
        get("format?amount=1.951").then().body("moneyFormatted", equalTo("1.95"))
    }

    @Test
    fun negativeNumberSplit() {
        get("format?amount=-4356").then().body("moneyFormatted", equalTo("-4 356.00"))
    }

    @Test
    fun negativeNumber() {
        get("format?amount=-100.98").then().body("moneyFormatted", equalTo("-100.98"))
    }

    @Test
    fun zero() {
        get("format?amount=0").then().body("moneyFormatted", equalTo("0.00"))
    }

    @Test
    fun nothingInAmount() {
        get("format?amount=").then().body("moneyFormatted", equalTo("0.00"))
    }

    @Test
    fun wrongParameter() {
        // Default is always 0.00
        get("format?amount444").then().body("moneyFormatted", equalTo("0.00"))
    }

    @Test
    fun wrongParameterStatus() {
        // Default is 0
        get("format?amount444").then().body("status", equalTo(0))
    }

    @Test
    fun measureTime() {
        get("format?amount=1").then().time(lessThan(5000L));
    }

}