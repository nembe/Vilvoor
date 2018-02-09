package nl.nanda.service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.UUID;

import javax.transaction.Transactional;

import nl.nanda.account.Account;
import nl.nanda.config.AbstractConfig;
import nl.nanda.exception.SvaException;
import nl.nanda.exception.SvaNotFoundException;
import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// TODO: Auto-generated Javadoc
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
     * Test transfer service account.
     */
    @Test
    public void testTransferServiceAccount() {

        final String accountUUID = transferService.createAccount("200", "10",
                "Theo");

        System.out.println("2: testTransferService "
                + transferService.getAccount(accountUUID).getName());

    }

    /**
     * Test transfer service save account.
     */
    @Test
    public void testTransferServiceSaveAccount() {

        final Account account = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(21.00), "Nice");
        transferService.saveAccount(account);

        System.out.println("2: testTransferServiceSaveAccount "
                + transferService.findAccountByName("Nice").getBalance());

    }

    /**
     * Test transfer service not found account.
     */
    @Test(expected = SvaNotFoundException.class)
    public void testTransferServiceNotFoundAccount() {

        transferService.getAccount("6ebb8693-0000-0000-80e1-89323971e98a");
    }

    /**
     * Test transfer service find all account.
     */
    @Test
    public void testTransferServiceFindAllAccount() {

        final Iterator<Account> itarator = transferService.findAllAccounts()
                .iterator();

        while (itarator.hasNext()) {
            final Account obj = itarator.next();
            System.out.println("testTransferServiceFindAllAccount "
                    + obj.getName());
        }

    }

    /**
     * Test transfer service transfer.
     */
    @Test
    public void testTransferServiceTransfer() {

        final String accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final String accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        System.out.println("3: testTransferService "
                + transferService.doTransfer(accountTheo, accountSaskia, 50));

        // System.out.println("3: testTransferService "
        // + accountRepo.findAccountById(accountTheo.intValue())
        // .getBalance());
        //
        // System.out.println("3: testTransferService "
        // + accountRepo.findAccountById(accountSaskia.intValue())
        // .getBalance());

    }

    /**
     * Test transfer service overdraft.
     */
    @Test(expected = SvaException.class)
    public void testTransferServiceOverdraft() {

        final String accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final String accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        System.out.println("3: testTransferService "
                + transferService.doTransfer("" + accountTheo, ""
                        + accountSaskia, 350));

    }

    /**
     * Test transfer service search.
     */
    @Test
    public void testTransferServiceSearch() {

        final Transfer transfer = transferService.findTransferById(0);
        System.out.println("3: testTransferService " + transfer.getTotaal());

    }

    /**
     * Test transfer service save.
     */
    @Test
    public void testTransferServiceSave() {

        final Transfer transfer = new Transfer(BigDecimal.valueOf(20.50));

        transfer.setCredit(UUID
                .fromString("6ebb8693-7179-4e04-80e1-89323971e98a"));
        transfer.setDebet(UUID
                .fromString("6ebb8693-7189-4e04-80e1-89323971e98a"));

        final Integer id = transferService.saveTransfer(transfer);
        System.out.println("3: testTransferService " + id);

    }

    /**
     * Test transfer service find all transfers.
     */
    @Test
    public void testTransferServiceFindAllTransfers() {
        final Iterator<Transfer> itarator = transferService.findAllTransfers()
                .iterator();

        while (itarator.hasNext()) {
            final Transfer obj = itarator.next();
            System.out.println("testTransferServiceFindAllTransfer "
                    + obj.getStates() + " totaal = " + obj.getTotaal());
        }
    }

    /**
     * Test transfer service transaction.
     */
    @Test
    public void testTransferServiceTransaction() {

        final String accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final String accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        final Integer transferId = transferService.doTransfer("" + accountTheo,
                "" + accountSaskia, 25);

        // System.out.println("3: testTransferService "
        // + transferService.findTransactionByTransfer(transferId)
        // .getAccount());

    }

    /**
     * Test transfer service find all transactions.
     */
    @Test
    public void testTransferServiceFindAllTransactions() {
        final Iterator<Transaction> itarator = transferService
                .findAllTransactions().iterator();

        while (itarator.hasNext()) {
            final Transaction obj = itarator.next();
            System.out.println("testTransferServiceFindAllTransaction "
                    + obj.getAccount() + " Transfer = " + obj.getTransfer());
        }
    }
}
