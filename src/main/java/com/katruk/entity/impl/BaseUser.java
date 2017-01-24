package com.katruk.entity.impl;

import com.katruk.entity.Model;
import com.katruk.entity.Person;
import com.katruk.entity.User;


public final class BaseUser extends BaseModel implements User {

  private final Person person;
  private final String username;
  private final String password;
  private final Role role;

  public BaseUser(Long id) {
    super(id);
    this.person = null;
    this.username = null;
    this.password = null;
    this.role = null;
  }

  public BaseUser(Person person, String username, String password) {
    super();
    this.person = person;
    this.username = username;
    this.password = password;
    this.role = null;
  }

//  public BaseUser(Person person, String username, String password, Role role) {
//    super();
//    this.person = person;
//    this.username = username;
//    this.password = password;
//    this.role = role;
//  }

  public BaseUser(Long id, Person person, String username, String password, Role role) {
    super(id);
    this.person = person;
    this.username = username;
    this.password = password;
    this.role = role;
  }
  /*
    @Override
    public boolean equals(Object o) {
      if (this == o) {
        return true;
      }
      if (o == null || getClass() != o.getClass()) {
        return false;
      }
      User user = (User) o;
      return Objects.equals(person, user.person) &&
             Objects.equals(username, user.username) &&
             Objects.equals(password, user.password) &&
             role == user.role;
    }

    @Override
    public int hashCode() {
      return Objects.hash(person, username, password, role);
    }
  */

  @Override
  public Person person() {
    return person;
  }

  @Override
  public String username() {
    return username;
  }

  @Override
  public String password() {
    return password;
  }

  @Override
  public Role role() {
    return role;
  }

  @Override
  public User changePerson(Person person) {
    return new BaseUser(this.id(), person, this.username, this.password, this.role);
  }
}