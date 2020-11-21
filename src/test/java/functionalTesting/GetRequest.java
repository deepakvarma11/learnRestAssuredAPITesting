package functionalTesting;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utils.PropFileReader;

public class GetRequest {

	@Test
	public void getRequest() {

		String endpoint = PropFileReader.endpoint().get("token");
		RestAssured.baseURI = PropFileReader.baseURL();

		Response response = RestAssured.given().when().get(endpoint);
		System.out.println(response.then().log().all());
		
		JsonPath path = response.jsonPath();
		
		String token = path.getString("token");
		String headerName = path.getString("headerName");
		System.out.println(token + headerName);
		
		
		RestAssured.given().auth().basic("varma@aflac", "test").header(headerName,token).header("mp-portal","MEMBER").post("/auth/login").then().log().all();
		
		
		
	}
}
