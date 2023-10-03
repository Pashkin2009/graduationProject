package ru.pavel.graduationProject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Formulas {
    protected List<String> firstNameGroup1;
    protected List<String> secondNameGroup1;
    protected List<String> firstNameGroup2;
    protected List<String> secondNameGroup2;
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

    public List<Integer> conversionToOrdinalScale(List<Integer>list,int scoreQuantity ){
        List<Integer> ordinalScale=new ArrayList<>();
        double pitch= (double) (Collections.max(list)-Collections.min(list))/scoreQuantity;
        int min=Collections.min(list);
        for(int i=0;i<=scoreQuantity-1;i++){
            int people=0;
            for (int x:list){
                if (i==0&&x<=min+pitch){
                    people++;
                }else if (x>min&&x<=min+pitch){
                    people++;
                }
            }
            min+= (int) pitch;
            assert false;
            ordinalScale.add(people);
        }
    return ordinalScale;
    }
    public List<Integer> conversionToOrdinalScale(List<Integer>list,int scoreQuantity ,int min,int max){
        List<Integer> ordinalScale=new ArrayList<>();
        double pitch= (double) (max-min)/scoreQuantity;
        for(int i=0;i<=scoreQuantity-1;i++){
            int people=0;
            for (int x:list){
                if (i==0&&x<=min+pitch){
                    people++;
                }else if (x>min&&x<=min+pitch){
                    people++;
                }
            }
            min+= (int) pitch;
            assert false;
            ordinalScale.add(people);
        }
        return ordinalScale;
    }

}