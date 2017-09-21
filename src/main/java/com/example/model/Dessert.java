package com.example.model;

public class Dessert extends Meal {

    public Dessert(String name, double price, Cuisine cuisine) {
        super(name, price, cuisine);
    }

    public Dessert(Meal meal) {
        super(meal);
    }

}
