package org.launchcode.Bartender.controllers;

import org.launchcode.Bartender.models.User;
import org.launchcode.Bartender.models.data.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    /*Commented out for testing. Uncomment this and delete duplicate below when finished

    @RequestMapping(value = "")
    public String index() {
        return "index";
    }*/

    @Autowired
    private UserDao userDao;
    @RequestMapping(value = "")
    public String index(HttpServletRequest request) {
        /*User user = userDao.findById(55).orElse(null);
        HttpSession session = request.getSession();
        session.setAttribute("name", user.getName());*/
        return "index";
    }
}