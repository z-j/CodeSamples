package models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import static models.Fixtures.activities;

public class ActivityTest
{

  @Test
  public void testCreate()
  {
    assertEquals("walk", activities[0].type);
    assertEquals("fridge", activities[0].location);
    assertEquals(0.001, activities[0].distance, 0.0001);
  }

  @Test
  public void testToString()
  {
    assertEquals("Activity{" + activities[0].id + ", walk, fridge, 0.001, []}", activities[0].toString());
  }
}