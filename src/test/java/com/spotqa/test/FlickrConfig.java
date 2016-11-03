package com.spotqa.test;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.internal.AuthenticationSpecificationImpl;
import com.jayway.restassured.specification.AuthenticationSpecification;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Khaja on 10/31/16.
 */
public class FlickrConfig {

    ResponseSpecification responseSpec;
    ResponseSpecBuilder resBuilder;
    RequestSpecBuilder reqBuilder;
    RequestSpecification requestSpecification;
    AuthenticationSpecification authSpec;

    Map<String, String> map;

    FlickrConfig() {

        map = new LinkedHashMap<String, String>();
        map.put("format", "json");
        map.put("nojsoncallback", "1");
        map.put("api_key", "2a687ef65c380e63590250c2556bf36f");

        reqBuilder = new RequestSpecBuilder();
        reqBuilder.addQueryParameters(map);
        reqBuilder.setBaseUri("https://api.flickr.com");
        reqBuilder.setBasePath("/services/rest/");

        authSpec = new AuthenticationSpecificationImpl(reqBuilder.build());
        authSpec.oauth("f41aa1380da1daa91e6f99e9af68c6ca",
                "f3fce6ab42a69e5b",
                "72157675710850186-683b6d39d595c2fd",
                "8ff6eacee9637bc4");
        requestSpecification = reqBuilder.build();
    }
}