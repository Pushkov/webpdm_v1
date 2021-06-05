package nicomed.webpdm.service;

import nicomed.webpdm.models.Office;
import nicomed.webpdm.repository.OfficeRepository;
import nicomed.webpdm.utils.GetNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OfficeServiceImpl implements OfficeService {

    private final OfficeRepository repository;

    public OfficeServiceImpl(OfficeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Office getOne(Long id) {
        return repository.getOne(id);
    }

    @Override
    public List<Office> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(Office office) {
        repository.save(office);
    }

    @Override
    public void delete(Office office) {
        repository.delete(office);
    }
}
