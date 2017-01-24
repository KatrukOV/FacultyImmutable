package com.katruk.dao.mysql.person;

import com.katruk.dao.PersonDao;
import com.katruk.entity.Person;
import com.katruk.entity.Student;
import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public final class PersonDaoCache implements PersonDao {

  private final Logger logger;
  private final PersonDao personDao;
  private final Map<Long, Person> persons;

  public PersonDaoCache(PersonDao personDao) throws DaoException {
    this.logger = Logger.getLogger(PersonDaoCache.class);
    this.personDao = personDao;
    this.persons = loadPerson();
  }

  private Map<Long, Person> loadPerson() throws DaoException {
    Collection<Person> allPerson = this.personDao.getAllPerson();
    Map<Long, Person> personMap = new HashMap<>();
    for (Person person : allPerson) {
      personMap.put(person.id(), person);
    }
    return personMap;
  }

  @Override
  public Person getPersonById(Long personId) throws DaoException {
    for (Person person : this.persons.values()) {
      if (person.id().equals(personId)) {
        return person;
      }
    }
    String message = String.format("Person not found by id: %d.", personId);
    logger.info(message);
    throw new DaoException(message, new NoSuchElementException());
  }

  @Override
  public Collection<Person> getAllPerson() throws DaoException {
    return this.persons.values();
  }

  @Override
  public Person save(Person person) throws DaoException {
    Person personInDB = this.personDao.save(person);
    this.persons.remove(personInDB.id());
    this.persons.put(personInDB.id(), personInDB);
    return personInDB;
  }
}