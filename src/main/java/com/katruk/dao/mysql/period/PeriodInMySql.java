package com.katruk.dao.mysql.period;

import com.katruk.dao.PeriodDao;
import com.katruk.dao.mysql.DataBaseNames;
import com.katruk.dao.mysql.checkExecute.CheckExecuteUpdate;
import com.katruk.entity.impl.BasePeriod;
import com.katruk.entity.Period;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;
import com.katruk.util.Sql;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public final class PeriodInMySql implements PeriodDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public PeriodInMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(PeriodInMySql.class);
  }

  @Override
  public Period getLastPeriod() throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_LAST_PERIOD))) {
      return getPeriodByStatement(statement).stream().iterator().next();
    } catch (SQLException e) {
      logger.error("Cannot get last period", e);
      throw new DaoException("Cannot get last period", e);
    }
  }

  @Override
  public Period save(Period period) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      period = saveAndCommitOrRollback(period, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot save period", e);
      throw new DaoException("Cannot save period", e);
    }
    return period;
  }

  private Period saveAndCommitOrRollback(Period period, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.INSERT_PERIOD),
                          Statement.RETURN_GENERATED_KEYS)) {
      statement.setString(1, period.status().name());
      new CheckExecuteUpdate(statement, "Creating period failed, no rows affected.").check();
      connection.commit();
      return getAndSetId(period, statement);
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement", e);
      throw new DaoException("Cannot prepare statement", e);
    }
  }

  private Period getAndSetId(Period period, PreparedStatement statement) throws SQLException {
    ResultSet generatedKeys = statement.getGeneratedKeys();
    if (generatedKeys.next()) {
      return period.addId(generatedKeys.getLong(1));
    } else {
      throw new SQLException("Creating period failed, no ID obtained.");
    }
  }

  private Collection<Period> getPeriodByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Period> periods = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Period period = getPeriod(resultSet);
        periods.add(period);
      }
    } catch (SQLException e) {
      logger.error("Unable to get period by statement", e);
      throw new DaoException("Unable to get period by statement", e);
    }
    if (periods.isEmpty()) {
      throw new DaoException("No user by statement.", new NoSuchElementException());
    }
    return periods;
  }

  private Period getPeriod(ResultSet resultSet) throws SQLException {
    Long id = resultSet.getLong(ID);
    Period.Status status = Period.Status.valueOf(resultSet.getString(STATUS));
    Date date = Date.valueOf(resultSet.getString(DATE));
    return new BasePeriod(id, status, date);
  }
}