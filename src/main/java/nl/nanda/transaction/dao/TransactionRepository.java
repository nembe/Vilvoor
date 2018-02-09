package nl.nanda.transaction.dao;

import java.util.List;
import java.util.UUID;

import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;

// TODO: Auto-generated Javadoc
/**
 * Uses Spring Data JPA to retrieved Transactions.
 *
 */
public interface TransactionRepository extends
        JpaRepository<Transaction, Integer> {

    /**
     * Searching for a Transaction with a given ID (primary key).
     *
     * @param id the id
     * @return the transaction
     */
    public Transaction findByEntityId(Integer id);

    /**
     * Searching for a Transaction with a given unique account id (UUID key).
     *
     * @param id the id
     * @return the transaction
     */
    public Transaction findByAccount(UUID id);

    /**
     * Searching for a Transaction with a given Transfer ID (primary key).
     *
     * @param transfer the transfer
     * @return the transaction
     */
    public Transaction findByTransfer(Transfer transfer);

    /**
     * Searching for all Transactions with a given unique account id (UUID key).
     *
     * @param id the id
     * @return the list
     */
    // @Query("select o from Transaction o where o.account = :uuid")
    public List<Transaction> findAllByAccount(UUID id);

}
