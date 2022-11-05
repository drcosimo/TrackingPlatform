package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class ChangeEmailDto {
    private String token;

    private String oldEmail;

    private String newEmail;

    public ChangeEmailDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public void setOldEmail(String oldEmail) {
        this.oldEmail = oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }
}
