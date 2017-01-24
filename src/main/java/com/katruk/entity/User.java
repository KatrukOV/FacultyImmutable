package com.katruk.entity;

import com.katruk.entity.impl.BaseUser;

public interface User extends Model {

  Person person();

  String username();

  String password();

  Role role();

  User changePerson(Person person);

  enum Role {
    STUDENT,
    TEACHER,
    ADMIN
  }
}
