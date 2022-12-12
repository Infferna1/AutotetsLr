package org.example.Lab3;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;
import static io.restassured.RestAssured.given;

public class Lab3 {
    private static final String baseUrl = "https://1783f321-7959-4de2-b6dc-910ad3d3b020.mock.pstmn.io";
    private static final String ownerNameSuccess = "/Infferna/success";
    private static final String ownerNameUnsuccess = "/Infferna/unsuccess";
    private static final String CREATE_SOMETHING = "/createSomething";
    private static final String PERMISSION = CREATE_SOMETHING + "?permission=yes";
    private static final String UPDATE = "/updateMe/1";
    private static final String Del = "/deleteWlrd";


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
                "name", Faker.instance().name().firstName()
        );//201
        given().body(body).post(PERMISSION).then().statusCode(HttpStatus.SC_CREATED);
    }

    @Test(dependsOnMethods = "verifyCreateAction")
    public void verifyBadRequestCreateAction() {
        Map<String, ?> body = Map.of(
                "id", Integer.valueOf("1"),
                "name", Faker.instance().name().firstName()
        );//400
        given().body(body).post(CREATE_SOMETHING).then().statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test(dependsOnMethods = "verifyBadRequestCreateAction")
    public void verifyGetAction() {
        given().get(ownerNameSuccess)
                .then()
                .statusCode(HttpStatus.SC_OK);//200
    }

    @Test(dependsOnMethods = "verifyGetAction")
    public void verifyUnsuccessGetAction() {
        given().get(ownerNameUnsuccess)
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);//403
    }

    @Test(dependsOnMethods = "verifyUnsuccessGetAction")
    public void verifyPutAction() {
        Map<String, ?> body = Map.of(
                "name", "",
                "surname", ""
        );//500
        given().body(body).put(UPDATE).then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test(dependsOnMethods = "verifyPutAction")
    public void verifyDelAction() {
        given().delete(Del).then().statusCode(HttpStatus.SC_GONE);//410
    }
}
