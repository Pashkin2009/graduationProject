package ru.pavel.graduationProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pavel.graduationProject.models.Group;
import ru.pavel.graduationProject.models.TaskName;
import ru.pavel.graduationProject.services.GroupService;
import ru.pavel.graduationProject.services.TaskNameService;
import ru.pavel.graduationProject.util.Calculation;

@Controller
@RequestMapping("/main")
public class MainPageController {
    private final GroupService groupService;
    private final TaskNameService taskNameService;
    private final Calculation calculation;



    @Autowired
    public MainPageController(GroupService groupService, TaskNameService taskNameService, Calculation calculation) {
        this.groupService = groupService;
        this.taskNameService = taskNameService;
        this.calculation = calculation;
    }

@GetMapping
    public String mainSelect(Model model,
                             @ModelAttribute("group1") Group group1,
                             @ModelAttribute("group2") Group group2,
                             @ModelAttribute("taskName") TaskName taskName){
        model.addAttribute("groups1",groupService.findAll());
        model.addAttribute("groups2",groupService.findAll());
        model.addAttribute("taskNames", taskNameService.findAll());
        return "mainPage";
}
@PostMapping("/select")
public String mainConfirm(@RequestParam("id") String id){
        calculation.testCalculation(calculation.setMainStr(id));
        System.out.println("Request param: "+id);
        return "redirect:/main";
}
}