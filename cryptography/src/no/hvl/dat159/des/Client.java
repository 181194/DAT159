/**
 * 
 */
package no.hvl.dat159.des;

import javax.crypto.SecretKey;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @author tosindo
 *
 */
public class Client implements IParent {

	private static SecretKey key;
	
	public static void main(String[] args){
		Client client = new Client();
		key = Utility.getKeyFromFile();
		client.sendAndReceice();
	}
	
	public void sendAndReceice() {
		Socket client;
	    ObjectOutputStream oos;
	    ObjectInputStream ois;
	    
	    try {
	    	client = new Socket("localhost", PORT);
			
	    	System.out.println("Connected to Server on "+ client.getInetAddress());

	    	oos = new ObjectOutputStream(client.getOutputStream());
	    	ois = new ObjectInputStream(client.getInputStream());
	    	
	    	// send a plaintext message to server
	    	String plaintxt = "Hello from client";

	    	
	    	// send message to server
	    	oos.writeObject(Utility.encrypt(plaintxt.getBytes(), key));
	    	oos.flush();
	    	
	    	// receive response from server
	    	byte[] response = (byte[]) ois.readObject();
	    	response = Utility.decrypt(response, key);
	    	
	    	System.out.println("Response from server: "+ new String(response, "ASCII"));
	    	
	    	// close cliet socket
	    	client.close();
	    	ois.close();
	    	oos.close();
	    	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
