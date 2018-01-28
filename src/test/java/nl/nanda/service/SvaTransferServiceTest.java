package nl.nanda.service;

import java.math.BigDecimal;
import java.util.Iterator;

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
public class SvaTransferServiceTest extends AbstractConfig {

    @Test
    public void testTransferServiceAccount() {

        final Long account = transferService.createAccount(Long.valueOf(200),
                Long.valueOf(10), "Theo");

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

        transferService.getAccount(22222L);
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

        final Long accountTheo = transferService.createAccount(
                Long.valueOf(200), Long.valueOf(10), "Theo");
        final Long accountSaskia = transferService.createAccount(
                Long.valueOf(0), Long.valueOf(0), "Saskia");

        System.out.println("3: testTransferService "
                + transferService.doTransfer(accountTheo, accountSaskia, 50));

        System.out.println("3: testTransferService "
                + accountRepo.findAccountById(accountTheo).getBalance());

        System.out.println("3: testTransferService "
                + accountRepo.findAccountById(accountSaskia).getBalance());

    }

    @Test(expected = SvaException.class)
    public void testTransferServiceOverdraft() {

        final Long accountTheo = transferService.createAccount(
                Long.valueOf(200), Long.valueOf(10), "Theo");
        final Long accountSaskia = transferService.createAccount(
                Long.valueOf(0), Long.valueOf(0), "Saskia");

        System.out.println("3: testTransferService "
                + transferService.doTransfer(accountTheo, accountSaskia, 350));

    }

    @Test
    public void testTransferServiceSearch() {

        final Transfer transfer = transferService.findTransferById(0L);
        System.out.println("3: testTransferService " + transfer.getTotaal());

    }

    @Test
    public void testTransferServiceSave() {

        final Transfer transfer = new Transfer(BigDecimal.valueOf(20.50));

        transfer.setCredit(1L);
        transfer.setDebet(2L);

        final Long id = transferService.saveTransfer(transfer);
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

        final Long accountTheo = transferService.createAccount(
                Long.valueOf(200), Long.valueOf(10), "Theo");
        final Long accountSaskia = transferService.createAccount(
                Long.valueOf(0), Long.valueOf(0), "Saskia");

        final Long transferId = transferService.doTransfer(accountTheo,
                accountSaskia, 25);

        System.out.println("3: testTransferService "
                + transferService.findTransactionByTransfer(transferId)
                        .getAccount());

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
