package com.disqo.assessment.notes.models.misc;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/15/21.
 * Time: 11:50 PM.
 */
public class UserSession {

    private long userId;
    private String email;

    public UserSession(long userId, String email) {
        this.userId = userId;
        this.email = email;
    }

    public UserSession() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                '}';
    }
}
