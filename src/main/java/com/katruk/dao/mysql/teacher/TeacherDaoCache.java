package com.katruk.dao.mysql.teacher;

import com.katruk.dao.TeacherDao;
import com.katruk.dao.UserDao;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public final class TeacherDaoCache implements TeacherDao {

  private final Logger logger;
  private final TeacherDao teacherDao;
  private final Map<Long, Teacher> teachers;

  public TeacherDaoCache(TeacherDao teacherDao) throws DaoException {
    this.logger = Logger.getLogger(TeacherDaoCache.class);
    this.teacherDao = teacherDao;
    this.teachers = loadTeacher();
  }

  private Map<Long, Teacher> loadTeacher() throws DaoException {
    Collection<Teacher> allTeacher = this.teacherDao.getAllTeacher();
    Map<Long, Teacher> teacherMap = new HashMap<>();
    for (Teacher teacher : allTeacher) {
      teacherMap.put(teacher.id(), teacher);
    }
    return teacherMap;
  }

  @Override
  public Teacher getTeacherById(Long teacherId) throws DaoException {
    for (Teacher teacher : this.teachers.values()) {
      if (teacher.id().equals(teacherId)) {
        return teacher;
      }
    }
    String message = String.format("Teacher not found by id: %d.", teacherId);
    logger.info(message);
    throw new DaoException(message, new NoSuchElementException());
  }

  @Override
  public Collection<Teacher> getAllTeacher() throws DaoException {
    return this.teachers.values();
  }

  @Override
  public Teacher save(Teacher teacher) throws DaoException {
    Teacher teacherInDB = this.teacherDao.save(teacher);
    this.teachers.remove(teacherInDB.id());
    this.teachers.put(teacherInDB.id(), teacherInDB);
    return teacherInDB;
  }

  @Override
  public void delete(Long teacherId) throws DaoException {
    this.teacherDao.delete(teacherId);
    this.teachers.remove(teacherId);
  }
}