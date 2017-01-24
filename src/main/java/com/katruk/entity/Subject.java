package com.katruk.entity;

public interface Subject extends Model {

  String title();

  Teacher teacher();

  Subject addId(Long id);
}
