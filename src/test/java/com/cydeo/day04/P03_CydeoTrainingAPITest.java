package com.cydeo.day04;

import com.cydeo.pojo.Student;
import com.cydeo.pojo.Students;
import com.cydeo.utilities.CydeoTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P03_CydeoTrainingAPITest extends CydeoTestBase {

     /*
    Given accept type is application/json
    And path param is 2
    When user send request /student/{id}
    Then status code should be 200
    And content type is application/json;charset=UTF-8
    And Date header is exist
    And Server header is envoy
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
    public void test1(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when().get("student/{id}");

        assertEquals(200,response.statusCode() );

        // verify content typ
        assertEquals("application/json;charset=UTF-8", response.contentType());


        //And Date header is exist
        assertTrue(response.headers().hasHeaderWithName("Date"));



        //And Server header is envoy
        assertEquals("envoy",response.header("server"));

//        firstName Mark
//        batch 13
//        major math
//        emailAddress mark@email.com
//        companyName Cydeo
//        street 777 5th Ave
//        zipCode 33222

        JsonPath jsonPath= response.jsonPath();

        // Deserialize to student class

        Student student = jsonPath.getObject("students[0]", Student.class);

        System.out.println("student.getFirstName() = " + student.getFirstName());
        System.out.println("student.getBatch() = " + student.getBatch());
        System.out.println("student.getMajor() = " + student.getMajor());
        System.out.println("student getEmailAddress() = " + student.getContact().getEmailAddress());
        System.out.println("student getCompanyName() = " + student.getCompany().getCompanyName());
        System.out.println("student getStreet() = " + student.getCompany().getAddress().getStreet());
        System.out.println("student getZipCode() = " + student.getCompany().getAddress().getZipCode());


        assertEquals("Mark",student.getFirstName());

        assertEquals(13,student.getBatch());

        assertEquals("math",jsonPath.getString("students[0].major"));


        assertEquals("mark@email.com",student.getContact().getEmailAddress());


        assertEquals("Cydeo",student.getCompany().getCompanyName());



        assertEquals("777 5th Ave",student.getCompany().getAddress().getStreet());


        assertEquals(33222,student.getCompany().getAddress().getZipCode());



    }

    @DisplayName("GET/Student/2")
    @Test
    public void test2(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when().get("student/{id}");

        assertEquals(200,response.statusCode() );

        // verify content typ
        assertEquals("application/json;charset=UTF-8", response.contentType());


        //And Date header is exist
        assertTrue(response.headers().hasHeaderWithName("Date"));



        //And Server header is envoy
        assertEquals("envoy",response.header("server"));

//        firstName Mark
//        batch 13
//        major math
//        emailAddress mark@email.com
//        companyName Cydeo
//        street 777 5th Ave
//        zipCode 33222

        JsonPath jsonPath= response.jsonPath();

        // Deserialize to student class

        Students students = jsonPath.getObject("", Students.class);

        //We deserialize everything to students class which is holding list of student

        Student student = students.getStudents().get(0);


        //if there is no path, we can use response .as method for deserialization
        //Students studentsWithAs = response.as(Students.class);

        System.out.println("student.getFirstName() = " + student.getFirstName());
        System.out.println("student.getBatch() = " + student.getBatch());
        System.out.println("student.getMajor() = " + student.getMajor());
        System.out.println("student getEmailAddress() = " + student.getContact().getEmailAddress());
        System.out.println("student getCompanyName() = " + student.getCompany().getCompanyName());
        System.out.println("student getStreet() = " + student.getCompany().getAddress().getStreet());
        System.out.println("student getZipCode() = " + student.getCompany().getAddress().getZipCode());


        assertEquals("Mark",student.getFirstName());

        assertEquals(13,student.getBatch());

        assertEquals("math",jsonPath.getString("students[0].major"));


        assertEquals("mark@email.com",student.getContact().getEmailAddress());


        assertEquals("Cydeo",student.getCompany().getCompanyName());



        assertEquals("777 5th Ave",student.getCompany().getAddress().getStreet());


        assertEquals(33222,student.getCompany().getAddress().getZipCode());





    }


    @DisplayName("GET/Student/2")
    @Test
    public void test3(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 2)
                .when().get("student/{id}");

        assertEquals(200,response.statusCode() );

        JsonPath jsonPath = response.jsonPath();

        com.cydeo.pojo.ready.Student student = jsonPath.getObject("students[0]", com.cydeo.pojo.ready.Student.class);


        System.out.println("student.firstName = " + student.firstName);
        System.out.println("student.birthDate = " + student.birthDate);
        System.out.println("student.password = " + student.password);
        System.out.println("student.joinDate = " + student.joinDate);
    }

}
