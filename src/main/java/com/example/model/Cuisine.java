package com.example.model;

public enum Cuisine {
    POLISH("Polish"),
    MEXICAN("Mexican"),
    ITALIAN("Italian");

    final String name;

    Cuisine(String name){
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
