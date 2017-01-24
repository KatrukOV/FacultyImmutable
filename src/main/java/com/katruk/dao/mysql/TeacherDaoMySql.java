package com.katruk.dao.mysql;

import static java.util.Objects.nonNull;

import com.katruk.dao.TeacherDao;
import com.katruk.dao.mysql.checkExecute.CheckExecuteUpdate;
import com.katruk.entity.impl.BaseTeacher;
import com.katruk.entity.impl.BaseUser;
import com.katruk.entity.User;
import com.katruk.entity.Teacher;
import com.katruk.exception.DaoException;
import com.katruk.util.ConnectionPool;
import com.katruk.util.Sql;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

public final class TeacherDaoMySql implements TeacherDao, DataBaseNames {

  private final ConnectionPool connectionPool;
  private final Logger logger;

  public TeacherDaoMySql() {
    this.connectionPool = ConnectionPool.getInstance();
    this.logger = Logger.getLogger(TeacherDaoMySql.class);
  }

  @Override
  public Teacher getTeacherById(final Long teacherId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_TEACHER_BY_ID))) {
      statement.setLong(1, teacherId);
      return getTeacherByStatement(statement).stream().iterator().next();
    } catch (SQLException e) {
      logger.error(String.format("Cannot get teacher by id: %d.", teacherId), e);
      throw new DaoException(String.format("Cannot get teacher by id: %d.", teacherId), e);
    }
  }

  @Override
  public Teacher save(final Teacher teacher) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      saveAndCommitOrRollback(teacher, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error("Cannot save teacher.", e);
      throw new DaoException("Cannot save teacher.", e);
    }
    return teacher;
  }

  private void saveAndCommitOrRollback(Teacher teacher, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.REPLACE_TEACHER))) {
      statement.setLong(1, teacher.user().id());
      statement.setString(2, teacher.position() != null ? teacher.position().name() : null);
      new CheckExecuteUpdate(statement, "Replace teacher failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  @Override
  public void delete(Long teacherId) throws DaoException {
    try (Connection connection = this.connectionPool.getConnection()) {
      connection.setAutoCommit(false);
      deleteAndCommitOrRollback(teacherId, connection);
      connection.setAutoCommit(true);
    } catch (SQLException e) {
      logger.error(String.format("Cannot delete teacher with id: %d.", teacherId), e);
      throw new DaoException(String.format("Cannot delete teacher with id: %d.", teacherId), e);
    }
  }

  private void deleteAndCommitOrRollback(Long teacherId, Connection connection)
      throws SQLException, DaoException {
    try (PreparedStatement statement = connection
        .prepareStatement(Sql.getInstance().get(Sql.DELETE_TEACHER))) {
      statement.setLong(1, teacherId);
      new CheckExecuteUpdate(statement, "Delete teacher failed, no rows affected.").check();
      connection.commit();
    } catch (SQLException e) {
      connection.rollback();
      logger.error("Cannot prepare statement.", e);
      throw new DaoException("Cannot prepare statement.", e);
    }
  }

  @Override
  public Collection<Teacher> getAllTeacher() throws DaoException {
    final Collection<Teacher> result;
    try (Connection connection = this.connectionPool.getConnection();
         PreparedStatement statement = connection
             .prepareStatement(Sql.getInstance().get(Sql.GET_ALL_TEACHER))) {
      result = getTeacherByStatement(statement);
    } catch (SQLException e) {
      logger.error("Cannot get all teachers.", e);
      throw new DaoException("Cannot get all teachers.", e);
    }
    return result;
  }

  private Collection<Teacher> getTeacherByStatement(final PreparedStatement statement)
      throws DaoException {
    final Collection<Teacher> teachers = new ArrayList<>();
    try (ResultSet resultSet = statement.executeQuery()) {
      while (resultSet.next()) {
        Teacher teacher = getTeacher(resultSet);
        teachers.add(teacher);
      }
    } catch (SQLException e) {
      logger.error("Cannot get teacher by statement.", e);
      throw new DaoException("Cannot get teacher by statement.", e);
    }
    if (teachers.isEmpty()) {
      throw new DaoException("No teachers by statement.", new NoSuchElementException());
    }
    return teachers;
  }

  private Teacher getTeacher(ResultSet resultSet) throws SQLException {
    Long id = resultSet.getLong(USER_ID);
    User user = new BaseUser(id);
    Teacher.Position position = null;
    if (nonNull(resultSet.getString(POSITION))) {
      position = Teacher.Position.valueOf(resultSet.getString(POSITION));
    }
    return new BaseTeacher(id, user, position);
  }
}