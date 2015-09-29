package tr.org.mmo.asansor.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

import tr.org.mmo.asansor.exception.SystemUnavailableException;



public class PassEncript {
	private static PassEncript instance;

	 /*  private PassEncript()
	   {
		   
	   }
	   */


	  public synchronized String encrypt(String plaintext) throws SystemUnavailableException
	   {
	    MessageDigest md = null;
	     Hex hex = new Hex();

	     //String plaintext=new String(plaintextx);
	     try
	     {
	       md = MessageDigest.getInstance("SHA-1"); //step 2
	     }
	     catch(NoSuchAlgorithmException e)
	     {
	       throw new SystemUnavailableException(e.getMessage());
	     }
	     try
	     {
	       md.update(plaintext.getBytes("UTF-8")); //step 3
	     }
	     catch(UnsupportedEncodingException e)
	     {
	       throw new SystemUnavailableException(e.getMessage());
	     }

	     byte raw[] = md.digest(); //step 4
//	     String hash = (new BASE64Encoder()).encode(raw); //step 5
	     String hash = new String(hex.encode(raw)); //step 5
//	     hash=hash.toUpperCase();
	     return hash.toUpperCase(); //step 6
	   }

	public static synchronized PassEncript getInstance() {
		if(instance == null)
	     {
	        instance = new PassEncript(); 
	     } 
	     return instance;
	}

	public static void setInstance(PassEncript instance) {
		PassEncript.instance = instance;
	}

	 


}
