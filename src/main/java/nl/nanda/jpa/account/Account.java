package nl.nanda.jpa.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.ColumnDefault;

import nl.nanda.exception.AnanieException;

/**
 * An account for a member of the reward network. An account has one or more
 * beneficiaries whose allocations must add up to 100%.
 * 
 * An account can make contributions to its beneficiaries. Each contribution is
 * distributed among the beneficiaries based on an allocation.
 * 
 * An entity. An aggregate.
 * 
 *
 */
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "T_ACCOUNT")
public class Account implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3642534679048425984L;

	/** The entity id. */
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Integer entityId;

	/** The account uuid. */
	@Id
	@Column(name = "ACCOUNT_UUID")
	@NotNull
	private UUID account_uuid;

	/** The overdraft. */
	@Column(name = "OVERDRAFT")
	@Min(value = 0)
	private BigDecimal overdraft;

	/** The balance. */
	@Column(name = "BALANCE")
	@Min(value = 0)
	private BigDecimal balance;

	/** The name. */
	@Column(name = "NAME")
	@NotNull
	private String name;

	@Version
	@ColumnDefault("0")
	@Column(name = "VERSION")
	private Integer version;

	/**
	 * Instantiates a new account.
	 */
	@SuppressWarnings("unused")
	private Account() {
	}

	/**
	 * Instantiates a new account, only the account name is mandatory.
	 *
	 * @param balance
	 *            the balance
	 * @param overdraft
	 *            the overdraft
	 * @param name
	 *            the name
	 */
	public Account(final BigDecimal balance, final BigDecimal overdraft, final String name) {
		this.balance = balance;
		this.overdraft = overdraft;
		this.name = name;
	}

	/**
	 * Instantiates a new account, only the account name is mandatory..
	 *
	 * @param balance
	 *            the balance
	 * @param name
	 *            the name
	 */
	public Account(final BigDecimal balance, final String name) {
		this.balance = balance;
		this.name = name;
		this.overdraft = BigDecimal.valueOf(0);
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param entityId
	 *            the new entity id
	 */
	public void setEntityId(final Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the overdraft.
	 *
	 * @return the overdraft
	 */
	public BigDecimal getOverdraft() {
		return overdraft;
	}

	/**
	 * Sets the overdraft.
	 *
	 * @param overdraft
	 *            the new overdraft
	 * @throws AnanieException
	 *             the sva exception
	 */
	public void setOverdraft(final BigDecimal overdraft) throws AnanieException {
		this.overdraft = overdraft;
	}

	/**
	 * Gets the balance.
	 *
	 * @return the balance
	 */
	public BigDecimal getBalance() {
		return balance;
	}

	/**
	 * Sets the balance.
	 *
	 * @param balance
	 *            the new balance
	 */
	public void setBalance(final BigDecimal balance) {
		this.balance = balance;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the amount this account can goes underwater.
	 *
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return this.balance.add(overdraft);
	}

	/**
	 * Gets the account UUID.
	 *
	 * @return the account UUID
	 */
	public UUID getAccountUUID() {
		return account_uuid;
	}

	/**
	 * Sets the account UUID.
	 *
	 * @param account_uuid
	 *            the new account UUID
	 */
	public void setAccountUUID(final UUID account_uuid) {
		this.account_uuid = account_uuid;
	}

}
