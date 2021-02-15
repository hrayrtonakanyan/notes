package com.disqo.assessment.notes.interceptors;

import com.disqo.assessment.notes.constants.RequestConstants;
import com.disqo.assessment.notes.models.db.User;
import com.disqo.assessment.notes.models.misc.UserSession;
import com.disqo.assessment.notes.models.network.Response;
import com.disqo.assessment.notes.repositories.UserRepository;
import com.disqo.assessment.notes.utils.NotesProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 1:08 AM.
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    // region Static fields
    private static final Logger logger = LogManager.getLogger(AuthInterceptor.class);
    // endregion

    // region Instance fields
    private UserRepository userRepository;
    private final ObjectMapper mapper = new ObjectMapper();
    // endregion

    // region Business logic
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader(RequestConstants.ACCESS_TOKEN_HEADER_NAME);
        if (accessToken == null || accessToken.isEmpty()) {
            logger.error("[NOT_AUTHORISED - INVALID_TOKEN] accessToken is empty.");
            handleResponse(response, Response.ErrorType.TOKEN_NOT_FOUND);
            return false;
        }
        UserSession userSession = decodeJwt(accessToken);
        if (userSession == null) {
            logger.error("[NOT_AUTHORISED - INVALID_TOKEN] userSession is null.");
            handleResponse(response, Response.ErrorType.INVALID_TOKEN);
            return false;
        }
        if (userSession.getCreatedAt() > NotesProperties.getAccessTokenLiveTime()) {
            logger.error("[NOT_AUTHORISED - TOKEN_EXPIRED] userSession is expired.");
            handleResponse(response, Response.ErrorType.TOKEN_EXPIRED);
            return false;
        }
        User user = userRepository.findById(userSession.getUserId());
        if (user == null) {
            logger.error(String.format("[NOT_AUTHORISED - USER_NOT_FOUND][%s]", userSession.getEmail()));
            handleResponse(response, Response.ErrorType.USER_NOT_FOUND);
            return false;
        }
        request.setAttribute(RequestConstants.USER_SESSION_ATTRIBUTE_NAME, userSession);
        logger.debug(String.format("[AUTHORISED][%s][%s]", userSession.getEmail(), userSession.getEmail()));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

    private UserSession decodeJwt(String jwtToken) {
        String userSessionJson = "";
        try {
            userSessionJson = (String) Jwts.parser()
                    .setSigningKey(TextCodec.BASE64.encode(NotesProperties.getJwtSecretKey()))
                    .parseClaimsJws(jwtToken)
                    .getBody().get("sub");
            return mapper.readValue(userSessionJson, UserSession.class);
        } catch (Exception e) {
            logger.error("Can not parse token to UserSession. userSessionJson=" + userSessionJson + ", " + e.getMessage());
            logger.info("Can not parse token to UserSession. userSessionJson=" + userSessionJson + ", " + e.getMessage(), e);
            return null;
        }
    }

    private void handleResponse(HttpServletResponse response, Response.ErrorType errorType) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(mapper.writeValueAsString(new Response(errorType)));
    }
    // endregion

    // region Setters
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    // endregion
}
