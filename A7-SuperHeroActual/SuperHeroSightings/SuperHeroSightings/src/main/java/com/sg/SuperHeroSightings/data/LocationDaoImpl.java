package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LocationDaoImpl implements LocationDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Location add(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(name, description, address, coordinates) VALUES(?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getCoordinates());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);

        return location;
    }

    @Override
    public List<Location> getAll() {

        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        List<Location> locations = jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());

        return locations;
    }

    @Override
    public Location getById(int id) {

        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            Location location = jdbc.queryForObject(SELECT_LOCATION_BY_ID,
                    new LocationMapper(), id);
            return location;
        } catch(DataAccessException ex) {
            return null;
        }

    }

    @Override
    public boolean update(Location location) {

        final String UPDATE_LOCATION = "UPDATE location "
                + "SET name = ?, description = ?, address = ?, coordinates = ? WHERE id = ?";
        return jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getCoordinates(),
                location.getId()) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Location location) {


        final String DELETE_SIGHTING = "DELETE FROM sighting "
                + "WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING, location.getId());

        final String DELETE_LOCATION = "DELETE FROM location "
                + "WHERE id = ?";
        jdbc.update(DELETE_LOCATION, location.getId());
        return true;

    }

    final public static class LocationMapper implements RowMapper<Location>{

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setCoordinates(rs.getString("coordinates"));
            return location;
        }


    }




}
