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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "ingredient")
public class IngredientController {

    @Autowired
    private IngredientDao ingredientDao;
/*TODO: Sort each type-list alphabetically*/
/*TODO: Make Ingredient List clickable links to all drinks containing that Ingredient*/

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addIngredient (Model model, HttpServletRequest request) {
        //check for logged-in user
        HttpSession session = request.getSession();
        String name = (String)session.getAttribute("name");
        if (name == null){
            model.addAttribute("title", "Ingredient List");
            model.addAttribute("message", "Only Logged-In Users may add ingredients");
            Ingredient.Type[] typeList = org.launchcode.Bartender.models.Ingredient.Type.values();
            model.addAttribute("typeList", typeList);
            model.addAttribute("vodkaList", generateTypeList(Ingredient.Type.Vodka));
            model.addAttribute("ginList", generateTypeList(Ingredient.Type.Gin));
            model.addAttribute("rumList", generateTypeList(Ingredient.Type.Rum));
            model.addAttribute("tequilaList", generateTypeList(Ingredient.Type.Tequila));
            model.addAttribute("whiskeyList", generateTypeList(Ingredient.Type.Whiskey));
            model.addAttribute("wineList", generateTypeList(Ingredient.Type.Wine));
            model.addAttribute("bittersList", generateTypeList(Ingredient.Type.Bitters));
            model.addAttribute("mixerList", generateTypeList(Ingredient.Type.Mixer));
            model.addAttribute("garnishList", generateTypeList(Ingredient.Type.Garnish));
            return "ingredient/index";
        }

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

        //checks for existing ingredient
        if (ingredientDao.existsByName(ingredient.getName())){
            model.addAttribute("title", "Add New Ingredient");
            model.addAttribute("nameError", "This Ingredient already exists");
            Ingredient.Type[] typeList = org.launchcode.Bartender.models.Ingredient.Type.values();
            model.addAttribute("typeList", typeList);
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
            return "ingredient/add";
        }

        ingredientDao.save(ingredient);

        model.addAttribute("ingredientList", ingredientDao.findAll());
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
        return "ingredient/index";
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index (Model model) {

        model.addAttribute("vodkaList", generateTypeList(Ingredient.Type.Vodka));
        model.addAttribute("ginList", generateTypeList(Ingredient.Type.Gin));
        model.addAttribute("rumList", generateTypeList(Ingredient.Type.Rum));
        model.addAttribute("tequilaList", generateTypeList(Ingredient.Type.Tequila));
        model.addAttribute("whiskeyList", generateTypeList(Ingredient.Type.Whiskey));
        model.addAttribute("wineList", generateTypeList(Ingredient.Type.Wine));
        model.addAttribute("cordialList", generateTypeList(Ingredient.Type.Cordial));
        model.addAttribute("bittersList", generateTypeList(Ingredient.Type.Bitters));
        model.addAttribute("mixerList", generateTypeList(Ingredient.Type.Mixer));
        model.addAttribute("garnishList", generateTypeList(Ingredient.Type.Garnish));



        return "ingredient/index";
    }

    //TODO: DRY this code- this function is repeated in multiple controllers.
    public List<Ingredient> generateTypeList(Ingredient.Type type){
        Iterable<Ingredient> ingredients = ingredientDao.findAll();
        List<Ingredient> typeList = new ArrayList<Ingredient>();
        for (Ingredient i : ingredients){
            if (i.getType().equals(type)){
                typeList.add(i);
            }
        }
        //alphabetize typeList before returning it
        for (int j=0; j<typeList.size(); j++){
            for(int i=j+1; i<typeList.size(); i++){
                if((typeList.get(i).getName().compareToIgnoreCase(typeList.get(j).getName()) < 0)){
                    Ingredient temp = typeList.get(j);
                    typeList.set(j, typeList.get(i));
                    typeList.set(i, temp);
                }
            }
        }

        return typeList;
    }
}
