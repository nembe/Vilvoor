package nl.nanda.account;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import nl.nanda.exception.SvaException;

@Entity
@Table(name = "T_ACCOUNT")
public class Account implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3642534679048425984L;

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer entityId;

    @Column(name = "OVERDRAFT")
    private BigDecimal overdraft;

    @Column(name = "BALANCE")
    private BigDecimal balance;

    @Column(name = "NAME")
    private String name;

    private BigDecimal amount;

    // @OneToMany
    // @JoinColumn(name = "ACCOUNT_ID")
    // private Set<Transaction> transactions = new HashSet<Transaction>();
    //
    // @OneToMany
    // @Lazy
    // @JoinColumn(name = "FROM_ACCOUNT")
    // private Set<Transfer> transfers = new HashSet<Transfer>();

    @SuppressWarnings("unused")
    private Account() {
        // Required by JPA
    }

    public Account(final BigDecimal balance, final BigDecimal overdraft,
            final String name) {
        this.balance = balance;
        this.overdraft = overdraft;
        this.name = name;
    }

    /**
     * Returns the entity identifier used to internally distinguish this entity
     * among other entities of the same type in the system. Should typically
     * only be called by privileged data access infrastructure code such as an
     * Object Relational Mapper (ORM) and not by application code.
     * 
     * @return the internal entity identifier
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * Sets the internal entity identifier - should only be called by privileged
     * data access code (repositories that work with an Object Relational Mapper
     * (ORM)) or unit tests. Should never be set by application code explicitly.
     * Hence it is package local.
     * 
     * @param entityId
     *            the internal entity identifier
     */
    void setEntityId(final Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * Returns the name on file for this account.
     */
    public String getName() {
        return name;
    }

    public BigDecimal getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(final BigDecimal overdraft) throws SvaException {
        if (overdraft.compareTo(BigDecimal.ZERO) == 0) {
            throw new SvaException("Geen Extra voor meneer of mevrouw");
        }
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
        System.out.println("1 Account overdraft " + overdraft.intValue());
        System.out.println("2 Account balance " + balance.intValue());
        return this.balance.add(overdraft);
    }

    // public Set<Transaction> getTransactions() {
    // return transactions;
    // }

}
