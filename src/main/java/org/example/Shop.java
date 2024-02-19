package org.example;

public class Shop {
    protected String name;
    protected String description;
    public Basket stock;

    public Shop(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void setStock() {
        stock = new Basket(this);
    }

    public Shop fillStock(Product product, int count) {
        stock.addProduct(product, count);
        return this;
    }

    public void takeFromStock(Product product, int count) {
        stock.removeProduct(product, count);
    }

    public String getStockInfo() {
        return stock.info("В магазине у нас есть:\n", false);
    }

    public Product findProductByName(String name) {
        return stock.findProductByName(name);
    }
}
