package controllers;

import java.io.File;
import java.util.Collection;

import com.google.common.base.Optional;

import asg.cliche.Command;
import asg.cliche.Param;
import asg.cliche.Shell;
import asg.cliche.ShellFactory;
import models.Activity;
import models.User;
import utils.JSONSerializer;
import utils.Print;
import utils.XMLSerializer;
import utils.YAMLSerializer;

public class Main
{
  pacemakerAPI paceApi;

  @Command(description = "Create a new User")
  public void createUser(@Param(name = "first name") String firstName, @Param(name = "last name") String lastName,
      @Param(name = "email") String email, @Param(name = "password") String password)
  {
    User u = paceApi.createUser(firstName, lastName, email, password);
    Print.printNoData("User created successfully with id: "+u.id);
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
    Print.printUser(user);
  }

  @Command(description = "Get user by id")
  public void listUser(@Param(name = "id") Long id)
  {
    User user = paceApi.getUserById(id);
    Print.printUser(user);
  }

  @Command(description = "Add Activity")
  public void addActivity(@Param(name = "user-id") Long userId, @Param(name = "type") String type,
      @Param(name = "location") String location, @Param(name = "distance") double distance,
      @Param(name = "date (dd:MM:yyyy HH:mm:ss)") String date, @Param(name = "duration (HH:mm:ss)") String duration)
  {
    Activity act = paceApi.addActivity(userId, type, location, distance, date, duration);
    Print.printNoData("Activity wih id:"+act.id+" was added.");
  }

  @Command(description = "Get all users details")
  public void listActivities(@Param(name = "user-id") Long userId, @Param(name = "SortBy") String sortBy)
  {
    
    User user = paceApi.getSortedActivities(userId, sortBy);
    if(user != null && user.activities != null && user.activities.size() > 0) {
      Print.printSortedActivities(user);
    } else { 
      Print.printNoData("Either No User Found for id:"+userId+" or NO activities for this user.");
    }

  }

  @Command(description = "Add Location")
  public void addLocation(@Param(name = "activity-id") Long activityId, @Param(name = "latitide") double latitude,
      @Param(name = "longitude") double longitude)
  {
    paceApi.addLocation(activityId, latitude, longitude);
    Print.printNoData("Location added.");
  }

  @Command(description = "Delete User")
  public void deleteUser(@Param(name = "id") long id)
  {
    if(paceApi.deleteUser(id)) {
      Print.printNoData("User Deleted Successfully.");
    } else {
      Print.printNoData("User could not be Deleted. Please check the id provided.");
    }
  }

  @Command(description = "Delete User")
  public void deleteUserbyEmail(@Param(name = "email") String email)
  {
    Optional<User> user = Optional.fromNullable(paceApi.getUserByEmail(email));
    if (user.isPresent())
    {
      if(paceApi.deleteUser(user.get().id)) {
        Print.printNoData("User Deleted Successfully.");
      } else {
        Print.printNoData("User could not be Deleted. Please check the id provided.");
      }
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
  @Command(description = "Change File Format (xml, json, yaml)")
  public void changeFileFormat(@Param(name = "type") String type)
  {
    deleteFile();
    
    if ("xml".equalsIgnoreCase(type))
    {
      //System.out.println("change file format xml");
      File datastore = new File("datastore.xml");
      this.paceApi.serializer = (new XMLSerializer(datastore));
      Print.printNoData("File format changed to xml. \nUse command 'store' or 'exit' to save in memory data to new file.");
    }
    else if ("json".equalsIgnoreCase(type)) {
      File datastore = new File("datastore.json");
      this.paceApi.serializer = (new JSONSerializer(datastore));
      Print.printNoData("File format changed to json. \nUse command 'store' or 'exit' to save in memory data to new file.");
    } else if ("yaml".equalsIgnoreCase(type)) {
      File datastore = new File("datastore.yaml");
      this.paceApi.serializer = (new YAMLSerializer(datastore));
      Print.printNoData("File format changed to yaml. \nUse command 'store' or 'exit' to save in memory data to new file.");
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
      //System.out.print("comning here and will store now.");
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
    File yamlFile = new File("datastore.yaml");

    if (xmlFile.exists() && xmlFile.isFile())
    {
      this.paceApi = new pacemakerAPI(new XMLSerializer(xmlFile));
      this.paceApi.load();
      Print.printNoData("XML File exists already. Data will be loaded from this file.");
    }
    else  if (jsonFile.exists() && jsonFile.isFile())
    {
      this.paceApi = new pacemakerAPI(new JSONSerializer(jsonFile));
      this.paceApi.load();
      Print.printNoData("JSON File exists already. Data will be loaded from this file.");
    }
    else  if (yamlFile.exists() && yamlFile.isFile())
    {
      this.paceApi = new pacemakerAPI(new YAMLSerializer(yamlFile));
      this.paceApi.load();
      Print.printNoData("YAML File exists already. Data will be loaded from this file.");
    }
    else
    {
      this.paceApi = new pacemakerAPI(new XMLSerializer(xmlFile));
      Print.printNoData("NO File exists already. Initializing xml format by default.\nFormat "
          + "can be changed using cff command.");
    }
  }

  /**
   * 
   * @throws Exception
   *           deleting both files irrespective what user entered in change file
   *           format command. because deleting both files will save the
   *           scenario in which current format is xml and user enters again
   *           xml. since data gets loaded automatically when application starts, this
   *           means we have all the data available in memory and can call store function once new
   *           file is created and new serializer is obtained
   */
  public void deleteFile()
  {

    File xmlFile = new File("datastore.xml");
    File jsonFile = new File("datastore.json");
    File yamlFile = new File("datastore.yaml");
    

    if (xmlFile.exists() && xmlFile.isFile())
    {
      xmlFile.delete();
      //System.out.print("xml file deleted");
      //Print.printNoData("xml file deleted.");
    }
    else if (jsonFile.exists() && jsonFile.isFile())
    {
      jsonFile.delete();
      //Print.printNoData("json file deleted.");
    }
    else if (yamlFile.exists() && yamlFile.isFile())
    {
      if(yamlFile.delete()){
        System.out.print("yaml file deleted");
      } else {
        System.out.print("yaml file could not be deleted");
      }
      //Print.printNoData("yaml file deleted.");
    }
  }
}
