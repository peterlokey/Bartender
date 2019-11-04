package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.Drink;
import org.launchcode.Bartender.models.Recipe;
import org.launchcode.Bartender.models.data.DrinkDao;
import org.launchcode.Bartender.models.data.IngredientDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping(value = "drink")
public class DrinkController {

    @Autowired
    private DrinkDao drinkDao;
    @Autowired
    private IngredientDao ingredientDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index (Model model) {

        model.addAttribute("title", "Drink List");
        model.addAttribute("drinkList", drinkDao.findAll());
        return "drink/index";
    }

    @RequestMapping(value="view/{drinkId}", method = RequestMethod.GET)
    public String displayRecipe(Model model, @PathVariable("drinkId")  int drinkId) {

        Drink drink = drinkDao.findById(drinkId).get();
        Map<String, String> recipeMap = drink.getRecipe();
        ArrayList<Recipe> recipeList = new ArrayList<>();

        //builds a list of Recipe objects to be passed to view.
        //Recipe object contains 2 String fields: measurement and ingredient name
        //(original HashMap recipe contained k,v pairs: measurement, ingredientId)
        for (Map.Entry<String, String> entry : recipeMap.entrySet()) {
            Recipe recipe = new Recipe();
            recipe.measurement = entry.getKey();
            int ingredientId = Integer.parseInt(entry.getValue());
            if (ingredientDao.findById(ingredientId).isPresent()) {
                recipe.ingredientName = ingredientDao.findById(ingredientId).get().getName();
                recipeList.add(recipe);
            }
        }

        model.addAttribute("ingredients", recipeList);
        model.addAttribute("drink", drink);

        return "drink/view";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addDrink (Model model) {
        //populate lists for glass types, mix types, and chill types
        Drink.GlassType[] glassList = org.launchcode.Bartender.models.Drink.GlassType.values();
        Drink.MixType[] mixList = org.launchcode.Bartender.models.Drink.MixType.values();
        Drink.ChillType[] chillList = org.launchcode.Bartender.models.Drink.ChillType.values();
        model.addAttribute("glassList", glassList);
        model.addAttribute("mixList", mixList);
        model.addAttribute("chillList", chillList);
        model.addAttribute("ingredientList", ingredientDao.findAll() );
        model.addAttribute("title", "Add A New Drink Recipe");


        return "drink/add";
    }

    @RequestMapping(value = "add", method=RequestMethod.POST)
    public String addDrink (Model model, @RequestParam String name,
                            @RequestParam String measurement1, @RequestParam String ingredient1,
                            @RequestParam String measurement2, @RequestParam String ingredient2,
                            @RequestParam String measurement3, @RequestParam String ingredient3,
                            @RequestParam String measurement4, @RequestParam String ingredient4,
                            @RequestParam Drink.GlassType glassType, @RequestParam Drink.MixType mixType,
                            @RequestParam Drink.ChillType chillType, @RequestParam String instructions) {
        //build recipe HashMap to add to Drink object
        Map<String, String> recipe = new LinkedHashMap<String, String>();

        recipe.put(measurement1, ingredient1);
        recipe.put(measurement2, ingredient2);
        if (!measurement3.isEmpty()){
            recipe.put(measurement3, ingredient3);
        }
        if (!measurement4.isEmpty()){
            recipe.put(measurement4, ingredient4);
        }

        Drink newDrink = new Drink();
        newDrink.setName(name);
        newDrink.setRecipe(recipe);
        newDrink.setInstructions(instructions);
        newDrink.setChillType(chillType);
        newDrink.setGlassType(glassType);
        newDrink.setMixType(mixType);
        drinkDao.save(newDrink);
        return "redirect:";
    }

}
