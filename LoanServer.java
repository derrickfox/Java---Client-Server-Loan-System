/*
 * Derrick Fox
 * CS 214 - Advanced Java
 * Project 7 - Networking
 * April 6, 2015
 * Java 1.8 JavaFX 2.2
 */
/* This is a bug I do not know how to fix. I have commented it below.
 * I will come in to your tutoring session for advice. 
 */


package application;

import java.io.*;
import java.net.*;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class LoanServer extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) throws Exception{
    // Text area for displaying contents
    TextArea ta = new TextArea();

    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
    primaryStage.setTitle("LoanServer"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    
    new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8001);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
  
        
  
        while (true) {
        	
        	// Create data input and output streams
            ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
          // Receive radius from the client
          
          double intrestRate = inputFromClient.readDouble();
          double years = inputFromClient.readDouble();
          double amount = inputFromClient.readDouble();
  		  
          // Compute area
          //double area = radius * radius * Math.PI;
  
          // Send area back to the client
          /*
          outputToClient.writeDouble(intrestRate);
          outputToClient.writeDouble(years);
          outputToClient.writeDouble(amount);
		  */
        	
         Loan object = null;
		try {
			object = (Loan) inputFromClient.readObject();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
          Platform.runLater(() -> {
            ta.appendText("Monthly Payment from server: " 
            // This is where I am stuck. I do not know how to fix this error. 
              + object.monthlyPayment() + '\n');
            ta.appendText("Years of the loan from server: " + object.getNumberOfYears() + '\n'); 
            ta.appendText("Amount of the loan: " + object.getLoanAmount() + '\n');
          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
