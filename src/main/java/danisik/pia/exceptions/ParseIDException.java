package danisik.pia.exceptions;

import org.springframework.web.bind.ServletRequestBindingException;

/**
 * Custom exception, occured when ID from url is not number.
 */
public class ParseIDException extends ServletRequestBindingException {

    /**
     * Constructor.
     */
    public ParseIDException() {
        super("");
    }

    /**
     * Get message if this exception occured.
     * @return String message.
     */
    public String getMessage() {
        return "Type of request parameter ID is not Long!";
    }
}

