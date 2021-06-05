package nicomed.webpdm.service;

import nicomed.webpdm.models.DocFormat;
import nicomed.webpdm.models.PdmDocument;

import java.util.List;

public interface FormatService {
    DocFormat getOne(Long id);
    DocFormat findByFormat(String format);
    List<DocFormat> findAll();
    List<DocFormat> findAllByDocs(PdmDocument doc);
    void save (DocFormat docFormat);
    void delete(DocFormat docFormat);
}
