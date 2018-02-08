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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer entityId;

    @Column(name = "FROM_ACCOUNT")
    private UUID credit;

    @Column(name = "TO_ACCOUNT")
    private UUID debet;

    @Embedded
    @AttributeOverride(name = "state", column = @Column(name = "STATUS_ID"))
    private Status states;

    @Column(name = "TRANSFER_DATE")
    private Date day;

    @Embedded
    @AttributeOverride(name = "totaal", column = @Column(name = "AMOUNT"))
    private Amount totaal;

    public Transfer() {
    }

    public Transfer(final BigDecimal value) {
        this.states = new Status(3);
        this.day = Date.valueOf(LocalDate.now());
        this.totaal = new Amount(value);
    }

    public UUID getCredit() {
        return credit;
    }

    public UUID getDebet() {
        return debet;
    }

    public void setCredit(final UUID credit) {
        this.credit = credit;
    }

    public void setDebet(final UUID debet) {
        this.debet = debet;
    }

    public String getStates() {
        return states.valueOf();
    }

    public Date getDay() {
        return day;
    }

    public String getTotaal() {
        return totaal.returnValue();
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void startTransfer(final Account zender, final Account ontvanger) {

        this.credit = zender.getAccount_uuid();
        this.debet = ontvanger.getAccount_uuid();
        this.totaal.creditAccount(zender);
        this.totaal.debetAccount(ontvanger);
        this.states.setState(1);
    }

}
