package utils;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.bethecoder.ascii_table.ASCIITable;

import models.Activity;
import models.User;

public class Print
{

  public static void printUsers(Collection<User> users)
  {

    String[] header = { "First Name", "Last Name", "Email", "Password" };

    if (users == null || users.size() == 0)
    {
      printNoData("No Users Found");
      return;
    }

    String[][] data = new String[users.size()][4];
    int index = 0;

    for (User u : users)
    {

      data[index][0] = u.firstName;
      data[index][1] = u.lastName;
      data[index][2] = u.email;
      data[index][3] = u.password;
      index++;
    }

    ASCIITable.getInstance().printTable(header, data);

  }

  public static void printNoData(String msg)
  {

    String[] header = { "Message" };
    String[][] data = new String[1][1];

    data[0][0] = msg;

    ASCIITable.getInstance().printTable(header, data);
  }


  /**
   *   //TODO
   *   right now this will work only for one user with
   *   multiple activities 
   * @param users
   */
  public static void printSortedActivities(User user) {

    String[] header = { "User Id", "First Name", "Type", "Location", "Distance", "Date", "Duration", "Route" };


    String [][]data;

    int index=0;

    if(user.activities != null) {
      
      data = new String [user.activities.size()][8];
      List<Activity> activities = user.activities;
      
      for(Activity act : activities) {
        data[index][0] = String.valueOf(user.id);
        data[index][1] = user.firstName;
        data[index][2] = act.type;
        data[index][3] = act.location;
        data[index][4] = String.valueOf(act.distance);
        data[index][5] = String.valueOf(act.date);
        data[index][6] = String.valueOf(act.duration);
        data[index][7] = act.route.toString();
        index++;
      }
      ASCIITable.getInstance().printTable(header, data);
    }
  }
  
  private static String convertDateToString(Date d) 
  {
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try {
      String date = formatter.format(d);
      return date;
    } catch (Exception e) {
      //e.printStackTrace();
      System.out.println("Date/Time could not be parsed. For now setting the current date/time instead.");
      return "";
    }
  }
}
