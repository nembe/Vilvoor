package nl.nanda.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import nl.nanda.account.Account;
import nl.nanda.account.dao.AccountRepository;
import nl.nanda.transaction.Transaction;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.Transfer;
import nl.nanda.transfer.dao.TransferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferServiceImpl implements TransferService {

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
    public Account getAccount(final Long id) {
        return accountRepo.findAccountById(id);
    }

    @Override
    public List<Account> findAllAccounts() {
        return accountRepo.findAll();
    }

    @Override
    public Long doTransfer(final Long from, final Long to, final Integer amount) {
        final Account fromAccount = accountRepo.findAccountById(from);
        final Account toAccount = accountRepo.findAccountById(to);

        final Transfer transfer = new Transfer(BigDecimal.valueOf(amount));
        transfer.startTransfer(fromAccount, toAccount);
        accountRepo.saveAndFlush(fromAccount);
        accountRepo.saveAndFlush(toAccount);
        final long transferId = transferRepo.save(transfer).getEntityId();
        final Transaction transaction = new Transaction(
                fromAccount.getEntityId(), transferId);
        transaction.setEntityId(11L);
        transactionRepo.saveAndFlush(transaction);
        return transferId;
    }

    @Override
    public Transfer findTransferById(final Long id) {
        return transferRepo.findByEntityId(id);
    }

    @Override
    public Transfer findTransferByDate(final Date day) {
        return transferRepo.findByDay(day);
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

}
