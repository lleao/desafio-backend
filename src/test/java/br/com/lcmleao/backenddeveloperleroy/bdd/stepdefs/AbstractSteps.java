package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;

import br.com.lcmleao.backenddeveloperleroy.bdd.CucumberTestContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.InputStream;

/**
 * Class that abstract test context management and REST API invocation.
 *
 */
public class AbstractSteps {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractSteps.class);

    private CucumberTestContext CONTEXT = CucumberTestContext.CONTEXT;

    @Autowired
    private ResourceLoader resourceLoader;

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    private MockMvc mock;

    public AbstractSteps() {

    }

    protected void setup(Object... ctrls) {
        mock = MockMvcBuilders.standaloneSetup(ctrls)
                .setControllerAdvice(this)
                .build();
    }

    protected Resource getStaticFile(String filename) {
        return resourceLoader.getResource("classpath:static/" + filename);
    }

    protected <T> T upload(String url, String filename, InputStream file, Class<T> clazzReturn) throws Exception {
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", filename, "multipart/form-data", file
        );
        MockMultipartHttpServletRequestBuilder upload = MockMvcRequestBuilders.multipart(url);
        upload.file(mockMultipartFile);
        ResultActions ret = mock.perform(upload);
        return deserializar(clazzReturn, ret.andReturn());
    }

    protected <T> T post(String url, Object body, Class<T> clazzReturn) throws Exception {
        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post(url);
        String retAsString;
        MvcResult ret;

        if ( null != body ) {
            post.content(body.toString().getBytes());
        }
        ret = mock.perform(
                post
        ).andReturn();
        return null != clazzReturn ? deserializar(clazzReturn, ret) : null;
    }

    protected <T> T put(String url, Object body, Class<T> clazzReturn) throws Exception {
        MockHttpServletRequestBuilder put = MockMvcRequestBuilders.put(url);
        String retAsString;
        MvcResult ret;

        if ( null != body ) {
            put.content(body.toString().getBytes());
        }
        ret = mock.perform(
                put
        ).andReturn();
        return null != clazzReturn ? deserializar(clazzReturn, ret) : null;
    }

    protected <T> T get(String url, Class<T> clazzReturn) throws Exception {
        MvcResult ret = mock.perform(
                MockMvcRequestBuilders.get(url)
        ).andReturn();
        return null != clazzReturn ? deserializar(clazzReturn, ret) : null;
    }

    protected <T> T delete(String url, Class<T> clazzReturn) throws Exception {
        MvcResult ret = mock.perform(
                MockMvcRequestBuilders.delete(url)
        ).andReturn();
        return null != clazzReturn ? deserializar(clazzReturn, ret) : null;
    }

    private <T> T deserializar(Class<T> clazzReturn, MvcResult ret) throws Exception {
        String retAsString;
        if (  null != ret ) {
            retAsString = ret.getResponse().getContentAsString();
            return mapper.readValue(retAsString, clazzReturn);
        }
        // Se não foi 2xx deve lançar erro
        if (  ret.getResponse().getStatus() != 200) {
            throw new Exception("Retorno vazio e status != 200");
        }
        // Tenta retornar uma instância da classe vazia
        return clazzReturn.newInstance();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handle(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            "falha"
        );
    }

    protected String baseUrl() {
        return "http://localhost:" + port;
    }

    protected CucumberTestContext testContext() {
        return CONTEXT;
    }


}
