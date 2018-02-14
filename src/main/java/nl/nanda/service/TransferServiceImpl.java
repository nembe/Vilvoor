package nl.nanda.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.exception.AnanieException;
import nl.nanda.exception.AnanieNotFoundException;
import nl.nanda.status.Status;
import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;
import nl.nanda.transfer.dao.TransferRepository;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#createAccount(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    /*
     * @see nl.nanda.service.TransferService#createAccount(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    public String createAccount(final String balance,
            final String roodToegestaan, final String accountUser) {
        final Account savedAccount = savingAccount(balance, roodToegestaan,
                accountUser);
        return savedAccount.getAccountUUID().toString();
    }

    /**
     * Converting the String from the upper layer to the approiated Types. After
     * 
     * @param balance
     * @param roodToegestaan
     * @param accountUser
     * @return
     */
    private Account savingAccount(final String balance,
            final String roodToegestaan, final String accountUser) {
        final Account account = new Account(BigDecimal.valueOf(Long
                .parseLong(balance)), BigDecimal.valueOf(Long
                .parseLong(roodToegestaan)), accountUser);
        final Account savedAccount = accountRepo.save(account);
        return savedAccount;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.nanda.service.TransferService#saveAccount(nl.nanda.account.Account)
     */
    /*
     * @see
     * nl.nanda.service.TransferService#saveAccount(nl.nanda.account.Account)
     */
    @Override
    public Account saveAccount(final Account account) {
        return accountRepo.save(account);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#findAccountByName(java.lang.String)
     */
    /*
     * @see nl.nanda.service.TransferService#findAccountByName(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public Account findAccountByName(final String name) {
        return accountRepo.findAccountByName(name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#getAccount(java.lang.String)
     */
    /*
     * @see nl.nanda.service.TransferService#getAccount(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true)
    public Account getAccount(final String id) {
        final Account account = findAccount(id);
        return account;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#findAllAccounts()
     */
    /*
     * @see nl.nanda.service.TransferService#findAllAccounts()
     */
    @Override
    @Transactional(readOnly = true)
    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#doTransfer(java.lang.String,
     * java.lang.String, double)
     */
    /*
     * @see nl.nanda.service.TransferService#doTransfer(java.lang.String,
     * java.lang.String, double)
     */
    @Override
    public Integer doTransfer(final String from, final String to,
            final double amount) {

        final Account fromAccount = findAccount(from);
        final Account toAccount = findAccount(to);

        final Integer transferId = createAndReturnTransfer(fromAccount,
                toAccount, amount);
        return transferId;
    }

    /**
     * Finding the account to complete Transaction.
     * 
     * @param from
     * @return
     */
    private Account findAccount(final String from) {
        final Account account = accountRepo.findByAccount_uuid(UUID
                .fromString(from));
        if (account == null) {
            throw new AnanieNotFoundException("Account Not found");
        }
        return account;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.nanda.service.TransferService#doTransfer(nl.nanda.transfer.Transfer)
     */
    @Override
    public Integer doTransfer(final Transfer transfer) {

        return doTransfer(transfer.getCredit().toString(), transfer.getDebet()
                .toString(), transfer.getTotaal());
    }

    /**
     * Returning the Transfer ID. We can trace the transfer state. If the
     * transfer is succes full the state of the transfer will be "CONFIRMED".
     * With the Transfer ID we can find the Transaction.
     * 
     * @param fromAccount
     * @param toAccount
     * @param amount
     * @return
     */
    private Integer createAndReturnTransfer(final Account fromAccount,
            final Account toAccount, final double amount) {
        Integer transferId = null;
        final Transfer transfer = new Transfer(fromAccount, toAccount);
        try {
            transfer.startTransfer(BigDecimal.valueOf(amount));
            accountRepo.save(fromAccount);
            accountRepo.save(toAccount);
            transferId = transferRepo.save(transfer).getEntityId();
            final Transaction transaction = new Transaction(
                    fromAccount.getAccountUUID(), transfer);
            transactionRepo.save(transaction);
        } catch (final AnanieException e) {
            transfer.setState(Status.INSUFFICIENT_FUNDS);
            transferId = transferRepo.save(transfer).getEntityId();
            logger.debug("AnanieException ", e);
        }
        return transferId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.nanda.service.TransferService#saveTransfer(nl.nanda.transfer.Transfer)
     */
    /*
     * @see
     * nl.nanda.service.TransferService#saveTransfer(nl.nanda.transfer.Transfer)
     */
    @Override
    public Integer saveTransfer(final Transfer transfer) {

        return transferRepo.save(transfer).getEntityId();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#findTransferById(java.lang.Integer)
     */
    /*
     * @see nl.nanda.service.TransferService#findTransferById(java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = true)
    public Transfer findTransferById(final Integer id) {
        final Transfer transfer = transferRepo.findByEntityId(id);
        if (transfer == null) {
            throw new AnanieNotFoundException("Transfer Not found");
        }
        return transfer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#findTransferByDate(java.sql.Date)
     */
    /*
     * @see nl.nanda.service.TransferService#findTransferByDate(java.sql.Date)
     */
    @Override
    @Transactional(readOnly = true)
    public Transfer findTransferByDate(final Date day) {

        final Transfer transfer = transferRepo.findByDay(day);
        if (transfer == null) {
            throw new AnanieNotFoundException("Transfer Not found");
        }
        return transfer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#findAllTransfers()
     */
    /*
     * @see nl.nanda.service.TransferService#findAllTransfers()
     */
    @Override
    @Transactional(readOnly = true)
    public List<Transfer> findAllTransfers() {
        return transferRepo.findAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#findTransaction(java.lang.Integer)
     */
    /*
     * @see nl.nanda.service.TransferService#findTransaction(java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = true)
    public Transaction findTransaction(final Integer id) {

        return transactionRepo.findByEntityId(id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.nanda.service.TransferService#findTransactionByAccount(java.lang.String
     * )
     */
    /*
     * @see
     * nl.nanda.service.TransferService#findTransactionByAccount(java.lang.String
     * )
     */
    @Override
    @Transactional(readOnly = true)
    public Transaction findTransactionByAccount(final String id) {
        return transactionRepo.findByAccount(UUID.fromString(id));
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.nanda.service.TransferService#findTransactionByTransfer(nl.nanda.transfer
     * .Transfer)
     */
    /*
     * @see
     * nl.nanda.service.TransferService#findTransactionByTransfer(nl.nanda.transfer
     * .Transfer)
     */
    @Override
    @Transactional(readOnly = true)
    public Transaction findTransactionByTransfer(final Transfer transfer) {
        return transactionRepo.findByTransfer(transfer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.nanda.service.TransferService#findAllTransactions()
     */
    /*
     * @see nl.nanda.service.TransferService#findAllTransactions()
     */
    @Override
    @Transactional(readOnly = true)
    public List<Transaction> findAllTransactions() {

        return transactionRepo.findAll();
    }

}
