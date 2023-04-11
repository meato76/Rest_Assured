package com.cydeo.day07;

import com.cydeo.pojo.Spartan;
import com.cydeo.utilities.SpartanTestBase;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class p04_SpartanFlow extends SpartanTestBase {

      /*
    Create a Spartan Flow to run below testCases dynamically
   - POST     /api/spartans
            Create a spartan Map,Spartan class
                name = "API Flow POST"
                gender="Male"
                phone=1231231231l
            - verify status code 201
            - message is "A Spartan is Born!"
            - take spartanID from response and save as a global variable
    - GET  Spartan with spartanID     /api/spartans/{id}
             - verify status code 200
             - verify name is API Flow POST
    - PUT  Spartan with spartanID    /api/spartans/{id}
             Create a spartan Map
                name = "API PUT Flow"
                gender="Female"
                phone=3213213213l
             - verify status code 204
    - GET  Spartan with spartanID     /api/spartans/{id}
             - verify status code 200
             - verify name is API PUT Flow
    - DELETE  Spartan with spartanID   /api/spartans/{id}
             - verify status code 204
     - GET  Spartan with spartanID   /api/spartans/{id}
             - verify status code 404
    Challenges
       Create @Test annotated method for each Request
       Put them in order to execute as expected
                    - Use your googling skills
                    - How to run Junit5 Tests in order  ?
     */


        private static int spartanId;

        @DisplayName("POST Request /api/spartans")
        @Test
        @Order(1)
        public void test01() {
        /* // Solution 1 with Map
        Map<String, Object> spartanMap = new LinkedHashMap<>();
        spartanMap.put("name", "API Flow POST");
        spartanMap.put("gender", "Male");
        spartanMap.put("phone", 1231231231l);*/

            // Solution 2 Spartan class
            Spartan spartan = new Spartan();
            spartan.setName("API Flow POST");
            spartan.setGender("Male");
            spartan.setPhone(1231231231l);

            JsonPath jsonPath = RestAssured.given().log().uri()
                    .accept(ContentType.JSON)
                    .and()
                    .contentType(ContentType.JSON)
                    .and()
                    .body(spartan)
                    .when()
                    .post("/api/spartans")
                    .then().log().ifValidationFails()
                    .statusCode(201)
                    .and()
                    .body("success", Matchers.is("A Spartan is Born!"))
                    .extract().jsonPath();

            spartanId = jsonPath.getInt("data.id");
        }

        @DisplayName("GET  Spartan with spartanID")
        @Test
        @Order(2)
        public void test02() {
            RestAssured.given().log().uri()
                    .accept(ContentType.JSON)
                    .and()
                    .pathParam("id", spartanId)
                    .when()
                    .get("/api/spartans/{id}")
                    .then().log().ifValidationFails()
                    .statusCode(HttpStatus.SC_OK)
                    .and()
                    .body("name", Matchers.equalTo("API Flow POST"));
        }

        @DisplayName("PUT Spartan with spartanID")
        @Test
        @Order(3)
        public void test03() {
            Map<String, Object> spartanMap = new LinkedHashMap<>();
            spartanMap.put("name", "API PUT Flow");
            spartanMap.put("gender", "Female");
            spartanMap.put("phone", 3213213213l);

            RestAssured.given().log().uri()
                    .contentType(ContentType.JSON)
                    .and()
                    .pathParam("id", spartanId)
                    .and()
                    .body(spartanMap)
                    .when()
                    .put("/api/spartans/{id}")
                    .then().log().ifValidationFails()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
        }

        @DisplayName("GET Spartan with spartanID")
        @Test
        @Order(4)
        public void test04() {
            RestAssured.given().log().uri()
                    .accept(ContentType.JSON)
                    .and()
                    .pathParam("id", spartanId)
                    .when()
                    .get("/api/spartans/{id}")
                    .then().log().ifValidationFails()
                    .statusCode(HttpStatus.SC_OK)
                    .and()
                    .body("name", Matchers.equalTo("API PUT Flow"));
        }

        @DisplayName("DELETE Spartan with spartanID")
        @Test
        @Order(5)
        public void test05() {
            RestAssured.given().log().uri()
                    .pathParam("id", spartanId)
                    .when()
                    .delete("/api/spartans/{id}")
                    .then()
                    .statusCode(HttpStatus.SC_NO_CONTENT);
        }

        @DisplayName("GET Spartan with spartanID")
        @Test
        @Order(6)
        public void test06() {
            RestAssured.given().log().uri()
                    .accept(ContentType.JSON)
                    .and()
                    .pathParam("id", spartanId)
                    .when()
                    .get("/api/spartans/{id}")
                    .then().log().ifValidationFails()
                    .statusCode(HttpStatus.SC_NOT_FOUND);
        }
    }

