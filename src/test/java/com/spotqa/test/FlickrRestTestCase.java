package com.spotqa.test;


import com.jayway.restassured.response.Response;
import org.testng.annotations.Test;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by Khaja on 10/30/16.
 */
public class FlickrRestTestCase {

//    @Test
//    public void testOne() {
//
//        /*
//        auth()..oauth("f41aa1380da1daa91e6f99e9af68c6ca", "f3fce6ab42a69e5b", "72157675710850186-683b6d39d595c2fd", "8ff6eacee9637bc4").
//         */
//        String str = given().auth().oauth("f41aa1380da1daa91e6f99e9af68c6ca", "f3fce6ab42a69e5b", "72157675710850186-683b6d39d595c2fd", "8ff6eacee9637bc4").
//                when().
//                        post("https://api.flickr.com/services/rest/?method=flickr.photos.comments.addComment&api_key=2a687ef65c380e63590250c2556bf36f&photo_id=30424816372&comment_text=jolly&format=json&nojsoncallback=1").
//                        getBody().asString();
//
//        System.out.println(str);
//
//    }

    String commentID;

    @Test
    public void testTwo(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Starting of Test Case Two");
        Response resp=given().auth().oauth("f41aa1380da1daa91e6f99e9af68c6ca", "f3fce6ab42a69e5b", "72157675710850186-683b6d39d595c2fd", "8ff6eacee9637bc4").baseUri("https://api.flickr.com").basePath("/services/rest/").
                when().
                queryParam("method", "flickr.photos.comments.addComment").
                queryParam("api_key", "2a687ef65c380e63590250c2556bf36f").
                queryParam("photo_id", "30424816372").
                queryParam("comment_text", "Hoola").
                queryParam("format", "json").
                queryParam("nojsoncallback", 1).
                post();

        commentID=resp.getBody().path("comment.id");

        System.out.println(commentID);

        System.out.println("Response for Add Comment is -"+resp.getBody().asString());
        /*
        ?method=flickr.photos.comments.addComment&api_key=2a687ef65c380e63590250c2556bf36f&photo_id=30424816372&comment_text=jolly&format=json&nojsoncallback=1
         */
//        System.out.println(str);
    }

    @Test(dependsOnMethods = "testTwo")
    public void testThree(){
        System.out.println(this.commentID);
        Response response = given().auth().oauth("f41aa1380da1daa91e6f99e9af68c6ca", "f3fce6ab42a69e5b", "72157675710850186-683b6d39d595c2fd", "8ff6eacee9637bc4").baseUri("https://api.flickr.com").basePath("/services/rest/").
                when().
                queryParam("method", "flickr.photos.comments.deleteComment").
                //queryParam("api_key", "2a687ef65c380e63590250c2556bf36f").
                queryParam("comment_id", commentID).
                queryParam("format", "json").
                queryParam("nojsoncallback", 1).
                post();

        System.out.println(response.getBody().path("stat"));

        /*
        ?method=flickr.photos.comments.addComment&api_key=2a687ef65c380e63590250c2556bf36f&photo_id=30424816372&comment_text=jolly&format=json&nojsoncallback=1
         */
    }

    @Test
    public void testFour(){
        System.out.println("Use Case for searching Photos");
        given().
                auth().
                oauth("f41aa1380da1daa91e6f99e9af68c6ca",
                        "f3fce6ab42a69e5b",
                        "72157675710850186-683b6d39d595c2fd",
                        "8ff6eacee9637bc4").
                baseUri("https://api.flickr.com").
                basePath("/services/rest/").
        when().
                queryParam("method", "flickr.photos.search").
                queryParam("");
    }
}