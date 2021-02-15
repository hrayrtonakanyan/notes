package com.disqo.assessment.notes.models.network;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 2:06 AM.
 */
public class UserAuthData {

    private String email;
    private String password;

    public UserAuthData() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAuthData{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
