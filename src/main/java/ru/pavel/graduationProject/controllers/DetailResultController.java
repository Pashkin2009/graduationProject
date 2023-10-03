package ru.pavel.graduationProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pavel.graduationProject.util.Calculation;
import ru.pavel.graduationProject.util.Formulas;
import ru.pavel.graduationProject.util.NumberFormulas;

import java.util.Arrays;


@Controller
@RequestMapping("/result")
public class DetailResultController {
    private final Calculation calculation;
    private final NumberFormulas numberFormulas;
    private final Formulas formulas;

    @Autowired
    public DetailResultController(Calculation calculation, NumberFormulas numberFormulas, Formulas formulas) {
        this.calculation = calculation;
        this.numberFormulas=numberFormulas;
        this.formulas = formulas;
    }

    @GetMapping("/detail")
    public String detailResultPage(Model model)
    {
        if (calculation.getNeedColum()==1){
            System.out.println("Используется колонка double resultPage");
           return "redirect:/main";
        }
        else if (calculation.getNeedColum()==2)
        {   System.out.println("Используется колонка integer resultPage");
            model.addAttribute("F",formulas);
            model.addAttribute("NF",numberFormulas);
            System.out.println(Arrays.toString(formulas.conversionToOrdinalScale(numberFormulas.getGroup2CTF(),3).toArray()));
            System.out.println(Arrays.toString(formulas.conversionToOrdinalScale(numberFormulas.getGroup2CTF(),3,5,20).toArray()));
            return "detailResultPage";
        }
        else {
            System.out.println("Используется колонка boolean resultPage");
            return "redirect:/main";
        }
    }
}
