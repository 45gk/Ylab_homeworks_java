package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import main.service.WalletService;
import main.entity.*;

public class Main {
    public static void main(String[] args) throws IOException {
        WalletService walletService = new WalletService();
        Scanner scanner = new Scanner(System.in);
        boolean inn = false;
        Player curloggedInPlayer = null;
        int idTransaction = 0;

        while (true) {
            System.out.println(" ");
            System.out.println("Выберите команду:");
            System.out.println("1. Регистрация");
            System.out.println("2. Авторизация");
            System.out.println("3. Выход из учётной записи");
            System.out.println("4. Узнать баланс");
            System.out.println("5. Пополнение счета");
            System.out.println("6. Снятие средств");
            System.out.println("7. История транзакций");
            System.out.println("8. Аудит действий");
            System.out.println("9. Выход из приложения");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Считываем символ новой строки

            switch (choice) {
                // Регистрация пользователя
                case 1:
                    System.out.print("Введите имя пользователя: ");
                    String regUsername = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String regPassword = scanner.nextLine();
                    boolean registrationResult = walletService.registerPlayer(regUsername, regPassword);
                    if (registrationResult) {
                        Player loggedInPlayer = walletService.login(regUsername, regPassword);
                        System.out.println("Регистрация успешна.");
                        System.out.println("Авторизуйтесь, чтобы начать выполнение операций.");
                        walletService.auditPut(loggedInPlayer,"Зарегистрировался");
                    } else {
                        System.out.println("Пользователь с таким именем уже существует.");
                    }
                    break;

                // Авторизация пользователя
                case 2:
                    System.out.print("Введите имя пользователя: ");
                    String loginUsername = scanner.nextLine();
                    System.out.print("Введите пароль: ");
                    String loginPassword = scanner.nextLine();
                    Player loggedInPlayer = walletService.login(loginUsername, loginPassword);
                    if (loggedInPlayer != null) {
                        System.out.println("Авторизация успешна.");
                        System.out.println(loggedInPlayer);
                        inn = true;
                        curloggedInPlayer = loggedInPlayer;
                        walletService.auditPut(curloggedInPlayer,"Вошёл в учётную запись");

                    } else {
                        System.out.println("Неверное имя пользователя или пароль.");
                    }
                    break;


                // Выход из учётной записи
                case 3:
                    if (inn){
                        walletService.auditPut(curloggedInPlayer,"Вышел из учётной записи");
                        inn = false;
                        curloggedInPlayer = null;
                        System.out.println("Вы успешно вышли из учётной записи");

                    }
                    else {
                        System.out.println("Вы не авторизованы!");
                    }
                    break;


                // Просмотр баланса
                case 4:
                    if (inn){

                        System.out.println(walletService.getBalance(curloggedInPlayer));
                        walletService.auditPut(curloggedInPlayer,"Посмотрел баланс");
                    }
                    else {
                        System.out.println("Вы не авторизованы!");
                    }
                    break;

                case 5:
                    // Пополнение счёта
                    if (inn){
                        System.out.println("Введите сумму пополнеия:");
                        int plusBalance = scanner.nextInt();
                        scanner.nextLine();
                        if (walletService.creditPlayer(curloggedInPlayer,String.valueOf(idTransaction),plusBalance)){
                            walletService.auditPut(curloggedInPlayer,"Пополнил средства");
                            System.out.println("Успешно!");
                            idTransaction++;
                        }
                        else {
                            System.out.println("Возникла ошибка!");
                        }



                    }
                    else {
                        System.out.println("Вы не авторизованы!");
                    }
                    break;

                case 6:
                    // Снятие со счёта
                    if (inn){
                        System.out.println("Введите сумму для снятия:");
                        int minusBalance = scanner.nextInt();
                        scanner.nextLine();
                        if (walletService.debitPlayer(curloggedInPlayer,String.valueOf(idTransaction),minusBalance)){
                            System.out.println("Успешно!");
                            walletService.auditPut(curloggedInPlayer,"Снял средства");
                            idTransaction++;
                        }
                        else {
                            System.out.println("Возникла ошибка!");
                        }



                    }
                    else {
                        System.out.println("Вы не авторизованы!");
                    }
                    break;

                case 7:
                    // Просмотр истории транзакций
                    if (inn){
                        List<Transaction> transactionList = walletService.getTransactionHistory(curloggedInPlayer);

                        for (int i = 0; i<transactionList.size();i++){
                            Transaction tr = transactionList.get(i);
                            System.out.println(tr);
                            System.out.println("ID:"+ tr.getId() );
                            System.out.println("Amount:"+ tr.getAmount() );
                            System.out.println("Type:"+ tr.getType() );

                            System.out.println(" ");
                        }

                        walletService.auditPut(curloggedInPlayer,"Посмотрел историю");
                    }
                    else {
                        System.out.println("Авторизуйтесь, чтобы посмотреть историю");
                    }
                    break;

                case 8:
                    // Просмотр аудита
                    if (inn){
                        walletService.auditGet(curloggedInPlayer);
                    }
                    else {
                        System.out.println("Авторизуйтесь, чтобы посмотреть аудит");
                    }
                    break;

                case 9:
                    // Выход из приложения
                    System.out.println("Выход из приложения.");
                    scanner.close();
                    System.exit(0);
                    break;
                // Случай неправильного ввода
                default:
                    System.out.println("Неверная команда. Повторите попытку.");
            }
        }

    }

}
