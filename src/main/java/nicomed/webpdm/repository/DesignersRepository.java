package nicomed.webpdm.repository;

import nicomed.webpdm.models.Designer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DesignersRepository extends JpaRepository<Designer,Long> {
    List<Designer> findDesignerByFirstName(String firstName);
    List<Designer> findDesignerByLastName(String lastName);
    List<Designer> findDesignerByFirstNameAndLastName(String firstName, String lastName);
    Optional<Designer> findDesignerByLogin(String login);

    Optional<Designer> findOneById(Long id);
}
