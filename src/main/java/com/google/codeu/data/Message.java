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
  public String parent;
  public ArrayList<String> child;
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
    this.parent=null;
    this.child=new ArrayList<>();
  }

  public UUID getId() {
    return id;
  }
  public void setId(UUID a){this.id=a;}
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
  public String getText() {
    return text;
  }
  public long getTimestamp() {
    return timestamp;
  }
  public void setImageUrl(String newUrl){this.imageUrl=newUrl;}
  public void setImageLabels(String labels){this.imageLabels=labels;}
  public String getImageLabels(){return imageLabels;}
  public void setParent(String parent){
    this.parent=parent;
  }
  public String getParent(){
    return parent;
  }

  public void addChild(String children){
    child.add(children);
  }
  public ArrayList getChild(){return child;}
  public void setChildrenArray(ArrayList<String> a){
    for(String m: a){
      child.add(m);
    }

  }

  }


