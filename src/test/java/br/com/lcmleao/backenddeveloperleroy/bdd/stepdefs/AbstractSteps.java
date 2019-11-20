package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;

import br.com.lcmleao.backenddeveloperleroy.bdd.CucumberTestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.LocalServerPort;

/**
 * Class that abstract test context management and REST API invocation.
 *
 */
public class AbstractSteps {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractSteps.class);

    private CucumberTestContext CONTEXT = CucumberTestContext.CONTEXT;

    @LocalServerPort
    private int port;

    protected String baseUrl() {
        return "http://localhost:" + port;
    }

    protected CucumberTestContext testContext() {
        return CONTEXT;
    }


}
