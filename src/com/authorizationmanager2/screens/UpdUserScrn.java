package com.authorizationmanager2.screens;

/**
 * @author pfrolich  
 * @version 1.0 
 * Date : September 2020
 * 
 * This Class is used for the program Passwordmanager and contains  the update screen for users
 */

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import javax.swing.*;

import com.authorizationmanager2.AuthorizationManager2;
import com.authorizationmanager2.data.DataUser;
import com.authorizationmanager2.tabbedpane.Console;

public class UpdUserScrn extends JPanel implements ActionListener {
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
	/*
	 * definitions midLpanel
	 */
	private JLabel label;
	private JProgressBar progressBar;
	/*
	 * definitions midRpanel
	 */
	private static JTextField inpFname;
	public static JTextField inpSname;
	public static JTextField inpEmail;
	private static JTextField inpId;

	private JButton button;

	private int selId;
	private String updSdate;
	private String updFnm;
	private String updSnm;
	private String updEmail;
	private String updValid;

	public static Boolean status = false;
	/*
	 * date /time definitions
	 */
	private static Timer timer;
	public static JComboBox<String> comboValid;
	public static JComboBox<String> comboYrS;
	public static JComboBox<String> comboMthS;
	public static JComboBox<String> comboDayS;

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

	public UpdUserScrn() {
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
	 * MidPanel part of UpdeUserScrn
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
	 * TopPanel - Title
	 */
	private Component topPanel() {
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension((int) (w * 0.55), (int) (h * 0.07)));
		topPanel.setLayout(new FlowLayout());
		topPanel.setBackground(greend1);

		JLabel titleInsert = new JLabel("Update User");
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

		String[] labelText = { "  Id :", "  Name :", "  Surname :", "  Email-address :", "  Start-date :", "  Valid : ", " ",
				" ", " " };

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
		int tf5 = (int) Math.round(w * 0.004);

		midRTopPanel = new JPanel();
		midRTopPanel.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.51)));
		midRTopPanel.setLayout(new GridLayout(8, 1));
		midRTopPanel.setBackground(vlgreen);

		JPanel id = new JPanel();
		id.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		id.setBackground(vlgreen);
		id.setLayout(new FlowLayout(0, x10, 0));

		setInpId(new JTextField(tf5));
		getInpId().setBackground(Color.white);
		getInpId().setForeground(greend1);
		getInpId().setFont(AuthorizationManager2.font18);
		getInpId().addActionListener(this);
		getInpId().setActionCommand("id");
		getInpId().setBorder(AuthorizationManager2.border);
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

		JPanel name = new JPanel();
		name.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		name.setBackground(vlgreen);
		name.setLayout(new FlowLayout(0, x10, 0));

		setInpFname(new JTextField(tf23));
		getInpFname().setFont(AuthorizationManager2.font18);
		getInpFname().setBorder(AuthorizationManager2.border);
		getInpFname().setBackground(Color.white);
		getInpFname().setForeground(greend1);

		name.add(getInpFname());

		JPanel sname = new JPanel();
		sname.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		sname.setBackground(vlgreen);
		sname.setLayout(new FlowLayout(0, x10, 0));

		inpSname = new JTextField(tf23);
		inpSname.setFont(AuthorizationManager2.font18);
		inpSname.setBorder(AuthorizationManager2.border);
		inpSname.setBackground(Color.white);
		inpSname.setForeground(greend1);

		sname.add(inpSname);

		JPanel email = new JPanel();
		email.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		email.setBackground(vlgreen);
		email.setLayout(new FlowLayout(0, x10, 0));

		inpEmail = new JTextField(23);
		inpEmail.setFont(AuthorizationManager2.font18);
		inpEmail.setBorder(AuthorizationManager2.border);
		inpEmail.setBackground(Color.white);
		inpEmail.setForeground(greend1);

		email.add(inpEmail);

		LocalDate date = LocalDate.now();
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
		comboYrS.setSelectedItem(yrNow);
		comboMthS.setSelectedIndex(mthNow - 1);
		comboDayS.setSelectedIndex(dayNow - 1);

		JPanel comboDateBox = new JPanel();
		comboDateBox.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		comboDateBox.setLayout(new FlowLayout(0, x10, 0));
		comboDateBox.setBackground(vlgreen);

		comboDateBox.add(comboYrS);
		comboDateBox.add(comboMthS);
		comboDateBox.add(comboDayS);

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

		midRTopPanel.add(id);
		midRTopPanel.add(name);
		midRTopPanel.add(sname);
		midRTopPanel.add(email);
		midRTopPanel.add(comboDateBox);
		midRTopPanel.add(valid);

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
	 * midBotPanel part of midPanel -- Buttons
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

			AuthorizationManager2.getMidRightPanel().add(new UpdUserScrn(), "newPanel");

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
							selId = Integer.parseInt(getInpId().getText());
							updFnm = getInpFname().getText();
							updSnm = inpSname.getText();
							updEmail = inpEmail.getText();
							updValid = (String) comboValid.getSelectedItem();
							String updYrS = (String) comboYrS.getSelectedItem();
							String updMthS = (String) comboMthS.getSelectedItem();
							String updDayS = (String) comboDayS.getSelectedItem();

							updSdate = updYrS + "-" + updMthS + "-" + updDayS;
							for (int i = 0; i < AuthorizationManager2.userData.size(); i++) {
								if (AuthorizationManager2.userData.get(i).getId() == selId) {
									AuthorizationManager2.userData.set(i,
											new DataUser(selId, updFnm, updSnm, updEmail, updSdate, updValid));
								}
							}
							AuthorizationManager2.getLog().info("User: " + updFnm + " " + updSnm + " with startdate: "
									+ updSdate + " changed \n auth_id = " + selId);
							Console.console.append("\nUser " + inpSname.getText() + " changed");

							getInpId().setText("");
							getInpId().setBackground(Color.white);
							getInpId().setEditable(true);
							getInpFname().setText("");
							inpSname.setText("");
							inpEmail.setText("");
							comboValid.setSelectedIndex(0);
							comboYrS.setSelectedItem(yrNow);
							comboMthS.setSelectedIndex(mthNow - 1);
							comboDayS.setSelectedIndex(dayNow - 1);
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
				Console.console.append("\nNo input");
			}
		}
			break;
		/*
		 * get the data with the key id from the arraylist
		 */
		case "id":
			status = false;
			selId = Integer.parseInt(getInpId().getText());
			for (DataUser d1 : AuthorizationManager2.userData) {
				int userid = d1.getId();
				if (selId == userid) {
					String fName = d1.getfName();
					String sName = d1.getsName();
					String email = d1.getEmail();
					String sdate = d1.getsDate();
					String act = d1.getAct();
					int isDayS = Integer.parseInt(sdate.substring(8));

					getInpFname().setText(fName);
					inpSname.setText(sName);
					inpEmail.setText(email);
					comboYrS.setSelectedItem(sdate.substring(0, 4));
					comboMthS.setSelectedItem(sdate.substring(5, 7));
					comboDayS.setSelectedIndex(isDayS - 1);
					comboValid.setSelectedItem(act);

					getInpId().setBackground(red1);
					getInpId().setEditable(false);
					getInpId().setDisabledTextColor(greend1);

					status = true;
					return;
				}

			}
			Console.console.setForeground(red2);
			Console.console.append("\nNot a valid Id!!!");
			break;
		}
	}

	public static JTextField getInpId() {
		return inpId;
	}

	public void setInpId(JTextField inpId) {
		UpdUserScrn.inpId = inpId;
	}

	public static JTextField getInpFname() {
		return inpFname;
	}

	public void setInpFname(JTextField inpFname) {
		UpdUserScrn.inpFname = inpFname;
	}
}