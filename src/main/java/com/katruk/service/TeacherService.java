package com.katruk.service;

import com.katruk.entity.Teacher;
import com.katruk.exception.ServiceException;

import java.util.Collection;

public interface TeacherService {

  Collection<Teacher> getAll() throws ServiceException;

  Teacher getTeacherById(final Long teacherId) throws ServiceException;

  Teacher save(final Teacher teacher) throws ServiceException;

  void remove(final Long teacherId) throws ServiceException;
}
