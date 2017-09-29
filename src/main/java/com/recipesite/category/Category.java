package com.recipesite.category;

import com.recipesite.base.BaseEntity;

import javax.persistence.Entity;

@Entity
public class Category extends BaseEntity {
    private String name;

    public Category() {
        super();
    }

    public Category(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
