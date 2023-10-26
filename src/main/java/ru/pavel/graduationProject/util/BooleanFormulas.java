package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BooleanFormulas extends Formulas{

    private ArrayList<List<Boolean>> groups;
    private ArrayList<List<String>> groupsString;
    private ArrayList<ArrayList<Integer>> score;
    private ArrayList<List<Double>> fisherResultList;
    private ArrayList<List<Double>> xiList;

    public BooleanFormulas(List<String> firstNameGroup1, List<String> secondNameGroup1, List<String> firstNameGroup2, List<String> secondNameGroup2, ArrayList<List<Boolean>> groups) {
        super(firstNameGroup1, secondNameGroup1, firstNameGroup2, secondNameGroup2);
        this.groups = groups;
    }

    public ArrayList<List<Boolean>> getGroups() {
        return groups;
    }

    public void setGroups(List<Boolean> group) {
        this.groups.add(group);
    }

    public ArrayList<List<String>> getGroupsString() {
        return groupsString;
    }

    public void setGroupsString(ArrayList<List<String>> groupsString) {
        this.groupsString = groupsString;
    }

    public ArrayList<ArrayList<Integer>> getScore() {
        return score;
    }

    public void setScore(ArrayList<ArrayList<Integer>> score) {
        this.score = score;
    }

    public ArrayList<List<Double>> getFisherResultList() {
        return fisherResultList;
    }

    public void setFisherResultList(ArrayList<List<Double>> fisherResultList) {
        this.fisherResultList = fisherResultList;
    }

    public void setGroups(ArrayList<List<Boolean>> groups) {
        this.groups = groups;
    }

    public ArrayList<List<Double>> getXiList() {
        return xiList;
    }

    public void setXiList(ArrayList<List<Double>> xiList) {
        this.xiList = xiList;
    }
    public double getXiCTF(){return this.xiList.get(0).get(1);}
    public double getXiCTT(){return this.xiList.get(2).get(3);}
    public double getFisherCTF(){return this.fisherResultList.get(0).get(1);}
    public double getFisherCTT(){return this.fisherResultList.get(2).get(3);}

    public ArrayList<List<String>> convertGroupToString(ArrayList<List<Boolean>> groups, String positive, String negative){
        ArrayList<List<String>> main=new ArrayList<>();
        for (List<Boolean> x:groups){
            List<String> stringList=new ArrayList<>();
            for (boolean y:x){
                if (y){
                    stringList.add(positive);
                }
                else {
                    stringList.add(negative);
                }
            }
            main.add(stringList);
        }
        return main;
    }
    public ArrayList<ArrayList<Integer>>  conversionToOrdinalScale(ArrayList<List<Boolean>> groups){
        ArrayList<ArrayList<Integer>> main=new ArrayList<>();
        for (int i=0;i<=1;i++){
            ArrayList<Integer> list = new ArrayList<>();
            if (i%2==0) {
                for (List<Boolean> x : groups) {
                    int falseScore = 0;
                    for (boolean y : x) {
                        if (!y) {
                            falseScore++;
                        }
                    }
                    list.add(falseScore);
                }
            }
            else {
                for (List<Boolean> x : groups) {
                    int trueScore = 0;
                    for (boolean y : x) {
                        if (y) {
                            trueScore++;
                        }
                    }
                    list.add(trueScore);
                }
            }
            main.add(list);
        }
        return main;
    }
}