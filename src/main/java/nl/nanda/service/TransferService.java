package nl.nanda.service;

import java.sql.Date;
import java.util.List;

import nl.nanda.account.Account;
import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

// TODO: Auto-generated Javadoc
/**
 * The CRUD operations for AAT application or done true the TransferService. Who
 * is responsible for delegating requests to the Repositories. We try to
 * encapsulate DataTypes and back-end operations for the UI layered.
 *
 */
public interface TransferService {

    /**
     * Creating a account in the UI layer with minimal conversion of Data Type.
     * Minimal requirements are a balance and user-name.
     *
     * @param balance
     *            (What the account can afford).
     * @param roodToegestaan
     *            (Where the account limit is).
     * @param accountUser
     *            (the account user).
     * @return the UUID string (unique) to be able to find the account.
     */
    String createAccount(final String balance, final String roodToegestaan,
            final String accountUser);

    /**
     * Save account that is new or modified.
     *
     * @param account
     *            the account
     * @return the manage account.
     */
    Account saveAccount(final Account account);

    /**
     * Retrieve the account.
     *
     * @param id
     *            the id (UUID) of the account
     * @return the account
     */
    Account getAccount(final String id);

    /**
     * Find an account by name.
     *
     * @param name
     *            the name that belongs to the account.
     * @return the account
     */
    Account findAccountByName(final String name);

    /**
     * Find all accounts.
     *
     * @return the list with accounts.
     */
    List<Account> findAllAccounts();

    /**
     * Save transfer.
     *
     * @param transfer
     *            the transfer
     * @return the integer
     */
    Integer saveTransfer(final Transfer transfer);

    /**
     * Do transfer.
     *
     * @param from
     *            the from
     * @param to
     *            the to
     * @param amount
     *            the amount
     * @return the integer
     */
    Integer doTransfer(final String from, final String to, final double amount);

    Integer doTransfer(Transfer transfer);

    /**
     * Find transfer by id.
     *
     * @param id
     *            the id
     * @return the transfer
     */
    Transfer findTransferById(final Integer id);

    /**
     * Find transfer by date.
     *
     * @param day
     *            the day
     * @return the transfer
     */
    Transfer findTransferByDate(final Date day);

    /**
     * Find all transfers.
     *
     * @return the list
     */
    List<Transfer> findAllTransfers();

    /**
     * Find transaction.
     *
     * @param id
     *            the id
     * @return the transaction
     */
    Transaction findTransaction(final Integer id);

    /**
     * Find transaction by account.
     *
     * @param id
     *            the id
     * @return the transaction
     */
    Transaction findTransactionByAccount(final String id);

    /**
     * Find transaction by transfer.
     *
     * @param transfer
     *            the transfer
     * @return the transaction
     */
    Transaction findTransactionByTransfer(final Transfer transfer);

    /**
     * Find all transactions.
     *
     * @return the list
     */
    List<Transaction> findAllTransactions();
}
