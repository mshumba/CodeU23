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

package com.google.codeu.servlets;


import com.google.appengine.api.blobstore.*;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.fasterxml.jackson.core.JsonFactory;
import com.google.cloud.vision.v1.*;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;
import com.google.protobuf.ByteString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.protobuf.Type;
import org.jsoup.Jsoup;
import java.io.*;

import java.util.stream.Collectors;


import org.jsoup.safety.Whitelist;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/** Handles fetching and saving {@link Message} instances. */
@WebServlet("/messages")
public class MessageServlet extends HttpServlet {

  private Datastore datastore;
  public void init(){
    datastore=new Datastore();
  }

  /**
   * Responds with a JSON representation of {@link Message} data for a specific user. Responds with
   * an empty array if the user is not provided.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");
    if (user == null || user.equals("")) {
      // Request is invalid, return empty array
      response.getWriter().println("[]");
      return;
    }

    List<Message> messages = datastore.getMessages(user);
    Gson gson = new Gson();
    String json = gson.toJson(messages);

    response.getWriter().println(json);
  }

  /**
   * Stores a new {@link Message}.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String user = userService.getCurrentUser().getEmail();



    String userText = Jsoup.clean(request.getParameter("text"), Whitelist.none());

    String regex = "(https?://\\S+\\.(png|jpg))";
    String replacement = "<img src=\"$1\" />";
    String textWithImagesReplaced = userText.replaceAll(regex, replacement);

    Message message = new Message(user, textWithImagesReplaced);

    BlobstoreService blobstoreService;
    Map<String, List<BlobKey>> blobs;
    List<BlobKey> blobKeys=null;
    if(request.getQueryString()==null) {
       blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
       blobs = blobstoreService.getUploads(request);
       blobs.get("image");
    }
    else{
      message.setParent(request.getQueryString().substring(7));
      List<Message> searching =datastore.getAllMessages();
      for(Message m: searching){
        if(m.getId().toString().equals(message.getParent())){
         // System.out.println(m.getText());
          m.addChild(message.getText());
          datastore.storeMessage(m);
        }
      }

      datastore.storeMessage(message);
      response.sendRedirect("/user-page.html?user=" + user);
      return;
    }
    if(blobKeys != null && !blobKeys.isEmpty()) {
      BlobKey blobKey = blobKeys.get(0);

      final BlobInfo blobInfo = new BlobInfoFactory().loadBlobInfo(blobKey);
      long size = blobInfo.getSize();
      if(size > 0){
        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(blobKey);
        String imageUrl = imagesService.getServingUrl(options);
        message.setImageUrl(imageUrl);
      } else {
        blobstoreService.delete(blobKey);
      }

      byte[] blobBytes = getBlobBytes(blobstoreService, blobKey);
      String imageLabels = getImageLabels(blobBytes);
      message.setImageLabels(imageLabels);
      datastore.storeMessage(message);
      response.sendRedirect("/user-page.html?user=" + user);
      return;
    }

    datastore.storeMessage(message);
    response.sendRedirect("/user-page.html?user=" + user);
  }
  private byte[] getBlobBytes(BlobstoreService blobstoreService, BlobKey blobKey)
          throws IOException {

    ByteArrayOutputStream outputBytes = new ByteArrayOutputStream();

    int fetchSize = BlobstoreService.MAX_BLOB_FETCH_SIZE;

    long currentByteIndex = 0;
    boolean continueReading = true;
    while (continueReading) {
      // end index is inclusive, so we have to subtract 1 to get fetchSize bytes
      byte[] b =
              blobstoreService.fetchData(blobKey, currentByteIndex, currentByteIndex + fetchSize - 1);
      outputBytes.write(b);

      // if we read fewer bytes than we requested, then we reached the end
      if (b.length < fetchSize) {
        continueReading = false;
      }

      currentByteIndex += fetchSize;
    }

    return outputBytes.toByteArray();
  }
  private String getImageLabels(byte[] imgBytes) throws IOException {
    ByteString byteString = ByteString.copyFrom(imgBytes);
    com.google.cloud.vision.v1.Image image=com.google.cloud.vision.v1.Image.newBuilder().setContent(byteString).build();


    Feature feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
    AnnotateImageRequest request =
            AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();
    List<AnnotateImageRequest> requests = new ArrayList<>();
    requests.add(request);

    ImageAnnotatorClient client = ImageAnnotatorClient.create();
    BatchAnnotateImagesResponse batchResponse = client.batchAnnotateImages(requests);
    client.close();
    List<AnnotateImageResponse> imageResponses = batchResponse.getResponsesList();
    AnnotateImageResponse imageResponse = imageResponses.get(0);

    if (imageResponse.hasError()) {
      System.err.println("Error getting image labels: " + imageResponse.getError().getMessage());
      return null;
    }

    String labelsString = imageResponse.getLabelAnnotationsList().stream()
            .map(EntityAnnotation::getDescription)
            .collect(Collectors.joining(", "));

    return labelsString;
  }
}