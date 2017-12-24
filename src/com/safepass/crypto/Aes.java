package com.safepass.crypto;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import com.safepass.crypto.exceptions.InvalidAesKeyException;

public class Aes {
	private Cipher cipher;
	private SecretKeySpec key;
	private byte[] password;
	
	public Aes(byte[] password) {
		init(password);	
	}
	public Aes(String password) {
		init(password.getBytes());
	}
	
	private  void init(byte[] password) {
		this.password = password;
		try {
			byte[] buff = new byte[16];
			for(int i = 0; i < 16; i++) {
				
				if(password.length > i) {
					buff[i] = password[i];
				} else {
					buff[i] = 0;
				}
			}
			
			key = new SecretKeySpec(buff, "AES");
			cipher = Cipher.getInstance("AES");
			
		} catch(NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
	}
	
	public byte[] encrypt(byte[] text) throws InvalidAesKeyException {
		try {
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return Base64.getEncoder().encode(cipher.doFinal(text));
		} catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException  e) {
			
			e.printStackTrace();
			throw new InvalidAesKeyException("Invalid Key", password);
		}
	}
	public String encrypt(String text) throws InvalidAesKeyException {
		return new String(encrypt(text.getBytes()));
	}
	

	public byte[] decrypt(byte[] text) throws InvalidAesKeyException {

		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			return cipher.doFinal(Base64.getDecoder().decode(text));
		}  catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			throw new InvalidAesKeyException("Invalid Key", password);
		}
	}
	public String decrypt(String text) throws InvalidAesKeyException {
		return new String(decrypt(text.getBytes()));
	}
	
	public byte[] getPassword() {
		return password;
	}
}
