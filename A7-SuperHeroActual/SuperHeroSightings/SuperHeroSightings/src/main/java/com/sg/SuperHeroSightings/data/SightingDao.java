package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Location;
import com.sg.SuperHeroSightings.model.Sighting;

import java.time.LocalDate;
import java.util.List;

public interface SightingDao {

    Sighting add(Sighting sighting);

    List<Sighting> getAll();

    Sighting getById(int id);

    boolean update(Sighting sighting);

    boolean delete(Sighting sighting);

    List<Sighting> getAllSightingsFromLocation(Location location);

    List<Sighting> getAllSightingsFromHero(Hero hero);

    List<Sighting> getSightingsByDate(LocalDate date);



}
