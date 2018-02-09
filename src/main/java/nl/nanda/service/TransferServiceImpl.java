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
import org.springframework.transaction.annotation.Transactional;

// TODO: Auto-generated Javadoc
/**
 * The Class TransferServiceImpl.
 */
@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    /** The logger. */
    private final Logger logger = Logger.getLogger(getClass());

    /** The account repo. */
    @Autowired
    public AccountRepository accountRepo;

    /** The transfer repo. */
    @Autowired
    public TransferRepository transferRepo;

    /** The transaction repo. */
    @Autowired
    public TransactionRepository transactionRepo;

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#createAccount(java.lang.String, java.lang.String, java.lang.String)
     */
    /*
     * @see nl.nanda.service.TransferService#createAccount(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public String createAccount(final String balance,
            final String roodToegestaan, final String accountUser) {
        final Account account = new Account(BigDecimal.valueOf(Long
                .parseLong(balance)), BigDecimal.valueOf(Long
                .parseLong(roodToegestaan)), accountUser);
        final Account savedAccount = accountRepo.save(account);
        return savedAccount.getAccountUUID().toString();
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#saveAccount(nl.nanda.account.Account)
     */
    /*
     * @see
     * nl.nanda.service.TransferService#saveAccount(nl.nanda.account.Account)
     */
    @Override
    public Account saveAccount(final Account account) {
        return accountRepo.save(account);
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findAccountByName(java.lang.String)
     */
    /*
     * @see nl.nanda.service.TransferService#findAccountByName(java.lang.String)
     */
    @Override
    public Account findAccountByName(final String name) {

        final Account account = accountRepo.findAccountByName(name);
        if (account == null) {
            throw new SvaNotFoundException("Account Not found");
        }

        return account;
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#getAccount(java.lang.String)
     */
    /*
     * @see nl.nanda.service.TransferService#getAccount(java.lang.String)
     */
    @Override
    public Account getAccount(final String id) {
        final Account account = accountRepo.findByAccount_uuid(UUID
                .fromString(id));
        if (account == null) {
            throw new SvaNotFoundException("Account Not found");
        }
        return account;
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findAllAccounts()
     */
    /*
     * @see nl.nanda.service.TransferService#findAllAccounts()
     */
    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#doTransfer(java.lang.String, java.lang.String, double)
     */
    /*
     * @see nl.nanda.service.TransferService#doTransfer(java.lang.String,
     * java.lang.String, double)
     */
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
                fromAccount.getAccountUUID(), transfer);
        transactionRepo.saveAndFlush(transaction);
        return transferId;
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#saveTransfer(nl.nanda.transfer.Transfer)
     */
    /*
     * @see
     * nl.nanda.service.TransferService#saveTransfer(nl.nanda.transfer.Transfer)
     */
    @Override
    public Integer saveTransfer(final Transfer transfer) {

        return transferRepo.saveAndFlush(transfer).getEntityId();
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findTransferById(java.lang.Integer)
     */
    /*
     * @see nl.nanda.service.TransferService#findTransferById(java.lang.Integer)
     */
    @Override
    public Transfer findTransferById(final Integer id) {
        final Transfer transfer = transferRepo.findByEntityId(id);
        if (transfer == null) {
            throw new SvaNotFoundException("Transfer Not found");
        }
        return transfer;
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findTransferByDate(java.sql.Date)
     */
    /*
     * @see nl.nanda.service.TransferService#findTransferByDate(java.sql.Date)
     */
    @Override
    public Transfer findTransferByDate(final Date day) {

        final Transfer transfer = transferRepo.findByDay(day);
        if (transfer == null) {
            throw new SvaNotFoundException("Transfer Not found");
        }
        return transfer;
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findAllTransfers()
     */
    /*
     * @see nl.nanda.service.TransferService#findAllTransfers()
     */
    @Override
    public List<Transfer> findAllTransfers() {
        return transferRepo.findAll();
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findTransaction(java.lang.Integer)
     */
    /*
     * @see nl.nanda.service.TransferService#findTransaction(java.lang.Integer)
     */
    @Override
    public Transaction findTransaction(final Integer id) {

        return transactionRepo.findByEntityId(id);
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findTransactionByAccount(java.lang.String)
     */
    /*
     * @see
     * nl.nanda.service.TransferService#findTransactionByAccount(java.lang.String
     * )
     */
    @Override
    public Transaction findTransactionByAccount(final String id) {
        return transactionRepo.findByAccount(UUID.fromString(id));
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findTransactionByTransfer(nl.nanda.transfer.Transfer)
     */
    /*
     * @see
     * nl.nanda.service.TransferService#findTransactionByTransfer(nl.nanda.transfer
     * .Transfer)
     */
    @Override
    public Transaction findTransactionByTransfer(final Transfer transfer) {
        return transactionRepo.findByTransfer(transfer);
    }

    /* (non-Javadoc)
     * @see nl.nanda.service.TransferService#findAllTransactions()
     */
    /*
     * @see nl.nanda.service.TransferService#findAllTransactions()
     */
    @Override
    public List<Transaction> findAllTransactions() {

        return transactionRepo.findAll();
    }

}
