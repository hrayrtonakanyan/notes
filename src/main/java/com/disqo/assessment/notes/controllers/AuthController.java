package com.disqo.assessment.notes.controllers;

import com.disqo.assessment.notes.models.network.RefreshTokenData;
import com.disqo.assessment.notes.models.network.Response;
import com.disqo.assessment.notes.models.network.UserAuthData;
import com.disqo.assessment.notes.services.AuthService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 2:01 AM.
 */
@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    // region Static fields
    private static final Logger logger = LogManager.getLogger(AuthController.class);
    // endregion

    // region Instance fields
    private AuthService authService;
    // endregion

    // region Requests
    @GetMapping("/ping")
    public Response getPing() {
        return new Response("pong");
    }

    @PostMapping("/log-in")
    public Response postLogIn(@RequestBody final UserAuthData userAuthData) {
        long startTs = System.currentTimeMillis();
        logger.debug("[POST_NOTES - req]");
        Map<String, String> userTokens = authService.logIn(userAuthData);
        logger.debug("[POST_NOTES - resp] [" + (System.currentTimeMillis() - startTs) + "]");
        return new Response(userTokens);
    }

    @PostMapping("/token")
    public Response postToken(@RequestBody final RefreshTokenData refreshTokenData) {
        long startTs = System.currentTimeMillis();
        logger.debug("[POST_NOTES - req]");
        String accessToken = authService.token(refreshTokenData);
        logger.debug("[POST_NOTES - resp] [" + (System.currentTimeMillis() - startTs) + "]");
        return new Response("accessToken", accessToken);
    }
    // endregion

    // region Setters
    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }
    // endregion
}
