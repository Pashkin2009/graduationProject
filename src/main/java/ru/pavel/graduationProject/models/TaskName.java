package ru.pavel.graduationProject.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "task_name")
public class TaskName {
    @javax.persistence.Id
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "sample_name")
    private String sampleName;
    @OneToMany(mappedBy = "taskName")
    private List<Sample> samples;

    public TaskName(){}

    public TaskName(String sampleName, List<Sample> samples) {
        this.sampleName = sampleName;
        this.samples = samples;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        this.samples = samples;
    }

    @Override
    public String toString() {
        return "TaskName{" +
                "id=" + id +
                ", sampleName='" + sampleName + '\'' +
                ", samples=" + samples +
                '}';
    }
}
