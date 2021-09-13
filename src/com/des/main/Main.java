package com.des.main;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Plain text::::ABCDEF0123456789");
		String testEncrypt1=DES.encrypt("ABCDEF0123456789", "0123456789ABCD");
		System.out.println("Encrypted text1:::::::"+testEncrypt1);
		String testDecrypt1=DES.decrypt(testEncrypt1, "0123456789ABCD");	
		System.out.println("Decrypted text:::::"+testDecrypt1);
		
		System.out.println("Plain text::::0123456789ABCDEF");
		String testEncrypt2=DES.encrypt("0123456789ABCDEF", "ABCDEF01234567");
		System.out.println("Encrypted text2:::::::"+testEncrypt2);
		String testDecrypt2=DES.decrypt(testEncrypt2, "ABCDEF01234567");	
		System.out.println("Decrypted text:::::"+testDecrypt2);
		
		System.out.println("Plain text::::C0B7A8D05F3A829C");
		String testEncrypt3=DES.encrypt("C0B7A8D05F3A829C", "14A7D67818CA18");
		System.out.println("Encrypted text3:::::::"+testEncrypt3);
		String testDecrypt3=DES.decrypt(testEncrypt3, "14A7D67818CA18");	
		System.out.println("Decrypted text:::::"+testDecrypt3);
		
		
	}

}
