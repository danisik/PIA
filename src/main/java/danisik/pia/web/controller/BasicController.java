package danisik.pia.web.controller;

import danisik.pia.exceptions.ParseIDException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Default controller.
 */
@Controller
public class BasicController {

    /**
     * Try to parse ID from URL.
     * @param Id ID from url.
     * @return Parser ID.
     * @throws ParseIDException Exception if ID from url is not valid.
     */
    protected Long parseId(String Id) throws ParseIDException {
        Long longId = null;

        try {
            longId = Long.parseLong(Id);
        }
        catch (Exception e) {
            throw new ParseIDException();
        }
        return longId;
    }

    /**
     * Return message containings all errors from validator.
     * @param result Validator results.
     * @return String message containings all errors from validator.
     */
    protected String getFullErrorMessage(BindingResult result) {
        String message = "";

        for (Object object : result.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;

                message += fieldError.getCode() + "\n";
                continue;
            }

            if (object instanceof ObjectError) {
                ObjectError objectError = (ObjectError) object;

                message += objectError.getCode()  + "\n";
                continue;
            }
        }
        return message;
    }
}
