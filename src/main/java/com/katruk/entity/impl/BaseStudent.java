package com.katruk.entity.impl;

import com.katruk.entity.Model;
import com.katruk.entity.Student;
import com.katruk.entity.User;

public final class BaseStudent extends BaseModel implements Student {

  private final User user;
  private final Form form;
  private final Contract contract;

  public BaseStudent(Long id) {
    super(id);
    this.user = null;
    this.form = null;
    this.contract = null;
  }
//
//  public BaseStudent(User user) {
//    super();
//    this.user = user;
//    this.form = null;
//    this.contract = null;
//  }
//
//  public BaseStudent(User user, Form form) {
//    super();
//    this.user = user;
//    this.form = form;
//    this.contract = null;
//  }
//
//  public BaseStudent(User user, Contract contract) {
//    super();
//    this.user = user;
//    this.form = null;
//    this.contract = contract;
//  }
//
//  public BaseStudent(User user, Form form, Contract contract) {
//    super();
//    this.user = user;
//    this.form = form;
//    this.contract = contract;
//  }

  public BaseStudent(Long id, User user, Form form, Contract contract) {
    super(id);
    this.user = user;
    this.form = form;
    this.contract = contract;
  }

  @Override public User user() {
    return user;
  }

  @Override public Form form() {
    return form;
  }

  @Override public Contract contract() {
    return contract;
  }
}
