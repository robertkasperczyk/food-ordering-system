package com.example.datasources;

import com.example.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class StubFoodDataSource implements FoodDataSource {
    private final List<Drink> drinks;
    private final List<MainCourse> mainCourses;
    private final List<Dessert> desserts;

    public StubFoodDataSource(List<Drink> drinks, List<MainCourse> mainCourses, List<Dessert> desserts) {
        this.drinks = drinks;
        this.mainCourses = mainCourses;
        this.desserts = desserts;
    }


    @Override
    public List<Drink> getListOfDrinks() {
        return drinks;
    }

    @Override
    public List<MainCourse> getListOfMainCourses(Cuisine cuisine) {
        return (List<MainCourse>) filterByCuisine(mainCourses, cuisine);
    }

    @Override
    public List<Dessert> getListOfDesserts(Cuisine cuisine) {
        return (List<Dessert>) filterByCuisine(desserts, cuisine);
    }

    private List<? extends Meal> filterByCuisine(List<? extends Meal> meals, Cuisine cuisine) {
        return meals.stream()
                .filter(meal -> meal.getCuisine() == cuisine)
                .collect(Collectors.toList());
    }
}
