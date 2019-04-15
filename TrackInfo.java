import java.time.ZonedDateTime;

import java.util.*;
import java.io.*;
import java.lang.*;

/**
 * Test the Track class
 *
 * @author Xyct
 */
public class TrackInfo{
	static void print(Track a) {
		System.out.println(a.size()+" points in track");
		System.out.println("Lowest point is "+a.lowestPoint());
		System.out.println("Highest point is "+a.highestPoint());
		System.out.println("Total distance = "+String.format("%.3f",a.totalDistance()/1000)+" km");
		System.out.println("Average speed = "+String.format("%.3f", a.averageSpeed())+" m/s");
	}
	static void helpMessage() {
		System.out.println("A file name should be supplied as command line argument from which the data is imported to the track whose info is to be displayed.");
		System.out.println("The file should be in csv format, with each line representing a point in the track");
	}
	public static void main(String[] args){
		Track a=new Track();
		String filename="";
		//Dealing with file names
		try {
			filename=args[0];
		}catch(ArrayIndexOutOfBoundsException e) {
			System.err.println("Error: No file name is supplied");
			helpMessage();
			System.exit(1);
		}
		//Dealing with tracks
		try{
			a.readFile(filename);
			print(a);
		}catch(Exception e) {
			System.err.println(e);
			System.exit(-1);
		}
	}
}
