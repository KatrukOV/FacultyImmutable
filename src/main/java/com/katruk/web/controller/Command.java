package com.katruk.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

  String execute(final HttpServletRequest request, final HttpServletResponse response);
}
