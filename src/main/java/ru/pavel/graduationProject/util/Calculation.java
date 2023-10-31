package ru.pavel.graduationProject.util;

import org.springframework.stereotype.Component;
import ru.pavel.graduationProject.models.Learner;
import ru.pavel.graduationProject.models.Sample;
import ru.pavel.graduationProject.models.TaskName;
import ru.pavel.graduationProject.repositories.GroupRepository;
import ru.pavel.graduationProject.repositories.LearnerRepository;
import ru.pavel.graduationProject.repositories.TaskNameRepository;
import ru.pavel.graduationProject.services.SampleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class Calculation {

    List<Integer> chooseList;
    private final SampleService sampleService;
    private final GroupRepository groupRepository;
    private final TaskNameRepository taskNameRepository;
    private final LearnerRepository learnerRepository;
    private final Formulas formulas;
    private final NumberFormulas numberFormulas;
    private final RealFormulas realFormulas;
    private final BooleanFormulas booleanFormulas;
    private int checkResult = 0;
    private int needColum=0;



    public Calculation(SampleService sampleService, GroupRepository groupRepository, TaskNameRepository taskNameRepository, LearnerRepository learnerRepository, Formulas formulas, NumberFormulas numberFormulas, RealFormulas realFormulas, BooleanFormulas booleanFormulas) {
        this.sampleService = sampleService;
        this.groupRepository = groupRepository;
        this.taskNameRepository = taskNameRepository;
        this.learnerRepository = learnerRepository;
        this.formulas = formulas;
        this.numberFormulas = numberFormulas;
        this.realFormulas = realFormulas;
        this.booleanFormulas = booleanFormulas;
    }
    public void setScore(int score){formulas.setScoreNumber(score);}

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
    public List<Integer> getChooseList() {
        return chooseList;
    }
    public void checkResult(String UserChoose,int score) {
        formulas.setScoreNumber(score);
        realFormulas.getGroups().clear();
        booleanFormulas.getGroups().clear();
        numberFormulas.getGroup1CTF().clear();
        numberFormulas.getGroup1CTT().clear();
        numberFormulas.getGroup2CTF().clear();
        numberFormulas.getGroup2CTT().clear();
        this.chooseList = Stream.of(UserChoose.split(",", -1))
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
            realFormulas.addGroup(sampleService.findResultDouble(false, chooseList.get(0), chooseList.get(2)));
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
                numberFormulas.setGroup1CTF(sampleService.findResultInt(false, chooseList.get(0), chooseList.get(2)));
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
                    booleanFormulas.setGroups(sampleService.findResultBoolean(false, chooseList.get(0), chooseList.get(2)));
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
                    realFormulas.addGroup(sampleService.findResultDouble(false, chooseList.get(1), chooseList.get(2)));
                    if (!realFormulas.getGroups().get(2).isEmpty() && realFormulas.getGroups().get(2).get(realFormulas.getGroups().get(2).size() - 1) != null){
                        realFormulas.addGroup(sampleService.findResultDouble(true, chooseList.get(1), chooseList.get(2)));
                        if (!realFormulas.getGroups().get(3).isEmpty() && realFormulas.getGroups().get(3).get(realFormulas.getGroups().get(3).size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется double");
                            realFormulas.setDispersion(sampleService.getDispersionRealNumbers(false, chooseList.get(0), chooseList.get(2)));
                            realFormulas.setDispersion(sampleService.getDispersionRealNumbers(true, chooseList.get(0), chooseList.get(2)));
                            realFormulas.setDispersion(sampleService.getDispersionRealNumbers(false, chooseList.get(1), chooseList.get(2)));
                            realFormulas.setDispersion(sampleService.getDispersionRealNumbers(true, chooseList.get(1), chooseList.get(2)));
                            realFormulas.setCramerList(realFormulas.cramerFormulas(realFormulas.getGroups(),realFormulas.getDispersion()));
                            realFormulas.setVilkasList(realFormulas.vilksonFormuls(realFormulas.getGroups()));
                            realFormulas.setScore(realFormulas.conversionToOrdinalScale(realFormulas.getGroups(),formulas.getScoreNumber()));
                            realFormulas.setXiList(formulas.xi(realFormulas.getScore(),realFormulas.getGroups().get(1).size(),realFormulas.getGroups().get(3).size()));
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
                    numberFormulas.setGroup2CTF(sampleService.findResultInt(false, chooseList.get(1), chooseList.get(2)));
                    if (!numberFormulas.getGroup2CTF().isEmpty() && numberFormulas.getGroup2CTF().get(numberFormulas.getGroup2CTF().size() - 1) != null){
                        numberFormulas.setGroup2CTT(sampleService.findResultInt(true, chooseList.get(1), chooseList.get(2)));
                        if (!numberFormulas.getGroup2CTT().isEmpty() && numberFormulas.getGroup2CTT().get(numberFormulas.getGroup2CTF().size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется integer");
                            numberFormulas.setDispersionGroup1CTF(sampleService.getDispersion(false, chooseList.get(0), chooseList.get(2)));
                            numberFormulas.setDispersionGroup1CTT(sampleService.getDispersion(true, chooseList.get(0), chooseList.get(2)));
                            numberFormulas.setDispersionGroup2CTF(sampleService.getDispersion(false, chooseList.get(1), chooseList.get(2)));
                            numberFormulas.setDispersionGroup2CTT(sampleService.getDispersion(true, chooseList.get(1), chooseList.get(2)));
                            numberFormulas.setCramer1(numberFormulas.cramerFormuls(numberFormulas.getGroup1CTF(),numberFormulas.getGroup2CTF(),numberFormulas.getDispersionGroup1CTF(),numberFormulas.getDispersionGroup2CTF()));
                            numberFormulas.setCramer2(numberFormulas.cramerFormuls(numberFormulas.getGroup1CTT(),numberFormulas.getGroup2CTT(),numberFormulas.getDispersionGroup1CTT(),numberFormulas.getDispersionGroup2CTT()));
                            numberFormulas.setVilkas1(numberFormulas.vilksonFormuls(numberFormulas.getGroup1CTF(),numberFormulas.getGroup2CTF()));
                            numberFormulas.setVilkas2(numberFormulas.vilksonFormuls(numberFormulas.getGroup1CTT(),numberFormulas.getGroup2CTT()));
                            numberFormulas.setScore(formulas.conversionToOrdinalScale(numberFormulas.getGroup1CTF(),numberFormulas.getGroup2CTF(),numberFormulas.getGroup1CTT(),numberFormulas.getGroup2CTT(),formulas.getScoreNumber()));
                            numberFormulas.setXiResultList(numberFormulas.xi(numberFormulas.getScore(),numberFormulas.getGroup1CTT().size(),numberFormulas.getGroup2CTT().size()));
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
                    booleanFormulas.setGroups(sampleService.findResultBoolean(false, chooseList.get(1), chooseList.get(2)));
                    if (!booleanFormulas.getGroups().get(2).isEmpty() && booleanFormulas.getGroups().get(2).get(booleanFormulas.getGroups().get(2).size() - 1) != null){
                        booleanFormulas.setGroups(sampleService.findResultBoolean(true, chooseList.get(1), chooseList.get(2)));
                        if (!booleanFormulas.getGroups().get(3).isEmpty() && booleanFormulas.getGroups().get(3).get(booleanFormulas.getGroups().get(3).size() - 1) != null){
                            setCheckResult(0);
                            System.out.println("группа 2 используется boolean");
                            formulas.setScoreNumber(2);
                            booleanFormulas.setGroupsString(booleanFormulas.convertGroupToString(booleanFormulas.getGroups(),"Сдал","Не сдал"));
                            booleanFormulas.setScore(booleanFormulas.conversionToOrdinalScale(booleanFormulas.getGroups()));
                            booleanFormulas.setFisherResultList(formulas.fisherCritrery(booleanFormulas.getScore(),booleanFormulas.getGroups().get(0).size(),booleanFormulas.getGroups().get(2).size()));
                            booleanFormulas.setXiList(formulas.xi(booleanFormulas.getScore(),booleanFormulas.getGroups().get(0).size(),booleanFormulas.getGroups().get(2).size()));
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
        formulas.setFirstNameGroup1(sampleService.getLearnerFirstName(false, chooseList.get(0), chooseList.get(2)));
        formulas.setSecondNameGroup1(sampleService.getLearnerLastName(false, chooseList.get(0), chooseList.get(2)));
        formulas.setFirstNameGroup2(sampleService.getLearnerFirstName(false, chooseList.get(1), chooseList.get(2)));
        formulas.setSecondNameGroup2(sampleService.getLearnerLastName(false, chooseList.get(1), chooseList.get(2)));
        System.out.println("CheckResult="+getCheckResult());
        System.out.println("NeedColum="+getNeedColum());
    }
    public void addNewGroup(String newGroupName){
        if(groupRepository.findByName(newGroupName).isPresent()){
            System.out.println("Группа с названием " +newGroupName+ " существует");
        }
        else {groupRepository.saveNewGroup(newGroupName);}
    }
    public void deleteGroup(int id){
        groupRepository.deleteById(id);
    }
    public void addNewTask(String newTaskName){
        if (taskNameRepository.findByName(newTaskName).isPresent()){
            System.out.println("Задание с названием " +newTaskName+ " существует");
        }
        else {taskNameRepository.saveNewTask(newTaskName);}
    }
    public void deleteTask(int id){taskNameRepository.deleteById(id);}
    public void editGroupName(String newName,int id){
        if (groupRepository.findByName(newName).isPresent()){
            System.out.println("Группа с названием " +newName+ " существует");
        }
        else {
            groupRepository.updateGroupByName(newName,id);
        }
    }
    public void editTaskName(String newName,int id){
        if (taskNameRepository.findByName(newName).isPresent()){
            System.out.println("Задание с названием " +newName+ " существует");
        }
        else {
            taskNameRepository.updateTaskByName(newName,id);
        }
    }
    public List<Learner> getLearnerByGroupId(int id){
        return learnerRepository.findAllByGroup_Id(id);
    }
    public void deleteLearner(int id){learnerRepository.deleteLearnerById(id);}
    public Optional<Learner> findLearnerByFIO(String fn,String ln){return learnerRepository.findByFirstNameAndLastName(fn, ln);}
    public void editLearnerFIO(int id,String newName){
        if(newName.matches("[А-Я][а-яё]{2,15}\\s[А-ЯЁ][а-яё]{2,15}"))
        {
            String[] name=newName.split(" ");
            if (learnerRepository.findByFirstNameAndLastName(name[0],name[1]).isPresent()){
                System.out.println("Пользователь с таким ФИО уже есть");
            }
            else {
                learnerRepository.updateLearnerFIO(name[0],name[1],id);
            }
        }
        else {
           System.out.println("Ошибка ввода");
        }
    }
    public void addLearner(int id,String newName){
        if(newName.matches("[А-Я][а-яё]{2,15}\\s[А-ЯЁ][а-яё]{2,15}")){
            String[] name=newName.split(" ");
            if (learnerRepository.findByFirstNameAndLastName(name[0],name[1]).isPresent()){
                System.out.println("Пользователь с таким ФИО уже есть");
            }
            else {
                learnerRepository.saveLearnerFIO(name[0],name[1],id);
            }
        }
        else {
            System.out.println("Ошибка ввода");
        }
    }
    public List<List<String>> getStringResultAndFIOByTaskAndGroupId(int group,int task,List<Learner> learners) {
        List<List<String>> main = new ArrayList<>(2);
        List<Sample> samples = sampleService.getALLByTaskNameIdAndLearnerGroupId(task, group);
        List<String> before = new ArrayList<>();
        List<String> after = new ArrayList<>();
        for (Learner x : learners) {
            before.add("Нет данных");
            after.add("Нет данных");
        }
        if (!samples.isEmpty()) {
            int needColum = samples.get(0).getFloatingNumberField() != null ? 1 : samples.get(0).getNumberField() != null ? 2 : 3;
            for (int i = 0; i <= learners.size() - 1; i++) {
                for (int j = 0; j <= samples.size() - 1; j++) {
                    if (learners.get(i).getId() == samples.get(j).getLearner().getId()) {
                        switch (needColum) {
                            case (1):
                                if (!samples.get(j).isControlTask()) {
                                    before.set(i, samples.get(j).getFloatingNumberField().toString());
                                } else {
                                    after.set(i, samples.get(j).getFloatingNumberField().toString());
                                }
                                break;
                            case (2):
                                if (!samples.get(j).isControlTask()) {
                                    before.set(i, samples.get(j).getNumberField().toString());
                                } else {
                                    after.set(i, samples.get(j).getNumberField().toString());
                                }
                                break;
                            case (3):
                                if (!samples.get(j).isControlTask()) {
                                    before.set(i, samples.get(j).getCheckField().toString());
                                } else {
                                    after.set(i, samples.get(j).getCheckField().toString());
                                }
                                break;
                        }
                    }
                }
            }
        }
            main.add(before);
            main.add(after);
        return main;
    }
    public List<TaskName> getTaskNameById(int id){return taskNameRepository.getTaskNameById(id);}
    public String groupDifference(double averageG1,double averageG2){
        if (averageG1==averageG2){
            return "Различиый нет";
        }
        else if (averageG1<averageG2){
        int percent= (int) Math.round(100-(averageG1/averageG2*100));
        return "Эксперементальная группа лучше на ≈"+percent+" %";
        }
        else {
            int percent= (int) Math.round(100-(averageG2/averageG1*100));
            return "Контрольная группа лучше на ≈"+percent+" %";
        }
    }
    public List<String> groupStatus(int needColum){
        List<String> result=new ArrayList<>(2);
        int before=0;
        int after=0;
        if (needColum==1){
           if (realFormulas.getCramerList().get(0)<=1.96){before++;}
           if (realFormulas.getVilkasList().get(0)<=1.96){before++;}
           if (realFormulas.getXiCTF()<=formulas.getAlphaForXi().get(formulas.getScoreNumber()-2)){before++;}
           if (realFormulas.getFisherCTF()<=1.64&&formulas.getScoreNumber()==2){before++;}
           if (before==0){result.add("Состояния различны");}
           else if (formulas.getScoreNumber()==2){
               result.add("Состояния совпадают по "+before+" критериям из 4");
           } else {result.add("Состояния совпадают по "+before+" критериям из 3");}

           if (realFormulas.getCramerList().get(1)<=1.96){after++;}
           if (realFormulas.getVilkasList().get(1)<=1.96){after++;}
           if (realFormulas.getXiCTT()<=formulas.getAlphaForXi().get(formulas.getScoreNumber()-2)){after++;}
           if (realFormulas.getFisherCTT()<=1.64&&formulas.getScoreNumber()==2){after++;}
           if (after==0){result.add("Состояния различны");}
           else if (formulas.getScoreNumber()==2){
               result.add("Состояния совпадают по "+after+" критериям из 4");
           } else {result.add("Состояния совпадают по "+after+" критериям из 3");}
        }
        else if (needColum==2){
            if (numberFormulas.getCramer1()<=1.96){before++;}
            if (numberFormulas.getVilkas1()<=1.96){before++;}
            if (numberFormulas.getXiCTF()<=formulas.getAlphaForXi().get(formulas.getScoreNumber()-2)){before++;}
            if (numberFormulas.getFisherCTF()<=1.64&&formulas.getScoreNumber()==2){before++;}
            if (before==0){result.add("Состояния различны");}
            else if (formulas.getScoreNumber()==2){
                result.add("Состояния совпадают по "+before+" критериям из 4");
            } else {result.add("Состояния совпадают по "+before+" критериям из 3");}

            if (numberFormulas.getCramer2()<=1.96){after++;}
            System.out.println("1 "+after);
            if (numberFormulas.getVilkas2()<=1.96){after++;}
            System.out.println("2 "+after);
            if (numberFormulas.getXiCTT()<=formulas.getAlphaForXi().get(formulas.getScoreNumber()-2)){after++;}
            System.out.println("#3 "+after+" "+numberFormulas.getXiCTT()+"xi"+formulas.getAlphaForXi().get(formulas.getScoreNumber()-2));
            if (numberFormulas.getFisherCTT()<=1.64&&formulas.getScoreNumber()==2){after++;}
            System.out.println("4 "+after);
            if (after==0){result.add("Состояния различны");}
            else if (formulas.getScoreNumber()==2){
                result.add("Состояния совпадают по "+after+" критериям из 4");
            } else {result.add("Состояния совпадают по "+after+" критериям из 3");}
        }
        else {
            if (booleanFormulas.getXiCTF()<=formulas.getAlphaForXi().get(formulas.getScoreNumber()-2)){before++;}
            if (before==0){result.add("Состояния различны");}
            else if (booleanFormulas.getFisherCTF()<=1.64&&formulas.getScoreNumber()==2){before++;}
                result.add("Состояния совпадают по "+before+" критериям из 2");
            if (booleanFormulas.getXiCTT()<=formulas.getAlphaForXi().get(formulas.getScoreNumber()-2)){after++;}
            if (after==0){result.add("Состояния различны");}
            else if (booleanFormulas.getFisherCTT()<=1.64&&formulas.getScoreNumber()==2){after++;}
                result.add("Состояния совпадают по "+after+" критериям из 2");
        }
        return result;
    }
    public void addFloatSample(int id, double value, boolean ct,int taskName){sampleService.addFloatSample(id,value,ct,taskName);}
    public void addIntSample(int id, int value, boolean ct,int taskName){sampleService.addIntegerSample(id,value,ct,taskName);}
    public void addBooleanSample(int id, boolean value, boolean ct,int taskName){sampleService.addBooleanSample(id,value,ct,taskName);}
    public Optional<Sample> findSampleByLearnerIdGroupTask(int id,boolean bool,int taskId){return sampleService.findByLearnerIdAndControlTaskAndTaskNameId(id,bool,taskId);}
    public void updateSampleDouble(double floatData,long sampleId){sampleService.updateSampleDouble(floatData,sampleId);}
    public void updateSampleInt(int integerData,long sampleId){sampleService.updateSampleInteger(integerData,sampleId);}
    public void updateSampleBool(boolean boolData,long sampleId){sampleService.updateSampleBoolean(boolData,sampleId);}
}