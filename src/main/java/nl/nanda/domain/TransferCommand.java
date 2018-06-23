package nl.nanda.domain;

import nl.nanda.account.Account;
import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

/**
 * This command is responsible for communicating with the backend DB and Validation.
 * 
 * @author Wroko
 *
 */
public abstract class TransferCommand implements Command {
	
	private Transfer transfer;
	private Account account;
	private Transaction transaction;
	
	public abstract Integer beginTransfer(final Transfer transfer);

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}
	
	public Integer returnTransferId() {
		return transfer.getEntityId();
	}
	
	public Integer returnTansactionId() {		
		return transaction.getEntityId();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
	
	
	
}
