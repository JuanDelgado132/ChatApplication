/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerOperations;
import java.io.*;
/**
 *
 * @author juand
 */
public class ChatPerson {
    public PrintWriter output;
    public BufferedReader input;
    
    public ChatPerson(PrintWriter output, BufferedReader input){
        this.output = output;
        this.input = input;
    }
}
