package main.service;

import main.entity.Journal;
import main.entity.Routes;
import java.util.List;

public interface RoutesService {
    List<Routes> listRoutes();
    Routes findById(Long id);
    Routes findByName(String name);
    Routes addRoutes(Routes routes);
    Journal addRoutesWithJournal(Routes routes);
}
