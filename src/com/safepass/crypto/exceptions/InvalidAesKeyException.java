package com.safepass.crypto.exceptions;

public class InvalidAesKeyException extends Exception {
	private static final long serialVersionUID = 3332588416785176925L;
	private byte[] key;
	public InvalidAesKeyException(String s, byte[] key) {
		super(s);
		this.key = key;
	}
	
	public byte[] getKey() {
		return key;
	}

}
