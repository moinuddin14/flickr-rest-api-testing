package com.spotqa.test;

import org.junit.Test;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * Created by Khaja on 10/27/16.
 */
public class RestAssuredTestCase {


    @Test
    public void testMethodOne() {

        String str1 = given().
                when().
                post("https://api.flickr.com/services/rest/?method=flickr.photos.comments.addComment&api_key=38bd8b2ec53acd8812a8d2069711cfd9&photo_id=30507774341&comment_text=Hello&format=rest&auth_token=72157674338234932-cddd33360313f1a1&api_sig=7db0ca5e467e4cf81cb089d00e42e752").
                then().
                extract().body().asString();
        System.out.println(str1);

    }
}