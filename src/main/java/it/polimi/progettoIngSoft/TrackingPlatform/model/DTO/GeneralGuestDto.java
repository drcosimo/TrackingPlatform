package it.polimi.progettoIngSoft.TrackingPlatform.model.DTO;

import java.time.Instant;

public class GeneralGuestDto {
    private Long id;

    private String name;

    private String surname;

    private String username;

    private String email;

    private Instant birthDate;

    private String sex;

    private boolean isAdmin;

    public GeneralGuestDto() {
    }

    public GeneralGuestDto(Long id, String name, String surname, String username, String email, Instant birthDate, String sex, boolean isAdmin) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.birthDate = birthDate;
        this.sex = sex;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
