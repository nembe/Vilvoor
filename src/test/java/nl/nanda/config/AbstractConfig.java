package nl.nanda.config;

import javax.persistence.EntityManager;

import nl.nanda.account.dao.AccountRepository;
import nl.nanda.service.TransferService;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.dao.TransferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = { AccountsConfig.class })
public class AbstractConfig {

    @Autowired
    public EntityManager manager;

    /**
     * The object being tested.
     */
    @Autowired
    public AccountRepository accountRepo;

    @Autowired
    public TransferRepository transferRepo;

    @Autowired
    public TransactionRepository transactionRepo;

    @Autowired
    public TransferService transferService;

}
