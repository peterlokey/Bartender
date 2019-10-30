package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.Ingredient;
import org.launchcode.Bartender.models.data.IngredientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "ingredient")
public class IngredientController {

    @Autowired
    private IngredientDao ingredientDao;


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addIngredient (Model model) {
        Ingredient.Type[] typeList = org.launchcode.Bartender.models.Ingredient.Type.values();
        model.addAttribute("typeList", typeList);
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("title", "Add New Ingredient");

        return "ingredient/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addIngredient (Model model, @Valid @ModelAttribute Ingredient ingredient,
                                Errors errors) {

        if (errors.hasErrors()) {
            Ingredient.Type[] typeList = org.launchcode.Bartender.models.Ingredient.Type.values();
            model.addAttribute("typeList", typeList);
            model.addAttribute("title", "Add New Ingredient");
            return "ingredient/add";
        }

        //TODO: Check for existing ingredient entry
        ingredientDao.save(ingredient);
        model.addAttribute("ingredientList", ingredientDao.findAll());

        return "ingredient/index";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index (Model model) {
        model.addAttribute("ingredientList", ingredientDao.findAll());
        return "ingredient/index";
    }
}
