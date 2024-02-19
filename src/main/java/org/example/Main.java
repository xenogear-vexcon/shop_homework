package org.example;

import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static Product banana, milk, potato, bread, juice, rice;
    public static Shop shop;
    public static Customer customer;

    public static void main(String[] args) {
        fillData();

        System.out.println("Добро пожаловать в наш магазин " + shop.name);

        while (true) {
            System.out.println(mainMenu());
            String userValue = scanner.nextLine();
            if (userValue.equals("0")) {
                System.out.println("Будем рады видеть вас снова!");
                break;
            }
            switch (userValue) {
                case "1":
                    System.out.println(shop.getStockInfo());
                    break;
                case "2":
                    while (true) {
                        System.out.println(basketsInfo(customer));
                        String userValue2 = scanner.nextLine();
                        if (userValue2.equals("0")) {
                            break;
                        }
                        if (userValue2.equals("+")) {
                            customer.baskets.add(new Basket(shop));
                        } else {
                            Basket custBasket;
                            try {
                                custBasket = customer.baskets.get(Integer.parseInt(userValue2) - 1);
                            } catch (IndexOutOfBoundsException e) {
                                System.out.println("Нет такой корзины");
                                break;
                            }
                            if (custBasket.status.equals(DeliveryStatus.DELIVERED) || custBasket.status.equals(DeliveryStatus.CANCELLED)) {
                                while (true) {
                                    System.out.println(basketDeliveredDialogue());
                                    String userValue3 = scanner.nextLine();
                                    if (userValue3.equals("0")) {
                                        break;
                                    }
                                    switch (userValue3) {
                                        case "1":
                                            System.out.println(custBasket.info("В корзине:\n", true));
                                            break;
                                        case "2":
                                            System.out.println(custBasket.getStatus());
                                            break;
                                        case "3":
                                            if (custBasket.status.equals(DeliveryStatus.DELIVERED)) {
                                                customer.baskets.add(new Basket(custBasket));
                                            } else {
                                                custBasket.setStatus(DeliveryStatus.ASSEMBLY);
                                            }
                                            System.out.println("Успешно обновлено");
                                            break;
                                    }
                                }
                            } else {
                                boolean continueText = true;
                                while (continueText) {
                                    System.out.println(basketDialogue());
                                    String userValue3 = scanner.nextLine();
                                    if (userValue3.equals("0")) {
                                        break;
                                    }
                                    switch (userValue3) {
                                        case "1":
                                            System.out.println(shop.getStockInfo());
                                            break;
                                        case "2":
                                            System.out.print("Введите название продукта: ");
                                            String prodName = scanner.nextLine();
                                            Product product;
                                            try {
                                                product = shop.findProductByName(prodName);
                                            } catch (IndexOutOfBoundsException e) {
                                                System.out.println("нет такого продукта");
                                                break;
                                            }
                                            System.out.print("Введите количество продукта: ");
                                            int prodCount = Integer.parseInt(scanner.nextLine());
                                            try {
                                                System.out.println(custBasket.add(product, prodCount));
                                            } catch (IllegalStateException e) {
                                                System.out.println(e.getMessage());
                                                break;
                                            }
                                            break;
                                        case "3":
                                            System.out.print("Введите название продукта: ");
                                            String prodName2 = scanner.nextLine();
                                            Product product2;
                                            try {
                                                product2 = custBasket.findProductByName(prodName2);
                                            } catch (IndexOutOfBoundsException e) {
                                                System.out.println("нет такого продукта в корзине");
                                                break;
                                            }
                                            System.out.print("Введите количество продукта: ");
                                            int prodCount2 = Integer.parseInt(scanner.nextLine());
                                            try {
                                                System.out.println(custBasket.remove(product2, prodCount2));
                                            } catch (IllegalStateException e) {
                                                System.out.println(e.getMessage());
                                                break;
                                            }
                                            break;
                                        case "4":
                                            System.out.println(custBasket.info("В корзине:\n", true));
                                            break;
                                        case "5":
                                            if (!custBasket.products.isEmpty()) {
                                                for (int i = 0; i < DeliveryStatus.StatusChangedTimes; i++) {
                                                    new java.util.Timer().schedule(
                                                            new java.util.TimerTask() {
                                                                @Override
                                                                public void run() {
                                                                    custBasket.changeStatus();
                                                                }
                                                            }, DeliveryStatus.ChangeStatusTimer
                                                    );
                                                }
                                                System.out.println("Успешно оплачено!");
                                                continueText = false;
                                            } else {
                                                System.out.println("Нечего оплачивать!");
                                            }
                                            break;
                                        case "6":
                                            System.out.println(custBasket.getStatus());
                                            break;
                                        case "7":
                                            System.out.println(custBasket.cancel());
                                            continueText = false;
                                            break;
                                        case "8":
                                            customer.baskets.remove(custBasket);
                                            continueText = false;
                                            break;
                                        default:
                                            System.out.println("\nВведено некорректное значение!");
                                            break;
                                    }
                                }
                            }
                        }
                    }

                    break;
                case "3":
                    System.out.println("Введите название продукта: ");
                    String prodName = scanner.nextLine();
                    System.out.println("Оценка: ");
                    String prodRating = scanner.nextLine();
                    Product prod = shop.stock.findProductByName(prodName);
                    try {
                        int number = Integer.parseInt(prodRating);
                        prod.add_rating(number);
                    } catch (NumberFormatException e) {
                        System.out.println("\nВведено некорректное значение!");
                    }
                    break;
                default:
                    System.out.println("\nВведено некорректное значение!");
                    break;
            }
        }
    }

    public static void fillData() {
        banana = new Product("Banana", 15);
        milk = new Product("Milk", 120);
        potato = new Product("Potato", 5);
        bread = new Product("Bread", 65);
        juice = new Product("Juice", 55);
        rice = new Product("Rice", 115);

        shop = new Shop("Best shop", "Main shop in the street");
        shop.setStock();
        shop.fillStock(banana, 10)
                .fillStock(milk, 10)
                .fillStock(potato, 10)
                .fillStock(bread, 10)
                .fillStock(juice, 10)
                .fillStock(rice, 10);

        customer = new Customer("Joe", "000001", "89009998877");
    }

    public static String mainMenu() {
        return "Выберите действие:\n"
                + "1. Что есть в магазине\n"
                + "2. Выбрать корзину\n"
                + "3. Выставить рейтинг для товара\n"
                + "0. Выйти\n";
    }

    public static String basketsInfo(Customer customer) {
        return "Выберите действие:\n"
                + customer.basketsInfo()
                + "+ Создать новую корзину\n"
                + "0. Назад\n";
    }

    public static String basketDialogue() {
        return "Выберите действие:\n"
                + "1. Что есть в магазине\n"
                + "2. Добавить продукт в корзину\n"
                + "3. Убрать из корзины\n"
                + "4. Что есть в корзине\n"
                + "5. Оплатить\n"
                + "6. Узнать статус доставки\n"
                + "7. Отменить заказ (если заказ еще не доставлен)\n"
                + "8. Удалить\n"
                + "0. Назад\n";
    }

    public static String basketDeliveredDialogue() {
        return "Выберите действие:\n"
                + "1. Инфо о продуктах\n"
                + "2. Статус\n"
                + "3. Повторить заказ\n"
                + "0. Назад\n";
    }
}