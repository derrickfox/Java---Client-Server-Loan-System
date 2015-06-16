/*
 * Derrick Fox
 * CS 214 - Advanced Java
 * Project 7 - Networking
 * April 6, 2015
 * Java 1.8 JavaFX 2.2
 */

package application;

import java.io.*;
import java.net.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LoanClient extends Application {
  DataOutputStream toServer = null;
  DataInputStream fromServer = null;	
	
  private TextField tfIntrestRate = new TextField();
  private TextField tfYears = new TextField();
  private TextField tfAmount = new TextField();
  private TextArea taReport = new TextArea();

  // Button for sending a student to the server
  private Button btSubmit = new Button("Submit");

  // Host name or ip
  String host = "localhost";

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    GridPane pane = new GridPane();
    pane.add(new Label("Annual Interest Rate"), 0, 0);
    pane.add(tfIntrestRate, 1, 0);    
    pane.add(new Label("Number Of Years"), 0, 1);
    pane.add(tfYears, 1, 1);
    pane.add(tfAmount, 1, 2);
    pane.add(new Label("Loan Amount"), 0, 2);
    pane.add(btSubmit, 1, 3);
    
    BorderPane mainPane = new BorderPane();
    // Text area to display contents
    TextArea ta = new TextArea();
    mainPane.setCenter(new ScrollPane(ta));
    mainPane.setTop(pane);
    
    GridPane.setHalignment(btSubmit, HPos.RIGHT);
    GridPane.setHalignment(taReport, HPos.CENTER);
    GridPane.setValignment(taReport, VPos.BOTTOM);
    
    pane.setAlignment(Pos.CENTER);   
    tfIntrestRate.setPrefColumnCount(15);
    tfYears.setPrefColumnCount(15);
    tfAmount.setPrefColumnCount(15);

    btSubmit.setOnAction(new ButtonListener());
    
    // Create a scene and place it in the stage
    Scene scene = new Scene(mainPane, 450, 200);
    primaryStage.setTitle("LoanClient"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
  }

  /** Handle button action */
  private class ButtonListener implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      try {
        // Establish connection with the server
        Socket socket = new Socket(host, 8001);

        // Create an output stream to the server
        ObjectOutputStream toServer =
          new ObjectOutputStream(socket.getOutputStream());

        // Get text field
        String intrestRateString = tfIntrestRate.getText().trim();
        String yearsString = tfYears.getText().trim();
        String amountString = tfAmount.getText().trim();
       
        double intrestRate = Double.parseDouble(intrestRateString);
        int years = Integer.parseInt(yearsString);
        double amount = Double.parseDouble(amountString);
        /*
        toServer.writeDouble(intrestRate);
        toServer.flush();
        */
        System.out.println("Intrest Rate Field: " + tfIntrestRate);
        System.out.println("Years Field: " + tfYears);
        System.out.println("Amount Field: " + tfAmount);

        System.out.println("Intrest Rate String: " + intrestRateString);
        System.out.println("Years String: " + yearsString);
        System.out.println("Amount String: " + amountString);

        System.out.println("Intrest Double: " + intrestRate);
        System.out.println("Years Double: " + years);
        System.out.println("Amount Double: " + amount);
        
        // Create a Student object and send to the server
        Loan s = new Loan(intrestRate, years, amount);
        toServer.writeObject(s);
      }
      catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }
  
  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
