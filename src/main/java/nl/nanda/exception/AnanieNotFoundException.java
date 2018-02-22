package nl.nanda.exception;

/**
 * 
 * The Class AnanieNotFoundException tell us that something not Nice happened,
 * so we stop further processing of the transfer.
 */
public class AnanieNotFoundException extends RuntimeException {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -606925344573029631L;

    /**
     * Instantiates a new Ananie not found exception.
     *
     * @param ex
     *            the ex
     */
    public AnanieNotFoundException(final String ex) {
        super(ex);
    }

}
