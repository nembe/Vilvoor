package nl.nanda.account;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class AccountId implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -2793983717566300654L;
    private Integer entityId;
    private UUID account_uuid;

    public AccountId() {

    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(final Integer entityId) {
        this.entityId = entityId;
    }

    public UUID getAccount_uuid() {
        return account_uuid;
    }

    public void setAccount_uuid(final UUID account_uuid) {
        this.account_uuid = account_uuid;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(entityId).append(account_uuid)
                .toHashCode();
    }

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
