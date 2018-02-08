package nl.nanda.service;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

import nl.nanda.account.Account;
import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

public interface TransferService {

    UUID createAccount(final String balance, final String roodToegestaan,
            final String accountUser);

    Account saveAccount(Account account);

    Account getAccount(final UUID id);

    Account findAccountByName(final String name);

    List<Account> findAllAccounts();

    Integer saveTransfer(Transfer transfer);

    Integer doTransfer(final String from, final String to, final double amount);

    Transfer findTransferById(final Integer id);

    Transfer findTransferByDate(final Date day);

    List<Transfer> findAllTransfers();

    Transaction findTransaction(final Integer id);

    Transaction findTransactionByAccount(final UUID id);

    Transaction findTransactionByTransfer(final Transfer transfer);

    List<Transaction> findAllTransactions();
}
