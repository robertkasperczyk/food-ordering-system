package com.example.model;

public abstract class Meal implements Orderable {

    private final double price;
    private final String name;

    private final Cuisine cuisine;

    public Cuisine getCuisine() {
        return cuisine;
    }

    Meal(String name, double price, Cuisine cuisine) {
        this.price = price;
        this.name = name;
        this.cuisine = cuisine;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }
}
