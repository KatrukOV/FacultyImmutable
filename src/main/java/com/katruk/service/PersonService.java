package com.katruk.service;

import com.katruk.entity.Person;
import com.katruk.entity.impl.BasePerson;
import com.katruk.exception.ServiceException;

import java.util.Collection;

public interface PersonService {

  BasePerson getPersonById(final Long personId) throws ServiceException;

  BasePerson save(final BasePerson person) throws ServiceException;

  Collection<Person> getAll() throws ServiceException;
}
