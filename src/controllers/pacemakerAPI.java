package controllers;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import models.Activity;
import models.Location;
import models.User;

public class pacemakerAPI
{
  private Map<Long, User>     userIndex       = new HashMap<>();
  private Map<String, User>   emailIndex      = new HashMap<>();
  private Map<Long, Activity> activityIndex   = new HashMap<>();

  public Collection<User> getUsers ()
  {
    return userIndex.values();
  }

  public  void deleteUsers() 
  {
    userIndex.clear();
    emailIndex.clear();
  }

  public User createUser(String firstName, String lastName, String email, String password) 
  {
    User user = new User (firstName, lastName, email, password);
    userIndex.put(user.id, user);
    emailIndex.put(email, user);
    return user;
  }

  public User getUserByEmail(String email) 
  {
    return emailIndex.get(email);
  }

  public User getUser(Long id) 
  {
    return userIndex.get(id);
  }

  public Activity addActivity(Long userId, String type, String location, double distance) 
  {

    Activity act = new Activity(type, location, distance);
    User u = userIndex.get(userId);

    if(u != null) 
    {
      u.activities.put(act.id, act);
      activityIndex.put(act.id, act);
    }

    return act;
  }

  public Activity getActivity (Long id)
  {
    return activityIndex.get(id);
  }

  public Location addLocation(Long activityId, double lat, double lon) 
  {

    Location location = new Location(activityId, lat, lon);
    Activity act = activityIndex.get(activityId);
    if(act != null) 
    {
      act.route.add(location);
    }

    return location;

  }
  
  @SuppressWarnings("unchecked")
  void load(File file) throws Exception
  {
    ObjectInputStream is = null;
    try
    {
      XStream xstream = new XStream(new DomDriver());
      is = xstream.createObjectInputStream(new FileReader(file));
      userIndex       = (Map<Long, User>)     is.readObject();
      emailIndex      = (Map<String, User>)   is.readObject();
      activityIndex = (Map<Long, Activity>) is.readObject();
    }
    finally
    {
      if (is != null)
      {
        is.close();
      }
    }
  }

  void store(File file) throws Exception
  {
    XStream xstream = new XStream(new DomDriver());
    ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(file));
    out.writeObject(userIndex);
    out.writeObject(emailIndex);
    out.writeObject(activityIndex);
    
    System.out.println(userIndex.size()+","+emailIndex.size()+","+activityIndex.size());
    out.close(); 
  }

  public void deleteUser(Long id) 
  {
    User user = userIndex.remove(id);
    emailIndex.remove(user.email);
  }
}