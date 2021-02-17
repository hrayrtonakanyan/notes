package com.disqo.assessment.notes.adapters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${db.driver}")
    private String dbDriver;
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPassword;

    @Bean
    public DataSource datasource() {

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
