package org.example;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    protected String name;
    protected String client_number;
    protected String phone;
    public List<Basket> baskets;

    public Customer(String name, String client_number, String phone) {
        this.name = name;
        this.client_number = client_number;
        this.phone = phone;
        this.baskets = new ArrayList<>();
    }

    public String basketsInfo() {
        StringBuilder finalText = new StringBuilder();
        if (baskets.size() > 0) {
            for (int i = 0; i < baskets.size(); i++) {
                finalText.append(i + 1).append(". ").append(baskets.get(i));
                finalText.append("\n");
            }
        } else {
            finalText.append("\n");
        }
        return finalText.toString();
    }
}
