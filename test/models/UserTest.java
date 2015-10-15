package models;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static models.Fixtures.users;;

public class UserTest
{

  @Test
  public void testCreate()
  {
    assertEquals ("marge",               users[0].firstName);
    assertEquals ("simpson",             users[0].lastName);
    assertEquals ("marge@simpson.com",   users[0].email);   
    assertEquals ("secret",              users[0].password);   
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
    assertEquals (users.length, ids.size());
  }

  @Test
  public void testToString()
  {
    assertEquals ("User{" + users[0].id + ", marge, simpson, secret, marge@simpson.com, {}}", users[0].toString());
  }
}