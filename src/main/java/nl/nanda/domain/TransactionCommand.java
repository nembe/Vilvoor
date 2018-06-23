package nl.nanda.domain;

import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;

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
