package ATM.service;

import ATM.exception.AccountNotFoundException;
import ATM.exception.InsufficientBalanceException;
import ATM.exception.InvalidPinException;
import ATM.model.UserAccount;
import ATM.repository.AccountRepository;
import java.util.List;

public class ATMServiceImpl implements ATMService {
    private final AccountRepository accountRepository;

    // Decoupled architecture: Service depends on interface, not concrete implementation.
    public ATMServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean authenticate(int accountNumber, int pin) throws AccountNotFoundException, InvalidPinException {
        UserAccount account = accountRepository.findAccount(accountNumber);
        if (account.getPin() != pin) {
            throw new InvalidPinException("Wrong PIN code! Access Denied.");
        }
        return true;
    }

    @Override
    public double checkBalance(int accountNumber) throws AccountNotFoundException {
        UserAccount account = accountRepository.findAccount(accountNumber);
        return account.getBalance();
    }

    @Override
    public void deposit(int accountNumber, double amount) throws AccountNotFoundException {
        UserAccount account = accountRepository.findAccount(accountNumber);
        account.deposit(amount);
        accountRepository.saveAccount(account);
    }

    @Override
    public void withdraw(int accountNumber, double amount) throws AccountNotFoundException, InsufficientBalanceException {
        UserAccount account = accountRepository.findAccount(accountNumber);
        account.withdraw(amount);
        accountRepository.saveAccount(account);
    }

    @Override
    public List<String> getMiniStatement(int accountNumber) throws AccountNotFoundException {
        UserAccount account = accountRepository.findAccount(accountNumber);
        return account.getTransactionHistory();
    }
}
