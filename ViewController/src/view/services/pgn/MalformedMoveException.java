package view.services.pgn;

public class MalformedMoveException extends Exception
{

    /**
     * 
     */
    private static final long serialVersionUID = 7389593033170883050L;

    /**
     * 
     */
    public MalformedMoveException() {
            // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     */
    public MalformedMoveException(String message) {
            super(message);
            // TODO Auto-generated constructor stub
    }

    /**
     * @param cause
     */
    public MalformedMoveException(Throwable cause) {
            super(cause);
            // TODO Auto-generated constructor stub
    }

    /**
     * @param message
     * @param cause
     */
    public MalformedMoveException(String message, Throwable cause) {
            super(message, cause);
            // TODO Auto-generated constructor stub
    }
}
