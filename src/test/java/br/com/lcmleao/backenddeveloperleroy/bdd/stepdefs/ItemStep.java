package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;


import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class ItemStep extends AbstractSteps {

    @E("que há uma categoria com ao menos (.*) itens")
    public void eHaUmaCategoriaComAoMenosXItens(final Integer quantidadeItens) {

    }

    @E("^é adicionado ao final do nome do item '(.*)'$")
    public void eEhAdicionadoFinalNome(final String sufixo) {

    }

    @E("^há um item inexistente na categoria$")
    public void eHaItemInexistenteCategoria() {

    }

    @Quando("^atualizo o item$")
    public void quandoAtualizoItem() {

    }

    @Quando("^consulto a lista de itens pela categoria$")
    public void quandoConsultoListaItensCategoria() {

    }

    @Entao("ao menos (.*) itens são retornados")
    public void entaoAoMenosXItensRetornados(final Integer quantidadeItens) {

    }

    @Entao("^o item é atualizado com sucesso e o nome contém '(.*)'$")
    public void entaoItemAtualizadoComSucessoNomeContem(final String contem) {

    }

    @Entao("o erro '(.*)' é gerado para o item")
    public void entaoErroGeradoParaItem(final String erro) {

    }

    @Entao("o item é excluído com sucesso")
    public void entaoItemExcluidoComSucesso() {

    }

    @Entao("o erro '(.*)' é gerado")
    public void entaoErroGerado(final String erro) {

    }

    @Quando("^excluo o item$")
    public void quandoExcluoItem() {

    }
}
