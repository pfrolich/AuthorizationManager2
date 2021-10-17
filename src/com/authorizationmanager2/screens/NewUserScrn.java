package com.authorizationmanager2.screens;

/**
 * @author pfrolich  
 * @version 1.0 
 * Date : September 2020
 * 
 * This Class is used for the program Passwordmanager and contains  the insert screen of new users
 */

import java.awt.*;
import java.awt.event.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.swing.*;

import com.authorizationmanager2.AuthorizationManager2;
import com.authorizationmanager2.CryptoUtils;
import com.authorizationmanager2.data.DataUser;
import com.authorizationmanager2.data.DataUserId;
import com.authorizationmanager2.tabbedpane.Console;

public class NewUserScrn extends JPanel implements ActionListener {
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
	private JPanel bar;

	/*
	 * definitions midLpanel
	 */
	private JLabel label;
	private JProgressBar progressBar;
	/*
	 * definitions midRpanel
	 */
	private static JTextField inpName;
	private static JTextField inpSname;
	private static JTextField inpEmail;
	private static JTextField inpId;
	private static JTextField inpUserId;

	private static JPasswordField inpPwInit;

	private JButton button;
	private JButton ok;

	/*
	 * definitions for create user & userid
	 */
	private String insNm;
	private String insSnm;
	private String insEmail;
	private String insSdate;

	public static String usrPat;
	public static boolean checkS = false;
	private boolean exists = false;
	private Boolean block1 = false;

	private char[] getPwdI;
	private String selPwdI;

	private String selValid;
	private String selValid1;
	private Boolean val1;

	private String selLvl;
	private String selPwStat;
	private int selId;
	private String newId;
	private String newPwd;
	private String userid;
	private String useridN;

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

	private String selYrS;
	private String selMthS;
	private String selDayS;

	private String insEdate;

	/*
	 * color definitions
	 */
	public static Color red1 = new Color(230, 204, 204);
	public static Color red2 = new Color(204, 0, 0);
	public static Color greend1 = new Color(26, 51, 0);
	public static Color vlgreen = new Color(235, 250, 235);
	public static Color green1 = new Color(46, 184, 46);

	// encryption/decryption
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
	private static final String eCode = "Kp&9011";

	private static final int TAG_LENGTH_BIT = 128; // must be one of {128, 120, 112, 104, 96}
	private static final int IV_LENGTH_BYTE = 12;
	private static final int SALT_LENGTH_BYTE = 16;
	private static final Charset UTF_8 = StandardCharsets.UTF_8;

	// return a base64 encoded AES encrypted text
	public static String encrypt(byte[] pText, String password) throws Exception {

		// 16 bytes salt
		byte[] salt = CryptoUtils.getRandomNonce(SALT_LENGTH_BYTE);

		// GCM recommended 12 bytes iv?
		byte[] iv = CryptoUtils.getRandomNonce(IV_LENGTH_BYTE);

		// secret key from password
		SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

		// ASE-GCM needs GCMParameterSpec
		cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

		byte[] cipherText = cipher.doFinal(pText);

		// prefix IV and Salt to cipher text
		byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length).put(iv).put(salt)
				.put(cipherText).array();

		// string representation, base64, send this string to other for decryption.
		return Base64.getEncoder().encodeToString(cipherTextWithIvSalt);

	}

	public NewUserScrn() {
		w = AuthorizationManager2.scrW;
		h = AuthorizationManager2.scrH;
		newPanel = new JPanel();
		newPanel.setPreferredSize(new Dimension((int) (w * 0.55), (int) (h * 0.78)));
		newPanel.setLayout(new GridLayout());
		newPanel.setBackground(vlgreen);
		newPanel.setVisible(true);

		add(midPanel());
	}

	/*
	 * MidPanel part of NewUserScrn
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

		JLabel titleInsert = new JLabel("Add New User");
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

		bar = new JPanel();
		bar.setPreferredSize(new Dimension((int) (w * 0.14), (int) (h * 0.044)));
		bar.setBackground(vlgreen);
		bar.setLayout(new FlowLayout(0, x10, 0));

		progressBar = new JProgressBar(JProgressBar.HORIZONTAL, 0, 100);
		progressBar.setBackground(vlgreen);
		progressBar.setForeground(greend1);
		progressBar.setBorder(AuthorizationManager2.border);

		bar.add(progressBar);

		String[] labelText = { "  new Id :", "  Name :", "  Surname :", "  Email-address :", "  Start-date :",
				"  Valid :", "  Create userid :", "  Init Password : ", " " };

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
		int tf10 = (int) Math.round(w * 0.008);
		int e70 = (int) Math.round(w * 0.055);
		int e30 = (int) Math.round(h * 0.042);
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
		getInpId().setFont(AuthorizationManager2.font18);
		getInpId().setBorder(AuthorizationManager2.border);
		getInpId().setEditable(false);
		getInpId().setDisabledTextColor(greend1);

		id.add(getInpId());

		JPanel name = new JPanel();
		name.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		name.setBackground(vlgreen);
		name.setLayout(new FlowLayout(0, x10, 0));

		setInpName(new JTextField(tf23));
		getInpName().setFont(AuthorizationManager2.font18);
		getInpName().setBackground(Color.white);
		getInpName().setForeground(greend1);
		getInpName().setBorder(AuthorizationManager2.border);
		getInpName().setCursor(getCursor());

		name.add(getInpName());

		JPanel sname = new JPanel();
		sname.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		sname.setBackground(vlgreen);
		sname.setLayout(new FlowLayout(0, x10, 0));

		setInpSname(new JTextField(tf23));
		getInpSname().setFont(AuthorizationManager2.font18);
		getInpSname().setBackground(Color.white);
		getInpSname().setForeground(greend1);
		getInpSname().setBorder(AuthorizationManager2.border);

		sname.add(getInpSname());

		JPanel email = new JPanel();
		email.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		email.setBackground(vlgreen);
		email.setLayout(new FlowLayout(0, x10, 0));

		setInpEmail(new JTextField(tf23));
		getInpEmail().setFont(AuthorizationManager2.font18);
		getInpEmail().setBackground(Color.white);
		getInpEmail().setForeground(greend1);
		getInpEmail().setBorder(AuthorizationManager2.border);

		email.add(getInpEmail());

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
		setComboValid(new JComboBox<String>(cbValid));
		getComboValid().setFont(AuthorizationManager2.font18);
		getComboValid().setForeground(greend1);
		getComboValid().setBorder(AuthorizationManager2.border);

		valid.add(getComboValid());

		JPanel userId = new JPanel();
		userId.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		userId.setBackground(vlgreen);
		userId.setLayout(new FlowLayout(0, x10, 0));

		setInpUserId(new JTextField(tf10));
		getInpUserId().setBackground(Color.white);
		getInpUserId().setForeground(greend1);
		getInpUserId().setFont(AuthorizationManager2.font18);
		getInpUserId().setBorder(AuthorizationManager2.border);
		getInpUserId().setEditable(false);

		ok = new JButton("OK");
		ok.setActionCommand("ok");
		ok.setPreferredSize(new Dimension(e70, e30));
		ok.setFont(AuthorizationManager2.font18);
		ok.setBorder(AuthorizationManager2.border);
		ok.setForeground(greend1);
		ok.addActionListener(this);

		userId.add(getInpUserId());
		userId.add(ok);

		JPanel pwInit = new JPanel();
		pwInit.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.056)));
		pwInit.setBackground(vlgreen);
		pwInit.setLayout(new FlowLayout(0, x10, 0));

		setInpPwInit(new JPasswordField(tf10));
		getInpPwInit().setBackground(Color.white);
		getInpPwInit().setForeground(greend1);
		getInpPwInit().setFont(AuthorizationManager2.font18);
		getInpPwInit().setBorder(AuthorizationManager2.border);

		pwInit.add(getInpPwInit());

		JPanel tab = new JPanel();
		tab.setPreferredSize(new Dimension((int) (w * 0.33), (int) (h * 0.25)));
		tab.setBackground(Color.yellow);
		tab.setLayout(new FlowLayout(0, 0, 0));

		midRTopPanel.add(id);
		midRTopPanel.add(name);
		midRTopPanel.add(sname);
		midRTopPanel.add(email);
		midRTopPanel.add(comboDateBox);
		midRTopPanel.add(valid);
		midRTopPanel.add(userId);
		midRTopPanel.add(pwInit);

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
			AuthorizationManager2.getMidRightPanel().setPreferredSize(new Dimension((int) (w * 0.56), (int) (h * 0.78)));
			AuthorizationManager2.getMidRightPanel().add(new ConsoleScrn(), "newPanel");	
			AuthorizationManager2.getMidRightPanel().validate();
			AuthorizationManager2.getMidRightPanel().repaint();
			Console.console.setText(AuthorizationManager2.cpyArea.getText());
			
			AuthorizationManager2.getMidLeftTopPanel().removeAll();
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getEmpty2());

			AuthorizationManager2.getMidLeftTopPanel().validate();
			AuthorizationManager2.getMidLeftTopPanel().repaint();
			AuthorizationManager2.setScreen("default");
			break;
		}
		/*
		 * clear input text fields
		 */
		case "Clear": {
			AuthorizationManager2.cpyArea.setText(Console.console.getText());
			AuthorizationManager2.getMidRightPanel().removeAll();

			AuthorizationManager2.getMidRightPanel().add(new NewUserScrn(), "newPanel");

			AuthorizationManager2.getMidRightPanel().revalidate();
			AuthorizationManager2.getMidRightPanel().repaint();
			AuthorizationManager2.getCmbUser().setSelectedIndex(0);
			Console.console.setText(AuthorizationManager2.cpyArea.getText());
		}
			break;
		/*
		 * save the change in the DataUser arraylist
		 */
		case "Save":

			selValid = (String) comboValid.getSelectedItem();
			selYrS = (String) comboYrS.getSelectedItem();
			selMthS = (String) comboMthS.getSelectedItem();
			selDayS = (String) comboDayS.getSelectedItem();

			insNm = inpName.getText();
			insSnm = inpSname.getText();
			insEmail = inpEmail.getText();
			insSdate = selYrS + "-" + selMthS + "-" + selDayS;

			if (!inpName.getText().isEmpty() && !insSnm.isEmpty()) {
				AuthorizationManager2.maxId = AuthorizationManager2.maxId + 1;
				selId = AuthorizationManager2.maxId;
				String fName;
				String sName;
				boolean exists = false;

				for (DataUser d1 : AuthorizationManager2.userData) {
					fName = d1.getfName();
					sName = d1.getsName();

					if (insNm.equals(fName) && insSnm.equals(sName)) {
						exists = true;
					}
				}
				if (exists) {
					Console.console.setForeground(red2);
					Console.console.append("\nUser already exists!!! Change name. Warning!!!");
				}
				if (!exists) {
					timer = new Timer(1, new ActionListener() {
						int count = 0;

						@Override
						public void actionPerformed(ActionEvent e) {
							progressBar.setValue(++count);
							progressBar.setForeground(red1);
							progressBar.setString("saving!!");
							if (count == 160) {
								// progressBar.setString("saved!!");
								AuthorizationManager2.userData
										.add(new DataUser(selId, insNm, insSnm, insEmail, insSdate, selValid));
								String selIdTxt = Integer.toString(selId);
								getInpId().setText(selIdTxt);
								getInpId().setBackground(red1);
								checkS = true;

								AuthorizationManager2.getLog().info("New user: " + insNm + " " + insSnm
										+ " with startdate: " + insSdate + " inserted \n auth_id = " + selId);
								Console.console.setForeground(greend1);
								Console.console.append("\nNew user: " + insNm + " " + insSnm + " with startdate: "
										+ insSdate + " inserted \n auth_id = " + selId);
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
				}
			} else {
				if (insNm.isEmpty() && insSnm.isEmpty()) {
					Console.console.setForeground(red2);
					Console.console.append("\nNo Input !!!");
				}
				if (insNm.isEmpty() && !insSnm.isEmpty()) {
					Console.console.setForeground(red2);
					Console.console.append("\nEnter Name !!");
				}
				if (insSnm.isEmpty() && !insNm.isEmpty()) {
					Console.console.setForeground(red2);
					Console.console.append("\nEnter Surname !!");
				}
			}
			break;
		/*
		 * Create an userid and add to the arraylist DataUserId
		 */
		case "ok":
			getPwdI = inpPwInit.getPassword();
			selPwdI = String.valueOf(getPwdI);
			usrPat = (String) AuthorizationManager2.getCmbUser().getSelectedItem();
			selYrS = (String) comboYrS.getSelectedItem();
			selMthS = (String) comboMthS.getSelectedItem();
			selDayS = (String) comboDayS.getSelectedItem();

			exists = false;
			insSdate = selYrS + "-" + selMthS + "-" + selDayS;
			insEdate = "9999-12-31";
			selValid1 = (String) comboValid.getSelectedItem();
			val1 = Boolean.parseBoolean(selValid1);
			block1 = false;
			selLvl = "2";
			selPwStat = "i";

			if (!checkS) {
				Console.console.setForeground(red2);
				Console.console.append("\nSave first new user before creating userid");
				break;
			} else {
				if (usrPat.matches("default")) {
					Console.console.setForeground(red2);
					Console.console.append("\nChoose an user pattern!!");
					return;
				}
				if (selPwdI.isEmpty()) {
					Console.console.setForeground(red2);
					Console.console.append("\nField Init Password is empty !!");
					return;
				}
			}
			if (checkS) {
				timer = new Timer(1, new ActionListener() {
					int count = 0;

					@Override
					public void actionPerformed(ActionEvent e) {
						progressBar.setValue(++count);
						progressBar.setForeground(red1);
						progressBar.setString("creating!!");

						if (count == 160) {
							newId = getInpId().getText();
							useridN = usrPat + newId.substring(2);
							try {
								newPwd = encrypt(selPwdI.getBytes(UTF_8), eCode);
								for (DataUserId d1 : AuthorizationManager2.userIdData) {
									userid = d1.getUser();
									inpUserId.setBackground(red1);
									inpUserId.setText(useridN);
									if (useridN.equals(userid)) {
										exists = true;
									}
								}
								if (exists) {
									Console.console.setForeground(red2);
									Console.console.append("\nUser already exists !!! \\n Choose other user_Id");
								}
								if (!exists) {
									AuthorizationManager2.userIdData.add(new DataUserId(selId, useridN, newPwd,
											insSdate, insEdate, val1, block1, selLvl, selPwStat));
									AuthorizationManager2.getLog().info("UserId: " + useridN + " with startdate: "
											+ insSdate + " inserted \n auth_id = " + selId);
									Console.console.setForeground(greend1);
									Console.console.append("\nNew userid '" + useridN + "' created");

									return;
								}
							} catch (Exception e1) {
								// TODO Auto-generated catch block
							}
						}
						if (count > 160) {
							timer.stop();
							checkS = false;
							progressBar.setForeground(greend1);
							progressBar.setString("created!!");
						}
					}
				});
				progressBar.setValue(0);
				progressBar.setStringPainted(true);
				timer.start();
				break;
			}
		}
	}

	public static JTextField getInpId() {
		return inpId;
	}

	public void setInpId(JTextField inpId) {
		NewUserScrn.inpId = inpId;
	}

	public static JTextField getInpName() {
		return inpName;
	}

	public void setInpName(JTextField inpName) {
		NewUserScrn.inpName = inpName;
	}

	public static JTextField getInpSname() {
		return inpSname;
	}

	public void setInpSname(JTextField inpSname) {
		NewUserScrn.inpSname = inpSname;
	}

	public static JTextField getInpEmail() {
		return inpEmail;
	}

	public void setInpEmail(JTextField inpEmail) {
		NewUserScrn.inpEmail = inpEmail;
	}

	public static JComboBox<String> getComboValid() {
		return comboValid;
	}

	public void setComboValid(JComboBox<String> comboValid) {
		NewUserScrn.comboValid = comboValid;
	}

	public static JTextField getInpUserId() {
		return inpUserId;
	}

	public void setInpUserId(JTextField inpUserId) {
		NewUserScrn.inpUserId = inpUserId;
	}

	public static JPasswordField getInpPwInit() {
		return inpPwInit;
	}

	public void setInpPwInit(JPasswordField inpPwInit) {
		NewUserScrn.inpPwInit = inpPwInit;
	}
}