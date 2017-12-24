package com.safepass.windows;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public abstract class Window extends JFrame {
	public static Window now;
	
	
	public Window() {
		Image image = Toolkit.getDefaultToolkit().createImage( getClass().getResource("/icon.gif") );
		setIconImage( image );
	}
	public void activate() {
		
		this.setVisible(true);
	}
	
	public void deactivate() {
		this.setVisible(false);
	}
	
}
