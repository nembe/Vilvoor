package nl.nanda.service;

import java.util.Iterator;

import nl.nanda.account.Account;
import nl.nanda.config.AbstractConfig;
import nl.nanda.exception.SvaException;
import nl.nanda.transfer.Transfer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SvaTransferServiceTest extends AbstractConfig {

    @Test
    public void testTransferServiceAccount() {

        System.out.println("1: testTransferService "
                + transferService.createAccount(Long.valueOf(200),
                        Long.valueOf(10), "Theo"));

        System.out.println("2: testTransferService "
                + transferService.getAccount(2L).getName());

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
}
