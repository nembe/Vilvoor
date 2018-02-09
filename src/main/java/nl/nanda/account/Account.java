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

// TODO: Auto-generated Javadoc
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

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 3642534679048425984L;

    /** The entity id. */
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Integer entityId;

    /** The account uuid. */
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ACCOUNT_UUID")
    private UUID account_uuid;

    /** The overdraft. */
    @Column(name = "OVERDRAFT")
    private BigDecimal overdraft;

    /** The balance. */
    @Column(name = "BALANCE")
    private BigDecimal balance;

    /** The name. */
    @Column(name = "NAME")
    private String name;

    /** The amount. */
    @SuppressWarnings("unused")
    private BigDecimal amount;

    /**
     * Instantiates a new account.
     */
    @SuppressWarnings("unused")
    private Account() {
    }

    /**
     * Instantiates a new account.
     *
     * @param balance the balance
     * @param overdraft the overdraft
     * @param name the name
     */
    public Account(final BigDecimal balance, final BigDecimal overdraft,
            final String name) {
        this.balance = balance;
        this.overdraft = overdraft;
        this.name = name;
    }

    /**
     * Instantiates a new account.
     *
     * @param balance the balance
     * @param name the name
     */
    public Account(final BigDecimal balance, final String name) {
        this.balance = balance;
        this.name = name;
        this.overdraft = BigDecimal.valueOf(0);
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
     * Sets the entity id.
     *
     * @param entityId the new entity id
     */
    public void setEntityId(final Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the overdraft.
     *
     * @return the overdraft
     */
    public BigDecimal getOverdraft() {
        return overdraft;
    }

    /**
     * Sets the overdraft.
     *
     * @param overdraft the new overdraft
     * @throws SvaException the sva exception
     */
    public void setOverdraft(final BigDecimal overdraft) throws SvaException {
        this.overdraft = overdraft;
    }

    /**
     * Gets the balance.
     *
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the balance.
     *
     * @param balance the new balance
     */
    public void setBalance(final BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Sets the name.
     *
     * @param name the new name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets the amount.
     *
     * @return the amount
     */
    public BigDecimal getAmount() {
        return this.balance.add(overdraft);
    }

    /**
     * Sets the amount.
     *
     * @param amount the new amount
     */
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Gets the account UUID.
     *
     * @return the account UUID
     */
    public UUID getAccountUUID() {
        return account_uuid;
    }

    /**
     * Sets the account UUID.
     *
     * @param account_uuid the new account UUID
     */
    public void setAccountUUID(final UUID account_uuid) {
        this.account_uuid = account_uuid;
    }

}
