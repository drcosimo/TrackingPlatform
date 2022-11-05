package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class ResetPasswordDto {
    private String token;

    private String oldPassword;

    private String newPassword;

    public ResetPasswordDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
