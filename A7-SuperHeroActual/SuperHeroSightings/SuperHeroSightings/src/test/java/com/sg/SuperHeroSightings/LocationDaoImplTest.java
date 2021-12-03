package com.sg.SuperHeroSightings;

import com.sg.SuperHeroSightings.data.LocationDao;
import com.sg.SuperHeroSightings.model.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.sg.SuperHeroSightings.TestApplicationConfiguration;

import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class LocationDaoImplTest {

    @Autowired
    LocationDao locationDao;


    @Before
    public void setUp() {

        List<Location> locations = locationDao.getAll();
        for (Location location : locations) {
            locationDao.delete(location);
        }


    }

    @Test
    public void testAdd() {

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Location fromDao = locationDao.getById(location.getId());

        assertEquals(location, fromDao);

    }

    @Test
    public void testGetAll() {

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Location location2 = new Location();
        location2.setName("New York City");
        location2.setDescription("Real life New York City");
        location2.setAddress("2nd Avenue, New York, NY");
        location2.setCoordinates("40.7N 74.0W");
        locationDao.add(location2);

        List<Location> locations = locationDao.getAll();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(location));
        assertTrue(locations.contains(location2));

    }

    @Test
    public void testUpdate() {

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        Location fromDao = locationDao.getById(location.getId());

        assertEquals(location, fromDao);

        location.setDescription("Hull's gone");

        locationDao.update(location);

        assertNotEquals(location, fromDao);

        fromDao = locationDao.getById(location.getId());

        assertEquals(location, fromDao);

    }

    @Test
    public void testDelete() {

        Location location = new Location();
        location.setName("Hull");
        location.setDescription("what can I say");
        location.setAddress("HU1 3EA Hull");
        location.setCoordinates("53.7N 0.3W");
        locationDao.add(location);

        locationDao.delete(location);

        Location fromDao = locationDao.getById(location.getId());

        assertNull(fromDao);

    }
}