package danisik.pia.model;

/**
 * Model representing object for password changing.
 */
public class ChangePasswordObject {

    private Long userId;

    private String oldPassword;

    private String newPassword;

    private String newPasswordConfirm;

    /**
     * Get new password confirmation.
     * @return New password confirmation.
     */
    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }

    /**
     * Set new password confirmation.
     * @param newPasswordConfirm New password confirmation.
     */
    public void setNewPasswordConfirm(String newPasswordConfirm) {
        this.newPasswordConfirm = newPasswordConfirm;
    }


    /**
     * Get new password.
     * @return New password.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Set new password.
     * @param newPassword New password.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Get old password.
     * @return Old password.
     */
    public String getOldPassword() {
        return oldPassword;
    }

    /**
     * Set old password.
     * @param oldPassword Old password.
     */
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    /**
     * Get user ID.
     * @return User id.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Set user Id.
     * @param userId User id.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
