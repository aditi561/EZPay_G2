package com.ezpay.bank.main;

import com.ezpay.bank.controller.*;
import com.ezpay.bank.model.*;

import java.time.LocalDateTime;
import java.util.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final userController userController = new userController();
    private static final bankAccountController accountController = new bankAccountController();
    private static final transferController transferController = new transferController();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n======= EZPay Banking System =======");
            System.out.println("1. Register User");
            System.out.println("2. Add Bank Account");
            System.out.println("3. Make a Transfer");
            System.out.println("4. View All Users");
            System.out.println("5. View All Bank Accounts");
            System.out.println("6. View All Transfers");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> addBankAccount();
                case 3 -> makeTransfer();
                case 4 -> viewAllUsers();
                case 5 -> viewAllBankAccounts();
                case 6 -> viewAllTransfers();
                case 0 -> System.out.println("Exiting... Thank you for using EZPay!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private static void registerUser() {
        System.out.print("Enter User ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        List<String> accounts = new ArrayList<>();
        User user = new User(id, name, email, accounts);
        userController.registerUser(user);
        System.out.println("✅ User registered successfully.");
    }

    private static void addBankAccount() {
        System.out.print("Enter Bank ID: ");
        int id = sc.nextInt(); sc.nextLine();
        System.out.print("Enter Bank Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        System.out.print("Is Verified (true/false): ");
        boolean isVerified = sc.nextBoolean();

        BankAccount account = new BankAccount(id, name, accNo, isVerified);
        accountController.addAccount(account);
        System.out.println("✅ Bank account added successfully.");

        System.out.print("Enter User ID to link this account: ");
        int userId = sc.nextInt();
        User user = userController.getUser(userId);
        if (user != null) {
            user.getAccounts().add(accNo);
            userController.updateUser(user);
            System.out.println("✅ Account linked to user.");
        } else {
            System.out.println("❌ User not found.");
        }
    }

    private static void makeTransfer() {
        System.out.print("Enter Sender Account Number: ");
        String sender = sc.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        String receiver = sc.nextLine();
        System.out.print("Enter Amount: ₹");
        double amount = sc.nextDouble();
        sc.nextLine();

        BankAccount senderAcc = accountController.getAccount(sender);
        BankAccount receiverAcc = accountController.getAccount(receiver);

        if (senderAcc == null || receiverAcc == null) {
            System.out.println("❌ Sender or Receiver account not found.");
            return;
        }

        Transfer transfer = new Transfer(0, sender, receiver, amount, LocalDateTime.now(), true);
        transferController.makeTransfer(transfer);

        System.out.println("✅ Transfer successful.");
    }

    private static void viewAllUsers() {
        List<User> users = userController.getAllUsers();
        System.out.println("\nRegistered Users:");
        for (User u : users) {
            System.out.println("ID: " + u.getUserID() + ", Name: " + u.getUserName() + ", Email: " + u.getEmailId());
        }
    }

    private static void viewAllBankAccounts() {
        List<BankAccount> accounts = accountController.getAllAccounts();
        System.out.println("\nBank Accounts:");
        for (BankAccount acc : accounts) {
            System.out.println(acc.getAccountNumber() + " - " + acc.getBankName() + " [Verified: " + acc.isVerified() + "]");
        }
    }

    private static void viewAllTransfers() {
        List<Transfer> transfers = transferController.getAllTransfers();
        System.out.println("\nTransfers:");
        for (Transfer t : transfers) {
            System.out.println("Transfer ID: " + t.getTransferId() + ", From: " + t.getSenderAccountNumber() +
                    ", To: " + t.getReceiverAccountNumber() + ", ₹" + t.getAmount() + ", Date: " + t.getTransferDateTime());
        }
    }
}
