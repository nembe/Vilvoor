package nl.nanda.service.empty;

import nl.nanda.transaction.Transaction;

/**
 * We promise the client to return a Transaction, so instead of returning
 * nothing we return this.
 */
public class TransactionNull extends Transaction {

}
