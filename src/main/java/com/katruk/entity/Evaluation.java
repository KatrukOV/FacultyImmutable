package com.katruk.entity;

public interface Evaluation extends Model {

  Subject subject();

  Student student();

  Status status();

  Rating rating();

  String feedback();

  Evaluation addId(Long id);

  enum Status {
    DECLARED,
    CONFIRMED,
    REJECTED,
    DELETED
  }

  enum Rating {
    A, B, C, D, E, Fx, F
  }
}
