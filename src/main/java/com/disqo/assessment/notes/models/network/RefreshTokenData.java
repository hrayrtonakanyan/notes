package com.disqo.assessment.notes.models.network;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 6:13 PM.
 */
public class RefreshTokenData {

    private String grantType;
    private String refreshToken;

    public RefreshTokenData() {
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return "RefreshTokenData{" +
                "grantType='" + grantType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                '}';
    }
}
