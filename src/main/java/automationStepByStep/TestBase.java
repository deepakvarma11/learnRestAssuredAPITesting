package automationStepByStep;

import static io.restassured.RestAssured.baseURI;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import apiConfigs.ApiValidation;
import utils.ExcelReader;
import utils.PropFileReader;

public class TestBase {

	protected ExcelReader read;
	protected String locationHeader;
	protected String baseUri;

	ApiValidation av;

	@BeforeTest
	public void testbase() {

		System.out.println("=============BeforeTest started============");
		av = new ApiValidation();

		read = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/Suites/Suites.xlsx");

		baseURI = PropFileReader.endpoint().get("baseURI");

	}

	@AfterTest
	void test() {
		System.out.println("=======AfterTest completed==========");
	}
}
