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
public class AuthorizationController {

    @GetMapping("/authorization/login")
    public String login(){
        return "authorization/login_page";
    }

}
