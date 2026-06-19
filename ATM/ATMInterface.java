package ATM;

import ATM.model.UserAccount;
import ATM.repository.AccountRepository;
import ATM.repository.InMemoryAccountRepository;
import ATM.service.ATMService;
import ATM.service.ATMServiceImpl;
import ATM.ui.ATMConsoleUI;

public class ATMInterface {
    public static void main(String[] args) {
        AccountRepository repository = new InMemoryAccountRepository();
        ATMService service = new ATMServiceImpl(repository);

        // Check if user ran the application in demo mode to show multithreading
        if (args.length > 0 && args[0].equalsIgnoreCase("--demo")) {
            runConcurrencyDemo(repository);
            return;
        }

        ATMConsoleUI ui = new ATMConsoleUI(service);
        ui.start();
    }

    private static void runConcurrencyDemo(AccountRepository repository) {
        System.out.println("=========================================");
        System.out.println("  CONCURRENCY SIMULATION (JOINT ACCOUNT) ");
        System.out.println("=========================================");

        try {
            int targetAccount = 1001;
            UserAccount account = repository.findAccount(targetAccount);
            System.out.printf("Initial Balance for Account %d: INR %.2f\n", targetAccount, account.getBalance());
            System.out.println("Starting 2 concurrent ATM threads withdrawing INR 3000.00 each simultaneously...\n");

            // Thread 1: ATM Terminal 1
            Thread atm1 = new Thread(() -> {
                try {
                    System.out.println("[ATM 1] Attempting to withdraw INR 3000.00...");
                    // Add a tiny delay to simulate network latency and test concurrency
                    Thread.sleep(100);
                    account.withdraw(3000.00);
                    System.out.printf("[ATM 1] SUCCESS! New Balance: INR %.2f\n", account.getBalance());
                } catch (Exception e) {
                    System.out.println("[ATM 1] FAILED: " + e.getMessage());
                }
            });

            // Thread 2: ATM Terminal 2
            Thread atm2 = new Thread(() -> {
                try {
                    System.out.println("[ATM 2] Attempting to withdraw INR 3000.00...");
                    Thread.sleep(100);
                    account.withdraw(3000.00);
                    System.out.printf("[ATM 2] SUCCESS! New Balance: INR %.2f\n", account.getBalance());
                } catch (Exception e) {
                    System.out.println("[ATM 2] FAILED: " + e.getMessage());
                }
            });

            // Start both withdrawals at the same time
            atm1.start();
            atm2.start();

            // Wait for both threads to complete
            atm1.join();
            atm2.join();

            System.out.println("\n-----------------------------------------");
            System.out.printf("Final Balance for Account %d: INR %.2f\n", targetAccount, account.getBalance());
            System.out.println("Note: Only one thread was able to withdraw successfully.");
            System.out.println("The other failed due to InsufficientBalanceException.");
            System.out.println("This proves the synchronized withdraw method is Thread-Safe!");
            System.out.println("=========================================");
        } catch (Exception e) {
            System.out.println("Demo Error: " + e.getMessage());
        }
    }
}
