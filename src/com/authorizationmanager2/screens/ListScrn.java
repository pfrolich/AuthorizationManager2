package com.authorizationmanager2.screens;

/**
 * @author pfrolich  
 * @version 1.0 
 * Date : September 2020
 * 
 * This Class is used for the program Passwordmanager and contains  the list screen for listing Users and Userid's
 */

import java.awt.*;
import javax.swing.*;

import com.authorizationmanager2.AuthorizationManager2;

public class ListScrn extends JPanel {
	/**
	 * Definitions: Panels, Buttons, ComboBoxes, TextAreas, TextFields, Labels,
	 * etc...
	 */
	private static final long serialVersionUID = 1L;
	private JPanel newPanel;
	private JPanel midPanel;
	private JPanel midBotPanel;
	private JPanel midTopPanel;

	private static JLabel titleInsert;

	public static JLabel status;
	public static JLabel txtName;
	public static JLabel loggedIn;

	private Font font24Ar = new Font("Arial", Font.BOLD, 24);

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

	public ListScrn() {
		newPanel = new JPanel();
		newPanel.setPreferredSize(new Dimension(700, 560));
		newPanel.setLayout(new FlowLayout());
		newPanel.setBackground(greend1);
		newPanel.setVisible(true);

		add(midPanel());
	}

	/*
	 * midPanel part of Listcrn
	 */
	private Component midPanel() {
		midPanel = new JPanel();
		midPanel.setPreferredSize(new Dimension(700, 560));
		midPanel.setLayout(new FlowLayout());
		midPanel.setBackground(greend1);

		midPanel.add(midTopPanel());
		midPanel.add(midBotPanel());

		return midPanel;
	}

	/*
	 * midTopPanel part of midPanel - Title of the table
	 */
	private Component midTopPanel() {
		midTopPanel = new JPanel();
		midTopPanel.setPreferredSize(new Dimension(700, 50));
		midTopPanel.setLayout(new FlowLayout());
		midTopPanel.setBackground(vlgreen);

		if (AuthorizationManager2.option.matches("user")) {
			titleInsert = (new JLabel("Listing all Users"));
		}
		if (AuthorizationManager2.option.matches("userid")) {
			titleInsert = (new JLabel("Listing all UserId's"));
		}
		titleInsert.setForeground(greend1);
		titleInsert.setFont(font24Ar);

		midTopPanel.add(titleInsert);

		return midTopPanel;
	}

	/*
	 * midBotPanel part of midPanel -location of the table user or userId
	 */
	private Component midBotPanel() {
		midBotPanel = new JPanel();
		midBotPanel.setPreferredSize(new Dimension(700, 480));
		midBotPanel.setLayout(new FlowLayout());
		midBotPanel.setBackground(vlgreen);

		midBotPanel.add(AuthorizationManager2.getJsp());

		return midBotPanel;
	}
}