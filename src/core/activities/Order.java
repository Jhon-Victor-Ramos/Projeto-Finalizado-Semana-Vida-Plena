package core.activities;

import core.Participant;
import restaurant.entities.Dish;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Participant client;
    private final List<Dish> orderedDishes;
    private final LocalDateTime orderTimestamp;
    private Voucher appliedVoucher;

    public Order(Participant client) {
        this.client = client;
        this.orderedDishes = new ArrayList<>();
        this.orderTimestamp = LocalDateTime.now();
        this.appliedVoucher = null;
    }


    public void applyVoucher(Voucher voucher) {
        this.appliedVoucher = voucher;
        voucher.markAsUsed();
        System.out.println("Voucher " + voucher.getId() + " aplicado com sucesso!");
    }

    public void addDish(Dish dish) {
        this.orderedDishes.add(dish);
    }

    public double calculateTotal() {
        double total = 0;
        for (Dish dish : this.orderedDishes) {
            total += dish.getPrice();
        }
        return total;
    }

    public List<Dish> getOrderedDishes() {
        return this.orderedDishes;
    }

    public Participant getParticipant() {
        return client;
    }
    public LocalDateTime getOrderTimestamp() {
        return orderTimestamp;
    }

    public Voucher getAppliedVoucher() { return appliedVoucher; }
}