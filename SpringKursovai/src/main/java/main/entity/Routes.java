package main.entity;

import javax.persistence.*;

@Entity
public class Routes {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    public Routes() {

    }

    public Routes(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Routes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Routes auto = (Routes) obj;
        if (this == obj) {
            return true;
        }
        if (this.name.equals(auto.name)) {
            return true;
        }
        return false;
    }
}
