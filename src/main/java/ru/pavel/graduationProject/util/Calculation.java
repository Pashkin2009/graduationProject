package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;
import ru.pavel.graduationProject.services.SampleService;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Calculation {

    private final SampleService sampleService;
    private final NumberFormulas numberFormulas;
    private final RealFormulas realFormulas;
    private final BooleanFormulas booleanFormulas;
    private int checkResult = 0;

    private int needColum=0;



    public Calculation(SampleService sampleService, NumberFormulas numberFormulas, RealFormulas realFormulas, BooleanFormulas booleanFormulas) {
        this.sampleService = sampleService;
        this.numberFormulas = numberFormulas;
        this.realFormulas = realFormulas;
        this.booleanFormulas = booleanFormulas;
    }




    public int getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(int checkResult) {
        this.checkResult = checkResult;
    }

    public int getNeedColum() {
        return needColum;
    }

    public void setNeedColum(int needColum) {
        this.needColum = needColum;
    }

    public void checkResult(String UserChoose) {
        List<Integer> chooseList = Stream.of(UserChoose.split(",", -1))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        //(!DG1CTF.isEmpty()&&DG1CTF.get(DG1CTF.size()-1)!=null)
        setNeedColum(0);
        setCheckResult(0);
        //проверка на одинаковые группы
        if (chooseList.get(0).equals(chooseList.get(1))) {
            setCheckResult(1);
            setNeedColum(0);
            System.out.println("Группы одинаковые");
        }
        else {
            realFormulas.setGroup1CTF(sampleService.findResultDouble(false,chooseList.get(0),chooseList.get(2)));
            if (!realFormulas.getGroup1CTF().isEmpty() && realFormulas.getGroup1CTF().get(realFormulas.getGroup1CTF().size() - 1) != null) {
                realFormulas.setGroup1CTT(sampleService.findResultDouble(true, chooseList.get(0), chooseList.get(2)));

                if (!realFormulas.getGroup1CTT().isEmpty() && realFormulas.getGroup1CTT().get(realFormulas.getGroup1CTT().size() - 1) != null) {
                    System.out.println("Группа 1 используется double");
                    setCheckResult(0);
                    setNeedColum(1);
                } else {
                    System.out.println("Группа 1 нет данных double CTT");
                    setCheckResult(2);
                    setNeedColum(0);
                }
            }
            else
            {
                numberFormulas.setGroup1CTF(sampleService.findResultInt(false,chooseList.get(0),chooseList.get(2)));

                if (!numberFormulas.getGroup1CTF().isEmpty() && numberFormulas.getGroup1CTF().get(numberFormulas.getGroup1CTF().size() - 1) != null) {
                    numberFormulas.setGroup1CTT(sampleService.findResultInt(true, chooseList.get(0), chooseList.get(2)));

                    if (!numberFormulas.getGroup1CTT().isEmpty() && numberFormulas.getGroup1CTT().get(numberFormulas.getGroup1CTT().size() - 1) != null) {
                        System.out.println("Группа 1 используется integer");
                        setCheckResult(0);
                        setNeedColum(2);
                    } else {
                        System.out.println("Группа 1 нет данных integer CTT");
                        setCheckResult(2);
                        setNeedColum(0);
                    }
                }
                else
                {
                    booleanFormulas.setGroup1CTF(sampleService.findResultBoolean(false,chooseList.get(0),chooseList.get(2)));

                    if (!booleanFormulas.getGroup1CTF().isEmpty() && booleanFormulas.getGroup1CTF().get(booleanFormulas.getGroup1CTF().size() - 1) != null) {
                        booleanFormulas.setGroup1CTT(sampleService.findResultBoolean(true, chooseList.get(0), chooseList.get(2)));

                        if (!booleanFormulas.getGroup1CTT().isEmpty() && booleanFormulas.getGroup1CTT().get(booleanFormulas.getGroup1CTT().size() - 1) != null) {
                            System.out.println("Группа 1 используется boolean");
                            setCheckResult(0);
                            setNeedColum(2);
                        } else {
                            System.out.println("Группа 1 нет данных boolean CTT");
                            setCheckResult(2);
                            setNeedColum(0);
                        }
                    }
                }

            }
            switch (getNeedColum()){
                case (0):
                    System.out.println("У группы 1 нет данных для этого задания");
                    setCheckResult(5);
                    setNeedColum(0);
                    break;
                case (1):
                    realFormulas.setGroup2CTF(sampleService.findResultDouble(false,chooseList.get(0),chooseList.get(2)));
                    if (!realFormulas.getGroup2CTF().isEmpty() && realFormulas.getGroup2CTF().get(realFormulas.getGroup2CTF().size() - 1) != null){
                        realFormulas.setGroup2CTT(sampleService.findResultDouble(true,chooseList.get(0),chooseList.get(2)));
                        if (!realFormulas.getGroup2CTT().isEmpty() && realFormulas.getGroup2CTT().get(realFormulas.getGroup2CTF().size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется double");
                        }
                        else
                        {
                            setCheckResult(3);
                            setNeedColum(0);
                            System.out.println("группа 2 нет данных double CTT");
                        }
                    }
                    else{
                        setCheckResult(4);
                        System.out.println("группа 2 нет данных double CTF");
                    }
                    break;
                case (2):
                    numberFormulas.setGroup2CTF(sampleService.findResultInt(false,chooseList.get(0),chooseList.get(2)));
                    if (!numberFormulas.getGroup2CTF().isEmpty() && numberFormulas.getGroup2CTF().get(numberFormulas.getGroup2CTF().size() - 1) != null){
                        numberFormulas.setGroup2CTT(sampleService.findResultInt(true,chooseList.get(0),chooseList.get(2)));
                        if (!numberFormulas.getGroup2CTT().isEmpty() && numberFormulas.getGroup2CTT().get(numberFormulas.getGroup2CTF().size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется integer");
                        }
                        else
                        {
                            setCheckResult(3);
                            setNeedColum(0);
                            System.out.println("группа 2 нет данных integer CTT");
                        }
                    }
                    else{
                        setCheckResult(4);
                        setNeedColum(0);
                        System.out.println("группа 2 нет данных integer CTF");
                    }
                    break;
                case (3):
                    booleanFormulas.setGroup2CTF(sampleService.findResultBoolean(false,chooseList.get(0),chooseList.get(2)));
                    if (!booleanFormulas.getGroup2CTF().isEmpty() && booleanFormulas.getGroup2CTF().get(booleanFormulas.getGroup2CTF().size() - 1) != null){
                        booleanFormulas.setGroup2CTT(sampleService.findResultBoolean(true,chooseList.get(0),chooseList.get(2)));
                        if (!booleanFormulas.getGroup2CTT().isEmpty() && booleanFormulas.getGroup2CTT().get(booleanFormulas.getGroup2CTF().size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется boolean");
                        }
                        else
                        {
                            setCheckResult(3);
                            setNeedColum(0);
                            System.out.println("группа 2 нет данных boolean CTT");
                        }
                    }
                    else{
                        setCheckResult(4);
                        setNeedColum(0);
                        System.out.println("группа 2 нет данных boolean CTF");
                    }
                    break;
            }
        }
        System.out.println("CheckResult="+getCheckResult());
        System.out.println("NeedColum="+getNeedColum());
    }
}
