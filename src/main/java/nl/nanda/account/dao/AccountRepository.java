package nl.nanda.account.dao;

import nl.nanda.account.Account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    // @PersistenceContext
    // public EntityManager entityManager = null;

    @Query("select u from Account u where u.entityId = :id")
    public Account findAccountById(@Param("id") Integer accountId);

    @Override
    public <S extends Account> S saveAndFlush(S arg0);

}
