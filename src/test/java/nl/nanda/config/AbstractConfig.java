package nl.nanda.config;

import nl.nanda.account.dao.AccountRepository;
import nl.nanda.service.TransferService;
import nl.nanda.transaction.dao.TransactionRepository;
import nl.nanda.transfer.dao.TransferRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * The Class AbstractConfig.
 */
@ContextConfiguration(classes = { AccountsConfig.class })
public class AbstractConfig {

    // /** The manager. */
    // @Autowired
    // public EntityManager manager;

    /**
     * The object being tested.
     */
    @Autowired
    public AccountRepository accountRepo;

    /** The transfer repo. */
    @Autowired
    public TransferRepository transferRepo;

    /** The transaction repo. */
    @Autowired
    public TransactionRepository transactionRepo;

    /** The transfer service. */
    @Autowired
    public TransferService transferService;

}
