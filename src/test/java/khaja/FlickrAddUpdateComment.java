package khaja;

import com.jayway.restassured.response.Response;
import khaja.config.ApiConfig;
import org.testng.annotations.Test;
import java.util.List;
import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Khaja on 11/2/16.
 */
public class FlickrAddUpdateComment extends ApiConfig{

    String methodGetList = "flickr.photos.comments.getList";
    String methodAddComment = "flickr.photos.comments.addComment";
    String methodEditComment = "flickr.photos.comments.editComment";
    String idPhoto = "30424816372";
    String addedComment = "This is great photo! Love it!";
    String updatedComment = "This is lovely picture! So adorable!";
    String commentContentPath = "comments.comment._content";
    String commentsCommentId = "comments.comment.id";

    /**
     * This method will have three
     */
    @Test
    public void flickrCommentUpdateSameComment() {

        System.out.println("Fetching Comments from the body section of the Photo");

        Response responseFetch = given().spec(requestSpecification).
                when().
                queryParam("method", methodGetList).
                queryParam("photo_id", idPhoto).
                get();

        System.out.println(responseFetch.getBody().asString());
        List<String> listOfComments = responseFetch.getBody().path(commentContentPath);
        List<String> listOfCommentsid = responseFetch.getBody().path(commentsCommentId);

        for (String comment : listOfComments)
            System.out.println(comment);

        System.out.println("Adding Comment to the Photo ID: " + idPhoto);
        Response postResponse = given().spec(requestSpecification).
                when().
                queryParam("method", methodAddComment).
                queryParam("photo_id", idPhoto).
                queryParam("comment_text", addedComment).
                post();

        String commentID = postResponse.getBody().path("comment.id");

        System.out.println("The Comment ID created is: " + commentID);
        System.out.println("Response for Add Comment is -" + postResponse.getBody().asString());
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