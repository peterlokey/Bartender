package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.Ingredient;
import org.launchcode.Bartender.models.data.IngredientDao;
import org.launchcode.Bartender.models.data.IngredientTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "ingredient")
public class IngredientController {

    @Autowired
    private IngredientDao ingredientDao;
    private IngredientTypeDao ingredientTypeDao;

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addIngredient (Model model) {
        model.addAttribute("ingredient", new Ingredient());
        model.addAttribute("types", ingredientTypeDao.findAll());
        model.addAttribute("title", "Add New Ingredient");

        return "ingredient/add";
    }

}
