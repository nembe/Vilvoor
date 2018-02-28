package nl.nanda.status;

import nl.nanda.exception.AnanieException;

/**
 * The enum Status with the different states a Transfer can be in..
 */

public enum Status {

    ACCOUNT_NOT_FOUND(0), CONFIRMED(1), INSUFFICIENT_FUNDS(2), PENDING(3), NOT_AVAILABLE(
            4);
    /** The state. */
    private Integer state;

    /**
     * Instantiates a new status.
     *
     * @param state
     *            the state
     */
    private Status(final Integer state) {
        this.state = state;
    }

    /**
     * Return the state of the Transfer.
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
        case 4:
            status = "NOT_AVAILABLE";
            break;

        default:
            status = "PENDING";
            break;
        }
        return status;
    }

    /**
     * Sets the state of the Transfer.
     *
     * @param state
     *            the new state
     */
    public void setState(final Integer state) {
        this.state = state;
    }

}
