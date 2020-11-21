package automationStepByStep;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class Test01_GET {
	
	@Test
	public void test_01() {

	Response res = get("https://reqres.in/api/users?page=2");
	
	System.out.println(res.asString());
	System.out.println(res.getBody().asString());
	System.out.println(res.getStatusCode());
	System.out.println(res.getStatusLine());
	System.out.println(res.getTime());
	int statusCode = res.getStatusCode();
	
	Assert.assertEquals(statusCode, 200);
	}
	
	
	@Test
	public void test_02() {
		
		given()
			.get("https://reqres.in/api/users?page=2")
			.then().statusCode(200)
			.body("total_pages",equalTo(2), "data.id", hasItems(7,9));
	}
	
//	@Test
	void test_03() {
		get("/products").then().assertThat().body(matchesJsonSchemaInClasspath("products-schema.json"));
	}
}
