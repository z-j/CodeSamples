package models;

import static models.Fixtures.activities;
import static models.Fixtures.locations;
import static models.Fixtures.users;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import controllers.pacemakerAPI;
import utils.Serializer;
import utils.XMLSerializer;


public class PersistenceTest
{
  pacemakerAPI pacemaker;
  
  
  @Test
  public void testPopulate()
  { 
    pacemaker = new pacemakerAPI(null);
    assertEquals(pacemaker.getUsers().size(), 0);
    populate (pacemaker);

    System.out.println(users.length+","+  pacemaker.getUsers().size());
    assertEquals(users.length, pacemaker.getUsers().size());
    assertEquals(2, pacemaker.getUserByEmail(users[0].email).activities.size());
    assertEquals(2, pacemaker.getUserByEmail(users[1].email).activities.size());   
    Long activityID = pacemaker.getUserByEmail(users[0].email).activities.get(0).id;
    assertEquals(locations.length, pacemaker.getActivity(activityID).route.size());   
  }

  void populate (pacemakerAPI pacemaker)
  {
    //pacemaker = new pacemakerAPI(null);

    for (User user : users)
    {
      pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
    }
    User user1 = pacemaker.getUserByEmail(users[0].email);
    Activity activity = pacemaker.addActivity(user1.id, activities[0].type, activities[0].location, activities[0].distance);
    pacemaker.addActivity(user1.id, activities[1].type, activities[1].location, activities[1].distance);
    User user2 = pacemaker.getUserByEmail(users[1].email);
    pacemaker.addActivity(user2.id, activities[2].type, activities[2].location, activities[2].distance);
    pacemaker.addActivity(user2.id, activities[3].type, activities[3].location, activities[3].distance);

    for (Location location : locations)
    {
      pacemaker.addLocation(activity.id, location.latitude, location.longitude);
    }
  }
  
  void deleteFile(String fileName)
  {
    File datastore = new File ("testdatastore.xml");
    if (datastore.exists())
    {
      datastore.delete();
    }
  }
  
  @Test
  public void testXMLSerializer() throws Exception
  { 
    System.out.println("test xml serilizaer");
    String datastoreFile = "testdatastore.xml";
    deleteFile (datastoreFile);

    Serializer serializer = new XMLSerializer(new File (datastoreFile));

    pacemaker = new pacemakerAPI(serializer); 
    populate(pacemaker);
    pacemaker.store();
    
    pacemakerAPI pacemaker2 =  new pacemakerAPI(serializer);
    pacemaker2.load();

    assertEquals (pacemaker.getUsers().size(), pacemaker2.getUsers().size());
    for (User user : pacemaker.getUsers())
    {
      assertTrue (pacemaker2.getUsers().contains(user));
    }
    deleteFile ("testdatastore.xml");
  }

}