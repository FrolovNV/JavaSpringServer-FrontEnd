package main.entity;

import org.json.JSONObject;

public class Auto {
    private Long id;
    private String num;
    private String color;
    private String mark;
    private AutoPersonnel autoPersonnel;

    public Auto() {
        this.id = (long)-1;
        this.mark = "";
        this.color = "";
        this.num = "";
        this.autoPersonnel = new AutoPersonnel();
    }

    public Auto(Long id, String num, String color, String mark, AutoPersonnel autoPersonnel) {
        this.id = id;
        this.num = num;
        this.color = color;
        this.mark = mark;
        this.autoPersonnel = autoPersonnel;
    }

    public Auto(String num, String color, String mark) {
        this.num = num;
        this.color = color;
        this.mark = mark;
    }

    public Auto(String num, String color, String mark, AutoPersonnel autoPersonnel) {
        this.num = num;
        this.color = color;
        this.mark = mark;
        this.autoPersonnel = autoPersonnel;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setNum(String num) {
        this.num = num;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public void setAutoPersonnel(AutoPersonnel autoPersonnel) {
        this.autoPersonnel = autoPersonnel;
    }

    public Long getId() {
        return id;
    }

    public String getNum() {
        return num;
    }

    public String getColor() {
        return color;
    }

    public String getMark() {
        return mark;
    }

    public AutoPersonnel getAutoPersonnel() {
        return autoPersonnel;
    }

    public static Auto getAutoFromJSON(JSONObject object) {
        Auto auto = new Auto();
        if (object != null) {
            Long id = object.getLong("id");
            String num = object.getString("num");
            String color = object.getString("color");
            String mark = object.getString("mark");
            auto.setId(id);
            auto.setMark(mark);
            auto.setColor(color);
            auto.setNum(num);
        }
        if (!object.isNull("autoPersonnel")) {
            JSONObject obj = object.getJSONObject("autoPersonnel");
            AutoPersonnel autoPersonnel = AutoPersonnel.getAutoPersonnelFromJSON(obj);
            auto.setAutoPersonnel(autoPersonnel);
        }
        return auto;
    }

    public String getStringToPostRequest() {
        StringBuilder stringBuilder = new StringBuilder("{\"num\": \"" + num + "\"" +
                ", \"color\": \"" + color + "\"" +
                ", \"mark\": \"" + mark + "\"}");
//        if (autoPersonnel != null) {
//            stringBuilder.append("\"autoPersonnel\": { \"id\":" + autoPersonnel.getId()
//                    + ", \"firstName\":" + autoPersonnel.getFirstName() + ", \"lastName\":" + autoPersonnel.getLastName() +
//                    ", \"patherName\":" + autoPersonnel.getPatherName() + "}");
//        }
        return stringBuilder.toString();
    }

    public String getStringToPostSet() {
        StringBuilder stringBuilder = new StringBuilder(
                "{\"id\":" + "\"" + id + "\"" +
                        ", \"num\": \"" + num + "\"" +
                        ", \"color\": \"" + color + "\"" +
                        ", \"mark\": \"" + mark + "\"");
        if (autoPersonnel != null) {
            stringBuilder.append(", \"autoPersonnel\": " +
                    "{ \"id\":" + "\"" + autoPersonnel.getId() + "\"" +
                    ", \"firstName\":" + "\"" + autoPersonnel.getFirstName() + "\"" +
                    ", \"lastName\":" + "\"" + autoPersonnel.getLastName() + "\"" +
                    ", \"patherName\":" + "\"" + autoPersonnel.getPatherName() + "\"" + "}");
        }
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "Auto{" +
                "num='" + num + '\'' +
                ", color='" + color + '\'' +
                ", mark='" + mark + '\'' +
                ", autoPersonnel=" + autoPersonnel.toString() +
                '}';
    }
}
