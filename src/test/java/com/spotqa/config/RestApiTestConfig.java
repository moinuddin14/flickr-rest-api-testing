package com.spotqa.config;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.internal.AuthenticationSpecificationImpl;
import com.jayway.restassured.specification.AuthenticationSpecification;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Khaja on 11/1/16.
 */
//@Parameters("baseuri")
public class RestApiTestConfig {

    /**
     *
     */
    public final String consumerKey = "f41aa1380da1daa91e6f99e9af68c6ca";
    public final String consumerSecret = "f3fce6ab42a69e5b";
    public final String accessToken = "72157675710850186-683b6d39d595c2fd";
    public final String secretToken = "8ff6eacee9637bc4";

    public RequestSpecBuilder requestSpecBuilder;
    public RequestSpecification requestSpecification;
    public ResponseSpecBuilder responseSpecBuilder;
    public AuthenticationSpecification authenticationSpecification;

    String baseUriValue = System.getProperty("baseuri");
    String portValue = "8080";
    String basePathValue = System.getProperty("basepath"); //Example: "/services/rest/";

    Map<String, String> map;

    public Map<String, String> getMap() {
        map = new LinkedHashMap<String, String>();
        map.put("format", "json");
        map.put("nojsoncallback", "1");
        map.put("api_key", "2a687ef65c380e63590250c2556bf36f");

        return map;
    }

    /**
     * Setting up Request Builder
     * @return Returns RequestSpecBuilder with set Query Params, baseUri and basePath
     */
    public RequestSpecBuilder setRequestSpecBuilder() {

        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addQueryParameters(getMap());
        requestSpecBuilder.setBaseUri(baseUriValue);
        requestSpecBuilder.setBasePath(basePathValue);

        return requestSpecBuilder;
    }

    /**
     * Setting up Response Builder
     */
    public void setResponseSpecBuilder() {
        responseSpecBuilder = new ResponseSpecBuilder();
    }

    /**
     * Setting up Authentication Builder
     */
    public void setAuthenticationSpecification() {
        authenticationSpecification = new AuthenticationSpecificationImpl(setRequestSpecBuilder().build());

        authenticationSpecification.oauth(consumerKey,
                consumerSecret,
                accessToken,
                secretToken);
    }

    /**
     * Method to set Base Path
     */

    public RestApiTestConfig() {
        System.out.println(System.getProperty("baseuri"));
        setAuthenticationSpecification();
    }
}