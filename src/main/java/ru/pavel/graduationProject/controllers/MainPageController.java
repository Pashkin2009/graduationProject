package ru.pavel.graduationProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.pavel.graduationProject.models.Group;
import ru.pavel.graduationProject.models.TaskName;
import ru.pavel.graduationProject.services.GroupService;
import ru.pavel.graduationProject.services.TaskNameService;
import ru.pavel.graduationProject.util.Calculation;
import ru.pavel.graduationProject.util.Formulas;


@Controller
@RequestMapping("/main")
public class MainPageController {
    private final GroupService groupService;
    private final TaskNameService taskNameService;
    private final Calculation calculation;
    private final Formulas formulas;

    public MainPageController(GroupService groupService,
                              TaskNameService taskNameService,
                              Calculation calculation, Formulas formulas)
    {
        this.groupService = groupService;
        this.taskNameService = taskNameService;
        this.calculation = calculation;
        this.formulas = formulas;
    }

@GetMapping
    public String mainSelect(Model model,
                             @ModelAttribute("group1") Group group1,
                             @ModelAttribute("group2") Group group2,
                             @ModelAttribute("taskName") TaskName taskName
                            ){
        model.addAttribute("check",calculation.getCheckResult());
        model.addAttribute("groups1",groupService.findAll());
        model.addAttribute("groups2",groupService.findAll());
        model.addAttribute("taskNames", taskNameService.findAll());
        return "mainPage";
}
@PostMapping("/select")
public String mainConfirm(@RequestParam("selectScore") int score,
                          @RequestParam("id") String id) {
        calculation.checkResult(id,score);
        if (calculation.getCheckResult()==0)
        {
            System.out.println("проверки выполненны");
            return "redirect:/result";
        }
        return "redirect:/main";
}
}