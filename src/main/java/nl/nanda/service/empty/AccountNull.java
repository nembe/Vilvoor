package nl.nanda.service.empty;

import java.math.BigDecimal;

import nl.nanda.jpa.account.Account;

/**
 * We promise the client to return a Account, so instead of returning nothing we
 * return this.
 * 
 *
 */
public class AccountNull extends Account {

    /**
     * 
     */
    private static final long serialVersionUID = 206329517857601376L;

    public AccountNull(final BigDecimal balance, final String name) {
        super(balance, name);
    }

}
