package org.launchcode.Bartender.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=30)
    private String name;

    @NotNull
    private Type type;

    public enum Type{
        Vodka, Gin, Rum, Tequila, Whiskey, Cordial, Wine, Mixer, Bitters, Garnish
    }

    public Ingredient (int id, String name, Type type) {
        this();
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @ManyToMany(mappedBy = "myBar")
    private List<User> users;

    public Ingredient() {}

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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}


