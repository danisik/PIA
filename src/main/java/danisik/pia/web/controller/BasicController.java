package danisik.pia.web.controller;

import danisik.pia.exceptions.ParseIDException;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Controller;

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
}
