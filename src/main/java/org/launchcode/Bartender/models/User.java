package org.launchcode.Bartender.models;

import org.launchcode.Bartender.models.data.UserDao;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

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

    private ArrayList<Integer> myBar;

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

    public ArrayList<Integer> getMyBar() {
        return myBar;
    }

    public void setMyBar(ArrayList<Integer> myBar) {
        this.myBar = myBar;
    }
}

