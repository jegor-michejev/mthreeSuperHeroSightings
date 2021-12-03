package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Sighting;
import com.sg.SuperHeroSightings.model.Superpower;

import java.util.List;

public interface SuperpowerDao {

    Superpower add(Superpower superpower);

    List<Superpower> getAll();

    Superpower getById(int id);

    boolean update(Superpower superpower);

    boolean delete(Superpower superpower);

}
