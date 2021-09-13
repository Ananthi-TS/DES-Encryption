package com.des.main;

public class EncryptionUtil {
	
	
	/**
	 * This method accepts the 16 bit Hexadecimal number and
	 * converts it to 64-bit binary number
	 * 
	 * @param value - 16 bit hexadecimal number
	 * @return - 64 bit binary number
	 */
	public static String getBinaryString(String value) {
		String binaryNumber="";
		
		for(int i=0 ; i < 16 ; i++) {
			String binStr = Integer.toBinaryString(Integer.parseInt(String.valueOf(value.charAt(i)), 16));
			while(binStr.length() < 4) {
				binStr = "0" + binStr;
			}
			binaryNumber+=binStr;
		}
		return binaryNumber;
	}

	/**
	 * This method accepts the 64-bit binary number and
	 * converts it to 16 bit Hexadecimal number 
	 * 
	 * @param value - 64-bit binary number
	 * @return - 16 bit Hexadecimal number 
	 */
	public static String getHexaDecimalString(String value) {
		
		String hexString="";
	    for (int i = 0; i < 64; i += 4) {
	    	String tmp=value.substring(i, Math.min(64, i + 4));
	    	hexString += Integer.toHexString(Integer.parseInt(tmp, 2));
	    }
		return hexString;
	}
	
	/**
	 * This method accepts the 14 bit Hexadecimal number and
	 * converts it to 56-bit binary number
	 * 
	 * @param value - 14 bit hexadecimal number
	 * @return - 56 bit binary number
	 */
	public static String getBinaryKeyString(String value) {
		String binaryNumber="";
		
		for(int i=0 ; i < 14 ; i++) {
			String binStr = Integer.toBinaryString(Integer.parseInt(String.valueOf(value.charAt(i)), 16));
			while(binStr.length() < 4) {
				binStr = "0" + binStr;
			}
			binaryNumber+=binStr;
		}
		return binaryNumber;
	}
}
