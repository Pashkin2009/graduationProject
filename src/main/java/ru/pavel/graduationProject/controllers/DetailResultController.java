package ru.pavel.graduationProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.pavel.graduationProject.util.*;

import java.util.List;


@Controller
@RequestMapping("/result")
public class DetailResultController {
    private final Calculation calculation;
    private final NumberFormulas numberFormulas;
    private final RealFormulas realFormulas;
    private final BooleanFormulas booleanFormulas;
    private final Formulas formulas;

    @Autowired
    public DetailResultController(Calculation calculation, NumberFormulas numberFormulas, RealFormulas realFormulas, BooleanFormulas booleanFormulas, Formulas formulas) {
        this.calculation = calculation;
        this.numberFormulas=numberFormulas;
        this.realFormulas = realFormulas;
        this.booleanFormulas = booleanFormulas;
        this.formulas = formulas;
    }

    @GetMapping("/detail")
    public String detailResultPage(Model model)
    {
        if (calculation.getNeedColum()==1){
            System.out.println("Используется колонка double resultPage");
            model.addAttribute("F",formulas);
            model.addAttribute("RF",realFormulas);
            System.out.println(realFormulas.conversionToOrdinalScale(realFormulas.getGroups(),3));
            return "detailResultPageRF";
        }
        else if (calculation.getNeedColum()==2)
        {   System.out.println("Используется колонка integer resultPage");
            model.addAttribute("F",formulas);
            model.addAttribute("NF",numberFormulas);
            return "detailResultPageNF";
        }
        else {
            System.out.println("Используется колонка boolean resultPage");
            model.addAttribute("F",formulas);
            model.addAttribute("BF",booleanFormulas);
            System.out.println(booleanFormulas.getScore().toString());
            return "detailResultPageBF";
        }
    }
}