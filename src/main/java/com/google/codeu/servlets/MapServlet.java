package com.google.codeu.servlets;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

@WebServlet("/resource-data")
public class MapServlet extends HttpServlet {

        JsonArray placeArray;

         @Override
         public void init() {
          placeArray = new JsonArray();
          Gson gson = new Gson();
          Scanner scanner = new Scanner(getServletContext().getResourceAsStream("/WEB-INF/Women_s_Resource_Map.csv"));
          while(scanner.hasNextLine()) {
           String line = scanner.nextLine();
           String[] cells = line.split(",");

           String name = cells[0];
           String number;
           switch(cells.length){
            case(1):
               placeArray.add(gson.toJsonTree(new Resource(name)));
               break;
            case(2):
              number = cells[1];
              placeArray.add(gson.toJsonTree(new Resource(name,number)));
              break;
            default:
              number = cells[1];
              double lat = Double.parseDouble(cells[2]);
              double lng = Double.parseDouble(cells[3]);
              placeArray.add(gson.toJsonTree(new Resource(name, number, lat, lng)));
           }
         }
         scanner.close();
       }

         @Override
         public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
          response.setContentType("application/json");
          response.getOutputStream().println(placeArray.toString());
         }

         private static class Resource{
          String name;
          String number = "";
          double lat = -200;
          double lng = -200;

          private Resource(String name, String number, double lat, double lng) {
           this.name = name;
           this.number = number;
           this.lat = lat;
           this.lng = lng;
          }
          private Resource(String name, String number) {
           this.name = name;
           this.number = number;
          }
          private Resource(String name) {
           this.name = name;
          }
      }
  }
