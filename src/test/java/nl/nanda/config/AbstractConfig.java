package nl.nanda.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import nl.nanda.jpa.account.dao.AccountRepository;
import nl.nanda.jpa.transaction.dao.TransactionRepository;
import nl.nanda.jpa.transfer.dao.TransferRepository;
import nl.nanda.service.TransferService;

/**
 * The Class AbstractConfig.
 */
@ContextConfiguration(classes = { AccountsConfig.class })
// @ActiveProfiles("test")
public class AbstractConfig {

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
