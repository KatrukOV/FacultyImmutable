package com.katruk.service.impl;

import com.katruk.dao.TeacherDao;
import com.katruk.dao.mysql.teacher.TeacherInMySql;
import com.katruk.entity.user.User;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.TeacherService;
import com.katruk.service.UserService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Objects;

public final class TeacherServiceImpl implements TeacherService {

  private final TeacherDao teacherDao;
  private final UserService userService;
  private final Logger logger;

  public TeacherServiceImpl() {
    this.teacherDao = new TeacherInMySql();
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(TeacherServiceImpl.class);
  }

  @Override
  public Collection<Teacher> getAll() throws ServiceException {
    Collection<Teacher> teachers;
    try {
      teachers = this.teacherDao.getAllTeacher();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    Collection<User> users = this.userService.getAll();
    for (User user : users) {
      teachers.stream().filter(teacher -> Objects.equals(user.id(), teacher.user().id()))
          .forEach(teacher -> {
            teacher.setUser(user);
          });
    }
    return teachers;
  }

  @Override
  public Teacher getTeacherById(final Long teacherId) throws ServiceException {
    final Teacher teacher;
    try {
      teacher = this.teacherDao.getTeacherById(teacherId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    final User user = this.userService.getUserById(teacher.user().id());
    teacher.setUser(user);
    return teacher;
  }

  @Override
  public Teacher save(final Teacher teacher) throws ServiceException {
    try {
      this.teacherDao.save(teacher);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return teacher;
  }

  @Override
  public void remove(final Long teacherId) throws ServiceException {
    try {
      this.teacherDao.delete(teacherId);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
  }
}
