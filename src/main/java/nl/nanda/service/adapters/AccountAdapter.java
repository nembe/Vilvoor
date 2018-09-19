package nl.nanda.service.adapters;

import java.util.List;

import nl.nanda.jpa.account.Account;

/**
 * Creates and store the Account.
 * For searching an Account we look it up throw the Account Repo.
 * For saving we create a AccountCommand which handels the valdation and useful stuff before saving.
 * The invoker will do the actual saving for us.
 *
 */
public interface AccountAdapter {
	
	 public Account findAccountByName(String name);
	
	 public List<Account> findAll();
	
	 public void updateAccountBalance(final double saldo, final String uuid);
	 
	 public Account save(final Account account);
	 
	 public Account findAccount(final String searchAccount);
	 
	 public Account savingAccount(final String balance, final String roodToegestaan, final String accountUser);

}
