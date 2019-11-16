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

    public String myBarSearch (Model model, HttpServletRequest request, @RequestParam String ingredientNumber){
        HttpSession session=request.getSession(false);
        String name = (String)session.getAttribute("name");
        User user = findByName(name);
        ArrayList<Drink> searchResults = searchMyBar(user, Integer.parseInt(ingredientNumber));

        model.addAttribute("title", "Search Using 'My Bar' Ingredients");
        model.addAttribute("searchResults", searchResults);
        return "search/mybar";
    }

    public ArrayList<Drink> searchMyBar (User user, int ingredientNumber){
        ArrayList<Drink> searchResults = new ArrayList<>();

        //loop through each Drink Recipe
        for (Drink drink : drinkDao.findAll()){
            int badIngredientCounter = 0;
            boolean matchedDrink = true;
            //loop through each ingredient in current Drink iteration
            for (Map.Entry<String,String> ingredientPair : drink.getRecipe().entrySet()){
                Integer ingredientId = Integer.parseInt(ingredientPair.getValue());
                Ingredient ingredient = ingredientDao.findById(ingredientId).get();
                //count ingredients not found in My Bar
                if (!user.getMyBar().contains(ingredient)){
                    badIngredientCounter ++;
                    if(badIngredientCounter > ingredientNumber){
                        matchedDrink = false;
                        break;
                    }
                }
            }
            if (matchedDrink == true){
                searchResults.add(drink);
            }
        }
        return searchResults;
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
