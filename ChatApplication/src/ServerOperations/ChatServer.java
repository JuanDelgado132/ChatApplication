/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerOperations;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Thread;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juand
 */
public class ChatServer {
public static ArrayList<ChatPerson> persons = new ArrayList<>();
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(4500, 50, InetAddress.getByName("192.168.0.14"));
            //ArrayList<ChatPerson> persons = new ArrayList<>();
            while (true) {
                //Loop periodically to keep accepting people in the chat once connected. Explore for group chat option in the near future.
                System.out.println(server.getInetAddress());
                Socket connectionToClient = server.accept();
                System.out.println("server accepted a new chatter");

                PrintWriter output = new PrintWriter(connectionToClient.getOutputStream(), true);
                BufferedReader input = new BufferedReader(new InputStreamReader(connectionToClient.getInputStream()));

                persons.add(new ChatPerson(output, input));

                //create a thread to manage the sending of the messages.
                new Thread(() -> {
                    try {
                        while (true) {
                            BufferedReader in = input;
                            String message = in.readLine();
                            sendMessagesToAll(message);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            }
            /*new Thread(()->{
            try{
            ServerSocket server = new ServerSocket(8000);
            Socket person1 = server.accept();
            System.out.println("person 1 joined");
            Socket person2 = server.accept();
            System.out.println("person 2 joined");
            new Thread(new ChatHandler(person1,person2)).start();
            }
            catch(IOException e){
            e.printStackTrace();
            }
            }).start();*/
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void sendMessagesToAll(String message) throws IOException {
        for (int i = 0; i < persons.size(); i++) {
            if (persons.get(i) == null || persons.get(i).output == null) {
                persons.remove(i);
            } 
            else {    
                persons.get(i).output.println(message);
            }
        }
    }
}
