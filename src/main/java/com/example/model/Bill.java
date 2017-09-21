package com.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class Bill {
    private final List<Orderable> listOfOrders;
    private final double sum;
    private final LocalDateTime dateTime;
    private final ResourceBundle bundle;

    public Bill(List<Orderable> listOfOrders, ResourceBundle bundle) {
        this.bundle = bundle;
        if (listOfOrders == null || listOfOrders.isEmpty()) {
            throw new IllegalArgumentException("Cannot create a bill from null or empty list!");
        }
        this.listOfOrders = listOfOrders;
        this.sum = listOfOrders.stream().mapToDouble(Orderable::getPrice).sum();
        this.dateTime = LocalDateTime.now();
    }

    public List<Orderable> getListOfOrders() {
        return listOfOrders;
    }

    public double getSum() {
        return sum;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 55; i++) {
            stringBuilder.append("_");
        }
        stringBuilder.append("\n"+bundle.getString("restaurantName")+"\n"+bundle.getString("city")+"\n\n");
        listOfOrders.forEach(order -> stringBuilder.append(order.getMenuEntry()));
        stringBuilder.append("\n"+bundle.getString("total"));
        for (int i = 0; i < 44; i++) {
            stringBuilder.append(" ");
        }
        stringBuilder.append(sum).append("$\n");
        stringBuilder.append(dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))).append("\n");
        for (int i = 0; i < 55; i++) {
            stringBuilder.append("_");
        }
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}
