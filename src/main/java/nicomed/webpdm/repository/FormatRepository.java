package nicomed.webpdm.repository;

import nicomed.webpdm.models.DocFormat;
import nicomed.webpdm.models.PdmDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormatRepository extends JpaRepository<DocFormat, Long> {
    DocFormat findByFormat(String format);
    List<DocFormat> findAllByDocs(PdmDocument doc);
}
