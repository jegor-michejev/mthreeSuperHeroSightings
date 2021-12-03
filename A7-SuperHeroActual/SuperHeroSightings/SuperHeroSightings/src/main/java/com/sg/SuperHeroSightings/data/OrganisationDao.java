package com.sg.SuperHeroSightings.data;



import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Organisation;

import java.util.List;

public interface OrganisationDao {

    Organisation add(Organisation organisation);

    List<Organisation> getAll();

    Organisation getById(int id);

    void update(Organisation organisation);

    boolean delete(Organisation organisation);

    List<Organisation> getAllOrganisationsFromHero(Hero hero);

    List<Hero> getAllHeroesFromOrganisation(Organisation organisation);



}
