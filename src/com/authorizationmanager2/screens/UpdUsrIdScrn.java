package com.authorizationmanager2.screens;

/**
 * @author pfrolich  
 * @version 1.0 
 * Date : September 2020
 * 
 * This Class is used for the program Authorizationmanager and contains  the update screen for userid's
 */

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;

import javax.swing.*;

import com.authorizationmanager2.AuthorizationManager2;
import com.authorizationmanager2.data.DataUserId;

public class UpdUsrIdScrn extends JPanel implements ActionListener {

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

	private JTextField inpUser;
	private JTextField inpPassW;
	private JTextField inpId;

	private JButton button;

	private int selId;
	private String selUser;
	private String selPwd;
	private String updSdate;
	private String updEdate;
	private String updValid;
	private String updLvl;
	private String updBlock;

	private Boolean status = false;

	private JComboBox<String> comboValid;
	private JComboBox<String> comboBlock;
	private JComboBox<String> comboLvl;
	private JComboBox<String> comboYrS;
	private JComboBox<String> comboMthS;
	private JComboBox<String> comboDayS;
	private JComboBox<String> comboYrE;
	private JComboBox<String> comboMthE;
	private JComboBox<String> comboDayE;

	private LocalDate date = LocalDate.now();
	private int mthNow;
	private int dayNow;

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

	public UpdUsrIdScrn() {
		newPanel = new JPanel();
		newPanel.setPreferredSize(new Dimension(700, 580));
		newPanel.setLayout(new GridLayout());
		newPanel.setBackground(greend1);
		newPanel.setVisible(true);

		add(midPanel());
	}

	/*
	 * MidPanel part of UpdUsrIdScrn
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
	 * TopPanel  part of midPanel - Title
	 */
	private Component topPanel() {
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(660, 50));
		topPanel.setLayout(new FlowLayout());
		topPanel.setBackground(greend1);

		JLabel titleInsert = new JLabel("Update  UserId's");
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

		String[] labelText = { "UserId", "Username:", "         ", "Start-date:", "End-date:", "Valid:        ",
				"Blocked:", "Auth-level: " };

		for (int i = 0; i < 8; i++) {
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

		JPanel user = new JPanel();
		user.setPreferredSize(new Dimension(420, 40));
		user.setBackground(vlgreen);
		user.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyU = new JTextField(25);
		emptyU.setBorder(null);
		emptyU.setEditable(false);
		emptyU.setBackground(vlgreen);

		inpUser = new JTextField(23);
		inpUser.setBackground(Color.white);
		inpUser.setFont(fontTxt);
		inpUser.addActionListener(this);
		inpUser.setActionCommand("user");

		user.add(emptyU);
		user.add(inpUser);

		inpPassW = new JTextField(12);
		inpPassW.setBackground(vlgreen);
		inpPassW.setBorder(null);
		inpPassW.setEditable(false);

		int fromYr = date.getYear();
		mthNow = date.getMonthValue();
		dayNow = date.getDayOfMonth();
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

		comboYrE = new JComboBox<String>();
		comboYrE.setFont(fontTxt);
		comboYrE.addItem("9999");

		for (int i = fromYr; i < toYr; i++) {
			String itemYr = Integer.toString(i);
			comboYrE.addItem(itemYr);
		}

		comboMthE = new JComboBox<String>(cbMth);
		comboMthE.setFont(fontTxt);

		comboDayE = new JComboBox<String>();
		comboDayE.setFont(fontTxt);

		for (int i = 31; i > 0; i--) {
			String itemDay = Integer.toString(i);

			comboDayE.addItem(itemDay);
		}

		comboMthS.setSelectedIndex(mthNow - 1);
		comboDayS.setSelectedIndex(dayNow - 1);
		comboMthE.setSelectedIndex(11);

		JPanel comboDateBoxS = new JPanel();
		comboDateBoxS.setPreferredSize(new Dimension(420, 40));
		comboDateBoxS.setLayout(new FlowLayout(0, 10, 0));
		comboDateBoxS.setBackground(vlgreen);

		JTextField emptyS = new JTextField(40);
		emptyS.setBackground(vlgreen);
		emptyS.setBorder(null);

		comboDateBoxS.add(emptyS);
		comboDateBoxS.add(comboYrS);
		comboDateBoxS.add(comboMthS);
		comboDateBoxS.add(comboDayS);

		JPanel comboDateBoxE = new JPanel();
		comboDateBoxE.setPreferredSize(new Dimension(420, 40));
		comboDateBoxE.setLayout(new FlowLayout(0, 10, 0));
		comboDateBoxE.setBackground(vlgreen);

		JTextField emptyE = new JTextField(40);
		emptyE.setBackground(vlgreen);
		emptyE.setBorder(null);

		comboDateBoxE.add(emptyE);
		comboDateBoxE.add(comboYrE);
		comboDateBoxE.add(comboMthE);
		comboDateBoxE.add(comboDayE);

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

		JPanel block = new JPanel();
		block.setPreferredSize(new Dimension(420, 40));
		block.setBackground(vlgreen);
		block.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyB = new JTextField(35);
		emptyB.setBorder(null);
		emptyB.setEditable(false);
		emptyB.setBackground(vlgreen);

		String cbBlock[] = { "true", "false" };
		comboBlock = new JComboBox<String>(cbBlock);
		comboBlock.setFont(fontTxt);

		block.add(emptyB);
		block.add(comboBlock);

		JPanel lvl = new JPanel();
		lvl.setPreferredSize(new Dimension(420, 40));
		lvl.setBackground(vlgreen);
		lvl.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyL = new JTextField(35);
		emptyL.setBorder(null);
		emptyL.setEditable(false);
		emptyL.setBackground(vlgreen);

		String cbLvl[] = { "1", "2" };
		comboLvl = new JComboBox<String>(cbLvl);
		comboLvl.setFont(fontTxt);

		lvl.add(emptyL);
		lvl.add(comboLvl);

		midRPanel.add(id);
		midRPanel.add(user);
		midRPanel.add(inpPassW);
		midRPanel.add(comboDateBoxS);
		midRPanel.add(comboDateBoxE);
		midRPanel.add(valid);
		midRPanel.add(block);
		midRPanel.add(lvl);

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

		String[] buttonNames = { "Save", "Clear", "Back" };
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

			AuthorizationManager2.getMidRightPanel().add(new UpdUsrIdScrn(), "newPanel");

			AuthorizationManager2.getMidRightPanel().revalidate();
			AuthorizationManager2.getMidRightPanel().repaint();

			break;
		}
		/*
		 * save the change in the DataUserId arraylist
		 */
		case "Save": {
			if (status) {
				selId = Integer.parseInt(inpId.getText());
				selUser = inpUser.getText();
				updValid = (String) comboValid.getSelectedItem();
				updLvl = (String) comboLvl.getSelectedItem();
				updBlock = (String) comboBlock.getSelectedItem();
				String updYrS = (String) comboYrS.getSelectedItem();
				String updMthS = (String) comboMthS.getSelectedItem();
				String updDayS = (String) comboDayS.getSelectedItem();
				String updYrE = (String) comboYrE.getSelectedItem();
				String updMthE = (String) comboMthE.getSelectedItem();
				String updDayE = (String) comboDayE.getSelectedItem();

				updSdate = updYrS + "-" + updMthS + "-" + updDayS;
				updEdate = updYrE + "-" + updMthE + "-" + updDayE;

				Boolean updVal = Boolean.parseBoolean(updValid);
				Boolean updBlk = Boolean.parseBoolean(updBlock);

				for (int i = 0; i < AuthorizationManager2.userIdData.size(); i++) {
					if (AuthorizationManager2.userIdData.get(i).getId() == selId) {

						selPwd = AuthorizationManager2.userIdData.get(i).getdPwd();
						String selPwStat = AuthorizationManager2.userIdData.get(i).getPwStat();
						AuthorizationManager2.userIdData.set(i,
								new DataUserId(selId, selUser, selPwd, updSdate, updEdate, updVal, updBlk, updLvl, selPwStat));
					}
				}
				AuthorizationManager2.getLog()
						.info("UserId: " + selUser + " with startdate: " + updSdate + " changed \n auth_id = " + selId);
				JFrame message = new JFrame();
				JOptionPane.showMessageDialog(message, "User " + inpUser.getText() + " changed", "Info",
						JOptionPane.INFORMATION_MESSAGE);
				inpId.setText("");
				inpUser.setText("");
				comboValid.setSelectedIndex(0);
				comboBlock.setSelectedIndex(1);
				comboLvl.setSelectedIndex(1);
				comboMthS.setSelectedIndex(mthNow - 1);
				comboDayS.setSelectedIndex(dayNow - 1);
				comboYrE.setSelectedIndex(0);
				comboMthE.setSelectedIndex(11);
				comboDayE.setSelectedIndex(0);
				status = false;
			} else {
				JFrame message = new JFrame();
				JOptionPane.showMessageDialog(message, "No input", "Info", JOptionPane.INFORMATION_MESSAGE);
			}
			break;
			}
		/*
		 * get the data with the key id from the arraylist
		 */
		case "id":
			status = false;
			selId = Integer.parseInt(inpId.getText());

			for (DataUserId d1 : AuthorizationManager2.userIdData) {
				int userid = d1.getId();
				if (selId == userid) {
					String uname = d1.getUser();
					String sdate = d1.getsDate();
					String edate = d1.getmDate();
					Boolean valid = d1.getVal();
					String val = Boolean.toString(valid);
					Boolean blocked = d1.getBlock();
					String blk = Boolean.toString(blocked);
					String level = d1.getLvl();
					int isDayS = Integer.parseInt(sdate.substring(8));

					inpUser.setText(uname);
					comboYrS.setSelectedItem(sdate.substring(0, 4));
					comboMthS.setSelectedItem(sdate.substring(5, 7));
					comboDayS.setSelectedIndex(isDayS - 1);
					comboYrE.setSelectedItem(edate.substring(0, 4));
					comboMthE.setSelectedItem(edate.substring(5, 7));
					comboDayE.setSelectedItem(edate.substring(8));
					comboValid.setSelectedItem(val);
					comboBlock.setSelectedItem(blk);
					comboLvl.setSelectedItem(level);

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
		/*
		 * get the data with the key user from the arraylist
		 */
		case "user":
			status = false;
			selUser = inpUser.getText();

			for (DataUserId d1 : AuthorizationManager2.userIdData) {
				String userNm = d1.getUser();
				if (selUser.equals(userNm)) {
					int authId = d1.getId();
					String authIdT = Integer.toString(authId);
					String sdate = d1.getsDate();
					String edate = d1.getmDate();
					Boolean valid = d1.getVal();
					String val = Boolean.toString(valid);
					Boolean blocked = d1.getBlock();
					String blk = Boolean.toString(blocked);
					String level = d1.getLvl();
					int isDayS = Integer.parseInt(sdate.substring(8));

					inpId.setText(authIdT);
					comboYrS.setSelectedItem(sdate.substring(0, 4));
					comboMthS.setSelectedItem(sdate.substring(5, 7));
					comboDayS.setSelectedIndex(isDayS - 1);
					comboYrE.setSelectedItem(edate.substring(0, 4));
					comboMthE.setSelectedItem(edate.substring(5, 7));
					comboDayE.setSelectedItem(edate.substring(8));
					comboValid.setSelectedItem(val);
					comboBlock.setSelectedItem(blk);
					comboLvl.setSelectedItem(level);

					inpId.setBackground(Color.red);
					inpId.setEditable(false);
					inpId.setEnabled(false);
					status = true;

					return;
				}
			}
			JFrame messageN = new JFrame();
			JOptionPane.showMessageDialog(messageN, "Not a valid username", "Info", JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}
}