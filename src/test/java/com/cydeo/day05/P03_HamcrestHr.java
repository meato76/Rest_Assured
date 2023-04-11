package com.cydeo.day05;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
public class P03_HamcrestHr extends HrTestBase {

    /*
          Given accept type is Json
          And parameters: q = {"job_id":"IT_PROG"}
          When users sends a GET request to "/employees"
          Then status code is 200
          And Content type is application/json
          Verify
              - each employees has manager
              - each employees working as IT_PROG
              - each of them getting salary greater than 3000
              - first names are .... (find proper method to check list against list)
              - emails without checking order (provide emails in different order,just make sure it has same emails)
              List<String> names = Arrays.asList("Alexander","Bruce","David","Valli","Diana");
              "DAUSTIN","AHUNOLD","BERNST","VPATABAL","DLORENTZ"
     */

    @DisplayName("GET employees PROG with hamcrest")
    @Test
    public void test1() {


        List<String> names = Arrays.asList("Alexander","Bruce","David","Valli","Diana");

        given()
                .accept(ContentType.JSON)
                .queryParam("q","{\"job_id\":\"IT_PROG\"}")
                .when()
                .get("/employees")
                .then()
                .statusCode(200)
                .and()
                .contentType("application/json")
                .and()
                .body("items.manager_id",everyItem(notNullValue()))
                .body("items.job_id",everyItem(equalTo("IT_PROG")) )
                .body("items.salary",everyItem(greaterThan(3000)))
                .body("items.first_name",equalTo(names))
                .body("items.email",containsInAnyOrder("DAUSTIN","AHUNOLD","BERNST","VPATABAL","DLORENTZ"));


    }

     /*
      Given
               accept type is application/json
       When
               user sends get request to /regions
       Then
               response status code must be 200
               verify Date has values
               first region name is Europe
               first region id is 1
               four regions we have
               region names are not null
               Regions name should be same order as "Europe","Americas","Asia","Middle East and Africa"
               region ids needs to be 1,2,3,4
               print all the regions names
               ...
               ..
               .
    */


    @Test
    public void test2(){

        JsonPath jsonPath =
                given()
                    .log().ifValidationFails()
                    .accept(ContentType.JSON)
                .when()
                    .get("/regions")
                .then()
                    .log().ifValidationFails()
                    .statusCode(200)
                    .contentType("application/json")
                    .header("Date", notNullValue())
                    .body("items[3].region_name", is(equalTo("Europe")))
                    .body("items[3].region_id", equalTo(1))
                    .body("items", hasSize(9))
                    .body("items.region_name", everyItem(notNullValue()))
                    .body("items.region_name", containsInRelativeOrder("Europe", "Americas", "Asia", "Middle East and Africa"))
                    .body("items.region_id", containsInRelativeOrder(1, 2, 3, 4))
                .extract().jsonPath();
        List<String> allRegionsNames = jsonPath.getList("items.region_name");
        System.out.println("allRegionsNames = " + allRegionsNames);

        //get region names from UI or Database


        //compare


    }

}
