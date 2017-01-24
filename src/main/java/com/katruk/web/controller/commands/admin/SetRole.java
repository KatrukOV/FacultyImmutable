package com.katruk.web.controller.commands.admin;

import static java.util.Objects.nonNull;

import com.katruk.converter.UserConverter;
import com.katruk.entity.Student;
import com.katruk.entity.Teacher;
import com.katruk.entity.User;
import com.katruk.exception.ServiceException;
import com.katruk.service.StudentService;
import com.katruk.service.TeacherService;
import com.katruk.service.UserService;
import com.katruk.service.impl.StudentServiceImpl;
import com.katruk.service.impl.TeacherServiceImpl;
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

public final class SetRole implements Command, PageAttribute {

  private final Logger logger;
  private final UserService userService;
  private final StudentService studentService;
  private final TeacherService teacherService;

  public SetRole() {
    this.logger = Logger.getLogger(SetRole.class);
    this.userService = new UserServiceImpl();
    this.studentService = new StudentServiceImpl();
    this.teacherService = new TeacherServiceImpl();
  }

  @Override
  public String execute(final HttpServletRequest request, final HttpServletResponse response) {
    String page = PageConfig.getInstance().getValue(PageConfig.USERS);
    try {
      User.Role role = User.Role.valueOf(request.getParameter(ROLE));
      Long userId = Long.parseLong(request.getParameter(USER_ID));
      User user = this.userService.getUserById(userId);
      if (nonNull(user.getRole())&&!role.equals(user.getRole())) {
        switch (user.getRole()) {
          case STUDENT: {
            this.studentService.remove(userId);
            break;
          }
          case TEACHER: {
            this.teacherService.remove(userId);
            break;
          }
        }
      }
      user.setRole(role);
      this.userService.save(user);
      switch (role) {
        case STUDENT: {
          final Student student = new Student(user, form, contract);
          student.setUser(user);
          this.studentService.save(student);
          break;
        }
        case TEACHER: {
          final Teacher teacher = new Teacher(user, position);
          teacher.setUser(user);
          this.teacherService.save(teacher);
          break;
        }
      }
      Collection<User> users = this.userService.getAll();
      List userList = Collections.EMPTY_LIST;
      if (!users.isEmpty()) {
        userList = new UserConverter().convertToDto(users);
      }
      request.setAttribute(USER_LIST, userList);
      logger.info(String.format("set role=%s for user= %s", role, user));
    } catch (ServiceException e) {
      page = PageConfig.getInstance().getValue(PageConfig.ERROR_PAGE);
      logger.error("Unable set role for user", e);
    }
    return page;
  }
}