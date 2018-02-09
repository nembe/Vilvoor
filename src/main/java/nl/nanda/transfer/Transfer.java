package nl.nanda.transfer;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.nanda.account.Account;
import nl.nanda.account.Amount;
import nl.nanda.status.Status;

// TODO: Auto-generated Javadoc
/**
 * An Transfer for a account of the Ananie Bank. An account has one or more
 * beneficiaries whose allocations must add up to 100%.
 * 
 * An account can make contributions to its beneficiaries. Each contribution is
 * distributed among the beneficiaries based on an allocation.
 * 
 * An entity. An aggregate.
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
    @Embedded
    @AttributeOverride(name = "state", column = @Column(name = "STATUS_ID"))
    private Status states;

    /** The day. */
    @Column(name = "TRANSFER_DATE")
    private Date day;

    /** The totaal. */
    @Embedded
    @AttributeOverride(name = "totaal", column = @Column(name = "AMOUNT"))
    private Amount totaal;

    /**
     * Instantiates a new transfer.
     */
    public Transfer() {
    }

    /**
     * Instantiates a new transfer.
     *
     * @param value the value
     */
    public Transfer(final BigDecimal value) {
        this.states = new Status(3);
        this.day = Date.valueOf(LocalDate.now());
        this.totaal = new Amount(value);
    }

    /**
     * Gets the credit.
     *
     * @return the credit
     */
    public UUID getCredit() {
        return credit;
    }

    /**
     * Gets the debet.
     *
     * @return the debet
     */
    public UUID getDebet() {
        return debet;
    }

    /**
     * Sets the credit.
     *
     * @param credit the new credit
     */
    public void setCredit(final UUID credit) {
        this.credit = credit;
    }

    /**
     * Sets the debet.
     *
     * @param debet the new debet
     */
    public void setDebet(final UUID debet) {
        this.debet = debet;
    }

    /**
     * Gets the states.
     *
     * @return the states
     */
    public String getStates() {
        return states.valueOf();
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
    public String getTotaal() {
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
     * Start transfer.
     *
     * @param zender the zender
     * @param ontvanger the ontvanger
     */
    public void startTransfer(final Account zender, final Account ontvanger) {

        this.credit = zender.getAccountUUID();
        this.debet = ontvanger.getAccountUUID();
        this.totaal.creditAccount(zender);
        this.totaal.debetAccount(ontvanger);
        this.states.setState(1);
    }

}
