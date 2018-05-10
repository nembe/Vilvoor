package nl.nanda.transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import nl.nanda.account.Account;
import nl.nanda.account.AccountFactory;
import nl.nanda.config.AbstractConfig;
import nl.nanda.transfer.Transfer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testing the TransactionRepository.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AatTransactionRepoTest extends AbstractConfig {

    /**
     * Searching for the Transfer ID belonging to a given Account.
     */
    @Test
    public void testFindTransactionByAccount() {

        final List<Transaction> txn = transactionRepo.findByAccount(UUID.fromString("6ebb8693-7179-4e04-80e1-89323971e98a"));

        assertEquals(Integer.valueOf(0), txn.get(0).getTransfer().getEntityId());
    }

    /**
     * Saving Transaction with empty transfer.
     */
    @Test
    public void testTransactionViolation() {

        final Transfer trans = new Transfer();

        final Transaction transaction = new Transaction(UUID.fromString("6ebb8693-7179-4e04-80e1-89323971e98a"), trans);
        transactionRepo.save(transaction);
    }

    /**
     * Searching for All the Transactions of a account.
     */
    @Test
    public void testFindAllTransactionsByAccount() {

        // Creating the accounts
        final Account accountDoubleSender = AccountFactory.createAccounts(1000.50, 20.0, "accountDoubleSender");
        final Account accountDoubleReciever = AccountFactory.createAccounts(0.50, 0.0, "accountDoubleReciever");

        // Saving the accounts (save entity in persistence context generation
        // id's )
        accountRepo.save(accountDoubleSender);
        accountRepo.save(accountDoubleReciever);

        // Creating the transfers
        final Transfer transOne = new Transfer(accountDoubleSender, accountDoubleReciever);
        final Transfer transTwo = new Transfer(accountDoubleSender, accountDoubleReciever);

        // Staring the Transfers
        transOne.startTransfer(BigDecimal.valueOf(10.50));

        // Create Transaction with given Transfer
        final Transaction txnOne = new Transaction(accountDoubleSender.getAccountUUID(), transOne);

        // Save the Transactions and Transfer
        //Uit gezet vanwege TransientPropertyValueException
//        transferRepo.save(transOne);
        transactionRepo.save(txnOne);

        transTwo.startTransfer(BigDecimal.valueOf(20.50));
        final Transaction txnTwo = new Transaction(accountDoubleSender.getAccountUUID(), transTwo);

      //Uit gezet vanwege TransientPropertyValueException
//        transferRepo.save(transTwo);
        transactionRepo.save(txnTwo);

        // Try to find the two transactions on this account
        final List<Transaction> transactions = transactionRepo.findByAccount(accountDoubleSender.getAccountUUID());

        assertTrue(10.50 == transactions.get(0).getTransfer().getTotaal());
        assertEquals(2, transactions.size());
        assertEquals(3, transactionRepo.findAll().size());
    }

}
