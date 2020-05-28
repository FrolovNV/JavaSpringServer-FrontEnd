package main.entity;

import org.json.JSONObject;

public class Journal {
    private Long id;
    private String timeOut;
    private String timeIn;
    private Auto auto_id;
    private Routes routes_id;

    public Journal() {
        this.timeOut = "Car in AUTOPARK";
        this.timeIn = "Car in AUTOPARK";
        this.auto_id = new Auto();
        this.routes_id  = new Routes();
    }

    public Journal(String timeOut) {
        this.timeOut = timeOut;
    }

    public Journal(String timeOut, String timeIn) {
        this.timeOut = timeOut;
        this.timeIn = timeIn;
    }

    public Journal(String timeOut, String timeIn, Auto auto_id, Routes routes_id) {
        this.timeOut = timeOut;
        this.timeIn = timeIn;
        this.auto_id = auto_id;
        this.routes_id = routes_id;
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

    public Auto getAuto_id() {
        return auto_id;
    }

    public Routes getRoutes_id() {
        return routes_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public void setAuto_id(Auto auto_id) {
        this.auto_id = auto_id;
    }

    public void setRoutes_id(Routes routes_id) {
        this.routes_id = routes_id;
    }

    public static Journal getJournalsFromJSON(JSONObject object) {
        Journal journal = new Journal();
        if (object != null) {
            if (object.isNull("timeOut")) {
                journal.setTimeOut("Car_In_Park");
            } else {
                journal.setTimeOut(object.getString("timeOut"));
            }

            if (object.isNull("timeIn") && object.isNull("timeOut")) {
                journal.setTimeIn("Car_In_Park");
            } else if (object.isNull("timeIn")) {
                journal.setTimeIn("Car_Didn't_Return_Yet");
            } else {
                journal.setTimeIn(object.getString("timeIn"));
            }
            if (!object.isNull("id")) {
                journal.setId(object.getLong("id"));
            }
            if (!object.isNull("auto")) {
                journal.setAuto_id(Auto.getAutoFromJSON(object.getJSONObject("auto")));
            }
            if (!object.isNull("routes")) {
                journal.setRoutes_id(Routes.getRoutesFromJSON(object.getJSONObject("routes")));
            }
        }
        return journal;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "timeOut='" + timeOut + '\'' +
                ", timeIn='" + timeIn + '\'' +
                ", auto_id=" + auto_id.toString() +
                ", routes_id=" + routes_id.toString() +
                '}';
    }
}
