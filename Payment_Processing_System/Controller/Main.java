package com.ezpay.bank.controller;

import com.ezpay.bank.model.BankAccount;
import com.ezpay.bank.model.Transfer;
import com.ezpay.bank.model.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for the EZPay Banking System.
 * Supports user registration, banking transfers, UPI payments, and account management.
 */
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserController userController = new UserController();
    private static final BankingServiceController accountController = new BankingServiceController();
    private static final TransferController transferController = new TransferController();
    private static final UPIPaymentController upiPaymentController = new UPIPaymentController();

    public static void main(String[] args) {
        int choice;

        System.out.println("🌟 Welcome to the EZPay Payment Processing System 🌟");

        do {
            System.out.println("\n======= EZPay Banking System Menu =======");
            System.out.println("1. Register User");
            System.out.println("2. Add Bank Account");
            System.out.println("3. Make a Bank Transfer");
            System.out.println("4. Make a UPI Payment");
            System.out.println("5. View All Users");
            System.out.println("6. View All Bank Accounts");
            System.out.println("7. View All Transfers");
            System.out.println("8. View UPI Transfers by Sender ID");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> addBankAccount();
                case 3 -> makeBankTransfer();
                case 4 -> makeUPIPayment();
                case 5 -> viewAllUsers();
                case 6 -> viewAllBankAccounts();
                case 7 -> viewAllTransfers();
                case 8 -> viewUPITransfersBySender();
                case 0 -> System.out.println("👋 Exiting... Thank you for using EZPay!");
                default -> System.out.println("❌ Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    /**
     * Register a new user with required details.
     */
    private static void registerUser() {
        System.out.print("Enter User ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        User user = new User(id, name, email, new ArrayList<>());
        userController.registerUser(user);
        System.out.println("✅ User registered successfully.");
    }

    /**
     * Add and link a bank account to a user.
     */
    private static void addBankAccount() {
        System.out.print("Enter Bank ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Bank Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Account Number: ");
        String accNo = scanner.nextLine();
        System.out.print("Is Verified (true/false): ");
        boolean isVerified = scanner.nextBoolean();

        BankAccount account = new BankAccount(id, name, accNo, isVerified);
        accountController.addAccount(account);
        System.out.println("✅ Bank account added successfully.");

        System.out.print("Enter User ID to link this account: ");
        int userId = scanner.nextInt();
        scanner.nextLine();

        User user = userController.getUser(userId);
        if (user != null) {
            user.getAccounts().add(accNo);
            userController.updateUser(user);
            System.out.println("🔗 Account linked to user.");
        } else {
            System.out.println("⚠️ User not found.");
        }
    }

    /**
     * Perform bank transfer between two accounts.
     */
    private static void makeBankTransfer() {
        System.out.print("Enter Sender Account Number: ");
        String sender = scanner.nextLine();
        System.out.print("Enter Receiver Account Number: ");
        String receiver = scanner.nextLine();
        System.out.print("Enter Amount: ₹");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        BankAccount senderAcc = accountController.getAccount(sender);
        BankAccount receiverAcc = accountController.getAccount(receiver);

        if (senderAcc == null || receiverAcc == null) {
            System.out.println("⚠️ Sender or Receiver account not found.");
            return;
        }

        Transfer transfer = new Transfer(0, sender, receiver, amount, LocalDateTime.now(), true);
        transferController.makeTransfer(transfer);
        System.out.println("✅ Bank Transfer successful.");
    }

    /**
     * Perform UPI payment using UPI ID.
     */
    private static void makeUPIPayment() {
        System.out.print("Enter Sender ID (User ID or Account Number): ");
        String senderId = scanner.nextLine();
        System.out.print("Enter Receiver UPI ID: ");
        String receiverUpiId = scanner.nextLine();
        System.out.print("Enter Amount: ₹");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        Transfer upiTransfer = new Transfer();
        upiTransfer.setSenderAccountNumber(senderId);
        upiTransfer.setReceiverAccountNumber(receiverUpiId);
        upiTransfer.setAmount(amount);
        upiTransfer.setTransferDateTime(LocalDateTime.now());

        String result = upiPaymentController.makeUPIPayment(upiTransfer);
        System.out.println(result);
    }

    /**
     * Show all users.
     */
    private static void viewAllUsers() {
        List<User> users = userController.getAllUsers();
        System.out.println("\n👤 Registered Users:");
        for (User u : users) {
            System.out.println("ID: " + u.getUserID() + ", Name: " + u.getUserName() + ", Email: " + u.getEmailId());
        }
    }

    /**
     * Show all bank accounts.
     */
    private static void viewAllBankAccounts() {
        List<BankAccount> accounts = accountController.getAllAccounts();
        System.out.println("\n🏦 Bank Accounts:");
        for (BankAccount acc : accounts) {
            System.out.println(acc.getAccountNumber() + " - " + acc.getBankName() + " [Verified: " + acc.isVerified() + "]");
        }
    }

    /**
     * Show all banking transfers.
     */
    private static void viewAllTransfers() {
        List<Transfer> transfers = transferController.getAllTransfers();
        System.out.println("\n💸 Bank Transfers:");
        for (Transfer t : transfers) {
            System.out.println("Transfer ID: " + t.getTransferId() + ", From: " + t.getSenderAccountNumber() +
                    ", To: " + t.getReceiverAccountNumber() + ", ₹" + t.getAmount() + ", Date: " + t.getTransferDateTime());
        }
    }

    /**
     * Show UPI transfers for a specific sender ID.
     */
    private static void viewUPITransfersBySender() {
        System.out.print("Enter Sender ID to view UPI transfers: ");
        String senderId = scanner.nextLine();
        List<Transfer> upiTransfers = upiPaymentController.getTransfersBySender(senderId);

        System.out.println("\n📱 UPI Transfers:");
        for (Transfer t : upiTransfers) {
            System.out.println("Transfer ID: " + t.getTransferId() + ", To UPI: " + t.getReceiverAccountNumber() +
                    ", ₹" + t.getAmount() + ", Date: " + t.getTransferDateTime());
        }

        if (upiTransfers.isEmpty()) {
            System.out.println("No UPI transfers found for sender: " + senderId);
        }
    }
}
