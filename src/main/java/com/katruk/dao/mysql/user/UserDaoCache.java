package com.katruk.dao.mysql.user;

import com.katruk.dao.UserDao;
import com.katruk.entity.User;
import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public final class UserDaoCache implements UserDao {

  private final Logger logger;
  private final UserDao userDao;
  private final Map<Long, User> users;

  public UserDaoCache(UserDao userDao) throws DaoException {
    this.logger = Logger.getLogger(UserDaoCache.class);
    this.userDao = userDao;
    this.users = loadUser();
  }

  private Map<Long, User> loadUser() throws DaoException {
    Collection<User> allUser = this.userDao.allUser();
    Map<Long, User> userMap = new HashMap<>();
    for (User user : allUser) {
      userMap.put(user.id(), user);
    }
    return userMap;
  }

  @Override
  public Collection<User> allUser() throws DaoException {
    return this.users.values();
  }

  @Override
  public User findUserByUsername(final String username) throws DaoException {
    for (User user : this.users.values()) {
      if (user.username().equals(username)) {
        return user;
      }
    }
    String message = String.format("User not found by username: %s.", username);
    logger.info(message);
    throw new DaoException(message, new NoSuchElementException());
  }

  @Override
  public User findUserById(final Long userId) throws DaoException {
    for (User user : this.users.values()) {
      if (user.id().equals(userId)) {
        return user;
      }
    }
    String message = String.format("User not found by id: %d.", userId);
    logger.info(message);
    throw new DaoException(message, new NoSuchElementException());
  }

  @Override
  public User save(final User user) throws DaoException {
    User userInDB = this.userDao.save(user);
    this.users.remove(userInDB.id());
    this.users.put(userInDB.id(), userInDB);
    return userInDB;
  }
}