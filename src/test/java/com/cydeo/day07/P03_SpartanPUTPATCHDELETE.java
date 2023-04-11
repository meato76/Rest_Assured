package com.cydeo.day07;

import com.cydeo.utilities.SpartanTestBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
public class P03_SpartanPUTPATCHDELETE extends SpartanTestBase {

@DisplayName("PUT Spartan with Map")
@Test
    public void test1(){

    //we can provide json request body with map, pojo,string all is valid here too

    Map<String, Object> requestBodyMap = new HashMap<>();

    requestBodyMap.put("name", "John Doe PUT");
    requestBodyMap.put("gender", "Male");
    requestBodyMap.put("phone", "8877445596");

    //PUT will update existing record so we choose one the existing ID, make sure it exist in your IP address

    int id = 116;

    given()
            .contentType(ContentType.JSON)
            .pathParam("id",id)
            .body(requestBodyMap)
            .when().put("/api/spartans/{id}")
            .then().statusCode(204);


}


    @DisplayName("PATCH Spartan with Map")
    @Test
    public void test2(){

        //we can provide json request body with map, pojo,string all is valid here too

        Map<String, Object> requestBodyMap = new HashMap<>();

        requestBodyMap.put("name", "John Doe PATCH");


        //PUT will update existing record so we choose one the existing ID, make sure it exist in your IP address

        int id = 116;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id",id)
                .body(requestBodyMap)
                .when().patch("/api/spartans/{id}")
                .then().statusCode(204);


    }

    @DisplayName("DELETE Spartan")
    @Test
    public void test3(){

        //W e can delete one id only one time, so it will give 204 only for the first execution

        int id = 122;

        given()
                .pathParam("id",id)
                .when().delete("/api/spartans/{id}")
                .then().statusCode(204);

        //after deleted same request needs to give 404

        given()
                .pathParam("id",id)
                .when().get("/api/spartans/{id}")
                .then().statusCode(404);



    }
}
