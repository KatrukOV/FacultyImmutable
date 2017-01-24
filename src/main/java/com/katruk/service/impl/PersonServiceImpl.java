package com.katruk.service.impl;

import com.katruk.dao.PersonDao;
import com.katruk.dao.mysql.person.PersonDaoMySql;
import com.katruk.entity.Person;
import com.katruk.entity.impl.BasePerson;
import com.katruk.exception.DaoException;
import com.katruk.exception.ServiceException;
import com.katruk.service.PersonService;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.NoSuchElementException;

final class PersonServiceImpl implements PersonService {

  private final Logger logger;
  private final PersonDao personDao;

  PersonServiceImpl() {
    this.logger = Logger.getLogger(PersonServiceImpl.class);
    this.personDao = new PersonDaoMySql();
  }

  @Override
  public BasePerson getPersonById(Long personId) throws ServiceException {
    final BasePerson person;
    try {
      person = this.personDao.getPersonById(personId)
          .orElseThrow(() -> new DaoException("BasePerson not found", new NoSuchElementException()));
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return person;
  }

  @Override
  public BasePerson save(BasePerson person) throws ServiceException {
    try {
      person = this.personDao.save(person);
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return person;
  }

  @Override
  public Collection<Person> getAll() throws ServiceException {
    Collection<Person> persons;
    try {
      persons = this.personDao.getAllPerson();
    } catch (DaoException e) {
      logger.error("err", e);
      throw new ServiceException("err", e);
    }
    return persons;
  }
}