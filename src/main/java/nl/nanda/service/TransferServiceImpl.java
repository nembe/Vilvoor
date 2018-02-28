package nl.nanda.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.exception.AnanieException;
import nl.nanda.exception.AnanieNotFoundException;
import nl.nanda.service.empty.AccountNull;
import nl.nanda.service.empty.TransactionNull;
import nl.nanda.service.empty.TransferNull;
import nl.nanda.status.Status;
import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;
import nl.nanda.transfer.dao.TransferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The Class TransferServiceImpl (Gateway) exposing Crud methods and do some
 * validation and error handling.
 */
@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final String uuidNullString = "00000000-0000-0000-0000-89323971e98a";
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
     * Converting the String from the upper layer to the destiny Types.
     * 
     * @param balance
     * @param roodToegestaan
     * @param accountUser
     * @return
     */
    private Account savingAccount(final String balance,
            final String roodToegestaan, final String accountUser) {
        final Account account = validateAndCreateAccount(balance,
                roodToegestaan, accountUser);
        final Account savedAccount = accountRepo.save(account);
        return savedAccount;
    }

    /**
     * Validating the Input before creating the Account.
     * 
     * @param balance
     * @param roodToegestaan
     * @param accountUser
     * @return Account entity object.
     */
    private Account validateAndCreateAccount(final String balance,
            final String roodToegestaan, final String accountUser) {
        Account account = null;
        try {
            account = new Account(BigDecimal.valueOf(Double
                    .parseDouble(balance)), BigDecimal.valueOf(Double
                    .parseDouble(roodToegestaan)), accountUser);
        } catch (final NumberFormatException e) {
            throw new AnanieException("Saving account problem ", e);
        }
        return account;
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
     * @see nl.nanda.service.TransferService#updateAccountBalance(double,
     * java.lang.String)
     */
    // TODO: What if the account can't be found.
    @Override
    public void updateAccountBalance(final double saldo, final String uuid) {

        accountRepo.updateAccountBalance(BigDecimal.valueOf(saldo),
                UUID.fromString(uuid));
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
        Account account = findAccount(id);
        if (account == null) {
            account = new AccountNull(BigDecimal.valueOf(0),
                    "Account not available");
        }
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

        final Integer transferId = returnTransfer(from, to, amount);
        return transferId;
    }

    /**
     * Finding the account to complete Transaction.
     * 
     * @param searchAccount
     * @return
     */
    private Account findAccount(final String searchAccount) {
        final Account account = accountRepo.findAccountByUuid(UUID
                .fromString(searchAccount));
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
     * Returning the Transfer ID so that We can trace the transfer state. If the
     * transfer is successful the state of the transfer will be "CONFIRMED".
     * With the Transfer ID we can find the "confirmed" Transaction.
     * 
     * @param fromAccount
     * @param toAccount
     * @param amount
     * @return the transfer primary key.
     */
    private Integer returnTransfer(final String from, final String to,
            final double amount) {

        final Transfer transfer = checkAccountAndReturnTransfer(from, to);
        transfer.startTransfer(BigDecimal.valueOf(amount));
        final Integer transferId = transferRepo.save(transfer).getEntityId();

        if ("CONFIRMED".equals(transfer.getState())) {
            saveAccontsToCommitTransfer(transfer);
            createTheTransaction(transfer);
        }
        return transferId;
    }

    /**
     * Here we are making the transfer official. Creating the Transaction that
     * took place in the Ananie Application.
     * 
     * 
     * @param transfer
     * @return
     */
    private void createTheTransaction(final Transfer transfer) {

        final Transaction transaction = new Transaction(transfer.getZender()
                .getAccountUUID(), transfer);
        transactionRepo.save(transaction);

    }

    /**
     * We update the account table with the new balance.
     * 
     * @param transfer
     */
    private void saveAccontsToCommitTransfer(final Transfer transfer) {
        accountRepo.save(transfer.getZender());
        accountRepo.save(transfer.getOntvanger());
    }

    /**
     * Creating a Transfer with state "Pending". If one of the accounts is not
     * found we throw a exception (AnanieNotFoundException). Because the
     * transfer can't continue without a valid account (see DB constraints).
     * 
     * @param from
     * @param to
     * @param amount
     * @return
     */
    private Transfer checkAccountAndReturnTransfer(final String from,
            final String to) {

        final Account accountFrom = findAccount(from);
        final Account accountTo = findAccount(to);

        final Transfer transfer = new Transfer();
        if (accountFrom == null || accountTo == null) {
            transfer.setCredit(UUID.fromString(from));
            transfer.setDebet(UUID.fromString(to));
            transfer.setState(Status.ACCOUNT_NOT_FOUND);
            transferRepo.save(transfer);
            throw new AnanieNotFoundException("Account Not found ");
        }
        transfer.setZender(accountFrom);
        transfer.setOntvanger(accountTo);
        return transfer;
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
        Transfer transfer = transferRepo.findByEntityId(id);
        if (transfer == null) {
            transfer = new TransferNull();
            transfer.setCredit(UUID.fromString(uuidNullString));
            transfer.setDebet(UUID.fromString(uuidNullString));
            transfer.setState(Status.NOT_AVAILABLE);
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

        Transfer transfer = transferRepo.findByDay(day);
        if (transfer == null) {
            transfer = new TransferNull();
            transfer.setCredit(UUID.fromString(uuidNullString));
            transfer.setDebet(UUID.fromString(uuidNullString));
            transfer.setState(Status.NOT_AVAILABLE);
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

        Transaction transaction = transactionRepo.findByEntityId(id);
        if (transaction == null) {
            transaction = new TransactionNull();
            transaction.setEntityId(-1);
            transaction.setAccount(UUID.fromString(uuidNullString));
        }
        return transaction;
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
        Transaction transaction = transactionRepo.findByAccount(UUID
                .fromString(id));
        if (transaction == null) {
            transaction = new TransactionNull();
            transaction.setEntityId(-1);
            transaction.setAccount(UUID.fromString(uuidNullString));
        }
        return transaction;
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
        Transaction transaction = transactionRepo.findByTransfer(transfer);
        if (transaction == null) {
            transaction = new TransactionNull();
            transaction.setEntityId(-1);
            transaction.setAccount(UUID.fromString(uuidNullString));
        }

        return transaction;
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
