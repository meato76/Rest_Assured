package com.cydeo.utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public  abstract class SpartanAuthBase {


    @BeforeAll
    public static void init() {

        RestAssured.baseURI = "http://100.26.235.33:7000";
    }

}
