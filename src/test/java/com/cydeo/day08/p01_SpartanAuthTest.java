package com.cydeo.day08;

import com.cydeo.utilities.SpartanAuthBase;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;


public class p01_SpartanAuthTest extends SpartanAuthBase {

    @DisplayName("GET /api/spartans as GUST user --> EXPECT -->401")
    @Test
    public void test1() {

        given().accept(ContentType.JSON)
                .when().get("/api/spartans/")
                .then().log().all()
                .statusCode(401)
                .body("error", is("Unauthorized"));

    }

    @DisplayName("GET /api/spartans as USER --> EXPECT -->200")
    @Test
    public void test2() {
        given().accept(ContentType.JSON)
                .auth().basic("user", "user")
                .when().get("/api/spartans/")
                .then().statusCode(200)
                .body("error", is("Unauthorized"))
                .log().all();


    }

    @DisplayName("DELET /api/spartan/{id} as EDITOR --EXPECT --> 403 FORBIDDEN")

    @Test
    public void test3() {
        given().accept(ContentType.JSON)
                .auth().basic("editor", "editor")
                .when()
                .delete("/api/spartans/1").prettyPeek()
                .then()
                .statusCode(403)
                .body("error", is("Forbidden"));

    }

    @DisplayName("DELET /api/spartan/{id} as ADMIN--EXPECT --> 204")

    @Test
    public void test4() {
        given().accept(ContentType.JSON)
                .auth().basic("admin", "admin")
                .when()
                .delete("/api/spartans/99").prettyPeek()
                .then()
                .statusCode(204)
                .log().all();
    }

    /**
     *  HOMEWORKS
     *
     *  	Role Based Control Test --> RBAC
     *
     * 			ADMIN  -->  GET  POST PUT PATCH  DELETE   --> Spartan Flow
     * 			EDITOR -->  GET  POST PUT PATCH   403
     * 			USER   -->  GET  403  403  403    403
     * 			GUEST  -->  401  401  401  401    401
     *
     *
     *   -- Create RBAC Test for all different roles from Spartan Application including with Negative Test cases
     public static void  GETSpartans(String role,String password,int statusCode,int id){
     *
     *                 given().pathParam("id",id)
     *                 .auth().basic(role,password).
     *                 when().get("/api/spartans/{id}").then().statusCode(statusCode);
     *
     *               }
     *
     *               **/

}