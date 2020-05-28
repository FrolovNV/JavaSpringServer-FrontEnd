package main.entity;

import ch.qos.logback.core.pattern.SpacePadder;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Journal {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TIME_OUT")
    private String timeOut;

    @Column(name = "TIME_IN")
    private String timeIn;

    @ManyToOne(/*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "Auto_id")
    private Auto auto;

    @ManyToOne(/*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "routes_id")
    private Routes routes;

    public Journal() {
    }

    public Journal(String timeIn) {
        this.timeIn = timeIn;
    }

    public Journal(String  timeOut, String timeIn, Auto auto, Routes routes) {
        this.timeOut = timeOut;
        this.timeIn = timeIn;
        this.auto = auto;
        this.routes = routes;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public void setAuto(Auto auto) {
        this.auto = auto;
    }

    public void setRoutes(Routes routes) {
        this.routes = routes;
    }

    public Long getId() {
        return id;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public Auto getAuto() {
        return auto;
    }

    public Routes getRoutes() {
        return routes;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id=" + id +
                ", timeOut=" + timeOut +
                ", timeIn=" + timeIn +
                "} " + auto.toString() + " " + routes.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Journal auto = (Journal) obj;
        if (this == obj) {
            return true;
        }
        if ((this.timeIn.equals(auto.timeIn)) && (this.timeOut.equals(auto.timeOut))) {
            return true;
        }
        return false;
    }
}
