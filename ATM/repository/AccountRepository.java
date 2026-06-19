package ATM.repository;

import ATM.model.UserAccount;
import ATM.exception.AccountNotFoundException;

public interface AccountRepository {
    UserAccount findAccount(int accountNumber) throws AccountNotFoundException;
    void saveAccount(UserAccount account);
}
