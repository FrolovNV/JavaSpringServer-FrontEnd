package main;

import main.entity.*;
import main.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class TestDataInit implements CommandLineRunner {
    @Autowired
    AutoRepository autoRepository;

    @Autowired
    AutoPersonnelRepository autoPersonnelRepository;

    @Autowired
    JournalRepository journalRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoutesRepository routesRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Routes routes = new Routes("Moscow");
        AutoPersonnel autoPersonnel = new AutoPersonnel("Nikita", "Frolov", "Vladimirovich");
        Auto auto = new Auto("Number1", "Black", "Audi", autoPersonnel);
        Auto auto2 = new Auto("Number2", "Green", "Audi2", autoPersonnel);

        String dateIn = new Date().toString().replaceAll(" ", "_");
        String dateOut = (new Date(18000)).toString().replaceAll(" ", "_");
        Journal journal = new Journal(dateOut , dateIn, auto, routes);

        autoPersonnelRepository.save(autoPersonnel);
        routesRepository.save(routes);
        autoRepository.save(auto);
        journalRepository.save(journal);
        autoRepository.save(auto2);
        userRepository.save(new User("user", passwordEncoder.encode("pwd"), Collections.singletonList("ROLE_USER")));
        userRepository.save(new User("admin", passwordEncoder.encode("apwd"), Collections.singletonList("ROLE_ADMIN")));
    }
}
