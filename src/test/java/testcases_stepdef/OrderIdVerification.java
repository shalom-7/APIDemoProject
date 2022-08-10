package testcases_stepdef;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.runtime.junit.Assertions;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utility.ExcelReader;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;


import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

import org.testng.Assert;



public class OrderIdVerification {
	
	String excelpath = System.getProperty("user.dir") + "//ExcelData//apidata.xlsm";
	
	
	
	@Given("^User enters end points$")
	public void user_enters_end_points() throws Throwable {
		RestAssured.baseURI = "getNaceDetails";
	}

	@Then("^User enters the Order ID \"([^\"]*)\" and rownumber (\\d+)$")
	public void user_enters_the_Order_ID_and_rownumber(String SheetName, int RowNumber) throws Throwable {
		
		ExcelReader reader = new ExcelReader();
		List<Map<String, String>> testData = reader.getData(excelpath, SheetName);
		String OderIdField = testData.get(RowNumber).get("Order");
		
	   
		Response response = given()
                .contentType(ContentType.JSON)
                .param("Order", OderIdField)
                .when()
                .get("/comments")
                .then()
                .extract().response();

        Assert.assertEquals(200, response.statusCode());
     
	}



}
