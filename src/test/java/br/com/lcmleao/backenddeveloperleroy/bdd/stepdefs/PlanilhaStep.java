package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;


import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class PlanilhaStep extends AbstractSteps {


    @Quando("consulto a lista de todas as planilhas")
    public void quandoConsultoListaTodasPlanilhas() {

    }

    @Quando("^consulto uma planilha existente por id$")
    public void quandoConsultoPlanilhaExistentePorId() {

    }

    @Quando("^consulto uma planilha inexistente por id$")
    public void quandoConsultoPlanilhaInexistentePorId() {

    }

    @Entao("^ao menos uma planilha é retornada$")
    public void entaoAoMenosUmaPlanilhaRetornada() {

    }

    @Entao("^a planilha é retornada$")
    public void entaoPlanilhaRetornada() {

    }

    @E("^há ao menos uma planilha com status \"(.*)\"$")
    public void eHaAoMenosUmaPlanilhaComStatus(final String status) {

    }

    @Quando("processo a planilha utilizando o id")
    public void quandoProcessoPlanilhaUtilizandoId() {

    }

    @Entao("o novo status da planilha deve ser \"(.*)\"")
    public void entaoNovoStatusPlanilhaDeveSer(final String status) {

    }

}
