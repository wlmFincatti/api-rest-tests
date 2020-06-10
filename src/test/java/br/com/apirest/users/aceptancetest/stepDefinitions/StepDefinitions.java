package br.com.apirest.users.aceptancetest.stepDefinitions;

import br.com.apirest.users.aceptancetest.AppContextConfig;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class StepDefinitions extends AppContextConfig {

    private Integer userId;
    private Response response;
    private final Long TIME_OUT = 2000L;
    private final String BASE_PATH = "/api/v1/users";

    @Before
    public void setup() {
        RestAssured.basePath = BASE_PATH;
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder()
                .expectResponseTime(lessThanOrEqualTo(TIME_OUT));
        RestAssured.responseSpecification = responseSpecBuilder.build();
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Given("^i have user id (\\d+)$")
    public void set_user_id(Integer id) throws Throwable {
        this.userId = id;
    }

    @When("^i make a request \"(.*?)\"$")
    public void i_make_a_request(String httpVerb) throws Throwable {
        response = given()
                .pathParam("id", this.userId.toString())
                .request(httpVerb, "/{id}");
    }

    @Then("^receive status code (\\d+)$")
    public void receive_status_code(Integer statusCode) throws Throwable {
        response
                .then()
                .statusCode(statusCode);
    }

    @Then("^validate message error \"(.*?)\"$")
    public void validate_message(String messageExpected) throws Throwable {
        response
                .then()
                .body("error", is(messageExpected));
    }

    @Then("^validate the user name \"(.*?)\" and age (\\d+)$")
    public void validate_user(String name, int age) throws Throwable {
        response
                .then()
                .body("name", is(name))
                .body("age", is(age));
    }

    @And("^validate schema json \"(.*?)\"$")
    public void validate_schema_json(String jsonPath) {
        response
                .then()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(jsonPath));
    }

}
