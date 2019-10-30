package org.launchcode.Bartender.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

@Entity
public class Ingredient {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=20)
    private String name;

    @NotNull
    private Type type;

    public enum Type{
        VODKA, GIN, RUM, TEQUILA, WHISKEY, CORDIAL, WINE, MIXER, BITTERS, GARNISH
    }

    public Ingredient (int id, String name, Type type) {
        this();
        this.id = id;
        this.name = name;
        this.type = type;
    }

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
