package com.katruk.dao.mysql.student;

import com.katruk.dao.StudentDao;
import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public final class StudentDaoCache implements StudentDao {

  private final Logger logger;
  private final StudentDao studentDao;
  private final Map<Long, Student> students;

  public StudentDaoCache(StudentDao studentDao) throws DaoException {
    this.logger = Logger.getLogger(StudentDaoCache.class);
    this.studentDao = studentDao;
    this.students = loadStudent();
  }

  private Map<Long, Student> loadStudent() throws DaoException {
    Collection<Student> allStudent = this.studentDao.getAllStudent();
    Map<Long, Student> studentMap = new HashMap<>();
    for (Student student : allStudent) {
      studentMap.put(student.id(), student);
    }
    return studentMap;
  }

  @Override
  public Collection<Student> getAllStudent() throws DaoException {
    return this.students.values();
  }

  @Override
  public Student getStudentById(Long studentId) throws DaoException {
    for (Student student : this.students.values()) {
      if (student.id().equals(studentId)) {
        return student;
      }
    }
    String message = String.format("Student not found by id: %d.", studentId);
    logger.info(message);
    throw new DaoException(message, new NoSuchElementException());
  }

  @Override
  public Student save(Student student) throws DaoException {
    Student studentInDB = this.studentDao.save(student);
    this.students.remove(studentInDB.id());
    this.students.put(studentInDB.id(), studentInDB);
    return studentInDB;
  }

  @Override
  public void delete(Long studentId) throws DaoException {
    this.studentDao.delete(studentId);
    this.students.remove(studentId);
  }
}