/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientOperations;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author juand
 */
public class ChatClientPrototype extends Pane {    
    private TextArea messageDisplay;
    private ScrollPane messageDisplayHolder;
    private VBox chatClient;
    private HBox writeAndSendHolder;
    private Button sendButton;
    private TextField messageBar;
    private Socket serverConnection;
    private PrintWriter output;
    private BufferedReader input;
    
    public ChatClientPrototype() throws IOException{
        connectToServer();
        launchDisplayUpdate();
        buildTheChatGui();
    }
    
    private void buildTheChatGui(){
        messageDisplay = new TextArea();
        messageDisplay.setEditable(false);
        messageDisplayHolder = new ScrollPane(messageDisplay);
        messageBar = new TextField();
        sendButton = new Button("Send");
        chatClient = new VBox(5);
        writeAndSendHolder = new HBox(5);
        writeAndSendHolder.getChildren().add(messageBar);
        writeAndSendHolder.getChildren().add(sendButton);
        chatClient.getChildren().add(messageDisplayHolder);
        chatClient.getChildren().add(writeAndSendHolder);
        getChildren().add(chatClient);
        sendButton.setOnAction(e->{
            System.out.println("I am here");
            String message = messageBar.getText();
           // messageDisplay.appendText(message);
            messageBar.clear();
            output.println(message);

        });
    }
    private void connectToServer(){
        try {
            serverConnection = new Socket("192.168.0.14", 4500);
            System.out.println(serverConnection.getRemoteSocketAddress());
        } catch (IOException ex) {
            Logger.getLogger(ChatClientPrototype.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This function is in charge of launching a thread to ensure periodic updates are made to the message display.
     */
    private void launchDisplayUpdate() throws IOException{
        output = new PrintWriter(serverConnection.getOutputStream(), true);
        input = new BufferedReader(new InputStreamReader(serverConnection.getInputStream()));
        
        new Thread(()->{
           while(true){
               try {
                   String message = input.readLine();
                   System.out.println("Message: " + message);
                   Platform.runLater(() ->{
                    messageDisplay.appendText(message + "\n");
                   });
               } catch (IOException ex) {
                   Logger.getLogger(ChatClientPrototype.class.getName()).log(Level.SEVERE, null, ex);
               }
               
           } 
        }).start();
        
        
    }
    
}
