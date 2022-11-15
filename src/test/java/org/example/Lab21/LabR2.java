package org.example.Lab21;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LabR2 {
    private static final String baseUrl = "https://petstore.swagger.io/v2";

    private static final String PET = "/pet",
        PET_ID = PET + "/{petId}";

    private static final String STATUS = "available";
    @BeforeClass
    public void setup() {
        RestAssured.baseURI = baseUrl;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void verifyCreateAction() {
        Map<String, ?> body = Map.of(
                "id", Integer.valueOf("1"),
                //"category", Integer.valueOf("1"), Faker.instance().name().name(),
                "name", Faker.instance().name().firstName(),
                //"photoUrls",
                "status", STATUS
        );
        given().body(body).post(PET).then().statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyCreateAction")
    public void verifyGetAction() {
        given().pathParam("petId", Integer.valueOf("1"))
                .get(PET_ID)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("id", equalTo(Integer.valueOf("1")));
    }

    @Test(dependsOnMethods = "verifyGetAction")
    public void verifyPutAction() {
        Map<String, ?> body = Map.of(
                "id", Integer.valueOf("1"),
                //"category", Integer.valueOf("1"), Faker.instance().name().name(),
                "name", Faker.instance().name().firstName(),
                //"photoUrls",
                "status", STATUS
        );
        given().body(body).post(PET).then().statusCode(HttpStatus.SC_OK);
    }

    @Test(dependsOnMethods = "verifyPutAction")
    public void verifyDeleteAction() {
        given().pathParam("petId", Integer.valueOf("1")).delete(PET_ID)
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
