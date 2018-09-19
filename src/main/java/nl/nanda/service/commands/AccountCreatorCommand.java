package nl.nanda.service.commands;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import nl.nanda.domain.AccountCommand;
import nl.nanda.exception.AnanieException;
import nl.nanda.jpa.account.Account;
import nl.nanda.jpa.account.dao.AccountRepository;

/**
 * The client creates this type of Command.
 * This Command needs three Repos to finish the Job.
 * And Implements the Interface what the Client don't know.
 * These class is not a singleton (Spring Bean) can be clean up by GC. 
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
