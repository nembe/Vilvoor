package nl.nanda.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.nanda.account.Account;
import nl.nanda.domain.CrunchifyRandomNumber;
import nl.nanda.service.adapters.AccountAdapter;
import nl.nanda.service.adapters.TransactionAdapter;
import nl.nanda.service.adapters.TransferAdapter;
import nl.nanda.service.empty.AccountNull;
import nl.nanda.service.empty.TransactionNull;
import nl.nanda.service.empty.TransferNull;
import nl.nanda.status.Status;
import nl.nanda.transaction.Transaction;
import nl.nanda.transfer.Transfer;

/**
 * The Class TransferServiceImpl (Gateway) exposing Crud methods and do some
 * validation and error handling.
 */
@Service
@Transactional
public class TransferServiceImpl implements TransferService {

    private final static String UUIDNULLSTRING = "00000000-0000-0000-0000-89323971e98a";
        
    @Autowired
    private TransactionAdapter transactionAdapter;  
   
    @Autowired
    private TransferAdapter transferAdapter;

    @Autowired
    private AccountAdapter accountAdapter;

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

        final Account savedAccount = accountAdapter.savingAccount(balance, roodToegestaan, accountUser);
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
        return accountAdapter.save(account);
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

    	accountAdapter.updateAccountBalance(saldo, uuid);
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
        return accountAdapter.findAccountByName(name);
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
        Account account = accountAdapter.findAccount(id);
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
        return accountAdapter.findAll();
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

        return transferAdapter.beginTransfer(transfer);
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
        return transferAdapter.saveTransfer(transfer);
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
        Transfer transfer = transferAdapter.findByEntityId(id);
        if (transfer == null) {
            transfer = new TransferNull();
            transfer.setCredit(UUID.fromString(UUIDNULLSTRING));
            transfer.setDebet(UUID.fromString(UUIDNULLSTRING));
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

        Transfer transfer = transferAdapter.findByDay(day);
        if (transfer == null) {
            transfer = new TransferNull();
            transfer.setCredit(UUID.fromString(UUIDNULLSTRING));
            transfer.setDebet(UUID.fromString(UUIDNULLSTRING));
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
        return transferAdapter.findAllTransfers();
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

        Transaction transaction = transactionAdapter.findByEntityId(id);
        if (transaction == null) {
            transaction = new TransactionNull();
            transaction.setEntityId(-1);
            transaction.setAccount(UUID.fromString(UUIDNULLSTRING));
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
        final List<Transaction> transactionList = transactionAdapter.findByAccount(UUID.fromString(id));
        if (transactionList == null || transactionList.isEmpty()) {
            final Transaction transact = new TransactionNull();
            transact.setEntityId(-1);
            transact.setAccount(UUID.fromString(UUIDNULLSTRING));
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
        Transaction transaction = transactionAdapter.findByTransfer(transfer);
        if (transaction == null) {
            transaction = new TransactionNull();
            transaction.setEntityId(-1);
            transaction.setAccount(UUID.fromString(UUIDNULLSTRING));
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

        return transactionAdapter.findAll();
    }

}
