package nl.nanda.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.persistence.Embeddable;

import nl.nanda.exception.SvaException;

@Embeddable
public class Amount implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6382918820030954080L;

    private BigDecimal totaal;

    public Amount(final BigDecimal value) {
        this.totaal = value;
    }

    private Amount() {

    }

    private void initValue(final BigDecimal value) {
        this.totaal = value.setScale(2, RoundingMode.HALF_EVEN);
    }

    public String returnValue() {

        return totaal.toPlainString();
    }

    public void transfer(final Account acct) {
        System.out.println("1 transfer " + acct.getBalance().intValue());
        System.out.println("1.1 transfer " + totaal.intValue());
        final BigDecimal newAmount = acct.getAmount().subtract(totaal);
        System.out.println("2 transfer " + newAmount.intValue());
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new SvaException("Geen Extra voor meneer of mevrouw");
        }
        acct.setBalance(newAmount.subtract(acct.getOverdraft()));
    }

    public void updateAccount(final Account acct) {
        System.out.println("1 updateAccount " + totaal.intValue());
        System.out.println("2 updateAccount acct.getBalance() "
                + acct.getBalance().intValue());
        final BigDecimal newAmount = acct.getBalance().add(totaal);
        System.out.println("3 updateAccount " + newAmount.intValue());
        acct.setBalance(newAmount);
    }
}
