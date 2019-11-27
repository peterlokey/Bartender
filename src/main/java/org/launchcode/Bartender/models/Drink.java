package org.launchcode.Bartender.models;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Drink {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min = 3, max = 20)
    private String name;

    @ElementCollection
    @MapKeyColumn(name="measurement")
    @Column(name = "ingredient")
    private Map<String, String> recipe = new LinkedHashMap<String, String>();    //Key - Measurement, Value - IngredientId

    private String instructions;

    private GlassType glassType;
    public enum GlassType {
        HIGHBALL, COCKTAIL, MARTINI, ROCKS, SHOT, PINT, WINE
    }

    private ChillType chillType;
    public enum ChillType {
        ROCKS, UP, NEAT
    }

    private MixType mixType;
    public enum MixType {
        SHAKE, STIR, BUILD
    }

    private String clipart;

    @ManyToMany(mappedBy = "drinks")
    private List<User> users;

    @OneToMany(mappedBy = "drink")
    private List<Rating> ratings = new ArrayList<>();

    public Drink (int id, String name, Map<String, String> recipe, String instructions, GlassType glassType,
                  ChillType chillType, MixType mixType){
        this.id = id;
        this.name = name;
        this.recipe = recipe;
        this.instructions = instructions;
        this.glassType = glassType;
        this.chillType = chillType;
        this.mixType = mixType;

    }

    public Drink () {}

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

    public Map<String, String> getRecipe() {
        return recipe;
    }

    public void setRecipe(Map<String, String> recipe) {
        this.recipe = recipe;
    }

    public String getInstructions() {
        return instructions;
    }

    public GlassType getGlassType() {
        return glassType;
    }

    public void setGlassType(GlassType glassType) {
        this.glassType = glassType;
    }

    public ChillType getChillType() {
        return chillType;
    }

    public void setChillType(ChillType chillType) {
        this.chillType = chillType;
    }

    public MixType getMixType() {
        return mixType;
    }

    public void setMixType(MixType mixType) {
        this.mixType = mixType;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public List<User> getUsers() {
        return users;
    }

    public String getClipart() {
        return clipart;
    }

    public void setClipart(String clipart) {
        this.clipart = clipart;
    }
}

