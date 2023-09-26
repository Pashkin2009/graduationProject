package ru.pavel.graduationProject.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.pavel.graduationProject.services.SampleService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Calculation {

    private SampleService sampleService;
    private int checkResult = 0;
    private int needColum;
    private List<Integer> listIt;
    private List<Integer> integerListG1;
    private List<Boolean> booleanListG1;
    private List<Double> doubleListG1;
    private List<Integer> integerListG2;
    private List<Boolean> booleanListG2;
    private List<Double> doubleListG2;

    public Calculation() {
    }
    @Autowired
    public Calculation(SampleService sampleService, List<Integer> listIt, List<Integer> integerListG1, List<Boolean> booleanListG1, List<Double> doubleListG1, List<Integer> integerListG2, List<Boolean> booleanListG2, List<Double> doubleListG2) {
        this.sampleService = sampleService;
        this.listIt = listIt;
        this.integerListG1 = integerListG1;
        this.booleanListG1 = booleanListG1;
        this.doubleListG1 = doubleListG1;
        this.integerListG2 = integerListG2;
        this.booleanListG2 = booleanListG2;
        this.doubleListG2 = doubleListG2;
    }

    public void setListIt(List<Integer> listIt) {
        this.listIt = listIt;
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

    public void setIntegerListG1(List<Integer> integerListG1) {
        this.integerListG1 = integerListG1;
    }

    public void setBooleanListG1(List<Boolean> booleanListG1) {
        this.booleanListG1 = booleanListG1;
    }

    public void setDoubleListG1(List<Double> doubleListG1) {
        this.doubleListG1 = doubleListG1;
    }

    public void setIntegerListG2(List<Integer> integerListG2) {
        this.integerListG2 = integerListG2;
    }

    public void setBooleanListG2(List<Boolean> booleanListG2) {
        this.booleanListG2 = booleanListG2;
    }

    public void setDoubleListG2(List<Double> doubleListG2) {
        this.doubleListG2 = doubleListG2;
    }

    public List<Integer> getIntegerListG1() {
        return integerListG1;
    }

    public List<Boolean> getBooleanListG1() {
        return booleanListG1;
    }

    public List<Double> getDoubleListG1() {
        return doubleListG1;
    }

    public List<Integer> getIntegerListG2() {
        return integerListG2;
    }

    public List<Boolean> getBooleanListG2() {
        return booleanListG2;
    }

    public List<Double> getDoubleListG2() {
        return doubleListG2;
    }

    public List<Integer> getListIt() {
        return listIt;
    }

    public void stringToList(String mainStr){
      setListIt(Stream.of(mainStr.split(",",-1))
              .map(String::trim)
              .map(Integer::parseInt)
              .collect(Collectors.toList()));
    }

    private void setAllList(boolean controlTask){
        setDoubleListG1(sampleService.findResultDouble(controlTask, this.listIt.get(0), this.listIt.get(2)));
        setDoubleListG2(sampleService.findResultDouble(controlTask, this.listIt.get(1), this.listIt.get(2)));
        setIntegerListG1(sampleService.findResultInt(controlTask, this.listIt.get(0), this.listIt.get(2)));
        setIntegerListG2(sampleService.findResultInt(controlTask, this.listIt.get(1), this.listIt.get(2)));
        setBooleanListG1(sampleService.findResultBoolean(controlTask, this.listIt.get(0), this.listIt.get(2)));
        setBooleanListG2(sampleService.findResultBoolean(controlTask, this.listIt.get(1), this.listIt.get(2)));
        }
        public void checkResult(){
        setAllList(false);//control task false. Контрольное задание до начала эксперемента
        if (listIt.get(0).equals(listIt.get(1)))
        {
            System.out.println("группы одинаковые");
            setCheckResult(1);
            return;
        }
        else if(doubleListG1.isEmpty()&&integerListG1.isEmpty()&&booleanListG1.isEmpty())
        {
            System.out.println("у группы 1 нет этого задания CTF");
            setCheckResult(2);
            return;
        }
        else if(doubleListG2.isEmpty()&&integerListG2.isEmpty()&&booleanListG2.isEmpty())
        {
            System.out.println("у группы 2 нет этого задания CTF");
            setCheckResult(3);
            return;
        }
        setAllList(true);//control task true. Контрольное задание после начала эксперемента
        if(doubleListG1.isEmpty()&&integerListG1.isEmpty()&&booleanListG1.isEmpty())
        {
            System.out.println("у группы 1 нет этого задания CTT");
            setCheckResult(4);
            return;
        }
        else if(doubleListG2.isEmpty()&&integerListG2.isEmpty()&&booleanListG2.isEmpty())
        {
            System.out.println("у группы 2 нет этого задания CTT");
            setCheckResult(5);
            return;
        }
            if (!(doubleListG1.get(0) ==null)){
                System.out.println("Лист double не пуст");
                setNeedColum(1);
            }
            else if (!(integerListG1.get(0) ==null)){
                System.out.println("Лист integer не пуст");
                setNeedColum(2);
            }
            else if (!(booleanListG1.get(0) ==null)){
                System.out.println("Лист boolean не пуст");
                setNeedColum(3);
            }
            else
            {
                System.out.println("Все листы путые");
            }
            System.out.println("проверки выполненны всё хорошо ");
            setCheckResult(0);
        }


}