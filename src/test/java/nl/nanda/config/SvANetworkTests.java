package nl.nanda.config;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;
import nl.nanda.transfer.dao.TransferRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A system test that verifies the components of the RewardNetwork application
 * work together to reward for dining successfully. Uses Spring to bootstrap the
 * application for use in a test environment.
 */
@ContextConfiguration(classes = { AccountsConfig.class })
@RunWith(SpringJUnit4ClassRunner.class)
public class SvANetworkTests {

    /**
     * The object being tested.
     */
    @Autowired
    AccountRepository repo;

    @Autowired
    TransferRepository repo2;

    @Autowired
    TransactionRepository repo3;

    @Test
    // @Ignore
    public void testAccount() {

        // entityManagerFactory.
        final Account acc = repo.findAccountById(0);
        System.out.println("Account " + acc.getName());

        final Account acc2 = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(20.00), "Jorka");
        repo.saveAndFlush(acc2);
        final Account acc3 = repo.findAccountById(2);
        System.out.println("Account " + acc3.getName());

    }

    @Test
    // @Ignore
    public void testTransfer() {
        final Transfer trans = repo2.findByDay(Date.valueOf(LocalDate.of(2018,
                01, 02)));
        System.out.println("testTransfer " + trans.getTotaal());
    }

    @Test
    // @Ignore
    public void testTransferService() {

        final Account acc = new Account(BigDecimal.valueOf(65.70),
                BigDecimal.valueOf(0.00), "Nencke");
        final Account acc2 = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(20.00), "Jorka");
        final Transfer trans = new Transfer(BigDecimal.valueOf(20.50));

        trans.startTransfer(acc, acc2);
        System.out.println("1: testTransferService " + acc.getBalance());
        System.out.println("2: testTransferService " + acc2.getBalance());
        System.out.println("3: testTransferService " + trans.getStates());
    }

    @Test
    // @Ignore
    public void testTransaction() {

        final Transaction txn = repo3.findByAccount(1);
        System.out.println("testTransaction " + txn.getTransfer());
    }
}