package com.des.main;

public class DES {

	public static int[] initialPermutation= {
			58,50,42,34,26,18,10,2,
			60,52,44,36,28,20,12,4,
			62,54,46,38,30,22,14,6,
			64,56,48,40,32,24,16,8,
			57,49,41,33,25,17,9,1,
			59,51,43,35,27,19,11,3,
			61,53,45,37,29,21,13,5,
			63,55,47,39,31,23,15,7
	};
	
	public static int[] finalPermutation= {
			40,8,48,16,56,24,64,32,
			39,7,47,15,55,23,63,31,
			38,6,46,14,54,22,62,30,
			37,5,45,13,53,21,61,29,
			36,4,44,12,52,20,60,28,
			35,3,43,11,51,19,59,27,
			34,2,42,10,50,18,58,26,
			33,1,41,9,49,17,57,25
	};
	
	public static int[] key1= {
			56,48,40,32,24,16,8,
			7,15,49,41,33,25,17,
			9,1,23,50,26,34,42,
			18,10,2,31,51,43,35,
			47,54,46,38,30,22,14,
			6,55,53,45,37,29,21,
			13,5,39,52,44,36,28,
			20,12,4,27,19,11,3
	};
			
	public static int[] key2= {
			14,17,11,24,1,5,3,28,
			15,6,43,10,23,19,12,4,
			26,8,16,7,27,20,13,2,
			41,52,31,37,47,55,30,40,
			51,45,33,48,44,49,39,56,
			34,53,46,42,50,36,29,32
	};
	
	
	/**
	 * This method accepts the 16 bit Hexadecimal number representing 64 bit number
	 * and 14 bit hexadecimal key value as 56 bit key
	 * The programs encrypts the value using DES algorithm 
	 * The cipher text is returned in hexadecimal format
	 * 
	 * @param key - 14 bit hexadecimal number
	 * @param plaintext - 16 bit hexadecimal number
	 * @return - 16 bit hexadecimal cipher text
	 */
	public static String encrypt(String plaintext, String key) {
		String encryptedText="";
		String[] keyArr=new String[16];
		
		String testTemp=EncryptionUtil.getBinaryString(plaintext); //converts input hexadecimal to binary
		String binaryValue=EncryptionUtil.getBinaryKeyString(key); //converts input key to binary
		keyArr = getKeys(binaryValue); //generates the keys for 16 rounds
		
		String encode="";
		for(int perm:initialPermutation) {
			encode+=testTemp.charAt(perm-1); //Initial permutation on the plain text
		}
		
		for(int i=0;i<16;i++) {
			String leftNumber=encode.substring(0,32); //Left number from permuted string
			String rightNumber=encode.substring(32,64); //Right number from permuted string
			
			String newLeft=rightNumber; //left number is assigned as  right number for next round
			//mangler function
			String rightNumber48=rightNumber+"0000000000000000"; //appending 16 zeroes to form a 48-bit number for XOR operation
			String ki=keyArr[i];
			StringBuffer sb=new StringBuffer();
	        for (int j = 0; j < 48; j++) {
	            sb.append(rightNumber48.charAt(j)^ki.charAt(j)); //xOR operation
	        }
			
	        String manglerXor=sb.toString().substring(0,32); //result is again converted to 32-bit
	        
	        StringBuffer builder=new StringBuffer();
	        for (int x=0;x< 32;x++) {
	        	builder.append(leftNumber.charAt(x)^manglerXor.charAt(x)); //The value from mangler function is again xORed with left value and stored as right number
	        }
	       String newRight=builder.toString();
	       encode=newLeft+newRight; //concatenating left and right numbers a
	        
		}
		
		String finalEncrypt="";
		
		for(int perm:finalPermutation) {
			finalEncrypt+=encode.charAt(perm-1); //final permutation is applied on the final value after 16 rounds
		}
		
		encryptedText=EncryptionUtil.getHexaDecimalString(finalEncrypt); //the final value is converted into hexadecimal and returned
		
		return encryptedText.toUpperCase();
	}


	/**
	 * Private method to generate 16 keys from given 56-bit key
	 * 
	 * @param key - 56 bit binary key number
	 * @return - String array containing all the 16 keys
	 */
	private static String[] getKeys(String binaryValue) {
		String[] keyArr=new String[16];
		String permutedKey="";
		for(int perm:key1) {
			permutedKey+=binaryValue.charAt(perm-1); //Initial permutation for 56-bit key
		}
		String c0=permutedKey.substring(0,28); //56-bit key is divided 
		String d0=permutedKey.substring(28,56);		
		
		for(int k=0;k<16;k++) {
			String c1=c0.charAt(27)+"";
			for(int i=0;i<27;i++) {
				c1+=c0.charAt(i);  //c0 is rotated one shift left
			}
			
			String d1=d0.charAt(27)+"";
			for(int i=0;i<27;i++) {
				d1+=d0.charAt(i); //d0 is rotated one shift right
			} 
				
			String combined=c1+d1;	//values are combined
			String permutedValue="";
			for(int perm:key2) {
				permutedValue+=combined.charAt(perm-1); //again key permutation is applied and bits are discarded
			}
			keyArr[k] = permutedValue; //key value generated stored in the array
			c0=c1;
			d0=d1;
		}
		
		return keyArr;
	}
	
	/**
	 * This method accepts the 16 bit Hexadecimal number representing 64 bit cipher number
	 * and 14 bit hexadecimal key value as 56 bit key
	 * The programs decrypts the value using DES algorithm 
	 * The plain text is returned in hexadecimal format
	 * 
	 * @param key - 14 bit hexadecimal number
	 * @param plaintext - 16 bit hexadecimal number
	 * @return - 16 bit hexadecimal plain text
	 */
	public static String decrypt(String decryptString,String key) {
		String plainText="";
		String[] keyArr=new String[16];
		
		String binaryStr=EncryptionUtil.getBinaryString(decryptString); //converts input hexadecimal to binary
		String initialPerm="";
		
		String binaryValue=EncryptionUtil.getBinaryKeyString(key); //converts input key to binary
		keyArr = getKeys(binaryValue); //generates the keys for 16 rounds
		
		for(int perm:initialPermutation) {  //permutation on input string
			initialPerm+=binaryStr.charAt(perm-1);
		}
		for(int i=15;i>=0;i--) { //generating the input string by iterating from k16,k15,k14,....,k1
			
			String left=initialPerm.substring(0,32); //permuted string splitted into two values
			String right=initialPerm.substring(32,64);	
			String newRight=left; //left string is assigned as right for next round
			String strFrManglr=left;
			
			String rightNumber48=strFrManglr+"0000000000000000"; //mangler operation performed on the right string
			String ki=keyArr[i];
			StringBuffer sb=new StringBuffer();
	        for (int j = 0; j < 48; j++) {
	            sb.append(rightNumber48.charAt(j)^ki.charAt(j)); //xOR - Mangler operation
	        }
			
	        String manglerXor=sb.toString().substring(0,32); //48 bit value converted to 32 bit string
	        
	        StringBuffer builder=new StringBuffer();
	        for (int x=0;x< 32;x++) {
	        	builder.append(right.charAt(x)^manglerXor.charAt(x)); //xOR operation for result from mangler function and right bit
	        }
	       String newLeft=builder.toString();
	       initialPerm=newLeft+newRight; //values combined to form 64-bit string
		}
		
		String finalEncrypt="";
		
		for(int perm:finalPermutation) { //final permutation applied on the result
			finalEncrypt+=initialPerm.charAt(perm-1);
		}
		plainText=EncryptionUtil.getHexaDecimalString(finalEncrypt); //value returned as hexadecimal
		
		return plainText.toUpperCase();
	}

}
