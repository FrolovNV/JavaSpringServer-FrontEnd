package main.service;

import main.entity.Journal;
import main.entity.Routes;
import main.exception.NotFoundInRepositoryException;
import main.repository.JournalRepository;
import main.repository.RoutesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoutesServiceImpl implements RoutesService{

    @Autowired
    RoutesRepository repository;

    @Autowired
    JournalRepository journalRepository;

    @Override
    public List<Routes> listRoutes() {
        return (List<Routes>)repository.findAll();
    }

    @Override
    public Routes findById(Long id) {
        Optional<Routes> routes = repository.findById(id);
        if (routes.isPresent()) {
            return routes.get();
        } else {
            throw new NotFoundInRepositoryException("Table named <<Routes>> doesn't have element with id = " + id);
        }
    }

    @Override
    public Routes addRoutes(Routes routes) {
//        Long index = DetailsFunctions.findInRepository(listRoutes(), routes);
//        if (index == null) {
//            return repository.save(routes);
//        }
//        List<Routes> list = listRoutes();
        return repository.save(routes);
    }

    @Override
    public Journal addRoutesWithJournal(Routes routes) {
        Routes newRoute = addRoutes(routes);
        Journal journal = new Journal();
        journal.setRoutes(repository.findById(newRoute.getId()).get());
        return journalRepository.save(journal);
    }

    @Override
    public Routes findByName(String name) {
        Optional<Routes> routes = Optional.empty();
        List<Routes> list = listRoutes();
        for (Routes ways : list) {
            if (ways.getName().equals(name)) {
                routes = Optional.of(ways);
            }
        }
        if (routes.isPresent()) {
            return routes.get();
        } else {
            throw new NotFoundInRepositoryException("Table named <<Routes>> doesn't have element with name = " + name);
        }


    }
}
