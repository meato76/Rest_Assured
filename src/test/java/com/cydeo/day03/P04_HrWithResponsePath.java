package com.cydeo.day03;

import com.cydeo.utilities.HrTestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.*;

public class P04_HrWithResponsePath extends HrTestBase {

    @DisplayName("GET request to countries with using Response Path")
    @Test
    public void test1() {
        Response response = given().accept(ContentType.JSON)
                .and()
                .queryParam("q", "{\"region_id\":2}")
                .when()
                .get("/countries");

        //response.prettyPrint();

        //print limit

        System.out.println("response.path(\"limit\") = " + response.path("limit"));
        //print hasMore
        System.out.println("response.path(\"hasMore\") = " + response.path("hasMore"));

        //print second country name
        System.out.println("response.path(\"items[1].country_name\") = " + response.path("items[1].country_name"));


        //print 4th element country name
        System.out.println("response.path(\"items[3].country_name\") = " + response.path("items[3].country_name"));

        //print 4th element href
        System.out.println("response.path(\"item[2].link[0].href\") = " + response.path("items[2].links[0].href"));


        //get all countries names

        List<String> allCountryNames = response.path("items.country_name");
        System.out.println("allCountryNames = " + allCountryNames);

        //get me all region_ids equal to 2

        List<Integer> allRegionsID = response.path("items.region_id");
        for (Integer id : allRegionsID) {
            assertEquals(2,id);
            System.out.println("id = " + id);

        }


    }

}
