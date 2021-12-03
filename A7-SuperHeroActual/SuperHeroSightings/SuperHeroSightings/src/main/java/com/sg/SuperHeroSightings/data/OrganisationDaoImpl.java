package com.sg.SuperHeroSightings.data;

import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Organisation;
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
public class OrganisationDaoImpl implements OrganisationDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Organisation add(Organisation organisation) {

        final String INSERT_ORGANISATION = "INSERT INTO organisation(name, description, address, contact) VALUES(?,?,?,?)";
        jdbc.update(INSERT_ORGANISATION,
                organisation.getName(),
                organisation.getDescription(),
                organisation.getAddress(),
                organisation.getContact());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organisation.setId(newId);


        insertHeroOrganisation(organisation);


        return organisation;

    }

    private void insertHeroOrganisation(Organisation organisation){

        final String INSERT_HERO_ORGANISATION = "INSERT INTO hero_organisation(heroId, organisationId) VALUES(?,?)";

        for (Hero hero : organisation.getHeroes()){
            jdbc.update(INSERT_HERO_ORGANISATION, hero.getId(), organisation.getId());
        }


    }


    @Override
    public List<Organisation> getAll() {

        final String SELECT_ALL_ORGANISATIONS = "SELECT * FROM organisation";
        List<Organisation> organisations = jdbc.query(SELECT_ALL_ORGANISATIONS, new OrganisationMapper());

        addHeroesToOrganisation(organisations);

        return organisations;

    }

    private void addHeroesToOrganisation(List<Organisation> organisations){

        for (Organisation organisation : organisations){
            organisation.setHeroes(getAllHeroesFromOrganisation(organisation));
        }

    }

    @Override
    public Organisation getById(int id) {

        try {
            final String SELECT_ORGANISATION_BY_ID = "SELECT * FROM organisation WHERE id = ?";
            Organisation organisation = jdbc.queryForObject(SELECT_ORGANISATION_BY_ID,
                    new OrganisationMapper(), id);
            organisation.setHeroes(getAllHeroesFromOrganisation(organisation));
            return organisation;
        } catch(DataAccessException ex) {
            return null;
        }

    }

    @Override
    @Transactional
    public void update(Organisation organisation) {

        final String UPDATE_ORGANISATION = "UPDATE organisation  "
                + "SET name = ?, description = ?, address = ?, contact = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANISATION,
                organisation.getName(),
                organisation.getDescription(),
                organisation.getAddress(),
                organisation.getContact(),
                organisation.getId());


        final String DELETE_HERO_ORGANISATION = "DELETE FROM hero_organisation "
                + "WHERE organisationId = ?";
        jdbc.update(DELETE_HERO_ORGANISATION, organisation.getId());
        insertHeroOrganisation(organisation);

    }

    @Override
    @Transactional
    public boolean delete(Organisation organisation) {

        final String DELETE_HERO_ORGANISATION = "DELETE FROM hero_organisation "
                + "WHERE organisationId = ?";
        jdbc.update(DELETE_HERO_ORGANISATION, organisation.getId());

        final String DELETE_LOCATION = "DELETE FROM organisation "
                + "WHERE id = ?";
        jdbc.update(DELETE_LOCATION, organisation.getId());
        return true;
    }

    @Override
    public List<Organisation> getAllOrganisationsFromHero(Hero hero) {

        final String SELECT_ORGANISATION_BY_HERO = "SELECT o.* FROM organisation o "
                + "JOIN hero_organisation ho ON o.id = ho.organisationId WHERE ho.heroId = ?";
        List<Organisation> organisations = jdbc.query(SELECT_ORGANISATION_BY_HERO,
                new OrganisationMapper(), hero.getId());

        addHeroesToOrganisation(organisations);

        return organisations;


    }

    @Override
    public List<Hero> getAllHeroesFromOrganisation(Organisation organisation) {

        final String SELECT_HERO_BY_ORGANISATION = "SELECT h.* FROM hero h "
                + "JOIN hero_organisation ho ON h.id = ho.heroId WHERE ho.organisationId = ?";
        List<Hero> heroes = jdbc.query(SELECT_HERO_BY_ORGANISATION,
                new HeroDaoImpl.HeroMapper(), organisation.getId());

        return heroes;
    }

    final private static class OrganisationMapper implements RowMapper<Organisation>{

        @Override
        public Organisation mapRow(ResultSet rs, int index) throws SQLException {
            Organisation organisation = new Organisation();
            organisation.setId(rs.getInt("id"));
            organisation.setName(rs.getString("name"));
            organisation.setDescription(rs.getString("description"));
            organisation.setAddress(rs.getString("address"));
            organisation.setContact(rs.getString("contact"));


            return organisation;
        }

    }


}
