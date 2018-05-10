package nl.nanda.transaction;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



import nl.nanda.transfer.Transfer;

/**
 * Transaction entity Class. Only if the transfer is successful (CONFIRMED
 * Transfers) a transaction on the given account is recorded.
 *
 */
@Entity
@Table(name = "T_TRANSACTION")
public class Transaction {

    /** The entity id. */
    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer entityId;

    /** The account. */
    @Column(name = "ACCOUNT_ID")
    private UUID account;

    /** The transfer. */
    @OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "TRANSFER_ID")    
    private Transfer transfer;

    /**
     * Instantiates a new transaction.
     */
    public Transaction() {
    }

    /**
     * Create a Transaction entity Object with a unique id and transfer.
     *
     * @param account
     *            the account
     * @param transfer
     *            the transfer
     */
    public Transaction(final UUID account, final Transfer transfer) {
        this.account = account;
        this.transfer = transfer;
    }

    /**
     * Set the PrimaryKey for the Transaction.
     *
     * @param entityId
     *            the new entity id
     */
    public void setEntityId(final Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * Get the PrimaryKey.
     *
     * @return the entity id
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * Return the unique id of a account.
     *
     * @return the account
     */
    public UUID getAccount() {
        return account;
    }

    public void setAccount(final UUID account) {
        this.account = account;
    }

    /**
     * Return the Transfer of the transaction.
     *
     * @return the transfer
     */
    public Transfer getTransfer() {
        return transfer;
    }

}
