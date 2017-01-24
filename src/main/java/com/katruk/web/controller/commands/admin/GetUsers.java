package com.katruk.web.controller.commands.admin;

import com.katruk.converter.UserConverter;
import com.katruk.entity.User;
import com.katruk.exception.ServiceException;
import com.katruk.service.UserService;
import com.katruk.service.impl.UserServiceImpl;
import com.katruk.util.PageConfig;
import com.katruk.web.PageAttribute;
import com.katruk.web.controller.Command;

import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class GetUsers implements Command, PageAttribute {

  private final Logger logger;
  private final UserService userService;

  public GetUsers() {
    this.userService = new UserServiceImpl();
    this.logger = Logger.getLogger(GetUsers.class);
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.USERS);
    try {
      Collection<User> users = this.userService.getAll();
      List userList = Collections.EMPTY_LIST;
      if (!users.isEmpty()) {
        userList = new UserConverter().convertToDto(users);
      }
      request.setAttribute(USER_LIST, userList);
      logger.info(String.format("get all users = %d", userList.size()));
    } catch (ServiceException e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable get all users", e);
    }
    return page;
  }
}
