package com.recipesite.ingredient;

import com.recipesite.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Ingredient extends BaseEntity {
    private String name;
    private String condition;
    private int quantity;

    public Ingredient() {
        super();
    }

    public Ingredient(String name, String condition, int quantity) {
        this();
        this.name = name;
        this.condition = condition;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCondition() { return condition; }

    public void setCondition(String condition) { this.condition = condition; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
