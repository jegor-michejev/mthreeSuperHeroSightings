package com.sg.SuperHeroSightings;

import com.sg.SuperHeroSightings.data.HeroDao;
import com.sg.SuperHeroSightings.data.SuperpowerDao;
import com.sg.SuperHeroSightings.model.Hero;
import com.sg.SuperHeroSightings.model.Superpower;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class HeroDaoImplTest {

    @Autowired
    HeroDao heroDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Before
    public void setUp() {

        List<Hero> heroes = heroDao.getAll();
        for (Hero hero : heroes) {
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

        Hero fromDao = heroDao.getById(hero.getId());

        assertEquals(hero, fromDao);

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

        Hero hero2 = new Hero();
        hero2.setName("Superman");
        hero2.setDescription("From Ohio");

        Superpower superpower2 = new Superpower();
        superpower2.setName("Flight");
        superpowerDao.add(superpower2);

        hero2.setSuperpower(superpower2);

        hero2 = heroDao.add(hero2);

        List<Hero> heroes = heroDao.getAll();

        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(hero));
        assertTrue(heroes.contains(hero2));

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

        Hero fromDao = heroDao.getById(hero.getId());

        assertEquals(hero, fromDao);

        hero.setDescription("Named Bruce");

        heroDao.update(hero);

        assertNotEquals(hero, fromDao);

        fromDao = heroDao.getById(hero.getId());

        assertEquals(hero, fromDao);

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

        heroDao.delete(hero);

        Hero fromDao = heroDao.getById(hero.getId());

        assertNull(fromDao);

    }
}