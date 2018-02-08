package nl.nanda.transaction;

import java.util.UUID;

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

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer entityId;

    @Column(name = "ACCOUNT_ID")
    private UUID account;

    @OneToOne
    @JoinColumn(name = "TRANSFER_ID")
    private Transfer transfer;

    public Transaction() {
    }

    /**
     * Create a Transaction entity Object with a unique id and transfer.
     * 
     * @param account
     * @param transfer
     */
    public Transaction(final UUID account, final Transfer transfer) {
        this.account = account;
        this.transfer = transfer;
    }

    /**
     * Set the PrimaryKey for the Transaction.
     * 
     * @param entityId
     */
    public void setEntityId(final Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * Get the PrimaryKey.
     * 
     * @return
     */
    public Integer getEntityId() {
        return entityId;
    }

    /**
     * Return the unique id of a account.
     * 
     * @return
     */
    public UUID getAccount() {
        return account;
    }

    /**
     * Return the Transfer of the transaction.
     * 
     * @return
     */
    public Transfer getTransfer() {
        return transfer;
    }

}
