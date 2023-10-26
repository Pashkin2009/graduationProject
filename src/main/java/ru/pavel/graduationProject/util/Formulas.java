package ru.pavel.graduationProject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class Formulas {
    protected List<String> firstNameGroup1;
    protected List<String> secondNameGroup1;
    protected List<String> firstNameGroup2;
    protected List<String> secondNameGroup2;
    private int scoreNumber=3;//градация баллов

    private final ArrayList<Double> alphaForXi=new ArrayList<>(Arrays.asList(3.84,5.99,7.82,9.49,11.07,12.59,14.07,15.52,16.92));//критерий для хи
    @Autowired
    public Formulas(List<String> firstNameGroup1, List<String> secondNameGroup1, List<String> firstNameGroup2, List<String> secondNameGroup2) {
        this.firstNameGroup1 = firstNameGroup1;
        this.secondNameGroup1 = secondNameGroup1;
        this.firstNameGroup2 = firstNameGroup2;
        this.secondNameGroup2 = secondNameGroup2;
    }

    public List<String> getFirstNameGroup1() {
        return firstNameGroup1;
    }

    public List<String> getSecondNameGroup1() {
        return secondNameGroup1;
    }

    public List<String> getFirstNameGroup2() {
        return firstNameGroup2;
    }

    public List<String> getSecondNameGroup2() {
        return secondNameGroup2;
    }

    public void setFirstNameGroup1(List<String> firstNameGroup1) {
        this.firstNameGroup1 = firstNameGroup1;
    }

    public void setSecondNameGroup1(List<String> secondNameGroup1) {
        this.secondNameGroup1 = secondNameGroup1;
    }

    public void setFirstNameGroup2(List<String> firstNameGroup2) {
        this.firstNameGroup2 = firstNameGroup2;
    }

    public void setSecondNameGroup2(List<String> secondNameGroup2) {
        this.secondNameGroup2 = secondNameGroup2;
    }

    public int getScoreNumber() {
        return scoreNumber;
    }

    public void setScoreNumber(int scoreNumber) {
        this.scoreNumber = scoreNumber;
    }

    public ArrayList<Double> getAlphaForXi() {
        return alphaForXi;
    }

    public ArrayList<ArrayList<Integer>>  conversionToOrdinalScale(List<Integer>list,
                                                                   List<Integer>list2,
                                                                   List<Integer>list3,
                                                                   List<Integer>list4,
                                                                   int scoreQuantity ){
        ArrayList<ArrayList<Integer>> main= new ArrayList<>();
        double min;
        double max;
        if (scoreQuantity==2){min=0;}else {
            //ищем минимальное значение среди 4 списков
            if (Collections.min(list) < Collections.min(list2)) {
                min = (double) Collections.min(list);
            } else {
                min = (double) Collections.min(list2);
            }
            if (min > Collections.min(list3)) {
                min = (double) Collections.min(list3);
            }
            if (min > Collections.min(list4)) {
                min = (double) Collections.min(list4);
            }
        }
        //ищем максимальное значение среди 4 списков
        if(Collections.max(list)>Collections.max(list2)){max=(double) Collections.max(list);}
        else {max=(double) Collections.max(list2);}
        if (max<Collections.max(list3)){max=(double) Collections.max(list3);}
        if (max<Collections.max(list4)){max=(double) Collections.max(list4);}

        double pitch=(max-min)/scoreQuantity;//шаг

        for(int i=0;i<=scoreQuantity-1;i++){
            ArrayList<Integer> ordinalScale=new ArrayList<>();
            int people=0;
            int people2=0;
            int people3=0;
            int people4=0;
            for (int x:list){
                if (i==0&&x<=min+pitch){
                    people++;
                }else if (x>min&&x<=min+pitch){
                    people++;
                }
            }
            for (int x:list2){
                if (i==0&&x<=min+pitch){
                    people2++;
                }else if (x>min&&x<=min+pitch){
                    people2++;
                }
            }
            for (int x:list3){
                if (i==0&&x<=min+pitch){
                    people3++;
                }else if (x>min&&x<=min+pitch){
                    people3++;
                }
            }
            for (int x:list4){
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
    public String Format(double number){
        DecimalFormat format = new DecimalFormat();
        format.setDecimalSeparatorAlwaysShown(false);
        return format.format(number);
    }
    public double average(List<Double> list){
        return list.stream().mapToDouble(Double::doubleValue).sum()/list.size();
    }
    public double averageInt(List<Integer> list){
        return (double) list.stream().mapToInt(Integer::intValue).sum() /list.size();
    }

    public double percent(double number, int quantity){
        return ( number /quantity)*100;
    }

    public ArrayList<List<Double>> fisherCritrery(ArrayList<ArrayList<Integer>> list, int group1Size,int group2Size) {
        ArrayList<List<Double>> fisherResult = new ArrayList<>();
        double d;
        for (int i = 0; i <= 3; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j <= 3; j++) {
                if (i==j){d=0.0;}
                else {
                    if (i % 2 == 0) {
                        if (j % 2 == 0) {
                            d=Math.abs(2*Math.asin(Math.sqrt((double) list.get(1).get(i) /group1Size))-2*Math.asin(Math.sqrt((double) list.get(1).get(j)/group1Size)));
                        } else {
                            //g1,g2
                            d=Math.abs(2*Math.asin(Math.sqrt((double) list.get(1).get(i) /group1Size))-2*Math.asin(Math.sqrt((double) list.get(1).get(j)/group2Size)));
                        }
                    } else {
                        if (j % 2 == 0) {
                            //g2,g1
                            d=Math.abs(2*Math.asin(Math.sqrt((double) list.get(1).get(i) /group2Size))-2*Math.asin(Math.sqrt((double) list.get(1).get(j)/group1Size)));
                        } else {
                            //g2,g2
                            d=Math.abs(2*Math.asin(Math.sqrt((double) list.get(1).get(i) /group2Size))-2*Math.asin(Math.sqrt((double) list.get(1).get(j)/group2Size)));
                        }
                    }
                }
                d=d*Math.sqrt((double) (group1Size * group2Size) /(group1Size+group2Size));
                row.add(d);
            }
            fisherResult.add(row);
        }
        return fisherResult;
    }

    public ArrayList<List<Double>> xi(ArrayList<ArrayList<Integer>> scoreList,int g1size,int g2size){
        ArrayList<List<Double>> xiResult=new ArrayList<>();
        double d;
        for (int i=0;i<=3;i++){
            ArrayList<Double> row=new ArrayList<>();
            for (int j=0;j<=3;j++){
                d=0.0;
                for (List<Integer> x:scoreList){
                    if (i==j||x.get(j)==0||x.get(i)==0){d=0.0;}
                    else {
                        if (j%2==0){
                            d +=Math.pow(((double) x.get(i) / g2size) - ((double) x.get(j) / g1size ), 2)/(x.get(i) + x.get(j));
                        }
                        else {
                            d +=Math.pow(((double) x.get(i) / g1size ) - ((double) x.get(j) / g2size ), 2)/(x.get(i) + x.get(j));
                        }
                    }
                }
                d=g2size*g1size*d;
                row.add(d);
            }
            xiResult.add(row);
        }
        return xiResult;
    }
}