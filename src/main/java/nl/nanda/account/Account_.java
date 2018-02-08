package nl.nanda.account;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Account.class)
public class Account_ {
    public static volatile SingularAttribute<Account, Integer> entityId;
}
