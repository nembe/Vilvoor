package nl.nanda.transfer;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import nl.nanda.account.Account;
import nl.nanda.config.AbstractConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class SvaTransferRepoTest extends AbstractConfig {
    @Test
    // @Ignore
    public void testTransfer() {
        final Transfer trans = transferRepo.findByDay(Date.valueOf(LocalDate
                .of(2018, 01, 02)));
        System.out.println("testTransfer " + trans.getTotaal());
    }

    @Test
    // @Ignore
    public void testTransferRepo() {

        final Account acc = new Account(BigDecimal.valueOf(65.70),
                BigDecimal.valueOf(0.00), "Nencke");
        final Account acc2 = new Account(BigDecimal.valueOf(1000.50),
                BigDecimal.valueOf(20.00), "Jorka");
        final Transfer trans = new Transfer(BigDecimal.valueOf(20.50));

        acc.setEntityId(1L);
        acc2.setEntityId(2L);
        trans.startTransfer(acc, acc2);
        System.out.println("1: testTransferService " + acc.getBalance());
        System.out.println("2: testTransferService " + acc2.getBalance());
        System.out.println("3: testTransferService " + trans.getStates());

        System.out.println("4: testTransferService " + trans.getCredit());

        System.out.println("5: testTransferService "
                + transferRepo.save(trans).getEntityId());
    }
}
