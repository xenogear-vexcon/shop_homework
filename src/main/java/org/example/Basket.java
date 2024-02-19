package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Basket {
    public DeliveryStatus status;
    public Map<Product, Integer> products = new HashMap<>();
    public int total;
    private static int counter;
    public int orderNumber;
    public Shop shop;

    public Basket(Shop shop) {
        this.shop = shop;
        this.status = DeliveryStatus.ASSEMBLY;
        counter++;
        this.orderNumber = counter;
    }

    public Basket(Basket copyInstance) {
        this.shop = copyInstance.shop;
        this.status = DeliveryStatus.ASSEMBLY;
        this.products = copyInstance.products;
        counter++;
        this.orderNumber = counter;
        this.total = copyInstance.total;
    }

    public String add(Product product, int count) {
        if (count <= 0) {
            throw new IllegalStateException("Количетсво товара должно быть больше 0!");
        }
        if (shop.stock.products.get(product) < count) {
            throw new IllegalStateException("Нет такого количетсва товаров");
        }
        addProduct(product, count);
        total += product.price * count;
        shop.takeFromStock(product, count);
        return "добавлен продукт: " + product.name + "(цена " + product.price + ")" + " в количестве " + count + ". Тотал: " + total;
    }

    public void addProduct(Product product, int count) {
        if (products.containsKey(product)) {
            products.replace(product, products.get(product) + count);
        } else {
            products.put(product, count);
        }
    }

    public String remove(Product product, int count) {
        if (count <= 0) {
            throw new IllegalStateException("Количетсво товара должно быть больше 0!");
        }
        if (products.get(product) < count) {
            throw new IllegalStateException("Нет такого количества!");
        }
        removeProduct(product, count);
        total -= product.price * count;
        shop.fillStock(product, count);
        return "удалён продукт: " + product.name + "(" + product.price + ")" + " в количестве " + count + ". Тотал: " + total;
    }

    public void removeProduct(Product product, int count) {
        if (products.get(product) > count) {
            products.replace(product, products.get(product) - count);
        } else if (products.get(product) == count) {
            products.remove(product);
        }
    }

    public String info(String message, Boolean showTotal) {
        StringBuilder finalText = new StringBuilder();
        finalText.append(message);
        for (Product product : products.keySet()) {
            int count = products.get(product);
            finalText.append("Продукт ")
                    .append(product.name)
                    .append("(цена ")
                    .append(product.price)
                    .append(") в количестве ")
                    .append(count)
                    .append(". ")
                    .append(product.ratingInfo());
            finalText.append("\n");
        }
        if (showTotal) {
            finalText.append("Тотал: \n").append(total);
        }
        return finalText.toString();
    }

    public Product findProductByName(String name) {
        List<Product> findProducts = new ArrayList<>();
        for (Product product : products.keySet()) {
            if (product.name.equals(name)) {
                findProducts.add(product);
            }
        }
        return findProducts.get(0);
    }

    public void changeStatus() {
        if (status == DeliveryStatus.ASSEMBLY) {
            setStatus(DeliveryStatus.ON_THE_WAY);
        } else if (status == DeliveryStatus.ON_THE_WAY) {
            setStatus(DeliveryStatus.DELIVERED);
        }
    }

    public DeliveryStatus getStatus() {
        return status;
    }

    public void setStatus(DeliveryStatus status) {
        this.status = status;
    }

    public String cancel() {
        if (status != DeliveryStatus.DELIVERED) {
            setStatus(DeliveryStatus.CANCELLED);
            for (Product product : products.keySet()) {
                shop.fillStock(product, products.get(product));
            }
            return "Отменено";
        } else {
            return "Уже доставлено, отменить невозможно";
        }
    }

    @Override
    public String toString() {
        return "Корзина " + orderNumber + " из \"" + shop.name + "\" с тоталом " + total + " и статусом \"" + status + "\"";
    }
}
