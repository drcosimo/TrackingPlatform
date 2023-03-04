package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

public class SendMessageDto {
    private String token;
    private String content;


    public SendMessageDto() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
