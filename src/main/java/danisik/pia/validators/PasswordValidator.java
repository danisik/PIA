package danisik.pia.validators;

import danisik.pia.InitConstants;
import danisik.pia.domain.User;
import danisik.pia.model.ChangePasswordObject;
import danisik.pia.service.user.UserManager;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Password validator for all password inputs.
 */
@Component
public class PasswordValidator implements Validator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserManager userManager;

    /**
     * Determine which class this validator supports.
     * @param clazz Tested class.
     * @return True if clazz is supported or not.
     */
    public boolean supports(Class clazz) {
        return ChangePasswordObject.class.equals(clazz);
    }

    /**
     * Validate password object.
     * @param obj Object to be validated.
     * @param e Error object.
     */
    @SneakyThrows
    public void validate(Object obj, Errors e) {
        ValidationUtils.rejectIfEmptyOrWhitespace(e, "newPassword", "field.required");

        ChangePasswordObject changePasswordObject = (ChangePasswordObject) obj;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userManager.findUserByUsername(username);

        if (!user.getRole().getCode().equals(InitConstants.DEFAULT_ROLE_ADMIN_CODE)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "oldPassword", "field.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "newPasswordConfirm", "field.required");

            if (!passwordEncoder.encode(changePasswordObject.getOldPassword()).equals(user.getPassword())) {
                e.rejectValue("oldPassword", "Staré heslo se neshoduje.");
            }
            else {

                if (changePasswordObject.getOldPassword().equals(changePasswordObject.getNewPassword())) {
                    e.rejectValue("newPassword", "Nové heslo je stejné jako staré heslo.");
                }
                else {

                    if (!changePasswordObject.getNewPassword().equals(changePasswordObject.getNewPasswordConfirm())) {
                        e.rejectValue("newPasswordConfirm", "Nová hesla se neshodují.");
                    }
                }
            }
        }
    }
}
