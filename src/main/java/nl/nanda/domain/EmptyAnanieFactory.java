package nl.nanda.domain;

import java.math.BigDecimal;
import java.util.UUID;

import nl.nanda.jpa.account.Account;
import nl.nanda.jpa.transaction.Transaction;
import nl.nanda.jpa.transfer.Transfer;
import nl.nanda.service.empty.AccountNull;
import nl.nanda.service.empty.TransactionNull;
import nl.nanda.service.empty.TransferNull;
import nl.nanda.status.Status;

/**
 * Factory for creating Null Objects (Transaction, Account, Transfer) to prevent
 * NullpointerExceptions.
 * 
 * @author java
 *
 */
public class EmptyAnanieFactory {

	private final static String UUIDNULLSTRING = "00000000-0000-0000-0000-89323971e98a";

	/**
	 * 
	 * 
	 * @return Returning a empty Transaction Object.
	 */
	public static Transaction getEmptyTransaction() {

		Transaction transaction = new TransactionNull();
		transaction.setEntityId(-1);
		transaction.setAccount(UUID.fromString(UUIDNULLSTRING));
		transaction.setTransfer(getEmptyTransfer());
		return transaction;
	}

	/**
	 * 
	 * @return Returning a empty Account Object.
	 */
	public static Account getEmptyAccount() {

		Account account = new AccountNull(BigDecimal.valueOf(0), "Account not available");
		account.setAccountUUID(UUID.fromString(UUIDNULLSTRING));
		return account;
	}

	/**
	 * @return Returning a empty Transfer Object.
	 */
	public static Transfer getEmptyTransfer() {

		Transfer transfer = new TransferNull();
		transfer.setCredit(UUID.fromString(UUIDNULLSTRING));
		transfer.setDebet(UUID.fromString(UUIDNULLSTRING));
		transfer.setState(Status.NOT_AVAILABLE);

		return transfer;
	}

}
