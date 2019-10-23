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
    private enum type{
        VODKA, GIN, RUM, TEQUILA, WHISKEY, CORDIAL, WINE, MIXER, GARNISH
    }

    public Ingredient (int id, String name) {
        this();
        this.id = id;
        this.name = name;
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
}
