package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RealFormulas {

    private List<Double> group1CTF;
    private List<Double> group1CTT;
    private List<Double> group2CTF;
    private List<Double> group2CTT;

    public List<Double> getGroup1CTF() {
        return group1CTF;
    }

    public void setGroup1CTF(List<Double> group1CTF) {
        this.group1CTF = group1CTF;
    }

    public List<Double> getGroup1CTT() {
        return group1CTT;
    }

    public void setGroup1CTT(List<Double> group1CTT) {
        this.group1CTT = group1CTT;
    }

    public List<Double> getGroup2CTF() {
        return group2CTF;
    }

    public void setGroup2CTF(List<Double> group2CTF) {
        this.group2CTF = group2CTF;
    }

    public List<Double> getGroup2CTT() {
        return group2CTT;
    }

    public void setGroup2CTT(List<Double> group2CTT) {
        this.group2CTT = group2CTT;
    }
}
