package nl.nanda.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.domain.AccountFacilitator;
import nl.nanda.domain.CrunchifyRandomNumber;
import nl.nanda.domain.TransferFacilitator;
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
    private AccountRepository accountRepo;

    /** The transfer repo. */
    @Autowired
    private TransferRepository transferRepo;

    /** The transaction repo. */
    @Autowired
    private TransactionRepository transactionRepo;

    @Autowired
    private TransferFacilitator transferFacilitator;

    @Autowired
    private AccountFacilitator accountFacilitator;

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
    public String createAccount(final String balance, final String roodToegestaan, final String accountUser) {

        final Account savedAccount = accountFacilitator.savingAccount(balance, roodToegestaan, accountUser);
        return savedAccount.getAccountUUID().toString();
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

        accountRepo.updateAccountBalance(BigDecimal.valueOf(saldo), UUID.fromString(uuid));
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
        Account account = accountFacilitator.findAccount(id);
        if (account == null) {
            account = new AccountNull(BigDecimal.valueOf(0), "Account not available");
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
    public Integer doTransfer(final String from, final String to, final double amount) {

        final Transfer transfer = new Transfer(UUID.fromString(from), UUID.fromString(to), amount);
        transfer.setEntityId(CrunchifyRandomNumber.generateRandomNumber());
        return doTransfer(transfer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * nl.nanda.service.TransferService#doTransfer(nl.nanda.transfer.Transfer)
     */
    @Override
    public Integer doTransfer(final Transfer transfer) {

        return transferFacilitator.beginTransfer(transfer);
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
    public List<Transaction> findTransactionByAccount(final String id) {
        final List<Transaction> transactionList = transactionRepo.findByAccount(UUID.fromString(id));
        if (transactionList == null || transactionList.isEmpty()) {
            final Transaction transact = new TransactionNull();
            transact.setEntityId(-1);
            transact.setAccount(UUID.fromString(uuidNullString));
            transactionList.add(transact);
        }
        return transactionList;
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
