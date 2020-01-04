package danisik.pia.validators;

import danisik.pia.Constants;
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

import java.util.regex.Pattern;

@Component
public class PasswordValidator implements Validator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserManager userManager;

    public boolean supports(Class clazz) {
        return ChangePasswordObject.class.equals(clazz);
    }

    @SneakyThrows
    public void validate(Object obj, Errors e) {

        ChangePasswordObject changePasswordObject = (ChangePasswordObject) obj;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userManager.findUserByUsername(username);

        if (!user.getRole().getCode().equals(InitConstants.DEFAULT_ROLE_ADMIN_CODE)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "oldPassword", "field.required");
            ValidationUtils.rejectIfEmptyOrWhitespace(e, "newPassword", "field.required");
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
