package com.authorizationmanager2.screens;

/**
 * @author pfrolich  
 * @version 1.0 
 * Date : September 2020
 * 
 * This Class is used for the program Passwordmanager and contains  the delete screen fordeleting users
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.authorizationmanager2.AuthorizationManager2;
import com.authorizationmanager2.tabbedpane.Console;

public class ConsoleScrn extends JPanel implements ActionListener {
	/*
	 * Full screen definitions
	 */
	public static int w;
	public static int h;
	/**
	 * Definitions: Panels, Buttons, ComboBoxes, TextAreas, TextFields, Labels,
	 * etc...
	 */
	private static final long serialVersionUID = 1L;
	private JPanel newPanel;
	private JPanel midPanel;
	private JPanel midLPanel;
	private JPanel midRPanel;
	private JPanel midRTopPanel;
	private JPanel midRBotPanel;
	private JPanel midBotPanel;
	private JPanel topPanel;
	
	public static String titleIns;

	private JButton button;

	public static Boolean status = false;

	public static Color blue1 = new Color(173, 193, 235);
	public static Color vlblue = new Color(235, 235, 250);
	public static Color blue2 = new Color(179, 198, 255);
	public static Color blue = new Color(0, 60, 179);
	public static Color blued4 = new Color(0, 60, 179);
	public static Color blued2 = new Color(0, 34, 102);
	public static Color blued3 = new Color(20, 20, 82);
	public static Color blued1 = new Color(0, 43, 128);

	public static Color greend1 = new Color(26, 51, 0);
	public static Color greend2 = new Color(40, 77, 0);
	public static Color greend3 = new Color(0, 43, 128);
	public static Color greend4 = new Color(0, 43, 128);
	public static Color vlgreen = new Color(235, 250, 235);
	public static Color green1 = new Color(46, 184, 46);

	Thread thread1;

	public ConsoleScrn() {
		w = AuthorizationManager2.scrW;
		h = AuthorizationManager2.scrH;
		newPanel = new JPanel();
		newPanel.setPreferredSize(new Dimension((int) (w * 0.55), (int) (h * 0.78)));
		newPanel.setLayout(new GridLayout());
		newPanel.setBackground(greend1);
		newPanel.setVisible(true);

		add(midPanel());
	}

	/*
	 * MidPanel part of DelUserIdScrn
	 */
	private Component midPanel() {
		midPanel = new JPanel();
		midPanel.setPreferredSize(new Dimension((int) (w * 0.515), (int) (h * 0.75)));
		midPanel.setLayout(new FlowLayout());
		midPanel.setBackground(greend1);

		midPanel.add(topPanel());
		midPanel.add(midLPanel());
		midPanel.add(midRPanel());
		midPanel.add(midBotPanel());

		return midPanel;
	}

	/*
	 * TopPanel part of midPanel - Title
	 */
	private Component topPanel() {
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension((int) (w * 0.55), (int) (h * 0.07)));
		topPanel.setLayout(new FlowLayout());
		topPanel.setBackground(greend1);

		JLabel titleInsert = new JLabel(titleIns);
		titleInsert.setForeground(vlgreen);
		titleInsert.setFont(AuthorizationManager2.font24);

		topPanel.add(titleInsert);

		return topPanel;
	}

	/*
	 * midLPanel part of midPanel -- Text labels
	 */
	private Component midLPanel() {
		midLPanel = new JPanel();
		midLPanel.setPreferredSize(new Dimension((int) (w * 0.14), (int) (h * 0.61))); // 180,440
		midLPanel.setLayout(new GridLayout(10, 1));
		midLPanel.setBackground(vlgreen);

		return midLPanel;
	}

	/*
	 * midRPanel part of midPanel -- contains midRTopPanel & midRBotPanel
	 */
	private Component midRPanel() {
		midRPanel = new JPanel();
		midRPanel.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.61)));
		midRPanel.setLayout(new FlowLayout());
		midRPanel.setBackground(vlgreen);

		midRPanel.add(midRTopPanel());
		midRPanel.add(midRBotPanel());

		return midRPanel;
	}

	/*
	 * midRTopPanel part of midRPanel -- Input textfield
	 */
	private Component midRTopPanel() {

		midRTopPanel = new JPanel();
		midRTopPanel.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.51))); // 420,440
		midRTopPanel.setLayout(new GridLayout(8, 1));
		midRTopPanel.setBackground(vlgreen);

		return midRTopPanel;
	}

	/*
	 * MidRBotPanel -- console
	 */
	private Component midRBotPanel() {
		midRBotPanel = new JPanel();
		midRBotPanel.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.21)));
		midRBotPanel.setLayout(new FlowLayout());
		midRBotPanel.setBackground(vlgreen);

		JTabbedPane tabbed = new JTabbedPane(JTabbedPane.LEFT);
		tabbed.setBackground(vlgreen);
		tabbed.setBorder(AuthorizationManager2.border);
		tabbed.add(new Console(), "Message");

		midRBotPanel.add(tabbed);

		return midRBotPanel;
	}

	/*
	 * MidBotPanel -- buttons
	 */
	private Component midBotPanel() {

		int e100 = (int) Math.round(w * 0.078);
		int e30 = (int) Math.round(h * 0.042);
		midBotPanel = new JPanel();
		midBotPanel.setPreferredSize(new Dimension((int) (w * 0.515), (int) (h * 0.069))); // 660,50
		midBotPanel.setLayout(new FlowLayout());
		midBotPanel.setBackground(greend1);

		String[] buttonNames = { "Back" };
		for (int i = 0; i < 1; i++) {
			button = new JButton(buttonNames[i]);
			button.setActionCommand(buttonNames[i]);
			button.setPreferredSize(new Dimension(e100, e30));
			button.setFont(AuthorizationManager2.font18);
			button.setBorder(AuthorizationManager2.border);
			button.setForeground(greend1);
			button.addActionListener(this);
			midBotPanel.add(button);
		}

		return midBotPanel;
	}

	/**
	 * actionPerformed
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		switch (action) {
		/*
		 * go back to the previous screen
		 */
		case "Back": {
			AuthorizationManager2.cpyArea.setText(Console.console.getText());
			AuthorizationManager2.getMidRightPanel().removeAll();
			AuthorizationManager2.getMidLeftTopPanel().removeAll();

			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getEmpty2());
			
			AuthorizationManager2.getMidRightPanel().add(new ConsoleScrn(), "newPanel");

			AuthorizationManager2.getMidLeftTopPanel().validate();
			AuthorizationManager2.getMidLeftTopPanel().repaint();

			AuthorizationManager2.getMidRightPanel().validate();
			AuthorizationManager2.getMidRightPanel().repaint();
			AuthorizationManager2.setScreen("default");
			Console.console.setText(AuthorizationManager2.cpyArea.getText());
			break;
			}
		}
	}
}