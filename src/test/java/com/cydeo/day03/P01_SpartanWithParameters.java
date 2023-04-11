package com.cydeo.day03;


import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class P01_SpartanWithParameters extends SpartanTestBase {

    /*   Given accept type is Json
      And Id parameter value is 24
      When user sends GET request to /api/spartans/{id}
      Then response status code should be 200
      And response content-type: application/json
      And "Julio" should be in response payload(body)
   */

    @DisplayName("GET SPARTAN /api/spartans/{id} path param 24")
    @Test
    public void test1() {

        Response response = given().
                accept(ContentType.JSON)
                .and()
                .pathParam("id", 24)
                .when()
                .get("/api/spartans/{id}");

        response.prettyPrint();


//    Then status code is 200
        assertEquals(200, response.statusCode());
//    And content-type is "application/json"
        assertEquals("application/json", response.contentType());
//    And "Julio" should be in response payload (body)

        assertTrue(response.body().asString().contains("Julio"));


    }


        /*
        TASK
        Given accept type is Json
        And Id parameter value is 500
        When user sends GET request to /api/spartans/{id}
        Then response status code should be 404
        And response content-type: application/json
        And "Not Found" message should be in response payload
      */


    @DisplayName("GET SPARTAN /api/spartans/{id} with invalid ID")
    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON)
                .and()// Syntactic sugar --> to increase readability of the code, not affect anything
                .pathParam("id", 500)
                .when()
                .get("/api/spartans/{id}");

        //Print response
        response.prettyPrint();

        //Then response status code should be 404
        assertEquals(404, response.statusCode());

        //same with above
        assertEquals(HttpStatus.SC_NOT_FOUND, response.statusCode());


        //And response content-type: application/json
        assertEquals("application/json", response.contentType());

        //And "Not Found" message should be in response payload
        assertTrue(response.body().asString().contains("Not Found"));

    }


     /*
        Given Accept type is Json
        And query parameter values are:
            gender|Female
            nameContains|e
        When user sends GET request to /api/spartans/search
        Then response status code should be 200
        And response content-type: application/json
        And "Female" should be in response payload
        And "Janette" should be in response payload
     */

    @DisplayName("GET Request to /api/spartans/search with Query Params")
    @Test
    public void test3() {

        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("gender", "Female")
                .and()
                .queryParam("nameContains", "e")
                .get("/api/spartans/search");

        //Then response status code should be 200
        assertEquals(200, response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode());// same as above

        // And response content-type: application/json
        assertEquals("application/json", response.contentType());

        //And "Female" should be in response payload
        assertTrue(response.body().asString().contains("Female"));

        //And "Janette" should be in response payload

        assertTrue(response.body().asString().contains("Janette"));

        response.prettyPrint();

        //assertTrue(response.body().asString().contains("Janette"));

       /*
        We are just doing exerise to verify something in Response. THi is not the proper way to verify
        all Spartans genders are Female, or name field is Janette, We will learn different method to get specific data.
        */


    }

    @DisplayName("GET Request to /api/spartans/search with Query Params")
    @Test
    public void test4() {

        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("gender","Female");
        queryMap.put("nameContains", "e");


        //api/spartans/search?gender=Female&nameContains=e
        Response response = given().accept(ContentType.JSON)
                .when()
                .and()
                .queryParams(queryMap)
                .get("/api/spartans/search");

        //Then response status code should be 200
        assertEquals(200, response.statusCode());
        assertEquals(HttpStatus.SC_OK, response.statusCode());// same as above

        // And response content-type: application/json
        assertEquals("application/json", response.contentType());

        //And "Female" should be in response payload
        assertTrue(response.body().asString().contains("Female"));

        //And "Janette" should be in response payload

        assertTrue(response.body().asString().contains("Janette"));

        response.prettyPrint();

        //assertTrue(response.body().asString().contains("Janette"));

       /*
        We are just doing exerise to verify something in Response. THi is not the proper way to verify
        all Spartans genders are Female, or name field is Janette, We will learn different method to get specific data.
        */


    }

}

