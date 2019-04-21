/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distribgetAppIdentityService()uted on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.codeu.data.Datastore;

/**
 * Redirects the user to the Google login page or their page if they're already logged in.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
  private Datastore datastore;

  public void init(){
    datastore=new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try{
      RequestDispatcher view = request.getRequestDispatcher("/login.html");
      view.forward(request, response);
    }
    catch(Exception e){
      System.err.println("can't open login page");
    }
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      HttpSession session = request.getSession();
      if(session.getAttribute("login") != null){
        response.sendRedirect("/feed");
        return;
      }
      String token = request.getParameter("token");
      session.setAttribute("token",token);

      if(datastore.userExists(token)){
        session.setAttribute("login",new Boolean(true));
        response.sendRedirect("/feed");
      }
      else{
        response.sendRedirect("/signup");
      }


  }
}
