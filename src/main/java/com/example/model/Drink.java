package com.example.model;

public class Drink implements Orderable {
    private boolean iceCubes = false;
    private boolean lemon = false;
    private final String name;
    private final double price;

    public Drink(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Drink(Drink drink) {
        this.name = drink.name;
        this.price = drink.price;
        this.iceCubes = drink.iceCubes;
        this.lemon = drink.lemon;
    }

    public boolean isIceCubes() {
        return iceCubes;
    }

    public boolean isLemon() {
        return lemon;
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void addIceCubes() {
        iceCubes = true;
    }

    public void addLemon() {
        lemon = true;
    }

    @Override
    public String getMenuEntry() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        int lineLength = 50;
        if (iceCubes) {
            stringBuilder.append(" + ice cubes");
            lineLength -= " + ice cubes".length();
        }
        if (lemon) {
            stringBuilder.append(" + lemon");
            lineLength -= " + lemon".length();
        }
        for (int i = 0; i < lineLength - getName().length(); i++) {
            stringBuilder.append(" ");
        }
        stringBuilder.append(getPrice());
        stringBuilder.append("$\n");
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Drink drink = (Drink) o;

        if (Double.compare(drink.price, price) != 0) return false;
        return name.equals(drink.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
