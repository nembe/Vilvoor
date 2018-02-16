package nl.nanda.account.dao;

import java.math.BigDecimal;
import java.util.UUID;

import nl.nanda.account.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

// TODO: Auto-generated Javadoc
/**
 * 
 * An Account Repository that uses JPA to find accounts. Spring DATA is used
 * here.
 *
 */
public interface AccountRepository extends JpaRepository<Account, Integer>,
        JpaSpecificationExecutor<Account> {

    /**
     * Find account by id.
     *
     * @param accountId
     *            the account id
     * @return the account
     */
    @Query("select u from Account u where u.entityId = :id")
    public Account findAccountById(@Param("id") Integer accountId);

    /**
     * Find by account uuid.
     *
     * @param account_uuid
     *            the account uuid
     * @return the account
     */
    @Query("select u from Account u where u.account_uuid = :uuid")
    public Account findAccountByUuid(@Param("uuid") UUID account_uuid);

    /**
     * Find account by name.
     *
     * @param name
     *            the name
     * @return the account
     */
    @Query("select u from Account u where u.name = :name")
    public Account findAccountByName(@Param("name") String name);

    /**
     * Find all by id.
     */
    @Query("select o from Account o")
    public void findAllById();

    /**
     * @param saldo
     * @param uuid
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account o SET o.balance = :saldo where o.account_uuid = :uuid")
    public void updateAccountBalance(@Param("saldo") BigDecimal saldo,
            @Param("uuid") UUID uuid);

}
