package com.sg.SuperHeroSightings.controller;

import com.sg.SuperHeroSightings.data.*;
import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The Superpower page's Controller class
 * Works with superpowers.html and editSuperpower.html
 */
@Controller
public class SuperpowerController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperpowerDao superpowerDao;

    /**
     * Returns all the superpowers to the html file to display them on the superpowers page
     * @param model
     * @return
     */
    @GetMapping("superpowers")
    public String displaySuperpowers(Model model) {
        List<Superpower> superpowers = new ArrayList<>();

        superpowers = superpowerDao.getAll();

        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }

    /**
     * Adds a new superpower to the database with the data provided by the user from a form
     * @param request
     * @return redirects back to the main heroes page
     */
    @PostMapping("addSuperpower")
    public String addSuperpower(HttpServletRequest request) {
        String name = request.getParameter("name");

        Superpower superpower = new Superpower();
        superpower.setName(name);

        superpowerDao.add(superpower);

        return "redirect:/superpowers";
    }

    /**
     * Deletes a superpower
     * @param request
     * @return
     */
    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        superpowerDao.delete(superpowerDao.getById(id));

        return "redirect:/superpowers";
    }

    /**
     * Redirects the user to an editSuperpower.html page to input data in a form
     * @param request
     * @param model
     * @return
     */
    @GetMapping("editSuperpower")
    public String editSuperpower(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getById(id);

        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }

    /**
     * Executes the update with the data provided by the user
     * @param request
     * @return
     */
    @PostMapping("editSuperpower")
    public String performEditSuperpower(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getById(id);

        superpower.setName(request.getParameter("name"));
        superpowerDao.update(superpower);

        return "redirect:/superpowers";
    }

}
