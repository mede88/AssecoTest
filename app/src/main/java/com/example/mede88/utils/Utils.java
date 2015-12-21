package com.example.mede88.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



public class Utils {

    public static boolean checkUrlRegEx(String url){
        if(url.matches("^((http(s)?://))(\\w+\\.\\w+)(\\.\\w+)?")){
            return true;
        }
        else {
            return false;
        }
    }

    //returns a first byte of a hash string
    public static int firstHashByte(String hash){
        byte[] ba=null;
        try {
            ba = hash.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return ba[0];
    }

    //Creates a hash string from a string and returns a hash string
    public static String getHash(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
            //str.hashCode();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = str.getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();

            String hexStr = "";
            for (int i = 0; i < digest.length; i++) {
                hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
            }
            return hexStr;
        }
}

