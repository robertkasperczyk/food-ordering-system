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
    private final ResourceBundle bundle;


    public FoodOrderingSystem(FoodDataSource dataSource, InputStream in) {

        bundle = ResourceBundle.getBundle("strings/strings");
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
        System.out.println(bundle.getString("welcome"));
        boolean orderReady = false;

        while (!orderReady) {
            orderReady = actionChooser();
        }
        return new Bill(this.order, bundle);
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
            System.out.println(bundle.getString("orderBaseChoice"));
            if (!order.isEmpty()) {
                System.out.println(bundle.getString("placeOrder"));
            }
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(bundle.getString("wrongInput"));
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
                    order.add(new MainCourse(lunch.getMainCourse()));
                    order.add(new Dessert(lunch.getDessert()));
                    break;
                case 3:
                    if (!order.isEmpty()) {
                        orderReady = true;
                        break;
                    }
                default:
                    System.out.println(bundle.getString("wrongNumber"));
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
        System.out.println(bundle.getString("chooseDrink"));
        List<Drink> drinks = new ArrayList<>(dataSource.getListOfDrinks());
        Drink drink = new Drink((Drink) elementChooser(drinks));
        boolean correctChoiceProvided = false;
        int choice;
        System.out.println(bundle.getString("lemonOrIce"));
        System.out.println(bundle.getString("lemonIceOptions"));
        while (!correctChoiceProvided) {
            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println(bundle.getString("wrongInput"));
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
                    System.out.println(bundle.getString("wrongNumber"));
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
            System.out.println(bundle.getString("noProductsFromCuisine"));
            return null;
        }
        System.out.println(bundle.getString("chooseDessert"));
        return (Dessert) elementChooser(desserts);

    }

    /**
     * cuisineChooser() presents list of available cuisines and allow user to choose one
     *
     * @return Cuisine chosen by user
     */
    private Cuisine cuisineChooser() {
        System.out.println(bundle.getString("chooseCuisine"));
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
                System.out.println(bundle.getString("wrongInput"));
                scanner.next();
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(bundle.getString("wrongNumber"));
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
            System.out.println(bundle.getString("noProductsFromCuisine"));
            return null;
        }
        System.out.println(bundle.getString("chooseMainCourse"));
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
                System.out.println(bundle.getString("wrongInput"));
                scanner.next();
            } catch (IndexOutOfBoundsException e) {
                System.out.println(bundle.getString("wrongNumber"));
            }
        }
        return meal;
    }
}