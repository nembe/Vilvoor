package nl.nanda.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import nl.nanda.account.Account;
import nl.nanda.config.AbstractConfig;
import nl.nanda.exception.AnanieNotFoundException;
import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * AAT (Ananie Account Transactions) A system tests that verifies the
 * TransferService (facade) component. Who is responsible for delegating
 * requests to the Repositories.
 * 
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AatTransferServiceTest extends AbstractConfig {

    /**
     * Create a account and try to receive this account base on the accounts
     * UUID.
     */
    @Test
    public void testTransferServiceAccount() {

        final String accountUUID = transferService.createAccount("200", "10",
                "Theo");

        assertEquals("Theo", transferService.getAccount(accountUUID).getName());
    }

    /**
     * Test transfer service save account.
     */
    @Test
    public void testTransferServiceSaveAccount() {

        final Account account = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(21.00), "Nice");
        transferService.saveAccount(account);

        assertTrue(1000.50 == transferService.findAccountByName("Nice")
                .getBalance().doubleValue());

    }

    /**
     * Test transfer service not found account.
     */
    @Test(expected = AnanieNotFoundException.class)
    public void testTransferServiceNotFoundAccount() {
        transferService.getAccount("6ebb8693-0000-0000-80e1-89323971e98a");
    }

    /**
     * Test transfer service find all accounts.
     */
    @Test
    public void testTransferServiceFindAllAccounts() {

        final List<Account> accountsRetrieved = transferService
                .findAllAccounts();
        assertTrue("Jasper"
                .equalsIgnoreCase(accountsRetrieved.get(0).getName()));
        assertTrue(accountsRetrieved.size() == 2);

    }

    /**
     * Test transfer service transfer returning a Transfer id.
     */
    @Test
    @Rollback(true)
    public void testTransferServiceTransfer() {

        final String accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final String accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        assertTrue(transferService.doTransfer(accountTheo, accountSaskia, 50) != 0);

    }

    /**
     * Test overdraft of the account, the transfer cannot complete.
     */
    @Test
    public void testTransferServiceOverdraft() {

        final String accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final String accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        final Integer transferTransactionId = transferService.doTransfer(
                accountTheo, accountSaskia, 350);
        assertEquals("INSUFFICIENT_FUNDS",
                transferService.findTransferById(transferTransactionId)
                        .getState());
    }

    /**
     * Testing/searching for the amount (Total) of the transfer.
     */
    @Test
    public void testTransferServiceTotalOfTransfer() {

        final Transfer transfer = transferService.findTransferById(0);
        assertTrue(transfer.getTotaal() == 25.10);

    }

    /**
     * Saving a Transfer, we have one already inserted at startup.
     */
    @Test
    @Rollback(true)
    public void testTransferServiceSaveTransfer() {

        final Transfer transfer = new Transfer();
        transfer.setTotaal(BigDecimal.valueOf(20.50));

        transfer.setCredit(UUID
                .fromString("6ebb8693-7179-4e04-80e1-89323971e98a"));
        transfer.setDebet(UUID
                .fromString("6ebb8693-7189-4e04-80e1-89323971e98a"));

        assertTrue(transferService.saveTransfer(transfer) != 0);
    }

    /**
     * Test transfer service finding all transfers and checking there states.
     */
    @Test
    public void testTransferServiceFindAllTransfers() {
        final List<Transfer> transfers = transferService.findAllTransfers();
        assertTrue(transfers.size() == 1);
        assertEquals("CONFIRMED", transfers.get(0).getState());
    }

    /**
     * Test transfer switching states of the Transfer.
     */
    @Test
    @Rollback(true)
    public void testTransferServiceTransferState() {

        final String accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final String accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        final Transfer transfer = new Transfer();
        transfer.setTotaal(BigDecimal.valueOf(20.50));

        transfer.setCredit(UUID.fromString(accountTheo));
        transfer.setDebet(UUID.fromString(accountSaskia));

        final Integer transferSaveId = transferService.saveTransfer(transfer);

        assertEquals("PENDING", transferService
                .findTransferById(transferSaveId).getState());

        final Integer transferTransactionId = transferService
                .doTransfer(transfer);

        assertEquals("CONFIRMED",
                transferService.findTransferById(transferTransactionId)
                        .getState());

        assertTrue(20.50 == transferService.findTransferById(
                transferTransactionId).getTotaal());

    }

    /**
     * Test transfer service finding all transactions that are inserted at
     * startup.
     */
    @Test
    public void testTransferServiceFindAllTransactions() {
        final List<Transaction> transactions = transferService
                .findAllTransactions();

        assertTrue("6ebb8693-7179-4e04-80e1-89323971e98a"
                .equalsIgnoreCase(transactions.get(0).getAccount().toString()));
        assertTrue(transactions.size() == 1);

    }

    /**
     * Finding the total Transfer of a Transaction.
     */
    @Test
    public void testTransferServiceTotalTransferByTransaction() {

        final Transaction trans = transferService.findTransaction(0);

        assertTrue("6ebb8693-7179-4e04-80e1-89323971e98a"
                .equalsIgnoreCase(trans.getAccount().toString()));
        assertTrue(trans.getTransfer().getTotaal() == 25.10);

    }
}
