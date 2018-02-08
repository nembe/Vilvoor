package nl.nanda.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.exception.SvaNotFoundException;
import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;
import nl.nanda.transfer.dao.TransferRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 *
 */
@Service
public class TransferServiceImpl implements TransferService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    public AccountRepository accountRepo;

    @Autowired
    public TransferRepository transferRepo;

    @Autowired
    public TransactionRepository transactionRepo;

    @Override
    public UUID createAccount(final String balance,
            final String roodToegestaan, final String accountUser) {
        final Account account = new Account(BigDecimal.valueOf(Long
                .parseLong(balance)), BigDecimal.valueOf(Long
                .parseLong(roodToegestaan)), accountUser);
        return accountRepo.saveAndFlush(account).getAccount_uuid();
    }

    @Override
    public Account saveAccount(final Account account) {
        return accountRepo.save(account);
    }

    @Override
    public Account findAccountByName(final String name) {

        final Account account = accountRepo.findAccountByName(name);
        if (account == null) {
            throw new SvaNotFoundException("Account Not found");
        }

        return account;
    }

    @Override
    public Account getAccount(final UUID id) {
        final Account account = accountRepo.findByAccount_uuid(id);
        if (account == null) {
            throw new SvaNotFoundException("Account Not found");
        }
        return account;
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public Integer doTransfer(final String from, final String to,
            final double amount) {

        final Account fromAccount = accountRepo.findByAccount_uuid(UUID
                .fromString(from));
        final Account toAccount = accountRepo.findByAccount_uuid(UUID
                .fromString(to));
        logger.info("Starting Transaction for Account = "
                + fromAccount.getName() + " With amount "
                + fromAccount.getAmount());

        final Transfer transfer = new Transfer(BigDecimal.valueOf(amount));
        transfer.startTransfer(fromAccount, toAccount);
        accountRepo.saveAndFlush(fromAccount);
        accountRepo.saveAndFlush(toAccount);
        final Integer transferId = transferRepo.save(transfer).getEntityId();
        final Transaction transaction = new Transaction(
                fromAccount.getAccount_uuid(), transfer);
        transactionRepo.saveAndFlush(transaction);
        return transferId;
    }

    @Override
    public Integer saveTransfer(final Transfer transfer) {

        return transferRepo.saveAndFlush(transfer).getEntityId();
    }

    @Override
    public Transfer findTransferById(final Integer id) {
        final Transfer transfer = transferRepo.findByEntityId(id);
        if (transfer == null) {
            throw new SvaNotFoundException("Transfer Not found");
        }
        return transfer;
    }

    @Override
    public Transfer findTransferByDate(final Date day) {

        final Transfer transfer = transferRepo.findByDay(day);
        if (transfer == null) {
            throw new SvaNotFoundException("Transfer Not found");
        }
        return transfer;
    }

    @Override
    public List<Transfer> findAllTransfers() {
        return transferRepo.findAll();
    }

    @Override
    public Transaction findTransaction(final Integer id) {

        return transactionRepo.findByEntityId(id);
    }

    @Override
    public Transaction findTransactionByAccount(final UUID id) {
        return transactionRepo.findByAccount(id);
    }

    @Override
    public Transaction findTransactionByTransfer(final Transfer transfer) {
        return transactionRepo.findByTransfer(transfer);
    }

    @Override
    public List<Transaction> findAllTransactions() {

        return transactionRepo.findAll();
    }

}
