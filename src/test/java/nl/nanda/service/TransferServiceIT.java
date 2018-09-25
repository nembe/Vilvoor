package nl.nanda.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import nl.nanda.account.AccountFactory;
import nl.nanda.config.AbstractConfig;
import nl.nanda.exception.AnanieException;
import nl.nanda.jpa.account.Account;
import nl.nanda.jpa.transaction.Transaction;
import nl.nanda.jpa.transfer.Transfer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Profile;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * AAT (Ananie Account Transactions) A system tests that verifies the
 * TransferService (facade) component. Who is responsible for delegating
 * requests to the Repositories. Already inserted data is only available in the
 * "test" profile.
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class TransferServiceIT extends AbstractConfig {

	private final static String FIRST_ACCOUNT = "6ebb8693-7179-4e04-80e1-89323971e98a";
	private final static String NEP_ACCOUNT = "6ebb8693-0000-0000-80e1-89323971e98a";
	private final static String UUID_NOT_FOUND = "00000000-0000-0000-0000-89323971e98a";

	/**
	 * Create a account and try to receive this account based on the accounts
	 * UUID.
	 */
	@Test
	public void testTransferServiceAccount() {

		final String accountUUID = transferService.createAccount("200", "10", "Theo");

		assertEquals("Theo", transferService.getAccount(accountUUID).getName());
	}

	/**
	 * Try to create a account with buggy data (saldo).
	 */
	@Test(expected = AnanieException.class)
	public void testCreateAccountWithBuggySaldo() {

		transferService.createAccount("200l", "10l", "Theo");
	}

	/**
	 * Test transfer service save account. The client is here responsible for
	 * creating the Account object before saving it.
	 */

	@Test
	public void testTransferServiceSaveAccount() {

		final Account account = AccountFactory.createAccounts(1000.50, 21.00, "Nice");
		transferService.saveAccount(account);

		assertTrue(1000.50 == transferService.findAccountByName("Nice").getBalance().doubleValue());

	}

	/**
	 * Test transfer service save account. The client is here responsible for
	 * creating the Account object before saving it, But name can not be NULL.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testSavingAccountNotValid() {

		final Account account = new Account(BigDecimal.valueOf(1000.50), BigDecimal.valueOf(21.00), null);
		transferService.saveAccount(account);

	}

	/**
	 * Testing updating the account from the client without creating/retrieving
	 * a account object.
	 */
	@Test
	public void testTransferServiceUpdatingAccount() {
		transferService.updateAccountBalance(220, FIRST_ACCOUNT);
		final Account account = transferService.getAccount(FIRST_ACCOUNT);

		if (account.getAccountUUID().toString().equalsIgnoreCase(UUID_NOT_FOUND)) {
			assertTrue(account.getBalance().doubleValue() == 0.0);
		} else {
			assertTrue(account.getBalance().doubleValue() == 220);
		}

	}

	/**
	 * Test transfer service not found account. Which the client can anticipate
	 * on the object AccountNull.
	 */
	@Test
	public void testTransferServiceNotFoundAccount() {
		final Account accountNull = transferService.getAccount(NEP_ACCOUNT);
		assertTrue("Account not available".equalsIgnoreCase(accountNull.getName()));
	}

	/**
	 * Test transfer service cannot complete because the account doesn't exist.
	 */
	@Test
	public void testNotFoundAccountByTryingTransfer() {

		final String accountSaskia = transferService.createAccount("0", "0", "Saskia");

		final Integer id = transferService.doTransfer(NEP_ACCOUNT, accountSaskia, 50);
		final Transfer transfer = transferService.findTransferById(id);
		assertTrue(transfer.getState().equalsIgnoreCase("ACCOUNT_NOT_FOUND"));
	}

	/**
	 * Test transfer service with incomplete information.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testTransferWithNoValidNumber() {

		final String accountSaskia = transferService.createAccount("0", "0", "Saskia");
		transferService.doTransfer(FIRST_ACCOUNT, accountSaskia, -5.04);
	}

	/**
	 * Test transfer service transfer returning a Transfer id.
	 */
	@Test
	@Rollback(true)
	public void testTransferServiceTransfer() {

		final String accountTheo = transferService.createAccount("200", "10", "Theo");
		final String accountSaskia = transferService.createAccount("0", "0", "Saskia");

		assertTrue(transferService.doTransfer(accountTheo, accountSaskia, 50) != 0);

	}

	/**
	 * Test transfer service with only strings returning a Transfer id.
	 */
	@Test
	public void testTransferServiceTransferWithStrings() {

		final String accountTheo = transferService.createAccount("200", "10", "Theo");
		final String accountSaskia = transferService.createAccount("0", "0", "Saskia");

		final Transfer transfer = new Transfer(UUID.fromString(accountTheo), UUID.fromString(accountSaskia), 30.50);

		final Integer id = transferService.doTransfer(transfer);
		assertTrue(id != 0);

		final Account account = transferService.getAccount(accountSaskia);
		assertTrue(account.getBalance().doubleValue() == 30.50);
	}

	/**
	 * Test overdraft of the account, the transfer cannot complete.
	 */
	@Test
	@Rollback(true)
	public void testTransferServiceOverdraft() {

		final String accountTheo = transferService.createAccount("200", "10", "Theo");
		final String accountSaskia = transferService.createAccount("0", "0", "Saskia");

		final Integer transferTransactionId = transferService.doTransfer(accountTheo, accountSaskia, 350);
		System.out.println("testTransferServiceOverdraft " + transferTransactionId);
		assertEquals("INSUFFICIENT_FUNDS", transferService.findTransferById(transferTransactionId).getState());
	}

	/**
	 * We don't want the client dealing with NULL checks, the client can trust
	 * us we return a TransactionNull Object.
	 */
	@Rollback(true)
	@Test
	public void testTransferServiceValidationReturningNull() {
		final String accountTheo = transferService.createAccount("200", "10", "Theo");
		final List<Transaction> transactionNull = transferService.findTransactionByAccount(accountTheo);
		assertTrue(transactionNull.get(0).getEntityId() == -1);
	}

	/**
	 * Testing/searching for the amount (Total) of the transfer.
	 */
	@Test
	public void testTransferServiceTotalOfTransfer() {

		final Transfer transfer = transferService.findTransferById(0);
		if (transfer.getCredit().toString().equalsIgnoreCase(UUID_NOT_FOUND)) {
			assertTrue(transfer.getTotaal() == 0.0);
		} else {
			assertTrue(transfer.getTotaal() == 25.10);
		}

	}

	/**
	 * Saving a Transfer, we have one already inserted at startup.
	 */
	@Test
	@Rollback(true)
	public void testTransferServiceSaveTransfer() {

		final Transfer transfer = new Transfer();
		transfer.setTotaal(BigDecimal.valueOf(20.50));

		transfer.setCredit(UUID.fromString(FIRST_ACCOUNT));
		transfer.setDebet(UUID.fromString("6ebb8693-7189-4e04-80e1-89323971e98a"));

		assertTrue(transferService.saveTransfer(transfer) != 0);
	}

	/**
	 * Checking the states of the already inserted transfers (data) only
	 * available in the "test" profile. Depending on which profile we are using
	 * here.
	 */
	@Test
	public void testTransferServiceFindingTheFirstConfirmedTransfer() {
		final List<Transfer> transfers = transferService.findAllTransfers();
		if (transfers.size() > 0) {
			assertEquals("CONFIRMED", transfers.get(0).getState());
		}
	}

	/**
	 * Test transfer switching states of the Transfer.
	 */
	@Test
	@Rollback(true)
	public void testTransferServiceTransferState() {

		final String accountTheo = transferService.createAccount("200", "10", "Theo");
		final String accountSaskia = transferService.createAccount("0", "0", "Saskia");

		final Transfer transfer = new Transfer();
		transfer.setTotaal(BigDecimal.valueOf(20.50));
		transfer.setCredit(UUID.fromString(accountTheo));
		transfer.setDebet(UUID.fromString(accountSaskia));

		final Integer transferTransactionId = transferService.doTransfer(transfer);
		assertNotNull(transferService.findTransaction(transferTransactionId));
		assertTrue(accountTheo.equalsIgnoreCase(transferService.findTransaction(transferTransactionId).getAccount().toString()));
	}

	/**
	 * Test transfer service finding all transactions that were inserted at
	 * startup (Only Test Profile has data).
	 */
	@Test
	public void testTransferServiceFindAllTransactions() {
		final List<Transaction> transactions = transferService.findAllTransactions();

		if (transactions.size() > 0) {
			assertTrue(FIRST_ACCOUNT.equalsIgnoreCase(transactions.get(0).getAccount().toString()));
			assertTrue(transactions.size() == 1);
		}

	}

	/**
	 * Test transfer service finding all accounts that were inserted at startup
	 * (Only Test Profile has data).
	 */
	@Test
	public void testTransferServiceFindAllAccounts() {

		final List<Account> accountsRetrieved = transferService.findAllAccounts();
		if (accountsRetrieved.size() > 0) {
			assertTrue("Jasper".equalsIgnoreCase(accountsRetrieved.get(0).getName()));
			assertTrue(accountsRetrieved.size() == 2);
		}

	}

	/**
	 * Finding the AccountId of the first successful Transfer that must be
	 * register as a Transaction.
	 */
	@Test
	public void testTransferServiceTotalTransferByTransaction() {

		final Transaction trans = transferService.findTransaction(0);

		if (trans.getAccount().toString().equalsIgnoreCase(UUID_NOT_FOUND)) {
			assertTrue(trans.getTransfer().getTotaal() == 0.0);
		} else {
			assertTrue(FIRST_ACCOUNT.equalsIgnoreCase(trans.getAccount().toString()));
			assertTrue(trans.getTransfer().getTotaal() == 25.10);
		}
	}

	/**
	 * Testing validation on methods. ConstraintViolationException is the second
	 * Exception that the client can look out for.
	 */
	@Test(expected = ConstraintViolationException.class)
	public void testConstraintViolationException() {

		transferService.getAccount("");

	}
}
