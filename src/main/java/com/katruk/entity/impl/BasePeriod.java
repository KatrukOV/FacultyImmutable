package com.katruk.entity.impl;

import com.katruk.entity.Model;
import com.katruk.entity.Period;

import java.sql.Date;

public final class BasePeriod extends BaseModel implements Period {

  private final Status status;
  private final Date date;

  public BasePeriod(Status status, Date date) {
    super();
    this.status = status;
    this.date = date;
  }

  public BasePeriod(Long id, Status status, Date date) {
    super(id);
    this.status = status;
    this.date = date;
  }

  @Override
  public Status status() {
    return status;
  }

  @Override
  public Date date() {
    return date;
  }

  @Override
  public Period addId(Long id) {
    return new BasePeriod(id, this.status, this.date);
  }
}