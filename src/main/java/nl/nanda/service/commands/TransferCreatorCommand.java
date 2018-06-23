package nl.nanda.service.commands;

import java.math.BigDecimal;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.domain.TransferCommand;
import nl.nanda.status.Status;
import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;
import nl.nanda.transfer.dao.TransferRepository;

public class TransferCreatorCommand extends TransferCommand {
	
	/** The transfer repo. */   
    private TransferRepository transferRepo;
    
    /** The account repo. */   
    private AccountRepository accountRepo;

    private TransactionRepository transactionRepo;
	
    public TransferCreatorCommand(TransferRepository transferRepo, AccountRepository accountRepo, TransactionRepository transactionRepo) {
	  this.transferRepo = transferRepo;
	  this.accountRepo = accountRepo;
	  this.transactionRepo = transactionRepo;
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
        setTransfer(checkAccountAndReturnTransfer(transfer));
        if (getTransfer().getOntvanger() != null || getTransfer().getZender() != null) {

        	getTransfer().startTransfer(BigDecimal.valueOf(transfer.getTotaal()));
   
            if ("CONFIRMED".equals(getTransfer().getState())) {
                return  createTheTransaction(getTransfer());
            } 
        }
        return getTransfer().getEntityId();
    }

    /**
     * Here we are making the transfer official. Creating the Transaction that
     * took place in the Ananie Application.
     * 
     * 
     * @param transfer
     * @return
     */
    private Integer createTheTransaction(final Transfer transfer) {

        setTransaction(new Transaction(transfer.getZender().getAccountUUID(), transfer));
        return getTransaction().getEntityId();
    }

   
    /**
     * Creating a Transfer with state "Pending". If one of the accounts is not
     * found we throw a exception (AnanieNotFoundException). Because the
     * transfer can't continue without a valid account (see DB constraints).
     * 
     * @param from
     * @param to
     * @param amount
     * @return
     */
    private Transfer checkAccountAndReturnTransfer(final Transfer transfer) {

        final Account accountFrom = accountRepo.findAccountByUuid(transfer.getCredit());
        final Account accountTo = accountRepo.findAccountByUuid(transfer.getDebet());

        if (accountFrom == null || accountTo == null) {
            transfer.setCredit(transfer.getCredit());
            transfer.setDebet(transfer.getDebet());
            transfer.setState(Status.ACCOUNT_NOT_FOUND);
            transferRepo.save(transfer);
        } else {
            transfer.setZender(accountFrom);
            transfer.setOntvanger(accountTo);
        }
        return transfer;
    }

	@Override
	public void create() {
		accountRepo.save(getTransfer().getZender());
        accountRepo.save(getTransfer().getOntvanger());        
        if (getTransaction() != null) {
			transactionRepo.save(getTransaction());
		}
		transferRepo.save(getTransfer());		
	}
    
    

}
