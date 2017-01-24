package com.katruk.entity.impl;

import com.katruk.entity.Model;

import java.io.Serializable;

abstract class BaseModel implements Serializable, Model {

  private final Long id;

  BaseModel() {
    this.id = null;
  }

  BaseModel(Long id) {
    this.id = id;
  }

  @Override public Long id() {
    return this.id;
  }
}
