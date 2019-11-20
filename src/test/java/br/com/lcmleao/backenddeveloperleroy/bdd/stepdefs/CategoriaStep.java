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

public class CategoriaStep extends AbstractSteps {
    @Autowired
    private FileController fileCtrl;

    private FileStoreDTO stored;

    private Exception ex;
    @Before
    public void setup() {
        setup(fileCtrl);
    }

    @Dado("^que já existe um arquivo já importado com sucesso$")
    public void dadoQueExisteArquivoJaImportadoComSucesso() {
        Resource atual = getStaticFile("products_teste_webdev_leroy.xlsx");
        try {
            ex = null;
            stored = upload("/file/upload", atual.getFilename(), atual.getInputStream(), FileStoreDTO.class);
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertNotNull("Arquivo deve ter sido enviado para upload", stored);
        Assert.assertNull("Não pode ter ocorrido nenhum erro", ex);
    }

    @Dado("^que já existe um arquivo com erro já importado com sucesso$")
    public void dadoQueExisteArquivoComErroJaImportadoComSucesso() {
        Resource atual = getStaticFile("products_teste_webdev_leroy_invalido.xlsx");
        try {
            ex = null;
            stored = upload("/file/upload", atual.getFilename(), atual.getInputStream(), FileStoreDTO.class);
        } catch (Exception e) {
            ex = e;
        }
        Assert.assertNotNull("Arquivo deve ter sido enviado para upload", stored);
        Assert.assertNull("Não pode ter ocorrido nenhum erro", ex);
    }

    @Quando("consulto a lista de todas as categorias")
    public void quandoConsultoListaTodasCategorias() {

    }

    @Entao("^ao menos uma categoria é retornada$")
    public void entaoAoMenosUmaCategoriaRetornada() {

    }

    @Quando("^consulto uma categoria existente por id$")
    public void quandoConsultoCategoriaExistentePorId() {

    }

    @Entao("^a categoria é retornada$")
    public void entaoCategoriaRetornada() {

    }

    @Quando("consulto uma categoria inexistente por id")
    public void quandoConsultoCategoriaInexistenteId() {

    }

    @Entao("^o erro de '(.*)' deve ocorrer para categoria$")
    public void entaoOErroDeveOcorrer(String error) {

    }


}
