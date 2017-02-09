package com.katruk.dao.mysql.subject;

import com.katruk.dao.SubjectDao;
import com.katruk.dao.TeacherDao;
import com.katruk.entity.Subject;
import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class SubjectDaoCache implements TeacherDao {

  private final Logger logger;
  private final SubjectDao subjectDao;
  private final Map<Long, Subject> subjects;

  public SubjectDaoCache(SubjectDao subjectDao) throws DaoException {
    this.logger = Logger.getLogger(SubjectDaoCache.class);
    this.subjectDao = subjectDao;
    this.subjects = loadSubject();
  }

  private Map<Long, Subject> loadSubject() throws DaoException {
    Collection<Subject> allSubject = this.subjectDao.getAllSubject();
    Map<Long, Subject> subjectMap = new HashMap<>();
    for (Subject subject : allSubject) {
      subjectMap.put(subject.id(), subject);
    }
    return subjectMap;
  }

  @Override
  public Teacher getTeacherById(Long teacherId) throws DaoException {
    return null;
  }

  @Override
  public Collection<Teacher> getAllTeacher() throws DaoException {
    return null;
  }

  @Override
  public Teacher save(Teacher teacher) throws DaoException {
    return null;
  }

  @Override
  public void delete(Long teacherId) throws DaoException {

  }
/*
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
  */
}