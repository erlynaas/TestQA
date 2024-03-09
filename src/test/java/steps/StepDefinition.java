package steps;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.configuration.ConfigurationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
public class StepDefinition {Properties prop = new Properties();

    FileInputStream file;

    {
        try {
            file = new FileInputStream("./src/test/resources/config.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    RequestSpecification request;
    Response response;


    @Given("^Login API is provided$")
    public void login_API_is_provided() throws Exception {
        prop.load(file);
        RestAssured.baseURI = prop.getProperty("baseUrl");
        request = RestAssured.given();
    }

    //Purchasing Product
    @When("^User call API")
    public void User_call_API() throws Exception{
        request.header("Content-Type", "application/json");
        request.header("Authorization", "Bearer 8353105339e63638b2071d591038cb3f5ee71020");
        response = request.get("https://app.todoist.com/API/v9.1/sync");
    }

    @Then("^todoist is landing shown$")
    public void todoistislandingshown() throws Exception{
        System.out.println("Response Body is => " + response.asString());
        String productID = response.jsonPath().getString("result.data[0].images[0].product_id");
        Assert.assertEquals(productID, "6528");
    }
}
