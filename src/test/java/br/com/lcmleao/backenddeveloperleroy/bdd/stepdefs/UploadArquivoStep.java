package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;


import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class UploadArquivoStep extends AbstractSteps {

    @Dado("^um arquivo válido de importação$")
    public void dadoUmArquivoValidoImportacao() {

    }

    @Dado("^um arquivo inválido de importação$")
    public void dadoUmArquivoInvalidoImportacao() {

    }

    @Quando("o upload é efetuado")
    public void quandoUploadEfetuado() {

    }

    @Entao("^o arquivo é enviado com '(.*)' ?(.*)$")
    public void entaoAoMenosUmaCategoriaRetornada(String status, String sufixo) {

    }

    @Entao("o erro de {string} deve ocorrer")
    public void entaoOErroDeveOcorrer(String error) {

    }


}
