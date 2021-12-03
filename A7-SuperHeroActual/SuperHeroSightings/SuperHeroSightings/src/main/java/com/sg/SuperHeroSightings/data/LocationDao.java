package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Location;

import java.util.List;

public interface LocationDao {

    Location add(Location location);

    List<Location> getAll();

    Location getById(int id);

    boolean update(Location location);

    boolean delete(Location location);




}
