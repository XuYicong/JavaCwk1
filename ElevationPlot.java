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
		    	XYChart.Series series = new XYChart.Series();
		    	String[] Name=name.split("\\\\");
		        series.setName(Name[Name.length-1]);
		        Track tk=new Track();
		        List<Double>dis;
			    try {
			    	dis=tk.readFile(name);
		        }catch(Exception e) {
		        	e.printStackTrace();
		        	return;
		        }
		        //populating the series with data
		        //series.getData().clear();
		        for(int i=0;i<tk.size();++i) {
		        	series.getData().add(new XYChart.Data(dis.get(i), tk.get(i).getElevation()));
		        }
		        //series.getData().add(new XYChart.Data(2, 14));
		        //series.getData().add(new XYChart.Data(3, 15));
		        
		        Scene scene  = new Scene(lineChart,1280,640);
		        lineChart.getData().add(series);
		       
		        stage.setScene(scene);
		        stage.show();
	        	
	        });
		    //Platform.exit();
	    }
 public static void main(String[] args) {
        launch(args);
    }
}