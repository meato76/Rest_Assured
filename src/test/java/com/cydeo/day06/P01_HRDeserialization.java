package com.cydeo.day06;


import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class P01_HRDeserialization extends HrTestBase {

    /**
     * Create a test called getLocation
     * 1. Send request to GET /locations
     * 2. log uri to see
     * 3. Get all Json as a map and print out screen all the things with the help of  map
     * System.out.println("====== GET FIRST LOCATION  ======");
     * System.out.println("====== GET FIRST LOCATION LINKS  ======");
     * System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
     * System.out.println("====== FIRST LOCATION ======");
     * System.out.println("====== FIRST LOCATION ID ======");
     * System.out.println("====== FIRST LOCATION COUNTRY_ID ======");
     * System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");
     */

    @DisplayName("GET/ locations to deserialization into Java Collections")


    @Test
    public void test1() {

        Response response = given().log().uri()
                .accept(ContentType.JSON)
                .when()
                .get("/locations")
                .then()
                .statusCode(200)
                .extract().response();

        JsonPath jsonPath = response.jsonPath();

        //items[0]
        System.out.println("====== GET FIRST LOCATION  ======");
        Map<String, Object> firstMap = jsonPath.getMap("items[0]");
        System.out.println("firstMap = " + firstMap);
        System.out.println("firstMap.get(\"location_id\") = " + firstMap.get("location_id"));


        System.out.println("====== GET FIRST LOCATION LINKS  ======");

        Map<String, Object> firstMapLinks = jsonPath.getMap("items[0].links[0]");
        System.out.println("firstMapLinks = " + firstMapLinks);

        System.out.println("====== GET ALL LOCATIONS AS LIST OF MAP======");
        List<Map<String,Object>> allLocationsMAp = jsonPath.getList("items");

        for (Map<String, Object> eachLocations : allLocationsMAp) {
            System.out.println("eachLocation = " + eachLocations);
        }

        System.out.println("====== FIRST LOCATION ======");
        System.out.println("allLocationsMAp.get(0) = " + allLocationsMAp.get(0));


        System.out.println("====== FIRST LOCATION ID ======");
        System.out.println("allLocationsMAp.get(\"location_id\") = " + allLocationsMAp.get(0).get("location_id"));


        System.out.println("====== FIRST LOCATION COUNTRY_ID ======");
        System.out.println("allLocationsMAp.get(0).get(\"country_id\") = " + allLocationsMAp.get(0).get("country_id"));


        System.out.println("====== GET FIRST LOCATION FIRST LINK  ====== ");
        System.out.println("allLocationsMAp.get(0).get(\"links\") = " + allLocationsMAp.get(0).get("links"));

        //we want to get href from first location what we need to do?

       List<Map<String,Object>> links = (List<Map<String, Object>>) allLocationsMAp.get(0).get("links");
        System.out.println("links = " + links);

        System.out.println("links.get(0).get(\"href\") = " + links.get(0).get("href"));

    }

}
