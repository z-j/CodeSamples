package controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    activityIndex.put(act.id, act);
    
    User u = userIndex.get(userId);
    u.activities.put(act.id, act);
    
    return act;
    
  }
  
  public Location addLocation(Long activityId, double lat, double lon) 
  {
    
    Location location = new Location(activityId, lat, lon);
    Activity act = activityIndex.get(activityId);
    act.route.add(location);
    
    return location;
    
  }

  public void deleteUser(Long id) 
  {
    User user = userIndex.remove(id);
    emailIndex.remove(user.email);
  }
}