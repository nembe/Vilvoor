package nl.nanda.status;

import java.io.Serializable;

import javax.persistence.Embeddable;

import nl.nanda.exception.AnanieException;

/**
 * The Class Status.
 */
@Embeddable
public class Status implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2513771070740729801L;

    /** The state. */
    private Integer state;

    /**
     * Instantiates a new status.
     */
    public Status() {

    }

    /**
     * Instantiates a new status.
     *
     * @param state
     *            the state
     */
    public Status(final Integer state) {
        this.state = state;
    }

    /**
     * Value of.
     *
     * @return the string
     */
    public String valueOf() {
        if (state == null || state == 0) {
            throw new AnanieException("The status value is required");
        }

        String status;
        switch (state) {
        case 0:
            status = "ACCOUNT_NOT_FOUND";
            break;
        case 1:
            status = "CONFIRMED";
            break;
        case 2:
            status = "INSUFFICIENT_FUNDS";
            break;
        case 3:
            status = "PENDING";
            break;

        default:
            status = "PENDING";
            break;
        }
        return status;
    }

    /**
     * Sets the state.
     *
     * @param state
     *            the new state
     */
    public void setState(final Integer state) {
        this.state = state;
    }

}
