package com.katruk.entity.impl;

import com.katruk.entity.Evaluation;
import com.katruk.entity.Model;
import com.katruk.entity.Student;
import com.katruk.entity.Subject;

public final class BaseEvaluation extends BaseModel implements Evaluation {

  private final Subject subject;
  private final Student student;
  private final Status status;
  private final Rating rating;
  private final String feedback;

//  public BaseEvaluation(Subject subject, Student student, Status status) {
//    super();
//    this.subject = subject;
//    this.student = student;
//    this.status = status;
//    this.rating = null;
//    this.feedback = null;
//  }
//
//  public BaseEvaluation(Subject subject, Student student, Status status, Rating rating,
//                        String feedback) {
//    super();
//    this.subject = subject;
//    this.student = student;
//    this.status = status;
//    this.rating = rating;
//    this.feedback = feedback;
//  }

  public BaseEvaluation(Long id, Subject subject, Student student, Status status, Rating rating,
                        String feedback) {
    super(id);
    this.subject = subject;
    this.student = student;
    this.status = status;
    this.rating = rating;
    this.feedback = feedback;
  }

  @Override
  public Subject subject() {
    return subject;
  }

  @Override
  public Student student() {
    return student;
  }

  @Override
  public Status status() {
    return status;
  }

  @Override
  public Rating rating() {
    return rating;
  }

  @Override
  public String feedback() {
    return feedback;
  }

  @Override
  public Evaluation addId(Long id) {
    return new BaseEvaluation(id, this.subject, this.student, this.status, this.rating,
                              this.feedback);
  }
}