package ATM.model;

import ATM.exception.InsufficientBalanceException;
import java.util.ArrayList;
import java.util.List;

public class UserAccount {
    private final int accountNumber;
    private int pin;
    private double balance;
    private final List<String> transactionHistory;

    public UserAccount(int accountNumber, int pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account opened with initial balance: INR " + initialBalance);
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public synchronized int getPin() {
        return pin;
    }

    public synchronized void setPin(int pin) {
        this.pin = pin;
    }

    public synchronized double getBalance() {
        return balance;
    }

    public synchronized void withdraw(double amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        if (amount > balance) {
            throw new InsufficientBalanceException("Insufficient balance. Current balance: INR " + balance);
        }
        balance -= amount;
        addTransaction("Withdrew: INR " + amount);
    }

    public synchronized void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        }
        balance += amount;
        addTransaction("Deposited: INR " + amount);
    }

    public synchronized List<String> getTransactionHistory() {
        // Return a copy to avoid external modification of our list
        return new ArrayList<>(transactionHistory);
    }

    private synchronized void addTransaction(String message) {
        transactionHistory.add(message);
    }
}
