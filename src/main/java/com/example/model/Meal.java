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

    Meal(Meal meal) {
        this.price = meal.price;
        this.name = meal.name;
        this.cuisine = meal.cuisine;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Meal meal = (Meal) o;

        if (Double.compare(meal.price, price) != 0) return false;
        if (!name.equals(meal.name)) return false;
        return cuisine == meal.cuisine;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(price);
        result = (int) (temp ^ (temp >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + cuisine.hashCode();
        return result;
    }
}
