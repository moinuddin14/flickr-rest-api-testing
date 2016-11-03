package com.spotqa.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FlickrApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.sikuli.script.*;

/**
 * Created by Khaja on 10/27/16.
 */
public class FlickrScribeLibraryHelper {

    private static final String PROTECTED_RESOURCE_URL = "https://api.flickr.com/services/rest/";

    public static void main(String[] args) {
        // Replace these with your own api key and secret
        String userLogin = "moinuddinbtech";
        String apiKey = "f41aa1380da1daa91e6f99e9af68c6ca";
        String apiSecret = "f3fce6ab42a69e5b";
        OAuthService service = new ServiceBuilder().provider(FlickrApi.class).apiKey(apiKey).apiSecret(apiSecret)
                .build();
        Scanner in = new Scanner(System.in);

        System.out.println("=== Flickr's OAuth Workflow: User - " + userLogin + "===");
        System.out.println();

        /**
         *  Obtain the Request Token
         */
        Token requestToken = service.getRequestToken();
        System.out.println();

        System.out.println("Now go and authorize here (coy-paste below URL in browser, Login and grant access):");
        String authorizationUrl = service.getAuthorizationUrl(requestToken);
        System.out.println(authorizationUrl + "&perms=write");

        /**
         Use method to fetch the verifier code from the url.
         */

        String verifierValue = getVerifier(authorizationUrl + "&perms=write");

        System.out.println("And paste the verifier code here");
        System.out.print(">>>>>");

        Verifier verifier = new Verifier(verifierValue);
        System.out.println();

        // Trade the Request Token and Verifier for the Access Token
        Token accessToken = service.getAccessToken(requestToken, verifier);
        System.out.println("Got the Access Token! for API Key: " + apiKey + " and API Secret: " + apiSecret);
        System.out.println("(if your curious it looks like this: " + accessToken + " )");
        System.out.println("(you can get the username, full name, and nsid by parsing the rawResponse: "
                + accessToken.getRawResponse() + ")");
        System.out.println();

        // Now let's go and ask for a protected resource!
        System.out.println("Now we're going to access a protected resource... PROTECTED_RESOURCE_URL: " + PROTECTED_RESOURCE_URL);
        OAuthRequest request = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        request.addQuerystringParameter("method", "flickr.test.login");
        System.out.println("accessToken: \"" + accessToken.getToken().toString() + "\" , \"" + accessToken.getSecret().toString() + "\"");
        System.out.println("request: " + request.toString());
        service.signRequest(accessToken, request);
        Response response = request.send();
        System.out.println("Got it! Lets see what we found...");
        System.out.println();
        System.out.println("Response body is as below!");
        System.out.println();
        System.out.println(response.getBody());

        System.out.println();
        System.out.println("Thats it! Go and build something awesome! :)");


        System.out.println("Use case to fetch comments for a photo!");
        OAuthRequest request1 = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        request1.addQuerystringParameter("method", "flickr.photos.comments.getList");
        request1.addQuerystringParameter("api_key", apiKey);
        request1.addQuerystringParameter("photo_id", "30518692926");
        request1.addQuerystringParameter("format", "rest");
        //request1.addQuerystringParameter("auth_token", "accessToken.getToken().toString()");
        //request1.addQuerystringParameter("api_sig", "accessToken.getToken().toString()");
        service.signRequest(accessToken, request1);
        Response response1 = request1.send();
        System.out.println();
        System.out.println(response1.getBody());

        System.out.println("Use case to fetch comments for a photo!");
        OAuthRequest request2 = new OAuthRequest(Verb.POST, PROTECTED_RESOURCE_URL);
        request2.addQuerystringParameter("method", "flickr.photos.comments.addComment");
        request2.addQuerystringParameter("api_key", apiKey);
        request2.addQuerystringParameter("photo_id", "30518692926");
        request2.addQuerystringParameter("comment_text", "Hello World!");
        request2.addQuerystringParameter("format", "rest");
        request2.addQuerystringParameter("perms", "write");

        //request1.addQuerystringParameter("auth_token", "accessToken.getToken().toString()");
        //request1.addQuerystringParameter("api_sig", "accessToken.getToken().toString()");
        service.signRequest(accessToken, request1);
        Response response2 = request2.send();
        System.out.println();
        System.out.println(response2.getBody());

    }

        /*
        Write a selenium webdriver to fetch the verifier code in silent mode (use HTMLUnitDriver or Phantom Driver)
        and pass it to the method "new Verifier(in.nextLine())"
         */

    private static String getVerifier(String authorizationUrlWithPermissions) {
            /*Capabilities caps = new DesiredCapabilities();
            ((DesiredCapabilities) caps).setJavascriptEnabled(true);
            ((DesiredCapabilities) caps).setCapability("takesScreenshot", true);
            ((DesiredCapabilities) caps).setCapability(
                    PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                    "/Users/Khaja/Downloads/phantomjs-2.1.1-macosx/bin/phantomjs");

            WebDriver driver = new PhantomJSDriver(caps);*/

        System.setProperty("webdriver.chrome.driver", "/Users/Khaja/Downloads/chromedriver_2");
        WebDriver driver = new ChromeDriver();
        driver.navigate().to(authorizationUrlWithPermissions);
        File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFileToDirectory(file, new File("/Users/Khaja/Documents/SpotQA/Rest-Assured-Framework/src/resources"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Entering user name!");
        driver.findElement(By.id("login-username")).sendKeys("moinuddinbtech@yahoo.com");
        driver.findElement(By.id("login-signin")).click();
        System.out.println("Entering Password!");

        Screen screen = new Screen();

        try {
            screen.wait("resources/abc.png");
            screen.click();
            screen.write("moinuddin14");

        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        }

        driver.findElement(By.id("login-signin")).click();

        try {
            Thread.sleep(5000);
            screen.wait("resources/authorize.png");
            screen.click();

        } catch (FindFailed findFailed) {
            findFailed.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("The Verifier value is: " + driver.findElement(By.xpath("/*//*[@id='Main']/p[2]/span")).getText());
        return driver.findElement(By.xpath("/*//*[@id='Main']/p[2]/span")).getText();
    }
}