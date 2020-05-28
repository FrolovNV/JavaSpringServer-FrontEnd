package main.service;

import main.entity.Auto;
import main.entity.AutoPersonnel;

import java.util.List;

public interface AutoService {
    List<Auto> listAuto();
    Auto findById(Long id);
    Auto findByNumber(String num);
    List<Auto> filterByMark(String mark);
    List<Auto> filterByColor(String color);
    Auto addAuto(Auto auto);
    Auto setAutoPersonnel(Long id, AutoPersonnel personnel);
}
