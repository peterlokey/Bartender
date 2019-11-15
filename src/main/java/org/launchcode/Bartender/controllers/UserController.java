package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.Ingredient;
import org.launchcode.Bartender.models.User;
import org.launchcode.Bartender.models.data.IngredientDao;
import org.launchcode.Bartender.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

//TODO : To re-implement Spring Security, uncomment  code in build.gradle, HomeResource, and Security Configuration
//TODO: change "MyBar" link to My Profile. Create profile with My Bar and Favorites.

@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IngredientDao ingredientDao;

    @RequestMapping(value = "signup", method = RequestMethod.GET)
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Sign Up");
        return "user/sign-up";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signUp(Model model, @ModelAttribute User user, String verify) {
        boolean hasErrors = false;
        //verify username length
        int len = user.getName().length();
        if (len < 4 || len > 12) {
            model.addAttribute("usernameError", "Username must be between 4 and 12 characters");
            hasErrors = true;
        }
        //verify username is available
        for (User u : userDao.findAll()) {
            if (u.getName().equals(user.getName())) {
                model.addAttribute("usernameError", "Username already exists");
                hasErrors = true;
            }
        }
        //verify email is available
        for (User u : userDao.findAll()) {
            if (u.getEmail().equals(user.getEmail())) {
                model.addAttribute("emailError", "Email already exists");
                hasErrors = true;
            }
        }
        //verify email
        len = user.getEmail().length();
        if (len < 8 || len > 25) {
            model.addAttribute("emailError", "Email must be between 8 and 25 characters");
            hasErrors = true;
        }
        if (user.getEmail().indexOf('.') < 0) {
            model.addAttribute("emailError", "Please enter a valid email");
            hasErrors = true;
        }
        //verify passwords match
        if (!verify.equals(user.getPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            hasErrors = true;
        }
        //verify password length
        len = user.getPassword().length();
        if (len < 6) {
            model.addAttribute("passwordError", "Password must be at least 6 characters");
            hasErrors = true;
        }

        if (hasErrors) {
            return "user/sign-up";
        }

        userDao.save(user);
        model.addAttribute("name", user.getName());
        return "user/index";
    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(Model model) {

        model.addAttribute("title", "Log In");
        return "user/login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login(Model model, @RequestParam String email, @RequestParam String password, HttpServletRequest request) {
        int userId = 0;
        boolean found = false;
        for (User user : userDao.findAll()) {
            if (user.getEmail().equals(email)){
                userId = user.getId();
                found = true;
            }
        }

        if (!found){
            model.addAttribute("emailError", "Email not found");
            return "user/login";
        }
        User user = userDao.findById(userId).orElse(null);

        if (!user.getPassword().equals(password)){
            model.addAttribute("emailError", "Email and password do not match");
            model.addAttribute("email", email);
            return "user/login";
        }


        HttpSession session = request.getSession();
        session.setAttribute("name", user.getName());
        model.addAttribute("myBar", user.getMyBar());

        return "redirect:";

    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String login(HttpSession session){
        session.invalidate();
        return "index";
    }

    @RequestMapping(value = "mybar/add", method = RequestMethod.GET)
    public String addToMyBar(Model model){
        model.addAttribute("vodkaList", generateTypeList(Ingredient.Type.VODKA));
        model.addAttribute("ginList", generateTypeList(Ingredient.Type.GIN));
        model.addAttribute("rumList", generateTypeList(Ingredient.Type.RUM));
        model.addAttribute("tequilaList", generateTypeList(Ingredient.Type.TEQUILA));
        model.addAttribute("whiskeyList", generateTypeList(Ingredient.Type.WHISKEY));
        model.addAttribute("wineList", generateTypeList(Ingredient.Type.WINE));
        model.addAttribute("bittersList", generateTypeList(Ingredient.Type.BITTERS));
        model.addAttribute("mixerList", generateTypeList(Ingredient.Type.MIXER));
        model.addAttribute("garnishList", generateTypeList(Ingredient.Type.GARNISH));

        return "user/add-ingredient";
    }

    @RequestMapping(value = "mybar/add", method = RequestMethod.POST)
    public String addToMyBar(HttpServletRequest request, Model model, @RequestParam Ingredient ingredient){
        HttpSession session=request.getSession(false);
        String name = (String)session.getAttribute("name");
        User user = findByName(name);
        if (user.getMyBar() == null) {
            user.setMyBar(new ArrayList<>());
        }
        if(! user.getMyBar().contains(ingredient)){
            user.addToMyBar(ingredient);
            userDao.save(user);
        }
        model.addAttribute("myBar", user.getMyBar());
        return "redirect:..";
    }

    @RequestMapping(value = "")
    public String index(HttpServletRequest request, Model model){
        HttpSession session=request.getSession(false);
        String name = (String)session.getAttribute("name");
        User user = findByName(name);

        model.addAttribute("myBar", user.getMyBar());
        return "user/index";
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

    public User findByName(String name){

        for (User user : userDao.findAll()){
            if(user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }
}