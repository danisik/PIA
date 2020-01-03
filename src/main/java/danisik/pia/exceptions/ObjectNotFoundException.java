package danisik.pia.exceptions;

import org.springframework.web.bind.ServletRequestBindingException;

public class ObjectNotFoundException extends ServletRequestBindingException {
    public ObjectNotFoundException() {
        super("");
    }

    public String getMessage() {
        return "Object not found!";
    }
}

