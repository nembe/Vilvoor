package nl.nanda.domain;

import java.math.BigDecimal;
import java.util.UUID;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.exception.AnanieException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO: Make Junit Test for this component.
/**
 * Validates and create the account.
 *
 */
@Component
public class AccountFacilitator {

    /** The account repo. */
    @Autowired
    public AccountRepository accountRepo;

    /**
     * Finding the account to complete Transaction.
     * 
     * @param searchAccount
     * @return
     */
    public Account findAccount(final String searchAccount) {
        final Account account = accountRepo.findAccountByUuid(UUID.fromString(searchAccount));
        return account;
    }

    /**
     * Converting the String from the upper layer to the destiny Types.
     * 
     * @param balance
     * @param roodToegestaan
     * @param accountUser
     * @return
     */
    public Account savingAccount(final String balance, final String roodToegestaan, final String accountUser) {
        final Account account = validateAndCreateAccount(balance, roodToegestaan, accountUser);
        final Account savedAccount = accountRepo.save(account);
        return savedAccount;
    }

    /**
     * Validating the Input before creating the Account.
     * 
     * @param balance
     * @param roodToegestaan
     * @param accountUser
     * @return Account entity object.
     */
    private Account validateAndCreateAccount(final String balance, final String roodToegestaan, final String accountUser) {
        Account account = null;
        try {
            account = new Account(BigDecimal.valueOf(Double.parseDouble(balance)), BigDecimal.valueOf(Double.parseDouble(roodToegestaan)),
                    accountUser);
        } catch (final NumberFormatException e) {
            throw new AnanieException("Saving account problem ", e);
        }
        final UUID accountUUID = UUID.randomUUID();
        account.setAccountUUID(accountUUID);
        return account;
    }
}
