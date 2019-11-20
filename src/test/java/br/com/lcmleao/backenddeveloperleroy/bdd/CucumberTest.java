package br.com.lcmleao.backenddeveloperleroy.bdd;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

/**
 * To run cucumber test
 */
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", extraGlue = "br.com.lcmleao")
public class CucumberTest {

}