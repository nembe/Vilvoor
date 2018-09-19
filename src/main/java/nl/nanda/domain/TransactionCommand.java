package nl.nanda.domain;

import nl.nanda.jpa.transaction.Transaction;
import nl.nanda.jpa.transaction.dao.TransactionRepository;

public class TransactionCommand implements Command {
	
	private TransactionRepository transactionRepo;
	private Transaction transaction;
	
	public TransactionCommand(TransactionRepository transactionRepo) {
		this.transactionRepo = transactionRepo;
	}
	
	@Override
	public void create() {		
		transactionRepo.save(transaction);		
	}

}
