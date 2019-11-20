package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;


import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class CategoriaStep extends AbstractSteps {

    @Dado("^que já existe um arquivo já importado com sucesso$")
    public void dadoQueExisteArquivoJaImportadoComSucesso() {

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
