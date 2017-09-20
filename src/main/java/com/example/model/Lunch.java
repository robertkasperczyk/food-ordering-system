package com.example.model;

public class Lunch {
    private final Dessert dessert;
    private final MainCourse mainCourse;

    public Lunch(Dessert dessert, MainCourse mainCourse) {
        this.dessert = dessert;
        this.mainCourse = mainCourse;
    }

    public Dessert getDessert() {
        return dessert;
    }

    public MainCourse getMainCourse() {
        return mainCourse;
    }
}
