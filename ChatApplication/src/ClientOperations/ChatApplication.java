/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientOperations;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author juand
 */
public class ChatApplication extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene sc = new Scene(new ChatClientPrototype());
        primaryStage.setScene(sc);
        primaryStage.show();
    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
}
