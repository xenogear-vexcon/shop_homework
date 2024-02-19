package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

public class Product {
    protected String name;
    protected int price;
    public List<Integer> rating = new ArrayList<>();

    public Product(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public void add_rating(int score) {
        rating.add(score);
    }

    public String ratingInfo() {
        OptionalDouble average = rating.stream().mapToDouble(a -> a).average();
        double finalAverage = average.isPresent() ? average.getAsDouble() : 0.00;
        return "Рейтинг: " + finalAverage;
    }
}
