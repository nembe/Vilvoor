package nl.nanda.service.adapters;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.eventbus.EventBus;

import nl.nanda.account.dao.AccountRepository;
import nl.nanda.domain.TransferCommand;
import nl.nanda.service.commands.TransferCreatorCommand;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;
import nl.nanda.transfer.dao.TransferRepository;

//TODO: Make Junit Test for this component.
/**
 * Searching for the participating accounts and start the Transfer.
 *
 */
@Component
public class TransferAdapterImpl implements TransferAdapter {
	
	@Autowired
	private EventBus eventBus;
	
	/** The account repo. */ 
	@Autowired
    private AccountRepository accountRepo;

	@Autowired
    private TransactionRepository transactionRepo;
   
    /** The transfer repo. */
    @Autowired
    private TransferRepository transferRepo;
      
    @Autowired
    private CommandsInvoker commandsInvoker;
    
    public TransferAdapterImpl(EventBus eventBus, CommandsInvoker commandsInvoker) {
    	eventBus.register(commandsInvoker);
	}

    /**
     * Returning the Transfer ID so that We can trace the transfer state. If the
     * transfer is successful the state of the transfer will be "CONFIRMED".
     * With the Transfer ID we can find the "confirmed" Transaction.
     * 
     * @param fromAccount
     * @param toAccount
     * @param amount
     * @return the transfer primary key.
     */
    public Integer beginTransfer(final Transfer transfer) {    	
    	 TransferCommand transferCommand = new TransferCreatorCommand(transferRepo, accountRepo, transactionRepo); 
         transferCommand.beginTransfer(transfer);        
         eventBus.post(transferCommand);
         return transferCommand.getTransaction() != null? transferCommand.returnTansactionId() :transferCommand.returnTransferId();
    }

	@Override
	public Integer saveTransfer(Transfer transfer) {
		TransferCommand transferCommand = new TransferCreatorCommand(transferRepo, accountRepo, transactionRepo); 
        transferCommand.beginTransfer(transfer); 
		eventBus.post(transferCommand);
		return transferCommand.returnTransferId();
	}

	@Override
	public Transfer findByEntityId(Integer id) {		
		return transferRepo.findByEntityId(id);
	}

	@Override
	public Transfer findByDay(Date day) {	
		return transferRepo.findByDay(day);
	}

	@Override
	public List<Transfer> findAllTransfers() {		
		return transferRepo.findAll();
	}

	
       
}
