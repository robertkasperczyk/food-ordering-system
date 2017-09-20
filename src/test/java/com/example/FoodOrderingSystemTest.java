package com.example;

import com.example.datasources.FoodDataSource;
import com.example.datasources.StubFoodDataSource;
import com.example.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FoodOrderingSystemTest {
    private FoodOrderingSystem foodOrderingSystem;
    private FoodDataSource foodDataSource;
    private MainCourse quesadilla;
    private Dessert iceCream;

    @Before
    public void setUp() throws Exception {
        Drink cola = new Drink("Coca-cola", 5);
        Drink water = new Drink("Sparkling water", 3);

        MainCourse tacos = new MainCourse("Chilli con carne tacos", 10, Cuisine.MEXICAN);
        quesadilla = new MainCourse("Quesadilla", 8.5, Cuisine.MEXICAN);
        MainCourse burrito = new MainCourse("Burrito", 9.5, Cuisine.MEXICAN);

        iceCream = new Dessert("Pistacio ice cream", 6, Cuisine.ITALIAN);
        Dessert cheeseCake = new Dessert("Cheese cake", 5, Cuisine.POLISH);

        foodDataSource = mock(FoodDataSource.class);
        when(foodDataSource.getListOfDesserts(Cuisine.POLISH)).thenReturn(Collections.singletonList(cheeseCake));
        when(foodDataSource.getListOfDesserts(Cuisine.ITALIAN)).thenReturn(Collections.singletonList(iceCream));
        when(foodDataSource.getListOfDrinks()).thenReturn(Arrays.asList(cola, water));
        when(foodDataSource.getListOfMainCourses(Cuisine.MEXICAN)).thenReturn(Arrays.asList(tacos,quesadilla,burrito));
//        foodDataSource = new StubFoodDataSource(new ArrayList<>(Arrays.asList(cola, water)),
//                new ArrayList<>(Arrays.asList(tacos, quesadilla, burrito)),
//                new ArrayList<>(Arrays.asList(iceCream, cheeseCake)));

    }

    @Test
    public void shouldReturnBillWithProperLunch() throws Exception {
        //given
        String input = "2\n2\n2\n3\n1\n3";
        foodOrderingSystem = new FoodOrderingSystem(foodDataSource, new ByteArrayInputStream(input.getBytes()));

        //when
        Bill bill = foodOrderingSystem.startSystem();

        //then
        assertThat(bill.getSum()).isEqualTo(14.5);
        assertThat(bill.getDateTime().toLocalDate()).isEquivalentAccordingToCompareTo(LocalDate.now());
        assertThat(bill.getListOfOrders()).isEqualTo(Arrays.asList(quesadilla, iceCream));
    }

    @Test
    public void shouldAskAgainIfTryToPlaceEmptyOrder() throws Exception {
        //given
        String input = "3\n2\n2\n2\n3\n1\n3";
        foodOrderingSystem = new FoodOrderingSystem(foodDataSource, new ByteArrayInputStream(input.getBytes()));

        //when
        Bill bill = foodOrderingSystem.startSystem();

        //then
        assertThat(bill.getSum()).isEqualTo(14.5);
        assertThat(bill.getDateTime().toLocalDate()).isEquivalentAccordingToCompareTo(LocalDate.now());
        assertThat(bill.getListOfOrders()).isEqualTo(Arrays.asList(quesadilla, iceCream));
    }

    @Test
    public void shouldAskAgainIfTryToInputLetter() throws Exception {
        //given
        String input = "b\n2\n2\n2\n3\n1\n3";
        foodOrderingSystem = new FoodOrderingSystem(foodDataSource, new ByteArrayInputStream(input.getBytes()));

        //when
        Bill bill = foodOrderingSystem.startSystem();

        //then
        assertThat(bill.getSum()).isEqualTo(14.5);
        assertThat(bill.getDateTime().toLocalDate()).isEquivalentAccordingToCompareTo(LocalDate.now());
        assertThat(bill.getListOfOrders()).isEqualTo(Arrays.asList(quesadilla, iceCream));
    }

}