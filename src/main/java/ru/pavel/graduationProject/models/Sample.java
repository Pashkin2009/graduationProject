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
    private Double floatingNumberField;
    @Column(name = "number_field")
    private Integer numberField;
    @Column(name = "check_field")
    private Boolean checkField;
    @Column(name = "control_task")
    private boolean controlTask;

    public Sample(){}

    public Sample(TaskName taskName, Learner learner, Double floatingNumberField, Integer numberField, Boolean checkField, boolean controlTask) {
        this.taskName = taskName;
        this.learner = learner;
        this.floatingNumberField = floatingNumberField;
        this.numberField = numberField;
        this.checkField = checkField;
        this.controlTask = controlTask;
    }

    public boolean isCheckField() {
        return checkField;
    }

    public void setCheckField(boolean checkField) {
        this.checkField = checkField;
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

    public Double getFloatingNumberField() {
        return floatingNumberField;
    }

    public void setFloatingNumberField(Double floatingNumberField) {
        this.floatingNumberField = floatingNumberField;
    }

    public Integer getNumberField() {
        return numberField;
    }

    public void setNumberField(Integer numberField) {
        this.numberField = numberField;
    }

    public Boolean getCheckField() {
        return checkField;
    }

    public void setCheckField(Boolean checkField) {
        this.checkField = checkField;
    }

    public boolean isControlTask() {
        return controlTask;
    }

    public void setControlTask(boolean controlTask) {
        this.controlTask = controlTask;
    }

}
