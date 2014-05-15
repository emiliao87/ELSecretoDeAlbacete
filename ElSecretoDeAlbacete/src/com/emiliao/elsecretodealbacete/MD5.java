package com.emiliao.elsecretodealbacete;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	public MD5() {
		
	}
	
	public static String hash(String string) {
		 
        try {
            //.s. Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(string.getBytes());
            byte messageDigest[] = digest.digest();
 
            //.s. Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();
 
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
 
        return string.replaceAll("[.:/,%?#&=]","");
    }

}
