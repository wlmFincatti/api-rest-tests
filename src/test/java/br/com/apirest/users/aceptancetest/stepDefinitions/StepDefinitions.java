package br.com.apirest.users.aceptancetest.stepDefinitions;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StepDefinitions extends AppContextConfig {

    private Integer userId;
    private Response response;

    @Before
    public void setup() {
        RestAssured.basePath = "/api/v1/users";
    }

    @Given("^i have user id (\\d+)$")
    public void set_user_id(Integer id) throws Throwable {
        this.userId = id;
    }

    @When("^i make a request \"(.*?)\"$")
    public void i_make_a_request(String httpVerb) throws Throwable {
        response = given()
                .pathParam("id", this.userId.toString())
                .log()
                .all()
                .request(httpVerb, "/{id}");
    }

    @Then("^receive status code (\\d+)$")
    public void receive_status_code(Integer statusCode) throws Throwable {
        response
                .then()
                .statusCode(statusCode);
    }

    @Then("^validate message \"(.*?)\"$")
    public void validate_message(String messageExpected) throws Throwable {
        String messageReturn = response
                .then()
                .extract()
                .asString();

        assertThat(messageReturn, is(messageExpected));
    }

    @Then("^validate the user name \"(.*?)\" and age (\\d+)$")
    public void validate_user(String name, int age) throws Throwable {
        response
                .then()
                .log()
                .all()
                .body("name", is(name))
                .body("age", is(age));
    }

}
