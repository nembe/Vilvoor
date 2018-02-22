package nl.nanda.exception;

/**
 * The Class AnanieException to stop further processing of the transfer.
 */
public class AnanieException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4786107113012806296L;

    /**
     * Instantiates a new ananie exception.
     *
     * @param ex
     *            the ex
     */
    public AnanieException(final String ex) {
        super(ex);
    }

    /**
     * @param ex
     * @param e
     */
    public AnanieException(final String ex, final Throwable e) {
        super(ex, e);
    }

}
