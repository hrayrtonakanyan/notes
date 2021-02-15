package com.disqo.assessment.notes.services;

import com.disqo.assessment.notes.exceptions.InvalidRequestException;
import com.disqo.assessment.notes.models.db.User;
import com.disqo.assessment.notes.models.misc.UserSession;
import com.disqo.assessment.notes.models.network.Response;
import com.disqo.assessment.notes.models.network.UserAuthData;
import com.disqo.assessment.notes.repositories.UserRepository;
import com.disqo.assessment.notes.utils.NotesProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 2:03 AM.
 */
@Service
public class AuthService {

    // region Static fields
    private static final Logger logger = LogManager.getLogger(AuthService.class);
    // endregion

    // region Instance fields
    private UserRepository userRepository;

    private final ObjectMapper mapper = new ObjectMapper();
    // endregion

    // region Business logic
    public Map<String, String> logIn(UserAuthData userAuthData) {

        User user = userRepository.findByEmail(userAuthData.getEmail());
        if (user == null) {
            String errorMessage = String.format("[USER_NOT_FOUND] email=%s", userAuthData.getEmail());
            throw new InvalidRequestException(Response.ErrorType.USER_NOT_FOUND, errorMessage);

        } else if (!BCrypt.checkpw(userAuthData.getPassword(), user.getPassword())) {
            String errorMessage = String.format("[INVALID_PASSWORD] email=%s", userAuthData.getEmail());
            throw new InvalidRequestException(Response.ErrorType.INVALID_PASSWORD, errorMessage);
        }
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);

        String accessToken = createUserSessionToken(user, true);
        String refreshToken = createUserSessionToken(user, false);

        Map<String, String> userTokenMap = new HashMap<>();
        userTokenMap.put("accessToken", accessToken);
        userTokenMap.put("refreshToken", refreshToken);
        return userTokenMap;
    }

    public String token(String grantType, String refreshToken) {

        if (!"grant_type".equals(grantType)) {
            String errorMessage = String.format("[INVALID_GRANT_TYPE] grantType=%s", grantType);
            throw new InvalidRequestException(Response.ErrorType.INVALID_GRANT_TYPE, errorMessage);
        }
        Map<String, String> userInfoMap = decodeJwt(refreshToken);
        if (userInfoMap == null) {
            String errorMessage = "[NOT_AUTHORISED - INVALID_TOKEN] userInfoMap is null.";
            throw new InvalidRequestException(Response.ErrorType.INVALID_TOKEN, errorMessage);
        }
        User user = userRepository.findByEmail(userInfoMap.get("email"));
        if (user == null) {
            String errorMessage = String.format("[INVALID_TOKEN - USER_NOT_FOUND] email=%s", userInfoMap.get("email"));
            throw new InvalidRequestException(Response.ErrorType.INVALID_TOKEN, errorMessage);

        } else if (!user.getPassword().equals(userInfoMap.get("password"))) {
            String errorMessage = String.format("[INVALID_TOKEN - INVALID_PASSWORD] email=%s", userInfoMap.get("email"));
            throw new InvalidRequestException(Response.ErrorType.INVALID_TOKEN, errorMessage);
        }
        user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        userRepository.save(user);

        return createUserSessionToken(user, true);
    }

    public String createUserSessionToken(User user, boolean isAccessToken) {
        try {
            String userSessionStr;
            if (isAccessToken) {
                UserSession userSession = new UserSession(user.getId(), user.getEmail());
                userSessionStr = mapper.writeValueAsString(userSession);
            } else {
                Map<String, String> userInfo = new HashMap<>();
                userInfo.put("email", user.getEmail());
                userInfo.put("password", user.getPassword());
                userSessionStr = mapper.writeValueAsString(userInfo);
            }

            return Jwts.builder()
                    .setSubject(userSessionStr)
                    .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(NotesProperties.getJwtSecretKey()))
                    .compact();
        } catch (JsonProcessingException e) {
            logger.error("Can not parse UserSession to String. username=" + user.getEmail() + ", " + e.getMessage());
            logger.info("Can not parse UserSession to String. username=" + user.getEmail() + ", " + e.getMessage(), e);
            return null;
        }
    }

    private Map<String, String> decodeJwt(String jwtToken) {
        String userSessionJson = "";
        try {
            userSessionJson = (String) Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.encode(NotesProperties.getJwtSecretKey()))
                    .parseClaimsJws(jwtToken)
                    .getBody().get("sub");
            return mapper.readValue(userSessionJson, new TypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            logger.error("Can not parse token to Map. userSessionJson=" + userSessionJson + ", " + e.getMessage());
            logger.info("Can not parse token to Map. userSessionJson=" + userSessionJson + ", " + e.getMessage(), e);
            return null;
        }
    }
    // endregion

    // region Setters
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // endregion
}
