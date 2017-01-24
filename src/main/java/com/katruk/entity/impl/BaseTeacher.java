package com.katruk.entity.impl;

import com.katruk.entity.Model;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;

public final class BaseTeacher extends BaseModel implements Teacher {

  private final User user;
  private final Position position;

  public BaseTeacher(Long id) {
    super(id);
    this.user = null;
    this.position = null;
  }
//
//  public BaseTeacher(User user) {
//    super();
//    this.user = user;
//    this.position = null;
//  }
//
//  public BaseTeacher(User user, Position position) {
//    super();
//    this.user = user;
//    this.position = position;
//  }

  public BaseTeacher(Long id, User user, Position position) {
    super(id);
    this.user = user;
    this.position = position;
  }

  @Override public User user() {
    return user;
  }

  @Override public Position position() {
    return position;
  }
}