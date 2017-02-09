package com.katruk.entity;

public interface Person extends Model {

  String lastName();

  String name();

  Person addId(Long id);
}
