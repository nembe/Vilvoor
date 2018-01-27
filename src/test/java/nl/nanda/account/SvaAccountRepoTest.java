package nl.nanda.account;

import java.math.BigDecimal;
import java.util.Iterator;

import nl.nanda.config.AbstractConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A system test that verifies the components of the RewardNetwork application
 * work together to reward for dining successfully. Uses Spring to bootstrap the
 * application for use in a test environment.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class SvaAccountRepoTest extends AbstractConfig {

    @Test
    public void testAccount() {

        // entityManagerFactory.
        final Account acc = accountRepo.findAccountById(0L);
        System.out.println("Account " + acc.getName());

        final Account acc2 = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(20.00), "Jorka");
        accountRepo.saveAndFlush(acc2);
        final Account acc3 = accountRepo.findAccountById(2L);
        System.out.println("Account " + acc3.getName());

    }

    @Test
    public void testFindAllAccounts() {

        final Iterator<Account> itarator = accountRepo.findAll().iterator();

        while (itarator.hasNext()) {
            final Account obj = itarator.next();
            System.out.println("testFindAllAccount " + obj.getName());
        }

    }

    @Test
    public void testAccountAmount() {

        final Account acc2 = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(20.00), "Jorka");

        System.out.println("Account Amount " + acc2.getAmount());

    }

    @Test
    public void testAccountBalance() {

        final Account acc2 = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(20.00), "Jorka");

        System.out.println("Account Balance " + acc2.getBalance());

    }

}