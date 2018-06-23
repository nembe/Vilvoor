package nl.nanda.service.adapters;

import java.util.List;
import java.util.UUID;

import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

public interface TransactionAdapter {
	
	public Transaction findByEntityId(Integer id);	
	public List<Transaction> findByAccount(UUID uuid);	
	public Transaction findByTransfer(Transfer transfer);
	public List<Transaction> findAll();
	

}
