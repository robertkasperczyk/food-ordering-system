package com.example;


import com.example.datasources.FoodDataSource;
import com.example.datasources.StubFoodDataSource;
import com.example.model.Cuisine;
import com.example.model.Dessert;
import com.example.model.Drink;
import com.example.model.MainCourse;

import java.util.ArrayList;
import java.util.Arrays;

class Application {


    public static void main(String[] args) {
        Drink cola = new Drink("Coca-cola", 5);
        Drink water = new Drink("Sparkling water", 3);

        MainCourse tacos = new MainCourse("Chilli con carne tacos", 10, Cuisine.MEXICAN);
        MainCourse quesadilla = new MainCourse("Quesadilla", 8.5, Cuisine.MEXICAN);
        MainCourse burrito = new MainCourse("Burrito", 9.5, Cuisine.MEXICAN);

        Dessert iceCream = new Dessert("Pistacio ice cream", 6, Cuisine.ITALIAN);
        Dessert cheeseCake = new Dessert("Cheese cake", 5, Cuisine.POLISH);


        FoodDataSource foodDataSource = new StubFoodDataSource(
                new ArrayList<>(Arrays.asList(cola, water)),
                new ArrayList<>(Arrays.asList(tacos, quesadilla, burrito)),
                new ArrayList<>(Arrays.asList(iceCream, cheeseCake)));


        FoodOrderingSystem foodOrderingSystem = new FoodOrderingSystem(foodDataSource, System.in);
        System.out.println(foodOrderingSystem.startSystem());
    }
}

