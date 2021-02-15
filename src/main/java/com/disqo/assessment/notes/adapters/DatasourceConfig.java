package com.disqo.assessment.notes.adapters;

import com.disqo.assessment.notes.utils.NotesProperties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: Hrayr
 * Date: 2/16/21.
 * Time: 1:54 AM.
 */
@Configuration
public class DatasourceConfig {

    private static final Logger logger = LogManager.getLogger(DatasourceConfig.class);

    @Bean
    public DataSource datasource() {

        String dbDriver = NotesProperties.getDbDriver();
        String dbUrl = NotesProperties.getDbUrl();
        String dbUsername = NotesProperties.getDbUsername();
        String dbPassword = NotesProperties.getDbPassword();

        logger.info("Database connection params. dbDriver={}, dbUrl={}, dbUsername={}, dbPassword={}",
                dbDriver, dbUrl, dbUsername, dbPassword);

        return DataSourceBuilder.create()
                .driverClassName(dbDriver)
                .url(dbUrl)
                .username(dbUsername)
                .password(dbPassword)
                .build();
    }
}
