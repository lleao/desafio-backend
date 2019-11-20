package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;


import br.com.lcmleao.backenddeveloperleroy.controllers.SheetController;
import br.com.lcmleao.backenddeveloperleroy.dto.SheetDTO;
import br.com.lcmleao.backenddeveloperleroy.enums.ProcessState;
import io.cucumber.java.Before;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PlanilhaStep extends AbstractSteps {

    @Autowired
    private SheetController sheetController;

    private List<SheetDTO> sheets;
    private SheetDTO sheet;
    private Exception ex;
    private SheetDTO toProcess;

    @Before
    public void setup() {
        setup(sheetController);
    }

    @Quando("consulto a lista de todas as planilhas")
    public void quandoConsultoListaTodasPlanilhas() throws Exception {
        sheets = Arrays.asList( get("/sheet", SheetDTO[].class) );
    }

    @Quando("^consulto uma planilha existente por id$")
    public void quandoConsultoPlanilhaExistentePorId() throws Exception {
        Optional<SheetDTO> random = Arrays.asList( get("/sheet", SheetDTO[].class) ).stream().findAny();
        sheet = get("/sheet/" + random.get().getId(), SheetDTO.class);
    }

    @Quando("^consulto uma planilha inexistente por id$")
    public void quandoConsultoPlanilhaInexistentePorId() throws Exception {
        try{
            ex = null;
            sheet = get("/sheet/" + Long.MAX_VALUE, SheetDTO.class);
        }catch(Exception e) {
            ex = e;
        }
    }

    @Entao("^ao menos uma planilha é retornada$")
    public void entaoAoMenosUmaPlanilhaRetornada() {
        Assert.assertTrue( sheets.size() > 1 );
    }

    @Entao("^a planilha é retornada$")
    public void entaoPlanilhaRetornada() {
        Assert.assertNotNull("Deveria retornar uma planilha", sheet);
    }

    @Entao("o erro de '(.*)' deve ocorrer")
    public void entaoErroDeveOcorrer(final String erro) {
        Assert.assertEquals("Erro não esperado",  ex.getMessage(), erro);
    }

    @E("^a planilha contém um erro$")
    public void eAPlanilhaContemErro() {

    }

    @E("^há ao menos uma planilha com status \"(.*)\"$")
    public void eHaAoMenosUmaPlanilhaComStatus(final String status) throws Exception {
        ProcessState state = ProcessState.valueOf(status);
        toProcess = Arrays.asList( get("/sheet", SheetDTO[].class) ).stream()
                .filter( sheetDTO -> sheetDTO.getState().equals(state) )
                .reduce( (f, s) -> s ).orElse(null);
        Assert.assertTrue( toProcess != null );
    }

    @Quando("processo a planilha utilizando o id")
    public void quandoProcessoPlanilhaUtilizandoId() throws Exception {
        try{
            String ret = post("/sheet/process/" + toProcess.getId(), "", String.class);
        }catch(Exception e) {
            ex = e;
        }
    }

    @Entao("o novo status da planilha deve ser \"(.*)\"")
    public void entaoNovoStatusPlanilhaDeveSer(final String status) throws Exception {
        ProcessState state = ProcessState.valueOf(status);
        SheetDTO newsheet = get("/sheet/" + toProcess.getId(), SheetDTO.class);
        Assert.assertEquals( "O estado deve ser o esperado", newsheet.getState(), state );
    }

}
