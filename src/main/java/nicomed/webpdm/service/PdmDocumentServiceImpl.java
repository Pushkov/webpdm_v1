package nicomed.webpdm.service;

import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.models.PlanPoint;
import nicomed.webpdm.repository.PdmDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PdmDocumentServiceImpl implements PdmDocumentService {

    @Autowired
    private PdmDocumentRepository pdmDocumentRepository;

    @Override
    public void save(PdmDocument document) {
        pdmDocumentRepository.save(document);
    }

    @Override
    public PdmDocument getByDes(String des) {
        return pdmDocumentRepository.getByDes(des);
    }

    @Override
    public PdmDocument getOne(Long id) {
//        if(pdmDocumentRepository.getOne(id).isPresent())
//        return pdmDocumentRepository.findById(id);
//        else
            return pdmDocumentRepository.getOne(id);
    }

    @Override
    public List<PdmDocument> findAllByOwner(Designer designer) {
        return pdmDocumentRepository.findAllByOwner(designer);
    }

    @Override
    public List<PdmDocument> findAllByWorkshop(Workshop workshop) {
        return pdmDocumentRepository.findAllByWorkshop(workshop);
    }

    @Override
    public List<PdmDocument> findAll() {
        return pdmDocumentRepository.findAll();
    }
    @Override
    public void delete(PdmDocument document) {
        pdmDocumentRepository.delete(document);
    }

    @Override
    public List<PdmDocument> findAllByPlanpoint(PlanPoint planPoint) {
        return pdmDocumentRepository.findAllByPlanpoint(planPoint);
    }


    @Override
    public List<PdmDocument> findAll(String prop) {
        return pdmDocumentRepository.findAll(Sort.by(prop));
    }
}
