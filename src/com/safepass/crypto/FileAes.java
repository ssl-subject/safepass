package com.safepass.crypto;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;

import com.safepass.crypto.exceptions.InvalidAesKeyException;


public class FileAes {
	private String data;
	private String name;
	private Aes aes;
	private final String prefix = ".safe";
	
	public FileAes(String name, String password) throws InvalidAesKeyException {
		aes = new Aes(password);
		this.name = name;
		File file = new File(name + prefix);
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try(FileReader reader = new FileReader(file)) {
			int c;
            String tmp = "";
            while((c=reader.read())!=-1){
            	tmp += (char)c;
            }
            data = aes.decrypt(tmp);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }   
		 
	}
	
	public boolean write(String newData) throws InvalidAesKeyException, InvalidKeyException {
		this.data = newData;
		try(FileWriter writer = new FileWriter(name + prefix, false)) {
			writer.write(aes.encrypt(data));
			writer.flush();
			return true;
        }
        catch(IOException ex){
        	System.out.println(ex.getMessage());
        	return false;
        } 
	}
	
	public String toString() {
		return this.data;
	}
	
	public byte[] getPassword() {
		return this.aes.getPassword();
	}
	
}
