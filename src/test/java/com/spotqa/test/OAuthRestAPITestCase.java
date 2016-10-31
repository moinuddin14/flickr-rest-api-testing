package com.spotqa.test;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by Khaja on 10/27/16.
 */
public class OAuthRestAPITestCase {

    private static String requestTokenStr;
    private static String oauth_verifier;
    private static String accessTokenStr;
    private static String CONSUMER_KEY = "7a09488ff528e9f331b3d1a8f842b7be";
    private static String CONSUMER_SECRET="168a516857943e38";

    private static String sha1(String s, String oauth_secret_key) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {

        SecretKeySpec key = new SecretKeySpec(oauth_secret_key.getBytes("UTF-8"), "HmacSHA1");

        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(key);

        byte[] bytes = mac.doFinal(s.getBytes("UTF-8"));

        return new String(Base64.encodeBase64(bytes) );
    }

    private static void requestToken(String url)
    {
        requestTokenStr = "";
        return;
    }


    private static final void tokenRequest(){
        //Step One: Singing Requests
        //Get timestamp
        Date date = new Date();
        long oauth_timestamp = date.getTime();

        //Generate OAUTH Signature
        String realm = "https://www.flickr.com/services/oauth/request_token" +
                "?oauth_nonce=" + generateNonce() +
                "&oauth_timestamp=" + oauth_timestamp +
                "&oauth_consumer_key=" + CONSUMER_KEY +
                "&oauth_signature_method=HMAC-SHA1" +
                "&oauth_version=1.0" +
                "&oauth_callback=oob";

        String oauth_signature;
        try {
            oauth_signature = sha1(realm,CONSUMER_SECRET);
            System.out.println(oauth_signature);

            oauth_timestamp = date.getTime();

            realm = "https://www.flickr.com/services/oauth/request_token" +
                    "?oauth_nonce=" + generateNonce() +
                    "&oauth_timestamp=" + oauth_timestamp +
                    "&oauth_consumer_key=" + CONSUMER_KEY +
                    "&oauth_signature_method=HMAC-SHA1" +
                    "&oauth_signature=" + oauth_signature +
                    "&oauth_version=1.0" +
                    "&oauth_callback=oob";

            requestToken(realm);

        } catch (InvalidKeyException e) {
            System.out.println("There is an invalid Key Exception");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.out.println("There is an Unsupported Encoding Exception");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("There is no such Algorithm");
            e.printStackTrace();
        }

    }

    public static String generateNonce(){
        //return UUID.randomUUID().toString();
        String nonce = Long.toString(System.nanoTime());
        return nonce;
    }

    private static final void accessRequest(){


    }

    public static void main(String[] args)
    {
        String oauth_nonce, oauth_timestamp, oauth_signature_method,
                oauth_version = "1.0", ouath_signature, oauth_callback;

        tokenRequest();
        accessRequest();
        System.out.println(requestTokenStr);
    }
}