package nl.nanda.service.adapters;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.domain.AccountCommand;
import nl.nanda.service.commands.AccountCreatorCommand;


/**
 * Creates and store the Account.
 * For searching an Account we look it up throw the Account Repo.
 * For saving we create a AccountCommand which handels the valdation and useful stuff before saving.
 * The invoker will do the actual saving for us.
 *
 */
@Component
public class AccountAdapterImpl implements AccountAdapter {
	
	@Autowired
	private EventBus eventBus;

    /** The account repo. */
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private CommandsInvoker commandsInvoker;
    
   
    public AccountAdapterImpl(EventBus eventBus, CommandsInvoker commandsInvoker) {    	
    	eventBus.register(commandsInvoker);
	}
    
    

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
    	AccountCommand accountCommand = new AccountCreatorCommand(accountRepo);
    	accountCommand.createAccount(balance, roodToegestaan, accountUser);
    	eventBus.post(accountCommand);
        return accountCommand.getAccount();
    }



	@Override
	public Account save(Account account) {	
		AccountCommand accountCommand = new AccountCreatorCommand(accountRepo);
		accountCommand.setAccount(account);
		eventBus.post(accountCommand);
		return account;
	}



	@Override
	public void updateAccountBalance(final double saldo, final String uuid) {
		accountRepo.updateAccountBalance(BigDecimal.valueOf(saldo), UUID.fromString(uuid));
	}



	@Override
	public List<Account> findAll() {		
		return accountRepo.findAll();
	}



	@Override
	public Account findAccountByName(String name) {		
		return accountRepo.findAccountByName(name);
	}
	
	

   
}
