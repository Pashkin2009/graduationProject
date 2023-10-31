package ru.pavel.graduationProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.pavel.graduationProject.models.Learner;
import ru.pavel.graduationProject.services.GroupService;
import ru.pavel.graduationProject.services.TaskNameService;
import ru.pavel.graduationProject.util.Calculation;
import ru.pavel.graduationProject.util.WorkingWithFiles;

import java.io.*;
import java.util.Arrays;
import java.util.List;

@Controller
public class EditController {
GroupService groupService;
TaskNameService taskNameService;
Calculation calculation;
WorkingWithFiles workingWithFiles;

    public EditController(GroupService groupService, TaskNameService taskNameService, Calculation calculation,WorkingWithFiles workingWithFiles) {
        this.groupService = groupService;
        this.taskNameService = taskNameService;
        this.calculation = calculation;
        this.workingWithFiles=workingWithFiles;
    }

    @GetMapping("/edit")
    public String editPage(Model model){
        model.addAttribute("allGroup",groupService.findAll());
        model.addAttribute("allTask",taskNameService.findAll());
        workingWithFiles.getError().clear();
        return "edit";
    }
    @GetMapping("edit/group/{groupId}")
    public String editGroupPage(Model model,@PathVariable int groupId){
        model.addAttribute("needGroup",groupId);
        model.addAttribute("learners",calculation.getLearnerByGroupId(groupId));
        workingWithFiles.getError().clear();
        return "editGroup";
    }
    @PostMapping("/edit/group/name")
    public String changeGroupName(@RequestParam("groupName") String name,
                                  @RequestParam("groupId") int id){
        calculation.editGroupName(name,id);
        return "redirect:/edit";
    }
    @PostMapping("/edit/group/delete")
    public String deleteGroup(@RequestParam("groupId") int id){
        calculation.deleteGroup(id);
        return "redirect:/edit";
    }
    @PostMapping("/edit/task/name")
    public String changeTaskName(@RequestParam("taskName") String name,
                                 @RequestParam("taskId") int id){
        calculation.editTaskName(name,id);
        return "redirect:/edit";
    }
    @PostMapping("/edit/task/delete")
    public String deleteTask(@RequestParam("taskId") int id){
        calculation.deleteTask(id);
        return "redirect:/edit";
    }
    @PostMapping("/edit/group/add")
    public String addGroup(@RequestParam("newGroupName") String name){
        calculation.addNewGroup(name);
        return "redirect:/edit";
    }
    @PostMapping("/edit/task/add")
    public String addTask(@RequestParam("newTaskName") String name){
        calculation.addNewTask(name);
        return "redirect:/edit";
    }

    @PostMapping("/edit/group/{groupId}/learner/delete")
    public String deleteLearner(@RequestParam("learnerId")int id,@PathVariable int groupId){
        calculation.deleteLearner(id);
        return "redirect:/edit/group/"+groupId+"?groupId="+groupId;
    }
    @PostMapping("/edit/group/{groupId}/learner/edit")
    public String editLearnerFIO(@RequestParam("learnerId")int id,
                                 @RequestParam("FIO")String FIO,
                                 @PathVariable int groupId){
        calculation.editLearnerFIO(id,FIO);
        return "redirect:/edit/group/"+groupId+"?groupId="+groupId;
    }

    @PostMapping("/edit/group/{groupId}/learner/add")
    public String addLearner(@RequestParam("FIO")String FIO,
                             @PathVariable int groupId){
        calculation.addLearner(groupId,FIO);
        return "redirect:/edit/group/"+groupId+"?groupId="+groupId;
    }

    @GetMapping("edit/task/result")
    public String editTaskResult(Model model,
                                 @RequestParam("groupName") int groupId,
                                 @RequestParam("taskName") int taskId){
        System.out.println("group= "+groupId);
        System.out.println("task= "+taskId);
        List<Learner> learners=calculation.getLearnerByGroupId(groupId);
        model.addAttribute("groupName",groupId);
        model.addAttribute("taskName",taskId);
        model.addAttribute("FIO",learners);
        model.addAttribute("allResult",calculation.getStringResultAndFIOByTaskAndGroupId(groupId,taskId,learners));
        model.addAttribute("error",workingWithFiles.getError());
        return "editResult";
    }


    @PostMapping("/edit/task/result/edit")
    public String editTaskResult(@RequestParam("before[]")String[] before,
                                 @RequestParam("after[]")String[] after,
                                 @RequestParam("groupName")int groupName,
                                 @RequestParam("taskName")int taskName){
        System.out.println(Arrays.toString(before)+" размер"+before.length);
        System.out.println(Arrays.toString(after)+" размер"+after.length);
        return "redirect:/edit/task/result?groupName="+groupName+"&taskName="+taskName;
    }

    @PostMapping(value = "/edit/task/result/file")
    public String fileResult(@RequestParam("groupName")int groupName,
                             @RequestParam("taskName")int taskName,
                             @RequestParam("file") MultipartFile file) throws IOException {
        workingWithFiles.addResultFromFile(file,taskName);
        return "redirect:/edit/task/result?groupName="+groupName+"&taskName="+taskName;
    }
}