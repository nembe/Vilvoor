package nl.nanda.service.adapters;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;


/**
 * This Adapter isn't decoupled.
 * No coupling (Command) between Adapter and Repos (no MiTM) to do the Job.
 * 
 * @author Wroko
 *
 */
@Component
public class TransactionAdapterImpl implements TransactionAdapter {
	
	 /** The transaction repo. */
    @Autowired
    private TransactionRepository transactionRepo;
      
    public TransactionAdapterImpl() {    
	}

	@Override
	public Transaction findByEntityId(Integer id) {		
		return transactionRepo.findByEntityId(id);
	}

	@Override
	public List<Transaction> findByAccount(UUID uuid) {		
		return transactionRepo.findByAccount(uuid);
	}

	@Override
	public Transaction findByTransfer(Transfer transfer) {		
		return transactionRepo.findByTransfer(transfer);
	}

	@Override
	public List<Transaction> findAll() {		
		return transactionRepo.findAll();
	}

}
