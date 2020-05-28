package main.service;

import main.entity.AutoPersonnel;
import main.exception.NotFoundInRepositoryException;
import main.repository.AutoPersonnelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AutoPersonnelServiceImpl implements AutoPersonnelService{

    @Autowired
    AutoPersonnelRepository repository;

    @Override
    public List<AutoPersonnel> listAutoPersonnel() {
        return (List<AutoPersonnel>)repository.findAll();
    }

    @Override
    public AutoPersonnel findById(Long id) {
        Optional<AutoPersonnel> optional = repository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw new NotFoundInRepositoryException("Table named <<AutoPersonnel>> doesn't have element with id = " + id);
    }

    @Override
    public AutoPersonnel findByFirstName(String firstName) {
        Optional<AutoPersonnel> optional = Optional.empty();
        List<AutoPersonnel> list = listAutoPersonnel();
        for (AutoPersonnel autoP : list) {
            if (autoP.getFirstName().equals(firstName)) {
                optional = Optional.of(autoP);
            }
        }
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NotFoundInRepositoryException("Table named <<AutoPersonnel>> doesn't have element with firstName = "
                    + firstName);
        }
    }

    @Override
    public AutoPersonnel findByLastName(String lastName) {
        Optional<AutoPersonnel> optional = Optional.empty();
        List<AutoPersonnel> list = listAutoPersonnel();
        for (AutoPersonnel autoP : list) {
            if (autoP.getLastName().equals(lastName)) {
                optional = Optional.of(autoP);
            }
        }
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NotFoundInRepositoryException("Table named <<AutoPersonnel>> doesn't have element with lastName = "
                    + lastName);
        }
    }

    @Override
    public AutoPersonnel findByPatherName(String patherName) {
        Optional<AutoPersonnel> optional = Optional.empty();
        List<AutoPersonnel> list = listAutoPersonnel();
        for (AutoPersonnel autoP : list) {
            if (autoP.getPatherName().equals(patherName)) {
                optional = Optional.of(autoP);
            }
        }
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new NotFoundInRepositoryException("Table named <<AutoPersonnel>> doesn't have element with patherName = "
                    + patherName);
        }
    }

    @Override
    public AutoPersonnel addAutoPersonnel(AutoPersonnel autoPersonnel) {
        Long index = DetailsFunctions.findInRepository(listAutoPersonnel(), autoPersonnel);
        if (index == null) {
            return repository.save(autoPersonnel);
        }
        List<AutoPersonnel> list = listAutoPersonnel();
        return repository.findById(list.get(index.intValue()).getId()).get();
    }
}
