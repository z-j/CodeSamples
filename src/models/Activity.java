package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.google.common.base.Objects;

public class Activity
{

  public Long id;
  public String type;
  public String location;
  public double distance;
  public Date date;
  public double duration;

  public List<Location> route = new ArrayList<>();

  public static Long counter = 0l;

  public Activity(String type, String location, double distance)
  {

    this.id = counter++;
    this.type = type;
    this.location = location;
    this.distance = distance;
    this.date = new Date();
  }

  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(id).addValue(type).addValue(location).addValue(distance).addValue(route)
        .toString();
  }

  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.id, this.type, this.location, this.distance);
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Activity)
    {
      final Activity other = (Activity) obj;
      return Objects.equal(type, other.type) && Objects.equal(location, other.location)
          && Objects.equal(distance, other.distance) && Objects.equal(route, other.route);
    }
    else
    {
      return false;
    }
  }

  public static Comparator<Activity> getSortedOnType()
  {
    return new Comparator<Activity>()
    {
      // compare using attribute 1

      public int compare(Activity one, Activity two)
      {

        int result = 0;

        result = one.type.compareTo(two.type);
        if (result == 0)
        {
          result = one.location.compareTo(two.location);
          if (result == 0)
          {
            Double d1 = one.distance;
            Double d2 = two.distance;
            result = d1.compareTo(d2);
            if (result == 0)
            {
              result = one.date.compareTo(two.date);
              if (result == 0)
              {
                result = one.location.compareTo(two.location);
              }
            }
          }
        }

        return result;
      }

    };
  }
}
