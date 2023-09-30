package ru.pavel.graduationProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pavel.graduationProject.util.Calculation;
import ru.pavel.graduationProject.util.NumberFormulas;




@Controller
@RequestMapping("/result")
public class DetailResultController {
    private final Calculation calculation;
    private final NumberFormulas numberFormulas;

    @Autowired
    public DetailResultController(Calculation calculation,NumberFormulas numberFormulas) {
        this.calculation = calculation;
        this.numberFormulas=numberFormulas;
    }

    @GetMapping("/detail")
    public String detailResultPage(Model model)
    {
        if (calculation.getNeedColum()==1){
            System.out.println("Используется колонка double");
           return "redirect:/main";
        }
        else if (calculation.getNeedColum()==2)
        {   System.out.println("Используется колонка integer");
            //numberFormulas.setIntegerList();
            model.addAttribute("NF",numberFormulas);
            return "detailResultPage";
        }
        else {
            System.out.println("Используется колонка boolean");
            return "redirect:/main";
        }
    }
}
