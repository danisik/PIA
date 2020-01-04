package danisik.pia.validators;

import danisik.pia.Constants;
import danisik.pia.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

/**
 * User validator for all users input.
 */
@Component
public class UserValidator implements Validator {

    /**
     * Determine which class this validator supports.
     * @param clazz Tested class.
     * @return True if clazz is supported or not.
     */
    public boolean supports(Class clazz) {
        return User.class.equals(clazz);
    }

    /**
     * Validate user object.
     * @param obj Object to be validated.
     * @param e Error object.
     */
    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "birthNumber", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "address", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "email", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "cardNumber", "field.required");

        Pattern cardPattern = Pattern.compile(Constants.VALIDATE_CARD_NUMBER_REGEX);
        Pattern birthNumberPattern = Pattern.compile(Constants.VALIDATE_BIRTH_NUMBER_REGEX);
        Pattern addressPattern = Pattern.compile(Constants.VALIDATE_ADDRESS_REGEX);
        Pattern emailPattern = Pattern.compile(Constants.VALIDATE_EMAIL_REGEX);
        Pattern phoneNumberPattern = Pattern.compile(Constants.VALIDATE_PHONE_NUMBER_REGEX);


        User user = (User) obj;

        if (!birthNumberPattern.matcher(user.getBirthNumber()).matches()) {
            e.rejectValue("birthNumber", "Neplatný formát rodného čísla (111222/3333).");
        }

        if (!addressPattern.matcher(user.getAddress()).matches()) {
            e.rejectValue("address", "Neplatný formát adresy (U Vodárny 26, 431 55 Radonice).");
        }

        if (!emailPattern.matcher(user.getEmail()).matches()) {
            e.rejectValue("email", "Neplatný formát emailu (a@seznam.cz).");
        }

        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank() && !phoneNumberPattern.matcher(user.getPhoneNumber()).matches()) {
            e.rejectValue("phoneNumber", "Neplatný formát čísla (333111234).");
        }

        if (!cardPattern.matcher(user.getCardNumber()).matches()) {
            e.rejectValue("cardNumber", "Neplatný formát čísla karty (VISA, MASTERCARD, ... formáty).");
        }
    }
}
