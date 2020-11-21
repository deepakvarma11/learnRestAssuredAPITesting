package automationStepByStep;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

//you can use gson, jackson, json by google, simple json by google
// Here i used gson for coverting map to json directly

public class Test02_PostPutPatchDelete {

//	@Test
	void test_GET() {
		given().get("https://reqres.in/api/users?page=2").then().statusCode(200)
				.body("data.id[1]", equalTo(8), "data.first_name", hasItems("Michael")).log().all();
	}

	@Test
	void test_POST() {

//		Map<String, Object> map = new HashMap<String, Object>();

		// map method to convert simple hashmap to json type
//		map.put("\"name\"", "\"varma\'");
//		map.put("\"job\"","\"SDET\"");
//		
//		System.out.println(map);

		// Using simple json dependency to convert map into json

//		map.put("name", "varma");
//		map.put("job","SDET");
//		
//		JSONObject request = new JSONObject(map);

		// Using simple json to convert map into json

		JSONObject request = new JSONObject();

		request.put("name", "varma");
		request.put("job", "POST");

		System.out.println(request);
		System.out.println(request.toJSONString());

		given().header("content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(request.toJSONString()).when().post("https://reqres.in/api/users").then().statusCode(201).log()
				.all();
	}

//	@Test
	void test_PUT() {

		JSONObject request = new JSONObject();

		request.put("name", "varma");
		request.put("job", "PUT");

		given().header("content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(request.toJSONString()).when().put("https://reqres.in/api/users/2").then().statusCode(200).log()
				.all();
	}

//	@Test
	void test_PATCH() {

		JSONObject request = new JSONObject();

		request.put("name", "varma");
//		request.put("job", "PATCH");

		given().header("content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(request.toJSONString()).when().patch("https://reqres.in/api/users/2").then().statusCode(200).log()
				.all();
	}

//	@Test
	void test_DELETE() {

		given().delete("https://reqres.in/api/users/2").then().statusCode(204).log().all();
	}
}
