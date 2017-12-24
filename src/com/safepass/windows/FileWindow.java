package com.safepass.windows;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;

import com.safepass.crypto.FileAes;
import com.safepass.crypto.exceptions.InvalidAesKeyException;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.InvalidKeyException;

public class FileWindow extends Window {

	private static final long serialVersionUID = -8372811713102387532L;
	
	private JPasswordField passwordField;
	private JTextPane textPane;
	@SuppressWarnings("unused")
	private FileAes file;

	public FileWindow(FileAes file, String name) {
		super();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.file = file;
		setTitle("SafePass ("+ name +")");
		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnSave = new JButton("Save");
		
		passwordField = new JPasswordField();
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 464, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSave)
							.addGap(9))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
					.addGap(9)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		textPane = new JTextPane();
		textPane.setFont(new Font("Consolas", Font.PLAIN, 12));
		textPane.setText(file.toString());
		scrollPane.setViewportView(textPane);
		getContentPane().setLayout(groupLayout);

		setLocationRelativeTo(null);
		
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						
						if(new String(file.getPassword()).equals(new String(passwordField.getPassword()))) {
							try {
								file.write(textPane.getText());
								passwordField.setText("");
								JOptionPane.showMessageDialog(null, "Success!");
							} catch (InvalidAesKeyException | InvalidKeyException e) {
								JOptionPane.showMessageDialog(null, "Error! Please restart application!");
							}
						} else {
							JOptionPane.showMessageDialog(null, "Invalid key.");
						}
					}
					
				}).start();
				
			}
			
		});
	}
}
