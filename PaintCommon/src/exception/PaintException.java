package exception;

/**
 *
 * @author MOHSINE
 */
public class PaintException extends Exception {

    /**
     * Creates a new instance of <code>PaintException</code> without detail
     * message.
     */
    public PaintException() {

    }

    /**
     * Constructs an instance of <code>PaintException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public PaintException(String msg) {
        super(msg);
    }
    
}
