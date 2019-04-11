import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Represents a track composed with points in space and time, recorded by a GPS sensor.
 *
 * @author Xyct
 */
public class Track{
	//The list storing points of the track.
	private List<Point> a;
	private int lowestPoint, highestPoint;
	private double totalDist;
	private ZonedDateTime pre,post;
	/**
	 * Initialize an empty track
	 */
	public Track() {
		a= new ArrayList<Point>();
	}
	/**
	 * Import track data from a CSV file.
	 * @param Path of the file
	 * @return 
	 * @throws FileNotFoundException
	 * @throws GPSException if format of the file is damaged
	 */
	public List<Double> readFile(String name) throws FileNotFoundException,GPSException{
		//Notice: directly running under eclipse causes the execution route being the installation route of java.
		Scanner sc = new Scanner(new File(name));
		a.clear();
		List<Double>ret=new ArrayList<Double>();
		while(sc.hasNextLine()) {
			String[] ch=sc.nextLine().split(",");
			if(ch.length<4) {
				sc.close();
				throw(new GPSException("Missing Parameter(s)"));
			}
			double x,y,e;
			try {
				x=Double.parseDouble(ch[1]);
				y=Double.parseDouble(ch[2]);
				e=Double.parseDouble(ch[3]);
			}catch(NumberFormatException E) {
				continue;//Filter headers
			}
			add(new Point(ZonedDateTime.parse(ch[0]),x,y,e));
			try {
				ret.add(totalDistance());
			}catch(GPSException ex) {
				ret.add(0.0);
			}
		}
		sc.close();
		return ret;
	}
	/**
	 * get the point with lowest elevation
	 * @return Point object that has the lowest elevation among the track
	 * @throws GPSException if not enough points in track
	 */
	public Point lowestPoint() throws GPSException{
		if(a.size()<1)throw(new GPSException("No point in track"));
		return get(lowestPoint);
	}
	/**
	 * get the point with highest elevation
	 * @return Point object that has the highest elevation among the track
	 * @throws GPSException if not enough points in track
	 */
	public Point highestPoint() throws GPSException{
		if(a.size()<1)throw(new GPSException("No point in track"));
		return get(highestPoint);
	}
	/**
	 * get the total distance of the track
	 * @return double the total distance
	 * @throws GPSException if not enough points in track
	 */
	public double totalDistance() throws GPSException{
		if(a.size()<2)throw(new GPSException("No enough points in track"));
		return totalDist;
	}
	public double averageSpeed() {
		if(a.size()<2)throw(new GPSException("No enough points in track"));
		return totalDist/ChronoUnit.SECONDS.between(pre, post);
	}
	/**
	 * Add a point to the end of the track
	 * @param p the point to be added
	 */
	public void add(Point p) {
		ZonedDateTime t=p.getTime();
		if(a.isEmpty()) {
			totalDist=lowestPoint=highestPoint=0;
			pre=post=t;
		}
		else {
			double e=p.getElevation();
			totalDist+=Point.greatCircleDistance(p, get(a.size()-1));
			if(t.compareTo(pre)<0)pre=t;
			else if(t.compareTo(post)>0)post=t;
			if(e>get(highestPoint).getElevation())highestPoint=a.size();
			else if(e<get(lowestPoint).getElevation())lowestPoint=a.size();
		}
		a.add(p);
	}
	/**
	 * Get the size of the track
	 * @return the number of points in the track
	 */
	public int size() {
		return a.size();
	}
	/**
	 * Get the point at a specific index in the track
	 * @param x Index of the point
	 * @return The point at the given index
	 * @throws GPSException
	 */
	public Point get(int x) throws GPSException{
		if(x<0||x>=a.size())throw(new GPSException("Index out of bound"));
		return a.get(x);
	}
}
