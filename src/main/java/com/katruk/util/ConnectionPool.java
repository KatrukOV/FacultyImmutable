package com.katruk.util;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import java.sql.Connection;
import java.sql.SQLException;

public final class ConnectionPool {

  //max amount of thread that are allowed to idle at the same time
  private final static int MAX_AMOUNT_OF_THREAD = 10;
  //max wait amount until request timeout log
  private final static int MAX_WAIT_AMOUNT = 100;
  //max amount of active threads in the INSTANCE
  private final static int MAX_AMOUNT_OF_ACTIVE_THREADS = 10;
  private final DataSource dataSource;

  public ConnectionPool() {
    BaseConfig dbConfig = BaseConfig.getInstance();
    PoolProperties poolProperties = new PoolProperties();
    poolProperties.setDriverClassName(dbConfig.getValue(BaseConfig.DRIVER));
    poolProperties.setUrl(dbConfig.getValue(BaseConfig.URL));
    poolProperties.setUsername(dbConfig.getValue(BaseConfig.USERNAME));
    poolProperties.setPassword(dbConfig.getValue(BaseConfig.PASSWORD));
    poolProperties.setMaxIdle(MAX_AMOUNT_OF_THREAD);
    poolProperties.setMaxWait(MAX_WAIT_AMOUNT);
    poolProperties.setMaxActive(MAX_AMOUNT_OF_ACTIVE_THREADS);
    this.dataSource = new DataSource();
    this.dataSource.setPoolProperties(poolProperties);
  }

  public Connection getConnection() throws SQLException {
    return dataSource.getConnection();
  }
}