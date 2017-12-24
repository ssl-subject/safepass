package com.safepass.windows;


import javax.swing.JFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.safepass.crypto.FileAes;
import com.safepass.crypto.exceptions.InvalidAesKeyException;

import javax.swing.JPasswordField;
import javax.swing.JButton;

public class LoginWindow extends Window {
	private static final long serialVersionUID = 5299139789490785384L;
	private JTextField textLogin;
	private JPasswordField textPassword;


	public LoginWindow() {
		super();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {

			e.printStackTrace();
		}
		
		setTitle("SafePass");
		setResizable(false);
		setBounds(100, 100, 352, 172);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblLogin = new JLabel("Login:");
		
		JLabel lblPassword = new JLabel("Password:");
		
		textLogin = new JTextField();
		textLogin.setColumns(10);
		
		textPassword = new JPasswordField();
		
		JButton btnContinue = new JButton("Continue");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblPassword, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textPassword, GroupLayout.DEFAULT_SIZE, 233, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblLogin, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textLogin, GroupLayout.DEFAULT_SIZE, 308, Short.MAX_VALUE))
						.addComponent(btnContinue, Alignment.TRAILING))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(35)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLogin)
						.addComponent(textLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(textPassword, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnContinue)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);
		
		Locale loc = new Locale("en","US");
		setLocale(loc);
		getInputContext().selectInputMethod(loc);
	     
		setLocationRelativeTo(null);
		pack();
		btnContinue.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						
						try {
							FileAes fa = new FileAes(textLogin.getText(), new String(textPassword.getPassword()));
							deactivate();
							new FileWindow(fa, textLogin.getText()).activate();
						} catch (InvalidAesKeyException e) {
							JOptionPane.showMessageDialog(null, "Invalid key");
						}
					}
					
				}).start();
			}
			
		});
	}
}
