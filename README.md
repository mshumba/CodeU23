# Spring 2019 CodeU Project
## Usage of Datastore
When retrieving information about usesr:

1. Prepare:
```java
import com.google.codeu.service.Session;
Session session = new Session();
```
2. Check if the user is logged in. Returns boolean value of whether is logged in
```java
session.isLoggedIn(request,response);
```
 * If not logged in,
 ```java
response.sendRedirect("/login");
```
3. If logged in,query the datastore
```java
 \\if you want to look up users with the key-value pair property
  List<User> users = datastore.findUserBy(key,value);
 \\ if you want to find the user that is currently logged in.
  User user = session.getCurrentUser(request,response);
```
## Current User data structure
```java
  private String userName;
  private String gender;
  private String email;
  private String birthday;
  private String description;
```
If you want to add other data field, please update the findUserBy and getCurrentUser method. It should be pretty straight forward how to update it.
