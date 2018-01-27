package nl.nanda.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "T_TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long entityId;

    @Column(name = "ACCOUNT_ID")
    private Long account;

    @Column(name = "TRANSFER_ID")
    private Long transfer;

    public Transaction() {
    }

    public Transaction(final Long account, final Long transfer) {
        this.account = account;
        this.transfer = transfer;
    }

    public void setEntityId(final Long entityId) {
        this.entityId = entityId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public Long getAccount() {
        return account;
    }

    public Long getTransfer() {
        return transfer;
    }

}
