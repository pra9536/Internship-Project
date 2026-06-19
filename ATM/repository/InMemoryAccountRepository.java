package ATM.repository;

import ATM.model.UserAccount;
import ATM.exception.AccountNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class InMemoryAccountRepository implements AccountRepository {
    private final Map<Integer, UserAccount> accounts;

    public InMemoryAccountRepository() {
        this.accounts = new HashMap<>();
        // Initialize with default accounts for demonstration
        saveAccount(new UserAccount(1001, 1111, 5000.00));
        saveAccount(new UserAccount(1002, 2222, 10000.00));
        saveAccount(new UserAccount(1003, 3333, 15000.00));
    }

    @Override
    public synchronized UserAccount findAccount(int accountNumber) throws AccountNotFoundException {
        UserAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException("Account number " + accountNumber + " does not exist.");
        }
        return account;
    }

    @Override
    public synchronized void saveAccount(UserAccount account) {
        if (account != null) {
            accounts.put(account.getAccountNumber(), account);
        }
    }
}
