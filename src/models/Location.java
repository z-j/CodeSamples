package models;

import static com.google.common.base.MoreObjects.toStringHelper;

import com.google.common.base.Objects;

public class Location
{

  static Long counter = 0l;
  public Long id;

  public Long activityId;
  public double latitude;
  public double longitude;

  public Location() {
    
  }
  
  public Location(long aid, double lat, double longitude)
  {

    this.id = counter++;
    this.activityId = aid;
    this.latitude = lat;
    this.longitude = longitude;

  }

  @Override
  public String toString()
  {
    return toStringHelper(this).addValue(activityId).addValue(latitude).addValue(longitude).toString();
  }

  @Override
  public int hashCode()
  {
    return Objects.hashCode(this.id, this.latitude, this.longitude);
  }

  @Override
  public boolean equals(final Object obj)
  {
    if (obj instanceof Location)
    {
      final Location other = (Location) obj;
      return Objects.equal(latitude, other.latitude) && Objects.equal(longitude, other.longitude);
    }
    else
    {
      return false;
    }
  }
}
