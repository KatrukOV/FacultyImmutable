package com.katruk.entity;

public interface Teacher extends Model {

  User user();

  Position position();

  enum Position {
    PROFESSOR,
    ASSOCIATE_PROFESSOR,
    ASSISTANT
  }
}
