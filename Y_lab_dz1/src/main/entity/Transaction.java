package main.entity;

import java.util.ArrayList;
import java.util.List;


public class Transaction {
    private String id;
    private String type;
    private double amount;
    private String timestamp;

    public Transaction(String id, String type, double amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        // Установите timestamp с текущей датой и временем
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getTimestamp() {
        return timestamp;
    }
}