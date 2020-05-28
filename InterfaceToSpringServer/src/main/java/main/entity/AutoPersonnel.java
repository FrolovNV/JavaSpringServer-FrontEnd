package main.entity;

import org.json.JSONObject;

public class AutoPersonnel {
    private Long id;
    private String firstName;
    private String lastName;
    private String patherName;

    public AutoPersonnel() {
        this.id = (long) -1;
        this.firstName = "";
        this.lastName = "";
        this.patherName = "";
    }

    public AutoPersonnel(Long id, String firstName, String lastName, String patherName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patherName = patherName;
    }

    public AutoPersonnel(String firstName, String lastName, String patherName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patherName = patherName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPatherName() {
        return patherName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPatherName(String patherName) {
        this.patherName = patherName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static AutoPersonnel getAutoPersonnelFromJSON(JSONObject object) {
        AutoPersonnel autoPersonnel = new AutoPersonnel();
        if (object != null) {
            Long idp = object.getLong("id");
            String firstName = object.getString("firstName");
            String lastName = object.getString("lastName");
            String patherName = object.getString("patherName");
            autoPersonnel.setId(idp);
            autoPersonnel.setFirstName(firstName);
            autoPersonnel.setLastName(lastName);
            autoPersonnel.setPatherName(patherName);
        }
        return autoPersonnel;
    }

    public String getStringToPostRequest() {
        return "{\"firstName\": \"" + firstName + "\"" +
                ", \"lastName\": \"" + lastName + "\"" +
                ", \"patherName\": \"" + patherName + "\"}";
    }

    @Override
    public String toString() {
        return "AutoPersonnel{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patherName='" + patherName + '\'' +
                '}';
    }
}
