package main.service;

import main.entity.Auto;
import main.entity.Journal;
import main.entity.Routes;

import java.util.List;

public interface JournalService {
    List<Journal> listJournal();
    Journal findById(Long id);
    Journal findByTimeOut(String timeOut);
    Journal findByTimeIn(String timeIn);
    Journal addJournal(Journal journal);
    Journal setTimeOut(Long id, String timeOut);
    Journal setTimeIn(Long id, String timeIn);
    Journal setRoute(Long id, Routes routes);
    Journal setAuto(Long id, Auto auto);
    Journal setAutoWithPersonnel(Long id, Auto auto);
    void deleteFromRepository(Long id);
}
