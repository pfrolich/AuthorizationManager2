package com.authorizationmanager2;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.authorizationmanager2.data.FileBank;
import com.authorizationmanager2.data.DataUser;
import com.authorizationmanager2.data.DataUserId;
import com.authorizationmanager2.screens.DelUserScrn;
import com.authorizationmanager2.screens.ListScrn;
import com.authorizationmanager2.screens.NewUserScrn;
import com.authorizationmanager2.screens.UpdUserScrn;
import com.authorizationmanager2.screens.UpdUsrIdScrn;

/**
 * @author pfrolich
 * @version 1.0 Date : September 2020
 * 
 *          This is a program for managing users and passwords. 
 *          1. create new users and userid's 
 *          2. update users and options for userid's 
 *          3. delete users 
 *          4. list users and userid's 
 *          5. reset and revoke passwords (password will be an initial password)
 */

public class AuthorizationManager2 extends JFrame implements ActionListener {

	/**
	 * Definitions: Panels, Buttons, ComboBoxes, TextAreas, TextFields, Labels,
	 * etc...
	 */
	private static final long serialVersionUID = 1L;
	public static List<DataUserId> userIdData;
	public static List<DataUser> userData;
	private static AuthorizationManager2 frame;
	private Container window;
	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel midPanel;
	private JPanel midLeftPanel;
	private static JPanel midLeftTopPanel;
	private JPanel midLeftBotPanel;
	private JPanel loginPanel;
	private JPanel loginPanelL;
	private JPanel loginPanelR;
	private JPanel chgPwdPanel;
	private JPanel chgPwdPanelL;
	private JPanel chgPwdPanelR;
	private static JPanel midRightPanel;
	private JPanel botPanel;

	public static JComboBox<String> combo;
	private static JComboBox<String> cmbUser;

	private JButton button;
	private JButton enter;
	private JButton enter2;
	private JButton back;
	public static JButton ok;
	private JButton ok1;
	private JButton ok2;

	private JTextArea empty;
	private static JTextArea empty2;
	private JTextArea empty3;
	private JTextField userId;

	private static JLabel usrSelect;
	private JLabel userText;

	private JLabel name;
	private JLabel loggedIn;
	private JLabel status;

	private JLabel lgnName;
	private JLabel password;

	private static JTextField inputName;
	private JTextField beforeEnter;
	private static JPasswordField inputPassword;
	public static String loginName;

	private JLabel oldPw;
	private JLabel pwdNew1;
	private JLabel pwdNew2;
	private JLabel iPwdTxt;

	public static JPasswordField inpOldPw;
	public static JPasswordField inpPwdNew1;
	public static JPasswordField inpPwdNew2;
	public static JPasswordField iPwdInp;

	public static char[] pwOld;
	public static char[] pwNew1;
	public static char[] pwNew2;
	public static char[] iPwdNew;

	private JTable table1;
	private JTable table2;

	private static Properties properties;
	private static String pathUsr;
	private static String pathUid;
	private static String pathUsrH;
	private static String pathUidH;
	private static String backup;
	private static String fileExt;
	private static String fileUsr;
	private static String fileUid;
	private static String pathLogProp;
	private static String pathLogFile;
	private static String dateTimeFormat;
	
	private static LocalDateTime dateTime;
	private static DateTimeFormatter dateFormatter;  
	private static String fileDate;
	

	private Boolean blocked = false;
	private String inpUser;
	private String inpIPw;
	private String newPwd;
	private static String usrPat;
	private Boolean existToRevoke;
	private Boolean existToReset;
	public static String option;

	public static Color greend1 = new Color(26, 51, 0);
	public static Color greend2 = new Color(40, 77, 0);
	public static Color greend3 = new Color(0, 43, 128);
	public static Color greend4 = new Color(0, 43, 128);
	public static Color vlgreen = new Color(235, 250, 235);
	public static Color green1 = new Color(46, 184, 46);

	private Font font18 = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	private Font font16 = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	private Font font24Ar = new Font("Arial", Font.BOLD, 24);
	private Font font18Ar = new Font("Arial", Font.BOLD, 18);

	private static Logger log = Logger.getLogger("com.authorizationmanager");
	private static FileHandler logFile;

	private static JScrollPane jsp;

	/*
	 * Definitions for read file
	 */
	private static final String COMMA_DELIMITER = ";";

	private String isUser;
	private String isPwd;
	private Boolean isVal;
	private Boolean isBlock;
	private String isLvl;
	private String chkPwStat;
	public static int maxId;

	public AuthorizationManager2() {
		/*
		 * Check if application starts correctly
		 */

		if (SwingUtilities.isEventDispatchThread()) {

			getLog().info("application AuthorizationManager has started correctly");

		} else {
			getLog().info("application did not start correctly. Place this in the correct event dispatchThread");
		}
	}

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

	private static String decrypt(String cText, String password) throws Exception {

		byte[] decode = Base64.getDecoder().decode(cText.getBytes(UTF_8));

		// get back the iv and salt from the cipher text
		ByteBuffer bb = ByteBuffer.wrap(decode);

		byte[] iv = new byte[IV_LENGTH_BYTE];
		bb.get(iv);

		byte[] salt = new byte[SALT_LENGTH_BYTE];
		bb.get(salt);

		byte[] cipherText = new byte[bb.remaining()];
		bb.get(cipherText);

		// get back the aes key from the same password and salt
		SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(password.toCharArray(), salt);

		Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);

		cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

		byte[] plainText = cipher.doFinal(cipherText);

		return new String(plainText, UTF_8);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				frame = new AuthorizationManager2();
				frame.setTitle("Developed by P.E. Frölich, Barueri, September 2020");
				frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\JAR-programs\\images\\pwmimage.png"));
				frame.setSize(1000, 740);
				frame.setBackground(greend1);
				frame.createGui();
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				// frame.setUndecorated(true);
				frame.setVisible(true);
				frame.addWindowListener(new WindowAdapter() {

					@Override
					public void windowClosing(WindowEvent e) {
						super.windowClosing(e);

						int reply = JOptionPane.showConfirmDialog(frame,
								"Are you sure? \nLogoff first and/or push Quit", "Close window",
								JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
						if (reply == JOptionPane.YES_OPTION) {
							// frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
						} else if (reply == JOptionPane.NO_OPTION) {
							frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
							getLog().info("Window not closed");
						}
					}
				});
				
				/*
				 * load of propertiesfile authman.ini
				 */
				properties = new Properties();
				userIdData = new ArrayList<>();
				userData = new ArrayList<>();

				try {
					properties.load(new FileInputStream("C:\\JAR-programs\\properties\\authorizationmanager\\authman2.ini"));
					
					pathUsr = properties.getProperty("path-usr");
					pathUsrH = properties.getProperty("path-usrh");
					pathUid = properties.getProperty("path-uid");
					pathUidH = properties.getProperty("path-uidh");
					fileExt = properties.getProperty("file-ext");
					backup = properties.getProperty("path-backup");
					fileUsr = properties.getProperty("file-usr-startswith");
					fileUid = properties.getProperty("file-uid-startswith");
					pathLogProp = properties.getProperty("path-log-properties");
					pathLogFile = properties.getProperty("path-log-file");
					dateTimeFormat = properties.getProperty("datetime-format");

					LogManager.getLogManager().readConfiguration(new FileInputStream(pathLogProp));
					log.setLevel(Level.FINE);
					logFile = new FileHandler(pathLogFile, true);
					logFile.setFormatter(new com.authorizationmanager2.logging.MyFormatter());
					getLog().addHandler(logFile);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	protected void createGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window = getContentPane();
		window.setLayout(new FlowLayout());

		window.add(mainPanel());
	}

	/*
	 * create MainPanel
	 */
	private Component mainPanel() {
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1000, 740));
		mainPanel.setLayout(new FlowLayout());
		mainPanel.setBackground(greend1);

		mainPanel.add(topPanel());
		mainPanel.add(midLeftPanel());
		mainPanel.add(midPanel());
		mainPanel.add(midRightPanel());
		mainPanel.add(botPanel());

		return mainPanel;
	}

	/*
	 * create TitlePanel - part of mainPanel
	 */
	private Component topPanel() {
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension(990, 60));
		topPanel.setLayout(new FlowLayout());
		topPanel.setBackground(green1);

		JLabel titleInsert = new JLabel("Autorisatie administrator");
		Font title = new Font(Font.SANS_SERIF, Font.BOLD, 36);
		titleInsert.setFont(title);
		titleInsert.setForeground(vlgreen);

		topPanel.add(titleInsert);

		return topPanel;
	}

	/*
	 * create leftPanel - part of mainPanel
	 */
	private Component midLeftPanel() {
		midLeftPanel = new JPanel();
		midLeftPanel.setPreferredSize(new Dimension(240, 580));
		midLeftPanel.setLayout(new FlowLayout());
		midLeftPanel.setBackground(greend1);

		midLeftPanel.add(midLeftTopPanel());
		midLeftPanel.add(midLeftBotPanel());

		return midLeftPanel;
	}

	/*
	 * create midLeftTopPanel - part of midLeftPanel 
	 * contents - JCombobox 
	 * 			- empty JTextarea's for filling up space
	 *  		- JButtons
	 */
	private Component midLeftTopPanel() {
		setMidLeftTopPanel(new JPanel());
		getMidLeftTopPanel().setPreferredSize(new Dimension(240, 510));
		getMidLeftTopPanel().setLayout(new FlowLayout(10, 10, 10));
		getMidLeftTopPanel().setBackground(greend1);

		String cbActie[] = { "Add new user", "Update User", "Delete User", "Change options UserId",
				"Reset UserPassword", "Revoke User", "List Users", "List UserId's", "Write to File", "Read from File" };
		combo = new JComboBox<String>(cbActie);
		combo.setFont(font18Ar);
		combo.addActionListener(this);
		combo.setActionCommand("actie");

		setUsrSelect(new JLabel("Select user Pattern:"));
		getUsrSelect().setForeground(vlgreen);
		getUsrSelect().setFont(font18Ar);
		getUsrSelect().setBorder(null);

		String cbUsr[] = { "default", "xspp", "uspp", "xrjp", "urjp" };
		setCmbUser(new JComboBox<>(cbUsr));
		getCmbUser().addActionListener(this);
		getCmbUser().setFont(font18Ar);
		getCmbUser().setActionCommand("usrSel");
		usrPat = "default";

		iPwdTxt = new JLabel("new Password : ");
		iPwdTxt.setForeground(vlgreen);
		iPwdTxt.setFont(font18Ar);
		iPwdTxt.setBorder(null);

		iPwdInp = new JPasswordField(12);
		iPwdInp.setFont(font18Ar);
		iPwdInp.setBackground(Color.white);

		setEmpty2(new JTextArea(8, 20));
		getEmpty2().setEditable(false);
		getEmpty2().setBackground(greend1);

		empty3 = new JTextArea(24, 20);
		empty3.setEditable(false);
		empty3.setBackground(greend1);

		userText = new JLabel();
		userText.setFont(font18Ar);

		userId = new JTextField(12);
		userId.setFont(font18Ar);
		userId.setBackground(Color.white);

		ok = new JButton("OK");
		ok.setPreferredSize(new Dimension(80, 40));
		ok.setFont(font18);
		ok.setForeground(greend1);
		ok.addActionListener(this);
		ok.setActionCommand("ok");

		ok1 = new JButton("OK");
		ok1.setPreferredSize(new Dimension(80, 40));
		ok1.setFont(font18);
		ok1.setForeground(greend1);
		ok1.addActionListener(this);
		ok1.setActionCommand("ok1");

		ok2 = new JButton("OK");
		ok2.setPreferredSize(new Dimension(80, 40));
		ok2.setFont(font18);
		ok2.setForeground(greend1);
		ok2.addActionListener(this);
		ok2.setActionCommand("ok2");

		back = new JButton("Back");
		back.setPreferredSize(new Dimension(100, 50));
		back.setFont(font18);
		back.setForeground(greend1);
		back.addActionListener(this);
		back.setActionCommand("back");

		return getMidLeftTopPanel();
	}
	/*
	 * Create midLeftBotPanel - part of midLeftPanel 
	 * Content - JButton (Quit  program)
	 */

	private Component midLeftBotPanel() {
		midLeftBotPanel = new JPanel();
		midLeftBotPanel.setPreferredSize(new Dimension(240, 50));
		midLeftBotPanel.setLayout(new FlowLayout(0, 10, 0));
		midLeftBotPanel.setBackground(greend1);

		String[] buttonNames = { "Quit", "Logoff" };
		for (int i = 0; i < 2; i++) {
			button = new JButton(buttonNames[i]);
			button.setActionCommand(buttonNames[i]);
			button.setPreferredSize(new Dimension(100, 50));
			button.setFont(font18);
			button.setForeground(greend1);
			button.addActionListener(this);
			midLeftBotPanel.add(button);
		}

		return midLeftBotPanel;
	}

	/*
	 * Create midPanel - part of mainPanel Content - login Screen. disappears after
	 * login
	 */

	private Component midPanel() {
		midPanel = new JPanel();
		midPanel.setPreferredSize(new Dimension(670, 580));
		midPanel.setLayout(new BorderLayout());
		midPanel.setBackground(greend1);

		JTextArea emptyN = new JTextArea(10, 10);
		emptyN.setEditable(false);
		emptyN.setBackground(vlgreen);
		JTextArea emptyW = new JTextArea(30, 11);
		emptyW.setEditable(false);
		emptyW.setBackground(vlgreen);
		JTextArea emptyS = new JTextArea(10, 10);
		emptyS.setEditable(false);
		emptyS.setBackground(vlgreen);
		JTextArea emptyE = new JTextArea(30, 11);
		emptyE.setEditable(false);
		emptyE.setBackground(vlgreen);

		midPanel.add(emptyN, BorderLayout.NORTH);
		midPanel.add(emptyW, BorderLayout.WEST);
		midPanel.add(emptyE, BorderLayout.EAST);
		midPanel.add(emptyS, BorderLayout.SOUTH);
		midPanel.add(loginPanel(), BorderLayout.CENTER);

		return midPanel;
	}

	private Component loginPanel() {
		loginPanel = new JPanel();
		loginPanel.setPreferredSize(new Dimension(300, 350));
		loginPanel.setLayout(new FlowLayout(0, 0, 0));
		loginPanel.setBackground(greend1);

		loginPanel.add(loginPanelL());
		loginPanel.add(loginPanelR());

		return loginPanel;
	}

	private Component loginPanelL() {
		loginPanelL = new JPanel();
		loginPanelL.setPreferredSize(new Dimension(150, 420));
		loginPanelL.setLayout(new FlowLayout(20, 10, 20));
		loginPanelL.setBackground(greend1);

		empty = new JTextArea(3, 11);
		empty.setEditable(false);
		empty.setBackground(greend1);

		lgnName = new JLabel("User :");
		lgnName.setForeground(vlgreen);
		lgnName.setFont(font24Ar);
		lgnName.setBorder(null);

		password = new JLabel("Password :");
		password.setForeground(vlgreen);
		password.setFont(font24Ar);
		password.setBorder(null);

		loginPanelL.add(empty);
		loginPanelL.add(lgnName);
		loginPanelL.add(password);

		return loginPanelL;
	}

	private Component loginPanelR() {
		loginPanelR = new JPanel();
		loginPanelR.setPreferredSize(new Dimension(250, 420));
		loginPanelR.setLayout(new FlowLayout(20, 10, 20));
		loginPanelR.setBackground(greend1);

		empty = new JTextArea(1, 20);
		empty.setEditable(false);
		empty.setBackground(greend1);

		inputName = new JTextField(11);
		inputName.setFont(font24Ar);
		inputName.addActionListener(this);
		inputName.setActionCommand("enter");

		inputPassword = new JPasswordField(11);
		inputPassword.setFont(font24Ar);
		inputPassword.addActionListener(this);
		inputPassword.setActionCommand("enter");

		beforeEnter = new JTextField(11);
		beforeEnter.setBackground(greend1);
		beforeEnter.setBorder(null);
		beforeEnter.setEditable(false);

		enter = new JButton("Enter");
		enter.setPreferredSize(new Dimension(100, 40));
		enter.setActionCommand("enter");
		enter.setFont(font18);
		enter.addActionListener(this);

		loginPanelR.add(empty);
		loginPanelR.add(inputName);
		loginPanelR.add(inputPassword);
		loginPanelR.add(beforeEnter);
		loginPanelR.add(enter);

		return loginPanelR;
	}

	private Component chgPwdPanel() {

		chgPwdPanel = new JPanel();
		chgPwdPanel.setLayout(new FlowLayout(0, 0, 0));
		chgPwdPanel.setPreferredSize(new Dimension(400, 350));
		chgPwdPanel.setBackground(greend1);

		chgPwdPanel.add(chgPwdPanelL());
		chgPwdPanel.add(chgPwdPanelR());
		return chgPwdPanel;
	}

	private Component chgPwdPanelL() {
		chgPwdPanelL = new JPanel();
		chgPwdPanelL.setPreferredSize(new Dimension(310, 420));
		chgPwdPanelL.setLayout(new FlowLayout(20, 10, 20));
		chgPwdPanelL.setBackground(greend1);

		empty = new JTextArea(1, 11);
		empty.setEditable(false);
		empty.setBackground(greend1);

		oldPw = new JLabel("Password old :");
		oldPw.setForeground(vlgreen);
		oldPw.setFont(font24Ar);
		oldPw.setBorder(null);

		pwdNew1 = new JLabel("Password new :");
		pwdNew1.setForeground(vlgreen);
		pwdNew1.setFont(font24Ar);
		pwdNew1.setBorder(null);

		pwdNew2 = new JLabel("Confirm password new :");
		pwdNew2.setForeground(vlgreen);
		pwdNew2.setFont(font24Ar);
		pwdNew2.setBorder(null);

		chgPwdPanelL.add(empty);
		chgPwdPanelL.add(oldPw);
		chgPwdPanelL.add(pwdNew1);
		chgPwdPanelL.add(pwdNew2);

		return chgPwdPanelL;
	}

	private Component chgPwdPanelR() {
		empty = new JTextArea(1, 11);
		empty.setEditable(false);
		empty.setBackground(greend1);

		chgPwdPanelR = new JPanel();
		chgPwdPanelR.setPreferredSize(new Dimension(250, 420));
		chgPwdPanelR.setLayout(new FlowLayout(20, 10, 20));
		chgPwdPanelR.setBackground(greend1);

		inpOldPw = new JPasswordField(11);
		inpOldPw.setFont(font24Ar);
		inpOldPw.addActionListener(this);
		inpOldPw.setActionCommand("enter2");

		inpPwdNew1 = new JPasswordField(11);
		inpPwdNew1.setFont(font24Ar);
		inpPwdNew1.addActionListener(this);
		inpPwdNew1.setActionCommand("enter2");

		inpPwdNew2 = new JPasswordField(11);
		inpPwdNew2.setFont(font24Ar);
		inpPwdNew2.addActionListener(this);
		inpPwdNew2.setActionCommand("enter2");

		beforeEnter = new JTextField(11);
		beforeEnter.setBackground(greend1);
		beforeEnter.setBorder(null);
		beforeEnter.setEditable(false);

		enter2 = new JButton("Enter");
		enter2.setPreferredSize(new Dimension(100, 40));
		enter2.setActionCommand("enter2");
		enter2.setFont(font18);
		enter2.addActionListener(this);

		chgPwdPanelR.add(empty);
		chgPwdPanelR.add(inpOldPw);
		chgPwdPanelR.add(inpPwdNew1);
		chgPwdPanelR.add(inpPwdNew2);
		chgPwdPanelR.add(beforeEnter);
		chgPwdPanelR.add(enter2);

		return chgPwdPanelR;
	}

	/*
	 * create midRightPanel - part of mainPanel initial during login empty. After
	 * login Size increased and used for all of the actions
	 */

	private Component midRightPanel() {
		setMidRightPanel(new JPanel());
		getMidRightPanel().setPreferredSize(new Dimension(20, 580));
		getMidRightPanel().setLayout(new FlowLayout());
		getMidRightPanel().setBackground(greend1);

		return getMidRightPanel();
	}

	/*
	 * create BotPanel - part of mainpanel Content: information about user and yes
	 * or not logged in
	 */
	private Component botPanel() {
		botPanel = new JPanel();
		botPanel.setPreferredSize(new Dimension(990, 30));
		botPanel.setLayout(new FlowLayout());
		botPanel.setBackground(green1);

		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 14);
		name = new JLabel("Name: null ");
		name.setFont(font);
		name.setForeground(vlgreen);
		loggedIn = new JLabel("Logged in: ");
		loggedIn.setFont(font);
		loggedIn.setForeground(vlgreen);
		status = new JLabel("false");
		status.setFont(font);
		status.setForeground(Color.red);

		botPanel.add(name);
		botPanel.add(loggedIn);
		botPanel.add(status);

		return botPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String buttonTxt = e.getActionCommand();

		switch (buttonTxt) {
		/*
		 * case Enter: used for login
		 */
		case "enter":
			loginName = inputName.getText();
			char[] getPassWrd = inputPassword.getPassword();
			String loginPassWrd = String.valueOf(getPassWrd);
			Boolean valid;

			// getLoginScrn().setVisible(false);
			File tempFile1 = new File(pathUid);
			File tempFile2 = new File(pathUsr);
			boolean exists1 = tempFile1.exists();
			boolean exists2 = tempFile2.exists();

			if (!exists1) {
				String selPwd = "Admin";
				try {
					newPwd = encrypt(selPwd.getBytes(UTF_8), eCode);
					userIdData.add(
							new DataUserId(10001, "Admin", newPwd, "2021-02-01", "2099-06-25", true, false, "1", "c"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, "Wrong Encrypt-Code");
					return;
				}

			} else {
				if (exists1 && !exists2) {
					JOptionPane.showMessageDialog(null, "Incomplete files !! \n File authuser2.txt is missing");
					return;
				}
				if (exists1 && exists2) {
					try {
						BufferedReader br1 = new BufferedReader(new FileReader(pathUid));
						BufferedReader br2 = new BufferedReader(new FileReader(pathUsr));
						String line1;
						String line2;
						userIdData.clear();
						while ((line1 = br1.readLine()) != null) {

							String[] temp = line1.split(COMMA_DELIMITER);
							int col0 = Integer.parseInt(temp[0]);
							String col1 = temp[1];
							String col2 = temp[2];
							String col3 = temp[3];
							String col4 = temp[4];
							Boolean col5 = Boolean.parseBoolean(temp[5]);
							Boolean col6 = Boolean.parseBoolean(temp[6]);
							String col7 = temp[7];
							String col8 = temp[8];

							userIdData.add(new DataUserId(col0, col1, col2, col3, col4, col5, col6, col7, col8));
						}
						userData.clear();
						while ((line2 = br2.readLine()) != null) {

							String[] temp = line2.split(COMMA_DELIMITER);
							int col0 = Integer.parseInt(temp[0]);
							String col1 = temp[1];
							String col2 = temp[2];
							String col3 = temp[3];
							String col4 = temp[4];
							String col5 = temp[5];

							userData.add(new DataUser(col0, col1, col2, col3, col4, col5));

						}
						br1.close();
						br2.close();

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

			for (DataUserId d1 : userIdData) {
				isUser = d1.getUser();
				isPwd = d1.getdPwd();
				isVal = d1.getVal();
				isBlock = d1.getBlock();
				isLvl = d1.getLvl();
				valid = isVal;
				blocked = isBlock;
				chkPwStat = d1.getPwStat();

				try {
					newPwd = decrypt(isPwd, eCode);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
				}

				if (loginName.equals(isUser) && loginPassWrd.equals(newPwd)) {
					if (isLvl.matches("1")) {
						if (valid && blocked) {
							JFrame message = new JFrame();
							JOptionPane.showMessageDialog(message,
									"Your password is blocked !!! \n \n Call your System Administrator \n",
									"Login error", JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (!valid) {
							JFrame message = new JFrame();
							JOptionPane.showMessageDialog(message,
									"Your userid revoked !!! \n \n Call your System Administrator \n", "Login error",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (valid && !blocked) {
							if (loginName.equals(isUser) && chkPwStat.equals("i")) {
								JTextArea emptyN = new JTextArea(9, 10);
								emptyN.setEditable(false);
								emptyN.setBackground(vlgreen);
								JTextArea emptyW = new JTextArea(30, 4);
								emptyW.setEditable(false);
								emptyW.setBackground(vlgreen);
								JTextArea emptyS = new JTextArea(9, 10);
								emptyS.setEditable(false);
								emptyS.setBackground(vlgreen);
								JTextArea emptyE = new JTextArea(30, 4);
								emptyE.setEditable(false);
								emptyE.setBackground(vlgreen);
								midPanel.removeAll();
								midPanel.add(emptyN, BorderLayout.NORTH);
								midPanel.add(emptyW, BorderLayout.WEST);
								midPanel.add(emptyE, BorderLayout.EAST);
								midPanel.add(emptyS, BorderLayout.SOUTH);
								midPanel.add(chgPwdPanel(), BorderLayout.CENTER);

								midPanel.revalidate();
								midPanel.repaint();

								return;
							} else {
								status.setForeground(Color.green);
								status.setText("true");
								name.setText("name: " + isUser);
								mainPanel.remove(midPanel);
								getMidRightPanel().setPreferredSize(new Dimension(720, 580));
								mainPanel.revalidate();
								mainPanel.repaint();
								getMidLeftTopPanel().add(combo);
								getMidLeftTopPanel().add(ok);
								getMidLeftTopPanel().add(getEmpty2());

								getLog().info("User " + loginName + " logged in ");

								JOptionPane.showMessageDialog(null, "Data Imported");
								return;
							}
						}
					} else {
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message, "You are not authotized to use this program \n",
								"Login error", JOptionPane.INFORMATION_MESSAGE);
						return;
					}

				}
			}

			if (status.getText() == "false" && !blocked) {
				JFrame message = new JFrame();
				JOptionPane.showMessageDialog(message, "Login Name and/or password not correct !!!", "Login error",
						JOptionPane.INFORMATION_MESSAGE);
			}
			break;

		/*
		 * case Enter2: used for change password if initial password 
		 */
		case "enter2":

			pwOld = inpOldPw.getPassword();
			pwNew1 = inpPwdNew1.getPassword();
			pwNew2 = inpPwdNew2.getPassword();

			String oldPw = String.valueOf(pwOld);
			String newPw1 = String.valueOf(pwNew1);
			String newPw2 = String.valueOf(pwNew2);
			int selId;
			String selUser;
			String selPw;
			String selSdate;
			String selEdate;
			Boolean selVal;
			Boolean selBlk;
			String selLvl;
			String selPwd;
			String selPwe;
			String selPwStat;

			try {
				dateTime = LocalDateTime.now();
				dateFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
				fileDate = dateTime.format(dateFormatter);
				for (int i = 0; i < userIdData.size(); i++) {
					if (userIdData.get(i).getUser().equals(loginName)) {
						selId = userIdData.get(i).getId();
						selUser = userIdData.get(i).getUser();
						selPw = userIdData.get(i).getdPwd();
						selSdate = userIdData.get(i).getsDate();
						selEdate = userIdData.get(i).getmDate();
						selVal = userIdData.get(i).getVal();
						selBlk = userIdData.get(i).getBlock();
						selLvl = userIdData.get(i).getLvl();
						selPwd = decrypt(selPw, eCode);
						selPwe = encrypt(newPw1.getBytes(UTF_8), eCode);
						selPwStat = "c";

						if (oldPw.equals(selPwd)) {

							if (newPw1.equals(newPw2)) {
								userIdData.set(i, new DataUserId(selId, selUser, selPwe, selSdate, selEdate, selVal,
										selBlk, selLvl, selPwStat));

								JFrame message = new JFrame();
								JOptionPane.showMessageDialog(message, "Your password is changed \n", "Info",
										JOptionPane.INFORMATION_MESSAGE);

								getLog().info("User " + loginName + " has changed his/her password successfully");
								/*
								 * write to file after password change
								 */
								Path source2 = Paths.get(pathUid);
								Path dest2 = Paths.get(pathUidH + fileDate + fileExt);

								if (Files.exists(source2)) {
									Files.copy(source2, dest2, StandardCopyOption.REPLACE_EXISTING);
								}

								File file2 = new File(pathUid);

								FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
								BufferedWriter bw2 = new BufferedWriter(fw2);

								String col0;
								String col1;
								String col2;
								String col3;
								String col4;
								String col5;
								String col6;
								String col7;
								String col8;

								// loop for table rows
								for (DataUserId d2 : userIdData) {
									col0 = Integer.toString(d2.getId());
									col1 = d2.getUser();
									col2 = d2.getdPwd();
									col3 = d2.getsDate();
									col4 = d2.getmDate();
									col5 = Boolean.toString(d2.getVal());
									col6 = Boolean.toString(d2.getBlock());
									col7 = d2.getLvl();
									col8 = d2.getPwStat();

									if (!col1.equals("Admin")) {
										bw2.write(col0 + ";" + col1 + ";" + col2 + ";" + col3 + ";" + col4 + ";" + col5
												+ ";" + col6 + ";" + col7 + ";" + col8 + ";");
										// break line at the end
										bw2.write("\n");
									}
								}
								getLog().info("File authuserid2 has changed");
								// close BufferedWriter
								bw2.close();
								// close FileWriter
								fw2.close();
								/*
								 * end write to file
								 */

							} else {
								JFrame message = new JFrame();
								JOptionPane.showMessageDialog(message, "passwords not equal. Try again \n", "Info",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
						} else {
							JFrame message = new JFrame();
							JOptionPane.showMessageDialog(message, "You entered a wrong password old", "Info",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			/*
			 * Open login screen after changed password
			 */
			mainPanel.removeAll();

			mainPanel.add(topPanel());
			mainPanel.add(midLeftPanel());
			mainPanel.add(midPanel());
			mainPanel.add(midRightPanel());
			mainPanel.add(botPanel());

			mainPanel.repaint();
			mainPanel.revalidate();

			break;

		/*
		 * case Quit: closes the program
		 */
		case "Quit":
			if (status.getText().equals("false")) {

				getLog().info("Program Stopped");
				System.exit(0);
			} else {
				// JFrame message = new JFrame();
				JOptionPane.showMessageDialog(null, "Log off first", "Message", JOptionPane.INFORMATION_MESSAGE);
			}

			break;

		/*
		 * case Logoff: Logoff userid
		 */
		case "Logoff":
			if (status.getText().equals("true")) {
				JFrame option = new JFrame();
				int reply = JOptionPane.showConfirmDialog(option, "Did you save your changes? ", "Logged off",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (reply == JOptionPane.YES_OPTION) {
					mainPanel.removeAll();
					mainPanel.add(topPanel());
					mainPanel.add(midLeftPanel());
					mainPanel.add(midPanel());
					mainPanel.add(midRightPanel());
					mainPanel.add(botPanel());
					status.setForeground(Color.red);
					status.setText("false");
					name.setText("name: " + "");

					mainPanel.revalidate();
					mainPanel.repaint();

					getLog().info("User " + loginName + " logged off ");
				} else if (reply == JOptionPane.NO_OPTION) {
					option.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					getLog().info("Not logged off");
				}

			}
			break;

		/*
		 * case ok: use for the action of the midLeftTop Panel
		 */
		case "ok":
			String selCmb = (String) combo.getSelectedItem();
			if (status.getText().equals("true")) {

				if (selCmb.contentEquals("Add new user")) {
					List<Integer> dataMax = new ArrayList<>();

					for (DataUserId d1 : userIdData) {
						dataMax.add(d1.getId());
					}
					Integer max = Collections.max(dataMax);
					maxId = (int) max;

					getMidLeftTopPanel().removeAll();
					getMidLeftTopPanel().add(combo);
					getMidLeftTopPanel().add(ok);
					getMidLeftTopPanel().add(getEmpty2());
					getMidLeftTopPanel().add(getUsrSelect());
					getMidLeftTopPanel().add(getCmbUser());
					getMidLeftTopPanel().revalidate();
					getMidLeftTopPanel().repaint();

					getMidRightPanel().removeAll();
					getMidRightPanel().add(new NewUserScrn(), "newPanel");
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();
				}
				if (selCmb.contentEquals("Update User")) {

					getMidLeftTopPanel().removeAll();
					getMidLeftTopPanel().add(combo);
					getMidLeftTopPanel().add(ok);
					getMidLeftTopPanel().add(getEmpty2());
					getMidLeftTopPanel().revalidate();
					getMidLeftTopPanel().repaint();

					getMidRightPanel().removeAll();
					getMidRightPanel().add(new UpdUserScrn(), "newPanel");
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();
				}
				if (selCmb.contentEquals("Change options UserId")) {
					getMidLeftTopPanel().removeAll();
					getMidLeftTopPanel().add(combo);
					getMidLeftTopPanel().add(ok);
					getMidLeftTopPanel().add(getEmpty2());
					getMidLeftTopPanel().revalidate();
					getMidLeftTopPanel().repaint();

					getMidRightPanel().removeAll();
					getMidRightPanel().add(new UpdUsrIdScrn(), "newPanel");
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();
				}
				if (selCmb.contentEquals("Delete User")) {

					getMidLeftTopPanel().removeAll();
					getMidLeftTopPanel().add(combo);
					getMidLeftTopPanel().add(ok);
					getMidLeftTopPanel().add(getEmpty2());
					getMidLeftTopPanel().revalidate();
					getMidLeftTopPanel().repaint();

					getMidRightPanel().removeAll();
					getMidRightPanel().add(new DelUserScrn(), "newPanel");
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();
				}
				if (selCmb.contentEquals("Reset UserPassword")) {

					getMidRightPanel().removeAll();
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();

					getMidLeftTopPanel().removeAll();

					userText.setText("Type user_id to reset:");
					userText.setForeground(vlgreen);

					getMidLeftTopPanel().add(combo);
					getMidLeftTopPanel().add(ok);
					getMidLeftTopPanel().add(getEmpty2());
					getMidLeftTopPanel().add(userText);
					getMidLeftTopPanel().add(userId);
					getMidLeftTopPanel().add(iPwdTxt);
					getMidLeftTopPanel().add(iPwdInp);
					getMidLeftTopPanel().add(ok2);

					getMidLeftTopPanel().revalidate();
					getMidLeftTopPanel().repaint();
				}
				if (selCmb.contentEquals("List Users")) {

					option = "user";

					String[] columnNames = { "UserId", "Name", "Surname", "email-address", "Start-date", "Active" };
					DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

					for (DataUser d1 : userData) {
						String userId = Integer.toString(d1.getId());
						String fName = d1.getfName();
						String sName = d1.getsName();
						String email = d1.getEmail();
						String sDate = d1.getsDate();
						String active = d1.getAct();
						String[] data = { userId, fName, sName, email, sDate, active };
						tableModel.addRow(data);
					}

					DefaultTableCellRenderer center = new DefaultTableCellRenderer();
					center.setHorizontalAlignment(JLabel.CENTER);

					table1 = new JTable(tableModel);
					table1.setBackground(vlgreen);
					table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table1.getTableHeader().setBackground(vlgreen);
					table1.getTableHeader().setFont(font18);
					table1.setFont(font16);
					table1.setEnabled(false);
					table1.setPreferredScrollableViewportSize(new Dimension(676, 428));

					table1.getColumnModel().getColumn(0).setPreferredWidth(80);
					table1.getColumnModel().getColumn(1).setPreferredWidth(180);
					table1.getColumnModel().getColumn(2).setPreferredWidth(200);
					table1.getColumnModel().getColumn(3).setPreferredWidth(280);
					table1.getColumnModel().getColumn(4).setPreferredWidth(120);
					table1.getColumnModel().getColumn(5).setPreferredWidth(80);
					table1.getColumnModel().getColumn(4).setCellRenderer(center);
					table1.getColumnModel().getColumn(5).setCellRenderer(center);

					setJsp(new JScrollPane(table1));
					getJsp().setBackground(greend1);
					getJsp().getViewport().setBackground(vlgreen);
					getJsp().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					getJsp().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

					getMidLeftTopPanel().removeAll();
					getMidLeftTopPanel().add(empty3);
					getMidLeftTopPanel().add(back);

					getMidRightPanel().removeAll();
					getMidRightPanel().setPreferredSize(new Dimension(700, 560));
					getMidRightPanel().add(new ListScrn(), "newPanel");
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();
				}
				if (selCmb.contentEquals("List UserId's")) {

					option = "userid";

					String[] columnNames = { "Auth_Id", "UserId", "Start-date", "End-date", "Valid", "Blocked",
							"Level", "Pw-Stat" };
					DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

					for (DataUserId d1 : userIdData) {
						String authid = Integer.toString(d1.getId());
						String uname = d1.getUser();
						String sdate = d1.getsDate();
						String edate = d1.getmDate();
						String valid1 = Boolean.toString(d1.getVal());
						String blocked1 = Boolean.toString(d1.getBlock());
						String level = d1.getLvl();
						String pwStat = d1.getPwStat();

						String[] data = { authid, uname, sdate, edate, valid1, blocked1, level, pwStat};
						tableModel.addRow(data);
					}

					DefaultTableCellRenderer center = new DefaultTableCellRenderer();
					center.setHorizontalAlignment(JLabel.CENTER);

					table2 = new JTable(tableModel);
					table2.setBackground(vlgreen);
					table2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
					table2.getTableHeader().setBackground(vlgreen);
					table2.getTableHeader().setFont(font18);
					table2.setFont(font16);
					table2.setEnabled(false);
					table2.setPreferredScrollableViewportSize(new Dimension(676, 428));

					table2.getColumnModel().getColumn(0).setPreferredWidth(70);
					table2.getColumnModel().getColumn(1).setPreferredWidth(120);
					table2.getColumnModel().getColumn(2).setPreferredWidth(200);
					table2.getColumnModel().getColumn(3).setPreferredWidth(120);
					table2.getColumnModel().getColumn(4).setPreferredWidth(80);
					table2.getColumnModel().getColumn(5).setPreferredWidth(80);
					table2.getColumnModel().getColumn(6).setPreferredWidth(70);
 					table2.getColumnModel().getColumn(7).setPreferredWidth(70);
					table2.getColumnModel().getColumn(2).setCellRenderer(center);
					table2.getColumnModel().getColumn(3).setCellRenderer(center);
					table2.getColumnModel().getColumn(4).setCellRenderer(center);
					table2.getColumnModel().getColumn(5).setCellRenderer(center);
					table2.getColumnModel().getColumn(6).setCellRenderer(center);
 					table2.getColumnModel().getColumn(7).setCellRenderer(center);

					setJsp(new JScrollPane(table2));
					getJsp().setBackground(greend1);
					getJsp().getViewport().setBackground(Color.LIGHT_GRAY);
					getJsp().setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
					getJsp().setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

					getMidLeftTopPanel().removeAll();
					getMidLeftTopPanel().add(empty3);
					getMidLeftTopPanel().add(back);

					getMidRightPanel().removeAll();
					getMidRightPanel().setPreferredSize(new Dimension(700, 560));
					getMidRightPanel().add(new ListScrn(), "newPanel");
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();
				}
				if (selCmb.contentEquals("Revoke User")) {

					getMidRightPanel().removeAll();
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();

					getMidLeftTopPanel().removeAll();

					userText.setText("Type userid to revoke:");
					userText.setForeground(vlgreen);

					getMidLeftTopPanel().add(combo);
					getMidLeftTopPanel().add(ok);
					getMidLeftTopPanel().add(getEmpty2());
					getMidLeftTopPanel().add(userText);
					getMidLeftTopPanel().add(userId);
					getMidLeftTopPanel().add(ok1);

					getMidLeftTopPanel().revalidate();
					getMidLeftTopPanel().repaint();
				}
				if (selCmb.contentEquals("Write to File")) {
					getMidRightPanel().removeAll();
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();

					getMidLeftTopPanel().removeAll();

					getMidLeftTopPanel().add(combo);
					getMidLeftTopPanel().add(ok);
					getMidLeftTopPanel().add(getEmpty2());

					getMidLeftTopPanel().revalidate();
					getMidLeftTopPanel().repaint();
					boolean existsToWrite = true;
					
					dateTime = LocalDateTime.now();
					dateFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
					fileDate = dateTime.format(dateFormatter);

					if (existsToWrite) {
						
						try {
							Path source1 = Paths.get(pathUsr);
							Path dest1 = Paths.get(pathUsrH + fileDate + fileExt);
							Path source2 = Paths.get(pathUid);
							Path dest2 = Paths.get(pathUidH + fileDate + fileExt);

							if (Files.exists(source1) || Files.exists(source2)) {
								Files.copy(source1, dest1, StandardCopyOption.REPLACE_EXISTING);
								Files.copy(source2, dest2, StandardCopyOption.REPLACE_EXISTING);
							}

							File file1 = new File(pathUsr);
							File file2 = new File(pathUid);

							FileWriter fw1 = new FileWriter(file1.getAbsoluteFile());
							BufferedWriter bw1 = new BufferedWriter(fw1);
							FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
							BufferedWriter bw2 = new BufferedWriter(fw2);

							String col0;
							String col1;
							String col2;
							String col3;
							String col4;
							String col5;
							String col6;
							String col7;
							String col8;

							// loop for table rows
							for (DataUser d1 : userData) {
								// loop for table column

								col0 = Integer.toString(d1.getId());
								col1 = d1.getfName();
								col2 = d1.getsName();
								col3 = d1.getEmail();
								col4 = d1.getsDate();
								col5 = d1.getAct();

								if (!col1.isEmpty()) {
									bw1.write(col0 + ";" + col1 + ";" + col2 + ";" + col3 + ";" + col4 + ";" + col5
											+ ";");
									// break line at the begin
									// break line at the end
									bw1.write("\n");
								}
							}
							for (DataUserId d2 : userIdData) {
								// loop for table column

								col0 = Integer.toString(d2.getId());
								col1 = d2.getUser();
								col2 = d2.getdPwd();
								col3 = d2.getsDate();
								col4 = d2.getmDate();
								col5 = Boolean.toString(d2.getVal());
								col6 = Boolean.toString(d2.getBlock());
								col7 = d2.getLvl();
								col8 = d2.getPwStat();

								if (!col1.equals("Admin")) {
									bw2.write(col0 + ";" + col1 + ";" + col2 + ";" + col3 + ";" + col4 + ";" + col5
											+ ";" + col6 + ";" + col7 + ";" + col8 + ";");
									// break line at the begin
									// break line at the end
									bw2.write("\n");
								}
							}
							// close BufferedWriter
							bw1.close();
							bw2.close();
							// close FileWriter
							fw1.close();
							fw2.close();
							JOptionPane.showMessageDialog(null, "Data Exported");
							getLog().info("Data written to files");
//							inputCode = null;
							/*
							 * Cleaning History user files
							 */

							ArrayList<FileBank> filesUsr = new ArrayList<>();
							filesUsr.clear();

							File tmp1 = new File(backup);
							FilenameFilter filter1 = (file, s) -> s.startsWith(fileUsr);
							File[] list1 = tmp1.listFiles(filter1);

							if (list1 != null) {
								Arrays.stream(list1).forEach(file -> {
									filesUsr.add(new FileBank(file.getPath()));
								});
							}

							File delUsrF;
							if (filesUsr.size() > 10) {
								for (int i = 0; i < (filesUsr.size() - 10); i++) {
									delUsrF = new File(filesUsr.get(i).getFiles());
									delUsrF.delete();
									getLog().info("file :" + delUsrF + " deleted");

								}
							}

							/*
							 * Cleaning History userId files
							 */
							ArrayList<FileBank> filesUid = new ArrayList<>();
							filesUid.clear();

							File tmp2 = new File(backup);
							FilenameFilter filter2 = (file, s) -> s.startsWith(fileUid);
							File[] list2 = tmp2.listFiles(filter2);

							if (list2 != null) {
								Arrays.stream(list2).forEach(file -> {
									filesUid.add(new FileBank(file.getPath()));
								});
							}

							File delUidF;
							if (filesUid.size() > 10) {
								for (int i = 0; i < (filesUid.size() - 10); i++) {
									delUidF = new File(filesUid.get(i).getFiles());
									delUidF.delete();
									getLog().info("file :" + delUidF + " deleted");
								}
							}

						} catch (Exception ex) {
							ex.printStackTrace();
						}

					}
					return;	
				}
				if (selCmb.contentEquals("Read from File")) {
					getMidRightPanel().removeAll();
					getMidRightPanel().revalidate();
					getMidRightPanel().repaint();

					getMidLeftTopPanel().removeAll();

					getMidLeftTopPanel().add(combo);
					getMidLeftTopPanel().add(ok);
					getMidLeftTopPanel().add(getEmpty2());

					getMidLeftTopPanel().revalidate();
					getMidLeftTopPanel().repaint();

					userData.clear();
					userIdData.clear();
					try {
						BufferedReader br1 = new BufferedReader(
								new FileReader(pathUid));
						BufferedReader br2 = new BufferedReader(
								new FileReader(pathUsr));
						String line1;
						String line2;

						while ((line1 = br1.readLine()) != null) {

							String[] temp = line1.split(COMMA_DELIMITER);
							int col0 = Integer.parseInt(temp[0]);
							String col1 = temp[1];
							String col2 = temp[2];
							String col3 = temp[3];
							String col4 = temp[4];
							Boolean col5 = Boolean.parseBoolean(temp[5]);
							Boolean col6 = Boolean.parseBoolean(temp[6]);
							String col7 = temp[7];
							String col8 = temp[8];

							userIdData.add(new DataUserId(col0, col1, col2, col3, col4, col5, col6, col7, col8));

						}
						while ((line2 = br2.readLine()) != null) {

							String[] temp = line2.split(COMMA_DELIMITER);
							int col0 = Integer.parseInt(temp[0]);
							String col1 = temp[1];
							String col2 = temp[2];
							String col3 = temp[3];
							String col4 = temp[4];
							String col5 = temp[5];

							userData.add(new DataUser(col0, col1, col2, col3, col4, col5));

						}
						br1.close();
						br2.close();

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Data Imported");
					getLog().info("Data read from the file");

				}
				break;

			} else {
				JFrame message = new JFrame();
				JOptionPane.showMessageDialog(message, "You are not logged in", "info",
						JOptionPane.INFORMATION_MESSAGE);
			}

			/*
			 * case ok1: is used for Revoke user:
			 */
		case "ok1":
			inpUser = userId.getText();
			// inpId =Integer.parseInt(userId.getText());
			existToRevoke = false;
			LocalDate date = LocalDate.now();
			String yrNow = Integer.toString(date.getYear());
			String mthNow = Integer.toString(date.getMonthValue());
			String dayNow = Integer.toString(date.getDayOfMonth());
			String newEdate = yrNow + "-" + mthNow + "-" + dayNow;

			if (inpUser.isEmpty()) {
				JFrame message = new JFrame();
				JOptionPane.showMessageDialog(message, "No input !!", "info", JOptionPane.INFORMATION_MESSAGE);
			} else {
				for (int i = 0; i < userIdData.size(); i++) {
					if (userIdData.get(i).getUser().equals(inpUser)) {
						existToRevoke = true;
						int authId = userIdData.get(i).getId();
						String selPwd1 = userIdData.get(i).getdPwd();
						String sdate = userIdData.get(i).getsDate();
						Boolean val = false;
						Boolean blk = userIdData.get(i).getBlock();
						String level = userIdData.get(i).getLvl();
						String pwStat = userIdData.get(i).getPwStat();

						AuthorizationManager2.userIdData.set(i,
								new DataUserId(authId, inpUser, selPwd1, sdate, newEdate, val, blk, level, pwStat));

						AuthorizationManager2.getLog().info("userid: " + inpUser + " revoked");
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message, "user " + inpUser + " revoked", "Info",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
				if (!existToRevoke) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "UserId doesn't exists !!! \n Retry", "Info",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			getMidLeftTopPanel().removeAll();
			getMidLeftTopPanel().add(combo);
			getMidLeftTopPanel().add(ok);
			getMidLeftTopPanel().add(getEmpty2());
			getMidLeftTopPanel().revalidate();
			getMidLeftTopPanel().repaint();

			break;
		/*
		 * case ok2: used for Reset user
		 */
		case "ok2":
			inpUser = userId.getText();
			iPwdNew = iPwdInp.getPassword();
			inpIPw = String.valueOf(iPwdNew);

			existToReset = false;

			if (inpUser.isEmpty() || inpIPw.isEmpty()) {
				JFrame message = new JFrame();
				JOptionPane.showMessageDialog(message, "User or new Password is missing \n Fill in the fields ", "info",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				for (int i = 0; i < userIdData.size(); i++) {
					if (userIdData.get(i).getUser().equals(inpUser)) {
						existToReset = true;

						int authId = userIdData.get(i).getId();
						String newPwd2;
						try {
							String user = userIdData.get(i).getUser();
							newPwd2 = encrypt(inpIPw.getBytes(UTF_8), eCode);
							String sdate = userIdData.get(i).getsDate();
							String edate = userIdData.get(i).getmDate();
							Boolean val = false;
							Boolean blk = userIdData.get(i).getBlock();
							String level = userIdData.get(i).getLvl();
							String pwStat = "i";
							AuthorizationManager2.userIdData.set(i,
									new DataUserId(authId, user, newPwd2, sdate, edate, val, blk, level, pwStat));

							AuthorizationManager2.getLog().info("userid: " + user + " resetted");
							System.out.println(newPwd2);
							/*
							 * write to file after password change
							 */
							Path source2 = Paths.get(pathUid);
							Path dest2 = Paths.get(pathUidH + fileDate + fileExt);

							if (Files.exists(source2)) {
								Files.copy(source2, dest2, StandardCopyOption.REPLACE_EXISTING);
							}

							File file2 = new File(pathUid);

							FileWriter fw2 = new FileWriter(file2.getAbsoluteFile());
							BufferedWriter bw2 = new BufferedWriter(fw2);

							String col0;
							String col1;
							String col2;
							String col3;
							String col4;
							String col5;
							String col6;
							String col7;
							String col8;

							// loop for table rows
							for (DataUserId d2 : userIdData) {
								col0 = Integer.toString(d2.getId());
								col1 = d2.getUser();
								col2 = d2.getdPwd();
								col3 = d2.getsDate();
								col4 = d2.getmDate();
								col5 = Boolean.toString(d2.getVal());
								col6 = Boolean.toString(d2.getBlock());
								col7 = d2.getLvl();
								col8 = d2.getPwStat();

								if (!col1.equals("Admin")) {
									bw2.write(col0 + ";" + col1 + ";" + col2 + ";" + col3 + ";" + col4 + ";" + col5
											+ ";" + col6 + ";" + col7 + ";" + col8 + ";");
									// break line at the end
									bw2.write("\n");
								}
							}
							getLog().info("File authuserid2 has changed");
							// close BufferedWriter
							bw2.close();
							// close FileWriter
							fw2.close();
							/*
							 * end write to file
							 */
						} catch (Exception e1) {
							// TODO Auto-generated catch block
						}
						JFrame message = new JFrame();
						JOptionPane.showMessageDialog(message,
								"user " + inpUser + " resetted \n new initial password is " + inpIPw, "Info",
								JOptionPane.INFORMATION_MESSAGE);

					}
				}
				if (!existToReset) {
					JFrame message = new JFrame();
					JOptionPane.showMessageDialog(message, "user_id doesn't exists !!! \n Retry", "Info",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
			getMidLeftTopPanel().removeAll();
			getMidLeftTopPanel().add(combo);
			getMidLeftTopPanel().add(ok);
			getMidLeftTopPanel().add(getEmpty2());
			getMidLeftTopPanel().revalidate();
			getMidLeftTopPanel().repaint();

			break;
		/*
		 * case back: is used for go back from the Listings (User & Userid's)
		 */
		case "back":
			getMidLeftTopPanel().removeAll();
			getMidLeftTopPanel().add(combo);
			getMidLeftTopPanel().add(ok);
			getMidLeftTopPanel().revalidate();
			getMidLeftTopPanel().repaint();

			getMidRightPanel().removeAll();
			getMidRightPanel().setPreferredSize(new Dimension(720, 580));
			getMidRightPanel().revalidate();
			getMidRightPanel().repaint();

			break;

		case "usrSel":
			setUsrPat((String) getCmbUser().getSelectedItem());
		}
	}

	/*
	 * Getters and setters used in all of the classes
	 */
	public static JPanel getMidRightPanel() {
		return midRightPanel;
	}

	public void setMidRightPanel(JPanel midRightPanel) {
		AuthorizationManager2.midRightPanel = midRightPanel;
	}

	public static Logger getLog() {
		return log;
	}

	public static void setLog(Logger log) {
		AuthorizationManager2.log = log;
	}

	public static JScrollPane getJsp() {
		return jsp;
	}

	public void setJsp(JScrollPane jsp) {
		AuthorizationManager2.jsp = jsp;
	}

	public static String getUsrPat() {
		return usrPat;
	}

	public void setUsrPat(String usrPat) {
		AuthorizationManager2.usrPat = usrPat;
	}

	public static JPanel getMidLeftTopPanel() {
		return midLeftTopPanel;
	}

	public void setMidLeftTopPanel(JPanel midLeftTopPanel) {
		AuthorizationManager2.midLeftTopPanel = midLeftTopPanel;
	}

	public static JTextArea getEmpty2() {
		return empty2;
	}

	public void setEmpty2(JTextArea empty2) {
		AuthorizationManager2.empty2 = empty2;
	}

	public static JLabel getUsrSelect() {
		return usrSelect;
	}

	public void setUsrSelect(JLabel usrSelect) {
		AuthorizationManager2.usrSelect = usrSelect;
	}

	public static JComboBox<String> getCmbUser() {
		return cmbUser;
	}

	public void setCmbUser(JComboBox<String> cmbUser) {
		AuthorizationManager2.cmbUser = cmbUser;
	}

}
