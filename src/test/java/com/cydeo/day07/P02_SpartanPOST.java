package com.cydeo.day07;


import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P02_SpartanPOST extends SpartanTestBase {

    /**
     * Given accept type is JSON
     * And Content type is JSON
     * And request json body is:
     * {
     * "gender":"Male",
     * "name":"John Doe",
     * "phone":8877445596
     * }
     * When user sends POST request to '/api/spartans'
     * Then status code 201
     * And content type should be application/json
     * And json payload/response/body should contain:
     * verify the success value is 'A Spartan is Born!'
     * "name": "John Doe",
     * "gender": "Male",
     * "phone": 8877445596
     */

    @DisplayName("POST Spartan with String body")

    @Test
    public void test1() {

        String requestBody = " {\n" +
                "     \"gender\":\"Male\",\n" +
                "     \"name\":\"John Doe\",\n" +
                "     \"phone\":8877445596\n" +
                "     }";

        JsonPath jsonPath = given()
                .accept(ContentType.JSON)// hey api, please send me JSON RESPoNSE BODY
                .and()
                .contentType(ContentType.JSON)// hey api, I am sending you JSON request body
                .body(requestBody)
                .when()
                .post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!"))
                .extract().jsonPath();


        //request body verification

        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(8877445596l, jsonPath.getLong("data.phone"));


        // I want to get is out of the response body, to delete or send request later on

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);
    }


    @DisplayName("POST Spartan with Map body")

    @Test
    public void test2() {

        Map<String, Object> requestBodyMap = new HashMap<>();

        requestBodyMap.put("name", "John Doe");
        requestBodyMap.put("gender", "Male");
        requestBodyMap.put("phone", "8877445596");

        //We created one map, and put the info that we want to send as a json REQUEST BODY

        System.out.println("requestBodyMap = " + requestBodyMap);


        JsonPath jsonPath = given().log().body()
                .accept(ContentType.JSON)// hey api, please send me JSON RESPoNSE BODY
                .and()
                .contentType(ContentType.JSON)// hey api, I am sending you JSON request body
                .body(requestBodyMap)
                .when()
                .post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!"))
                .extract().jsonPath();


        //request body verification

        assertEquals("John Doe", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(8877445596l, jsonPath.getLong("data.phone"));


        // I want to get is out of the response body, to delete or send request later on

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);


        // can we have SpartanUtil class which is giving as a requestMap with dynamic dummny value?
        // using faker library inside?

    }


    @DisplayName("POST Spartan with Spartan POJO")

    @Test
    public void test3() {

        Spartan spartan = new Spartan();
        spartan.setName("Harold Finch");
        spartan.setGender("Male");
        spartan.setPhone(8877445596l);

        System.out.println("spartan = " + spartan);


        JsonPath jsonPath = given().log().body()
                .accept(ContentType.JSON)// hey api, please send me JSON RESPoNSE BODY
                .and()
                .contentType(ContentType.JSON)// hey api, I am sending you JSON request body
                .body(spartan)
                .when()
                .post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success", is("A Spartan is Born!"))
                .extract().jsonPath();


        //request body verification

        assertEquals("Harold Finch", jsonPath.getString("data.name"));
        assertEquals("Male", jsonPath.getString("data.gender"));
        assertEquals(8877445596l, jsonPath.getLong("data.phone"));


        // I want to get is out of the response body, to delete or send request later on

        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);


        // can we have SpartanUtil class which is giving as a requestMap with dynamic dummny value?
        // using faker library inside?

    }


    @DisplayName("POST Spartan with Spartan POJO and GET same Spartan")

    @Test
    public void test4() {
        //empty spartan object and we use setters to set some value
        //values can be from faker library or somewhere else which is changing dynamically
        Spartan spartanPOST = new Spartan();
        spartanPOST.setName("Harold Finch");
        spartanPOST.setGender("Male");
        spartanPOST.setPhone(1234567890l);
        spartanPOST.setId(500); //even if we put some id value, it didnt serialized because of the jackson annotation on the class

        System.out.println("spartan = " + spartanPOST);


        JsonPath jsonPath = given().log().body()
                .accept(ContentType.JSON) //hey api, please send me JSON RESPONSE BODY
                .and()
                .contentType(ContentType.JSON) // hey api, I am sending you JSON REQUEST BODY
                .body(spartanPOST) //it will take spartan object and serialize to JSON
                .when()
                .post("/api/spartans").prettyPeek()
                .then()
                .statusCode(201)
                .contentType("application/json")
                .body("success",is("A Spartan is Born!"))
                .extract().jsonPath();

        //request body verification
        assertEquals("Harold Finch",jsonPath.getString("data.name"));
        assertEquals("Male",jsonPath.getString("data.gender"));
        assertEquals(1234567890l,jsonPath.getLong("data.phone"));

        //I want to get id out of the response body, to delete or send get request later on
        int id = jsonPath.getInt("data.id");
        System.out.println("id = " + id);

        //SEND GET REQUEST TO THE SPARTAN THAT IS CREATED THEN DESERIALIZE TO SPARTAN CLASS and compare

        Response response = given()
                .accept(ContentType.JSON)
                .pathParam("id", id)
                .when().get("/api/spartans/{id}")
                .then().statusCode(200).extract().response();
        //get json response and deserialize to spartan class
        Spartan spartanGET = response.as(Spartan.class);

        System.out.println("spartanGET = " + spartanGET);

        //verify names that we sent and get is matching
        assertEquals(spartanPOST.getName(),spartanGET.getName());

    }

}
