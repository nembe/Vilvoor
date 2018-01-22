package nl.nanda.status;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class Status implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2513771070740729801L;
    private Integer state;

    public Status(final Integer state) {
        this.state = state;
    }

    private Status() {
    }

    public String valueOf() {
        if (state == null || state == 0) {
            throw new IllegalArgumentException("The status value is required");
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

    public void setState(final Integer state) {
        this.state = state;
    }

}
