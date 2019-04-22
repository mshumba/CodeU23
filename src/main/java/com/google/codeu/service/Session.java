package com.google.codeu.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;


public class Session{
  private Datastore datastore = new Datastore();

  public boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException{
    if(request.getSession().getAttribute("login") != null && ((Boolean)request.getSession().getAttribute("login")).booleanValue()){
      return true;
    }
    else{
      return false;
    }
  }
  public User getCurrentUser(HttpServletRequest request, HttpServletResponse response) throws IOException{
      return datastore.getCurrentUser(request.getSession().getAttribute("token").toString());
  }
}
