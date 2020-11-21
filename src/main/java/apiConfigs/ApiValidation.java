package apiConfigs;

import static io.restassured.RestAssured.given;

import org.json.JSONObject;

import automationStepByStep.TestBase;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ExcelReader;

public class ApiValidation extends TestBase {
	

	public Response getMethod(String locationHeader, String testCase) {

		System.out.println("------Entered into GET method-----------");

		read = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/Suites/Suites.xlsx");

		String CSRFHeaderKeyName = read.getCellDataValue("APITestData", "CSRFHeaderKeyName", testCase);
		String resourceURI = read.getCellDataValue("APITestData", "ResourceURI", testCase);
		String HeaderKey = read.getCellDataValue("APITestData", "HeaderName", testCase);
		String HeaderValue = read.getCellDataValue("APITestData", "HeaderValue", testCase);
		String Query1Key = read.getCellDataValue("APITestData", "Query1Key", testCase);
		String Query1Value = read.getCellDataValue("APITestData", "Query1Value", testCase);
		String Query2Key = read.getCellDataValue("APITestData", "Query2Key", testCase);
		String Query2Value = read.getCellDataValue("APITestData", "Query2Value", testCase);
		String Query3Key = read.getCellDataValue("APITestData", "Query3Key", testCase);
		String Query3Value = read.getCellDataValue("APITestData", "Query3Value", testCase);

		System.out.println("========Extracted the values from Excel ===========");

		Response res = given().when().get(locationHeader);

		System.out.println("====printing the GET=====" + res.prettyPrint());

		System.out.println("===========Exited the GET method============");

		return res;

	}

	public Response postMethod(JSONObject requestBody, String testCase) {

		System.out.println("---------Enterd into POST method----------");

		read = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/Suites/Suites.xlsx");

//		String CSRFHeaderKeyName = read.getCellDataValue("APITestData", "CSRFHeaderKeyName", testCase);
		String resourceURI = read.getCellDataValue("APITestData", "ResourceURI", testCase);
		String HeaderKey = read.getCellDataValue("APITestData", "HeaderName", testCase);
		String HeaderValue = read.getCellDataValue("APITestData", "HeaderValue", testCase);
		String Query1Key = read.getCellDataValue("APITestData", "Query1Key", testCase);
		String Query1Value = read.getCellDataValue("APITestData", "Query1Value", testCase);
		String Query2Key = read.getCellDataValue("APITestData", "Query2Key", testCase);
		String Query2Value = read.getCellDataValue("APITestData", "Query2Value", testCase);
		String Query3Key = read.getCellDataValue("APITestData", "Query3Key", testCase);
		String Query3Value = read.getCellDataValue("APITestData", "Query3Value", testCase);

		Response res = given().contentType(ContentType.JSON).accept(ContentType.JSON).header(HeaderKey, HeaderValue)
				.body(requestBody.toString()).when().post(resourceURI);

		System.out.println("----------POST method Exited----------------");

		return res;
	}

}
