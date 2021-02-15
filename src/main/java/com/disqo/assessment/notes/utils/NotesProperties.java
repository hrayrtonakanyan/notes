package com.disqo.assessment.notes.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 1:21 AM.
 */
public class NotesProperties {

    // region Static fields
    private static final Logger logger = LogManager.getLogger(NotesProperties.class);
    // endregion

    // region Default value
    private static final String dbDriver = "org.postgresql.Driver";
    private static final String dbUrl = "jdbc:postgresql://127.0.0.1:5432/notes";
    private static final String dbUsername = "dbuser";
    private static final String dbPassword = "dbpassword139*";

    private static final String jwtSecretKey = "sdfadfghsfgjhdgfhjdghmdghnsfbadf";

    private static final long accessTokenLiveTime = 5L * 60 * 60 * 1000;
    private static final int noteTitleMaxLength = 50;
    private static final int noteBodyMaxLength = 1000;
    // endregion

    // region Getters
    public static String getDbDriver() {
        return dbDriver;
    }

    public static String getDbUrl() {
        return dbUrl;
    }

    public static String getDbUsername() {
        return dbUsername;
    }

    public static String getDbPassword() {
        return dbPassword;
    }

    public static String getJwtSecretKey() {
        return jwtSecretKey;
    }

    public static long getAccessTokenLiveTime() {
        return accessTokenLiveTime;
    }

    public static int getNoteTitleMaxLength() {
        return noteTitleMaxLength;
    }

    public static int getNoteBodyMaxLength() {
        return noteBodyMaxLength;
    }

    // endregion
}
