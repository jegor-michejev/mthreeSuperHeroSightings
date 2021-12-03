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
import java.util.List;

/**
 * The Hero page's Controller class
 * Works with heroes.html and editHero.html
 */
@Controller
public class HeroController {

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
     * Returns all the heroes to the html file to display them on the heroes page
     * @param model
     * @return
     */
    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDao.getAll();
        List<Superpower> superpowers = superpowerDao.getAll();
        model.addAttribute("heroes", heroes);
        model.addAttribute("superpowers", superpowers);
        return "heroes";
    }

    /**
     * Adds a new hero to the database with the data provided by the user from a form
     * @param request
     * @return redirects back to the main heroes page
     */
    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String superpowerId = request.getParameter("superpowerId");

        Hero hero = new Hero();
        hero.setName(name);
        hero.setDescription(description);
        hero.setSuperpower(superpowerDao.getById(Integer.parseInt(superpowerId)));

        heroDao.add(hero);

        return "redirect:/heroes";
    }

    /**
     * Deletes a hero
     * @param request
     * @return
     */
    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDao.delete(heroDao.getById(id));

        return "redirect:/heroes";
    }

    /**
     * Redirects the user to an editHero.html page to input data in a form
     * @param request
     * @param model
     * @return
     */
    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getById(id);

        List<Superpower> superpowers = superpowerDao.getAll();

        model.addAttribute("hero", hero);
        model.addAttribute("superpowers", superpowers);
        return "editHero";
    }

    /**
     * Executes the update with the data provided by the user
     * @param request
     * @return
     */
    @PostMapping("editHero")
    public String performEditHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getById(id);

        hero.setName(request.getParameter("name"));
        hero.setDescription(request.getParameter("description"));
        hero.setSuperpower(superpowerDao.getById(Integer.parseInt
                (request.getParameter("superpower"))));

        heroDao.update(hero);

        return "redirect:/heroes";
    }

}
