package com.spotqa.test;

import org.apache.http.client.HttpClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by Khaja on 10/26/16.
 */
public class OAuthForFlickr {

    /*
    https://www.flickr.com/services/oauth/request_token
    ?oauth_nonce=89601180
    &oauth_timestamp=1305583298
    &oauth_consumer_key=653e7a6ecc1d528c516cc8f92cf98611
    &oauth_signature_method=HMAC-SHA1
    &oauth_version=1.0
    &oauth_callback=http%3A%2F%2Fwww.example.com
     */

    static String consumer_key = "e37c2eb1986d3009d5526834030664d3";
    static String signature_method = "HMAC-SHA1";
    static String oauth_version= "1.0";
    static String callback = "";

    public static void main(String[] args) {
        System.out.println(generateNonce());
        System.out.println(getTimeStamp());

        //GET&https%3A%2F%2Fwww.flickr.com%2Fservices%2Foauth%2Frequest_token&oauth_callback%3Dhttp%253A%252F%252Fwww.example.com%26oauth_consumer_key%3D653e7a6ecc1d528c516cc8f92cf98611%26oauth_nonce%3D95613465%26oauth_signature_method%3DHMAC-SHA1%26oauth_timestamp%3D1305586162%26oauth_version%3D1.0

        //Callback --- Why is this request for?

        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        map.put("oauth_nonce", generateNonce());
        map.put("oauth_timestamp", getTimeStamp());
        map.put("oauth_consumer_key", consumer_key);
        map.put("oauth_signature_method", signature_method);
        map.put("oauth_version", oauth_version);
        //map.put("oauth_callback", callback);
        map.put("nojsoncallback", "1");

        /*
        ?oauth_nonce=89601180
        &oauth_timestamp=1305583298
        &oauth_consumer_key=653e7a6ecc1d528c516cc8f92cf98611
        &oauth_signature_method=HMAC-SHA1
        &oauth_version=1.0
        &oauth_callback=http%3A%2F%2Fwww.example.com
         */

    }

    public static String generateNonce(){
        //return UUID.randomUUID().toString();
        String nonce = Long.toString(System.nanoTime());
        return nonce;
    }

    public static String getTimeStamp(){
        String timeStamp = Long.toString((System.currentTimeMillis()) / 1000);
        return timeStamp;
    }

    private String oauthEncode(String input) {
        Map<String, String> oathEncodeMap = new HashMap<String, String>();
        oathEncodeMap.put("\\*", "%2A");
        oathEncodeMap.put("\\+", "%20");
        oathEncodeMap.put("%7E", "~");
        String encoded = "";
        try {
            encoded = URLEncoder.encode(input, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : oathEncodeMap.entrySet()) {
            encoded = encoded.replaceAll(entry.getKey(), entry.getValue());
        }
        return encoded;
    }

    private String getSignature(String key, String data)
    {
        final String HMAC_ALGORITHM = "HmacSHA1";
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), HMAC_ALGORITHM);
        Mac macInstance = null;
        try {
            macInstance = Mac.getInstance(HMAC_ALGORITHM);
            macInstance.init(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] signedBytes = macInstance.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(signedBytes);
    }
    String signature = getSignature(consumer_key, "baseString");
    String signatureParam = "oauth_signature=" + oauthEncode(signature);

}