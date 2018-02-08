package nl.nanda.account.dao;

import java.util.UUID;

import nl.nanda.account.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * 
 * An Account Repository that uses JPA to find accounts. Spring DATA is used
 * here.
 *
 */
public interface AccountRepository extends JpaRepository<Account, Integer>,
        JpaSpecificationExecutor<Account> {

    @Query("select u from Account u where u.entityId = :id")
    public Account findAccountById(@Param("id") Integer accountId);

    @Query("select u from Account u where u.account_uuid = :uuid")
    public Account findByAccount_uuid(@Param("uuid") UUID account_uuid);

    @Query("select u from Account u where u.name = :name")
    public Account findAccountByName(@Param("name") String name);

    // @Query("select o from Account o where o.entityId != null")
    @Query("select o from Account o")
    void findAllById();

}
