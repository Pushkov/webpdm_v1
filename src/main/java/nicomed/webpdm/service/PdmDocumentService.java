package nicomed.webpdm.service;

import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.models.PlanPoint;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface PdmDocumentService {
    void save(PdmDocument document);
    PdmDocument getByDes(String des);
    PdmDocument getOne(Long id);
    List<PdmDocument> findAll();
    void delete(PdmDocument document);
    List<PdmDocument> findAllByOwner(Designer designer);
    List<PdmDocument> findAllByWorkshop(Workshop workshop);
    List<PdmDocument> findAllByPlanpoint(PlanPoint planPoint);


    List<PdmDocument> findAll(String prop);
}
