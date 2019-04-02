import java.time.ZonedDateTime;
import java.util.*;
import java.io.*;

import static java.lang.Math.*;

/**
 * Represents a track composed with points in space and time, recorded by a GPS sensor.
 *
 * @author Xyct
 */
public class Track{
	//The list storing points of the track.
	private List<Point> a;
	/**
	 * Initialize an empty track
	 */
	public Track() {
		a= new ArrayList<Point>();
	}
	/**
	 * Import track data from a CSV file.
	 * @param Path of the file
	 * @throws FileNotFoundException
	 * @throws GPSException
	 */
	public void readFile(String name) throws FileNotFoundException,GPSException{
		//TODO: Have problem finding files with relative paths in Eclipse under Windows
		Scanner sc = new Scanner(new File(name));
		while(sc.hasNextLine()) {
			String s=sc.nextLine();
			String[] ch=s.split(",");
			if(ch.length!=4) {
				sc.close();
				throw(new GPSException("Missing Parameter(s)"));
			}
			if(ch[0].toLowerCase().equals("time"))continue;
			a.add(new Point(ZonedDateTime.parse(ch[0]),Double.parseDouble(ch[1]),Double.parseDouble(ch[2]),Double.parseDouble(ch[3])));
		}
		sc.close();
	}
	/**
	 * Add a point to the end of the track
	 * @param p the point to be added
	 */
	public void add(Point p) {
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
