package nl.nanda.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.nanda.exception.SvaException;

import org.hibernate.annotations.GenericGenerator;

/**
 * An account for a member of the reward network. An account has one or more
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
@Table(name = "T_ACCOUNT")
// @IdClass(AccountId.class)
public class Account implements Serializable {

    private static final long serialVersionUID = 3642534679048425984L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer entityId;

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ACCOUNT_UUID")
    private UUID account_uuid;

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
    }

    public Account(final BigDecimal balance, final BigDecimal overdraft,
            final String name) {
        this.balance = balance;
        this.overdraft = overdraft;
        this.name = name;
    }

    public Account(final BigDecimal balance, final String name) {
        this.balance = balance;
        this.name = name;
        this.overdraft = BigDecimal.valueOf(0);
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(final Integer entityId) {
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

    public UUID getAccount_uuid() {
        return account_uuid;
    }

    public void setAccount_uuid(final UUID account_uuid) {
        this.account_uuid = account_uuid;
    }

}
