package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Calculation {
    private String mainStr;

    public Calculation() {
    }

    public Calculation(String mainStr) {
        this.mainStr = mainStr;
    }

    public String getMainStr() {
        return mainStr;
    }

    public String setMainStr(String mainStr) {
        this.mainStr = mainStr;
        return mainStr;
    }

    public void testCalculation(String mainStr){
        List<Integer> listIt= Stream.of(mainStr.split(",",-1))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        System.out.println("TestCalculationMethod: "+listIt);
    }
}
