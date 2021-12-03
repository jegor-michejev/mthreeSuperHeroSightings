package com.sg.SuperHeroSightings.model;

import java.util.List;
import java.util.Objects;

public class Organisation {

    private int id;
    private String name;
    private String description;
    private String address;
    private String contact;
    private List<Hero> heroes;

    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", heroes=" + heroes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return id == that.id && name.equals(that.name) && Objects.equals(description, that.description) && address.equals(that.address) && contact.equals(that.contact) && Objects.equals(heroes, that.heroes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, address, contact, heroes);
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<Hero> getHeroes() {
        return heroes;
    }

    public void setHeroes(List<Hero> heroes) {
        this.heroes = heroes;
    }
}
