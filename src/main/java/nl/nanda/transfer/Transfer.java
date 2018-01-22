package nl.nanda.transfer;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.nanda.account.Account;
import nl.nanda.account.Amount;
import nl.nanda.status.Status;

@Entity
@Table(name = "T_TRANSFER")
public class Transfer {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long entityId;

    @Column(name = "FROM_ACCOUNT")
    private Integer credit;

    @Column(name = "TO_ACCOUNT")
    private Integer debet;

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

    public Integer getCredit() {
        return credit;
    }

    public Integer getDebet() {
        return debet;
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

    public void startTransfer(final Account zender, final Account ontvanger) {
        this.credit = zender.getEntityId();
        this.debet = ontvanger.getEntityId();
        this.totaal.transfer(zender);
        this.totaal.updateAccount(ontvanger);
        this.states.setState(1);
    }

}
