package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Location;
import com.sg.SuperHeroSightings.model.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class SightingDaoImpl implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Sighting add(Sighting sighting) {

            final String INSERT_SIGHTING = "INSERT INTO sighting(date, heroId, locationId) VALUES(?,?,?)";
            jdbc.update(INSERT_SIGHTING,
                    sighting.getDate(),
                    sighting.getHero().getId(),
                    sighting.getLocation().getId());
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
            sighting.setId(newId);

            return sighting;
    }

    @Override
    public List<Sighting> getAll() {

        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());

        addHeroAndLocationToSighting(sightings);

        return sightings;
    }

    private void addHeroAndLocationToSighting(List<Sighting> sightings){

        for (Sighting sighting : sightings){
            sighting.setHero(getHeroFromSighting(sighting));
            sighting.setLocation(getLocationFromSighting(sighting));
        }


    }

    private Hero getHeroFromSighting(Sighting sighting){
        final String SELECT_HERO_FROM_SIGHTING = "Select h.* FROM hero h"
                + " JOIN sighting s ON h.id = s.heroId WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_HERO_FROM_SIGHTING, new HeroDaoImpl.HeroMapper(),
                sighting.getId());


    }


    private Location getLocationFromSighting(Sighting sighting){
        final String SELECT_LOCATION_FROM_SIGHTING = "Select l.* FROM location l"
                + " JOIN sighting s ON l.id = s.locationId WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FROM_SIGHTING, new LocationDaoImpl.LocationMapper(),
                sighting.getId());


    }




    @Override
    public Sighting getById(int id) {

        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_ID,
                    new SightingMapper(), id);
            sighting.setHero(getHeroFromSighting(sighting));
            sighting.setLocation(getLocationFromSighting(sighting));
            return sighting;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean update(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting  "
                + "SET date = ?, heroId = ?, locationId = ? WHERE id = ?";
        return jdbc.update(UPDATE_SIGHTING,
                sighting.getDate(),
                sighting.getHero().getId(),
                sighting.getLocation().getId(),
                sighting.getId()) > 0;

    }

        @Override
    @Transactional
    public boolean delete(Sighting sighting) {

            final String DELETE_SIGHTING = "DELETE FROM sighting "
                    + "WHERE id = ?";
            jdbc.update(DELETE_SIGHTING, sighting.getId());
            return true;

    }

    @Override
    public List<Sighting> getAllSightingsFromLocation(Location location) {

        final String SELECT_SIGHTINGS_FROM_LOCATION = "SELECT * FROM sighting"
        + " WHERE locationId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FROM_LOCATION, new SightingMapper(),
                location.getId());

        addHeroAndLocationToSighting(sightings);

        return sightings;
    }

    @Override
    public List<Sighting> getAllSightingsFromHero(Hero hero) {

        final String SELECT_SIGHTINGS_FROM_HERO = "SELECT * FROM sighting"
                + " WHERE heroId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FROM_HERO, new SightingMapper(),
                hero.getId());

        addHeroAndLocationToSighting(sightings);

        return sightings;
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        final String SELECT_SIGHTINGS_FROM_HERO = "SELECT * FROM sighting"
                + " WHERE date = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FROM_HERO, new SightingMapper(),
                date);

        addHeroAndLocationToSighting(sightings);

        return sightings;
    }

    final public static class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setDate(rs.getDate("date").toLocalDate());
            return sighting;
        }

    }
}
