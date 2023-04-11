package com.cydeo.day07;

import com.cydeo.utilities.CydeoTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class p01_CydeoTrainingDeserializationPOJO extends CydeoTestBase {

      /*
         And verify following
                firstName Mark
                batch 13
                major math
                emailAddress mark@email.com
                companyName Cydeo
                street 777 5th Ave
                zipCode 33222
         */


    @DisplayName("GET/Student/2")
    @Test
    public void test() {

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when().get("student/{id}");

        assertEquals(200, response.statusCode());

        /*
         And verify following
                firstName Mark
                batch 13
                major math
                emailAddress mark@email.com
                companyName Cydeo
                street 777 5th Ave
                zipCode 33222
         */

        JsonPath jsonPath= response.jsonPath();

        assertEquals("Mark",jsonPath.getString("students[0].firstName"));

        assertEquals(13,jsonPath.getInt("students[0].batch"));

        assertEquals("math",jsonPath.getString("students[0].major"));


        assertEquals("mark@email.com",jsonPath.getString("students[0].contact.emailAddress"));


        assertEquals("Cydeo",jsonPath.getString("students[0].company.companyName"));



        assertEquals("777 5th Ave",jsonPath.getString("students[0].company.address.street"));


        assertEquals(33222,jsonPath.getInt("students[0].company.address.zipCode"));



    }


}
