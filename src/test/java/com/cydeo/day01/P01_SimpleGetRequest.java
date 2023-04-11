package com.cydeo.day01;

import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class P01_SimpleGetRequest {

    String url= "http://100.26.235.33:8000/api/spartans";

    /*

    When users send request to /api/spartans endpoint
    Then user should be able to see status code is 200
    and print out response body into screen
     */

    @Test
    public void simpleGetRequest(){

        Response response = RestAssured.get(url);

        // both same no differance, they will get the status com
        System.out.println("response.statusCode() = " + response.statusCode());
        System.out.println("response.getStatusCode() = " + response.getStatusCode());

        //verify that status code is 200
        int actualStatusCode = response.statusCode();

        //assert that it is 200

        Assertions.assertEquals(200,actualStatusCode);

        // how to print json response body on console
        response.prettyPrint();



    }
}
