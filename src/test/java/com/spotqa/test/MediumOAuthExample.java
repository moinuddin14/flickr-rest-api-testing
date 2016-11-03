package com.spotqa.test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Khaja on 10/27/16.
 */
public class MediumOAuthExample {

    String baseString1 = "GET";

    String nonce = "flickr_oauth" + String.valueOf(System.currentTimeMillis());
    String timestamp = String.valueOf(System.currentTimeMillis() / 1000);
    String callbackParam = "oauth_callback=" + oauthEncode("http://flickr.com");
    String apiKeyParam = "oauth_consumer_key=" + "7a09488ff528e9f331b3d1a8f842b7be"; //your apiKey from flickr
    String nonceParam = "oauth_nonce=" + nonce;
    String signatureMethodParam = "oauth_signature_method=" + "HMAC-SHA1";
    String timestampParam = "oauth_timestamp=" + timestamp;
    String versionParam = "oauth_version=" + "1.0";
    String unencBaseString3 = callbackParam + "&" + apiKeyParam + "&" + nonceParam + "&" + signatureMethodParam + "&" + timestampParam + "&" + versionParam;
    String baseString3 = oauthEncode(unencBaseString3);

    String requestTokenUrl = "http://www.flickr.com/services/oauth/request_token";
    String baseString2 = oauthEncode(requestTokenUrl);

    String baseString = baseString1 + "&" + baseString2 + "&" + baseString3;

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

    private String getSignature(String key, String data) {
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

    String signature = getSignature(baseString3, baseString);
    String signatureParam = "oauth_signature=" + oauthEncode(signature);

}
