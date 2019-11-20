package br.com.lcmleao.backenddeveloperleroy.bdd.stepdefs;


import br.com.lcmleao.backenddeveloperleroy.controllers.CategoryController;
import br.com.lcmleao.backenddeveloperleroy.controllers.ItemController;
import br.com.lcmleao.backenddeveloperleroy.controllers.SheetController;
import br.com.lcmleao.backenddeveloperleroy.dto.CategoryDTO;
import br.com.lcmleao.backenddeveloperleroy.dto.ItemDTO;
import io.cucumber.java.Before;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ItemStep extends AbstractSteps {
    @Autowired
    private SheetController sheetController;
    @Autowired
    private CategoryController categoryController;
    @Autowired
    private ItemController itemController;
    private List<CategoryDTO> cat;
    private List<ItemDTO> itens;
    private Optional<ItemDTO> item;
    private Long catItem;
    private Exception ex;


    @Before
    public void setup() {
        setup(sheetController, itemController, categoryController);
    }

    @E("que há uma categoria com ao menos (.*) itens")
    public void eHaUmaCategoriaComAoMenosXItens(final Integer quantidadeItens) throws Exception {
        cat = Arrays.asList(get("/category", CategoryDTO[].class))
                .stream()
                .filter( (each) -> each.getItens().size() > quantidadeItens )
                .collect(Collectors.toList());
        catItem =  cat.stream().map( (c) -> c.getId() ).findAny().orElse(1L);
        itens = Arrays.asList(
                get("/item/category/" + catItem, ItemDTO[].class)
        );
        Assert.assertTrue("Deve haver ao menos uma categoria com mais de " + quantidadeItens + " itens", cat.size() > 0);
    }

    @E("^seleciono im item qualquer")
    public void eSelecionoUmItemQualquer() {
        item = itens.stream().findAny();
    }

    @E("^é adicionado ao final do nome do item '(.*)'$")
    public void eEhAdicionadoFinalNome(final String sufixo) throws Exception {
        item = itens.stream().filter( (i) -> !i.getName().endsWith(sufixo) ).findAny();
        item.get().setName( item.get().getName() + sufixo );
    }

    @E("^há um item inexistente na categoria$")
    public void eHaItemInexistenteCategoria() throws Exception {
        item.get().setId(Long.MAX_VALUE);
    }

    @Quando("^atualizo o item$")
    public void quandoAtualizoItem() throws Exception {
        try {
            ex = null;
            ItemDTO newitem = put(
                    String.format("/item/category/%d/item", catItem),
                    item.get(),
                    ItemDTO.class
            );
            item = Optional.of(newitem);
        }catch(Exception e) {
            ex = e;
        }

    }

    @Quando("^consulto a lista de itens pela categoria$")
    public void quandoConsultoListaItensCategoria() throws Exception {
        itens = Arrays.asList(
            get("/item/category/" + cat.stream().map( (c) -> c.getId() ).findAny().orElse(1L), ItemDTO[].class)
        );

    }

    @Entao("ao menos (.*) itens são retornados")
    public void entaoAoMenosXItensRetornados(final Integer quantidadeItens) {
        Assert.assertTrue(
            String.format( "Ao menos %d itens devem ser retornados", quantidadeItens ),
            itens.size() >= quantidadeItens
        );
    }

    @Entao("^o item é atualizado com sucesso e o nome contém '(.*)'$")
    public void entaoItemAtualizadoComSucessoNomeContem(final String contem) {
        Assert.assertTrue("Nome deve ser atualizado", item.get().getName().contains(contem));
    }

    @Entao("o erro '(.*)' é gerado para o item")
    public void entaoErroGeradoParaItem(final String erro) {
        Assert.assertNotNull("Erro esperado", ex);
        Assert.assertEquals("Erro inesperado", ex.getMessage(), erro);
    }

    @Entao("o item é excluído com sucesso")
    public void entaoItemExcluidoComSucesso() {
        Assert.assertNull("Erro não esperado", ex);
    }

    @Quando("^excluo o item$")
    public void quandoExcluoItem() {
        try {
            ex = null;
            delete(
                    String.format("/item/category/%d/item/%d", catItem, item.get().getId()), String.class
            );
        }catch(Exception e) {
            ex = e;
        }
    }
}
