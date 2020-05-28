package main.entity;

import javax.persistence.*;

@Entity
public class Auto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "Num", nullable = true, unique = true)
    private String num;

    @Column(name = "Color", nullable = true)
    private String color;

    @Column(name = "Mark", nullable = true)
    private String mark;

    @ManyToOne(/*cascade = CascadeType.ALL*/)
    @JoinColumn(name = "auto_personnel_id")
    private AutoPersonnel autoPersonnel;

    public Auto() {
    }

    public Auto(String num, String color, String mark, AutoPersonnel autoPersonnel) {
        this.num = num;
        this.color = color;
        this.mark = mark;
        this.autoPersonnel = autoPersonnel;
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

    @Override
    public String toString() {
        return "Auto{" +
                "id=" + id +
                ", num='" + num + '\'' +
                ", color='" + color + '\'' +
                ", mark='" + mark + '\'' +
                ", autoPersonnel=" + autoPersonnel.toString() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        Auto auto = (Auto)obj;
        if (this == obj) {
            return true;
        }
        if ((this.num.equals(auto.num)) && (this.color.equals(auto.color)) && (this.mark.equals(auto.mark))) {
            return true;
        }
        return false;
    }
}
