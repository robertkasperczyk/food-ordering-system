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

    /**
     * Main loop of food ordering system, stops when user chooses to place his order.
     *
     * @return Bill object containing list of orders.
     */
    public Bill startSystem() {
        System.out.println("Welcome in our restaurant!");
        boolean orderReady = false;

        while (!orderReady) {
            orderReady = actionChooser();
        }
        return new Bill(this.order);
    }

    /**
     * actionChooser() allows user to choose from ordering a Drink or a Lunch or placing his order.
     * User can place his order only when order is not empty.
     *
     * @return True if user decided to place his order and False when user want to order another product.
     */
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
                    Lunch lunch = lunchChooser();
                    order.add(lunch.getDessert());
                    order.add(lunch.getMainCourse());
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

    /**
     * drinkChooser() is responsible for getting list of Drinks which belong to given cuisine.
     * Then allows user to choose one drink.
     * Finally present to user choice of getting additional lemon or/and ice cubes to drink.
     *
     * @return Drink object chosen by user.
     */
    private Drink drinkChooser() {
        System.out.println("Please choose a drink:");
        List<Drink> drinks = new ArrayList<>(dataSource.getListOfDrinks());
        Drink drink = (Drink) elementChooser(drinks);
        boolean correctChoiceProvided = false;
        int choice;
        System.out.println("Would you like to add an ice or lemon to your drink?");
        System.out.println("(1) Ice");
        System.out.println("(2) Lemon");
        System.out.println("(3) Both");
        System.out.println("(4) None");
        while (!correctChoiceProvided) {
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


    /**
     * lunchChooser task is to complete the lunch, firstly get MainCourse object, then Dessert from user.
     *
     * @return Lunch object, which contain MainCourse and Dessert
     */
    private Lunch lunchChooser() {
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
        return new Lunch(dessert, mainCourse);
    }

    /**
     * desertChooser() is responsible for getting list of Desserts which belong to given cuisine
     *
     * @param cuisine chosen by user cuisine
     * @return Dessert chosen by user in elementChooser() method
     */
    private Dessert dessertChooser(Cuisine cuisine) {
        List<Dessert> desserts = new ArrayList<>(dataSource.getListOfDesserts(cuisine));
        if (desserts == null || desserts.isEmpty()) {
            System.out.println("Unfortunately, we have no products from chosen cuisine, please choose other one.\n");
            return null;
        }
        System.out.println("Please, choose a dessert:");
        return (Dessert) elementChooser(desserts);

    }

    /**
     * cuisineChooser() presents list of available cuisines and allow user to choose one
     *
     * @return Cuisine chosen by user
     */
    private Cuisine cuisineChooser() {
        System.out.println("Please choose a cuisine:");
        Cuisine[] cuisines = Cuisine.values();
        IntStream.range(1, cuisines.length + 1).forEach(i ->
                System.out.println("(" + i + ") " + cuisines[i - 1])
        );
        Cuisine cuisine = null;
        while (cuisine == null) {
            try {
                int choice = scanner.nextInt();
                cuisine = cuisines[choice - 1];
            } catch (InputMismatchException e) {
                System.out.println("You must have provided wrong input, please try again!");
                scanner.next();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("You must have provided wrong number, please try again!");
            }
        }
        return cuisine;
    }

    /**
     * mainCourseChooser() is responsible for getting list of MainCourses which belong to given cuisine
     *
     * @param cuisine chosen by user cuisine
     * @return MainCourse choosen by user in elementChooser
     */
    private MainCourse mainCourseChooser(Cuisine cuisine) {
        List<MainCourse> mainCourses = new ArrayList<>(dataSource.getListOfMainCourses(cuisine));
        if (mainCourses == null || mainCourses.isEmpty()) {
            System.out.println("Unfortunately, we have no products from chosen cuisine, please choose other one.\n");
            return null;
        }
        System.out.println("Please, choose a main course:");
        return (MainCourse) elementChooser(mainCourses);
    }

    /**
     * elementChooser() firstly presents list of elements available to order and then get user input and return chosen object
     *
     * @param meals list of filtered by cuisine Orderables
     * @return Dessert, MainCourse or Drink choosen by user
     */
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
                System.out.println("You must have provided a wrong input, please try again!");
                scanner.next();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You must have provided a wrong number, please try again!");
            }
        }
        return meal;
    }
}