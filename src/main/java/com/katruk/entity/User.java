package com.katruk.entity;

public final class User extends Model {

  private final Person person;
  private final String username;
  private final String password;
  private final Role role;

  public User(Long id) {
    super(id);
    this.person = null;
    this.username = null;
    this.password = null;
    this.role = null;
  }

  public User(Person person, String username, String password) {
    super();
    this.person = person;
    this.username = username;
    this.password = password;
    this.role = null;
  }

  public User(Person person, String username, String password, Role role) {
    super();
    this.person = person;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public User(Long id, Person person, String username, String password, Role role) {
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
  public enum Role {
    STUDENT,
    TEACHER,
    ADMIN
  }

  public Person person() {
    return person;
  }

  public String username() {
    return username;
  }

  public String password() {
    return password;
  }

  public Role role() {
    return role;
  }

  public User changePerson(Person person) {
    return new User(this.id(), person, this.username, this.password, this.role);
  }
}