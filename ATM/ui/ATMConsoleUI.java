package ATM.ui;

import ATM.exception.AccountNotFoundException;
import ATM.exception.InsufficientBalanceException;
import ATM.exception.InvalidPinException;
import ATM.service.ATMService;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ATMConsoleUI {
    private final ATMService atmService;
    private final Scanner scanner;

    public ATMConsoleUI(ATMService atmService) {
        this.atmService = atmService;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=========================================");
        System.out.println("       WELCOME TO THE ADVANCED ATM       ");
        System.out.println("=========================================");

        while (true) {
            try {
                System.out.print("\nEnter Account Number (or enter 0 to exit): ");
                if (!scanner.hasNextInt()) {
                    System.out.println("[INPUT ERROR]: Account number must be numeric!");
                    scanner.next(); // consume invalid input
                    continue;
                }
                int accountNumber = scanner.nextInt();
                if (accountNumber == 0) {
                    System.out.println("Thank you for using our ATM. Goodbye!");
                    break;
                }

                System.out.print("Enter your 4-digit PIN: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("[INPUT ERROR]: PIN must be numeric!");
                    scanner.next(); // consume invalid input
                    continue;
                }
                int pin = scanner.nextInt();

                if (atmService.authenticate(accountNumber, pin)) {
                    System.out.println("\n>>> Login Successful!");
                    userMenu(accountNumber);
                }
            } catch (AccountNotFoundException | InvalidPinException e) {
                System.out.println("\n[AUTHENTICATION ERROR]: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERROR]: An unexpected error occurred: " + e.getMessage());
                if (scanner.hasNext()) {
                    scanner.nextLine(); // clear buffer
                }
            }
        }
    }

    private void userMenu(int accountNumber) {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n-----------------------------");
            System.out.println("          ATM MENU           ");
            System.out.println("-----------------------------");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini-Statement (History)");
            System.out.println("5. Logout");
            System.out.print("Select an option (1-5): ");

            int choice = 0;
            try {
                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();
                } else {
                    scanner.next(); // consume invalid input
                    throw new InputMismatchException("Option must be a valid number (1-5).");
                }

                switch (choice) {
                    case 1:
                        double balance = atmService.checkBalance(accountNumber);
                        System.out.printf("Current Account Balance: INR %.2f\n", balance);
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double depAmount = getDoubleInput();
                        atmService.deposit(accountNumber, depAmount);
                        System.out.printf("Successfully deposited: INR %.2f\n", depAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = getDoubleInput();
                        atmService.withdraw(accountNumber, withdrawAmount);
                        System.out.printf("Successfully withdrawn: INR %.2f\n", withdrawAmount);
                        break;
                    case 4:
                        List<String> history = atmService.getMiniStatement(accountNumber);
                        System.out.println("\n--------- MINI-STATEMENT ---------");
                        for (String tx : history) {
                            System.out.println(" - " + tx);
                        }
                        System.out.println("----------------------------------");
                        break;
                    case 5:
                        loggedIn = false;
                        System.out.println("Logged out successfully from account: " + accountNumber);
                        break;
                    default:
                        System.out.println("[INPUT ERROR]: Invalid choice. Choose between 1 and 5.");
                }
            } catch (InputMismatchException e) {
                System.out.println("[INPUT ERROR]: " + e.getMessage());
            } catch (AccountNotFoundException e) {
                System.out.println("[ACCOUNT ERROR]: " + e.getMessage());
                loggedIn = false;
            } catch (InsufficientBalanceException e) {
                System.out.println("[TRANSACTION FAILED]: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("[ERROR]: " + e.getMessage());
            }
        }
    }

    private double getDoubleInput() throws InputMismatchException {
        if (scanner.hasNextDouble()) {
            double value = scanner.nextDouble();
            if (value <= 0) {
                throw new IllegalArgumentException("Amount must be greater than zero.");
            }
            return value;
        } else {
            scanner.next(); // consume invalid input
            throw new InputMismatchException("Invalid amount format. Please enter a valid decimal number.");
        }
    }
}
