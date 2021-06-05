package nicomed.webpdm.repository;

import nicomed.webpdm.enums.Workshop;
import nicomed.webpdm.models.Designer;
import nicomed.webpdm.models.PdmDocument;
import nicomed.webpdm.models.PlanPoint;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PdmDocumentRepository extends JpaRepository<PdmDocument, Long> {
    PdmDocument getByDes(String des);
    List<PdmDocument> findAllByOwner(Designer designer);
    List<PdmDocument> findAllByWorkshop(Workshop workshop);
    List<PdmDocument> findAllByPlanpoint(PlanPoint planPoint);

    List<PdmDocument> findAll(Sort sort);
}
