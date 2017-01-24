package com.katruk.entity;

import java.sql.Date;

public interface Period {

  Status status();

  Date date();

  Period addId(Long id);

  enum Status {
    DISTRIBUTION,
    LEARNING
  }
}
