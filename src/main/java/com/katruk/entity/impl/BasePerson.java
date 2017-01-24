package com.katruk.entity.impl;

import com.katruk.entity.Model;
import com.katruk.entity.Person;

public final class BasePerson extends BaseModel implements Person {

  private final String lastName;
  private final String name;
  private final String patronymic;

//  public BasePerson(Long id) {
//    super(id);
//    this.lastName = null;
//    this.name = null;
//    this.patronymic = null;
//  }

  public BasePerson(String lastName, String name, String patronymic) {
    super();
    this.lastName = lastName;
    this.name = name;
    this.patronymic = patronymic;
  }

  public BasePerson(Long id, String lastName, String name, String patronymic) {
    super(id);
    this.lastName = lastName;
    this.name = name;
    this.patronymic = patronymic;
  }

  @Override
  public String lastName() {
    return lastName;
  }

  @Override
  public String name() {
    return name;
  }

  @Override
  public String patronymic() {
    return patronymic;
  }

  @Override
  public Person addId(Long id) {
    return new BasePerson(id, this.lastName, this.name, this.patronymic);
  }
}