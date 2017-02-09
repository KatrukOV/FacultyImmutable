package com.katruk.entity.user.mysql;

import com.katruk.dao.mysql.checkExecute.CheckExecuteUpdate;
import com.katruk.entity.Person;
import com.katruk.entity.user.User;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;
import com.katruk.util.Sql;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class UserMySql implements User {

  private final Person person;
  private final String username;
  private final String password;
  private final Role role;
  private final ConnectionPool connectionPool;
  private final Logger logger;


  public UserMySql(ConnectionPool connectionPool, Person person, String username, String password,
                   Role role) {
    this.connectionPool = connectionPool;
    this.person = person;
    this.username = username;
    this.password = password;
    this.role = role;
    this.logger = Logger.getLogger(UserMySql.class);
  }

  @Override
  public User save() throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      saveAndCommitOrRollback(connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot save user.", e);
      throw new DaoException("Cannot save user.", e);
    }
    return new UserMySql(this.connectionPool, person, this.username, this.password, this.role);
  }

  private void saveAndCommitOrRollback(Connection connection) throws SQLException, DaoException {
    try (PreparedStatement statement = connection.prepareStatement(Sql.getInstance().get(Sql.REPLACE_USER))) {
      fillSaveUserStatement(statement);
      this.person.save();
      new CheckExecuteUpdate(statement, "Replace user failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  private void fillSaveUserStatement(PreparedStatement statement) throws SQLException {
    statement.setLong(1, user.person().id());
    statement.setString(2, this.username);
    statement.setString(3, this.password);
    statement.setString(4, this.role != null ? this.role.name() : null);
  }



  @Override
  public User changePerson(Person person) {
    return new UserMySql(this.connectionPool, person, this.username, this.password, this.role);
  }
}