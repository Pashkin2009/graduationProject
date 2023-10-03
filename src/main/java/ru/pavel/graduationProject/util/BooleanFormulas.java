package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BooleanFormulas {

    private List<Boolean> group1CTF;
    private List<Boolean> group1CTT;
    private List<Boolean> group2CTF;
    private List<Boolean> group2CTT;

    public List<Boolean> getGroup1CTF() {
        return group1CTF;
    }

    public void setGroup1CTF(List<Boolean> group1CTF) {
        this.group1CTF = group1CTF;
    }

    public List<Boolean> getGroup1CTT() {
        return group1CTT;
    }

    public void setGroup1CTT(List<Boolean> group1CTT) {
        this.group1CTT = group1CTT;
    }

    public List<Boolean> getGroup2CTF() {
        return group2CTF;
    }

    public void setGroup2CTF(List<Boolean> group2CTF) {
        this.group2CTF = group2CTF;
    }

    public List<Boolean> getGroup2CTT() {
        return group2CTT;
    }

    public void setGroup2CTT(List<Boolean> group2CTT) {
        this.group2CTT = group2CTT;
    }
}
