package ATM.service;

import ATM.exception.AccountNotFoundException;
import ATM.exception.InsufficientBalanceException;
import ATM.exception.InvalidPinException;
import java.util.List;

public interface ATMService {
    boolean authenticate(int accountNumber, int pin) throws AccountNotFoundException, InvalidPinException;
    double checkBalance(int accountNumber) throws AccountNotFoundException;
    void deposit(int accountNumber, double amount) throws AccountNotFoundException;
    void withdraw(int accountNumber, double amount) throws AccountNotFoundException, InsufficientBalanceException;
    List<String> getMiniStatement(int accountNumber) throws AccountNotFoundException;
}
