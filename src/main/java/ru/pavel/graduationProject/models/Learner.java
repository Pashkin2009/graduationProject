package ru.pavel.graduationProject.models;

import org.springframework.data.annotation.Id;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "learner")
public class Learner {
    @javax.persistence.Id
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    private Group group;

    @OneToMany(mappedBy = "learner")
    private List<Sample> samples;


    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }
    public Learner(Group group, List<Sample> samples, String first_name, String last_name) {
        this.group = group;
        this.samples = samples;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public Learner(){};

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Learner{" +
                "id=" + id +
                ", group=" + group +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                '}';
    }
}
