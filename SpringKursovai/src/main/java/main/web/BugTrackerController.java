package main.web;

import main.entity.Auto;
import main.entity.AutoPersonnel;
import main.entity.Journal;
import main.entity.Routes;
import main.exception.NotFoundInRepositoryException;
import main.service.AutoPersonnelService;
import main.service.AutoService;
import main.service.JournalService;
import main.service.RoutesService;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/bat")
public class BugTrackerController {
    private JournalService journalService;
    private AutoService autoService;
    private RoutesService routesService;
    private AutoPersonnelService autoPersonnelService;


    //POSTMAPPING FUNCTIONS
    @PostMapping(value = "/addRoute", consumes = "application/json", produces = "application/json")
    public Routes addRoute(@RequestBody Routes newRoute) {
        return routesService.addRoutes(newRoute);
    }

    @PostMapping(value = "/addRouteWithJournal", consumes = "application/json", produces = "application/json")
    public Journal addRoutesWithJournal(@RequestBody Routes newRoute) {
        return routesService.addRoutesWithJournal(newRoute);
    }

    @PostMapping(value = "/addAuto", consumes = "application/json", produces = "application/json")
    public Auto addAuto(@RequestBody Auto newAuto) {
        return autoService.addAuto(newAuto);
    }

    @PostMapping(value = "/setAutoPersonnelInAuto/{id}", consumes = "application/json", produces = "application/json")
    public Auto setAutoPersonnelInAuto(@PathVariable Long id, @RequestBody AutoPersonnel personnel) {
        try {
            return autoService.setAutoPersonnel(id, personnel);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @PostMapping(value = "/setAutoWithPersonnel/{id}", consumes = "application/json", produces = "application/json")
    public Journal setAutoWithPersonnel(@PathVariable Long id, @RequestBody Auto auto) {
        try {
            return journalService.setAutoWithPersonnel(id, auto);
        } catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @PostMapping(value = "/addAutoPersonnel", consumes = "application/json", produces = "application/json")
    public AutoPersonnel addAutoPersonnel(@RequestBody AutoPersonnel newPersonnel) {
        return autoPersonnelService.addAutoPersonnel(newPersonnel);
    }

    @PostMapping(value = "/addJournal", consumes = "application/json", produces = "application/json")
    public Journal addJournal(@RequestBody Journal journal) {
        try {
            return journalService.addJournal(journal);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @PostMapping(value = "/setTimeOutInJournal/{id}", consumes = "application/json", produces = "application/json")
    public Journal addTimeOut(@PathVariable Long id, @RequestBody String timeOut) {
        try {
            return journalService.setTimeOut(id, timeOut);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @PostMapping(value = "/setTimeInInJournal/{id}", consumes = "application/json", produces = "application/json")
    public Journal addTimeIn(@PathVariable Long id, @RequestBody String timeIn) {
        try {
            return journalService.setTimeIn(id, timeIn);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @PostMapping(value = "/setRouteInJournal/{id}", consumes = "application/json", produces = "application/json")
    public Journal setRouteJournal(@PathVariable Long id, @RequestBody Routes routes) {
        try {
            return journalService.setRoute(id, routes);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @PostMapping(value = "/setAutoInJournal/{id}", consumes = "application/json", produces = "application/json")
    public Journal setAutoInJournal(@PathVariable Long id, @RequestBody Auto auto) {
        try {
            return journalService.setAuto(id, auto);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }
    //END POSTMAPPING FUNCTIONS

    //DELETEMAPPING
    @DeleteMapping(value = "/deleteJournal/{id}")
    public void deleteJournal(@PathVariable Long id) {
        try {
            journalService.deleteFromRepository(id);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }
    //END DELETEMAPPING

    //ROUTES
    @GetMapping("/routes")
    public ResponseEntity<List<Routes>> getRoutes() {
        List<Routes> list = routesService.listRoutes();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/route/byId/{id}")
    public ResponseEntity<Routes> getRouteById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(routesService.findById(id), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found");
        }
    }

    @GetMapping("/route/byName/{name}")
    public ResponseEntity<Routes> getRouteByName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(routesService.findByName(name), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Route not found");
        }
    }
    //END ROUTES

    //AUTO
    @GetMapping("/autos")
    public ResponseEntity<List<Auto>> getAutos() {
        List<Auto> list = autoService.listAuto();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/auto/byId/{id}")
    public ResponseEntity<Auto> getAutoById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(autoService.findById(id), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auto not found");
        }
    }

    @GetMapping("/auto/byNum/{num}")
    public ResponseEntity<Auto> getAutoByNum(@PathVariable String num) {
        try {
            return new ResponseEntity<>(autoService.findByNumber(num), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Auto not found");
        }
    }

    @GetMapping("/auto/filterByMark/{mark}")
    public ResponseEntity<List<Auto>> getFilteredListByMark(@PathVariable String mark) {
        try {
            return new ResponseEntity<>(autoService.filterByMark(mark), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @GetMapping("/auto/filterByColor/{color}")
    public ResponseEntity<List<Auto>> getFilteredListByColor(@PathVariable String color) {
        try {
            return new ResponseEntity<>(autoService.filterByColor(color), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }
    //END AUTO

    //AUTO_PERSONNEL
    @GetMapping("/autoPersonnels")
    public ResponseEntity<List<AutoPersonnel>> getAutoPersonnels() {
        return new ResponseEntity<>(autoPersonnelService.listAutoPersonnel(), HttpStatus.OK);
    }

    @GetMapping("/autoPersonnel/byId/{id}")
    public ResponseEntity<AutoPersonnel> getAutoPersonnelById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(autoPersonnelService.findById(id), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @GetMapping("/autoPersonnel/byFirstName/{name}")
    public ResponseEntity<AutoPersonnel> getAutoPersonnelByFirstName(@PathVariable String name){
        try {
            return new ResponseEntity<>(autoPersonnelService.findByFirstName(name), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @GetMapping("/autoPersonnel/byLastName/{name}")
    public ResponseEntity<AutoPersonnel> getAutoPersonnelByLastName(@PathVariable String name){
        try {
            return new ResponseEntity<>(autoPersonnelService.findByLastName(name), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @GetMapping("/autoPersonnel/byPatherName/{name}")
    public ResponseEntity<AutoPersonnel> getAutoPersonnelByPatherName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(autoPersonnelService.findByPatherName(name), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }
    //END AUTO_PERSONNEL

    //JOURNAL
    @GetMapping("/journals")
    public ResponseEntity<List<Journal>> getJournals() {
        return new ResponseEntity<>(journalService.listJournal(), HttpStatus.OK);
    }

    @GetMapping("/journal/byId/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(journalService.findById(id), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @GetMapping("/journal/byTimeOut/{timeOut}")
    public ResponseEntity<Journal> getJournalByTimeOut(@PathVariable String timeOut) {
        try {
            return new ResponseEntity<>(journalService.findByTimeOut(timeOut), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    @GetMapping("/journal/byTimeIn/{timeIn}")
    public ResponseEntity<Journal> getJournalByTimeIn(@PathVariable String timeIn) {
        try {
            return new ResponseEntity<>(journalService.findByTimeIn(timeIn), HttpStatus.OK);
        }
        catch (NotFoundInRepositoryException exp) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exp.getMessage());
        }
    }

    //END JOURNALS

    @Autowired
    public void setJournalService(JournalService journalService) {
        this.journalService = journalService;
    }

    @Autowired
    public void setAutoService(AutoService autoService) {
        this.autoService = autoService;
    }

    @Autowired
    public void setRoutesService(RoutesService routesService) {
        this.routesService = routesService;
    }

    @Autowired
    public void setAutoPersonnelService(AutoPersonnelService autoPersonnelService) {
        this.autoPersonnelService = autoPersonnelService;
    }
}
