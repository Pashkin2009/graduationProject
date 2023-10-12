package ru.pavel.graduationProject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.pavel.graduationProject.services.GroupService;


@Controller
public class EditController {
GroupService groupService;

    public EditController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping("/edit")
    public String editPage(Model model,String groupName){
        model.addAttribute("allGroup",groupService.findAll());
        model.addAttribute("groupName",groupName);
        return "edit";
    }
    @PostMapping("/edit/group/name")
    public String changeGroupName(@RequestParam("groupName") String name){
        System.out.println("Имя группы "+name);
        return "redirect:/edit";
    }
    @PostMapping("/edit/group/delete")
    public String deleteGroup(@RequestParam("groupId") int id){
        System.out.println("ID группы "+id);
        return "redirect:/edit";
    }
}
