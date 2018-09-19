package nl.nanda.jpa.transaction.dao;

import java.util.List;
import java.util.UUID;

import nl.nanda.jpa.transaction.Transaction;
import nl.nanda.jpa.transfer.Transfer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Uses Spring Data JPA to retrieved Transactions.
 *
 */
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    /**
     * Searching for a Transaction with a given ID (primary key).
     *
     * @param id
     *            the id
     * @return the transaction
     */
    Transaction findByEntityId(Integer id);

    /**
     * Searching for a All Transactions with a given unique account id (UUID
     * key).
     *
     * @param id
     *            the id
     * @return the transaction
     */
    List<Transaction> findByAccount(UUID id);

    /**
     * Searching for a Transaction with a given Transfer ID (primary key).
     *
     * @param transfer
     *            the transfer
     * @return the transaction
     */
    Transaction findByTransfer(Transfer transfer);

}
