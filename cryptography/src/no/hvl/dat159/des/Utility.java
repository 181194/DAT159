/**
 * 
 */
package no.hvl.dat159.des;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author tosindo
 *
 */
public class Utility {

    public static SecretKey getKeyFromFile() {
        SecretKey key = null;
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Keyfile.xx"));
            key = (SecretKey) in.readObject();
            in.close();
            return key;
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return key;
    }

    public static SecretKey generateKeyToFile() {
        SecretKey key = null;
        try {
            key = KeyGenerator.getInstance("DES").generateKey();
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Keyfile.xx"));
            out.writeObject(key);
            out.close();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    public static byte[] encrypt(byte[] input, SecretKey key) throws Exception {
        Cipher ecipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, key);
        // Encrypt
        byte[] enc = ecipher.doFinal(input);
        // Encode bytes to base64 to get a string
        return Base64.getEncoder().encode(enc);
    }

    public static byte[] decrypt(byte[] input, SecretKey key) throws Exception {
        Cipher dcipher = Cipher.getInstance("DES");
        dcipher.init(Cipher.DECRYPT_MODE, key);
        // Decode base64 to get bytes
        byte[] dec = Base64.getDecoder().decode(input);
        return dcipher.doFinal(dec);
    }
}