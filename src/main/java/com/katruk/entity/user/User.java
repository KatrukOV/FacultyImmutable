package com.katruk.entity.user;

import com.katruk.entity.Model;
import com.katruk.entity.Person;
import com.katruk.exception.DaoException;

public interface User {

  User save() throws DaoException;

  User changePerson(Person person);

  enum Role {
    STUDENT,
    TEACHER,
    ADMIN
  }
}