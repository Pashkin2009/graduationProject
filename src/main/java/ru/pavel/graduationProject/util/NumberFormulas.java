package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Component
public class NumberFormulas extends Formulas{

    private List<Integer> group1CTF;//результаты для группы 1 до эксперемента
    private List<Integer> group2CTF;//результаты для группы 2 до эксперемента
    private List<Integer> group1CTT;//результаты для группы 1 после эксперемента
    private List<Integer> group2CTT;//результаты для группы 2 после эксперемента

    private double dispersionGroup2CTF;//подсчет дисперсии для группы 2 до эксперемента
    private double dispersionGroup1CTF;//подсчет дисперсии для группы 1 до эксперемента
    private double dispersionGroup2CTT;//подсчет дисперсии для группы 2 после эксперемента
    private double dispersionGroup1CTT;//подсчет дисперсии для группы 1 после эксперемента
    private double cramer1;//результат формулы крамера
    private double cramer2;//результат формулы крамера
    private double vilkas1;//результат формулы вилксона
    private double vilkas2;//результат формулы вилксона
    private ArrayList<ArrayList<Integer>> Score;// лист результатов с баллами по всем группам
    private ArrayList<List<Double>> xiResultList;//лист с результатами расчета хи формулы
    private ArrayList<List<Double>> fisherResultList;//лист с результатами расчета формулыфишера

    public NumberFormulas(List<String> firstNameGroup1, List<String> secondNameGroup1, List<String> firstNameGroup2, List<String> secondNameGroup2, List<Integer> group1CTF, List<Integer> group2CTF, List<Integer> group1CTT, List<Integer> group2CTT) {
        super(firstNameGroup1, secondNameGroup1, firstNameGroup2, secondNameGroup2);
        this.group1CTF = group1CTF;
        this.group2CTF = group2CTF;
        this.group1CTT = group1CTT;
        this.group2CTT = group2CTT;
    }

    public List<Integer> getGroup1CTF() {
        return group1CTF;
    }

    public void setGroup1CTF(List<Integer> group1CTF) {
        this.group1CTF = group1CTF;
    }

    public List<Integer> getGroup2CTF() {
        return group2CTF;
    }

    public void setGroup2CTF(List<Integer> group2CTF) {
        this.group2CTF = group2CTF;
    }

    public List<Integer> getGroup1CTT() {
        return group1CTT;
    }

    public void setGroup1CTT(List<Integer> group1CTT) {
        this.group1CTT = group1CTT;
    }

    public List<Integer> getGroup2CTT() {
        return group2CTT;
    }

    public void setGroup2CTT(List<Integer> group2CTT) {
        this.group2CTT = group2CTT;
    }

    public double getDispersionGroup2CTF() {
        return dispersionGroup2CTF;
    }

    public void setDispersionGroup2CTF(double dispersionGroup2CTF) {
        this.dispersionGroup2CTF = dispersionGroup2CTF;
    }

    public double getDispersionGroup1CTF() {
        return dispersionGroup1CTF;
    }

    public void setDispersionGroup1CTF(double dispersionGroup1CTF) {
        this.dispersionGroup1CTF = dispersionGroup1CTF;
    }

    public double getDispersionGroup2CTT() {
        return dispersionGroup2CTT;
    }

    public void setDispersionGroup2CTT(double dispersionGroup2CTT) {
        this.dispersionGroup2CTT = dispersionGroup2CTT;
    }

    public double getDispersionGroup1CTT() {
        return dispersionGroup1CTT;
    }

    public void setDispersionGroup1CTT(double dispersionGroup1CTT) {
        this.dispersionGroup1CTT = dispersionGroup1CTT;
    }

    public double getCramer1() {
        return cramer1;
    }

    public void setCramer1(double cramer1) {
        this.cramer1 = cramer1;
    }

    public double getCramer2() {
        return cramer2;
    }

    public void setCramer2(double cramer2) {
        this.cramer2 = cramer2;
    }

    public double getVilkas1() {
        return vilkas1;
    }

    public void setVilkas1(double vilkas1) {
        this.vilkas1 = vilkas1;
    }

    public double getVilkas2() {
        return vilkas2;
    }

    public void setVilkas2(double vilkas2) {
        this.vilkas2 = vilkas2;
    }

    public ArrayList<ArrayList<Integer>> getScore() {
        return Score;
    }

    public void setScore(ArrayList<ArrayList<Integer>> score) {
        Score = score;
    }

    public ArrayList<List<Double>> getXiResultList() {
        return xiResultList;
    }

    public void setXiResultList(ArrayList<List<Double>> xiResultList) {
        this.xiResultList = xiResultList;
    }


   public Double getXiCTF(){return getXiResultList().get(0).get(1);}
    public Double getXiCTT(){return getXiResultList().get(2).get(3);}

    public ArrayList<List<Double>> getFisherResultList() {
        return fisherResultList;
    }

    public void setFisherResultList(ArrayList<List<Double>> fisherResultList) {
        this.fisherResultList = fisherResultList;
    }

    public Double getFisherCTF(){return getFisherResultList().get(0).get(1);}
    public Double getFisherCTT(){return getFisherResultList().get(2).get(3);}

    public Double cramerFormuls(List<Integer> list1, List<Integer>list2, double dispersion1, double dispersion2){
    return (Math.sqrt(list1.size()*list2.size())*Math.abs(((double)list1.stream().mapToInt(Integer::intValue).sum() /list1.size())-((double)list2.stream().mapToInt(Integer::intValue).sum() /list2.size())))/
            Math.sqrt(list1.size()*dispersion2+list2.size()*dispersion1);
    }

    public Double vilksonFormuls(List<Integer> list1,List<Integer> list2){
        int U=0;
        if (list1.size()<list2.size()){
            for (int y:list1){
                for (int x:list2){
                    if(x>y) {
                        U++;
                    }
                }
            }
        }
        else
        {
            for (int y:list2){
                for (int x:list1){
                    if(x>y) {
                        U++;
                    }
                }
            }
        }
    return (Math.abs(((list1.size()*list2.size())/2)-U))/Math.sqrt((double) (list1.size() * list2.size() * (list1.size() + list2.size() + 1)) /12);
    }

    public double percent(int number, int quantity){
        return ((double) number /quantity)*100;
    }

}