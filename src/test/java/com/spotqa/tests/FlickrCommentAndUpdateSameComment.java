package com.spotqa.tests;

import com.jayway.restassured.response.Response;
import com.spotqa.config.RestApiTestConfig;
import khaja.config.BasePath;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.util.List;
import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Khaja on 10/30/16.
 */
public class FlickrCommentAndUpdateSameComment extends RestApiTestConfig {

    String methodComment = "flickr.photos.comments.getList";
    String methodAddComment = "flickr.photos.comments.addComment";
    String methodEditComment = "flickr.photos.comments.editComment";
    String methodGetList = "flickr.photos.comments.getList";
    String idPhoto = "30424816372";
    String addedComment = "Hello Mustafa";
    String updatedComment = "Hello Syed";

    @Parameters({"environment", "baseuri"})
    @Test
    public void flickrCommentUpdateSameComment() {

        System.out.println("Value passed from the mvn command on command prompt window "+System.getProperty("environment"));
        System.out.println("Fetching Comments from the body section of the Photo");

        Response resp1 = given().spec(requestSpecification).
                when().
                queryParam("method", BasePath.Photos.comments.GET_LIST_COMMENTS).
                queryParam("photo_id", idPhoto).
                get();

        System.out.println(resp1.getBody().asString());
        List<String> listOfComments = resp1.getBody().path("comments.comment._content");
        List<String> listOfCommentsid = resp1.getBody().path("comments.comment.id");

        /*for(String commentAndId: Iterables.concat(listOfComments, listOfCommentsid))
            System.out.println(commentAndId.toLowerCase());*/

        for (String comment : listOfComments)
            System.out.println(comment);

        //System.out.println(resp1.getBody().path("comments.comment._content").toString());

        System.out.println("Adding Comment to the Photo ID: " + idPhoto);
        Response resp = given().spec(requestSpecification).
                when().
                queryParam("method", methodAddComment).
                queryParam("photo_id", idPhoto).
                queryParam("comment_text", addedComment).
                post();

        String commentID = resp.getBody().path("comment.id");

        System.out.println("The Comment ID created is: " + commentID);

        System.out.println("Response for Add Comment is -" + resp.getBody().asString());

        System.out.println("Editing the newly added comment of the comment id: "
                + commentID + " with comment: "
                + addedComment + " to the updated comment: " + updatedComment);

        Response resp3 = given().
                spec(requestSpecification).
                when().
                queryParam("method", methodEditComment).
                queryParam("comment_id", commentID).
                queryParam("comment_text", updatedComment).
                post();

        System.out.println("Did the comment get updated properly?(ok/fail) " + resp3.getBody().path("stat"));

        System.out.println("Retrieving the list of comment ids to " +
                "check the updated comment is stored properly in the database");

        Response res4 = given().
                spec(requestSpecification).
                when().
                queryParam("method", methodGetList).
                queryParam("photo_id", idPhoto).
                then().
                get();

        System.out.println("List of comment ids are: ");
        System.out.println(res4.getBody().path("comments.comment.id"));

        List<String> str = res4.getBody().path("comments.comment.id");

        int i = 0;
        for (String str1 : str) {
            System.out.println("String at index " + i + " is ---> " + str1);
            i++;
        }
        System.out.println("Does the comments list containt the comment id : " + commentID + " (true/false) ---> " + res4.getBody().path("comments.comment.id").toString().contains(commentID));
        System.out.println("is the comment updated successfully?" + " ---> " + res4.getBody().path("stat"));
    }
}