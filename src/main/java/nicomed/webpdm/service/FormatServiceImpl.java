package nicomed.webpdm.service;

import nicomed.webpdm.models.DocFormat;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.repository.FormatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormatServiceImpl implements FormatService {

    private final FormatRepository formatRepository;

    public FormatServiceImpl(FormatRepository formatRepository) {
        this.formatRepository = formatRepository;
    }

    @Override
    public DocFormat getOne(Long id) {
        return formatRepository.getOne(id);
    }

    @Override
    public DocFormat findByFormat(String format) {
        return formatRepository.findByFormat(format);
    }

    @Override
    public List<DocFormat> findAll() {
        return formatRepository.findAll();
    }

    @Override
    public List<DocFormat> findAllByDocs(PdmDocument doc) {
        return formatRepository.findAllByDocs(doc);
    }

    @Override
    public void save(DocFormat docFormat) {
        formatRepository.save(docFormat);
    }

    @Override
    public void delete(DocFormat docFormat) {
        formatRepository.delete(docFormat);
    }
}
