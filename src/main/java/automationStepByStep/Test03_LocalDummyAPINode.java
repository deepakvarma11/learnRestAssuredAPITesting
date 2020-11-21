package automationStepByStep;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class Test03_LocalDummyAPINode {
	
//	@Test
	void test_GET01() {
		baseURI = "http://localhost:3000/";
		
		given().param("name", "Automation").when().get("/subjects").
			then().statusCode(200).log().all();
		
	}
	
//	@Test
	void test_GET() {
		baseURI = "http://localhost:3000/";
		
		given().when().get("/users/4").
			then().statusCode(200).log().all();
		
	}
	
//	@Test
	void test_POST() {
		baseURI = "http://localhost:3000/";
		
		JSONObject req = new JSONObject();
		req.put("first_name", "Geetha");
		req.put("last_name", "Sri");
		req.put("subjectId", "2");
		
		given().
			contentType(ContentType.JSON).accept(ContentType.JSON).header("Content-Type", "application/json").
			body(req.toJSONString()).when().post("/users").then().statusCode(201).log().all();
	}
	
//	@Test
	void test_PUT() {
		baseURI = "http://localhost:3000/";
		
		JSONObject req = new JSONObject();
		req.put("first_name", "Geetha");
		req.put("last_name", "Sri Pandi ");
		req.put("subjectId", "2");
		
		given().
			contentType(ContentType.JSON).accept(ContentType.JSON).header("Content-Type", "application/json").
			body(req.toJSONString()).when().put("/users/4").then().statusCode(200).log().all();
	}
	
//	@Test
	void test_patch() {
		baseURI = "http://localhost:3000/";
		
		JSONObject req = new JSONObject();
		req.put("first_name", "Deepak");
		req.put("last_name", "varma");
		
		given().
			contentType(ContentType.JSON).accept(ContentType.JSON).header("Content-Type", "application/json").
			body(req.toJSONString()).when().patch("/users/1").then().statusCode(200).log().all();
	}
	
//	@Test
	void test_Delete() {
		baseURI = "http://localhost:3000/";
		
		given().
			when().delete("/users/4").then().statusCode(200).log().all();
	}
	

}
