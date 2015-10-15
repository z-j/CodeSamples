package models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.pacemakerAPI;
import static models.Fixtures.users;
import static models.Fixtures.activities;
import static models.Fixtures.locations;

public class pacemakerAPITest
{
  private pacemakerAPI pacemaker;

  @Before
  public void setup()
  {
    pacemaker = new pacemakerAPI(null);
  }

  @After
  public void tearDown()
  {
    pacemaker = null;
  }
  
  @Test
  public void testUser()
  {
    User homer = new User ("homer", "simpson", "homer@simpson.com",  "secret");

    assertEquals (0, pacemaker.getUsers().size());
    pacemaker.createUser("homer", "simpson", "homer@simpson.com", "secret");
    assertEquals (1, pacemaker.getUsers().size());

    assertEquals (homer, pacemaker.getUserByEmail("homer@simpson.com"));
  }   
  
  @Test
  public void testEquals()
  {
    User homer = new User ("homer", "simpson", "homer@simpson.com",  "secret");
    User homer2 = new User ("homer", "simpson", "homer@simpson.com",  "secret"); 
    User bart   = new User ("bart", "simpson", "bartr@simpson.com",  "secret"); 

    assertEquals(homer, homer);
    assertEquals(homer, homer2);
    assertNotEquals(homer2, bart);
  }  
}