package nl.nanda.transaction;

import nl.nanda.config.AbstractConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SvaTransactionRepoTest extends AbstractConfig {

    @Test
    public void testTransaction() {

        final Transaction txn = transactionRepo.findByAccount(1L);
        System.out.println("testTransaction " + txn.getTransfer());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testTransactionViolation() {

        final Transaction transaction = new Transaction(0L, 0L);
        transactionRepo.saveAndFlush(transaction);
    }

    @Test
    public void testFindTransactionByAccount() {

        System.out.println("3: testTransferService "
                + transactionRepo.findByAccount(0L));

    }

}
