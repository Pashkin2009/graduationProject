package ru.pavel.graduationProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pavel.graduationProject.services.SampleService;
import ru.pavel.graduationProject.util.Calculation;

import java.util.List;


@Controller
@RequestMapping("/result")
public class DetailResultController {
    private final Calculation calculation;
    private final SampleService sampleService;
    @Autowired
    public DetailResultController(Calculation calculation, SampleService sampleService) {
        this.calculation = calculation;
        this.sampleService = sampleService;
    }

    @GetMapping("/detail")
    public String detailResultPage(Model model)
    {
        List<Integer> listIt=calculation.getListIt();
        if (calculation.getNeedColum()==1){
            System.out.println("Используется колонка double");
           return "redirect:/main";
        }
        else if (calculation.getNeedColum()==2)
        {   System.out.println("Используется колонка integer");
            model.addAttribute("studentNameG1",sampleService.getLearnerFirstName(true,listIt.get(0),listIt.get(2)));
            model.addAttribute("studentLastnameG1",sampleService.getLearnerLastName(true,listIt.get(0),listIt.get(2)));
            model.addAttribute("studentNameG2",sampleService.getLearnerFirstName(true,listIt.get(1),listIt.get(2)));
            model.addAttribute("studentLastnameG2",sampleService.getLearnerLastName(true,listIt.get(1),listIt.get(2)));
            model.addAttribute("dataListG1CT",sampleService.findResultInt(true,listIt.get(0),listIt.get(2)));
            model.addAttribute("dataListG2CT",sampleService.findResultInt(true,listIt.get(1),listIt.get(2)));
            model.addAttribute("dataListG1CF",sampleService.findResultInt(false,listIt.get(0),listIt.get(2)));
            model.addAttribute("dataListG2CF",sampleService.findResultInt(false,listIt.get(1),listIt.get(2)));
            return "detailResultPage";
        }
        else {
            System.out.println("Используется колонка boolean");
            return "redirect:/main";
        }
    }
}
