package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.Drink;
import org.launchcode.Bartender.models.data.DrinkDao;
import org.launchcode.Bartender.models.data.IngredientDao;
import org.launchcode.Bartender.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;

@Controller
@RequestMapping(value = "search")
public class SearchController {
    @Autowired
    private DrinkDao drinkDao;
    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String search (Model model){

        model.addAttribute("title", "Search");
        return "search/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String search (Model model, @RequestParam String searchTerm){

        ArrayList<Drink> searchResults = new ArrayList<>();
        for (Drink drink : drinkDao.findAll()){
            if (drink.getName().toLowerCase().contains(searchTerm.toLowerCase())){
                searchResults.add(drink);
            }
        }
        model.addAttribute("searchResults", searchResults);
        return "search/index";
    }
}
