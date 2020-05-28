package main.entity;

import javax.persistence.*;

@Entity
public class AutoPersonnel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "FirstName", nullable = true)
    private String firstName;

    @Column(name = "LastName", nullable = true)
    private String lastName;

    @Column(name = "PatherName", nullable = true)
    private String patherName;

    public AutoPersonnel() {
    }

    public AutoPersonnel(String firstName, String lastName, String patherName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.patherName = patherName;
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

    @Override
    public String toString() {
        return "AutoPersonnel{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", patherName='" + patherName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        AutoPersonnel auto = (AutoPersonnel)obj;
        if (this == obj) {
            return true;
        }
        if ((this.firstName.equals(auto.firstName)) && (this.patherName.equals(auto.patherName))
                && (this.lastName.equals(auto.lastName))) {
            return true;
        }
        return false;
    }
}
