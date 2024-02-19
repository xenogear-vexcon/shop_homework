package org.example;

public enum DeliveryStatus {
    ASSEMBLY("в сборке"),
    ON_THE_WAY("в пути"),
    DELIVERED("доставлено"),
    CANCELLED("отменён");

    static final int ChangeStatusTimer = 10_000; // через сколько секунд меняем статус
    static final int StatusChangedTimes = 2; // сколько раз будем менять статус
    private final String title;

    DeliveryStatus(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
