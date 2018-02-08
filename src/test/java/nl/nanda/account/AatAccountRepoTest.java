package nl.nanda.account;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import nl.nanda.config.AbstractConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * A system test that verifies the Account component and the AccountRepository
 * of the AAT application. Uses Spring to bootstrap the application for use in a
 * test environment.
 */

@RunWith(SpringJUnit4ClassRunner.class)
public class AatAccountRepoTest extends AbstractConfig {

    @Test
    public void testFindAccountByName() {

        final Account account = AccountFactory.createAccounts(1000.50, 0.0,
                "Jorka");
        accountRepo.save(account);
        final Account accountFound = accountRepo.findAccountByName(account
                .getName());
        assertEquals(Integer.valueOf(2), accountFound.getEntityId());
        assertEquals("Jorka", accountFound.getName());

    }

    /**
     * Finding a Account by its UUID.
     */
    @Test
    public void testFindAccountByUUID() {

        final Account account = AccountFactory.createAccounts(1000.50, 20.0,
                "Coemba");
        final Account accountSave = accountRepo.save(account);

        final Account accountFound = accountRepo.findByAccount_uuid(accountSave
                .getAccount_uuid());
        assertEquals(Integer.valueOf(3), accountFound.getEntityId());
        assertEquals("Coemba", accountFound.getName());

    }

    /**
     * Finding accounts by there Primary Keys.
     */
    @Test
    public void testFindAccount() {
        final Account account = accountRepo.findAccountById(0);
        assertEquals("Jasper", account.getName());
    }

    @Test
    public void testFindAllAccounts() {

        // final Specification<Account> acc = new Specification<Account>() {
        //
        // @Override
        // public Predicate toPredicate(final Root<Account> root,
        // final CriteriaQuery<?> query, final CriteriaBuilder cb) {
        //
        // return cb.equal(root.get(Account_.entityId), 0);
        // }
        // };

        final List<Account> entities = accountRepo.findAll();

        final Account account = entities.get(0);
        assertEquals("Jasper", account.getName());
        assertEquals(Integer.valueOf(0), account.getEntityId());

    }

    /**
     * The amount is what the account limit is.
     * 
     */
    @Test
    public void testAccountAmount() {

        final Account account = AccountFactory.createAccounts(1020.50, 0.00,
                "Jorka2");

        assertEquals(BigDecimal.valueOf(1020.50), account.getAmount());

    }

    /**
     * The balance is what the account contains.
     */
    @Test
    public void testAccountBalance() {

        final Account account = AccountFactory.createAccounts(1000.50, 20.00,
                "Jorka3");

        assertEquals(BigDecimal.valueOf(1000.50), account.getBalance());

    }

}