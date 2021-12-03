package com.sg.SuperHeroSightings.controller;

import com.sg.SuperHeroSightings.data.HeroDao;
import com.sg.SuperHeroSightings.data.LocationDao;
import com.sg.SuperHeroSightings.data.OrganisationDao;
import com.sg.SuperHeroSightings.data.SightingDao;
import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Location;
import com.sg.SuperHeroSightings.model.Organisation;
import com.sg.SuperHeroSightings.model.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * The Sighting page's Controller class
 * Works with sightings.html and editSighting.html
 */
@Controller
public class SightingController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    SightingDao sightingDao;

    /**
     * Returns all the sightings to the html file to display them on the sightings page
     * @param model
     * @return
     */
    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAll();
        List<Hero> heroes = heroDao.getAll();
        List<Location> locations=  locationDao.getAll();
        model.addAttribute("sightings", sightings);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

        return "sightings";
    }

    /**
     * Adds a new sighting to the database with the data provided by the user from a form
     * @param request
     * @return redirects back to the main heroes page
     */
    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        LocalDate date = LocalDate.parse( request.getParameter("date"));

        Sighting sighting = new Sighting();
        sighting.setHero(heroDao.getById(Integer.parseInt(heroId)));
        sighting.setLocation(locationDao.getById(Integer.parseInt(locationId)));
        sighting.setDate(date);

        sightingDao.add(sighting);

        return "redirect:/sightings";
    }

    /**
     * Deletes a sighting
     * @param request
     * @return
     */
    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.delete(sightingDao.getById(id));

        return "redirect:/sightings";
    }

    /**
     * Redirects the user to an editSighting.html page to input data in a form
     * @param id
     * @param model
     * @return
     */
    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
        Sighting sighting = sightingDao.getById(id);
        List<Hero> heroes = heroDao.getAll();
        List<Location> locations=  locationDao.getAll();
        model.addAttribute("sighting", sighting);
        model.addAttribute("heroes", heroes);
        model.addAttribute("locations", locations);

        return "editSighting";
    }

    /**
     * Executes the update with the data provided by the user
     * @param request
     * @return
     */
    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request) {
        String id = request.getParameter("id");
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        LocalDate date = LocalDate.parse( request.getParameter("date"));

        Sighting sighting = new Sighting();
        sighting.setId(Integer.parseInt(id));
        sighting.setHero(heroDao.getById(Integer.parseInt(heroId)));
        sighting.setLocation(locationDao.getById(Integer.parseInt(locationId)));
        sighting.setDate(date);

        sightingDao.update(sighting);

        return "redirect:/sightings";
    }

}
