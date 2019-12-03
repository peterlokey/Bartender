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


import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

//TODO: Create a sortMyBar function to return a sorted list of "MyBar" ingredients (by type? by name?)

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

    @RequestMapping(value = "select-ingredients", method = RequestMethod.GET)
    public String selectIngredients (Model model, HttpServletRequest request){
        HttpSession session=request.getSession(false);
        String name = (String)session.getAttribute("name");
        User user = findByName(name);
        model.addAttribute("title", "Select ingredients to search for");
        /*model.addAttribute("myBar", user.getMyBar());*/
        model.addAttribute("vodkaList", generateTypeList(Ingredient.Type.Vodka, ingredientDao.findAll()));
        model.addAttribute("ginList", generateTypeList(Ingredient.Type.Gin, ingredientDao.findAll()));
        model.addAttribute("rumList", generateTypeList(Ingredient.Type.Rum, ingredientDao.findAll()));
        model.addAttribute("tequilaList", generateTypeList(Ingredient.Type.Tequila, ingredientDao.findAll()));
        model.addAttribute("whiskeyList", generateTypeList(Ingredient.Type.Whiskey, ingredientDao.findAll()));
        model.addAttribute("wineList", generateTypeList(Ingredient.Type.Wine, ingredientDao.findAll()));
        model.addAttribute("bittersList", generateTypeList(Ingredient.Type.Bitters, ingredientDao.findAll()));
        model.addAttribute("mixerList", generateTypeList(Ingredient.Type.Mixer, ingredientDao.findAll()));
        model.addAttribute("garnishList", generateTypeList(Ingredient.Type.Garnish, ingredientDao.findAll()));
        return "search/select-ingredients";
    }

    @RequestMapping(value = "select-ingredients", method = RequestMethod.POST)
    public String selectIngredients (Model model, @RequestParam String ingredients){

        String[] ingredientsArray = ingredients.split(",");
        ArrayList<Drink> searchResults = searchSpecificIngredients(ingredientsArray);

        model.addAttribute("searchResults", searchResults);
        return "search/index";
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

    public ArrayList<Drink> searchSpecificIngredients(String[] ingredientIds){
        ArrayList<Drink> results = new ArrayList<>();
        for (Drink drink : drinkDao.findAll()) {
            boolean match = true;
            for (String id : ingredientIds) {
                if (!drink.getRecipe().containsValue(id)){
                    match = false;
                }
            }
            if (match == true){
                results.add(drink);
            }
        }
        return results;
    }

    public List<Ingredient> generateTypeList(Ingredient.Type type, Iterable<Ingredient> ingredients){

        List<Ingredient> typeList = new ArrayList<Ingredient>();
        for (Ingredient i : ingredients){
            if (i.getType().equals(type)){
                typeList.add(i);
            }
        }
        return typeList;
    }
}
