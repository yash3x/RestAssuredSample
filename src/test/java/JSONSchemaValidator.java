import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

public class JSONSchemaValidator {
    @Test
    public void testGet() {
        baseURI = "https://reqres.in/api/";
        given().get("users?page=2").
                then().statusCode(200).
                assertThat().body(matchesJsonSchemaInClasspath("schema.json")).
                body("data[4].first_name", equalTo("George")).
                body("data.first_name", hasItems("George", "Byron"));
    }
}
