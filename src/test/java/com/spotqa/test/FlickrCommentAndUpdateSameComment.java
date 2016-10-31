package com.spotqa.test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.internal.AuthenticationSpecificationImpl;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.AuthenticationSpecification;
import com.jayway.restassured.specification.ResponseSpecification;
import org.junit.Test;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by Khaja on 10/30/16.
 */
public class FlickrCommentAndUpdateSameComment {

    ResponseSpecification responseSpec;
    ResponseSpecBuilder resBuilder;
    RequestSpecBuilder reqBuilder;
    AuthenticationSpecification authSpec;

    @Test
    public void flickrCommentUpdateSameComment(){

        Map<String, String> map = new LinkedHashMap<String, String>();

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

        Response response = given().spec(reqBuilder.build()).
                /*auth().
                oauth("f41aa1380da1daa91e6f99e9af68c6ca",
                        "f3fce6ab42a69e5b",
                        "72157675710850186-683b6d39d595c2fd",
                        "8ff6eacee9637bc4").*/
                /*baseUri("https://api.flickr.com").
                basePath("/services/rest/").*/
        when().
                queryParam("method", "flickr.photos.search").
                queryParam("text", "Hyderabad").
                /*queryParam("format", "json").
                queryParam("nojsoncallback", 1).*/
                get();

        resBuilder = new ResponseSpecBuilder();
        resBuilder.expectStatusCode(200);
        /*resBuilder.expectBody("photos.photo.id[0]",is("30629494746"));*/

        response.then().spec(resBuilder.build());

        System.out.println(response.getBody().asString());

        String photoId = response.jsonPath().getList("photos.photo.id").get(0).toString();

        System.out.println(photoId);

        Response resp1 = given().spec(reqBuilder.build()).
                when().
                    queryParam("method", "flickr.photos.comments.getList").
                    queryParam("photo_id", "30424816372").
                get();

        System.out.println("Fetching Comments from the body section: "+resp1.getBody().asString());
        System.out.println(resp1.getBody().path("comments.comment._content").toString());

        /*System.out.println("Adding Comment to the Photo ID: " + photoId);
        Response resp2 = given().
                spec(reqBuilder.build()).
        when().
                queryParam("photo_id","30424816372").
                queryParam("comment_text", "New").
                queryParam("method", "flickr.photos.comments.addComment").
                queryParam("api_key", "2a687ef65c380e63590250c2556bf36f").
                post();

        System.out.println("The status code retured after adding the comment is: "+resp2.getBody().asString());*/

        String addedComment = "Johnny";
        String updatedComment = "Johnny123";

        System.out.println("Adding Comment to the Photo ID: "+"30424816372");
        Response resp=given().spec(reqBuilder.build()).
                when().
                queryParam("method", "flickr.photos.comments.addComment").
                queryParam("photo_id", "30424816372").
                queryParam("comment_text", addedComment).
                post();

        String commentID=resp.getBody().path("comment.id");

        System.out.println("The Comment ID created is: "+commentID);

        System.out.println("Response for Add Comment is -"+resp.getBody().asString());

        System.out.println("Editing the newly added comment of the comment id: "
                +commentID+" with comment: "
                +addedComment+" to the updated comment: "+updatedComment);


        Response resp3 = given().
                spec(reqBuilder.build()).
        when().
                queryParam("method", "flickr.photos.comments.editComment").
                queryParam("comment_id", commentID).
                queryParam("comment_text", updatedComment).
        post();

        System.out.println("Did the comment get updated properly? "+resp3.getBody().path("stat"));

        System.out.println("Retrieving the list of comment ids to " +
                "check the updated comment is stored properly in the database");

        Response res4 = given().
                spec(reqBuilder.build()).
        when().
                queryParam("method", "flickr.photos.comments.getList").
                queryParam("photo_id", "30424816372").
        then().
                get();

        System.out.println("List of comment ids are: ");
        System.out.println(res4.getBody().path("comments.comment.id"));

        List<String> str = res4.getBody().path("comments.comment.id");

        int i = 0;
        for(String str1:str){
            System.out.println("String at index "+i+" is ---> "+str1);
            i++;
        }
        System.out.println(res4.getBody().path("comments.comment.id").toString().contains(commentID));
    }
}