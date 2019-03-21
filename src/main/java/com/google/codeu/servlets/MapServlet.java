//package com.google.codeu.servlets;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
/*import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;*/

public class MapServlet /*extends HttpServlet*/{
  public static void main(String[] args) throws Exception{
    Scanner scanner = new Scanner(new File("Women_s_Resource_Map.csv"));
    while(scanner.hasNextLine()) {
      String line = scanner.nextLine();
      if(line.trim().equals("")){
        continue;
      }
      String[] cells = line.split(",");
      System.out.println(line+ "New line");

      String name = cells[0];
      System.out.println("name: " + name);

      String number = cells[1];
      System.out.println("number: " + number);
      String url = cells[2];
      String description = cells[3];
      //double lat = Double.parseDouble(cells[4]);
      //double lng = Double.parseDouble(cells[5]);
      System.out.println("lat: ");
      System.out.println("lng: ");
      System.out.println();
    }
    scanner.close();

  }
}
