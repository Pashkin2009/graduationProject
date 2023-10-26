package ru.pavel.graduationProject.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.pavel.graduationProject.util.*;



@Controller
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
    @GetMapping("/result")
    public String resultPage(Model model)
    {
        model.addAttribute("taskName",calculation.getTaskNameById(calculation.getChooseList().get(2)));
        System.out.println("need="+calculation.getNeedColum());
        model.addAttribute("groupStatus",calculation.groupStatus(calculation.getNeedColum()));
        if (calculation.getNeedColum()==1){
            double average1=formulas.average(realFormulas.getGroups().get(1));
            double average2=formulas.average(realFormulas.getGroups().get(3));
            model.addAttribute("average1",average1);
            model.addAttribute("average2",average2);
            model.addAttribute("difference",calculation.groupDifference(average1,average2));
        }
        else if (calculation.getNeedColum()==2){
            double average1=formulas.averageInt(numberFormulas.getGroup1CTT());
            double average2=formulas.averageInt(numberFormulas.getGroup2CTT());
            model.addAttribute("average1",average1);
            model.addAttribute("average2",average2);
            model.addAttribute("difference",calculation.groupDifference(average1,average2));
        }
        else {
            double average1= (double) booleanFormulas.getScore().get(1).get(1) /booleanFormulas.getGroups().get(1).size();
            double average2= (double) booleanFormulas.getScore().get(1).get(3) /booleanFormulas.getGroups().get(3).size();
            model.addAttribute("average1",average1);
            model.addAttribute("average2",average2);
            model.addAttribute("difference",calculation.groupDifference(average1,average2));
        }
        return "resultPage";
    }

    @GetMapping("/result/detail")
    public String detailResultPage(Model model)
    {
        if (calculation.getNeedColum()==1){
            System.out.println("Используется колонка double resultPage");
            model.addAttribute("F",formulas);
            model.addAttribute("RF",realFormulas);
            System.out.println(realFormulas.getXiList().toString());
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
            return "detailResultPageBF";
        }
    }
}