package khaja.httpmethods;

import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.AuthenticationSpecificationImpl;
import com.jayway.restassured.specification.AuthenticationSpecification;
import com.jayway.restassured.specification.RequestSpecification;
import java.util.LinkedHashMap;
import java.util.Map;
import com.jayway.restassured.response.Response;
import khaja.config.BaseUri;

import static com.jayway.restassured.RestAssured.given;

/**
 * Created by Khaja on 11/2/16.
 */
public class Restify {

    public AuthenticationSpecification authenticationSpecification;
    public Map<String, String> map;
    public RequestSpecBuilder requestSpecBuilder;
    public RequestSpecification requestSpecification;
    public Restify(String formatType, String nojsoncallback, String api_key, String consumerKey, String consumerSecret, String accessToken, String secretToken){
        map = new LinkedHashMap<String, String>();
        map.put("format", formatType);
        map.put("nojsoncallback", nojsoncallback);
        map.put("api_key", api_key);

        requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addQueryParameters(map);
        requestSpecBuilder.setBaseUri(BaseUri.PROD);
        requestSpecification = requestSpecBuilder.build();

        authenticationSpecification = new AuthenticationSpecificationImpl(requestSpecification);
        authenticationSpecification.oauth(consumerKey,
                consumerSecret,
                accessToken,
                secretToken);
    }

    public Response Post(String endPoint, String requestBody){

        Response response = given().
                contentType(ContentType.JSON).body(requestBody).post(endPoint);

        return response;
    }

    public Response Get(String endPoint, Map<Object, Object> queryParams){

        Response response = given().contentType(ContentType.JSON).get(endPoint);
        return response;
    }

    public String Get(String endPoint, String[]... queryParams){

        return "";
    }

    public Response Put(String endPoint, String requestBody){

        Response response = given().
                contentType(ContentType.JSON).body(requestBody).put(endPoint);

        return response;
    }

    public Response Delete(String endPoint, Map<Object, Object> queryParams){

        Response response = given().contentType(ContentType.JSON).delete(endPoint);
        return response;
    }
}
