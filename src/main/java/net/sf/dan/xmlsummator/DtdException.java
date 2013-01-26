package net.sf.dan.xmlsummator;

/**
 * Xml document doesn't have proper structure.
 *
 * Daneel Yaitskov
 */
public class DtdException extends Exception {

    public DtdException() {
    }

    public DtdException(String message) {
        super(message);
    }

    public DtdException(String message, Throwable cause) {
        super(message, cause);
    }

    public DtdException(Throwable cause) {
        super(cause);
    }

    public DtdException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
