package view.services.pgn;

public class PGNParseException extends Exception 
{
    /**
     * 
     */
    private static final long serialVersionUID = -2253519940443925317L;

    /**
     * 
     */
    public PGNParseException() {
            // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public PGNParseException(String message) {
            super(message);
            // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public PGNParseException(Throwable cause) {
            super(cause);
            // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public PGNParseException(String message, Throwable cause) {
            super(message, cause);
            // TODO Auto-generated constructor stub
    }

}
