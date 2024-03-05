package dataAccess;

/**
 * Indicates there was an error connecting to the database
 */
public class DataAccessException extends Exception{
    final Integer code;
    public DataAccessException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public int StatusCode() {
        return code;
    }
}
