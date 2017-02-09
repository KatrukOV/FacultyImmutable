package com.katruk.dao.mysql.user;

import com.katruk.dao.UserDao;
import com.katruk.entity.user.User;
import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

public final class UserDaoCache implements UserDao {

  private final Logger logger;
  private final UserDao userDao;
  private final Map<Long, User> users;
  private static final UserDaoCache userDaoCache = new UserDaoCache(new UsersInMySql());

  public static UserDaoCache getInstance() {
    return userDaoCache;
  }

  private UserDaoCache(UserDao userDao) {
    this.logger = Logger.getLogger(UserDaoCache.class);
    this.userDao = userDao;
    this.users = loadUser();
  }

  private Map<Long, User> loadUser() {
    Collection<User> allUser;
    try {
      allUser = this.userDao.allUser();
    } catch (DaoException e) {
      this.logger.error("Can't load all user.", e);
      return Collections.emptyMap();
    }
    Map<Long, User> userMap = new ConcurrentHashMap<>();
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
        logger.debug(String.format("Find user in cache with id: %d", userId));
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
    logger.debug("Save user in cache.");
    return userInDB;
  }
}