/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 * Class might not be needed anymore. Will move all operations to ChatServer.java. 
 * Will revisit further down line to explore option for group chat or just a regular chat between two people.
 * @Deprecated.
 */
package ServerOperations;
//import java.lang.Runnable;
import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author juand
 */
public class ChatHandler implements Runnable{
    
    private Socket person1;
    private Socket person2;
    private PrintWriter toPerson1;
    private PrintWriter toPerson2;
    private BufferedReader fromPerson1;
    private BufferedReader fromPerson2;
    
    public ChatHandler(Socket person1, Socket person2){
        this.person1 = person1;
        this.person2 = person2;
    }

    @Override
    public void run() {
        try {
            System.out.println("In the chat handler");
            toPerson1 = new PrintWriter(person1.getOutputStream(),true);
            toPerson2 = new PrintWriter(person2.getOutputStream(),true);
            fromPerson1 = new BufferedReader(new InputStreamReader(person1.getInputStream()));
            fromPerson2 = new BufferedReader(new InputStreamReader(person2.getInputStream()));
            
            while(true){
                System.out.println("repeat");
                //Read the inputs from both persons.
                String messageFromPerson1 = fromPerson1.readLine();
                String messageFromPerson2 = fromPerson2.readLine();
                
                System.out.println("Message from person1: " + messageFromPerson1);
                System.out.println("Message from person1: " + messageFromPerson2);
                
                //Send outputs to each person.
                toPerson1.println(messageFromPerson2);
                toPerson2.println(messageFromPerson1);
            }
        } 
        catch (IOException ex) {
            Logger.getLogger(ChatHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }  
}
