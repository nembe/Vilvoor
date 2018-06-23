package nl.nanda.service.commands;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.domain.AccountCommand;
import nl.nanda.exception.AnanieException;

/**
 * @author Wroko
 *
 */
public class AccountCreatorCommand extends AccountCommand {
	
	 /** The account repo. */   
    private AccountRepository accountRepo;
   
	public AccountCreatorCommand(AccountRepository accountRepo) {
		this.accountRepo = accountRepo;
	}

		
	 /**
     * Converting the String from the upper layer to the destiny Types.
     * 
     * @param balance
     * @param roodToegestaan
     * @param accountUser
     * @return
     */	
    public void createAccount(final String balance, final String roodToegestaan, final String accountUser) {
        setAccount(validateAndCreateAccount(balance, roodToegestaan, accountUser));        
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


	@Override
	public void create() {
		accountRepo.save(getAccount());		
	}



	
    
	
	

}
