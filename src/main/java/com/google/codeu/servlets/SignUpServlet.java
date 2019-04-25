package com.google.codeu.servlets;

import java.io.IOException;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.google.codeu.data.Datastore;
import javax.servlet.http.Part;
import java.util.Collection;
import com.google.codeu.data.User;
@WebServlet("/signup")
public class SignUpServlet extends HttpServlet {
  private Datastore datastore;

  public void init(){
    datastore=new Datastore();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
    try{
      RequestDispatcher view = request.getRequestDispatcher("/signup.html");
      view.forward(request, response);
    }
    catch(Exception e){
      System.err.println("can't open signup page");
    }
  }
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    HttpSession session = request.getSession();
    User user = new User(request.getParameter("userName"),(String)session.getAttribute("email")
    ,request.getParameter("gender"),request.getParameter("birthday"),request.getParameter("description"));

    if(session.getAttribute("token") == null){
      response.sendRedirect("/login");
    }
    else{
      datastore.storeUser(user,(String)session.getAttribute("token"));
      session.setAttribute("login",true);
      response.sendRedirect("/");
    }

  }
}
