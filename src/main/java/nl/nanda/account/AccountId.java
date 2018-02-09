package nl.nanda.account;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

// TODO: Auto-generated Javadoc
/**
 * The Class AccountId.
 */
public class AccountId implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2793983717566300654L;
    
    /** The entity id. */
    private Integer entityId;
    
    /** The account uuid. */
    private UUID account_uuid;

    /**
     * Instantiates a new account id.
     */
    public AccountId() {

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
     * @param entityId the new entity id
     */
    public void setEntityId(final Integer entityId) {
        this.entityId = entityId;
    }

    /**
     * Gets the account uuid.
     *
     * @return the account uuid
     */
    public UUID getAccount_uuid() {
        return account_uuid;
    }

    /**
     * Sets the account uuid.
     *
     * @param account_uuid the new account uuid
     */
    public void setAccount_uuid(final UUID account_uuid) {
        this.account_uuid = account_uuid;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(entityId).append(account_uuid)
                .toHashCode();
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof AccountId) {
            final AccountId other = (AccountId) obj;
            return new EqualsBuilder().append(entityId, other.entityId)
                    .append(account_uuid, other.account_uuid).isEquals();
        } else {
            return false;
        }
    }
}
