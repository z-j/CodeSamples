package models;

public class Fixtures
{
  public static User[] users = { new User("marge", "simpson", "marge@simpson.com", "secret"),
      new User("lisa", "simpson", "lisa@simpson.com", "secret"),
      new User("bart", "simpson", "bart@simpson.com", "secret"),
      new User("maggie", "simpson", "maggie@simpson.com", "secret") };

  public static Activity[] activities = { new Activity("walk", "fridge", 0.001, "12:10:2015 15:30:29", "03:30:23"), new Activity("walk", "bar", 1.0, "13:10:2015 16:30:29", "03:30:23"),
      new Activity("run", "work", 2.2, "12:10:2015 15:30:29", "03:30:23"), new Activity("walk", "shop", 2.5, "12:10:2015 15:30:29", "03:30:23"), new Activity("cycle", "school", 4.5, "17:10:2015 14:30:29", "03:30:23") };

  public static Location[] locations = { new Location(1, 23.3d, 33.3d), new Location(2, 34.4d, 45.2d),
      new Location(3, 25.3d, 34.3d), new Location(4, 44.4d, 23.3d) };
}