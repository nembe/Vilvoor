package nl.nanda.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

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

@Service
public class TransferServiceImpl implements TransferService {

    private final Logger logger = Logger.getLogger(getClass());

    /**
     * The object being tested.
     */
    @Autowired
    public AccountRepository accountRepo;

    @Autowired
    public TransferRepository transferRepo;

    @Autowired
    public TransactionRepository transactionRepo;

    @Override
    public Long createAccount(final Long balance, final Long roodToegestaan,
            final String accountUser) {
        final Account account = new Account(BigDecimal.valueOf(balance),
                BigDecimal.valueOf(roodToegestaan), accountUser);
        return accountRepo.saveAndFlush(account).getEntityId();
    }

    @Override
    public Long saveAccount(final Account account) {

        return accountRepo.saveAndFlush(account).getEntityId();
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
    public Account getAccount(final Long id) {
        final Account account = accountRepo.findAccountById(id);
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
    public Long doTransfer(final Long from, final Long to, final Integer amount) {

        final Account fromAccount = accountRepo.findAccountById(from);
        final Account toAccount = accountRepo.findAccountById(to);
        logger.info("Starting Transaction for Account = "
                + fromAccount.getName() + " With amount "
                + fromAccount.getAmount());

        final Transfer transfer = new Transfer(BigDecimal.valueOf(amount));
        transfer.startTransfer(fromAccount, toAccount);
        accountRepo.saveAndFlush(fromAccount);
        accountRepo.saveAndFlush(toAccount);
        final long transferId = transferRepo.save(transfer).getEntityId();
        final Transaction transaction = new Transaction(
                fromAccount.getEntityId(), transferId);
        transactionRepo.saveAndFlush(transaction);
        return transferId;
    }

    @Override
    public Long saveTransfer(final Transfer transfer) {

        return transferRepo.saveAndFlush(transfer).getEntityId();
    }

    @Override
    public Transfer findTransferById(final Long id) {
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
    public Transaction findTransaction(final Long id) {

        return transactionRepo.findByEntityId(id);
    }

    @Override
    public Transaction findTransactionByAccount(final Long id) {
        return transactionRepo.findByAccount(id);
    }

    @Override
    public Transaction findTransactionByTransfer(final Long id) {
        return transactionRepo.findByTransfer(id);
    }

    @Override
    public List<Transaction> findAllTransactions() {

        return transactionRepo.findAll();
    }

}
