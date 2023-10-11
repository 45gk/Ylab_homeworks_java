package main.entity;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String username;
    private String password;
    private double balance;
    private List<Transaction> transactionHistory;
    private List<String> auditHistory;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
        this.auditHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public void addToBalance(double amount) {
        balance += amount;
    }

    public void subtractFromBalance(double amount) {
        balance -= amount;
    }

    public boolean addTransaction(Transaction transaction) {
        if (!transactionHistory.contains(transaction.getId())) {
            transactionHistory.add(transaction);
            return true;
        }
        else {
            return false;
        }
    }
    public List<String> getAuditHistory(){
        return auditHistory;
    }
    public void addAudit(String action){
        auditHistory.add(action);
    }
}