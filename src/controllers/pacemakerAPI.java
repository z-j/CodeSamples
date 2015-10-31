package controllers;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.common.base.Optional;

import models.Activity;
import models.Location;
import models.User;
import utils.Print;
import utils.Serializer;

public class pacemakerAPI
{

  public Serializer serializer;

  private Map<Long, User> userIndex = new HashMap<>();
  private Map<String, User> emailIndex = new HashMap<>();
  private Map<Long, Activity> activityIndex = new HashMap<>();

  public pacemakerAPI(Serializer serializer)
  {

    this.serializer = serializer;
  }

  public void printSize()
  {
    //System.out.println(userIndex);
    //System.out.println(userIndex.size() + "," + emailIndex.size() + "," + activityIndex.size());
  }

  public Collection<User> getUsers()
  {
    return userIndex.values();
  }

  public void deleteUsers()
  {
    userIndex.clear();
    emailIndex.clear();
  }

  public User createUser(String firstName, String lastName, String email, String password)
  {
    User user = new User(firstName, lastName, email, password);
    userIndex.put(user.id, user);
    emailIndex.put(email, user);
    return user;
  }

  public void changeFileFormat()
  {
    userIndex.clear();
    emailIndex.clear();
  }

  public boolean deleteUser(long id)
  {
    if (userIndex.get(id) != null)
    {
      String email = userIndex.get(id).email;
      userIndex.remove(id);
      emailIndex.remove(email);
      return true;
    }
    else
    {
      //System.out.println("user is null");
      return false;
    }
  }

  public User getUserByEmail(String email)
  {
    return emailIndex.get(email);
  }

  public User getUserById(Long id)
  {
    return userIndex.get(id);
  }

  public User getUser(Long id)
  {
    return userIndex.get(id);
  }

  public Activity addActivity(Long id, String type, String location, double distance, String date, String duration)
  {
    Activity activity = null;
    Optional<User> user = Optional.fromNullable(userIndex.get(id));
    if (user.isPresent())
    {
      activity = new Activity(type, location, distance, date, duration);
      // user.get().activities.put(activity.id, activity);
      user.get().activities.add(activity);
      activityIndex.put(activity.id, activity);
    }
    return activity;
  }

  public User getSortedActivities(Long id, String sortBy)
  {

    User u = userIndex.get(id);

    if (u !=null && u.activities != null && u.activities.size() > 0)
    {
      //System.out.println(u.activities.size() + "," + u.activities.get(0).id);
      this.sortActivities(u, sortBy);
    } else {
      //System.out.println("either null or 0 size");
    }
    return u;
  }

  private void sortActivities(User u, String sortBy) {

    if("type".equals(sortBy)) {
      Collections.sort(u.activities, Activity.getSortedOnType());
    } else if("date".equalsIgnoreCase(sortBy)) {
      Collections.sort(u.activities, Activity.getSortedOnDate());
    } else if("location".equalsIgnoreCase(sortBy)) {
      Collections.sort(u.activities, Activity.getSortedOnLocation());
    } else if("distance".equalsIgnoreCase(sortBy)) {
      Collections.sort(u.activities, Activity.getSortedOnDistance());
    } else if("duration".equalsIgnoreCase(sortBy)) {
      Collections.sort(u.activities, Activity.getSortedOnDuration());
    } else {
      Print.printNoData("Sort By Parameter is not correct.");
    }

  }

  public Activity getActivity(Long id)
  {
    return activityIndex.get(id);
  }

  public Location addLocation(Long activityId, double lat, double lon)
  {

    Location location = new Location(activityId, lat, lon);
    Activity act = activityIndex.get(activityId);
    if (act != null)
    {
      act.route.add(location);
    }

    return location;

  }

  @SuppressWarnings("unchecked")
  public void load() throws Exception
  {
    serializer.read();
    activityIndex = (Map<Long, Activity>) serializer.pop();
    emailIndex = (Map<String, User>) serializer.pop();
    userIndex = (Map<Long, User>) serializer.pop();
    //System.out.println("loaded:" + userIndex.size() + "," + emailIndex.size() + "," + activityIndex.size());
  }

  public void store() throws Exception
  {

    serializer.push(userIndex);
    serializer.push(emailIndex);
    serializer.push(activityIndex);

    serializer.write();
    //System.out.println("storage:" + userIndex.size() + "," + emailIndex.size() + "," + activityIndex.size());
  }
  


}