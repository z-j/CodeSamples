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
import utils.Print;
import utils.XMLSerializer;

public class Main
{
  pacemakerAPI paceApi;

  @Command(description = "Create a new User")
  public void createUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
      @Param(name = "email") String email, @Param(name = "password") String password)
  {
    paceApi.createUser(firstName, lastName, email, password);
  }

  @Command(description = "Get all users details")
  public void listUsers()
  {
    Collection<User> users = paceApi.getUsers();
    // System.out.println(users);
    Print.printUsers(users);
  }

  @Command(description = "Get user details")
  public void listUser(@Param(name = "email") String email)
  {
    User user = paceApi.getUserByEmail(email);
    System.out.println(user);
  }

  @Command(description = "Get user by id")
  public void listUser(@Param(name = "id") Long id)
  {
    User user = paceApi.getUserById(id);
    System.out.println(user.toString());
  }

  @Command(description = "Add Activity")
  public void addActivity(@Param(name = "user-id") Long userId, @Param(name = "type") String type,
      @Param(name = "location") String location, @Param(name = "distance") double distance)
  {
    paceApi.addActivity(userId, type, location, distance);
  }

  @Command(description = "Get all users details")
  public void listActivities(@Param(name = "user-id") Long userId, @Param(name = "SortBy") String sortBy)
  {
    User user = paceApi.getSortedActivities(userId, "");
    if(user != null && user.activities != null && user.activities.size() > 0) {
      Print.printSortedActivities(user);
    } else { 
      Print.printNoData("Either No User Found for id:"+userId+" or no activities for this user.");
    }

  }

  @Command(description = "Add Location")
  public void addLocation(@Param(name = "activity-id") Long activityId, @Param(name = "latitide") double latitude,
      @Param(name = "longitude") double longitude)
  {
    paceApi.addLocation(activityId, latitude, longitude);
  }

  @Command(description = "Delete User")
  public void deleteUser(@Param(name = "id") long id)
  {
    paceApi.deleteUser(id);
  }

  @Command(description = "Delete User")
  public void deleteUserbyEmail(@Param(name = "email") String email)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUserByEmail(email));
    if (user.isPresent())
    {
      System.out.println("user is present" + user.get().id);
      paceApi.deleteUser(user.get().id);
    }
  }

  @Command(description = "Load")
  public void load()
  {
    try
    {
      paceApi.load();
      Print.printNoData("Data loaded successfully.");
    }
    catch (Exception e)
    {

    }
  }

  @Command(description = "Store")
  public void store()
  {
    try
    {
      paceApi.store();
      Print.printNoData("Data stored successfully.");
    }
    catch (Exception e)
    {

    }
  }

  /**
   * in memory objects will have snapshot of latest data. because data gets
   * loaded when shell starts, and if user adds new data then in memory objects
   * will have both old and new data. Delete the existing file, initialize new
   * serializer, save the data and return
   * 
   * @param type
   */
  @Command(description = "Change File Format")
  public void changeFileFormat(@Param(name = "type") String type)
  {

    if ("xml".equalsIgnoreCase(type))
    {
      deleteFile();
      System.out.println("change file format xml");
      File datastore = new File("datastore.xml");
      this.paceApi.serializer = (new XMLSerializer(datastore));

    }
    else
      if ("json".equalsIgnoreCase(type))
      {
        deleteFile();
        File datastore = new File("datastore.json");
        this.paceApi.serializer = (new JSONSerializer(datastore));
      }
      else
      {
        Print.printNoData("Not valid file format. Valid file formats are 'xml' or 'json'.");
      }
  }

  public static void main(String[] args)
  {
    Main main = new Main();
    try
    {
      main.getAppropriateSerializer();
      main.paceApi.printSize();
      Shell shell = ShellFactory.createConsoleShell("pc", "Welcome to pcemaker-console - ?help for instructions", main);
      shell.commandLoop();
      main.paceApi.store();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  /**
   * get XML serializer if .xml file exists. get JSON serializer if .json file
   * exists. we need the function to remember what user entered last time when
   * change file format command was issued. if it is the first time, by default
   * the format is .xml
   */
  public void getAppropriateSerializer() throws Exception
  {

    File xmlFile = new File("datastore.xml");
    File jsonFile = new File("datastore.json");

    if (xmlFile.exists() && xmlFile.isFile())
    {
      this.paceApi = new pacemakerAPI(new XMLSerializer(xmlFile));
      this.paceApi.load();
    }
    else
      if (jsonFile.exists() && jsonFile.isFile())
      {
        this.paceApi = new pacemakerAPI(new JSONSerializer(jsonFile));
        this.paceApi.load();
      }
      else
      {
        this.paceApi = new pacemakerAPI(new XMLSerializer(xmlFile));
      }
  }

  /**
   * 
   * @throws Exception
   *           deleting both files irrespective what user entered in change file
   *           format command. because deleting both files will save the
   *           scenario in which current format is xml and user enters again
   *           xml.
   */
  public void deleteFile()
  {

    File xmlFile = new File("datastore.xml");
    File jsonFile = new File("datastore.json");

    if (xmlFile.exists() && xmlFile.isFile())
    {
      xmlFile.delete();
    }
    else
      if (jsonFile.exists() && jsonFile.isFile())
      {
        jsonFile.delete();
      }
  }
}
