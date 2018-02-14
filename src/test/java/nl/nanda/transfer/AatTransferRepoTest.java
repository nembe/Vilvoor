package nl.nanda.transfer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import nl.nanda.account.Account;
import nl.nanda.config.AbstractConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Testing the TransferRepository.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AatTransferRepoTest extends AbstractConfig {

    /**
     * Try to find a Transfer with a given Day.
     */
    @Test
    public void testTransfer() {
        final Transfer trans = transferRepo.findByDay(Date.valueOf(LocalDate
                .of(2018, 01, 02)));
        assertTrue(25.10 == trans.getTotaal());
    }

    /**
     * Testing starting a Transfer for 20.50. We are checking if the accounts
     * get debet or credit. Also the states of the transfer are checked.
     */
    @Test
    public void testTransferRepo() {

        final Account creditAccount = new Account(BigDecimal.valueOf(65.70),
                BigDecimal.valueOf(0.00), "Nencke");
        final Account debetAccount = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(20.00), "Jorka");

        accountRepo.save(creditAccount);
        accountRepo.save(debetAccount);

        final Transfer trans = new Transfer(creditAccount, debetAccount);

        assertEquals("PENDING", trans.getState());
        trans.startTransfer(BigDecimal.valueOf(20.50));
        assertEquals(BigDecimal.valueOf(45.2), creditAccount.getBalance());
        assertEquals(BigDecimal.valueOf(1021.0), debetAccount.getBalance());
        assertEquals("CONFIRMED", trans.getState());

        assertNotNull(trans.getCredit());
        assertEquals(Integer.valueOf(1), transferRepo.save(trans).getEntityId());
    }
}
