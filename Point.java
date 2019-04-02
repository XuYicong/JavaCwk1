import java.time.ZonedDateTime;

import static java.lang.Math.*;

/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Nick Efford & Xyct
 */
public class Point{
  // Constants useful for bounds checking, etc

  private static final double MIN_LONGITUDE = -180.0;
  private static final double MAX_LONGITUDE = 180.0;
  private static final double MIN_LATITUDE = -90.0;
  private static final double MAX_LATITUDE = 90.0;
  private static final double MEAN_EARTH_RADIUS = 6.371009e+6;
  private ZonedDateTime time;
  private double longitude, latitude, elevation;
  /**
   * Initialize a point with time and position.
   *
   * @param time Time of the point
   * @param lon Longitude of the point, from -180 to 180 inclusively
   * @param lat Latitude of the point, from -90 to 90 inclusively
   * @param ele Elevation of the point in metres
   * @throws GPSException
   */
  public Point(ZonedDateTime time, double lon, double lat, double ele) throws GPSException{
	  if(lon<MIN_LONGITUDE||lon>MAX_LONGITUDE||lat>MAX_LATITUDE||lat<MIN_LATITUDE) {
		  throw(new GPSException("Invalid coordinates"));
	  }
	  this.time=time;
	  longitude=lon;
	  latitude=lat;
	  elevation=ele;
  }

  /**
   * Get the time of the point.
   *
   * @return The time of the point
   */
  public ZonedDateTime getTime() {
	  return time;
  }
  /**
   * Get the longitude of the point.
   *
   * @return The longitude of the point
   */
  public double getLongitude() {
	  return longitude;
  }
  /**
   * Get the latitude of the point.
   *
   * @return The latitude of the point
   */
  public double getLatitude() {
	  return latitude;
  }
  /**
   * Get the elevation of the point.
   *
   * @return The elevation of the point
   */
  public double getElevation() {
	  return elevation;
  }
  /**
   * Represent the position of the point as a string.
   *
   * @return Position of the point in the format "(longitude, latitude), elevation"
   */
  public String toString() {
	  return "("+String.format("%.5f", longitude)+", "+String.format("%.5f", latitude)+"), "+elevation+" m";
  }

  // Do not alter anything beneath this comment

  /**
   * Computes the great-circle distance or orthodromic distance between
   * two points on a spherical surface, using Vincenty's formula.
   *
   * @param p First point
   * @param q Second point
   * @return Distance between the points, in metres
   */
  public static double greatCircleDistance(Point p, Point q) {
    double phi1 = toRadians(p.getLatitude());
    double phi2 = toRadians(q.getLatitude());

    double lambda1 = toRadians(p.getLongitude());
    double lambda2 = toRadians(q.getLongitude());
    double delta = abs(lambda1 - lambda2);

    double firstTerm = cos(phi2)*sin(delta);
    double secondTerm = cos(phi1)*sin(phi2) - sin(phi1)*cos(phi2)*cos(delta);
    double top = sqrt(firstTerm*firstTerm + secondTerm*secondTerm);

    double bottom = sin(phi1)*sin(phi2) + cos(phi1)*cos(phi2)*cos(delta);

    return MEAN_EARTH_RADIUS * atan2(top, bottom);
  }
}
