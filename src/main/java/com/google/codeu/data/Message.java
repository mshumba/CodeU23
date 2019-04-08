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


import java.util.ArrayList;
import java.util.UUID;

/** A single message posted by a user. */
public class Message {

  private UUID id;
  private String user;
  private String text;
  private String imageUrl;
  private String imageLabels;
  private long timestamp;
  private ArrayList<SubMessage> children = new ArrayList<SubMessage>();
  /**
   * Constructs a new {@link Message} posted by {@code user} with {@code text} content. Generates a
   * random ID and uses the current system time for the creation time.
   */

  public Message(String user, String text) {
    this(UUID.randomUUID(), user, text, System.currentTimeMillis(),null,null);
  }
  /*Please dont use this constructor it is bad.*/
  public Message(){
    this("TestUser","Hey");
  }


  public Message(UUID id, String user, String text, long timestamp, String imageUrl, String imageLabels) {
    this.id = id;
    this.user = user;
    this.text = text;
    this.timestamp = timestamp;
    this.imageUrl=imageUrl;
    this.imageLabels=imageLabels;
  }

  public UUID getId() {
    return id;
  }
  @Override
  public String toString(){
    String ret="";
    ret+="User: "+user+ " text: "+ text;
    return ret;
  }
  public String getImageUrl(){return imageUrl;}
  public String getUser() {
    return user;
  }
  public void addChild(SubMessage child){
    children.add(child);
  }
  public String getText() {
    return text;
  }
  public long getTimestamp() {
    return timestamp;
  }
  public void setImageUrl(String newUrl){this.imageUrl=newUrl;}
  public void setImageLabels(String labels){this.imageLabels=labels;}
  public String getImageLabels(){return imageLabels;}

}
