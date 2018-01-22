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
    private Integer account;

    @Column(name = "TRANSFER_ID")
    private Integer transfer;

    public Transaction() {
    }

    public Integer getAccount() {
        return account;
    }

    public Integer getTransfer() {
        return transfer;
    }

}
