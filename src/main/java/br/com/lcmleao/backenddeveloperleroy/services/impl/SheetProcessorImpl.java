package br.com.lcmleao.backenddeveloperleroy.services.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    public interface Hook {
        public Pair<Integer, Integer> cell();
    }

    /***
     * Enfileira o arquivo para ser processado
     * @param id Do arquivo que deve ser enfileirado
     */
    @Transactional
    @Override
    public void queueFile(Long id) {
        FileStore file = fileStoreRepository.getOne(id);
        Sheet sheet = getOrCreateSheet(file);
    }

    /***
     * Processa uma planilha baseado no id
     * @param sheetId
     */
    @Override
    public void processSheet(Long sheetId) {
        Sheet sheet = sheetRepository.findById(sheetId).orElseThrow(() -> new SheetProcessException("Planilha não localizada"));
        Category cat;
        if ( sheet.getState() != ProcessState.QUEUED ) {
            throw new SheetProcessException("A planilha não pode ser processada. STATE != ProcessState.QUEUED");
        }
        try {
            sheet.setState(ProcessState.PROCESSING);
            sheet = sheetRepository.save(sheet);

            Map<Category, List<Item>> ret = transform(sheet.getFileStore().getResource().openStream());

            sheet.setSuccess(true);
            sheet.setState(ProcessState.DONE);
            sheet = sheetRepository.save(sheet);

            sheetRepository.save(sheet);
            ret.forEach( (key, vals) -> {
                Category newCat = categoryRepository.save(key);
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

    public Map<Category, List<Item>> transform(InputStream in ) {
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

    private Boolean getCellValueAsBoolean(XSSFRow row, Integer col) {

        switch (row.getCell(col).getCellTypeEnum()) {
            case STRING: return row.getCell(col).getStringCellValue().length() > 0;
            case NUMERIC: return row.getCell(col).getNumericCellValue() > 0;
        }
        return false;
    }

    private String getCellValueAsString(XSSFRow row, Integer col) {

        switch (row.getCell(col).getCellTypeEnum()) {
            case STRING: return row.getCell(col).getStringCellValue();
            case NUMERIC: return (row.getCell(col).getNumericCellValue()+"").replaceAll("\\.0", "");
        }
        return row.getCell(col).getRawValue();
    }

    private BigDecimal getCellValueAsBigDecimal(XSSFRow row, Integer col) {

        switch (row.getCell(col).getCellTypeEnum()) {
            case STRING: return new BigDecimal( row.getCell(col).getStringCellValue() );
            case NUMERIC: return new BigDecimal( row.getCell(col).getNumericCellValue() );
        }
        return new BigDecimal( row.getCell(col).getRawValue() );
    }

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
}
