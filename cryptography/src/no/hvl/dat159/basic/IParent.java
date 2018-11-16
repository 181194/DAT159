/**
 * 
 */
package no.hvl.dat159.basic;

/**
 * @author tosindo
 *
 */
public interface IParent {
	
	public static final int PORT = 9091;
	
	public byte[] encryptMessage(byte[] plaintext);
	
	public byte[] decryptMessage(byte[] ciphertext);
	
}
