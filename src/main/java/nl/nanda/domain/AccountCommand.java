package nl.nanda.domain;

import java.util.UUID;

import nl.nanda.account.Account;

/**
 * This command is responsible for communicating with the backend DB and Validation.
 * 
 * @author Wroko
 *
 */
public abstract class AccountCommand implements Command {
	
	private Account account;
	
	public abstract void createAccount(final String balance, final String roodToegestaan, final String accountUser);
	
	public Account getAccount() {
		return account;
	}


	public void setAccount(Account account) {
		this.account = account;
	}
	
	public UUID returnAccountUUID() {
		return account.getAccountUUID();
	}
	

}
