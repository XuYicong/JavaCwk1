import java.io.File;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
 
public class ElevationPlot extends Application {
	 @Override public void start(Stage stage) {
	        stage.setTitle("Elevation Plot");
	        //defining the axes
	        final NumberAxis xAxis = new NumberAxis();
	        final NumberAxis yAxis = new NumberAxis();
	        xAxis.setLabel("Distance (m)");
	        yAxis.setLabel("Elevation (m)");
	        //creating the chart
	        final LineChart<Number,Number> lineChart = 
	                new LineChart<Number,Number>(xAxis,yAxis);
	                
	        lineChart.setTitle("Elevation Plot");
	        lineChart.setCreateSymbols(false);
	        //defining a series
		    getParameters().getRaw().forEach((String name)->{
		    	//Add multiple lines to the graph
		    	XYChart.Series series = new XYChart.Series();
		        Track tk=new Track();
		        List<Double>dis;
			    try {
			    	//readFile() is modified so that it returns a list of distances
			    	dis=tk.readFile(name);
		        }catch(Exception e) {
		        	e.printStackTrace();
		        	return;
		        }
		    	//Set the series name to file name
		        series.setName(new File(name).getName());
		        //populating the series with data
		        for(int i=0;i<tk.size();++i) {
		        	series.getData().add(new XYChart.Data(dis.get(i), tk.get(i).getElevation()));
		        }
		        lineChart.getData().add(series);
	        });
	        Scene scene  = new Scene(lineChart,1280,640);
	       
	        stage.setScene(scene);
	        stage.show();
	    }
 public static void main(String[] args) {
	 	if(args.length<1) {
	 		System.err.println("Error: No file name provided");
	 		System.out.println("The track file to be plotted should be passed in as a command line argument");
	 		System.exit(-1);
	 	}
        launch(args);
    }
}