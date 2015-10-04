package models;

import static com.google.common.base.MoreObjects.toStringHelper;

public class Location
{

  public Long activityId;
  public double latitude;
  public double longitude;

  public Location(long aid, double lat, double longitude) {
    
    this.activityId = aid;
    this.latitude = lat;
    this.longitude = longitude;
    
  }

  @Override 
  public String toString() {
    return toStringHelper(this).addValue(activityId)
        .addValue(latitude)
        .addValue(longitude)
        .toString();
  }
}
