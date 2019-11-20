package br.com.lcmleao.backenddeveloperleroy;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:features", extraGlue = "br.com.lcmleao", plugin = {"pretty",
		"json:target/cucumber-report.json"})
public class BackendDeveloperLeroyApplicationTests {

	@Test
	void contextLoads() {
	}

}
