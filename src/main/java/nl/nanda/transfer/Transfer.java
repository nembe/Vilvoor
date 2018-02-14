package nl.nanda.transfer;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import nl.nanda.account.Account;
import nl.nanda.account.Amount;
import nl.nanda.status.Status;

/**
 * An Transfer for are to a account of the Ananie Bank. For updating the Account
 * entity we delegate that to the Amount class. The Status of the Transfer is
 * updated accordingly if a exception occur, we deal with that in the service
 * layer.
 * 
 * 
 *
 */
@Entity
@Table(name = "T_TRANSFER")
public class Transfer {

    /** The entity id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer entityId;

    /** The credit. */
    @Column(name = "FROM_ACCOUNT")
    private UUID credit;

    /** The debet. */
    @Column(name = "TO_ACCOUNT")
    private UUID debet;

    /** The states. */
    @Enumerated
    @Column(columnDefinition = "smallint", name = "STATUS_ID")
    private Status state;

    /** The day. */
    @Column(name = "TRANSFER_DATE")
    private final Date day;

    /** The totaal. */
    @Embedded
    @AttributeOverride(name = "totaal", column = @Column(name = "AMOUNT"))
    private final Amount totaal;

    @Transient
    private Account zender;

    @Transient
    private Account ontvanger;

    /**
     * Instantiates a new transfer. Before saving this transfer the accounts
     * UUID's have to be set first.
     * 
     * This can be done from the Service layer especially when transfering is
     * required. Are use the other constructor if needed to transfer money.
     * 
     */
    public Transfer() {
        this.totaal = new Amount(BigDecimal.valueOf(0));
        this.state = Status.PENDING;
        this.day = Date.valueOf(LocalDate.now());
    }

    /**
     * Instantiates a new transfer. Before starting the transfer we need two
     * Accounts entities objects.
     * 
     * @param zender
     *            = the sender account entity object (sending money).
     * @param ontvanger
     *            = the ontvanger account entity object (receiving money).
     */
    public Transfer(final Account zender, final Account ontvanger) {
        this.totaal = new Amount(BigDecimal.valueOf(0));
        this.state = Status.PENDING;
        this.day = Date.valueOf(LocalDate.now());
        this.debet = ontvanger.getAccountUUID();
        this.credit = zender.getAccountUUID();
        this.zender = zender;
        this.ontvanger = ontvanger;
    }

    /**
     * Gets the credit UUID belonging to the senders Account.
     *
     * @return the credit
     */
    public UUID getCredit() {
        return credit;
    }

    /**
     * Gets the debet UUID belonging to the receiver Account.
     *
     * @return the debet
     */
    public UUID getDebet() {
        return debet;
    }

    /**
     * Sets the credit.
     *
     * @param credit
     *            the new credit
     */
    public void setCredit(final UUID credit) {
        this.credit = credit;
    }

    /**
     * Sets the debet.
     *
     * @param debet
     *            the new debet
     */
    public void setDebet(final UUID debet) {
        this.debet = debet;
    }

    /**
     * Gets the states.
     *
     * @return the states
     */
    public String getState() {
        return state.valueOf();
    }

    /**
     * Gets the day.
     *
     * @return the day
     */
    public Date getDay() {
        return day;
    }

    /**
     * Gets the totaal.
     *
     * @return the totaal
     */
    public double getTotaal() {
        return totaal.returnValue();
    }

    /**
     * Gets the entity id.
     *
     * @return the entity id
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * Setting the total saldo for this transfer.
     * 
     * @param value
     */
    public void setTotaal(final BigDecimal value) {
        this.totaal.setTotaal(value);
    }

    /**
     * Changing the state of the Transfer.
     * 
     * @param state
     */
    public void setState(final Status state) {
        this.state = state;
    }

    /**
     * Starting the transfer we need Two Accounts entities objects.
     * 
     * @param value
     *            = The total that must be transfer between accounts.
     */
    public void startTransfer(final BigDecimal value) {
        this.totaal.setTotaal(value);
        this.totaal.creditAccount(zender);
        this.totaal.debetAccount(ontvanger);
        this.state = Status.CONFIRMED;
    }

}
