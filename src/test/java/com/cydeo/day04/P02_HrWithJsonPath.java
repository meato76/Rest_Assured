package com.cydeo.day04;


import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class P02_HrWithJsonPath extends HrTestBase {

    @DisplayName("GET all /countries")
    @Test
    public void test1() {

        Response response = get("/countries");

        // response.prettyPrint();

        //Verify status code

        assertEquals(HttpStatus.SC_OK, response.statusCode());

        //Create jsonpath object

        JsonPath jsonPath = response.jsonPath();

        // get me 3d country name

        String countryName = jsonPath.getString("items[2].country_name");
        System.out.println("countryName = " + countryName);

        //get me 3rd and 4th country name at same time

        String countryNames = jsonPath.getString("items[2,3].country_name");
        System.out.println("countryNames = " + countryNames);

        //get me all country name where region_id is 2

        List<String> list = jsonPath.getList("items.findAll{it.region_id==2}.country_name");

        System.out.println("list = " + list);

    }


     /*
    Given accept type is application/json
    And query param limit is 200
    When user send request /employees
    Then user should see ............
     */


    @DisplayName("GET all /employees?limit=200 with JsonPath")
    @Test
    public void test2() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("limit", 200)
                .when().get("/employees");

        //response.prettyPrint();

        assertEquals(200, response.statusCode());

        //create jsonpath object
        JsonPath jsonPath = response.jsonPath();

        //get all emails from response
        List<String> allEmails = jsonPath.getList("items.email");
        System.out.println("allEmails = " + allEmails);
        System.out.println("allEmails.size() = " + allEmails.size());

        System.out.println("--------------------------------------------------------");
        //get all employees emails who are working as IT_PROG
        List<String> emailsIT = jsonPath.getList("items.findAll{it.job_id=='IT_PROG'}.email");
        System.out.println("emailsIT = " + emailsIT);

        System.out.println("--------------------------------------------------------");
        //get me all employees first names whose salary is more than 1000
        List<String> allEmpSalaryMoreThan10k = jsonPath.getList("items.findAll{it.salary>10000}.first_name");

        System.out.println("allEmpSalaryMoreThan10K = " + allEmpSalaryMoreThan10k);

        System.out.println("--------------------------------------------------------");

        //get me all information from response who has max salary
        System.out.println("jsonPath.getString(\"items.max {it.salary}\") = " + jsonPath.getString("items.max {it.salary}"));

        System.out.println("--------------------------------------------------------");
        //get me first name from response who has max salary
        System.out.println("jsonPath.getString(\"items.max {it.salary}.first_name\") = " + jsonPath.getString("items.max {it.salary}.first_name"));

        System.out.println("--------------------------------------------------------");
        //get me first name from respond who has min salary

        System.out.println("jsonPath.getString(\"items.min {it.salary}.first_name\") = " + jsonPath.getString("items.min {it.salary}.first_name"));
    }


}



