package com.katruk.entity;

public interface Person extends Model {

  String lastName();

  String name();

  String patronymic();

  Person addId(Long id);
}
