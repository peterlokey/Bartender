package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.User;
import org.launchcode.Bartender.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

//TODO: verify username is available


@Controller
@RequestMapping(value = "user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "signup", method= RequestMethod.GET)
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("title", "Sign Up");
        return "user/sign-up";
    }

    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String signUp(Model model, @ModelAttribute User user, String verify) {
        if (verify.equals(user.getPassword())) {
            //verify username is available
            userDao.save(user);
            model.addAttribute("name", user.getName());
            return "user/index";
        } else{
            System.out.println("Passwords do not match");
        }
        return "user/sign-up";
    }

}