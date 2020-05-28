package main.service;

import main.entity.AutoPersonnel;
import java.util.List;

public interface AutoPersonnelService {
    List<AutoPersonnel> listAutoPersonnel();
    AutoPersonnel findById(Long id);
    AutoPersonnel findByFirstName(String firstName);
    AutoPersonnel findByLastName(String lastName);
    AutoPersonnel findByPatherName(String patherName);
    AutoPersonnel addAutoPersonnel(AutoPersonnel autoPersonnel);
}
