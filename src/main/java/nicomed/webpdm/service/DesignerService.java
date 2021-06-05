package nicomed.webpdm.service;

import nicomed.webpdm.models.Designer;

import java.util.List;
import java.util.Optional;

public interface DesignerService {
//    Designer getOne(Long id);
    void save(Designer designer);
    void delete(Designer designer);
    List<Designer> findAll();
    List<Designer> findDesignerByFirstName(String firstName);
    List<Designer> findDesignerByLastName(String lastName);
    List<Designer> findDesignerByFirstNameAndLastName(String firstName, String lastName);
    Designer findDesignerByLogin(String login);

    Designer findOneById(Long id);
}
