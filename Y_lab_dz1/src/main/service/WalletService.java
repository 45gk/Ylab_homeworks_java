package main.service;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import main.entity.*;
public class WalletService {
    private Map<String, Player> players;

    public WalletService() {
        players = new HashMap<>();
    }

    // Регистрация нового игрока
    public boolean registerPlayer(String username, String password) {
        if (players.containsKey(username)) {
            return false; // Пользователь с таким именем уже существует
        }

        Player newPlayer = new Player(username, password);
        players.put(username, newPlayer);
        return true;
    }

    // Авторизация игрока
    public Player login(String username, String password) {
        Player player = players.get(username);
        if (player != null && player.getPassword().equals(password)) {
            return player; // Возвращаем игрока, если имя и пароль совпадают
        }
        return null; // Возвращаем null, если пользователь не найден или пароль неверен
    }

    // Добавление средств на счет игрока
    public boolean creditPlayer(Player player, String transactionId, double amount) {
        if (amount <= 0) {
            return false; // Ошибка: нельзя добавить отрицательную сумму
        }

        Transaction transaction = new Transaction(transactionId, "Credit", amount);
        if (!player.addTransaction(transaction)) {
            return false; // Ошибка: идентификатор транзакции не уникален
        }

        player.addToBalance(amount);
        return true;
    }

    // Снятие средств со счета игрока
    public boolean debitPlayer(Player player, String transactionId, double amount) {
        if (amount <= 0 || player.getBalance() < amount) {
            return false; // Ошибка: нельзя снять отрицательную сумму или сумму больше, чем есть на счету
        }

        Transaction transaction = new Transaction(transactionId, "Debit", amount);
        if (!player.addTransaction(transaction)) {
            return false; // Ошибка: идентификатор транзакции не уникален
        }

        player.subtractFromBalance(amount);
        return true;
    }

    // Получение истории транзакций игрока
    public List<Transaction> getTransactionHistory(Player player) {
        return player.getTransactionHistory();
    }

    // Аудит действий игрока
    public void auditGet(Player player) {

        List<String> audit = player.getAuditHistory();
        for (int i = 0;i<audit.size();i++) {
            System.out.println("Player: " + player.getUsername() + " Action: " + audit.get(i));
        }
    }
    // Дополнение записей в аудите аудита
    public void auditPut(Player player, String action){
        player.addAudit(action);
    }

    // Получение баланса
    public int getBalance(Player player){
        return (int) player.getBalance();
    }
}
