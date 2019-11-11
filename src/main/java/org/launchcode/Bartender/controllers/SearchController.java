package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.Drink;
import org.launchcode.Bartender.models.Ingredient;
import org.launchcode.Bartender.models.User;
import org.launchcode.Bartender.models.data.DrinkDao;
import org.launchcode.Bartender.models.data.IngredientDao;
import org.launchcode.Bartender.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

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

    @RequestMapping(value = "mybar")
    public String myBarSearch (Model model, HttpServletRequest request){
        HttpSession session=request.getSession(false);
        String name = (String)session.getAttribute("name");
        User user = findByName(name);
        ArrayList<Drink> searchResults = new ArrayList<>();

        for (Drink drink : drinkDao.findAll()){
            boolean matchedDrink = true;
            for (Map.Entry<String,String> ingredientPair : drink.getRecipe().entrySet()){
                Integer ingredientId = Integer.parseInt(ingredientPair.getValue());
                Ingredient ingredient = ingredientDao.findById(ingredientId).get();
                if (!user.getMyBar().contains(ingredient)){
                    matchedDrink = false;
                    break;
                }
            }
            if (matchedDrink == true){
                searchResults.add(drink);
            }
        }

        model.addAttribute("searchResults", searchResults);
        return "search/index";
    }

    public User findByName(String name){

        for (User user : userDao.findAll()){
            if(user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }
}