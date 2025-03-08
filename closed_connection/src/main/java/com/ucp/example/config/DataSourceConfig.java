package com.ucp.example.config;

import java.sql.SQLException;
import java.time.Duration;

import javax.sql.DataSource;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import oracle.ucp.jdbc.PoolDataSource;
import oracle.ucp.jdbc.PoolDataSourceFactory;

@Configuration
public class DataSourceConfig implements EnvironmentAware {

  private Environment environment;

  @Bean
  public DataSource dataSource() throws SQLException {
    PoolDataSource poolDataSource = PoolDataSourceFactory.getPoolDataSource();
    poolDataSource.setConnectionFactoryClassName(environment.getProperty("spring.datasource.driverClassName"));
    poolDataSource.setURL(environment.getProperty("spring.datasource.url"));
    poolDataSource.setUser(environment.getProperty("spring.datasource.username"));
    poolDataSource.setPassword(environment.getProperty("spring.datasource.password"));
    poolDataSource.setMinPoolSize(environment.getProperty("spring.datasource.ucp.min-poo-size", Integer.class, 2));
    poolDataSource.setMaxPoolSize(environment.getProperty("spring.datasource.ucp.max-poo-size", Integer.class, 4));
    poolDataSource.setAbandonedConnectionTimeout(environment.getProperty("spring.datasource.ucp.abandoned-connection-timeout", Integer.class, 20));
    poolDataSource.setInactiveConnectionTimeout(environment.getProperty("spring.datasource.ucp.inactive-connection-timeout", Integer.class, 20));
    poolDataSource.setConnectionWaitDuration(Duration.ofSeconds(environment.getProperty("spring.datasource.ucp.connection-wait-duration", Long.class, 10L)));
    poolDataSource.setValidateConnectionOnBorrow(true);
    poolDataSource.setConnectionProperty("oracle.ucp.jdbc.Debug", "true");

    return poolDataSource;
  }

  @Override
  public void setEnvironment(@NonNull Environment environment) {
    this.environment = environment;
  }
}

