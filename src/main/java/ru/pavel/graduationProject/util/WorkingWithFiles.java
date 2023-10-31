package ru.pavel.graduationProject.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.pavel.graduationProject.models.Learner;
import ru.pavel.graduationProject.models.Sample;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Component
public class WorkingWithFiles {
    Calculation calculation;
    private List<String> error;

    public WorkingWithFiles(Calculation calculation, List<String> error) {
        this.calculation = calculation;
        this.error = error;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }

    private int checkData(String data){
        if (data.contains(".")||data.contains(",")){return 1;}
        else if(data.equals("false")||data.equals("true")){return 3;}
        else if (data.matches("\\d++")){return 2;}
        else {return 0;}
    }
    private void addResult(int colum,boolean CT,int learnerId,int taskId,String data){
        Optional<Sample> sample=calculation.findSampleByLearnerIdGroupTask(learnerId,CT,taskId);
        if (colum==1){
            if (!(Double.parseDouble(data) <0.0)) {
                if (sample.isPresent()) {
                    if (sample.get().getFloatingNumberField() != Double.parseDouble(data)) {
                        calculation.updateSampleDouble(Double.parseDouble(data), sample.get().getId());
                    }
                } else {
                    calculation.addFloatSample(learnerId, Double.parseDouble(data), CT, taskId);
                }
            } else {error.add("Значение меньше нуля "+data);
                System.out.println("Значение меньше нуля");}
        }
        else if (colum==2){
            if (!(Integer.parseInt(data)<0)) {
            if (sample.isPresent()){
                if (sample.get().getNumberField()!=Integer.parseInt(data)){
                calculation.updateSampleInt(Integer.parseInt(data),sample.get().getId());}
            }
            else {
                calculation.addIntSample(learnerId,Integer.parseInt(data),CT,taskId);
            }}
            else {error.add("Значение меньше нуля "+data);
                System.out.println("Значение меньше нуля");}
        }
        else if (colum==3){
            if (sample.isPresent()){
                if (sample.get().getCheckField()!=Boolean.parseBoolean(data)){
                calculation.updateSampleBool(Boolean.parseBoolean(data),sample.get().getId());}
            }
            else {
                calculation.addBooleanSample(learnerId,Boolean.parseBoolean(data),CT,taskId);
            }
        }
    }
    public void addResultFromFile(@NotNull MultipartFile file, int taskName) throws IOException {
        error.clear();
        if (Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")&&!file.isEmpty()) {
            File textFile = File.createTempFile("text", ".tmp");
            file.transferTo(textFile);
            try {
                FileReader fr = new FileReader(textFile);
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    String[] data = line.split("[\t ;]");
                    if (data.length != 4) {
                        System.out.println("Ошибка формата строки");
                        error.add("Ошибка формата строки: "+line);
                    } else {
                        Optional<Learner> learner = calculation.findLearnerByFIO(data[0], data[1]);
                        if (learner.isPresent()) {
                            if (!data[2].equals("@") && !data[3].equals("@")) {
                                if (checkData(data[2]) == 1 && checkData(data[3]) == 1) {
                                    data[2] = data[2].replace(",", ".");
                                    data[3] = data[3].replace(",", ".");
                                    addResult(1, false, learner.get().getId(), taskName, data[2]);
                                    addResult(1, true, learner.get().getId(), taskName, data[3]);
                                } else if (checkData(data[2]) == 2 && checkData(data[3]) == 2) {
                                    addResult(2, false, learner.get().getId(), taskName, data[2]);
                                    addResult(2, true, learner.get().getId(), taskName, data[3]);
                                } else if (checkData(data[2]) == 3 && checkData(data[3]) == 3) {
                                    addResult(3, false, learner.get().getId(), taskName, data[2]);
                                    addResult(3, true, learner.get().getId(), taskName, data[3]);
                                } else {
                                    System.out.println("Неверный формат данных");
                                    error.add("Ошибка формата строки: "+line);
                                }
                            } else if (data[3].equals("@")) {
                                if (checkData(data[2]) == 1) {
                                    data[2] = data[2].replace(",", ".");
                                    addResult(1, false, learner.get().getId(), taskName, data[2]);
                                } else if (checkData(data[2]) == 2) {
                                    addResult(2, false, learner.get().getId(), taskName, data[2]);
                                } else if (checkData(data[2]) == 3) {
                                    addResult(3, false, learner.get().getId(), taskName, data[2]);
                                } else {
                                    System.out.println("Неверный формат данных");
                                    error.add("Ошибка формата строки: "+line);
                                }
                            } else {
                                if (checkData(data[3]) == 1) {
                                    data[3] = data[3].replace(",", ".");
                                    addResult(1, true, learner.get().getId(), taskName, data[3]);
                                } else if (checkData(data[3]) == 2) {
                                    addResult(2, true, learner.get().getId(), taskName, data[3]);
                                } else if (checkData(data[3]) == 3) {
                                    addResult(3, true, learner.get().getId(), taskName, data[3]);
                                } else {
                                    System.out.println("Неверный формат данных");
                                    error.add("Ошибка формата строки: "+line);
                                }
                            }
                        } else {
                            System.out.println("Пользователь с таким фио не найден " + data[0] + " " + data[1]);
                            error.add("Пользователь с таким фио не найден " + data[0] + " " + data[1]);
                        }
                    }
                    line = br.readLine();
                }
                fr.close();
                br.close();
                textFile.delete();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {System.out.println(file.getOriginalFilename());
               System.out.println("Ошибка расширения");
            error.add("Ошибка расширения");
        }
    }
}