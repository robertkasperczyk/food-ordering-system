package com.example.model;

public interface Orderable {

    double getPrice();

    String getName();

    default String getMenuEntry() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        for (int i = 0; i < 50 - getName().length(); i++) {
            stringBuilder.append(" ");
        }
        stringBuilder.append(getPrice());
        stringBuilder.append("$\n");
        return stringBuilder.toString();
    }


}
