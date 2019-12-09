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
import java.util.*;


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

        model.addAttribute("title", "Select ingredients to search for");

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

        //Convert String to List//
        ArrayList<String> ingredientsList = new ArrayList<String>(Arrays.asList(ingredients.split(",")));

        ArrayList<Drink> searchResults= searchSelectedIngredients(ingredientsList);

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
                Integer ingredientId = Integer.parseInt(ingredientPair.getKey());
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

    public ArrayList<Drink> searchSelectedIngredients(List<String> ingredientsList){
        ArrayList<Drink> results = new ArrayList<>();
        for (Drink drink : drinkDao.findAll()) {
            boolean match = false;
            for (String ingredient : ingredientsList) {
                if (!isNumeric(ingredient)){
                    if (drinkContainsIngredientType(ingredient, drink)){
                        match = true;
                    }
                }else if (drink.getRecipe().containsKey(ingredient)){
                    match = true;
                }
            }
            if (match){
                results.add(drink);
            }
        }
        return results;
    }

    public boolean drinkContainsIngredientType(String typeString, Drink drink){
        boolean match = false;

        Ingredient.Type type = convertTypeStringToTypeClass(typeString);

        for (String id : drink.getRecipe().keySet()){
            Ingredient ingredient = ingredientDao.findById(Integer.parseInt(id)).get();
            if (ingredient.getType() == type){
                match = true;
            }
        }
        return match;
    }

    public Ingredient.Type  convertTypeStringToTypeClass (String typeString){
        Ingredient.Type type;
        switch (typeString){
            default:
            case "vodka":
                type = Ingredient.Type.Vodka;
                break;
            case "gin":
                type = Ingredient.Type.Gin;
                break;
            case "rum":
                type = Ingredient.Type.Rum;
                break;
            case "tequila":
                type = Ingredient.Type.Tequila;
                break;
            case "whiskey":
                type = Ingredient.Type.Whiskey;
                break;
            case "cordial":
                type = Ingredient.Type.Cordial;
                break;
            case "wine":
                type = Ingredient.Type.Wine;
                break;
            case "bitters":
                type = Ingredient.Type.Bitters;
                break;
            case "mixer":
                type = Ingredient.Type.Mixer;
                break;
        }
        return type;
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

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
