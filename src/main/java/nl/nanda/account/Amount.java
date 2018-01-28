package nl.nanda.account;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

import nl.nanda.exception.SvaException;

@Embeddable
public class Amount implements Serializable {

    private static final long serialVersionUID = 6382918820030954080L;

    private BigDecimal totaal;

    public Amount(final BigDecimal value) {
        this.totaal = value;
    }

    @SuppressWarnings("unused")
    private Amount() {

    }

    public String returnValue() {
        return totaal.toPlainString();
    }

    public void creditAccount(final Account acct) {
        final BigDecimal newAmount = acct.getAmount().subtract(totaal);
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new SvaException("Geen Extra voor meneer of mevrouw");
        }
        acct.setBalance(newAmount.subtract(acct.getOverdraft()));
    }

    public void debetAccount(final Account acct) {
        final BigDecimal newAmount = acct.getBalance().add(totaal);
        acct.setBalance(newAmount);

    }
}
