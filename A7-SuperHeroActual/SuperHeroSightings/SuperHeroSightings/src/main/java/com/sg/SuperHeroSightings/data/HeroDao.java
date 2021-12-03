package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Hero;

import java.util.List;

public interface HeroDao {

    Hero add(Hero hero);

    List<Hero> getAll();

    Hero getById(int id);

    boolean update(Hero hero);

    boolean delete(Hero hero);

}
