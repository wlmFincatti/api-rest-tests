package br.com.apirest.users.aceptancetest;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"br.com.apirest.users.aceptancetest.stepDefinitions"}
)
public class CucumberTest {
}
