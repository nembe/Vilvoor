package nl.nanda.account;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Creates accounts Objects for testing.
 *
 */
public class AccountFactory {

    /**
     * If there is no given Overdraft on this account, the account is created
     * without overdraft (limit under 0).
     *
     * @param balance
     *            the balance
     * @param overdraft
     *            the overdraft
     * @param name
     *            the name
     * @return the account
     */
    static public Account createAccounts(final double balance,
            final double overdraft, final String name) {

        final UUID accountUUID = UUID.randomUUID();
        Account account = null;
        if (overdraft == 0) {
            account = new Account(BigDecimal.valueOf(balance), name);
        } else {
            account = new Account(BigDecimal.valueOf(balance),
                    BigDecimal.valueOf(overdraft), name);
        }
        account.setAccountUUID(accountUUID);
        return account;
    }

}
