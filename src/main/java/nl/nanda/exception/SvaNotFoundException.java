package nl.nanda.exception;

public class SvaNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -606925344573029631L;

    public SvaNotFoundException(final String ex) {
        super(ex);
    }

}
