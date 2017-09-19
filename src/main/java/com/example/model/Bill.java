package com.example.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Bill {
    private final List<Orderable> listOfOrders;
    private final double sum;
    private final LocalDateTime dateTime;

    public Bill(List<Orderable> listOfOrders) {
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
        stringBuilder.append("\nYummy Restaurant\nCracow\n\n");
        listOfOrders.forEach(order -> stringBuilder.append(order.getMenuEntry()));
        stringBuilder.append("\ntotal:");
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
