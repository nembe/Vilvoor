package nl.nanda.domain;

import java.math.BigDecimal;
import java.util.UUID;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.exception.AnanieNotFoundException;
import nl.nanda.status.Status;
import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;
import nl.nanda.transfer.dao.TransferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO: Make Junit Test for this component.
/**
 * Searching for the participating accounts and start the Transfer.
 *
 */
@Component
public class TransferFacilitator {

    /** The account repo. */
    @Autowired
    private AccountRepository accountRepo;

    /** The transfer repo. */
    @Autowired
    private TransferRepository transferRepo;

    /** The transaction repo. */
    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private AccountFacilitator accountFacilitator;

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
    public Integer returnTransfer(final String from, final String to, final double amount) {

        final Transfer transfer = checkAccountAndReturnTransfer(from, to);
        transfer.startTransfer(BigDecimal.valueOf(amount));
        final Integer returnedTransferId = transferRepo.save(transfer).getEntityId();

        if ("CONFIRMED".equals(transfer.getState())) {
            saveAccontsToCommitTransfer(transfer);
            createTheTransaction(transfer);
        }
        return returnedTransferId;
    }

    /**
     * Here we are making the transfer official. Creating the Transaction that
     * took place in the Ananie Application.
     * 
     * 
     * @param transfer
     * @return
     */
    private void createTheTransaction(final Transfer transfer) {

        final Transaction transaction = new Transaction(transfer.getZender().getAccountUUID(), transfer);
        transactionRepo.save(transaction);

    }

    /**
     * We update the account table with the new balance of the accounts.
     * 
     * @param transfer
     */
    private void saveAccontsToCommitTransfer(final Transfer transfer) {
        accountRepo.save(transfer.getZender());
        accountRepo.save(transfer.getOntvanger());
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
    private Transfer checkAccountAndReturnTransfer(final String from, final String to) {

        final Account accountFrom = accountFacilitator.findAccount(from);
        final Account accountTo = accountFacilitator.findAccount(to);

        final Transfer transfer = new Transfer();
        if (accountFrom == null || accountTo == null) {
            transfer.setCredit(UUID.fromString(from));
            transfer.setDebet(UUID.fromString(to));
            transfer.setState(Status.ACCOUNT_NOT_FOUND);
            transferRepo.save(transfer);
            throw new AnanieNotFoundException("Account Not found ");
        }
        transfer.setZender(accountFrom);
        transfer.setOntvanger(accountTo);
        return transfer;
    }
}
