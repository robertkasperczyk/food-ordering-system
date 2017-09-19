package com.example;

import com.example.datasources.FoodDataSource;
import com.example.model.*;

import java.io.InputStream;
import java.util.*;
import java.util.stream.IntStream;

class FoodOrderingSystem {
    private final FoodDataSource dataSource;
    private final List<Orderable> order;
    private final Scanner scanner;


    public FoodOrderingSystem(FoodDataSource dataSource, InputStream in) {
        this.dataSource = dataSource;
        this.order = new ArrayList<>();
        scanner = new Scanner(in);
    }

    public Bill startSystem() {
        System.out.println("Welcome in our restaurant!");
        boolean orderReady = false;

        while (!orderReady) {
            orderReady = actionChooser();
        }
        return new Bill(this.order);
    }

    private Drink drinkChooser() {
        System.out.println("Please choose a drink:");
        List<Drink> drinks = new ArrayList<>(dataSource.getListOfDrinks());
        Drink drink = (Drink) elementChooser(drinks);
        boolean correctChoiceProvided = false;
        int choice;
        while (!correctChoiceProvided) {
            System.out.println("Would you like to add an ice or lemon to your drink?");
            System.out.println("(1) Ice");
            System.out.println("(2) Lemon");
            System.out.println("(3) Both");
            System.out.println("(4) None");
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You must have provided wrong input, please try again!");
                scanner.next();
                continue;

            }
            correctChoiceProvided = true;
            switch (choice) {
                case 1:
                    drink.addIceCubes();
                    break;
                case 2:
                    drink.addLemon();
                    break;
                case 3:
                    drink.addLemon();
                    drink.addIceCubes();
                    break;
                case 4:
                    break;
                default:
                    System.out.println("You must have provided wrong number, please try again!");
                    correctChoiceProvided = false;
            }
        }
        return drink;
    }

    private boolean actionChooser() {
        boolean orderReady = false;
        int choice;
        boolean correctChoiceProvided = false;
        while (!correctChoiceProvided) {
            System.out.println("Would you like to order:");
            System.out.println("(1) Drink");
            System.out.println("(2) Lunch");
            if (!order.isEmpty()) {
                System.out.println("(3) I want to place my order.");
            }
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You must have provided wrong input, please try again!");
                scanner.next();
                continue;
            }
            correctChoiceProvided = true;
            switch (choice) {
                case 1:
                    this.order.add(drinkChooser());
                    break;
                case 2:
                    Cuisine cuisine;
                    MainCourse mainCourse = null;
                    while (mainCourse == null) {
                        System.out.print("Main course - ");
                        cuisine = cuisineChooser();
                        mainCourse = mainCourseChooser(cuisine);
                    }
                    this.order.add(mainCourse);

                    Dessert dessert = null;
                    while (dessert == null) {
                        System.out.print("Dessert - ");
                        cuisine = cuisineChooser();
                        dessert = dessertChooser(cuisine);
                    }
                    this.order.add(dessert);
                    break;
                case 3:
                    if (!order.isEmpty()) {
                        orderReady = true;
                        break;
                    }
                default:
                    System.out.println("You must have provided wrong number, please try again!");
                    correctChoiceProvided = false;
            }
        }
        return orderReady;
    }

    private Dessert dessertChooser(Cuisine cuisine) {
        List<Dessert> desserts = new ArrayList<>(dataSource.getListOfDesserts(cuisine));
        if (desserts == null || desserts.isEmpty()) {
            System.out.println("Unfortunately, we have no products from chosen cuisine, please choose other one.\n");
            return null;
        }
        System.out.println("Please, choose a dessert:");
        return (Dessert) elementChooser(desserts);

    }

    private Cuisine cuisineChooser() {
        System.out.println("Please choose a cuisine:");
        Cuisine[] cuisines = Cuisine.values();
        IntStream.range(1, cuisines.length + 1).forEach(i ->
                System.out.println("(" + i + ") " + cuisines[i - 1])
        );
        Cuisine cuisine = null;
        while (cuisine == null) {
            int choice = scanner.nextInt();
            try {
                cuisine = cuisines[choice - 1];
            } catch (ArrayIndexOutOfBoundsException | InputMismatchException e) {
                System.out.println("You must have provided wrong input, please try again!");
                scanner.next();
            }
        }
        return cuisine;
    }

    private MainCourse mainCourseChooser(Cuisine cuisine) {
        List<MainCourse> mainCourses = new ArrayList<>(dataSource.getListOfMainCourses(cuisine));
        if (mainCourses == null || mainCourses.isEmpty()) {
            System.out.println("Unfortunately, we have no products from chosen cuisine, please choose other one.\n");
            return null;
        }
        System.out.println("Please, choose a main course:");
        return (MainCourse) elementChooser(mainCourses);
    }

    private Orderable elementChooser(List<? extends Orderable> meals) {

        IntStream.range(1, meals.size() + 1).forEach(i ->
                System.out.print("(" + i + ") " + meals.get(i - 1).getMenuEntry())
        );
        Orderable meal = null;
        while (meal == null) {
            try {
                int choice = scanner.nextInt();
                meal = meals.get(choice - 1);
            } catch (InputMismatchException e) {
                System.out.println("You must have provided wrong input, please try again!");
                scanner.next();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You must have provided wrong number, please try again!");
            }
        }
        return meal;
    }
}



