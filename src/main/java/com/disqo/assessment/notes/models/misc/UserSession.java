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
    private long createdAt;

    public UserSession(long userId, String email) {
        this.userId = userId;
        this.email = email;
        this.createdAt = System.currentTimeMillis();
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

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
