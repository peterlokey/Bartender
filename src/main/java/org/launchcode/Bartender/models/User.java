package org.launchcode.Bartender.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Column(unique=true)
    @Size(min=4, max=12)
    private String name;

    @NotNull
    @Column(unique=true)
    private String email;

    @NotNull
    private String password;

    @ManyToMany
    private List<Ingredient> myBar;

    @ManyToMany
    private List<Drink> drinks;

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings = new ArrayList<>();

    public User (int id, String name, String email, String password) {
        this();
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;

    }

    public User() {}

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Ingredient> getMyBar() {
        return myBar;
    }

    public void setMyBar(List<Ingredient> myBar) {
        this.myBar = myBar;
    }

    public void addToMyBar(Ingredient ingredient){
        this.myBar.add(ingredient);
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
    }

    public void addToDrinks(Drink drink) {
        this.drinks.add(drink);
    }

    public void removeFromDrinks(Drink drink) {
        this.drinks.remove(drink);
    }
}

