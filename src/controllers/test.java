package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test
{

  
  public static void main(String [] args) {
    
    
    convertStringTimeToSeconds("01:00:00");
    
  }

  private static int convertStringTimeToSeconds(String dateString) 
  {
    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    try {
      Date date = formatter.parse(dateString);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return (calendar.get(Calendar.HOUR_OF_DAY)*3600
          + calendar.get(Calendar.MINUTE)*60
          + calendar.get(Calendar.SECOND));
    } catch (ParseException e) {
      //e.printStackTrace();
      System.out.println("Time could not be parsed. For now setting the current as 0.");
      return 0;
    }
  }
}
