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


@Controller
@RequestMapping(value = "drink")
public class DrinkController {

    /*TODO: After rating a drink, return to that drink's individual view, not Drink List*/

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
        model.addAttribute("drinkList", sortDrinkListAlphabetically());
        model.addAttribute("sorting", "alpha");
        return "drink/index";
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public String index (Model model, @RequestParam String sorting) {

        model.addAttribute("title", "Drink List");
        model.addAttribute("sorting", sorting);
        List<Drink> sortedDrinks = sortDrinkListByRating();
        if(sorting.equals("alpha")){
            model.addAttribute("drinkList", sortDrinkListAlphabetically());
        }
        if(sorting.equals("rating")){
            model.addAttribute("drinkList", sortDrinkListByRating());
        }
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
            Rating userRating = findRating(drink.getId(), findByName(name).getId());
            if (userRating != null){
                model.addAttribute("userRating", userRating.getScore());
            }else{
                model.addAttribute("userRating", 0);
            }
        }
        double rating  = getAverageRating(drink);
        int numberOfRatings = getRatingCount(drink);
        model.addAttribute("rating", rating);
        model.addAttribute("numberOfRatings", numberOfRatings);
        model.addAttribute("ingredients", recipeList);
        model.addAttribute("drink", drink);
        model.addAttribute("clipart", getClipArtString(drink));

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
        model.addAttribute("vodkaList", generateTypeList(Ingredient.Type.Vodka));
        model.addAttribute("ginList", generateTypeList(Ingredient.Type.Gin));
        model.addAttribute("rumList", generateTypeList(Ingredient.Type.Rum));
        model.addAttribute("tequilaList", generateTypeList(Ingredient.Type.Tequila));
        model.addAttribute("whiskeyList", generateTypeList(Ingredient.Type.Whiskey));
        model.addAttribute("cordialList", generateTypeList(Ingredient.Type.Cordial));
        model.addAttribute("wineList", generateTypeList(Ingredient.Type.Wine));
        model.addAttribute("bittersList", generateTypeList(Ingredient.Type.Bitters));
        model.addAttribute("mixerList", generateTypeList(Ingredient.Type.Mixer));
        model.addAttribute("garnishList", generateTypeList(Ingredient.Type.Garnish));


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
        newDrink.setClipart(getClipArtString(newDrink));
        drinkDao.save(newDrink);
        return "redirect:";
    }

    @RequestMapping(value = "rate")
    public String rate (Model model, HttpServletRequest request, @RequestParam String drinkId,
                        @RequestParam String ratingScore){
        int score=Integer.parseInt(ratingScore);
        int drinkIdInt=Integer.parseInt(drinkId);
        System.out.println(score);
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

        return "redirect:/drink/view/"+drinkId;
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

        return "redirect:/drink/view/"+drinkId;
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

    public double getAverageRating(Drink drink){
        int count = 0;
        int sum = 0;
        for (Rating rating : ratingDao.findAll()){
            if (rating.getDrink().equals(drink)){
                sum += rating.getScore();
                count++;
            }
        }
        if (count == 0){
            return 0;
        }
        double average = (double)sum/count;
        return average;
    }

    public int getRatingCount(Drink drink){
        int count = 0;
        for (Rating rating : ratingDao.findAll()){
            if (rating.getDrink().equals(drink)){
                count++;
            }
        }
        return count;
    }

    public String getClipArtString(Drink drink){
        Drink.GlassType type = drink.getGlassType();
        switch (type){
            case Highball:
            case Pint:
                return "/img/pint.jpg";
            case Cocktail:
                return "/img/coupe.jpg";
            case Martini:
                return "/img/martini.jpg";
            case Rocks:
                return "/img/rocks.jpg";
            case Wine:
                return "/img/wine.jpg";
            case Shot:
                return "/img/shot.jpg";
        }
        return "";
    }

    public ArrayList<Drink> sortDrinkListAlphabetically(){
        Iterable<Drink> drinks = drinkDao.findAll();
        ArrayList<Drink> drinkList = new ArrayList<Drink>();

        for (Drink i : drinks){
            drinkList.add(i);
        }

        for (int j=0; j<drinkList.size(); j++){
            for(int i=j+1; i<drinkList.size(); i++){
                if((drinkList.get(i).getName().compareToIgnoreCase(drinkList.get(j).getName()) < 0)){
                    Drink temp = drinkList.get(j);
                    drinkList.set(j, drinkList.get(i));
                    drinkList.set(i, temp);
                }
            }
        }

        return drinkList;
    }

    public List<Drink> sortDrinkListByRating(){
        /*Iterable<Drink> drinks = drinkDao.findAll();
        ArrayList<Drink> drinkList = new ArrayList<Drink>();*/
        ArrayList<Drink> drinkList = sortDrinkListAlphabetically();
        /*for (Drink i : drinks){
            drinkList.add(i);
        }*/
        for (int j=0; j<drinkList.size(); j++){
            for(int i=j+1; i<drinkList.size(); i++){
                if(Double.compare(getAverageRating(drinkList.get(i)), getAverageRating(drinkList.get(j))) > 0){
                    Drink temp = drinkList.get(j);
                    drinkList.set(j, drinkList.get(i));
                    drinkList.set(i, temp);
                }
            }
        }

        return drinkList;
    }
}
