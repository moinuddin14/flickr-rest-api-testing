package khaja.config;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.builder.ResponseSpecBuilder;
import com.jayway.restassured.internal.AuthenticationSpecificationImpl;
import com.jayway.restassured.specification.AuthenticationSpecification;
import com.jayway.restassured.specification.RequestSpecification;
import com.jayway.restassured.specification.ResponseSpecification;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Khaja on 11/2/16.
 */
public class ApiConfig {

    public RequestSpecBuilder requestSpecBuilder;
    public RequestSpecification requestSpecification;
    public ResponseSpecification responseSpecification;
    public ResponseSpecBuilder responseSpecBuilderBuilder;
    public AuthenticationSpecification authenticationSpecification;

    public static final String format="json";
    public static final String nojsoncallback="1";
    public static final String api_key="2a687ef65c380e63590250c2556bf36f";

    public static String baseUri=BaseUri.PROD;
    //public static String basePath=BaseUri.BASEPATH;

    //String consumerKey, String consumerSecret, String accessToken, String secretToken
    public static final String consumerKey="f41aa1380da1daa91e6f99e9af68c6ca";
    public static final String consumerSecret="f3fce6ab42a69e5b";
    public static final String accessToken="72157675710850186-683b6d39d595c2fd";
    public static final String secretToken="8ff6eacee9637bc4";

    Map<String, String> map;

    public ApiConfig() {

        map = new LinkedHashMap<String, String>();
        map.put("format", format);
        map.put("nojsoncallback", nojsoncallback);
        map.put("api_key", api_key);

        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addQueryParameters(map);
        requestSpecBuilder.setBaseUri(baseUri);
        //requestSpecBuilder.setBasePath(basePath);

        requestSpecification=requestSpecBuilder.build();

        responseSpecBuilderBuilder = new ResponseSpecBuilder();

        authenticationSpecification = new AuthenticationSpecificationImpl(requestSpecification);
        authenticationSpecification.oauth(consumerKey,
                consumerSecret,
                accessToken,
                secretToken);
    }
}