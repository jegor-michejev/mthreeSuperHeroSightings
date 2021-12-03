package com.sg.SuperHeroSightings.model;

import java.time.LocalDate;
import java.util.Objects;

public class Sighting {

    private int id;
    private LocalDate date;
    private Hero hero;
    private Location location;

    @Override
    public String toString() {
        return "Sighting{" +
                "id=" + id +
                ", date=" + date +
                ", hero=" + hero +
                ", location=" + location +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return id == sighting.id && date.equals(sighting.date) && hero.equals(sighting.hero) && location.equals(sighting.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, hero, location);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
