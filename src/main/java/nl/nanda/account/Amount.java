package nl.nanda.account;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

import nl.nanda.exception.SvaException;

// TODO: Auto-generated Javadoc
/**
 * The Class Amount.
 */
@Embeddable
public class Amount implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6382918820030954080L;

    /** The totaal. */
    private BigDecimal totaal;

    /**
     * Instantiates a new amount.
     *
     * @param value the value
     */
    public Amount(final BigDecimal value) {
        this.totaal = value;
    }

    /**
     * Instantiates a new amount.
     */
    @SuppressWarnings("unused")
    private Amount() {

    }

    /**
     * Return value.
     *
     * @return the string
     */
    public String returnValue() {
        return totaal.toPlainString();
    }

    /**
     * Credit account.
     *
     * @param acct the acct
     */
    public void creditAccount(final Account acct) {
        final BigDecimal newAmount = acct.getAmount().subtract(totaal);
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            throw new SvaException("Geen Extra voor meneer of mevrouw");
        }
        acct.setBalance(newAmount.subtract(acct.getOverdraft()));
    }

    /**
     * Debet account.
     *
     * @param acct the acct
     */
    public void debetAccount(final Account acct) {
        final BigDecimal newAmount = acct.getBalance().add(totaal);
        acct.setBalance(newAmount);

    }
}
