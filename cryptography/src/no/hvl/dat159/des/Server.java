/**
 * 
 */
package no.hvl.dat159.des;

import javax.crypto.SecretKey;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author tosindo
 *
 */
public class Server implements IParent {

	private static SecretKey key;

	/**
	   * Main Method
	   * 
	   * @param args
	   */
	  public static void main(String args[])
	  {
		  Server server = new Server();
		  server.setup();
		  // Wait for requests
		  while (true) {
			  server.receiveAndSend();
		  }
	  }

	private void setup(){ key = Utility.generateKeyToFile(); }
	
	public void receiveAndSend() {
		ServerSocket server;
	    Socket client;
	    ObjectOutputStream oos;
	    ObjectInputStream ois;
	    
	    try{
	    	server = new ServerSocket(PORT);
	    	System.out.println("Waiting for requests from client...");
	    	client = server.accept();
	    	System.out.println("Connected to client at the address: "+client.getInetAddress());
	    	
	    	oos = new ObjectOutputStream(client.getOutputStream());
	        ois = new ObjectInputStream(client.getInputStream());

	        // Receive message from the client
	        byte[] clientMsg = (byte[]) ois.readObject();
	        clientMsg = Utility.decrypt(clientMsg, key);
	        
	        // Print the message in UTF-8 format
	        System.out.println("Message from Client: "+ new String(clientMsg, "UTF-8"));
	        
	        // Create a response to client if message received
	        String response = "No message received";
	        
	        if(ois != null){
	        	response = "Message received from client";
	        	// Send the plaintext response message to the client
	            oos.writeObject(Utility.encrypt(response.getBytes(), key));
	            oos.flush();
	        }
	        
	        // Close Client and Server sockets
	        client.close();
	        server.close();
	        oos.close();
	        ois.close();
	        
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
		
	}
}
