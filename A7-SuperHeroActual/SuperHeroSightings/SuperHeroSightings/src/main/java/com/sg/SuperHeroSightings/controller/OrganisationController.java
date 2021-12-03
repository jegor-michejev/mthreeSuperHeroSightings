package com.sg.SuperHeroSightings.controller;

import com.sg.SuperHeroSightings.data.HeroDao;
import com.sg.SuperHeroSightings.data.LocationDao;
import com.sg.SuperHeroSightings.data.OrganisationDao;
import com.sg.SuperHeroSightings.data.SightingDao;
import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Location;
import com.sg.SuperHeroSightings.model.Organisation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The Organisation page's Controller class
 * Works with organisations.html, editOrganisation.html and organisationDetail.html
 */
@Controller
public class OrganisationController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    SightingDao sightingDao;

    /**
     * Returns all the organisations to the html file to display them on the organisations page
     * @param model
     * @return
     */
    @GetMapping("organisations")
    public String displayLocations(Model model) {
        List<Organisation> organisations = organisationDao.getAll();
        List<Hero> heroes = heroDao.getAll();
        model.addAttribute("organisations", organisations);
        model.addAttribute("heroes", heroes);
        return "organisations";
    }

    /**
     * Adds a new organisation to the database with the data provided by the user from a form
     * @param request
     * @return redirects back to the main organisations page
     */
    @PostMapping("addOrganisation")
    public String addOrganisation(Organisation organisation, HttpServletRequest request) {
        String[] heroIds = request.getParameterValues("heroId");

        List<Hero> heroes = new ArrayList<>();
        for (String heroId : heroIds)   {
            heroes.add(heroDao.getById(Integer.parseInt(heroId)));
        }

        organisation.setHeroes(heroes);

        organisationDao.add(organisation);

        return "redirect:/organisations";
    }

    /**
     * Redirects the user to the organisation Details page after pressing the "Details" button
     * @param id
     * @param model
     * @return
     */
    @GetMapping("organisationDetail")
    public String organisationDetail(Integer id, Model model) {
        Organisation organisation = organisationDao.getById(id);
        model.addAttribute("organisation", organisation);
        return "organisationDetail";
    }

    /**
     * Deletes an organisation
     * @param request
     * @return
     */
    @GetMapping("deleteOrganisation")
    public String deleteOrganisation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        organisationDao.delete(organisationDao.getById(id));

        return "redirect:/organisations";
    }

    /**
     * Redirects the user to an editOrganisation.html page to input data in a form
     * @param id
     * @param model
     * @return
     */
    @GetMapping("editOrganisation")
    public String editOrganisation(Integer id, Model model) {
        Organisation organisation = organisationDao.getById(id);
        List<Hero> heroes = heroDao.getAll();
        model.addAttribute("organisation", organisation);
        model.addAttribute("heroes", heroes);

        return "editOrganisation";
    }

    /**
     * Executes the update with the data provided by the user
     * @param request
     * @return
     */
    @PostMapping("editOrganisation")
    public String performEditOrgansiation(Organisation organisation, HttpServletRequest request) {
         String[] heroIds = request.getParameterValues("heroId");

        List<Hero> heroes = new ArrayList<>();
        for(String heroId : heroIds){
            heroes.add(heroDao.getById(Integer.parseInt(heroId)));
        }
        organisation.setHeroes(heroes);

        organisationDao.update(organisation);

        return "redirect:/organisations";
    }

}
