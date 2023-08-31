package ru.pavel.graduationProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pavel.graduationProject.models.Person;
import ru.pavel.graduationProject.services.RegistrationService;
import ru.pavel.graduationProject.util.PersonValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/authorization")
public class AuthorizationController {
    private final PersonValidator personValidator;
    private final RegistrationService registrationService;


    @Autowired
    public AuthorizationController(PersonValidator personValidator, RegistrationService registrationService) {
        this.personValidator = personValidator;
        this.registrationService = registrationService;
    }

    @GetMapping("/login")
    public String login(){
        return "authorization/login_page";
    }

    @GetMapping("/registration")
    public String registration(@ModelAttribute("Person")Person person){
        return "authorization/registration_page";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("Person") @Valid Person person, BindingResult bindingResult){
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors()) return "authorization/registration_page";
        registrationService.register(person);
    return "redirect:/authorization/login";
    }
}
