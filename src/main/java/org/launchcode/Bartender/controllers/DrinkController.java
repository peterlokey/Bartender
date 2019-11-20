package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.*;
import org.launchcode.Bartender.models.data.DrinkDao;
import org.launchcode.Bartender.models.data.IngredientDao;
import org.launchcode.Bartender.models.data.RatingDao;
import org.launchcode.Bartender.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

//TODO: I need to put in some redirects somewhere. Getting mapping errors after multiple navigation clicks


@Controller
@RequestMapping(value = "drink")
public class DrinkController {

    @Autowired
    private DrinkDao drinkDao;
    @Autowired
    private IngredientDao ingredientDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RatingDao ratingDao;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index (Model model) {

        model.addAttribute("title", "Drink List");
        model.addAttribute("drinkList", drinkDao.findAll());
        return "drink/index";
    }

    @RequestMapping(value="view/{drinkId}", method = RequestMethod.GET)
    public String displayRecipe(Model model, HttpServletRequest request, @PathVariable("drinkId")  int drinkId) {

        Drink drink = drinkDao.findById(drinkId).get();
        Map<String, String> recipeMap = drink.getRecipe();
        ArrayList<Recipe> recipeList = createSortedRecipeList(recipeMap);

        HttpSession session=request.getSession(false);
        if (session != null) {
            String name = (String) session.getAttribute("name");
            User user = findByName(name);
            if (user.getDrinks().contains(drink)) {
                model.addAttribute("favorited", true);
            }
        }

        model.addAttribute("ingredients", recipeList);
        model.addAttribute("drink", drink);

        return "drink/view";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addDrink (Model model, HttpServletRequest request) {
        //check for logged-in user
        HttpSession session = request.getSession();
        String name = (String)session.getAttribute("name");
        if (name == null){
            model.addAttribute("title", "Drink List");
            model.addAttribute("message", "Only Logged-In Users may add drinks");
            model.addAttribute("drinkList", drinkDao.findAll());
            return "drink/index";
        }
        //populate lists for glass types, mix types, and chill types
        Drink.GlassType[] glassList = org.launchcode.Bartender.models.Drink.GlassType.values();
        Drink.MixType[] mixList = org.launchcode.Bartender.models.Drink.MixType.values();
        Drink.ChillType[] chillList = org.launchcode.Bartender.models.Drink.ChillType.values();
        Ingredient.Type[] typeList = org.launchcode.Bartender.models.Ingredient.Type.values();
        model.addAttribute("typeList", typeList);
        model.addAttribute("glassList", glassList);
        model.addAttribute("mixList", mixList);
        model.addAttribute("chillList", chillList);
        model.addAttribute("title", "Add A New Drink Recipe");
        model.addAttribute("vodkaList", generateTypeList(Ingredient.Type.VODKA));
        model.addAttribute("ginList", generateTypeList(Ingredient.Type.GIN));
        model.addAttribute("rumList", generateTypeList(Ingredient.Type.RUM));
        model.addAttribute("tequilaList", generateTypeList(Ingredient.Type.TEQUILA));
        model.addAttribute("whiskeyList", generateTypeList(Ingredient.Type.WHISKEY));
        model.addAttribute("wineList", generateTypeList(Ingredient.Type.WINE));
        model.addAttribute("bittersList", generateTypeList(Ingredient.Type.BITTERS));
        model.addAttribute("mixerList", generateTypeList(Ingredient.Type.MIXER));
        model.addAttribute("garnishList", generateTypeList(Ingredient.Type.GARNISH));


        return "drink/add";
    }

    @RequestMapping(value = "add", method=RequestMethod.POST)
    public String addDrink (Model model, @RequestParam String name,
                            @RequestParam String measurement1, @RequestParam String ingredient1,
                            @RequestParam String measurement2, @RequestParam String ingredient2,
                            @RequestParam String measurement3, @RequestParam String ingredient3,
                            @RequestParam String measurement4, @RequestParam String ingredient4,
                            @RequestParam String measurement5, @RequestParam String ingredient5,
                            @RequestParam String measurement6, @RequestParam String ingredient6,
                            @RequestParam String measurement7, @RequestParam String ingredient7,
                            @RequestParam String measurement8, @RequestParam String ingredient8,
                            @RequestParam Drink.GlassType glassType, @RequestParam Drink.MixType mixType,
                            @RequestParam Drink.ChillType chillType, @RequestParam String instructions) {
        //build recipe HashMap to add to Drink object
        Map<String, String> recipe = new LinkedHashMap<String, String>();
        recipe.put(measurement1, ingredient1);
        recipe.put(measurement2, ingredient2);
        if (!measurement3.isEmpty()){
            recipe.put(measurement3, ingredient3.replaceAll("[^\\d]", ""));
        }
        if (!measurement4.isEmpty()){
            recipe.put(measurement4, ingredient4.replaceAll("[^\\d]", ""));
        }
        if (!measurement5.isEmpty()){
            recipe.put(measurement5, ingredient5.replaceAll("[^\\d]", ""));
        }
        if (!measurement6.isEmpty()){
            recipe.put(measurement6, ingredient6.replaceAll("[^\\d]", ""));
        }
        if (!measurement7.isEmpty()){
            recipe.put(measurement7, ingredient7.replaceAll("[^\\d]", ""));
        }
        if (!measurement8.isEmpty()){
            recipe.put(measurement8, ingredient8.replaceAll("[^\\d]", ""));
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

    @RequestMapping(value = "rate")
    public String rate (Model model, HttpServletRequest request, @RequestParam String drinkId,
                        @RequestParam String ratingScore){
        int score=Integer.parseInt(ratingScore);
        int drinkIdInt=Integer.parseInt(drinkId);
        HttpSession session = request.getSession();
        String name = (String)session.getAttribute("name");
        User user = findUserByName(name);
        Drink drink = drinkDao.findById(drinkIdInt).get();
        Rating rating;
        if (findRating(drinkIdInt, user.getId())!= null) {
            rating = findRating(drinkIdInt, user.getId());
            rating.setScore(score);
        } else {
            rating = new Rating(user, drink, score);
        }
        ratingDao.save(rating);
        return "redirect:";
    }

    @RequestMapping(value = "favorite")
    public String addToFavorites (Model model, HttpServletRequest request, @RequestParam String drinkId){
        HttpSession session = request.getSession();
        String name = (String)session.getAttribute("name");
        User user = findUserByName(name);
        Drink drink = drinkDao.findById(Integer.parseInt(drinkId)).get();

        if (user.getDrinks() == null) {
            user.setDrinks(new ArrayList<>());
        }
        if(! user.getDrinks().contains(drink)){
            user.addToDrinks(drink);
            userDao.save(user);
        }
        Map<String, String> recipeMap = drink.getRecipe();
        ArrayList<Recipe> recipeList = createSortedRecipeList(recipeMap);

        model.addAttribute("favorited", true);
        model.addAttribute("ingredients", recipeList);
        model.addAttribute("drink", drink);

        return "drink/view";
    }

    @RequestMapping(value = "favorite/remove")
    public String removeFromFavorites (Model model, HttpServletRequest request, @RequestParam String drinkId){
        HttpSession session = request.getSession();
        String name = (String)session.getAttribute("name");
        User user = findUserByName(name);
        Drink drink = drinkDao.findById(Integer.parseInt(drinkId)).get();

        user.removeFromDrinks(drink);
        userDao.save(user);

        Map<String, String> recipeMap = drink.getRecipe();
        ArrayList<Recipe> recipeList = createSortedRecipeList(recipeMap);

        model.addAttribute("favorited", false);
        model.addAttribute("ingredients", recipeList);
        model.addAttribute("drink", drink);

        return "drink/view";
    }
    public List<Ingredient> generateTypeList(Ingredient.Type type){
        Iterable<Ingredient> ingredients = ingredientDao.findAll();
        List<Ingredient> typeList = new ArrayList<Ingredient>();
        for (Ingredient i : ingredients){
            if (i.getType().equals(type)){
                typeList.add(i);
            }
        }
        return typeList;
    }


    public ArrayList<Recipe> createSortedRecipeList(Map<String,String> recipeMap){
        ArrayList<Recipe> recipeList = new ArrayList<>();
        for (Ingredient.Type t : Ingredient.Type.values()){
            for (Map.Entry<String, String> entry : recipeMap.entrySet()) {
                //call ingredient object

                int ingredientId = Integer.parseInt(entry.getValue());
                Ingredient ingredient = ingredientDao.findById(ingredientId).get();
                //check if ingredient type matches iteration type
                if (ingredient.getType().equals(t)) {
                    Recipe recipe = new Recipe();
                    recipe.measurement = entry.getKey();
                    //add Recipe object to List if type matches iteration type
                    if (ingredientDao.findById(ingredientId).isPresent()) {
                        recipe.ingredientName = ingredientDao.findById(ingredientId).get().getName();
                        recipeList.add(recipe);
                    }
                }
            }
        }
        return recipeList;
    }

    public User findUserByName (String name) {
        for (User u : userDao.findAll()){
            if (u.getName().equals(name)){
                return u;
            }
        }
        return null;
    }

    public User findByName(String name){

        for (User user : userDao.findAll()){
            if(user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }

    public Rating findRating(int drinkId, int userId){
        for (Rating r : ratingDao.findAll()){
            if (r.getDrink().getId()==drinkId){
                if (r.getUser().getId()==userId){
                    return r;
                }
            }
        }
        return null;
    }

}
