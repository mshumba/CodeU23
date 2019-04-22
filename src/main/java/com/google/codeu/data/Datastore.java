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
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.data;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.FetchOptions;
 import com.google.appengine.api.datastore.KeyFactory.Builder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/** Provides access to the data stored in Datastore. */
public class Datastore {

  private DatastoreService datastore;

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /** Stores the Message in Datastore. */
  public void storeMessage(Message message) {

    Entity messageEntity = new Entity("Message", message.getId().toString());
    messageEntity.setProperty("user", message.getUser());
    messageEntity.setProperty("text", message.getText());
    messageEntity.setProperty("timestamp", message.getTimestamp());

    if(message.getImageUrl() != null) {
      messageEntity.setProperty("imageUrl", message.getImageUrl());
    }
    if(message.getImageUrl() != null) {
      messageEntity.setProperty("imageLabels", message.getImageLabels());
    }
    messageEntity.setProperty("parent",message.getParent());
    ArrayList<String> forChildren=new ArrayList<>();
    for(String s: message.getChild()){
      forChildren.add(s);
    }

    System.out.println("THe size of responses in "+message.getChild().size());
    messageEntity.setProperty("child", forChildren);

    datastore.put(messageEntity);
  }

  public int getTotalMessageCount(){
    Query query = new Query("Message");
    PreparedQuery results = datastore.prepare(query);
    return results.countEntities(FetchOptions.Builder.withLimit(1000));
  }

  /**
   * Gets messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has never posted a
   *     message. List is sorted by time descending.
   */
  public List<Message> getMessages(String user) {
    List<Message> messages = new ArrayList<>();

    Query query =
        new Query("Message")
            .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    getMessagesHelper(false, results,messages, user);

    return messages;
  }

  public List<Message> getAllMessages(){
    List<Message> messages = new ArrayList<>();

    Query query = new Query("Message")
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);
    getMessagesHelper(true,results,messages,"");

    return messages;
  }
  private void getMessagesHelper(boolean all, PreparedQuery results,List<Message> messages,String user){

    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        if(all){
          user = (String) entity.getProperty("user");
        }
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");
        String imageUrl = (String) entity.getProperty("imageUrl");
        String imageLabels= (String) entity.getProperty("imageLabels");
        Message message = new Message(id, user, text, timestamp,imageUrl,imageLabels);
        String messageParent=(String)entity.getProperty("parent");
        message.setId(id);
        message.setParent(messageParent);
        ArrayList<String> responses=(ArrayList<String>)entity.getProperty("child");
        if(responses!=null){
          message.setChildrenArray(responses);

        }
        //System.out.println(responses);

        messages.add(message);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
  }
  public List<UserMarker> getMarkers() {
    List<UserMarker> markers = new ArrayList<>();

    Query query = new Query("UserMarker");
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
     try {
      double lat = (double) entity.getProperty("lat");
      double lng = (double) entity.getProperty("lng");
      String content = (String) entity.getProperty("content");
      System.out.println(content);
      UserMarker marker = new UserMarker(lat, lng, content);
      markers.add(marker);
     } catch (Exception e) {
      System.err.println("Error reading marker.");
      System.err.println(entity.toString());
      e.printStackTrace();
     }
    }
    return markers;
  }

  public void storeMarker(UserMarker marker) {
    Entity markerEntity = new Entity("UserMarker");
    markerEntity.setProperty("lat", marker.getLat());
    markerEntity.setProperty("lng", marker.getLng());
    markerEntity.setProperty("content", marker.getContent());
    datastore.put(markerEntity);
  }

  //for log in
  //return user identified by the user name. User name is unique throught out the whole site
  public boolean checkUserNameExist(String userName){
    Query q = new Query("User").setFilter(new Query.FilterPredicate("userName", FilterOperator.EQUAL, userName));
    PreparedQuery results = datastore.prepare(q);
    if(results.countEntities(FetchOptions.Builder.withLimit(1000)) > 0){
      return true;
    }
    else{
      return false;
    }
  }

  public List<User> findUserBy(String field, String value){
    Query q = new Query("User").setFilter(new Query.FilterPredicate(field, FilterOperator.EQUAL, value));
    PreparedQuery results = datastore.prepare(q);
    ArrayList<User> result = new ArrayList<>();
    try{
      for(Entity entity : results.asIterable()){
        String userName = (String) entity.getProperty("userName");
        String gender = (String) entity.getProperty("gender");
        String birthday = (String) entity.getProperty("birthday");
        String description = (String) entity.getProperty("description");
        String email = (String) entity.getProperty("email");
        result.add(new User(userName, email,gender, birthday, description));
      }
    }
    catch (Exception e) {
      System.err.println("Error finding user by field.");
      e.printStackTrace();
    }
    return result;
  }
  public void storeUser(User user,String token){
    Entity e  = new Entity("User",token);
    e.setProperty("email",user.getEmail());
    e.setProperty("userName",user.getUserName());
    e.setProperty("birthday",user.getBirthday());
    e.setProperty("description",user.getDescription());
    e.setProperty("gender",user.getGender());
    datastore.put(e);
  }
  public boolean userExists(String key){
    try{
      datastore.get(new Builder("User",key).getKey());
      return true;
    }
    catch(Exception e){
      return false;
    }
  }
  public User getCurrentUser(String key){
    try{
      Entity entity = datastore.get(new Builder("User",key).getKey());
      String userName = (String) entity.getProperty("userName");
      String gender = (String) entity.getProperty("gender");
      String birthday = (String) entity.getProperty("birthday");
      String description = (String) entity.getProperty("description");
      String email = (String) entity.getProperty("email");
      return new User(userName, email,gender, birthday, description);
    }
    catch(Exception e){
      System.err.println(e);
      return null;
    }

  }
}
