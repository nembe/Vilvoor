package nl.nanda.jpa.account;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import nl.nanda.status.Status;

/**
 * The Class Amount where the Total of the Transaction is calculated. After
 * calculation the Accounts Objects are updated.
 */
@Embeddable
public class Amount implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 6382918820030954080L;

    /** The totaal. */
    @NotNull
    private BigDecimal totaal;

    /**
     * Instantiates a new amount.
     *
     * @param value
     *            the value
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
    public double returnValue() {
        return totaal.doubleValue();
    }

    /**
     * Credit the account that started the transfer. The service layer will
     * noticed the upper layers if the transfer finish successful.
     *
     * @param account
     *            the account that must be credit.
     */
    public Status creditAccount(final Account account) {
        final BigDecimal newAmount = account.getAmount().subtract(totaal);
        if (newAmount.compareTo(BigDecimal.ZERO) < 0) {
            return Status.INSUFFICIENT_FUNDS;
        }
        account.setBalance(newAmount.subtract(account.getOverdraft()));
        return Status.CONFIRMED;
    }

    /**
     * Debet the account that is receiving money.
     *
     * @param account
     *            the acct
     */
    public void debetAccount(final Account account) {
        final BigDecimal newAmount = account.getBalance().add(totaal);
        account.setBalance(newAmount);

    }

    /**
     * Returing the total saldo.
     * 
     * @return
     */
    public BigDecimal getTotaal() {
        return totaal;
    }

    /**
     * Setting the total saldo.
     * 
     * @param totaal
     */
    public void setTotaal(final BigDecimal totaal) {
        this.totaal = totaal;
    }

}
