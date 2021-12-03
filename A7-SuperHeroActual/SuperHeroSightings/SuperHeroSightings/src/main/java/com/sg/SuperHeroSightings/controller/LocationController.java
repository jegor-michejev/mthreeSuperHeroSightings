package com.sg.SuperHeroSightings.controller;

import com.sg.SuperHeroSightings.data.HeroDao;
import com.sg.SuperHeroSightings.data.LocationDao;
import com.sg.SuperHeroSightings.data.OrganisationDao;
import com.sg.SuperHeroSightings.data.SightingDao;
import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * The Location page's Controller class
 * Works with locations.html and editLocations.html
 */
@Controller
public class LocationController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    SightingDao sightingDao;

    /**
     * Returns all the locations to the html file to display them on the locations page
     * @param model
     * @return
     */
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locations = locationDao.getAll();
        model.addAttribute("locations", locations);
        return "locations";
    }

    /**
     * Adds a new location to the database with the data provided by the user from a form
     * @param request
     * @return redirects back to the main heroes page
     */
    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String coordinates = request.getParameter("coordinates");

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setCoordinates(coordinates);

        locationDao.add(location);

        return "redirect:/locations";
    }

    /**
     * Deletes a location
     * @param request
     * @return
     */
    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.delete(locationDao.getById(id));

        return "redirect:/locations";
    }

    /**
     * Redirects the user to an editLocation.html page to input data in a form
     * @param request
     * @param model
     * @return
     */
    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getById(id);

        model.addAttribute("location", location);
        return "editLocation";
    }

    /**
     * Executes the update with the data provided by the user
     * @param request
     * @return
     */
    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getById(id);

        location.setName(request.getParameter("name"));
        location.setDescription(request.getParameter("description"));
        location.setAddress(request.getParameter("address"));
        location.setCoordinates(request.getParameter("coordinates"));

        locationDao.update(location);

        return "redirect:/locations";
    }

}
