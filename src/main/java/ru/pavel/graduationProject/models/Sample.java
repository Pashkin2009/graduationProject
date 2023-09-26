package ru.pavel.graduationProject.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "sample")
public class Sample {
    @javax.persistence.Id
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "task_name_id", referencedColumnName = "id")
    private TaskName taskName;

    @ManyToOne
    @JoinColumn(name = "learner_id",referencedColumnName = "id")
    private Learner learner;
    @Column(name = "floating_number_field")
    private float floatingNumberField;
    @Column(name = "number_field")
    private int numberField;
    @Column(name = "check_field")
    private boolean checkField;
    @Column(name = "control_task")
    private boolean controlTask;

    public Sample(){}

    public Sample(TaskName taskName, Learner learner, float floatingNumberField, int numberField, boolean checkField, boolean controlTask) {
        this.taskName = taskName;
        this.learner = learner;
        this.floatingNumberField = floatingNumberField;
        this.numberField = numberField;
        this.checkField = checkField;
        this.controlTask = controlTask;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TaskName getTaskName() {
        return taskName;
    }

    public void setTaskName(TaskName taskName) {
        this.taskName = taskName;
    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public float getFloatingNumberField() {
        return floatingNumberField;
    }

    public void setFloatingNumberField(float floatingNumberField) {
        this.floatingNumberField = floatingNumberField;
    }

    public int getNumberField() {
        return numberField;
    }

    public void setNumberField(int numberField) {
        this.numberField = numberField;
    }

    public boolean isCheckField() {
        return checkField;
    }

    public void setCheckField(boolean checkField) {
        this.checkField = checkField;
    }

    public boolean isControlTask() {
        return controlTask;
    }

    public void setControlTask(boolean controlTask) {
        this.controlTask = controlTask;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", taskName=" + taskName +
                ", learner=" + learner +
                ", floatingNumberField=" + floatingNumberField +
                ", numberField=" + numberField +
                ", checkField=" + checkField +
                ", controlTask=" + controlTask +
                '}';
    }
}
