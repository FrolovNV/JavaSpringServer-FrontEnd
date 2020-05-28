package main.service;

import main.entity.Auto;
import main.entity.Journal;
import main.entity.Routes;
import main.exception.NotFoundInRepositoryException;
import main.repository.AutoRepository;
import main.repository.JournalRepository;
import main.repository.RoutesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JournalServiceImpl implements JournalService{

    @Autowired
    JournalRepository repository;

    @Autowired
    AutoRepository autoRepository;

    @Autowired
    RoutesRepository routesRepository;

    @Override
    public List<Journal> listJournal() {
        return (List<Journal>)repository.findAll();
    }

    @Override
    public Journal findById(Long id) {
        Optional<Journal> journal = repository.findById(id);
        if (journal.isPresent()) {
            return journal.get();
        } else {
            throw new NotFoundInRepositoryException("Table named <<Journal>> doesn't have element with id = " + id);
        }
    }

    @Override
    public Journal findByTimeOut(String timeOut) {
        Optional<Journal> journal = Optional.empty();
        List<Journal> list = listJournal();
        for (Journal elem : list) {
            if (elem.getTimeOut().equals(timeOut)) {
                journal = Optional.of(elem);
            }
        }
        if (journal.isPresent()) {
            return journal.get();
        } else {
            throw new NotFoundInRepositoryException("Table named <<Journal>> doesn't have element with TimeOut = "
                    + timeOut);
        }
    }

    @Override
    public Journal findByTimeIn(String timeIn) {
        Optional<Journal> journal = Optional.empty();
        List<Journal> list = listJournal();
        for (Journal elem : list) {
            if (elem.getTimeIn().equals(timeIn)) {
                journal = Optional.of(elem);
            }
        }
        if (journal.isPresent()) {
            return journal.get();
        } else {
            throw new NotFoundInRepositoryException("Table named <<Journal>> doesn't have element with TimeIn = "
                    + timeIn);
        }
    }

    @Override
    public Journal addJournal(Journal journal) {
        Long index = DetailsFunctions.findInRepository(listJournal(), journal);
        if (index == null) {
            return repository.save(journal);
        }
        List<Journal> list = listJournal();
        return repository.findById(list.get(index.intValue()).getId()).get();
    }

    @Override
    public Journal setTimeOut(Long id, String timeOut) {
        Optional<Journal> optional = repository.findById(id);
        if (optional.isPresent()) {
            optional.get().setTimeOut(timeOut);
            return repository.save(optional.get());
        } else {
            throw new NotFoundInRepositoryException("Didn't find id = " + id);
        }
    }

    @Override
    public Journal setTimeIn(Long id, String timeIn) {
        Optional<Journal> optional = repository.findById(id);
        if (optional.isPresent()) {
            optional.get().setTimeIn(timeIn);
            return repository.save(optional.get());
        } else {
            throw new NotFoundInRepositoryException("Didn't find id = " + id);
        }
    }

    @Override
    public Journal setRoute(Long id, Routes routes) {
        Long index = DetailsFunctions.findInRepository((List<Routes>)routesRepository.findAll(), routes);
        if (index == null) {
            routesRepository.save(routes);
        }
        Long i = DetailsFunctions.findInRepository((List<Routes>)routesRepository.findAll(), routes);
        List<Routes> list = (List<Routes>)routesRepository.findAll();
        index = list.get(i.intValue()).getId();

        Optional<Journal> optional = repository.findById(id);
        if (optional.isPresent()) {
            optional.get().setRoutes(routesRepository.findById(index).get());
            return repository.save(optional.get());
        } else {
            throw new NotFoundInRepositoryException("Didn't find id = " + id);
        }
    }

    @Override
    public Journal setAuto(Long id, Auto auto) {
        Long index = DetailsFunctions.findInRepository((List<Auto>)autoRepository.findAll(), auto);
        if (index == null) {
            autoRepository.save(auto);
        }
        Long i = DetailsFunctions.findInRepository((List<Auto>)autoRepository.findAll(), auto);
        List<Auto> list = (List<Auto>)autoRepository.findAll();
        index = list.get(i.intValue()).getId();
        Optional<Journal> optional = repository.findById(id);
        if (optional.isPresent()) {
            optional.get().setAuto(autoRepository.findById(index).get());
            return repository.save(optional.get());
        } else {
            throw new NotFoundInRepositoryException("Didn't find id = " + id);
        }
    }

    @Override
    public Journal setAutoWithPersonnel(Long id, Auto auto) {
        Optional<Journal> journal = repository.findById(id);
        if (journal.isPresent()) {
            journal.get().setAuto(auto);
            return repository.save(journal.get());
        }
        throw new NotFoundInRepositoryException("Didn't find id = " + id);
    }

    @Override
    public void deleteFromRepository(Long id) {
        Optional<Journal> optional = repository.findById(id);
        if (optional.isPresent()) {
            repository.delete(optional.get());
        } else {
            throw new NotFoundInRepositoryException("Didn't find id = " + id);
        }
    }
}
