package com.katruk.dao.mysql.period;

import com.katruk.dao.PeriodDao;
import com.katruk.exception.DaoException;

import org.apache.log4j.Logger;

public final class PeriodDaoCache implements PeriodDao {

  private final Logger logger;
  private final PeriodDao periodDao;
  private Period lastPeriod;

  public PeriodDaoCache(PeriodDao periodDao) throws DaoException {
    this.logger = Logger.getLogger(PeriodDaoCache.class);
    this.periodDao = periodDao;
    this.lastPeriod = loadLastPeriod();
  }

  private Period loadLastPeriod() throws DaoException {
    return this.periodDao.getLastPeriod();
  }

  @Override
  public Period getLastPeriod() throws DaoException {
    logger.debug(String.format("Get last period: %s.", this.lastPeriod));
    return this.lastPeriod;
  }

  @Override
  public Period save(Period period) throws DaoException {
    this.lastPeriod = period;
    this.periodDao.save(period);
    logger.debug(String.format("Update last period to: %s.", period));
    return period;
  }
}