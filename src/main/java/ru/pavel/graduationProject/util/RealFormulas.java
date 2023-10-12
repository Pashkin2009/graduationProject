package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RealFormulas extends Formulas {

    private ArrayList<List<Double>> groups;
    private final List<Double> dispersion;
    private List<Double> cramerList;
    private List<Double> vilkasList;
    private ArrayList<ArrayList<Integer>> score;
    private ArrayList<List<Double>> xiList;
    private ArrayList<List<Double>> fisherResultList;

    public RealFormulas(List<String> firstNameGroup1, List<String> secondNameGroup1, List<String> firstNameGroup2, List<String> secondNameGroup2, ArrayList<List<Double>> groups, List<Double> dispersion) {
        super(firstNameGroup1, secondNameGroup1, firstNameGroup2, secondNameGroup2);
        this.groups = groups;
        this.dispersion = dispersion;
    }

    public ArrayList<List<Double>> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<List<Double>> groups) {
        this.groups = groups;
    }

    public void addGroup(ArrayList<Double> group){
       this.groups.add(group);
    }

    public List<Double> getDispersion() {
        return dispersion;
    }

    public void setDispersion(Double dispersion) {
        this.dispersion.add(dispersion);
    }

    public List<Double> getCramerList() {
        return cramerList;
    }

    public void setCramerList(List<Double> cramerList) {
        this.cramerList = cramerList;
    }

    public List<Double> getVilkasList() {
        return vilkasList;
    }

    public void setVilkasList(List<Double> vilkasList) {
        this.vilkasList = vilkasList;
    }

    public ArrayList<ArrayList<Integer>> getScore() {
        return score;
    }

    public void setScore(ArrayList<ArrayList<Integer>> score) {
        this.score = score;
    }

    public ArrayList<List<Double>> getXiList() {
        return xiList;
    }

    public void setXiList(ArrayList<List<Double>> xiList) {
        this.xiList = xiList;
    }

    public double getXiCTF(){
        return this.xiList.get(0).get(1);
    }

    public double getXiCTT(){
        return this.xiList.get(2).get(3);
    }

    public ArrayList<List<Double>> getFisherResultList() {
        return fisherResultList;
    }

    public void setFisherResultList(ArrayList<List<Double>> fisherResultList) {
        this.fisherResultList = fisherResultList;
    }

    public ArrayList<Double> cramerFormulas(ArrayList<List<Double>> groups, List<Double> dispersion){
            ArrayList<Double> result=new ArrayList<>();
           double cramer= (Math.sqrt(groups.get(0).size()*groups.get(2).size())*Math.abs(groups.get(0).stream().mapToDouble(Double::doubleValue).sum() /groups.get(0).size() -(groups.get(2).stream().mapToDouble(Double::doubleValue).sum()/groups.get(2).size())))/
                Math.sqrt(groups.get(0).size()*dispersion.get(2)+groups.get(2).size()*dispersion.get(0));
           result.add(cramer);
        cramer= (Math.sqrt(groups.get(1).size()*groups.get(3).size())*Math.abs(groups.get(1).stream().mapToDouble(Double::doubleValue).sum() /groups.get(1).size() -(groups.get(3).stream().mapToDouble(Double::doubleValue).sum()/groups.get(3).size())))/
                Math.sqrt(groups.get(1).size()*dispersion.get(3)+groups.get(3).size()*dispersion.get(1));
        result.add(cramer);
        return result;
    }
    public ArrayList<Double> vilksonFormuls(ArrayList<List<Double>> list){
        int U;
        ArrayList<Double> main=new ArrayList<>();
        List<Double> list1;
        List<Double> list2;
        for (int i=0;i<=1;i++) {
            U=0;
            double d=0.0;
            if (i%2==0){list1=list.get(0);list2=list.get(2);}else {list1=list.get(1);list2=list.get(3);}
            if (list1.size() <= list2.size()) {
                for (double y : list1) {
                    for (double x : list2) {
                        if (x > y) {
                            U++;
                        }
                    }
                }
            } else {
                for (double y : list2) {
                    for (double x : list1) {
                        if (x > y) {
                            U++;
                        }
                    }
                }
            }
            d=(Math.abs(((list1.size()*list2.size())/2)-U))/Math.sqrt((double) (list1.size() * list2.size() * (list1.size() + list2.size() + 1)) /12);
            main.add(d);
        }
        return main;
    }

    public ArrayList<ArrayList<Integer>>  conversionToOrdinalScale(ArrayList<List<Double>> groups ,int scoreQuantity ){
        ArrayList<ArrayList<Integer>> main= new ArrayList<>();
        double min=0.0;
        double max=0.0;
        for (List<Double> x:groups){
           if(min>Collections.min(x)){min=Collections.min(x);}
           if(max<Collections.max(x)){max=Collections.max(x);}
        }
        if (scoreQuantity==2){min=0;}
        double pitch=(max-min)/scoreQuantity;

        for(int i=0;i<=scoreQuantity-1;i++){
            ArrayList<Integer> ordinalScale=new ArrayList<>();
            int people=0;
            int people2=0;
            int people3=0;
            int people4=0;
            for (double x:groups.get(0)){
                if (i==0&&x<=min+pitch){
                    people++;
                }else if (x>min&&x<=min+pitch){
                    people++;
                }
            }
            for (double x:groups.get(1)){
                if (i==0&&x<=min+pitch){
                    people2++;
                }else if (x>min&&x<=min+pitch){
                    people2++;
                }
            }
            for (double x:groups.get(2)){
                if (i==0&&x<=min+pitch){
                    people3++;
                }else if (x>min&&x<=min+pitch){
                    people3++;
                }
            }
            for (double x:groups.get(3)){
                if (i==0&&x<=min+pitch){
                    people4++;
                }else if (x>min&&x<=min+pitch){
                    people4++;
                }
            }
            min+= pitch;
            ordinalScale.add(people);
            ordinalScale.add(people2);
            ordinalScale.add(people3);
            ordinalScale.add(people4);
            main.add(ordinalScale);
        }
        return main;
    }

    public ArrayList<List<Double>> xi(ArrayList<ArrayList<Integer>> scoreList,ArrayList<List<Double>> groups){
        ArrayList<List<Double>> xiResult=new ArrayList<>();
        double d;
        for (int i=0;i<=3;i++){
            ArrayList<Double> row=new ArrayList<>();
            for (int j=0;j<=3;j++){
                d=0.0;
                for (List<Integer> x:scoreList){
                    if (i==j){d=0.0;}
                    else {
                        if (j%2==0){
                            d +=Math.pow(((double) x.get(i) / groups.get(2).size()) - ((double) x.get(j) / groups.get(0).size()), 2)/(x.get(i) + x.get(j));
                        }
                        else {
                            d +=Math.pow(((double) x.get(i) / groups.get(0).size()) - ((double) x.get(j) / groups.get(2).size()), 2)/(x.get(i) + x.get(j));
                        }
                    }
                }
                d=groups.get(2).size()*groups.get(0).size()*d;
                row.add(d);
            }
            xiResult.add(row);
        }
        return xiResult;
    }

}