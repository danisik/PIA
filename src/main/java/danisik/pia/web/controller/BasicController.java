package danisik.pia.web.controller;

import danisik.pia.exceptions.ParseIDException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Controller
public class BasicController {

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
