package controllers;

import java.io.File;
import java.util.Collection;

import com.google.common.base.Optional;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.User;
import utils.JSONSerializer;

public class Main
{
  pacemakerAPI paceApi;

  @Command(description="Create a new User")
  public void createUser (@Param(name="first name") String firstName, @Param(name="last name") String lastName, 
      @Param(name="email")      String email,     @Param(name="password")  String password)
  {
    paceApi.createUser(firstName, lastName, email, password);
  }

  @Command(description="Get all users details")
  public void listUsers ()
  {
    Collection<User> users = paceApi.getUsers();
    System.out.println(users);
  }

  @Command(description="Get user details")
  public void listUser (@Param(name="email") String email)
  {
    User user = paceApi.getUserByEmail(email);
    System.out.println(user);
  }
  
  @Command(description="Get user by id")
  public void listUser (@Param(name="id") Long id)
  {
    User user = paceApi.getUserById(id);
    System.out.println(user.toString());
  }
  
  @Command(description="Add Activity")
  public void addActivity (@Param(name="user-id") Long userId, @Param(name="type") String type, @Param(name="location") String location, 
      @Param(name="distance") double distance)
  {
    paceApi.addActivity(userId, type, location, distance);
  }
  
  @Command(description="Get all users details")
  public void listActivities (@Param(name="user-id") Long userId, @Param(name="SortBy") String sortBy)
  {
    Collection<User> users = paceApi.getSortedActivities(userId,  "");
    System.out.println(users);
  }
  
  @Command(description="Add Location")
  public void addLocation (@Param(name="activity-id") Long activityId, @Param(name="latitide") double latitude, 
      @Param(name="longitude") double longitude)
  {
    paceApi.addLocation(activityId, latitude, longitude);
  }
  
  @Command(description="Delete User")
  public void deleteUser (@Param(name="id") long id)
  {
    System.out.println("am I here?");
    paceApi.deleteUser(id);
  }
  
  @Command(description="Delete User")
  public void deleteUserbyEmail (@Param(name="email") String email)
  {
    System.out.println("am I here? email");
    Optional<User> user = Optional.fromNullable(paceApi.getUserByEmail(email));
    if (user.isPresent())
    {
      System.out.println("user is present"+user.get().id);
      paceApi.deleteUser(user.get().id);
    }
  }

  public static void main(String[] args) 
  {
    
    Main main = new Main();
    
    
    try {
    File datastore = new File("datastore.xml");
    main.paceApi = new pacemakerAPI(new JSONSerializer(datastore));
    
    if(datastore.isFile()) 
    {
      main.paceApi.load();
        
    }
    
    main.paceApi.printSize();
    
    Shell shell = ShellFactory.createConsoleShell("pc", "Welcome to pcemaker-console - ?help for instructions", main);
    shell.commandLoop(); 
    
    main.paceApi.store();
    
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
}
