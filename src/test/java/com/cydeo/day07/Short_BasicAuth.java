package com.cydeo.day07;

import com.cydeo.utilities.SpartanAuthBase;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;



import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class Short_BasicAuth extends SpartanAuthBase {

    @Test
    public void GETRequestAsGuestUser(){

        given()
                .accept(ContentType.JSON)
                .when().get("/api/spartans")
                .then()
                .statusCode(401)
                .body("error", Matchers.is("Unauthorized"));

    }

}
