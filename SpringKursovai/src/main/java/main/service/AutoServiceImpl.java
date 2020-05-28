package main.service;

import main.entity.Auto;
import main.entity.AutoPersonnel;
import main.exception.NotFoundInRepositoryException;
import main.repository.AutoPersonnelRepository;
import main.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AutoServiceImpl implements AutoService{

    @Autowired
    private AutoRepository repository;

    @Autowired
    private AutoPersonnelRepository repositoryAutoPersonnel;

    @Override
    public List<Auto> listAuto() {
        return (List<Auto>)repository.findAll();
    }

    @Override
    public Auto findById(Long id) {
        Optional<Auto> opAuto = repository.findById(id);
        if (opAuto.isPresent()) {
            return opAuto.get();
        }
        throw new NotFoundInRepositoryException("Table named <<AUTO>> doesn't have element with id = " + id);
    }

    @Override
    public Auto addAuto(Auto auto) {
        Long index = DetailsFunctions.findInRepository((List<Auto>)repository.findAll(), auto);
        if (index == null) {
            return repository.save(auto);
        }
        List<Auto> list = listAuto();
        return repository.findById(list.get(index.intValue()).getId()).get();
    }

    @Override
    public Auto findByNumber(String num) {
        List<Auto> list = listAuto();

        for (Auto auto : list) {
            if (auto.getNum().equals(num)) {
                return auto;
            }
        }
        throw new NotFoundInRepositoryException("Table named <<AUTO>> doesn't have element with num = " + num);
    }

    @Override
    public List<Auto> filterByMark(String mark) {
        List<Auto> list = listAuto();
        List<Auto> outPutList = new ArrayList<>();
        for (Auto auto : list) {
            if (auto.getMark().equals(mark)){
                outPutList.add(auto);
            }
        }
        return outPutList;
    }

    @Override
    public List<Auto> filterByColor(String color) {
        List<Auto> list = listAuto();
        List<Auto> outPutList = new ArrayList<>();
        for (Auto auto : list) {
            if (auto.getColor().equals(color)){
                outPutList.add(auto);
            }
        }
        return outPutList;
    }

    @Override
    public Auto setAutoPersonnel(Long id, AutoPersonnel personnel) {
        Long index = DetailsFunctions.findInRepository((List<AutoPersonnel>)repositoryAutoPersonnel.findAll(), personnel);
        if (index == null) {
            repositoryAutoPersonnel.save(personnel);
        }
        Long i = DetailsFunctions.findInRepository((List<AutoPersonnel>)repositoryAutoPersonnel.findAll(), personnel);
        List<AutoPersonnel> list = (List<AutoPersonnel>)repositoryAutoPersonnel.findAll();
        index = list.get(i.intValue()).getId();
        Optional<Auto> optional = repository.findById(id);
        if (optional.isPresent()) {
            optional.get().setAutoPersonnel(repositoryAutoPersonnel.findById(index).get());
            return repository.save(optional.get());
        } else {
            throw new NotFoundInRepositoryException("Didn't find id = " + id);
        }
    }
}
