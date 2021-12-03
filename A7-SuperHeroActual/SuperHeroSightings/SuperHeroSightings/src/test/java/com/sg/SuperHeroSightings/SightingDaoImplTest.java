package com.sg.SuperHeroSightings;

import com.sg.SuperHeroSightings.data.HeroDao;
import com.sg.SuperHeroSightings.data.LocationDao;
import com.sg.SuperHeroSightings.data.SightingDao;
import com.sg.SuperHeroSightings.data.SuperpowerDao;
import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Location;
import com.sg.SuperHeroSightings.model.Sighting;
import com.sg.SuperHeroSightings.model.Superpower;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.sg.SuperHeroSightings.TestApplicationConfiguration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class SightingDaoImplTest {

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Before
    public void setUp() {

        List<Hero> heroes = heroDao.getAll();
        for (Hero hero : heroes) {
            heroDao.delete(hero);
        }

        List<Location> locations = locationDao.getAll();
        for (Location loaction : locations) {
            locationDao.delete(loaction);
        }

        List<Sighting> sightings = sightingDao.getAll();
        for (Sighting sighting : sightings) {
            sightingDao.delete(sighting);
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

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.add(sighting);

        Sighting fromDao = sightingDao.getById(sighting.getId());

        assertEquals(sighting, fromDao);

    }

    @Test
    public void testGetAll() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);
        hero = heroDao.add(hero);

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.add(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setHero(hero);
        sighting2.setLocation(location);
        sighting2.setDate(LocalDate.now());
        sightingDao.add(sighting2);

        List<Sighting> sightings = sightingDao.getAll();

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));


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

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.add(sighting);

        Sighting fromDao = sightingDao.getById(sighting.getId());

        assertEquals(sighting, fromDao);

        Hero hero2 = new Hero();
        hero2.setName("Superman");
        hero2.setDescription("From Ohio");
        Superpower superpower2 = new Superpower();
        superpower2.setName("Flight");
        superpowerDao.add(superpower2);

        hero2.setSuperpower(superpower2);
        hero2 = heroDao.add(hero2);

        sighting.setHero(hero2);

        sightingDao.update(sighting);

        assertNotEquals(sighting, fromDao);

        fromDao = sightingDao.getById(sighting.getId());

        assertEquals(sighting, fromDao);

    }

    @Test
    public void testDelete() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);        hero = heroDao.add(hero);

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.add(sighting);

        sightingDao.delete(sighting);

        Sighting fromDao = sightingDao.getById(sighting.getId());

        assertNull(fromDao);

    }

    @Test
    public void testGetAllSightingsFromLocation() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);        hero = heroDao.add(hero);

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.add(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setHero(hero);
        sighting2.setLocation(location);
        sighting2.setDate(LocalDate.now());
        sightingDao.add(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightingsFromLocation(location);

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));

    }

    @Test
    public void getAllSightingsFromHero() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);        hero = heroDao.add(hero);

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.add(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setHero(hero);
        sighting2.setLocation(location);
        sighting2.setDate(LocalDate.now());
        sightingDao.add(sighting2);

        List<Sighting> sightings = sightingDao.getAllSightingsFromHero(hero);

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));

    }

    @Test
    public void testGetSightingsByDate() {

        Hero hero = new Hero();
        hero.setName("Batman");
        hero.setDescription("Has a Batmobile");
        Superpower superpower = new Superpower();
        superpower.setName("Rich");
        superpowerDao.add(superpower);

        hero.setSuperpower(superpower);        hero = heroDao.add(hero);

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Sighting sighting = new Sighting();
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setDate(LocalDate.now());
        sightingDao.add(sighting);

        Sighting sighting2 = new Sighting();
        sighting2.setHero(hero);
        sighting2.setLocation(location);
        sighting2.setDate(sighting.getDate());
        sightingDao.add(sighting2);

        List<Sighting> sightings = sightingDao.getSightingsByDate(sighting.getDate());

        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(sighting));
        assertTrue(sightings.contains(sighting2));

    }
}