package com.katruk.entity.impl;

import com.katruk.entity.Model;
import com.katruk.entity.Subject;
import com.katruk.entity.Teacher;

public final class BaseSubject extends BaseModel implements Subject {

  private final String title;
  private final Teacher teacher;

  public BaseSubject(Long id) {
    super(id);
    this.title = null;
    this.teacher = null;
  }

  public BaseSubject(String title, Teacher teacher) {
    super();
    this.title = title;
    this.teacher = teacher;
  }

  public BaseSubject(Long id, String title, Teacher teacher) {
    super(id);
    this.title = title;
    this.teacher = teacher;
  }

  @Override public String title() {
    return title;
  }

  @Override public Teacher teacher() {
    return teacher;
  }

  @Override public Subject addId(Long id) {
    return new BaseSubject(id, this.title, this.teacher);
  }
}