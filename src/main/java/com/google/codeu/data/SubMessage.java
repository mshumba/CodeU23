package com.google.codeu.data;

import java.util.UUID;

public class SubMessage extends Message {
    private Message parent;
    private UUID id;
    private String user;
    private String text;
    private String imageUrl;
    private String imageLabels;
    private long timestamp;

    public SubMessage(String user, String text, Message sub) {
        this(sub,UUID.randomUUID(), user, text, System.currentTimeMillis(),null,null);
    }


    public SubMessage(Message sub,UUID id, String user, String text, long timestamp, String imageUrl, String imageLabels) {
        this.parent=sub;
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


    public com.google.codeu.data.Message getSub() {
        return parent;
    }
    public void setSub(Message newMessage) {
        newMessage.addChild(this);
        parent = newMessage;
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
