package models;

import static models.Fixtures.users;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import controllers.pacemakerAPI;;

public class UserTest
{

  @Test
  public void testCreate()
  {
    assertEquals("marge", users[0].firstName);
    assertEquals("simpson", users[0].lastName);
    assertEquals("marge@simpson.com", users[0].email);
    assertEquals("secret", users[0].password);
  }

  @Test
  /**
   * the rational is to check if there are any duplicate users
   */
  public void testIds()
  {
    Set<Long> ids = new HashSet<>();
    for (User user : users)
    {
      ids.add(user.id);
    }
    assertEquals(users.length, ids.size());
  }

  @Test
  public void testUsers()
  {
    pacemakerAPI pacemaker = new pacemakerAPI(null);

    for (User user : users)
    {
      pacemaker.createUser(user.firstName, user.lastName, user.email, user.password);
    }
    assertEquals(users.length, pacemaker.getUsers().size());
    for (User user : users)
    {
      User eachUser = pacemaker.getUserByEmail(user.email);
      assertEquals(user, eachUser);
      assertNotSame(user, eachUser);
    }
  }

  @Test
  public void testToString()
  {
    System.out.println(users[0].toString());
    assertEquals("User{" + users[0].id + ", marge, simpson, secret, marge@simpson.com, []}", users[0].toString());
  }
}