package nicomed.webpdm.service;

import nicomed.webpdm.models.Office;

import java.util.List;

public interface OfficeService  {
    Office getOne(Long id);
    List<Office> findAll();
    void save (Office office);
    void delete (Office office);

}
