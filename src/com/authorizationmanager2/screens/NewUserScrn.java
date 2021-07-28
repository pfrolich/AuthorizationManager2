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

public class NewUserScrn extends JPanel implements ActionListener {

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

	private JTextField inpName;
	private JTextField inpSname;
	private JTextField inpEmail;
	private JTextField inpId;
	private JTextField inpUserId;
	
	private JPasswordField inpPwInit;

	private JButton button;
	private JButton ok;

	private String insNm;
	private String insSnm;
	private String insEmail;
	private String insSdate;
	private int selId;
	private boolean checkS = false;

	private JComboBox<String> comboValid;
	private JComboBox<String> comboYrS;
	private JComboBox<String> comboMthS;
	private JComboBox<String> comboDayS;

	private LocalDate date = LocalDate.now();
	private int mthNow = date.getMonthValue();
	private int dayNow = date.getDayOfMonth();

	private Font font18 = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	private Font font24Ar = new Font("Arial", Font.BOLD, 24);
	private Font font18Ar = new Font("Arial", Font.BOLD, 18);

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
		newPanel = new JPanel();
		newPanel.setPreferredSize(new Dimension(700, 580));
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

		JLabel titleInsert = new JLabel("Add New User");
		titleInsert.setForeground(vlgreen);
		titleInsert.setFont(font24Ar);

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

		String[] labelText = { "new Id :", "Name :", "Surname :", "Email-address :", "Start-date :", "Valid :",
				"Create userid :", "Init Password : " };

		for (int i = 0; i < 8; i++) {
			label = new JLabel(labelText[i]);
			label.setFont(font18Ar);
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
		inpId.setFont(font18Ar);
		inpId.setEditable(false);

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

		inpName = new JTextField(23);
		inpName.setBackground(Color.white);
		inpName.setFont(font18Ar);
		inpName.setCursor(getCursor());

		name.add(emptyN);
		name.add(inpName);

		JPanel sname = new JPanel();
		sname.setPreferredSize(new Dimension(420, 40));
		sname.setBackground(vlgreen);
		sname.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyS = new JTextField(25);
		emptyS.setBorder(null);
		emptyS.setEditable(false);
		emptyS.setBackground(vlgreen);

		inpSname = new JTextField(23);
		inpSname.setFont(font18Ar);
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
		inpEmail.setFont(font18Ar);
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
		comboYrS.setFont(font18Ar);

		for (int i = fromYr; i < toYr; i++) {
			String itemYr = Integer.toString(i);
			comboYrS.addItem(itemYr);
		}

		comboMthS = new JComboBox<String>(cbMth);
		comboMthS.setFont(font18Ar);

		comboDayS = new JComboBox<String>();
		comboDayS.setFont(font18Ar);

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
		comboValid.setFont(font18Ar);

		valid.add(emptyV);
		valid.add(comboValid);

		JPanel userId = new JPanel();
		userId.setPreferredSize(new Dimension(420, 40));
		userId.setBackground(vlgreen);
		userId.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyO = new JTextField(30);
		emptyO.setBorder(null);
		emptyO.setEditable(false);
		emptyO.setBackground(vlgreen);

		inpUserId = new JTextField(10);
		inpUserId.setBackground(Color.white);
		inpUserId.setFont(font18Ar);
		inpUserId.setEditable(false);

		ok = new JButton("OK");
		ok.setActionCommand("ok");
		ok.setPreferredSize(new Dimension(70, 30));
		ok.setFont(font18);
		ok.setForeground(greend1);
		ok.addActionListener(this);

		userId.add(emptyO);
		userId.add(inpUserId);
		userId.add(ok);
		
		JPanel pwInit = new JPanel();
		pwInit.setPreferredSize(new Dimension(420, 40));
		pwInit.setBackground(vlgreen);
		pwInit.setLayout(new FlowLayout(0, 10, 0));

		JTextField emptyP = new JTextField(30);
		emptyP.setBorder(null);
		emptyP.setEditable(false);
		emptyP.setBackground(vlgreen);

		inpPwInit = new JPasswordField(10);
		inpPwInit.setBackground(Color.white);
		inpPwInit.setFont(font18Ar);
				
		pwInit.add(emptyP);
		pwInit.add(inpPwInit);

		midRPanel.add(id);
		midRPanel.add(name);
		midRPanel.add(sname);
		midRPanel.add(email);
		midRPanel.add(comboDateBox);
		midRPanel.add(valid);
		midRPanel.add(userId);
		midRPanel.add(pwInit);

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
			button.setPreferredSize(new Dimension(100, 40));
			button.setFont(font18);
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

			AuthorizationManager2.getMidRightPanel().validate();
			AuthorizationManager2.getMidRightPanel().repaint();

			AuthorizationManager2.getMidLeftTopPanel().removeAll();
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.combo);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.ok);
			AuthorizationManager2.getMidLeftTopPanel().add(AuthorizationManager2.getEmpty2());

			AuthorizationManager2.getMidLeftTopPanel().validate();
			AuthorizationManager2.getMidLeftTopPanel().repaint();
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
		}
			break;

		/*
		 * save the change in the DataUser arraylist
		 */
		case "Save":

			String selValid = (String) comboValid.getSelectedItem();
			String selYrS = (String) comboYrS.getSelectedItem();
			String selMthS = (String) comboMthS.getSelectedItem();
			String selDayS = (String) comboDayS.getSelectedItem();

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
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "User already exists !!! \n Change name", "Warning",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (!exists) {
					AuthorizationManager2.userData
							.add(new DataUser(selId, insNm, insSnm, insEmail, insSdate, selValid));
					AuthorizationManager2.getLog().info("New user: " + insNm + " " + insSnm + " with startdate: "
							+ insSdate + " inserted \n auth_id = " + selId);
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "New user inserted", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					String selIdTxt = Integer.toString(selId);
					inpId.setText(selIdTxt);
					checkS = true;
				}
			} else {
				if (insNm.isEmpty() && insSnm.isEmpty()) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "No input", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
				if (insNm.isEmpty() && !insSnm.isEmpty()) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "Enter Name", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
				if (insSnm.isEmpty() && !insNm.isEmpty()) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "Enter Surname", "Info", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			break;
		/*
		 * Create an userid and add to the arraylist DataUserId
		 */
		case "ok":
			char[] getPwdI = inpPwInit.getPassword();
			String selPwdI = String.valueOf(getPwdI);
			if (checkS) {
				if (AuthorizationManager2.getUsrPat().matches("default")) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "Choose an user pattern!!", "Warning",
							JOptionPane.INFORMATION_MESSAGE);
					
					return;
				}
				if(selPwdI.isEmpty()) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "Field Init Password is empty !!", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					String newId = Integer.toString(selId);
					String useridN = AuthorizationManager2.getUsrPat() + newId.substring(2);
					String userid;
					inpUserId.setText(useridN);

					boolean exists = false;

					String insEdate = "9999-12-31";
					String selValid1 = (String) comboValid.getSelectedItem();
					Boolean val1 = Boolean.parseBoolean(selValid1);
					Boolean block1 = false;
					String selLvl = "2";
					String selPwStat = "i";

					String newPwd;
					
						try {
							newPwd = encrypt(selPwdI.getBytes(UTF_8), eCode);
							for (DataUserId d1 : AuthorizationManager2.userIdData) {
								userid = d1.getUser();
								if (useridN.equals(userid)) {
									exists = true;
								}
							}
							if (exists) {
								JFrame message = new JFrame();
								JOptionPane.showMessageDialog(message,
										"User already exists !!! \n Choose other user_Id", "Warning",
										JOptionPane.INFORMATION_MESSAGE);
							}
							if (!exists) {
								AuthorizationManager2.userIdData.add(new DataUserId(selId, useridN, newPwd, insSdate,
										insEdate, val1, block1, selLvl, selPwStat));
								AuthorizationManager2.getLog().info("UserId: " + useridN + " with startdate: "
										+ insSdate + " inserted \n auth_id = " + selId);
								JFrame message = new JFrame();
								JOptionPane.showMessageDialog(message, "New userid created", "Info",
										JOptionPane.INFORMATION_MESSAGE);

								inpId.setText("");
								inpName.setText("");
								inpSname.setText("");
								inpEmail.setText("");
								comboValid.setSelectedIndex(0);
								comboMthS.setSelectedIndex(mthNow - 1);
								comboDayS.setSelectedIndex(dayNow - 1);
								inpUserId.setText("");
								inpPwInit.setText("");
								checkS = false;
							} 

						} catch (Exception e1) {
							// TODO Auto-generated catch block
						}
			}
				checkS = false;
			} else {
				JFrame message = new JFrame();
				JOptionPane.showMessageDialog(message, "Save first new user before creating userid", "Info",
						JOptionPane.INFORMATION_MESSAGE);

			}
		}
	}

}