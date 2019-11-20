package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;


import br.com.lcmleao.backenddeveloperleroy.controllers.FileController;
import br.com.lcmleao.backenddeveloperleroy.dto.FileStoreDTO;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

public class UploadArquivoStep extends AbstractSteps {

    @Autowired
    private FileController ctrl;
    private Resource arquivoValido;
    private Resource arquivoInvalido;
    private Resource atual;
    private FileStoreDTO result;
    private Exception ex;

    @Before
    public void setup() {
        super.setup(ctrl);
    }

    @Dado("^um arquivo válido de importação$")
    public void dadoUmArquivoValidoImportacao() {
        arquivoValido = getStaticFile("products_teste_webdev_leroy.xlsx");
        atual = arquivoValido;
    }

    @Dado("^um arquivo inválido de importação$")
    public void dadoUmArquivoInvalidoImportacao() {
        arquivoInvalido = getStaticFile("products_teste_webdev_leroy_invalido.xlsx");
        atual = arquivoInvalido;
    }

    @Quando("o upload é efetuado")
    public void quandoUploadEfetuado() {
        try {
            ex = null;
            result = upload("/file/upload", atual.getFilename(), atual.getInputStream(), FileStoreDTO.class);
        } catch (Exception e) {
            ex = e;
        }
    }

    @Entao("^o arquivo é enviado com '(.*)' ?(.*)$")
    public void entaoAoMenosUmaCategoriaRetornada(String status, String sufixo) {
        switch (status) {
            case "sucesso":
                Assert.assertNotNull("Arquivo deve ter sido enviado com sucesso.", result);
                break;
            case "erro":
                Assert.assertNull("Arquivo deve ter sido enviado com ERRO.", result);
                break;
        }
        Assert.assertNull("Não pode ter ocorrido nenhum erro", ex);

    }

}
