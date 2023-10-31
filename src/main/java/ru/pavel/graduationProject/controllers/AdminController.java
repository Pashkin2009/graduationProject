package ru.pavel.graduationProject.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pavel.graduationProject.models.Person;
import ru.pavel.graduationProject.repositories.PeopleRepository;
import ru.pavel.graduationProject.services.PersonDetailsService;
import ru.pavel.graduationProject.services.RegistrationService;
import ru.pavel.graduationProject.util.PersonValidator;

import javax.validation.Valid;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;
    private final PeopleRepository peopleRepository;
    private final PersonDetailsService personDetailsService;

    @Autowired
    public AdminController(PersonValidator personValidator, RegistrationService registrationService, PeopleRepository peopleRepository, PersonDetailsService personDetailsService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
        this.peopleRepository = peopleRepository;
        this.personDetailsService = personDetailsService;
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("Person") Person person){
        return "admin/registration_page";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("Person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) return "admin/registration_page";
        registrationService.register(person);
        return "redirect:/authorization/login";
    }
    @GetMapping("/edit")
    public String editUser(Model model,@ModelAttribute("person") Person person){
        model.addAttribute("people",peopleRepository.findAll());
        return "admin/edit_page";}
    @PostMapping("/edit")
    public String editUserProcess(@ModelAttribute("Person") Person person){
        personDetailsService.updateUsernameAndRole(person);
        return "redirect:/admin/edit";
    }
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") int id){
        System.out.println(id);
        peopleRepository.deleteById(id);
        return "redirect:/admin/edit";
    }
}
