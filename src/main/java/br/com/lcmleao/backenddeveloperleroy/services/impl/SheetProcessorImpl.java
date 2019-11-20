package br.com.lcmleao.backenddeveloperleroy.services.impl;

import br.com.lcmleao.backenddeveloperleroy.configurations.QueueConfiguration;
import br.com.lcmleao.backenddeveloperleroy.dto.SheetDTO;
import br.com.lcmleao.backenddeveloperleroy.entities.Category;
import br.com.lcmleao.backenddeveloperleroy.entities.FileStore;
import br.com.lcmleao.backenddeveloperleroy.entities.Item;
import br.com.lcmleao.backenddeveloperleroy.entities.Sheet;
import br.com.lcmleao.backenddeveloperleroy.enums.ProcessState;
import br.com.lcmleao.backenddeveloperleroy.exceptions.SheetProcessException;
import br.com.lcmleao.backenddeveloperleroy.repositories.CategoryRepository;
import br.com.lcmleao.backenddeveloperleroy.repositories.FileStoreRepository;
import br.com.lcmleao.backenddeveloperleroy.repositories.ItemRepository;
import br.com.lcmleao.backenddeveloperleroy.repositories.SheetRepository;
import br.com.lcmleao.backenddeveloperleroy.services.SheetProcessor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/***
 * Implementação do serviço de processamento de planilha
 */
@Service
public class SheetProcessorImpl implements SheetProcessor {
    @Autowired
    private FileStoreRepository fileStoreRepository;

    @Autowired
    private SheetRepository sheetRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private QueueConfiguration queueConfiguration;

    /***
     * Interface para uma localização dentro do arquivo.
     * Serve para marcar pontos importantes e então percorrer o arquivo de forma relativa
     */
    public interface Hook {
        public Pair<Integer, Integer> cell();
    }

    /***
     * Enfileira o arquivo para ser processado.
     * @param id Do arquivo que deve ser enfileirado
     */
    @Transactional
    @Override
    public void queueFile(Long id) {
        FileStore file = fileStoreRepository.getOne(id);
        Sheet sheet = getOrCreateSheet(file);
        rabbitTemplate.convertAndSend(
                "amq.topic",
                queueConfiguration.getQueue(),
                SheetDTO.builder()
                .id( sheet.getId() )
                .build()
        );
    }

    /***
     * Retorna uma nova categoria ou obtém uma existente do banco
     * @param key Categoria exemplo
     * @return Category categoria criada ou obtida
     */
    private Category getOrCreateCategory(Category key) {
        return categoryRepository.findOne(
                Example.of(key)
        ).orElseGet( () -> categoryRepository.save(key) );
    }

    /***
     * Processa uma planilha baseado no id
     * @param sheetId
     */
    @Transactional
    @Override
    public void processSheet(Long sheetId) {
        // Obtém uma planilha que deve ser processada
        Sheet sheet = sheetRepository.findById(sheetId).orElseThrow(() -> new SheetProcessException("Planilha não localizada"));
        Category cat;
        // Se não está no estado correto lança erro
        if ( sheet.getState() != ProcessState.QUEUED ) {
            throw new SheetProcessException("A planilha não pode ser processada. STATE != ProcessState.QUEUED");
        }
        try {
            Map<Category, List<Item>> ret;

            sheet.setState(ProcessState.PROCESSING);
            sheet = sheetRepository.save(sheet);

            ret = transform(sheet.getFileStore().getResource().openStream());

            sheet.setSuccess(true);
            sheet.setState(ProcessState.DONE);
            sheet = sheetRepository.save(sheet);

            sheetRepository.save(sheet);
            // Para cada categoria, salva os itens
            ret.forEach( (key, vals) -> {
                Category newCat = getOrCreateCategory(key);
                vals.forEach( (item) -> item.setCategory(newCat) );
                itemRepository.saveAll(vals);
            } );

        } catch (Exception e) {
            sheet.setSuccess(false);
            sheet.setState(ProcessState.ERROR);
            sheetRepository.save(sheet);
            throw new SheetProcessException("Resource Inacessível", e);
        }
    }

    /***
     * Transforma o arquivo em entidades passiveis de serem salvas no banco.
     * Retorna um mapa, pois, pode ser possível adicionar uma categoria por aba
     * @param in Stream para os dados que devem ser importados
     * @return
     */
    public Map<Category, List<Item>> transform( InputStream in ) {
        Map<Category, List<Item>> mapa = new HashMap<>();
        XSSFWorkbook workbook = null;
        XSSFSheet worksheet;
        Category category = new Category();
        try {
            workbook = new XSSFWorkbook(in);
        } catch (IOException e) {
            throw new SheetProcessException("Falha ao consumir arquivo", e);
        }

        worksheet = workbook.getSheetAt(0);
        Hook chook = null; //Gancho para a posição de Category
        Hook headerHook = null; //Gancho para o cabeçalho
        for(int i = 0;i<worksheet.getPhysicalNumberOfRows() ;i++) {
            XSSFRow row = worksheet.getRow(i);
            if ( null == row ) {
                continue;
            }
            // Detecta onde começa o cabeçalho
            if ( null != chook && null == headerHook) {
                for ( int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++ ) {
                    if ( null != row.getCell(j) ) {
                        Pair<Integer, Integer> cellHook = Pair.of(
                                i, j
                        );
                        headerHook = () -> cellHook;
                        break;
                    }
                }
            } else if( null == chook) {
                // Detecta onde está a informação da categoria
                for ( int j = row.getFirstCellNum(); j < row.getLastCellNum(); j++ ) {
                    if ( "Category".equalsIgnoreCase( row.getCell(j).getStringCellValue() )) {
                        Pair<Integer, Integer> cellHook = Pair.of(
                                i, j
                        );
                        chook = () -> cellHook;
                        break;
                    }
                }
            } else {
                break;
            }

        }
        category.setCategory(
                getCellValueAsString(
                        worksheet.getRow( chook.cell().getFirst() ),
                        chook.cell().getSecond()+1
                )
        );
        mapa.put( category, new LinkedList<>() );
        // Começa a consumir a partir da linha de dados
        for(int i=headerHook.cell().getFirst()+1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            Integer ptr = headerHook.cell().getSecond();
            Item item = Item.builder()
                    .code( getCellValueAsString(row, ptr++) )
                    .name( getCellValueAsString(row, ptr++ ) )
                    .freeShipping( getCellValueAsBoolean(row, ptr++)  )
                    .description( getCellValueAsString(row, ptr++ ) )
                    .price( getCellValueAsBigDecimal(row, ptr++ ) )
                    .build();
            mapa.get(category).add(item);
        }

        return mapa;
    }

    /***
     * Metódo auxiliar para a partir de uma linha retornar o valor como Boolean
     * @param row
     * @param col
     * @return
     */
    private Boolean getCellValueAsBoolean(XSSFRow row, Integer col) {

        switch (row.getCell(col).getCellTypeEnum()) {
            case STRING: return row.getCell(col).getStringCellValue().length() > 0;
            case NUMERIC: return row.getCell(col).getNumericCellValue() > 0;
        }
        return false;
    }
    /***
     * Metódo auxiliar para a partir de uma linha retornar o valor como String
     * @param row
     * @param col
     * @return
     */
    private String getCellValueAsString(XSSFRow row, Integer col) {

        switch (row.getCell(col).getCellTypeEnum()) {
            case STRING: return row.getCell(col).getStringCellValue();
            case NUMERIC: return (row.getCell(col).getNumericCellValue()+"").replaceAll("\\.0", "");
        }
        return row.getCell(col).getRawValue();
    }
    /***
     * Metódo auxiliar para a partir de uma linha retornar o valor como BigDecimal
     * @param row
     * @param col
     * @return
     */
    private BigDecimal getCellValueAsBigDecimal(XSSFRow row, Integer col) {

        switch (row.getCell(col).getCellTypeEnum()) {
            case STRING: return new BigDecimal( row.getCell(col).getStringCellValue() );
            case NUMERIC: return new BigDecimal( row.getCell(col).getNumericCellValue() );
        }
        return new BigDecimal( row.getCell(col).getRawValue() );
    }

    /***
     * Obtém ou cria uma planilha baseado no arquivo
     * @param file
     * @return
     */
    private Sheet getOrCreateSheet(FileStore file) {
        return sheetRepository.findOne(
                Example.of(
                        Sheet.builder()
                                .fileStore(file)
                                .build()
                )
        ).orElseGet(() -> {
            return sheetRepository.save(
                    Sheet.builder()
                            .fileStore(file)
                            .state(ProcessState.QUEUED)
                            .success(false)
                            .build()
            );
        });
    }

    /***
     * Consome uma planilha que está na fila
     * @param sheet
     */
    @RabbitListener(queues = "SheetProcessor")
    public void consumeSheetQueue(@Payload SheetDTO sheet) {
        processSheet(sheet.getId());
    }
}
