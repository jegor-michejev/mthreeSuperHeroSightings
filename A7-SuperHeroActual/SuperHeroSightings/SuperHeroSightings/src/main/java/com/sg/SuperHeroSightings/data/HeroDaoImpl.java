package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Superpower;
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
public class HeroDaoImpl implements HeroDao{

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    SuperpowerDao superpowerDao;


    @Override
    @Transactional
    public Hero add(Hero hero) {

        final String INSERT_HERO = "INSERT INTO hero(name, description, superpowerId) VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpower().getId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);

        return hero;

    }

    @Override
    public List<Hero> getAll() {

        final String SELECT_ALL_HEROES = "SELECT * FROM hero";
        List<Hero> heroes = jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
        for (Hero hero: heroes){
            hero.setSuperpower(getSuperpowerFromHero(hero));
        }


        return heroes;

    }

    private Superpower getSuperpowerFromHero(Hero hero){

        final String SELECT_SUPERPOWER_FROM_HERO = "Select s.* FROM superpower s"
                + " JOIN hero h ON s.id = h.superpowerId WHERE h.id = ?";
        return jdbc.queryForObject(SELECT_SUPERPOWER_FROM_HERO, new SuperpowerDaoImpl.SuperpowerMapper(),
                hero.getId());

    }


    @Override
    public Hero getById(int id) {

        try {
            final String SELECT_HERO_BY_ID = "SELECT * FROM hero WHERE id = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID,
                    new HeroMapper(), id);
            hero.setSuperpower(getSuperpowerFromHero(hero));
            return hero;
        } catch(DataAccessException ex) {
            return null;
        }

    }

    @Override
    public boolean update(Hero hero) {
        final String UPDATE_HERO = "UPDATE hero "
                + "SET name = ?, description = ?, superpowerId = ? WHERE id = ?";
        return jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpower().getId(),
                hero.getId()) > 0;
    }

    @Override
    @Transactional
    public boolean delete(Hero hero) {

        final String DELETE_HERO_ORGANISATION = "DELETE FROM hero_organisation "
                + "WHERE heroId = ?";
        jdbc.update(DELETE_HERO_ORGANISATION, hero.getId());

        final String DELETE_SIGHTING = "DELETE FROM sighting "
                + "WHERE heroId = ?";
        jdbc.update(DELETE_SIGHTING, hero.getId());


        final String DELETE_HERO = "DELETE FROM hero "
                + "WHERE id = ?";
        jdbc.update(DELETE_HERO, hero.getId());
        return true;

    }

    static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            return hero;
        }

    }




}
