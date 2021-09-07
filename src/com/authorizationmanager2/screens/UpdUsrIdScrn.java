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
import com.authorizationmanager2.tabbedpane.Console;

public class UpdUsrIdScrn extends JPanel implements ActionListener {
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
	private JPanel midRBotPanel;
	private JPanel midRTopPanel;
	private JPanel midBotPanel;
	private JPanel topPanel;
	/*
	 * definitions midLpanel
	 */
	private JLabel label;
	private JProgressBar progressBar;
	/*
	 * definitions midRpanel
	 */
	private static JTextField inpUser;
	private JTextField inpPassW;
	private static JTextField inpId;

	private JButton button;

	private int selId;
	private String selUser;
	private String selPwd;
	private String updSdate;
	private String updEdate;
	private String updValid;
	private String updLvl;
	private String updBlock;
	public static Boolean status = false;
	/*
	 * date /time definitions
	 */
	private static Timer timer;

	public static JComboBox<String> comboValid;
	private JComboBox<String> comboBlock;
	public static JComboBox<String> comboLvl;
	public static JComboBox<String> comboYrS;
	public static JComboBox<String> comboMthS;
	public static JComboBox<String> comboDayS;
	public static JComboBox<String> comboYrE;
	public static JComboBox<String> comboMthE;
	public static JComboBox<String> comboDayE;

	private LocalDate date = LocalDate.now();
	private String yrNow = Integer.toString(date.getYear());
	private int mthNow = date.getMonthValue();
	private int dayNow = date.getDayOfMonth();
	/*
	 * color definitions
	 */
	public static Color red1 = new Color(230, 204, 204);
	public static Color red2 = new Color(204, 0, 0);
	public static Color greend1 = new Color(26, 51, 0);
	public static Color vlgreen = new Color(235, 250, 235);
	public static Color green1 = new Color(46, 184, 46);

	Thread thread1;

	public UpdUsrIdScrn() {
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
	 * MidPanel part of UpdUsrIdScrn
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

		JLabel titleInsert = new JLabel("Update  UserId's");
		titleInsert.setForeground(vlgreen);
		titleInsert.setFont(AuthorizationManager2.font24);

		topPanel.add(titleInsert);

		return topPanel;
	}

	/*
	 * midLPanel part of midPanel -- Text labels
	 */
	private Component midLPanel() {
		int x10 = (int) Math.round(w * 0.008);
		midLPanel = new JPanel();
		midLPanel.setPreferredSize(new Dimension((int) (w * 0.14), (int) (h * 0.61)));
		midLPanel.setLayout(new GridLayout(10, 1));
		midLPanel.setBackground(vlgreen);

		JPanel bar = new JPanel();
		bar.setPreferredSize(new Dimension((int) (w * 0.14), (int) (h * 0.044)));
		bar.setBackground(vlgreen);
		bar.setLayout(new FlowLayout(0, x10, 0));

		progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100); 
		progressBar.setBackground(vlgreen);
		progressBar.setForeground(greend1);
		progressBar.setBorder(AuthorizationManager2.border);

		bar.add(progressBar);

		String[] labelText = { "  Id", "  Username:", " ", "  Start-date:", "  End-date:", "  Valid: ",
				"  Blocked:", "  Auth-level: ", " " };

		for (int i = 0; i < 9; i++) {
			label = new JLabel(labelText[i]);
			label.setFont(AuthorizationManager2.font18);
			label.setForeground(greend1);
			midLPanel.add(label);
		}

		midLPanel.add(bar);

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
		int x10 = (int) Math.round(w * 0.008); // 20
		int tf23 = (int) Math.round(w * 0.018);
		int tf12 = (int) Math.round(w * 0.009375);

		midRTopPanel = new JPanel();
		midRTopPanel.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.51)));
		midRTopPanel.setLayout(new GridLayout(8, 1));
		midRTopPanel.setBackground(vlgreen);

		JPanel id = new JPanel();
		id.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		id.setBackground(vlgreen);
		id.setLayout(new FlowLayout(0, x10, 0));

		setInpId(new JTextField(5));
		getInpId().setBackground(Color.white);
		getInpId().setForeground(greend1);
		getInpId().setBorder(AuthorizationManager2.border);
		getInpId().setFont(AuthorizationManager2.font18);
		getInpId().addActionListener(this);
		getInpId().setActionCommand("id");
		getInpId().addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
					getInpId().setEditable(true);
				} else {
					if (ke.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE
							|| ke.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
						getInpId().setEditable(true);
					} else {
						getInpId().setEditable(false);
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message, "* Enter only numeric digits(0-9)", "INFO",
								JOptionPane.INFORMATION_MESSAGE);
						getInpId().setEditable(true);
					}
				}
			}
		});

		id.add(getInpId());

		JPanel user = new JPanel();
		user.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		user.setBackground(vlgreen);
		user.setLayout(new FlowLayout(0, x10, 0));

		setInpUser(new JTextField(tf23));
		getInpUser().setBackground(Color.white);
		getInpUser().setFont(AuthorizationManager2.font18);
		getInpUser().setForeground(greend1);
		getInpUser().setBorder(AuthorizationManager2.border);
		getInpUser().addActionListener(this);
		getInpUser().setActionCommand("user");

		user.add(getInpUser());

		inpPassW = new JTextField(tf12);
		inpPassW.setBackground(vlgreen);
		inpPassW.setBorder(null);
		inpPassW.setEditable(false);

		int fromYr = date.getYear() - 100;
		int mthNow = date.getMonthValue();
		int dayNow = date.getDayOfMonth();
		int toYr = fromYr + 200;

		String cbMth[] = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };

		comboYrS = new JComboBox<String>();
		comboYrS.setFont(AuthorizationManager2.font18);
		comboYrS.setForeground(greend1);
		comboYrS.setBorder(AuthorizationManager2.border);

		for (int i = fromYr; i < toYr; i++) {
			String itemYr = Integer.toString(i);
			comboYrS.addItem(itemYr);
		}

		comboMthS = new JComboBox<String>(cbMth);
		comboMthS.setFont(AuthorizationManager2.font18);
		comboMthS.setForeground(greend1);
		comboMthS.setBorder(AuthorizationManager2.border);

		comboDayS = new JComboBox<String>();
		comboDayS.setFont(AuthorizationManager2.font18);
		comboDayS.setForeground(greend1);
		comboDayS.setBorder(AuthorizationManager2.border);

		for (int i = 1; i < 32; i++) {
			String itemDay = Integer.toString(i);

			comboDayS.addItem(itemDay);
		}

		comboYrE = new JComboBox<String>();
		comboYrE.setFont(AuthorizationManager2.font18);
		comboYrE.setForeground(greend1);
		comboYrE.setBorder(AuthorizationManager2.border);
		comboYrE.addItem("9999");

		for (int i = 2020; i < toYr; i++) {
			String itemYr = Integer.toString(i);
			comboYrE.addItem(itemYr);
		}

		comboMthE = new JComboBox<String>(cbMth);
		comboMthE.setFont(AuthorizationManager2.font18);
		comboMthE.setForeground(greend1);
		comboMthE.setBorder(AuthorizationManager2.border);

		comboDayE = new JComboBox<String>();
		comboDayE.setFont(AuthorizationManager2.font18);
		comboDayE.setForeground(greend1);
		comboDayE.setBorder(AuthorizationManager2.border);

		for (int i = 31; i > 0; i--) {
			String itemDay = Integer.toString(i);

			comboDayE.addItem(itemDay);
		}
		comboYrS.setSelectedItem(yrNow);
		comboMthS.setSelectedIndex(mthNow - 1);
		comboDayS.setSelectedIndex(dayNow - 1);
		comboMthE.setSelectedIndex(11);

		JPanel comboDateBoxS = new JPanel();
		comboDateBoxS.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		comboDateBoxS.setLayout(new FlowLayout(0, x10, 0));
		comboDateBoxS.setBackground(vlgreen);

		comboDateBoxS.add(comboYrS);
		comboDateBoxS.add(comboMthS);
		comboDateBoxS.add(comboDayS);

		JPanel comboDateBoxE = new JPanel();
		comboDateBoxE.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		comboDateBoxE.setLayout(new FlowLayout(0, x10, 0));
		comboDateBoxE.setBackground(vlgreen);

		comboDateBoxE.add(comboYrE);
		comboDateBoxE.add(comboMthE);
		comboDateBoxE.add(comboDayE);

		JPanel valid = new JPanel();
		valid.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		valid.setBackground(vlgreen);
		valid.setLayout(new FlowLayout(0, x10, 0));

		String cbValid[] = { "true", "false" };
		comboValid = new JComboBox<String>(cbValid);
		comboValid.setFont(AuthorizationManager2.font18);
		comboValid.setForeground(greend1);
		comboValid.setBorder(AuthorizationManager2.border);

		valid.add(comboValid);

		JPanel block = new JPanel();
		block.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		block.setBackground(vlgreen);
		block.setLayout(new FlowLayout(0, x10, 0));

		String cbBlock[] = { "true", "false" };
		comboBlock = new JComboBox<String>(cbBlock);
		comboBlock.setFont(AuthorizationManager2.font18);
		comboBlock.setForeground(greend1);
		comboBlock.setBorder(AuthorizationManager2.border);
		comboBlock.setSelectedIndex(1);

		block.add(comboBlock);

		JPanel lvl = new JPanel();
		lvl.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		lvl.setBackground(vlgreen);
		lvl.setLayout(new FlowLayout(0, x10, 0));

		String cbLvl[] = { "1", "2" };
		comboLvl = new JComboBox<String>(cbLvl);
		comboLvl.setFont(AuthorizationManager2.font18);
		comboLvl.setForeground(greend1);
		comboLvl.setBorder(AuthorizationManager2.border);

		lvl.add(comboLvl);

		midRTopPanel.add(id);
		midRTopPanel.add(user);
		midRTopPanel.add(inpPassW);
		midRTopPanel.add(comboDateBoxS);
		midRTopPanel.add(comboDateBoxE);
		midRTopPanel.add(valid);
		midRTopPanel.add(block);
		midRTopPanel.add(lvl);

		return midRTopPanel;
	}

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
	 * MidBotPanel -- part of midPanel
	 */
	private Component midBotPanel() {
		int e100 = (int) Math.round(w * 0.078);
		int e30 = (int) Math.round(h * 0.042);
		midBotPanel = new JPanel();
		midBotPanel.setPreferredSize(new Dimension((int) (w * 0.515), (int) (h * 0.069)));
		midBotPanel.setLayout(new FlowLayout());
		midBotPanel.setBackground(greend1);

		String[] buttonNames = { "Save", "Clear", "Back" };
		for (int i = 0; i < 3; i++) {
			button = new JButton(buttonNames[i]);
			button.setActionCommand(buttonNames[i]);
			button.setPreferredSize(new Dimension(e100, e30));
			button.setFont(AuthorizationManager2.font18);
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

			AuthorizationManager2.getMidLeftTopPanel().validate();
			AuthorizationManager2.getMidLeftTopPanel().repaint();

			AuthorizationManager2.getMidRightPanel().validate();
			AuthorizationManager2.getMidRightPanel().repaint();
			AuthorizationManager2.setScreen("default");
			break;
		}
		/*
		 * clear input text fields
		 */
		case "Clear": {
			AuthorizationManager2.cpyArea.setText(Console.console.getText());
			AuthorizationManager2.getMidRightPanel().removeAll();

			AuthorizationManager2.getMidRightPanel().add(new UpdUsrIdScrn(), "newPanel");

			AuthorizationManager2.getMidRightPanel().revalidate();
			AuthorizationManager2.getMidRightPanel().repaint();
			Console.console.setText(AuthorizationManager2.cpyArea.getText());

		}
			break;
		/*
		 * save the change in the DataUserId arraylist
		 */
		case "Save": {
			if (status) {
				timer = new Timer(1, new ActionListener() {
					int count = 0;

					@Override
					public void actionPerformed(ActionEvent e) {
						progressBar.setValue(++count);
						progressBar.setForeground(red1);
						progressBar.setString("saving!!");
						if (count == 160) {
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
									AuthorizationManager2.userIdData.set(i, new DataUserId(selId, selUser, selPwd,
											updSdate, updEdate, updVal, updBlk, updLvl, selPwStat));
								}
							}
							AuthorizationManager2.getLog().info("UserId: " + selUser + " with startdate: " + updSdate
									+ " changed \n auth_id = " + selId);
							Console.console.append("\nUser " + inpUser.getText() + " changed");

							inpId.setText("");
							inpId.setBackground(Color.white);
							inpId.setEditable(true);
							inpUser.setText("");
							comboValid.setSelectedIndex(0);
							comboBlock.setSelectedIndex(1);
							comboLvl.setSelectedIndex(1);
							comboYrS.setSelectedItem(yrNow);
							comboMthS.setSelectedIndex(mthNow - 1);
							comboDayS.setSelectedIndex(dayNow - 1);
							comboYrE.setSelectedIndex(0);
							comboMthE.setSelectedIndex(11);
							comboDayE.setSelectedIndex(0);
							status = false;
						}
						if (count > 160) {
							timer.stop();
							progressBar.setForeground(greend1);
							progressBar.setString("saved!!");
						}
					}
				});

				progressBar.setValue(0);
				progressBar.setStringPainted(true);
				timer.start();
				break;
			} else {
				Console.console.setForeground(red2);
				Console.console.append("\nNo Input !!!");
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

					inpId.setBackground(red1);
					inpId.setEditable(false);
					inpId.setDisabledTextColor(greend1);

					status = true;
					return;
				}
			}
			Console.console.setForeground(red2);
			Console.console.append("\nNot a valid Id!!!");
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

					inpId.setBackground(red1);
					inpId.setEditable(false);
					inpId.setEnabled(false);
					status = true;

					return;
				}
			}
			Console.console.setForeground(red2);
			Console.console.append("\nNot a valid username");
			break;
		}
	}

	public static JTextField getInpId() {
		return inpId;
	}

	public void setInpId(JTextField inpId) {
		UpdUsrIdScrn.inpId = inpId;
	}

	public static JTextField getInpUser() {
		return inpUser;
	}

	public void setInpUser(JTextField inpUser) {
		UpdUsrIdScrn.inpUser = inpUser;
	}

}