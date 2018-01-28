package nl.nanda.service;

import java.sql.Date;
import java.util.List;

import nl.nanda.account.Account;
import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

public interface TransferService {

    Long createAccount(final Long balance, final Long roodToegestaan,
            final String accountUser);

    Long saveAccount(Account account);

    Account getAccount(final Long id);

    Account findAccountByName(final String name);

    List<Account> findAllAccounts();

    Long saveTransfer(Transfer transfer);

    Long doTransfer(final Long from, final Long to, final Integer amount);

    Transfer findTransferById(final Long id);

    Transfer findTransferByDate(final Date day);

    List<Transfer> findAllTransfers();

    Transaction findTransaction(final Long id);

    Transaction findTransactionByAccount(final Long id);

    Transaction findTransactionByTransfer(final Long id);

    List<Transaction> findAllTransactions();
}
