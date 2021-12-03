package com.sg.SuperHeroSightings;

import com.sg.SuperHeroSightings.data.HeroDao;
import com.sg.SuperHeroSightings.data.OrganisationDao;
import com.sg.SuperHeroSightings.data.SuperpowerDao;
import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Organisation;
import com.sg.SuperHeroSightings.model.Superpower;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.sg.SuperHeroSightings.TestApplicationConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class OrganisationDaoImplTest {

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganisationDao organisationDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Before
    public void setUp() {

        List<Organisation> organisations = organisationDao.getAll();
        for (Organisation organisation : organisations){
            organisationDao.delete(organisation);
        }

        List<Hero> heroes = heroDao.getAll();
        for (Hero hero : heroes){
            heroDao.delete(hero);
        }

        List<Superpower> superpowers = superpowerDao.getAll();
        for (Superpower superpower : superpowers) {
            superpowerDao.delete(superpower);
        }

    }

    @Test
    public void testAdd() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);
        hero = heroDao.add(hero);

        Hero hero2 = new Hero();
        hero2.setName("Superman");
        hero2.setDescription("From Ohio");
        Superpower superpower2 = new Superpower();
        superpower2.setName("Flight");
        superpowerDao.add(superpower2);

        hero2.setSuperpower(superpower2);
        hero2 = heroDao.add(hero2);

        Organisation organisation = new Organisation();
        organisation.setName("Justice League");
        organisation.setDescription("Serves to protect the innocent");
        organisation.setAddress("Space");
        organisation.setContact("1-800-JUSTICE");
        organisation.setHeroes(heroDao.getAll());
        organisationDao.add(organisation);

        Organisation fromDao = organisationDao.getById(organisation.getId());

        assertEquals(organisation, fromDao);

    }

    @Test
    public void getAll() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);
        hero = heroDao.add(hero);

        Hero hero2 = new Hero();
        hero2.setName("Superman");
        hero2.setDescription("From Ohio");
        Superpower superpower2 = new Superpower();
        superpower2.setName("Flight");
        superpowerDao.add(superpower2);

        hero2.setSuperpower(superpower2);
        hero2 = heroDao.add(hero2);

        Organisation organisation = new Organisation();
        organisation.setName("Justice League");
        organisation.setDescription("Serves to protect the innocent");
        organisation.setAddress("Space");
        organisation.setContact("1-800-JUSTICE");
        organisation.setHeroes(heroDao.getAll());
        organisationDao.add(organisation);

        Hero hero3 = new Hero();
        hero3.setName("Iron Man");
        hero3.setDescription("Tony Stank");
        hero3.setSuperpower(superpower);
        hero3 = heroDao.add(hero3);

        Hero hero4 = new Hero();
        hero4.setName("Woleverine");
        hero4.setDescription("Canadian");
        Superpower superpower3 = new Superpower();
        superpower3.setName("Regeneration");
        superpowerDao.add(superpower3);

        hero4.setSuperpower(superpower3);
        hero4 = heroDao.add(hero4);

        Organisation organisation2 = new Organisation();
        organisation2.setName("Avengers");
        organisation2.setDescription("Revenge served fresh");
        organisation2.setAddress("Real Life New York City");
        organisation2.setContact("1-800-AVENGER");
        List<Hero> heroes2 = new ArrayList<Hero>();
        heroes2.add(hero3);
        heroes2.add(hero4);
        organisation2.setHeroes(heroes2);
        organisationDao.add(organisation2);

        List<Organisation> organisations = organisationDao.getAll();

        assertEquals(2, organisations.size());
        assertTrue(organisations.contains(organisation));
        assertTrue(organisations.contains(organisation2));



    }

    @Test
    public void testUpdate() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);
        hero = heroDao.add(hero);

        Hero hero2 = new Hero();
        hero2.setName("Superman");
        hero2.setDescription("From Ohio");
        Superpower superpower2 = new Superpower();
        superpower2.setName("Flight");
        superpowerDao.add(superpower2);

        hero2.setSuperpower(superpower2);
        hero2 = heroDao.add(hero2);

        Organisation organisation = new Organisation();
        organisation.setName("Justice League");
        organisation.setDescription("Serves to protect the innocent");
        organisation.setAddress("Space");
        organisation.setContact("1-800-JUSTICE");
        organisation.setHeroes(heroDao.getAll());
        organisationDao.add(organisation);

        Organisation fromDao = organisationDao.getById(organisation.getId());

        assertEquals(organisation, fromDao);

        organisation.setAddress("Falling on Earth");

        organisationDao.update(organisation);

        assertNotEquals(organisation, fromDao);

        fromDao = organisationDao.getById(organisation.getId());

        assertEquals(organisation, fromDao);

    }

    @Test
    public void testDelete() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);
        hero = heroDao.add(hero);

        Hero hero2 = new Hero();
        hero2.setName("Superman");
        hero2.setDescription("From Ohio");
        Superpower superpower2 = new Superpower();
        superpower2.setName("Flight");
        superpowerDao.add(superpower2);

        hero2.setSuperpower(superpower2);
        hero2 = heroDao.add(hero2);

        Organisation organisation = new Organisation();
        organisation.setName("Justice League");
        organisation.setDescription("Serves to protect the innocent");
        organisation.setAddress("Space");
        organisation.setContact("1-800-JUSTICE");
        organisation.setHeroes(heroDao.getAll());
        organisationDao.add(organisation);

        organisationDao.delete(organisation);

        Organisation fromDao = organisationDao.getById(organisation.getId());

        assertNull(fromDao);

    }

    @Test
    public void testGetAllOrganisationsFromHero() {

        Hero hero = new Hero();
        hero.setName("Woleverine");
        hero.setDescription("Canadian");
        Superpower superpower = new Superpower();
        superpower.setName("Regeneration");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);
        hero = heroDao.add(hero);

        Organisation organisation = new Organisation();
        organisation.setName("Avengers");
        organisation.setDescription("Revenge served fresh");
        organisation.setAddress("Real Life New York City");
        organisation.setContact("1-800-AVENGER");
        organisation.setHeroes(heroDao.getAll());
        organisationDao.add(organisation);

        Organisation organisation2 = new Organisation();
        organisation2.setName("X-Men");
        organisation2.setDescription("and x-women and x-non binaries too");
        organisation2.setAddress("Somewhere in Kentucky probably");
        organisation2.setContact("1-800-500XMEN");
        organisation2.setHeroes(heroDao.getAll());
        organisationDao.add(organisation2);

        List<Organisation> organisations = organisationDao.getAllOrganisationsFromHero(hero);

        assertEquals(2, organisations.size());
        assertTrue(organisations.contains(organisation));
        assertTrue(organisations.contains(organisation2));

    }

    @Test
    public void testGetAllHeroesFromOrganisation() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);
        hero = heroDao.add(hero);

        Hero hero2 = new Hero();
        hero2.setName("Superman");
        hero2.setDescription("From Ohio");
        Superpower superpower2 = new Superpower();
        superpower2.setName("Flight");
        superpowerDao.add(superpower2);

        hero2.setSuperpower(superpower2);        hero2 = heroDao.add(hero2);

        Organisation organisation = new Organisation();
        organisation.setName("Justice League");
        organisation.setDescription("Serves to protect the innocent");
        organisation.setAddress("Space");
        organisation.setContact("1-800-JUSTICE");
        organisation.setHeroes(heroDao.getAll());
        organisationDao.add(organisation);

        List<Hero> heroes = organisationDao.getAllHeroesFromOrganisation(organisation);

        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));

    }
}