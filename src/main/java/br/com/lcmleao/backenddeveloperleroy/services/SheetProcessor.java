package br.com.lcmleao.backenddeveloperleroy.services;

public interface SheetProcessor {
    void queueFile(Long id);
    void processSheet(Long sheetId);
}
