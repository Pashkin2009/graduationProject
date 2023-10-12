package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;
import ru.pavel.graduationProject.services.SampleService;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Calculation {

    private final SampleService sampleService;
    private final Formulas formulas;
    private final NumberFormulas numberFormulas;
    private final RealFormulas realFormulas;
    private final BooleanFormulas booleanFormulas;
    private int checkResult = 0;

    private int needColum=0;



    public Calculation(SampleService sampleService, Formulas formulas, NumberFormulas numberFormulas, RealFormulas realFormulas, BooleanFormulas booleanFormulas) {
        this.sampleService = sampleService;
        this.formulas = formulas;
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
        realFormulas.getGroups().clear();
        booleanFormulas.getGroups().clear();
        numberFormulas.getGroup1CTF().clear();
        numberFormulas.getGroup1CTT().clear();
        numberFormulas.getGroup2CTF().clear();
        numberFormulas.getGroup2CTT().clear();
        List<Integer> chooseList = Stream.of(UserChoose.split(",", -1))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        setNeedColum(0);
        setCheckResult(0);
        //проверка на одинаковые группы
        if (chooseList.get(0).equals(chooseList.get(1))) {
            setCheckResult(1);
            setNeedColum(0);
            System.out.println("Группы одинаковые");
        }
        else {
            realFormulas.addGroup(sampleService.findResultDouble(false,chooseList.get(0),chooseList.get(2)));
            if (!realFormulas.getGroups().get(0).isEmpty() && realFormulas.getGroups().get(0).get(realFormulas.getGroups().get(0).size() - 1) != null) {
                realFormulas.addGroup(sampleService.findResultDouble(true, chooseList.get(0), chooseList.get(2)));
                if (!realFormulas.getGroups().get(1).isEmpty() && realFormulas.getGroups().get(1).get(realFormulas.getGroups().get(1).size() - 1) != null) {
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
                    booleanFormulas.setGroups(sampleService.findResultBoolean(false,chooseList.get(0),chooseList.get(2)));
                    if (!booleanFormulas.getGroups().get(0).isEmpty() && booleanFormulas.getGroups().get(0).get(booleanFormulas.getGroups().get(0).size() - 1) != null) {
                        booleanFormulas.setGroups(sampleService.findResultBoolean(true, chooseList.get(0), chooseList.get(2)));

                        if (!booleanFormulas.getGroups().get(1).isEmpty() && booleanFormulas.getGroups().get(1).get(booleanFormulas.getGroups().get(1).size() - 1) != null) {
                            System.out.println("Группа 1 используется boolean");
                            setCheckResult(0);
                            setNeedColum(3);
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
                    realFormulas.addGroup(sampleService.findResultDouble(false,chooseList.get(1),chooseList.get(2)));
                    if (!realFormulas.getGroups().get(2).isEmpty() && realFormulas.getGroups().get(2).get(realFormulas.getGroups().get(2).size() - 1) != null){
                        realFormulas.addGroup(sampleService.findResultDouble(true,chooseList.get(1),chooseList.get(2)));
                        if (!realFormulas.getGroups().get(3).isEmpty() && realFormulas.getGroups().get(3).get(realFormulas.getGroups().get(3).size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется double");
                            realFormulas.setDispersion(sampleService.getDispersionRealNumbers(false,chooseList.get(0),chooseList.get(2)));
                            realFormulas.setDispersion(sampleService.getDispersionRealNumbers(true,chooseList.get(0),chooseList.get(2)));
                            realFormulas.setDispersion(sampleService.getDispersionRealNumbers(false,chooseList.get(1),chooseList.get(2)));
                            realFormulas.setDispersion(sampleService.getDispersionRealNumbers(true,chooseList.get(1),chooseList.get(2)));
                            realFormulas.setCramerList(realFormulas.cramerFormulas(realFormulas.getGroups(),realFormulas.getDispersion()));
                            realFormulas.setVilkasList(realFormulas.vilksonFormuls(realFormulas.getGroups()));
                            realFormulas.setScore(realFormulas.conversionToOrdinalScale(realFormulas.getGroups(),realFormulas.getScoreNumber()));
                            realFormulas.setXiList(realFormulas.xi(realFormulas.getScore(),realFormulas.getGroups()));
                            realFormulas.setFisherResultList(realFormulas.fisherCritrery(realFormulas.getScore(),realFormulas.getGroups().get(0).size(),realFormulas.getGroups().get(2).size()));
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
                        setNeedColum(0);
                        System.out.println("группа 2 нет данных double CTF");
                    }
                    break;
                case (2):
                    numberFormulas.setGroup2CTF(sampleService.findResultInt(false,chooseList.get(1),chooseList.get(2)));
                    if (!numberFormulas.getGroup2CTF().isEmpty() && numberFormulas.getGroup2CTF().get(numberFormulas.getGroup2CTF().size() - 1) != null){
                        numberFormulas.setGroup2CTT(sampleService.findResultInt(true,chooseList.get(1),chooseList.get(2)));
                        if (!numberFormulas.getGroup2CTT().isEmpty() && numberFormulas.getGroup2CTT().get(numberFormulas.getGroup2CTF().size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется integer");
                            numberFormulas.setDispersionGroup1CTF(sampleService.getDispersion(false,chooseList.get(0),chooseList.get(2)));
                            numberFormulas.setDispersionGroup1CTT(sampleService.getDispersion(true,chooseList.get(0),chooseList.get(2)));
                            numberFormulas.setDispersionGroup2CTF(sampleService.getDispersion(false,chooseList.get(1),chooseList.get(2)));
                            numberFormulas.setDispersionGroup2CTT(sampleService.getDispersion(true,chooseList.get(1),chooseList.get(2)));
                            numberFormulas.setCramer1(numberFormulas.cramerFormuls(numberFormulas.getGroup1CTF(),numberFormulas.getGroup2CTF(),numberFormulas.getDispersionGroup1CTF(),numberFormulas.getDispersionGroup2CTF()));
                            numberFormulas.setCramer2(numberFormulas.cramerFormuls(numberFormulas.getGroup1CTT(),numberFormulas.getGroup2CTT(),numberFormulas.getDispersionGroup1CTT(),numberFormulas.getDispersionGroup2CTT()));
                            numberFormulas.setVilkas1(numberFormulas.vilksonFormuls(numberFormulas.getGroup1CTF(),numberFormulas.getGroup2CTF()));
                            numberFormulas.setVilkas2(numberFormulas.vilksonFormuls(numberFormulas.getGroup1CTT(),numberFormulas.getGroup2CTT()));
                            numberFormulas.setScore(formulas.conversionToOrdinalScale(numberFormulas.getGroup1CTF(),numberFormulas.getGroup2CTF(),numberFormulas.getGroup1CTT(),numberFormulas.getGroup2CTT(),numberFormulas.getScoreNumber()));
                            numberFormulas.setXiResultList(numberFormulas.xi(numberFormulas.getScore(),numberFormulas.getGroup1CTF(),numberFormulas.getGroup2CTF()));
                            numberFormulas.setFisherResultList(numberFormulas.fisherCritrery(numberFormulas.getScore(),numberFormulas.getGroup1CTF().size(),numberFormulas.getGroup2CTF().size()));
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
                    booleanFormulas.setGroups(sampleService.findResultBoolean(false,chooseList.get(1),chooseList.get(2)));
                    if (!booleanFormulas.getGroups().get(2).isEmpty() && booleanFormulas.getGroups().get(2).get(booleanFormulas.getGroups().get(2).size() - 1) != null){
                        booleanFormulas.setGroups(sampleService.findResultBoolean(true,chooseList.get(1),chooseList.get(2)));
                        if (!booleanFormulas.getGroups().get(3).isEmpty() && booleanFormulas.getGroups().get(3).get(booleanFormulas.getGroups().get(3).size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется boolean");
                            booleanFormulas.setGroupsString(booleanFormulas.convertGroupToString(booleanFormulas.getGroups(),"Сдал","Не сдал"));
                            booleanFormulas.setScore(booleanFormulas.conversionToOrdinalScale(booleanFormulas.getGroups()));
                            booleanFormulas.setFisherResultList(formulas.fisherCritrery(booleanFormulas.getScore(),booleanFormulas.getGroups().get(0).size(),booleanFormulas.getGroups().get(2).size()));
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
        formulas.setFirstNameGroup1(sampleService.getLearnerFirstName(false,chooseList.get(0),chooseList.get(2)));
        formulas.setSecondNameGroup1(sampleService.getLearnerLastName(false,chooseList.get(0),chooseList.get(2)));
        formulas.setFirstNameGroup2(sampleService.getLearnerFirstName(false,chooseList.get(1),chooseList.get(2)));
        formulas.setSecondNameGroup2(sampleService.getLearnerLastName(false,chooseList.get(1),chooseList.get(2)));
        System.out.println("CheckResult="+getCheckResult());
        System.out.println("NeedColum="+getNeedColum());
    }
}