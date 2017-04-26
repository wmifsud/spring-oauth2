package com.oauth2.config.database;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

/**
 * @author waylon on 01/02/2016.
 */
@Configuration
@EntityScan(basePackages = "com.oauth2.entity")
@EnableJpaRepositories(
        basePackages = "com.oauth2.repository")
public class Oauth2DataSource {

    @Value("${hikari.transaction.isolation}")
    private String transactionIsolation;

    @Value("${hikari.username}")
    private String userName;

    @Value("${hikari.password}")
    private String password;

    @Value("${hikari.url}")
    private String jdbcUrl;

    @Value("${hikari.auto.commit}")
    private boolean autoCommit;

    @Value("${hikari.connection.timeout}")
    private int connectionTimeout;

    @Value("${hikari.idle.timeout}")
    private int idleTimeout;

    @Value("${hikari.max.lifetime}")
    private int maxLifetime;

    @Value("${hikari.minimum.idle}")
    private int minimumIdle;

    @Value("${hikari.max.pool.size}")
    private int maxPoolSize;

    @Value("${hikari.pool.name}")
    private String poolName;

    @Value("${hikari.initialization.fail.fast}")
    private boolean initializationFailFast;

    @Value("${hikari.isolate.internal.queries}")
    private boolean isolateInternalQueries;

    @Value("${hikari.allow.pool.suspension}")
    private boolean allowPoolSuspension;

    @Value("${hikari.read.only}")
    private boolean readOnly;

    @Value("${hikari.register.mbeans}")
    private boolean registerMbeans;

    @Value("${hikari.validation.timeout}")
    private int validationTimeout;

    @Value("${hikari.leak.detection.threshold}")
    private int leakDetectionThreshold;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setTransactionIsolation(transactionIsolation);
        dataSource.setMaximumPoolSize(maxPoolSize);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setAutoCommit(autoCommit);
        dataSource.setConnectionTimeout(connectionTimeout);
        dataSource.setIdleTimeout(idleTimeout);
        dataSource.setMaxLifetime(maxLifetime);
        dataSource.setMinimumIdle(minimumIdle);
        dataSource.setMaximumPoolSize(maxPoolSize);
        dataSource.setPoolName(poolName);

        dataSource.setInitializationFailFast(initializationFailFast);
        dataSource.setIsolateInternalQueries(isolateInternalQueries);
        dataSource.setAllowPoolSuspension(allowPoolSuspension);
        dataSource.setReadOnly(readOnly);
        dataSource.setRegisterMbeans(registerMbeans);
        dataSource.setValidationTimeout(validationTimeout);
        dataSource.setLeakDetectionThreshold(leakDetectionThreshold);

        return dataSource;
    }
}