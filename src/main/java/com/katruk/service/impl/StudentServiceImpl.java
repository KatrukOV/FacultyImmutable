package com.katruk.service.impl;

import com.katruk.dao.StudentDao;
import com.katruk.dao.mysql.student.StudentInMySql;
import com.katruk.entity.user.User;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.StudentService;
import com.katruk.service.UserService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Objects;

public final class StudentServiceImpl implements StudentService {

  private final StudentDao studentDao;
  private final UserService userService;
  private final Logger logger;

  public StudentServiceImpl() throws ServiceException {
    this.studentDao = new StudentInMySql();
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(StudentServiceImpl.class);
  }

  @Override
  public Collection<Student> getAll() throws ServiceException {
    final Collection<Student> students;
    try {
      students = this.studentDao.getAllStudent();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    Collection<User> users = this.userService.getAll();
    for (User user : users) {
      students.stream().filter(student -> Objects.equals(user.id(), student.user().id()))
          .forEach(student -> {
//            student.setUser(user);
          });
    }
    return students;
  }

  @Override
  public Student getStudentById(final Long studentId) throws ServiceException {
    final Student student;
    try {
      student = this.studentDao.getStudentById(studentId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final User user = this.userService.getUserById(student.user().id());
//    student.setUser(user);
    return student;
  }

  @Override
  public Student save(final Student student) throws ServiceException {
    try {
      this.studentDao.save(student);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return student;
  }

  @Override
  public void remove(final Long studentId) throws ServiceException {
    try {
      this.studentDao.delete(studentId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
  }
}