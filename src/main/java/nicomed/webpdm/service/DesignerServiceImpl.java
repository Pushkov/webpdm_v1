package nicomed.webpdm.service;

import nicomed.webpdm.models.Designer;
import nicomed.webpdm.repository.DesignersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DesignerServiceImpl implements DesignerService {

    private final DesignersRepository repository;

    @Autowired
    public DesignerServiceImpl(DesignersRepository repository) {
        this.repository = repository;
    }

//    @Override
//    public Designer getOne(Long id) {
//        return repository.getOne(id);
//    }

    @Override
    public void save(Designer designer) {
        repository.save(designer);
    }

    @Override
    public void delete(Designer designer) {
        repository.delete(designer);
    }

    @Override
    public List<Designer> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Designer> findDesignerByFirstName(String firstName) {
        return repository.findDesignerByFirstName(firstName);
    }

    @Override
    public List<Designer> findDesignerByLastName(String lastName) {
        return repository.findDesignerByLastName(lastName);
    }

    @Override
    public List<Designer> findDesignerByFirstNameAndLastName(String firstName, String lastName) {
        return repository.findDesignerByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public Designer findDesignerByLogin(String login) {
        return repository.findDesignerByLogin(login).orElseThrow(() -> new NoSuchElementException("User with login:'" + login + "' not found"));
    }

    @Override
    public Designer findOneById(Long id) {
        return repository.findOneById(id).orElseThrow(()->new NoSuchElementException("User with id: " + id + " not found"));
    }
}
