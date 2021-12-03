package com.sg.SuperHeroSightings.model;

import java.util.Objects;

public class Hero {

    private int id;
    private String name;
    private String description;
    private Superpower superpower;

    @Override
    public String toString() {
        return "hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", superpower='" + superpower + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return id == hero.id && name.equals(hero.name) && Objects.equals(description, hero.description) && Objects.equals(superpower, hero.superpower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, superpower);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) { this.superpower = superpower; }

}
