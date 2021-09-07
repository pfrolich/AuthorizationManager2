package com.authorizationmanager2.tabbedpane;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.authorizationmanager2.AuthorizationManager2;

public class Console extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static JTextArea console;
	public static int w;
	public static int h;
 
	public Console() {
		w = AuthorizationManager2.scrW;
		h = AuthorizationManager2.scrH;
		console = new JTextArea((int) (h * 0.0035), (int) (w * 0.022));
		console.setBackground(Color.white);
		console.setBorder(AuthorizationManager2.border);
		console.setEditable(false);
		JScrollPane scrollConsole = new JScrollPane(console);
		
		add(scrollConsole);
	}

}