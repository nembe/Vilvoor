package nl.nanda.account.dao;

import java.math.BigDecimal;
import java.util.UUID;

import nl.nanda.account.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * The Account Repository that uses Spring Data JPA to find and insert accounts.
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
    Account findAccountById(@Param("id") Integer accountId);

    /**
     * Find account by uuid.
     *
     * @param account_uuid
     *            the account uuid
     * @return the account
     */
    @Query("select u from Account u where u.account_uuid = :uuid")
    Account findAccountByUuid(@Param("uuid") UUID account_uuid);

    /**
     * Find account by name.
     *
     * @param name
     *            the name
     * @return the account
     */
    @Query("select u from Account u where u.name = :name")
    Account findAccountByName(@Param("name") String name);

    /**
     * Find all Accounts by id.
     */
    @Query("select o from Account o")
    void findAllById();

    /**
     * Clients can update an Account without to have or need to have an JPA
     * manage Account entity.
     * 
     * @param saldo
     * @param uuid
     */
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account o SET o.balance = :saldo where o.account_uuid = :uuid")
    void updateAccountBalance(@Param("saldo") BigDecimal saldo,
            @Param("uuid") UUID uuid);

}
