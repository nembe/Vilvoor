package nl.nanda.transaction.dao;

import java.util.List;
import java.util.UUID;

import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Uses Spring Data JPA to retrieved Transactions.
 *
 */
public interface TransactionRepository extends
        JpaRepository<Transaction, Integer> {

    /**
     * Searching for a Transaction with a given ID (primary key).
     * 
     * @param id
     * @return
     */
    public Transaction findByEntityId(Integer id);

    /**
     * Searching for a Transaction with a given unique account id (UUID key).
     * 
     * @param id
     * @return
     */
    public Transaction findByAccount(UUID id);

    /**
     * Searching for a Transaction with a given Transfer ID (primary key).
     * 
     * @param id
     * @return
     */
    public Transaction findByTransfer(Transfer transfer);

    /**
     * Searching for all Transactions with a given unique account id (UUID key).
     * 
     * @param id
     * @return
     */
    // @Query("select o from Transaction o where o.account = :uuid")
    public List<Transaction> findAllByAccount(UUID id);

}
