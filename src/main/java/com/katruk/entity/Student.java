package com.katruk.entity;

public interface Student extends Model {

  User user();

  Form form();

  Contract contract();

  enum Form {
    FULL_TAME,
    EXTRAMURAL,
    EVENING,
    DISTANCE
  }

  enum Contract {
    STATE_ORDER,
    CONTRACT
  }
}
