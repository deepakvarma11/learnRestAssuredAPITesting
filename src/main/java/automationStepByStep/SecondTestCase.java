package automationStepByStep;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import org.skyscreamer.jsonassert.FieldComparisonFailure;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelReader;

public class SecondTestCase extends TestBase {

	@Test
	public void testdata() {
		
		boolean isPassed = false;

		System.out.println(baseURI);

		JSONObject requestBody = new JSONObject(read.getCellDataValue("APITestData", "Body", "TC_02"));

		System.out.println("======Actual Request body from Excel:\n" + requestBody.toString() + "\n=========");

		Response postResponse = av.postMethod(requestBody, "TC_02");

		System.out.println("\n=========After POST request===========");

		String locationHeader = postResponse.getHeader("location");

		System.out.println("=======LocaitonHeader======" + locationHeader + "\n=======");
		String locationId = locationHeader.split("/")[locationHeader.split("/").length - 1];
		System.out.println("========LocationID created by post method========" + locationId + "\n--");

		System.out.println("======Get method started=========\n");

		Response GetResponse = av.getMethod(locationHeader, "TC_02");

		JSONObject responseObject = new JSONObject(GetResponse.asString());

		System.out.println("\n=======get method ended =======");

		responseObject.put("id", locationId);

		System.out.println("Expected:" + responseObject.toString());

		JSONCompareResult result = JSONCompare.compareJSON(responseObject, requestBody, JSONCompareMode.NON_EXTENSIBLE);
		
//		System.out.println("JSONCompare result: " + result);

		isPassed = result.passed();
		
		System.out.println("Boolean passed or not:" + isPassed);
		
		Assert.assertEquals(isPassed, true, "\n=======Successfully done the POST method=======");
		
//
//		if (isPassed) {
//			
//			System.out.println("\n=======Successfully done the POST method=======");
//		}

	}

}
