import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class SoapXMLRequest {
    @Test
    public void validateSoapXML() throws IOException {
        File file = new File("src/test/resources/add.xml");
        if (file.exists())
            System.out.println("  >> File Exists");
        FileInputStream fileInputStream = new FileInputStream(file);
        String requestBody = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
        baseURI = "http://www.dneonline.com";
        given().
                contentType("text/xml").
                accept(ContentType.XML).
                body(requestBody).
                when().
                post("/calculator.asmx").
                then().
                statusCode(200).log().all().and().
                body("//*:AddResult.text()", equalTo("5")).and().
                assertThat().body(RestAssuredMatchers.matchesXsdInClasspath("schema.xml"));
    }
}
