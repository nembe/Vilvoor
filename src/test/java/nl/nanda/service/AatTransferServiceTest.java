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

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AatTransferServiceTest extends AbstractConfig {

    @Test
    public void testTransferServiceAccount() {

        final UUID account = transferService.createAccount("200", "10", "Theo");

        System.out.println("2: testTransferService "
                + transferService.getAccount(account).getName());

    }

    @Test
    public void testTransferServiceSaveAccount() {

        final Account account = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(21.00), "Nice");
        transferService.saveAccount(account);

        System.out.println("2: testTransferServiceSaveAccount "
                + transferService.findAccountByName("Nice").getBalance());

    }

    @Test(expected = SvaNotFoundException.class)
    public void testTransferServiceNotFoundAccount() {

        transferService.getAccount(UUID
                .fromString("6ebb8693-0000-0000-80e1-89323971e98a"));
    }

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

    @Test
    public void testTransferServiceTransfer() {

        final UUID accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final UUID accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        System.out.println("3: testTransferService "
                + transferService.doTransfer("" + accountTheo, ""
                        + accountSaskia, 50));

        // System.out.println("3: testTransferService "
        // + accountRepo.findAccountById(accountTheo.intValue())
        // .getBalance());
        //
        // System.out.println("3: testTransferService "
        // + accountRepo.findAccountById(accountSaskia.intValue())
        // .getBalance());

    }

    @Test(expected = SvaException.class)
    public void testTransferServiceOverdraft() {

        final UUID accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final UUID accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        System.out.println("3: testTransferService "
                + transferService.doTransfer("" + accountTheo, ""
                        + accountSaskia, 350));

    }

    @Test
    public void testTransferServiceSearch() {

        final Transfer transfer = transferService.findTransferById(0);
        System.out.println("3: testTransferService " + transfer.getTotaal());

    }

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

    @Test
    public void testTransferServiceTransaction() {

        final UUID accountTheo = transferService.createAccount("200", "10",
                "Theo");
        final UUID accountSaskia = transferService.createAccount("0", "0",
                "Saskia");

        final Integer transferId = transferService.doTransfer("" + accountTheo,
                "" + accountSaskia, 25);

        // System.out.println("3: testTransferService "
        // + transferService.findTransactionByTransfer(transferId)
        // .getAccount());

    }

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
