package automationStepByStep;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompare;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class FirstTestCase extends TestBase {

	@Test
	public void testdata() {

		String CSRFHeaderKeyName = read.getCellDataValue("APITestData", "CSRFHeaderKeyName", "TC_02");
		String resourceURI = read.getCellDataValue("APITestData", "ResourceURI", "TC_02");
		String HeaderKey = read.getCellDataValue("APITestData", "HeaderName", "TC_02");
		String HeaderValue = read.getCellDataValue("APITestData", "HeaderValue", "TC_02");
		String Query1Key = read.getCellDataValue("APITestData", "Query1Key", "TC_02");
		String Query1Value = read.getCellDataValue("APITestData", "Query1Value", "TC_02");
		String Query2Key = read.getCellDataValue("APITestData", "Query2Key", "TC_02");
		String Query2Value = read.getCellDataValue("APITestData", "Query2Value", "TC_02");
		String Query3Key = read.getCellDataValue("APITestData", "Query3Key", "TC_02");
		String Query3Value = read.getCellDataValue("APITestData", "Query3Value", "TC_02");

		JSONObject requestBody = new JSONObject(read.getCellDataValue("APITestData", "Body", "TC_02"));

		System.out.println("Actual:" + requestBody.toString());

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).header(HeaderKey, HeaderValue)
				.body(requestBody.toString()).when().post(resourceURI);

		String locationHeader = res.getHeader("location");
		String locationId = locationHeader.split("/")[locationHeader.split("/").length - 1];

		JSONObject responseObject = new JSONObject(given().when().get("/users/" + locationId).asString());
		responseObject.remove("id");

		System.out.println("Expected:" + responseObject.toString());

		JSONCompareResult result = JSONCompare.compareJSON(responseObject, requestBody, JSONCompareMode.STRICT);

		boolean isPassed = result.passed();

		if (isPassed = true) {
			System.out.println("Successfully done the POST method");
		}

	}

}
