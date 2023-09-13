package ru.pavel.graduationProject.models;

import org.springframework.data.annotation.Id;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "groups")
public class Group {
    @javax.persistence.Id
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "group")
    private List<Learner> learners;
    public Group(){};

    public Group(String group_name) {
        this.groupName = group_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String group_name) {
        this.groupName = group_name;
    }

    public List<Learner> getLearners() {
        return learners;
    }

    public void setLearners(List<Learner> learners) {
        this.learners = learners;
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", group_name='" + groupName + '\'' +
                '}';
    }
}
