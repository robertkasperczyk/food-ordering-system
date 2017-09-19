package com.example.datasources;

import com.example.model.*;

import java.util.List;

public interface FoodDataSource {
    List<Drink> getListOfDrinks();

    List<MainCourse> getListOfMainCourses(Cuisine cuisine);

    List<Dessert> getListOfDesserts(Cuisine cuisine);

}
