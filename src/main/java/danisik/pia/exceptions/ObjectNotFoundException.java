package danisik.pia.exceptions;

import org.springframework.web.bind.ServletRequestBindingException;

/**
 * Custom exception, occured when specific object (user, invoice, ...) was not found during getting data.
 */
public class ObjectNotFoundException extends ServletRequestBindingException {

    /**
     * Constructor.
     */
    public ObjectNotFoundException() {
        super("");
    }

    /**
     * Get message if this exception occured.
     * @return String message.
     */
    public String getMessage() {
        return "Object not found!";
    }
}

