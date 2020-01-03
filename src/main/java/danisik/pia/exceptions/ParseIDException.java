package danisik.pia.exceptions;

import org.springframework.web.bind.ServletRequestBindingException;

public class ParseIDException extends ServletRequestBindingException {
    public ParseIDException() {
        super("");
    }

    public String getMessage() {
        return "Type of request parameter ID is not Long!";
    }
}

