package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Location;
import com.sg.SuperHeroSightings.model.Sighting;
import com.sg.SuperHeroSightings.model.Superpower;
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
public class SuperpowerDaoImpl implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Superpower add(Superpower superpower) {

        final String INSERT_SIGHTING = "INSERT INTO superpower(name) VALUES(?)";
        jdbc.update(INSERT_SIGHTING,
                superpower.getName());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        superpower.setId(newId);

        return superpower;
    }

    @Override
    public List<Superpower> getAll() {

        final String SELECT_ALL_SUPERPOWERS = "SELECT * FROM superpower";
        List<Superpower> superpowers = jdbc.query(SELECT_ALL_SUPERPOWERS, new SuperpowerMapper());

        return superpowers;
    }

    @Override
    public Superpower getById(int id) {

        try {
            final String SELECT_SUPERPOWER_BY_NAME = "SELECT * FROM superpower WHERE id = ?";
            Superpower superpower = jdbc.queryForObject(SELECT_SUPERPOWER_BY_NAME,
                    new SuperpowerMapper(), id);
            return superpower;
        } catch(DataAccessException ex) {
            return null;
        }
    }

    @Override
    public boolean update(Superpower superpower) {
        final String UPDATE_SUPERPOWER = "UPDATE superpower  "
                + "SET name = ? WHERE id = ?";
        return jdbc.update(UPDATE_SUPERPOWER,
                superpower.getName(),
                superpower.getId()) > 0;

    }

    @Override
    @Transactional
    public boolean delete(Superpower superpower) {

        final String DELETE_SUPERPOWER = "DELETE FROM superpower "
                + "WHERE id = ?";
        jdbc.update(DELETE_SUPERPOWER, superpower.getId());
        return true;

    }

    final public static class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower superpower = new Superpower();
            superpower.setId(rs.getInt("id"));
            superpower.setName(rs.getString("name"));
            return superpower;
        }

    }
}
