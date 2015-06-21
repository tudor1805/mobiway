package ro.pub.acs.traffic.utils;

import java.security.*;

public class HashString {

    /* Usage 
     String str = "aqwsedrftg";
     HashString hs = new HashString("SHA1");
     System.out.println(hs.hash(str));
     */
    private MessageDigest md;

    public HashString(String algorithm) {
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static String bytesToHex(byte[] b) {
        char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        StringBuilder buf = new StringBuilder();
        for (int j = 0; j < b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }

    public String hash(String str) {
        md.update(str.getBytes());
        byte[] output = md.digest();
        return bytesToHex(output).toLowerCase();
    }

}
