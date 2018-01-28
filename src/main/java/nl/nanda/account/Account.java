package nl.nanda.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.nanda.exception.SvaException;

@Entity
@Table(name = "T_ACCOUNT")
public class Account implements Serializable {

    private static final long serialVersionUID = 3642534679048425984L;

    @Id
    @Column(name = "ID")
    private Long entityId;

    @Column(name = "OVERDRAFT")
    private BigDecimal overdraft;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Column(name = "NAME")
    private String name;

    @SuppressWarnings("unused")
    private BigDecimal amount;

    @SuppressWarnings("unused")
    private Account() {
        this.entityId = ThreadLocalRandom.current().nextLong(1000000000000L);
    }

    public Account(final BigDecimal balance, final BigDecimal overdraft,
            final String name) {
        this.entityId = ThreadLocalRandom.current().nextLong(1000000000000L);
        this.balance = balance;
        this.overdraft = overdraft;
        this.name = name;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(final Long entityId) {
        this.entityId = entityId;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(final BigDecimal overdraft) throws SvaException {
        this.overdraft = overdraft;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return this.balance.add(overdraft);
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

}
