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
import java.time.LocalDate;
import javax.swing.*;

import com.authorizationmanager2.AuthorizationManager2;
import com.authorizationmanager2.data.DataUser;

public class DelUserScrn extends JPanel implements ActionListener {

	/**
	 * Definitions: Panels, Buttons, ComboBoxes, TextAreas, TextFields, Labels,
	 * etc...
	 */
	private static final long serialVersionUID = 1L;
	private JPanel newPanel;
	private JPanel midPanel;
	private JPanel midLPanel;
	private JPanel midRPanel;
	private JPanel midBotPanel;
	private JPanel topPanel;

	private JLabel label;

	private JTextField inpFname;
	private JTextField inpSname;
	private JTextField inpEmail;
	private JTextField inpId;

	private JButton button;

	private int selId;

	private String delSdate;
	private String delFnm;
	private String delSnm;

	private Boolean status = false;

	private JComboBox<String> comboValid;
	private JComboBox<String> comboYrS;
	private JComboBox<String> comboMthS;
	private JComboBox<String> comboDayS;

	private LocalDate date = LocalDate.now();
	private int mthNow = date.getMonthValue();
	private int dayNow = date.getDayOfMonth();

	private Font fontBttn = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	private Font title = new Font("Arial", Font.BOLD, 24);
	private Font fontTxt = new Font("Arial", Font.BOLD, 18);

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

	public DelUserScrn() {
		newPanel = new JPanel();
		newPanel.setPreferredSize(new Dimension(700, 580));
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
		midPanel.setPreferredSize(new Dimension(700, 560));
		midPanel.setLayout(new FlowLayout());
		midPanel.setBackground(vlgreen);

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
		topPanel.setPreferredSize(new Dimension(660, 50));
		topPanel.setLayout(new FlowLayout());
		topPanel.setBackground(greend1);

		JLabel titleInsert = new JLabel("Delete User");
		titleInsert.setForeground(vlgreen);
		titleInsert.setFont(title);

		topPanel.add(titleInsert);

		return topPanel;
	}

	/*
	 * midLPanel part of midPanel -- Text labels
	 */
	private Component midLPanel() {
		midLPanel = new JPanel();
		midLPanel.setPreferredSize(new Dimension(180, 440));
		midLPanel.setLayout(new GridLayout(10, 1));
		midLPanel.setBackground(vlgreen);

		String[] labelText = { "UserId :", "Name :", "Surname :", "Email-address :", "Start-date :",
				"Valid :        " };

		for (int i = 0; i < 6; i++) {
			label = new JLabel(labelText[i]);
			label.setFont(fontBttn);
			label.setForeground(greend1);
			midLPanel.add(label);
		}

		return midLPanel;
	}

	/*
	 * MidRPanel part of midPanel -- Input textfield
	 */
	private Component midRPanel() {
		midRPanel = new JPanel();
		midRPanel.setPreferredSize(new Dimension(420, 440));
		midRPanel.setLayout(new GridLayout(10, 1));
		midRPanel.setBackground(vlgreen);

		JPanel id = new JPanel();
		id.setPreferredSize(new Dimension(420, 40));
		id.setBackground(vlgreen);
		id.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyI = new JTextField(30);
		emptyI.setBorder(null);
		emptyI.setEditable(false);
		emptyI.setBackground(vlgreen);

		inpId = new JTextField(5);
		inpId.setBackground(Color.white);
		inpId.setFont(fontTxt);
		inpId.addActionListener(this);
		inpId.setActionCommand("id");
		inpId.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
					inpId.setEditable(true);
				} else {
					if (ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE
							|| ke.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
						inpId.setEditable(true);
					} else {
						inpId.setEditable(false);
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message, "* Enter only numeric digits(0-9)", "INFO",
								JOptionPane.INFORMATION_MESSAGE);
						inpId.setEditable(true);
					}
				}
			}
		});

		id.add(emptyI);
		id.add(inpId);

		JPanel name = new JPanel();
		name.setPreferredSize(new Dimension(420, 40));
		name.setBackground(vlgreen);
		name.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyN = new JTextField(25);
		emptyN.setBorder(null);
		emptyN.setEditable(false);
		emptyN.setBackground(vlgreen);

		inpFname = new JTextField(23);
		inpFname.setFont(fontTxt);
		inpFname.setBackground(Color.white);

		name.add(emptyN);
		name.add(inpFname);

		JPanel sname = new JPanel();
		sname.setPreferredSize(new Dimension(420, 40));
		sname.setBackground(vlgreen);
		sname.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyS = new JTextField(25);
		emptyS.setBorder(null);
		emptyS.setEditable(false);
		emptyS.setBackground(vlgreen);

		inpSname = new JTextField(23);
		inpSname.setFont(fontTxt);
		inpSname.setBackground(Color.white);

		sname.add(emptyS);
		sname.add(inpSname);

		JPanel email = new JPanel();
		email.setPreferredSize(new Dimension(420, 40));
		email.setBackground(vlgreen);
		email.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyE = new JTextField(30);
		emptyE.setBorder(null);
		emptyE.setEditable(false);
		emptyE.setBackground(vlgreen);

		inpEmail = new JTextField(23);
		inpEmail.setFont(fontTxt);
		inpEmail.setBackground(Color.white);

		email.add(emptyE);
		email.add(inpEmail);

		LocalDate date = LocalDate.now();
		int fromYr = date.getYear();
		int mthNow = date.getMonthValue();
		int dayNow = date.getDayOfMonth();
		int toYr = fromYr + 100;

		String cbMth[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

		comboYrS = new JComboBox<String>();
		comboYrS.setFont(fontTxt);

		for (int i = fromYr; i < toYr; i++) {
			String itemYr = Integer.toString(i);
			comboYrS.addItem(itemYr);
		}

		comboMthS = new JComboBox<String>(cbMth);
		comboMthS.setFont(fontTxt);

		comboDayS = new JComboBox<String>();
		comboDayS.setFont(fontTxt);
		for (int i = 1; i < 32; i++) {
			String itemDay = Integer.toString(i);

			comboDayS.addItem(itemDay);
		}

		comboMthS.setSelectedIndex(mthNow - 1);
		comboDayS.setSelectedIndex(dayNow - 1);

		JPanel comboDateBox = new JPanel();
		comboDateBox.setPreferredSize(new Dimension(420, 40));
		comboDateBox.setLayout(new FlowLayout(0, 10, 0));
		comboDateBox.setBackground(vlgreen);

		JTextField emptyD = new JTextField(40);
		emptyD.setBackground(vlgreen);
		emptyD.setBorder(null);

		comboDateBox.add(emptyD);
		comboDateBox.add(comboYrS);
		comboDateBox.add(comboMthS);
		comboDateBox.add(comboDayS);

		JPanel valid = new JPanel();
		valid.setPreferredSize(new Dimension(420, 40));
		valid.setBackground(vlgreen);
		valid.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyV = new JTextField(40);
		emptyV.setBorder(null);
		emptyV.setEditable(false);
		emptyV.setBackground(vlgreen);

		String cbValid[] = { "true", "false" };
		comboValid = new JComboBox<String>(cbValid);
		comboValid.setFont(fontTxt);

		valid.add(emptyV);
		valid.add(comboValid);

		midRPanel.add(id);
		midRPanel.add(name);
		midRPanel.add(sname);
		midRPanel.add(email);
		midRPanel.add(comboDateBox);
		midRPanel.add(valid);

		return midRPanel;
	}

	/*
	 * midBotPanel part of midPanel -- Buttons
	 */
	private Component midBotPanel() {
		midBotPanel = new JPanel();
		midBotPanel.setPreferredSize(new Dimension(660, 50));
		midBotPanel.setLayout(new FlowLayout());
		midBotPanel.setBackground(greend1);

		String[] buttonNames = { "Delete", "Clear", "Back" };
		for (int i = 0; i < 3; i++) {
			button = new JButton(buttonNames[i]);
			button.setActionCommand(buttonNames[i]);
			button.setPreferredSize(new Dimension(100, 30));
			button.setFont(fontBttn);
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

			AuthorizationManager2.getMidRightPanel().removeAll();
			AuthorizationManager2.getMidLeftTopPanel().removeAll();

			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getEmpty2());
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.codeTxt);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.codeInp);

			AuthorizationManager2.getMidLeftTopPanel().validate();
			AuthorizationManager2.getMidLeftTopPanel().repaint();

			AuthorizationManager2.getMidRightPanel().validate();
			AuthorizationManager2.getMidRightPanel().repaint();
			break;
		}
		/*
		 * clear input text fields
		 */
		case "Clear": {

			AuthorizationManager2.getMidRightPanel().removeAll();

			AuthorizationManager2.getMidRightPanel().add(new DelUserScrn(), "newPanel");

			AuthorizationManager2.getMidRightPanel().revalidate();
			AuthorizationManager2.getMidRightPanel().repaint();
			break;
		}
		/*
		 * Delete the record in the DataUserId and DataUser arraylist
		 */
		case "Delete":
			if (status) {
				selId = Integer.parseInt(inpId.getText());
				delFnm = inpFname.getText();
				delSnm = inpSname.getText();

				String delYrS = (String) comboYrS.getSelectedItem();
				String delMthS = (String) comboMthS.getSelectedItem();
				String delDayS = (String) comboDayS.getSelectedItem();

				delSdate = delYrS + "-" + delMthS + "-" + delDayS;
				int dSize = AuthorizationManager2.userData.size();
				int dIdSize = AuthorizationManager2.userIdData.size();

				int reply = JOptionPane.showConfirmDialog(this,
						"Are you want to delete " + delFnm + " " + delSnm + " ?", "Warning", JOptionPane.YES_NO_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					for (int i = 0; i < dSize; i++) {
						System.out.println(AuthorizationManager2.userData.get(i).getId());
						if (AuthorizationManager2.userData.get(i).getId() == selId) {
							AuthorizationManager2.userData.remove(i);
							i--;
							dSize--;
						}
					}
					for (int i = 0; i < dIdSize; i++) {
						System.out.println(AuthorizationManager2.userIdData.get(i).getId());
						if (AuthorizationManager2.userIdData.get(i).getId() == selId) {
							AuthorizationManager2.userIdData.remove(i);
							i--;
							dIdSize--;
						}
					}
					AuthorizationManager2.getLog().info("User: " + delFnm + " " + delSnm + " with startdate: "
							+ delSdate + " is deleted \n auth_id = " + selId);
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "User " + inpSname.getText() + " is deleted", "INFO",
							JOptionPane.INFORMATION_MESSAGE);
					inpId.setText("");
					inpFname.setText("");
					inpSname.setText("");
					inpEmail.setText("");
					comboValid.setSelectedIndex(0);
					comboMthS.setSelectedIndex(mthNow - 1);
					comboDayS.setSelectedIndex(dayNow - 1);
					status = false;

				} else if (reply == JOptionPane.NO_OPTION) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "User " + inpSname.getText() + " is not deleted", "INFO",
							JOptionPane.INFORMATION_MESSAGE);
				}
				break;
			}
			/*
			 * get the data with the key id from the arraylist
			 */
		case "id":
			status = false;
			selId = Integer.parseInt(inpId.getText());

			for (DataUser d1 : AuthorizationManager2.userData) {
				int userid = d1.getId();
				if (selId == userid) {
					String fName = d1.getfName();
					String sName = d1.getsName();
					String email = d1.getEmail();
					String sdate = d1.getsDate();
					String act = d1.getAct();
					int isDayS = Integer.parseInt(sdate.substring(8));

					inpFname.setText(fName);
					inpSname.setText(sName);
					inpEmail.setText(email);
					comboYrS.setSelectedItem(sdate.substring(0, 4));
					comboMthS.setSelectedItem(sdate.substring(5, 7));
					comboDayS.setSelectedIndex(isDayS - 1);
					comboValid.setSelectedItem(act);

					inpId.setBackground(Color.red);
					inpId.setEditable(false);
					inpId.setEnabled(false);

					status = true;
					return;
				}

			}
			JFrame messageI = new JFrame();
			JOptionPane.showMessageDialog(messageI, "Not a valid Id!!!", "Info", JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}
}