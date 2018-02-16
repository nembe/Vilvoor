package nl.nanda.service;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import nl.nanda.account.Account;
import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;

/**
 * The CRUD operations for AAT application or done true the TransferService. Who
 * is responsible for delegating requests to the Repositories. We try to
 * encapsulate DataTypes and back-end operations for the UI layered. These 3
 * Entity Objects Account, Transfer and Transaction are the only one that can be
 * used in the UI layer. It is not possible to use the Status, Amount, and the
 * Repositories outside the Service Layer. The only two exceptions that the
 * client can handle from these operations are ConstraintViolationException,
 * AnanieException(parse error) and AnanieNotFoundException
 *
 */
@Validated
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
    @NotNull
    String createAccount(@NotEmpty final String balance,
            @NotEmpty final String roodToegestaan,
            @NotEmpty final String accountUser);

    /**
     * Save account that is new or modified.
     *
     * @param account
     *            the account
     * @return the manage account.
     */
    @NotNull
    Account saveAccount(@Valid final Account account);

    /**
     * Updating account balance without forcing the client to search for the
     * Account.
     * 
     * @param saldo
     * @param uuid
     */
    void updateAccountBalance(@Min(value = 0) double saldo,
            @NotEmpty String uuid);

    /**
     * Retrieve the account.
     *
     * @param id
     *            the id (UUID) of the account
     * @return the account
     */
    @NotNull
    Account getAccount(@NotEmpty final String id);

    /**
     * Find an account by name.
     *
     * @param name
     *            the name that belongs to the account.
     * @return the account
     */
    @NotNull
    Account findAccountByName(@NotEmpty final String name);

    /**
     * Find all accounts.
     *
     * @return the list with accounts.
     */
    @NotNull
    List<Account> findAllAccounts();

    /**
     * Save the transfer.
     *
     * @param transfer
     *            the transfer entity object.
     * @return the integer (primary key of the transfer).
     */
    @NotNull
    Integer saveTransfer(@Valid final Transfer transfer);

    /**
     * Do transfer.
     *
     * @param from
     *            the from
     * @param to
     *            the to
     * @param amount
     *            the amount
     * @return the integer (primary key of the transfer).
     */
    @NotNull
    Integer doTransfer(@NotEmpty final String from, @NotEmpty final String to,
            @Min(value = 0) final double amount);

    /**
     * Try to use the overloaded method with String parameters in the UI layer.
     * This is more for testing, because it creates a dependency to the Transfer
     * object.
     * 
     * @param transfer
     * @return (primary key of the transfer).
     */
    @NotNull
    Integer doTransfer(@Valid Transfer transfer);

    /**
     * Find transfer by id.
     *
     * @param id
     *            the id
     * @return the transfer
     */
    @NotNull
    Transfer findTransferById(@NotNull final Integer id);

    /**
     * Find transfer by date.
     *
     * @param day
     *            the day
     * @return the transfer
     */
    @NotNull
    Transfer findTransferByDate(@NotNull final Date day);

    /**
     * Find all transfers.
     *
     * @return the list
     */
    @NotNull
    List<Transfer> findAllTransfers();

    /**
     * Find transaction.
     *
     * @param id
     *            the id
     * @return the transaction
     */
    @NotNull
    Transaction findTransaction(@NotNull final Integer id);

    /**
     * Find transaction by account.
     *
     * @param id
     *            the id
     * @return the transaction
     */
    @NotNull
    Transaction findTransactionByAccount(@NotEmpty final String id);

    /**
     * Find transaction by transfer.
     *
     * @param transfer
     *            the transfer
     * @return the transaction
     */
    @NotNull
    Transaction findTransactionByTransfer(@Valid final Transfer transfer);

    /**
     * Find all transactions.
     *
     * @return the list with transactions.
     */
    @NotNull
    List<Transaction> findAllTransactions();
}
