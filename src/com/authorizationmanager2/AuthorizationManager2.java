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
import java.nio.file.LinkOption;
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
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import com.authorizationmanager2.data.FileBank;
import com.authorizationmanager2.data.DataUser;
import com.authorizationmanager2.data.DataUserId;
import com.authorizationmanager2.screens.ConsoleScrn;
import com.authorizationmanager2.screens.DelUserScrn;
import com.authorizationmanager2.screens.NewUserScrn;
import com.authorizationmanager2.screens.UpdUserScrn;
import com.authorizationmanager2.screens.UpdUsrIdScrn;
import com.authorizationmanager2.tabbedpane.Console;

/**
 * @author pfrolich
 * @version 1.0 Date : September 2020
 * 
 *          This is a program for managing users and passwords. 1. create new
 *          users and userid's 2. update users and options for userid's 3.
 *          delete users 4. list users and userid's 5. reset and revoke
 *          passwords (password will be an initial password)
 */

public class AuthorizationManager2 extends JFrame implements ActionListener {
	/*
	 * Environment definitions
	 * 
	 * private static String os;
	 */
	private static String workingDir;
	public static Thread thread1;

	/*
	 * Full screen definitions
	 */
	private static Dimension screenSize;
	public static int w;
	public static int h;
	public static int scrW;
	public static int scrH;

	private boolean fullScr = false;
	private boolean login = false;
	private static String screen = "default";

	private static GraphicsDevice gDevice;

	private String id;
	private String nameF;
	private String nameS;
	private String email;
	private String cVal;
	private String cLvl;
	private String cYrsS;
	private String cMthS;
	private String cDayS;
	private String cYrsE;
	private String cMthE;
	private String cDayE;
	private Boolean sts;
	private Boolean chk;
	private String user;
	private char[] pwdi;
	private String pwd;
	private String pat;
	private String patU;
	private Color color1;
	private Color color2;
	private int maxN;
	public static JTextArea cpyArea = new JTextArea(20, 29);
	/**
	 * Definitions: Panels, Buttons, ComboBoxes, TextAreas, TextFields, Labels,
	 * etc...
	 */
	private static final long serialVersionUID = 1L;
	public static List<DataUserId> userIdData;
	public static List<DataUser> userData;
	public static AuthorizationManager2 frame;
	private Container window;
	private JPanel mainPanel;
	private JPanel subMainLeftPanel;
	private JPanel subMainMidPanel;
	private JPanel subMainRightPanel;
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
	private static JButton back;
	public static JButton ok;
	private static JButton ok1;
	private static JButton ok2;

	private JTextArea empty;
	private static JTextArea empty2;
	public static JTextArea empty3;
	private static JTextField userId;

	private static JLabel usrSelect;
	private static JLabel userText;

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
	private static JLabel iPwdTxt;

	public static JPasswordField inpOldPw;
	public static JPasswordField inpPwdNew1;
	public static JPasswordField inpPwdNew2;
	public static JPasswordField iPwdInp;

	public static char[] pwOld;
	public static char[] pwNew1;
	public static char[] pwNew2;
	public static char[] iPwdNew;

	/*
	 * properties definitions
	 */
	private static String defPathIni;
	private static String defPathUsr;
	private static String defPathUsrH;
	private static String defPathUid;
	private static String defPathUidH;
	private static String defPathImg;
	private static String defBackup;
	private static String defConsole;
	private static String defConsoleB;
	private static String defPathLogProp;
	private static String defPathLogFile;

	private static Properties properties;
	private static String pathIni;
	private static String pathUsr;
	private static String pathUid;
	private static String pathUsrH;
	private static String pathUidH;
	private static String pathImg;
	private static String backup;
	private static String consoleF;
	private static String consoleFB;
	private static String fileExt;
	private static String fileUsr;
	private static String fileUid;
	private static String userPat;
	private static String pathLogProp;
	private static String pathLogFile;
	private static String dateTimeFormat;
	/*
	 * date definitions
	 */
	private static LocalDateTime dateTime;
	private static DateTimeFormatter dateFormatter;
	private static String fileDate;

	private Boolean blocked = false;
	private String inpUser;
	private String inpIPw;
	private String newPwd;
	private Boolean existToRevoke;
	private Boolean existToReset;
	private Boolean existConsole =  false;
	public static String option;
	/*
	 * Definitions for subMainRPanel
	 */
	private JButton exB, miB, maB;

	/*
	 * color definitions
	 */
	public static Color red1 = new Color(230, 204, 204);
	public static Color red2 = new Color(204, 0, 0);
	public static Color greend1 = new Color(26, 51, 0);
	public static Color vlgreen = new Color(235, 250, 235);
	public static Color green1 = new Color(46, 184, 46);
	public static SoftBevelBorder border = new SoftBevelBorder(BevelBorder.RAISED, greend1, greend1, greend1, greend1);
	/*
	 * fonts definitions
	 */
	public static Font font18;
	private static Font font16;
	private Font font14;
	public static Font font24;
	private Font title;
	/*
	 * logfile definitions
	 */
	private static Logger log = Logger.getLogger("com.authorizationmanager2");
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
				GraphicsEnvironment gEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
				gDevice = gEnvironment.getDefaultScreenDevice();
				screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				scrW = screenSize.width * 2 / 3;
				scrH = screenSize.height * 2 / 3;
				
				thread1 = new Thread(this, "thread1");

				workingDir = System.getProperty("user.dir");
//				os = System.getProperty("os.name").toLowerCase();

				defPathIni = "\\properties\\authorizationmanager2\\authman2.ini";
				pathIni = workingDir + defPathIni;

				/*
				 * load of properties file authman2.ini
				 */
				properties = new Properties();
				userIdData = new ArrayList<>();
				userData = new ArrayList<>();

				try {
					properties.load(
							new FileInputStream(pathIni));
					
					defPathUsr = properties.getProperty("path-usr");
					defPathUsrH = properties.getProperty("path-usrh");
					defPathUid = properties.getProperty("path-uid");
					defPathUidH = properties.getProperty("path-uidh");
					defPathImg = properties.getProperty("path-image");
					defBackup = properties.getProperty("path-backup");
					defConsole = properties.getProperty("path-console");
					defConsoleB = properties.getProperty("path-consoleB");
					defPathLogProp = properties.getProperty("path-log-properties");
					defPathLogFile = properties.getProperty("path-log-file");

					pathUsr = workingDir + defPathUsr;
					pathUsrH = workingDir + defPathUsrH;
					pathUid = workingDir + defPathUid;
					pathUidH = workingDir + defPathUidH;
					pathImg = workingDir + defPathImg;
					backup = workingDir + defBackup;
					consoleF = workingDir + defConsole;
					consoleFB = workingDir + defConsoleB;
					pathLogProp = workingDir + defPathLogProp;
					pathLogFile = workingDir + defPathLogFile;

					fileExt = properties.getProperty("file-ext");
					fileUsr = properties.getProperty("file-usr-startswith");
					fileUid = properties.getProperty("file-uid-startswith");
					userPat = properties.getProperty("user-patern"); 
					dateTimeFormat = properties.getProperty("datetime-format");
					
					LogManager.getLogManager().readConfiguration(new FileInputStream(pathLogProp));
					log.setLevel(Level.FINE);
					logFile = new FileHandler(pathLogFile, true);
					logFile.setFormatter(new com.authorizationmanager2.logging.MyFormatter());
					getLog().addHandler(logFile);

				} catch (IOException e1) {
					e1.printStackTrace();
				}
				frame = new AuthorizationManager2();
				frame.setTitle("Developed by P.E. Frölich, Barueri, September 2020");
				frame.setIconImage(Toolkit.getDefaultToolkit().getImage(pathImg + "pwmimage.png"));
				frame.setSize(scrW, scrH);
				frame.setBackground(greend1);
				frame.createGui();
				frame.setLocationRelativeTo(null);
				frame.setResizable(false);
				frame.setUndecorated(true);
				frame.setVisible(true);
			}
		});

	}

	protected void createGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window = getContentPane();
		window.setSize(scrW, scrH);
		window.setLayout(new FlowLayout());
		w = scrW;
		h = scrH;

		creFont();

		window.add(mainPanel());
	}

	/*
	 * create MainPanel
	 */
	private void creFont() {
		int f1 = (int) Math.round(w * 0.011);
		font14 = new Font("Arial", Font.BOLD, f1);
		int f2 = (int) Math.round(w * 0.01875);
		font24 = new Font(Font.SANS_SERIF, Font.BOLD, f2);
		int f3 = (int) Math.round(w * 0.0125);
		font16 = new Font(Font.SANS_SERIF, Font.BOLD, f3);
		int f4 = (int) Math.round(w * 0.014);
		font18 = new Font(Font.SANS_SERIF, Font.BOLD, f4);
		int ft = (int) Math.round(w * 0.0375);
		title = new Font(Font.SANS_SERIF, Font.BOLD, ft);

	}

	private void setFullScreen() {
		frame.remove(mainPanel);
		frame.repaint();
		gDevice.setFullScreenWindow(frame);

		scrW = frame.getWidth();
		scrH = frame.getHeight();

		creFont();
		createGui();

		if (login) {
			status.setForeground(Color.green);
			status.setText("true");
			name.setText("name: " + loginName);
			subMainMidPanel.remove(midPanel);
			getMidRightPanel().setPreferredSize(new Dimension((int) (w * 0.56), (int) (h * 0.78)));
			subMainMidPanel.revalidate();
			subMainMidPanel.repaint();

			getMidLeftTopPanel().add(combo);
			getMidLeftTopPanel().add(ok);
			getMidLeftTopPanel().add(getEmpty2());
		}
	}

	private void endFullScreen() {
		frame.remove(mainPanel);
		frame.repaint();
		gDevice.setFullScreenWindow(null);

		scrW = screenSize.width * 2 / 3;
		scrH = screenSize.height * 2 / 3;

		creFont();
		createGui();

		if (login) {
			status.setForeground(Color.green);
			status.setText("true");
			name.setText("name: " + loginName);
			subMainMidPanel.remove(midPanel);
			getMidRightPanel().setPreferredSize(new Dimension((int) (w * 0.56), (int) (h * 0.78)));
			subMainMidPanel.revalidate();
			subMainMidPanel.repaint();

			getMidLeftTopPanel().add(combo);
			getMidLeftTopPanel().add(ok);
			getMidLeftTopPanel().add(getEmpty2());
		}
	}

	private Component mainPanel() {
		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(scrW, scrH));
		mainPanel.setLayout(new FlowLayout(0));
		mainPanel.setBackground(vlgreen);

		mainPanel.add(subMainleftPanel());
		mainPanel.add(subMainMidPanel());
		mainPanel.add(subMainRightPanel());

		return mainPanel;
	}

	private Component subMainleftPanel() {
		subMainLeftPanel = new JPanel();
		subMainLeftPanel.setPreferredSize(new Dimension((int) (w * 0.108), h));
		subMainLeftPanel.setLayout(new FlowLayout());
		subMainLeftPanel.setBackground(greend1);

		return subMainLeftPanel;
	}

	private Component subMainMidPanel() {
		subMainMidPanel = new JPanel();
		subMainMidPanel.setPreferredSize(new Dimension((int) (w * 0.77), h));
		subMainMidPanel.setLayout(new FlowLayout());
		subMainMidPanel.setBackground(greend1);

		subMainMidPanel.add(topPanel());
		subMainMidPanel.add(midLeftPanel());
		subMainMidPanel.add(midPanel());
		subMainMidPanel.add(midRightPanel());
		subMainMidPanel.add(botPanel());

		return subMainMidPanel;
	}

	private Component subMainRightPanel() {
		subMainRightPanel = new JPanel();
		subMainRightPanel.setPreferredSize(new Dimension((int) (w * 0.108), scrH));
		subMainRightPanel.setLayout(new FlowLayout());
		subMainRightPanel.setBackground(greend1);

		int sbW = (int) Math.round(w * 0.025);
		int sbH = (int) Math.round(h * 0.035);
		exB = new JButton();
		exB.setIcon(new ImageIcon(pathImg + "iconexitg.png"));
		exB.setPreferredSize(new Dimension(sbW, sbH));
		exB.setFont(font18);
		exB.setBorder(border);
		exB.setBackground(vlgreen);
		exB.setActionCommand("exit");
		exB.addActionListener(this);

		miB = new JButton();
		miB.setIcon(new ImageIcon(pathImg + "iconming.png"));
		miB.setPreferredSize(new Dimension(sbW, sbH));
		miB.setFont(font18);
		miB.setBorder(border);
		miB.setBackground(vlgreen);
		miB.setActionCommand("minimize");
		miB.addActionListener(this);

		maB = new JButton();
		maB.setIcon(new ImageIcon(pathImg + "iconmax2g.gif"));
		maB.setPreferredSize(new Dimension(sbW, sbH));
		maB.setFont(font18);
		maB.setBorder(border);
		maB.setBackground(vlgreen);
		maB.setActionCommand("maximize");
		maB.addActionListener(this);

		subMainRightPanel.add(miB);
		subMainRightPanel.add(maB);
		subMainRightPanel.add(exB);

		return subMainRightPanel;
	}

	/*
	 * create TitlePanel - part of mainPanel
	 */
	private Component topPanel() {
		topPanel = new JPanel();
		topPanel.setPreferredSize(new Dimension((int) (w * 0.77), (int) (h * 0.125)));
		topPanel.setLayout(new FlowLayout());
		topPanel.setBackground(green1);

		JLabel titleInsert = new JLabel("Autorisatie administrator");
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
		midLeftPanel.setPreferredSize(new Dimension((int) (w * 0.1875), (int) (h * 0.78))); // (240, 580/560)
		midLeftPanel.setLayout(new FlowLayout());
		midLeftPanel.setBackground(greend1);

		midLeftPanel.add(midLeftTopPanel());
		midLeftPanel.add(midLeftBotPanel());

		return midLeftPanel;
	}

	/*
	 * create midLeftTopPanel - part of midLeftPanel contents - JCombobox - empty
	 * JTextarea's for filling up space - JButtons
	 */
	private Component midLeftTopPanel() {
		setMidLeftTopPanel(new JPanel());
		getMidLeftTopPanel().setPreferredSize(new Dimension((int) (w * 0.1875), (int) (h * 0.68))); // (240, 510/ 490)
		int y10 = (int) Math.round(h * 0.014); // 16
		int x10 = (int) Math.round(w * 0.008); // 20
		int tf12 = (int) Math.round(w * 0.009);
		getMidLeftTopPanel().setLayout(new FlowLayout(y10, x10, y10));
		getMidLeftTopPanel().setBackground(greend1);

		String cbActie[] = { "Add new user", "Update User", "Delete User", "Change options UserId",
				"Reset UserPassword", "Revoke User", "List Users", "List UserId's", "Write to File", "Read from File" };
		combo = new JComboBox<String>(cbActie);
		combo.setFont(font18);
		combo.setForeground(greend1);
		combo.setBackground(vlgreen);
		combo.setBorder(border);
		combo.addActionListener(this);
		combo.setActionCommand("actie");

		setUsrSelect(new JLabel("Select user Pattern:"));
		getUsrSelect().setForeground(vlgreen);
		getUsrSelect().setFont(font18);
		getUsrSelect().setBorder(null);

	 	String cbUsr[] = userPat.split(",");
		setCmbUser(new JComboBox<>(cbUsr));
		getCmbUser().setFont(font18);
		getCmbUser().setForeground(greend1);
		getCmbUser().setBackground(vlgreen);
		getCmbUser().setBorder(border);
		getCmbUser().setSelectedIndex(0);

		setiPwdTxt(new JLabel("new Password : "));
		getiPwdTxt().setForeground(vlgreen);
		getiPwdTxt().setFont(font18);
		getiPwdTxt().setBorder(null);

		iPwdInp = new JPasswordField(tf12);
		iPwdInp.setFont(font18);
		iPwdInp.setBorder(border);
		iPwdInp.setForeground(greend1);
		iPwdInp.setBackground(vlgreen);

		int ta8 = (int) Math.round(h * 0.0111);
		int ta20 = (int) Math.round(w * 0.0165);
		int ta24 = (int) Math.round(h * 0.0333);

		int e80 = (int) Math.round(w * 0.0625);
		int e100 = (int) Math.round(w * 0.078);
		int e40 = (int) Math.round(h * 0.056);
		int e50 = (int) Math.round(h * 0.069);

		setEmpty2(new JTextArea(ta8, ta20));
		getEmpty2().setEditable(false);
		getEmpty2().setBackground(greend1);

		empty3 = new JTextArea(ta24, ta20);
		empty3.setEditable(false);
		empty3.setBackground(greend1);

		setUserText(new JLabel());
		getUserText().setFont(font18);

		setUserId(new JTextField(tf12));
		getUserId().setFont(font18);
		getUserId().setForeground(greend1);
		getUserId().setBackground(vlgreen);

		ok = new JButton("OK");
		ok.setPreferredSize(new Dimension(e80, e40));
		ok.setFont(font18);
		ok.setForeground(greend1);
		ok.setBorder(border);
		ok.addActionListener(this);
		ok.setActionCommand("ok");

		setOk1(new JButton("OK"));
		getOk1().setPreferredSize(new Dimension(e80, e40));
		getOk1().setFont(font18);
		getOk1().setBorder(border);
		getOk1().setForeground(greend1);
		getOk1().addActionListener(this);
		getOk1().setActionCommand("ok1");

		setOk2(new JButton("OK"));
		getOk2().setPreferredSize(new Dimension(e80, e40));
		getOk2().setFont(font18);
		getOk2().setBorder(border);
		getOk2().setForeground(greend1);
		getOk2().addActionListener(this);
		getOk2().setActionCommand("ok2");

		setBack(new JButton("Back"));
		getBack().setPreferredSize(new Dimension(e100, e50));
		getBack().setFont(font18);
		getBack().setBorder(border);
		getBack().setForeground(greend1);
		getBack().addActionListener(this);
		getBack().setActionCommand("back");

		return getMidLeftTopPanel();
	}
	/*
	 * Create midLeftBotPanel - part of midLeftPanel Content - JButton (Quit
	 * program)
	 */

	private Component midLeftBotPanel() {
		int x10 = (int) Math.round(w * 0.008); // 20
		int e100 = (int) Math.round(w * 0.078);
		int e50 = (int) Math.round(h * 0.069);
		midLeftBotPanel = new JPanel();
		midLeftBotPanel.setPreferredSize(new Dimension((int) (w * 0.1875), (int) (h * 0.069))); // (240, 50)
		midLeftBotPanel.setLayout(new FlowLayout(0, x10, 0));
		midLeftBotPanel.setBackground(greend1);

		String[] buttonNames = { "Quit", "Logoff" };
		for (int i = 0; i < 2; i++) {
			button = new JButton(buttonNames[i]);
			button.setActionCommand(buttonNames[i]);
			button.setPreferredSize(new Dimension(e100, e50));
			button.setFont(font18);
			button.setBorder(border);
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
		int ta1 = (int) Math.round(h * 0.0014);
		int ta10 = (int) Math.round(w * 0.0078);
		int ta10h = (int) Math.round(h * 0.014);
		int ta11 = (int) Math.round(w * 0.0086);
		int ta30 = (int) Math.round(h * 0.041);

		midPanel = new JPanel();
		midPanel.setPreferredSize(new Dimension((int) (w * 0.523), (int) (h * 0.78))); // (670, 580/ 560)
		midPanel.setLayout(new BorderLayout());
		midPanel.setBackground(greend1);

		JTextArea emptyN = new JTextArea(ta1, ta10);
		emptyN.setEditable(false);
		emptyN.setBackground(vlgreen);
		JTextArea emptyW = new JTextArea(ta30, ta11);
		emptyW.setEditable(false);
		emptyW.setBackground(vlgreen);
		JTextArea emptyS = new JTextArea(ta10h, ta10);
		emptyS.setEditable(false);
		emptyS.setBackground(vlgreen);
		JTextArea emptyE = new JTextArea(ta30, ta11);
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
		loginPanel.setPreferredSize(new Dimension((int) (w * 0.234), (int) (h * 0.486))); // (300, 350)
		loginPanel.setLayout(new FlowLayout(0, 0, 0));
		loginPanel.setBackground(greend1);

		loginPanel.add(loginPanelL());
		loginPanel.add(loginPanelR());

		return loginPanel;
	}

	private Component loginPanelL() {
		int y20 = (int) Math.round(h * 0.028); // 16
		int x10 = (int) Math.round(w * 0.008); // 20
		int ta3 = (int) Math.round(h * 0.0042);
		int ta11 = (int) Math.round(w * 0.0086);
		loginPanelL = new JPanel();
		loginPanelL.setPreferredSize(new Dimension((int) (w * 0.117), (int) (h * 0.583))); // (150, 420)
		loginPanelL.setLayout(new FlowLayout(y20, x10, y20));
		loginPanelL.setBackground(greend1);

		empty = new JTextArea(ta3, ta11);
		empty.setEditable(false);
		empty.setBackground(greend1);

		lgnName = new JLabel("User :");
		lgnName.setForeground(vlgreen);
		lgnName.setFont(font24);
		lgnName.setBorder(null);

		password = new JLabel("Password :");
		password.setForeground(vlgreen);
		password.setFont(font24);
		password.setBorder(null);

		loginPanelL.add(empty);
		loginPanelL.add(lgnName);
		loginPanelL.add(password);
		return loginPanelL;
	}

	private Component loginPanelR() {
		int y20 = (int) Math.round(h * 0.028); // 16
		int x10 = (int) Math.round(w * 0.008); // 20
		int ta3 = (int) Math.round(h * 0.0042);
		int ta20 = (int) Math.round(w * 0.0165);
		int tf11 = (int) Math.round(w * 0.0086);
		int e100 = (int) Math.round(w * 0.078);
		int e40 = (int) Math.round(h * 0.056);
		loginPanelR = new JPanel();
		loginPanelR.setPreferredSize(new Dimension((int) (w * 0.195), (int) (h * 0.583)));// (250, 420)
		loginPanelR.setLayout(new FlowLayout(y20, x10, y20));
		loginPanelR.setBackground(greend1);

		empty = new JTextArea(ta3, ta20);
		empty.setEditable(false);
		empty.setBackground(greend1);

		inputName = new JTextField(tf11);
		inputName.setFont(font24);
		inputName.setBackground(vlgreen);
		inputName.setForeground(greend1);
		inputName.setBorder(border);
		inputName.addActionListener(this);
		inputName.setActionCommand("enter");

		inputPassword = new JPasswordField(tf11);
		inputPassword.setFont(font24);
		inputPassword.setBackground(vlgreen);
		inputPassword.setForeground(greend1);
		inputPassword.setBorder(border);
		inputPassword.addActionListener(this);
		inputPassword.setActionCommand("enter");

		beforeEnter = new JTextField(tf11);
		beforeEnter.setBackground(greend1);
		beforeEnter.setBorder(null);
		beforeEnter.setEditable(false);

		enter = new JButton("Enter");
		enter.setPreferredSize(new Dimension(e100, e40)); // (100, 40)
		enter.setActionCommand("enter");
		enter.setFont(font18);
		enter.setForeground(greend1);
		enter.setBorder(border);
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
		chgPwdPanel.setPreferredSize(new Dimension((int) (w * 0.3125), (int) (h * 0.486))); // (400, 350)
		chgPwdPanel.setBackground(greend1);

		chgPwdPanel.add(chgPwdPanelL());
		chgPwdPanel.add(chgPwdPanelR());
		return chgPwdPanel;
	}

	private Component chgPwdPanelL() {
		int y20 = (int) Math.round(h * 0.028); // 16
		int x10 = (int) Math.round(w * 0.008); // 20
		int ta1 = (int) Math.round(h * 0.0014);
		int ta11 = (int) Math.round(w * 0.0086);

		chgPwdPanelL = new JPanel();
		chgPwdPanelL.setPreferredSize(new Dimension((int) (w * 0.242), (int) (h * 0.583)));// (310, 420)
		chgPwdPanelL.setLayout(new FlowLayout(y20, x10, y20));
		chgPwdPanelL.setBackground(greend1);

		empty = new JTextArea(ta1, ta11);
		empty.setEditable(false);
		empty.setBackground(greend1);

		oldPw = new JLabel("Password old :");
		oldPw.setForeground(vlgreen);
		oldPw.setFont(font24);
		oldPw.setBorder(null);

		pwdNew1 = new JLabel("Password new :");
		pwdNew1.setForeground(vlgreen);
		pwdNew1.setFont(font24);
		pwdNew1.setBorder(null);

		pwdNew2 = new JLabel("Confirm password new :");
		pwdNew2.setForeground(vlgreen);
		pwdNew2.setFont(font24);
		pwdNew2.setBorder(null);

		chgPwdPanelL.add(empty);
		chgPwdPanelL.add(oldPw);
		chgPwdPanelL.add(pwdNew1);
		chgPwdPanelL.add(pwdNew2);

		return chgPwdPanelL;
	}

	private Component chgPwdPanelR() {
		int y20 = (int) Math.round(h * 0.028); // 16
		int x10 = (int) Math.round(w * 0.008); // 20
		int ta1 = (int) Math.round(h * 0.0014);
		int ta11 = (int) Math.round(w * 0.0086);
		int tf11 = (int) Math.round(w * 0.0086);
		int e100 = (int) Math.round(w * 0.078);
		int e40 = (int) Math.round(h * 0.056);
		empty = new JTextArea(ta1, ta11);
		empty.setEditable(false);
		empty.setBackground(greend1);

		chgPwdPanelR = new JPanel();
		chgPwdPanelR.setPreferredSize(new Dimension((int) (w * 0.195), (int) (h * 0.583)));// (250, 420)
		chgPwdPanelR.setLayout(new FlowLayout(y20, x10, y20));
		chgPwdPanelR.setBackground(greend1);

		inpOldPw = new JPasswordField(tf11);
		inpOldPw.setFont(font24);
		inpOldPw.setBackground(vlgreen);
		inpOldPw.setForeground(greend1);
		inpOldPw.setBorder(border);
		inpOldPw.addActionListener(this);
		inpOldPw.setActionCommand("enter2");

		inpPwdNew1 = new JPasswordField(tf11);
		inpPwdNew1.setFont(font24);
		inpPwdNew1.setBackground(vlgreen);
		inpPwdNew1.setForeground(greend1);
		inpPwdNew1.setBorder(border);
		inpPwdNew1.addActionListener(this);
		inpPwdNew1.setActionCommand("enter2");

		inpPwdNew2 = new JPasswordField(tf11);
		inpPwdNew2.setFont(font24);
		inpPwdNew2.setBackground(vlgreen);
		inpPwdNew2.setForeground(greend1);
		inpPwdNew2.setBorder(border);
		inpPwdNew2.addActionListener(this);
		inpPwdNew2.setActionCommand("enter2");

		beforeEnter = new JTextField(tf11);
		beforeEnter.setBackground(greend1);
		beforeEnter.setBorder(null);
		beforeEnter.setEditable(false);

		enter2 = new JButton("Enter");
		enter2.setPreferredSize(new Dimension(e100, e40));
		enter2.setActionCommand("enter2");
		enter2.setFont(font18);
		enter2.setForeground(greend1);
		enter2.setBorder(border);
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
		botPanel.setPreferredSize(new Dimension((int) (w * 0.77), (int) (h * 0.042))); // (990, 30)
		botPanel.setLayout(new FlowLayout());
		botPanel.setBackground(green1);

		name = new JLabel("Name: null ");
		name.setFont(font14);
		name.setForeground(vlgreen);
		loggedIn = new JLabel("Logged in: ");
		loggedIn.setFont(font14);
		loggedIn.setForeground(vlgreen);
		status = new JLabel("false");
		status.setFont(font14);
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
		case "exit": {
			log.info("Application PWManager closed");
			int reply = JOptionPane.showConfirmDialog(frame,
					"Are you sure? \nHave you saved the changes to file? \n" + "If no! choose first option WrFile ",
					"Close window", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (reply == JOptionPane.OK_OPTION) {
				// frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				System.exit(0);
			} else if (reply == JOptionPane.CANCEL_OPTION) {
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				log.info("Window not closed");
			}
		}
			break;
		/*
		 * case minimize
		 */
		case "minimize": {
			frame.setState(Frame.ICONIFIED);
		}
			break;
		/*
		 * case maximize is used for full screen mode and normal screen mode
		 */
		case "maximize":
			if (!fullScr) {
				fullScr = true;
				if (!login) {
					user = inputName.getText();
					pwdi = inputPassword.getPassword();
					pwd = String.valueOf(pwdi);

					setFullScreen();

					inputName.setText(user);
					inputPassword.setText(pwd);
				}

				if (login) {
					if (screen.matches("default")) {
						cpyArea.setText(Console.console.getText());
						setFullScreen();
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("new")) {
						id = NewUserScrn.getInpId().getText();
						color1 = NewUserScrn.getInpId().getBackground();
						nameF = NewUserScrn.getInpName().getText();
						nameS = NewUserScrn.getInpSname().getText();
						email = NewUserScrn.getInpEmail().getText();
						cVal = (String) NewUserScrn.comboValid.getSelectedItem();
						cYrsS = (String) NewUserScrn.comboYrS.getSelectedItem();
						cMthS = (String) NewUserScrn.comboMthS.getSelectedItem();
						cDayS = (String) NewUserScrn.comboDayS.getSelectedItem();
						user = NewUserScrn.getInpUserId().getText();
						pwdi = NewUserScrn.getInpPwInit().getPassword();
						color2 = NewUserScrn.getInpPwInit().getBackground();
						pwd = String.valueOf(pwdi);
						chk = NewUserScrn.checkS;
						pat = (String) getCmbUser().getSelectedItem();
						patU = NewUserScrn.usrPat;
						maxN = maxId;
						cpyArea.setText(Console.console.getText());

						setFullScreen();
						Actions.New();

						NewUserScrn.getInpId().setText(id);
						NewUserScrn.getInpId().setBackground(color1);
						NewUserScrn.getInpName().setText(nameF);
						NewUserScrn.getInpSname().setText(nameS);
						NewUserScrn.getInpEmail().setText(email);
						NewUserScrn.comboYrS.setSelectedItem(cYrsS);
						NewUserScrn.comboMthS.setSelectedItem(cMthS);
						NewUserScrn.comboDayS.setSelectedItem(cDayS);
						NewUserScrn.comboValid.setSelectedItem(cVal);
						NewUserScrn.getInpUserId().setText(user);
						NewUserScrn.getInpUserId().setBackground(color2);
						NewUserScrn.getInpPwInit().setText(pwd);
						NewUserScrn.checkS = chk;
						getCmbUser().setSelectedItem(pat);
						NewUserScrn.usrPat = patU;
						maxId = maxN;
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("update")) {
						id = UpdUserScrn.getInpId().getText();
						color1 = UpdUserScrn.getInpId().getBackground();
						nameF = UpdUserScrn.getInpFname().getText();
						nameS = UpdUserScrn.inpSname.getText();
						email = UpdUserScrn.inpEmail.getText();
						cVal = (String) UpdUserScrn.comboValid.getSelectedItem();
						cYrsS = (String) UpdUserScrn.comboYrS.getSelectedItem();
						cMthS = (String) UpdUserScrn.comboMthS.getSelectedItem();
						cDayS = (String) UpdUserScrn.comboDayS.getSelectedItem();
						sts = UpdUserScrn.status;
						cpyArea.setText(Console.console.getText());

						setFullScreen();
						Actions.Update();

						UpdUserScrn.getInpId().setText(id);
						UpdUserScrn.getInpId().setBackground(color1);
						UpdUserScrn.getInpFname().setText(nameF);
						UpdUserScrn.inpSname.setText(nameS);
						UpdUserScrn.inpEmail.setText(email);
						UpdUserScrn.comboYrS.setSelectedItem(cYrsS);
						UpdUserScrn.comboMthS.setSelectedItem(cMthS);
						UpdUserScrn.comboDayS.setSelectedItem(cDayS);
						UpdUserScrn.comboValid.setSelectedItem(cVal);
						UpdUserScrn.status = sts;
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("change")) {
						id = UpdUsrIdScrn.getInpId().getText();
						color1 = UpdUsrIdScrn.getInpId().getBackground();
						user = UpdUsrIdScrn.getInpUser().getText();
						cYrsS = (String) UpdUsrIdScrn.comboYrS.getSelectedItem();
						cMthS = (String) UpdUsrIdScrn.comboMthS.getSelectedItem();
						cDayS = (String) UpdUsrIdScrn.comboDayS.getSelectedItem();
						cYrsE = (String) UpdUsrIdScrn.comboYrE.getSelectedItem();
						cMthE = (String) UpdUsrIdScrn.comboMthE.getSelectedItem();
						cDayE = (String) UpdUsrIdScrn.comboDayE.getSelectedItem();
						cVal = (String) UpdUsrIdScrn.comboValid.getSelectedItem();
						cLvl = (String) UpdUsrIdScrn.comboLvl.getSelectedItem();
						sts = UpdUsrIdScrn.status;
						cpyArea.setText(Console.console.getText());

						setFullScreen();
						Actions.Change();

						UpdUsrIdScrn.getInpId().setText(id);
						UpdUsrIdScrn.getInpId().setBackground(color1);
						UpdUsrIdScrn.getInpUser().setText(user);
						UpdUsrIdScrn.comboYrS.setSelectedItem(cYrsS);
						UpdUsrIdScrn.comboMthS.setSelectedItem(cMthS);
						UpdUsrIdScrn.comboDayS.setSelectedItem(cDayS);
						UpdUsrIdScrn.comboYrE.setSelectedItem(cYrsE);
						UpdUsrIdScrn.comboMthE.setSelectedItem(cMthE);
						UpdUsrIdScrn.comboDayE.setSelectedItem(cDayE);
						UpdUsrIdScrn.comboValid.setSelectedItem(cVal);
						UpdUsrIdScrn.comboLvl.setSelectedItem(cLvl);
						UpdUsrIdScrn.status = sts;
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("delete")) {
						id = DelUserScrn.inpId.getText();
						color1 = DelUserScrn.inpId.getBackground();
						nameF = DelUserScrn.inpFname.getText();
						nameS = DelUserScrn.inpSname.getText();
						email = DelUserScrn.inpEmail.getText();
						cVal = (String) DelUserScrn.comboValid.getSelectedItem();
						cYrsS = (String) DelUserScrn.comboYrS.getSelectedItem();
						cMthS = (String) DelUserScrn.comboMthS.getSelectedItem();
						cDayS = (String) DelUserScrn.comboDayS.getSelectedItem();
						sts = DelUserScrn.status;
						cpyArea.setText(Console.console.getText());

						setFullScreen();
						Actions.Delete();

						DelUserScrn.inpId.setText(id);
						DelUserScrn.inpId.setBackground(color1);
						DelUserScrn.inpFname.setText(nameF);
						DelUserScrn.inpSname.setText(nameS);
						DelUserScrn.comboYrS.setSelectedItem(cYrsS);
						DelUserScrn.comboMthS.setSelectedItem(cMthS);
						DelUserScrn.comboDayS.setSelectedItem(cDayS);
						DelUserScrn.comboValid.setSelectedItem(cVal);
						DelUserScrn.status = sts;
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("reset")) {
						user = userId.getText();
						pwdi = iPwdInp.getPassword();
						pwd = String.valueOf(pwdi);

						setFullScreen();
						Actions.Reset();

						userId.setText(user);
						iPwdInp.setText(pwd);
					}
					if (screen.matches("revoke")) {
						user = userId.getText();

						setFullScreen();
						Actions.Revoke();

						userId.setText(user);
					}
					if (screen.matches("listuser")) {
						setFullScreen();
						Actions.ListUser();
					}
					if (screen.matches("listid")) {
						setFullScreen();
						Actions.ListId();
					}
				}
				maB.setIcon(new ImageIcon(pathImg + "iconmax3g.gif"));
				name.setText("name: " + loginName);
			} else {
				fullScr = false;

				if (!login) {
					user = inputName.getText();
					pwdi = inputPassword.getPassword();
					pwd = String.valueOf(pwdi);

					endFullScreen();

					inputName.setText(user);
					inputPassword.setText(pwd);
				}

				if (login) {
					if (screen.matches("default")) {
						cpyArea.setText(Console.console.getText());
						endFullScreen();
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("new")) {
						id = NewUserScrn.getInpId().getText();
						color1 = NewUserScrn.getInpId().getBackground();
						nameF = NewUserScrn.getInpName().getText();
						nameS = NewUserScrn.getInpSname().getText();
						email = NewUserScrn.getInpEmail().getText();
						cVal = (String) NewUserScrn.comboValid.getSelectedItem();
						cYrsS = (String) NewUserScrn.comboYrS.getSelectedItem();
						cMthS = (String) NewUserScrn.comboMthS.getSelectedItem();
						cDayS = (String) NewUserScrn.comboDayS.getSelectedItem();
						user = NewUserScrn.getInpUserId().getText();
						color2 = NewUserScrn.getInpPwInit().getBackground();
						pwdi = NewUserScrn.getInpPwInit().getPassword();
						pwd = String.valueOf(pwdi);
						chk = NewUserScrn.checkS;
						pat = (String) getCmbUser().getSelectedItem();
						patU = NewUserScrn.usrPat;
						maxN = maxId;
						cpyArea.setText(Console.console.getText());

						endFullScreen();
						Actions.New();

						NewUserScrn.getInpId().setText(id);
						NewUserScrn.getInpId().setBackground(color1);
						NewUserScrn.getInpName().setText(nameF);
						NewUserScrn.getInpSname().setText(nameS);
						NewUserScrn.getInpEmail().setText(email);
						NewUserScrn.comboYrS.setSelectedItem(cYrsS);
						NewUserScrn.comboMthS.setSelectedItem(cMthS);
						NewUserScrn.comboDayS.setSelectedItem(cDayS);
						NewUserScrn.comboValid.setSelectedItem(cVal);
						NewUserScrn.getInpUserId().setText(user);
						NewUserScrn.getInpUserId().setBackground(color2);
						NewUserScrn.getInpPwInit().setText(pwd);
						NewUserScrn.checkS = chk;
						getCmbUser().setSelectedItem(pat);
						NewUserScrn.usrPat = patU;
						maxId = maxN;
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("update")) {
						id = UpdUserScrn.getInpId().getText();
						nameF = UpdUserScrn.getInpFname().getText();
						nameS = UpdUserScrn.inpSname.getText();
						email = UpdUserScrn.inpEmail.getText();
						cVal = (String) UpdUserScrn.comboValid.getSelectedItem();
						cYrsS = (String) UpdUserScrn.comboYrS.getSelectedItem();
						cMthS = (String) UpdUserScrn.comboMthS.getSelectedItem();
						cDayS = (String) UpdUserScrn.comboDayS.getSelectedItem();
						sts = UpdUserScrn.status;
						cpyArea.setText(Console.console.getText());

						endFullScreen();
						Actions.Update();

						UpdUserScrn.getInpId().setText(id);
						UpdUserScrn.getInpFname().setText(nameF);
						UpdUserScrn.inpSname.setText(nameS);
						UpdUserScrn.inpEmail.setText(email);
						UpdUserScrn.comboYrS.setSelectedItem(cYrsS);
						UpdUserScrn.comboMthS.setSelectedItem(cMthS);
						UpdUserScrn.comboDayS.setSelectedItem(cDayS);
						UpdUserScrn.comboValid.setSelectedItem(cVal);
						UpdUserScrn.status = sts;
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("change")) {
						id = UpdUsrIdScrn.getInpId().getText();
						user = UpdUsrIdScrn.getInpUser().getText();
						cYrsS = (String) UpdUsrIdScrn.comboYrS.getSelectedItem();
						cMthS = (String) UpdUsrIdScrn.comboMthS.getSelectedItem();
						cDayS = (String) UpdUsrIdScrn.comboDayS.getSelectedItem();
						cYrsE = (String) UpdUsrIdScrn.comboYrE.getSelectedItem();
						cMthE = (String) UpdUsrIdScrn.comboMthE.getSelectedItem();
						cDayE = (String) UpdUsrIdScrn.comboDayE.getSelectedItem();
						cVal = (String) UpdUsrIdScrn.comboValid.getSelectedItem();
						cLvl = (String) UpdUsrIdScrn.comboLvl.getSelectedItem();
						sts = UpdUsrIdScrn.status;
						cpyArea.setText(Console.console.getText());

						endFullScreen();
						Actions.Change();

						UpdUsrIdScrn.getInpId().setText(id);
						UpdUsrIdScrn.getInpUser().setText(user);
						UpdUsrIdScrn.comboYrS.setSelectedItem(cYrsS);
						UpdUsrIdScrn.comboMthS.setSelectedItem(cMthS);
						UpdUsrIdScrn.comboDayS.setSelectedItem(cDayS);
						UpdUsrIdScrn.comboYrE.setSelectedItem(cYrsE);
						UpdUsrIdScrn.comboMthE.setSelectedItem(cMthE);
						UpdUsrIdScrn.comboDayE.setSelectedItem(cDayE);
						UpdUsrIdScrn.comboValid.setSelectedItem(cVal);
						UpdUsrIdScrn.comboLvl.setSelectedItem(cLvl);
						UpdUsrIdScrn.status = sts;
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("delete")) {
						id = DelUserScrn.inpId.getText();
						nameF = DelUserScrn.inpFname.getText();
						nameS = DelUserScrn.inpSname.getText();
						email = DelUserScrn.inpEmail.getText();
						cVal = (String) DelUserScrn.comboValid.getSelectedItem();
						cYrsS = (String) DelUserScrn.comboYrS.getSelectedItem();
						cMthS = (String) DelUserScrn.comboMthS.getSelectedItem();
						cDayS = (String) DelUserScrn.comboDayS.getSelectedItem();
						sts = DelUserScrn.status;
						cpyArea.setText(Console.console.getText());

						endFullScreen();
						Actions.Delete();

						DelUserScrn.inpId.setText(id);
						DelUserScrn.inpFname.setText(nameF);
						DelUserScrn.inpSname.setText(nameS);
						DelUserScrn.comboYrS.setSelectedItem(cYrsS);
						DelUserScrn.comboMthS.setSelectedItem(cMthS);
						DelUserScrn.comboDayS.setSelectedItem(cDayS);
						DelUserScrn.comboValid.setSelectedItem(cVal);
						DelUserScrn.status = sts;
						Console.console.setText(cpyArea.getText());
					}
					if (screen.matches("reset")) {
						user = userId.getText();
						pwdi = iPwdInp.getPassword();
						pwd = String.valueOf(pwdi);

						endFullScreen();
						Actions.Reset();

						userId.setText(user);
						iPwdInp.setText(pwd);
					}
					if (screen.matches("revoke")) {
						user = userId.getText();

						endFullScreen();
						Actions.Revoke();

						userId.setText(user);
					}
					if (screen.matches("listuser")) {
						endFullScreen();
						Actions.ListUser();
					}
					if (screen.matches("listid")) {
						endFullScreen();
						Actions.ListId();
					}
				}

				maB.setIcon(new ImageIcon(pathImg + "iconmax2g.gif"));
				name.setText("name: " + loginName);
			}
			break;
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
							if (chkPwStat.equals("i")) {
								int ta4 = (int) Math.round(w * 0.0023);
								int ta10 = (int) Math.round(w * 0.0078);
								int ta9 = (int) Math.round(h * 0.0125);
								int ta30 = (int) Math.round(h * 0.041);
								JTextArea emptyN = new JTextArea(ta9, ta10);
								emptyN.setEditable(false);
								emptyN.setBackground(vlgreen);
								JTextArea emptyW = new JTextArea(ta30, ta4);
								emptyW.setEditable(false);
								emptyW.setBackground(vlgreen);
								JTextArea emptyS = new JTextArea(ta9, ta10);
								emptyS.setEditable(false);
								emptyS.setBackground(vlgreen);
								JTextArea emptyE = new JTextArea(ta30, ta4);
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
								login = true;
								existConsole = true;
								status.setForeground(Color.green);
								status.setText("true");
								name.setText("name: " + isUser);
								subMainMidPanel.remove(midPanel);
								getMidRightPanel().setPreferredSize(new Dimension((int) (w * 0.56), (int) (h * 0.78)));
								getMidRightPanel().add(new ConsoleScrn(), "newPanel");
								subMainMidPanel.revalidate();
								subMainMidPanel.repaint();

								getMidLeftTopPanel().add(combo);
								getMidLeftTopPanel().add(ok);
								getMidLeftTopPanel().add(getEmpty2());

								getLog().info("User " + loginName + " logged in ");

								Console.console.append("Data Imported");
								cpyArea.setText(Console.console.getText());
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
								Files.setAttribute(source2, "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);

								File file2 = new File(pathUid);
								file2.setWritable(true);

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

								Files.setAttribute(source2, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
								file2.setReadOnly();
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
			subMainMidPanel.removeAll();

			subMainMidPanel.add(topPanel());
			subMainMidPanel.add(midLeftPanel());
			subMainMidPanel.add(midPanel());
			subMainMidPanel.add(midRightPanel());
			subMainMidPanel.add(botPanel());

			subMainMidPanel.repaint();
			subMainMidPanel.revalidate();

			break;

		/*
		 * case Quit: closes the program
		 */
		case "Quit":
			if (status.getText().equals("false")) {

				dateTime = LocalDateTime.now();
				dateFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
				fileDate = dateTime.format(dateFormatter);

				if (existConsole) {

					try {
					 	if (!Console.console.getText().isEmpty()) {
						Path source1 = Paths.get(consoleF);
						Path dest1 = Paths.get(consoleFB + fileDate + fileExt);

							if (Files.exists(source1)) {
								Files.copy(source1, dest1, StandardCopyOption.REPLACE_EXISTING);
							}

							File fileC = new File(consoleF);
							FileWriter fwC;

							fwC = new FileWriter(fileC.getAbsoluteFile());
							BufferedWriter bwC = new BufferedWriter(fwC);
							bwC.write(Console.console.getText());
							bwC.close();
							fwC.close();
					 	}
					 	
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				getLog().info("Program Stopped");
				System.exit(0);
			} else {
				Console.console.setForeground(red2);
				Console.console.append("\nLog off first");
			}

			break;

		/*
		 * case Logoff: Logoff userid
		 */
		case "Logoff":
			if (status.getText().equals("true")) {
				JFrame option = new JFrame();
				option.setBackground(vlgreen);
				option.setForeground(greend1);
				
				int reply = JOptionPane.showConfirmDialog(option, "Before you log off SAVE your changes to file!! \n"
						+ "or you will loose all your changes \n \nChoose OK if you want to log off", "Logged off",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (reply == JOptionPane.OK_OPTION) {
					login = false;
					subMainMidPanel.removeAll();
					subMainMidPanel.add(topPanel());
					subMainMidPanel.add(midLeftPanel());
					subMainMidPanel.add(midPanel());
					subMainMidPanel.add(midRightPanel());
					subMainMidPanel.add(botPanel());
					status.setForeground(Color.red);
					status.setText("false");
					name.setText("name: " + "");

					mainPanel.revalidate();
					mainPanel.repaint();

					getLog().info("User " + loginName + " logged off ");
				} else if (reply == JOptionPane.CANCEL_OPTION) {
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
					Actions.New();
				}
				if (selCmb.contentEquals("Update User")) {
					Actions.Update();
				}
				if (selCmb.contentEquals("Change options UserId")) {
					Actions.Change();
				}
				if (selCmb.contentEquals("Delete User")) {
					Actions.Delete();
				}
				if (selCmb.contentEquals("Reset UserPassword")) {
					Actions.Reset();
				}
				if (selCmb.contentEquals("Revoke User")) {
					Actions.Revoke();
				}
				if (selCmb.contentEquals("List Users")) {
					Actions.ListUser();
				}
				if (selCmb.contentEquals("List UserId's")) {
					Actions.ListId();
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
							file1.setWritable(true);
							Files.setAttribute(source2, "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
							file2.setWritable(true);

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

							file1.setReadOnly();
							file2.setReadOnly();
							Files.setAttribute(source2, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);

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
						BufferedReader br1 = new BufferedReader(new FileReader(pathUid));
						BufferedReader br2 = new BufferedReader(new FileReader(pathUsr));
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
					Console.console.append("\nData Imported");
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
				Console.console.setForeground(red2);
				Console.console.append("\nNo input !!");
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
						Console.console.append("\nUser: " + inpUser + " revoked");
					}
				}
				if (!existToRevoke) {
					Console.console.setForeground(red2);
					Console.console.append("\nUser-Id : " + inpUser + " doesn't exists !!! \n Retry");
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
			dateTime = LocalDateTime.now();
			dateFormatter = DateTimeFormatter.ofPattern(dateTimeFormat);
			fileDate = dateTime.format(dateFormatter);

			existToReset = false;

			if (inpUser.isEmpty() || inpIPw.isEmpty()) {
				Console.console.setForeground(red2);
				Console.console.append("\nUser or new Password is missing !!");
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
							Boolean val = true;
							Boolean blk = userIdData.get(i).getBlock();
							String level = userIdData.get(i).getLvl();
							String pwStat = "i";
							AuthorizationManager2.userIdData.set(i,
									new DataUserId(authId, user, newPwd2, sdate, edate, val, blk, level, pwStat));
							AuthorizationManager2.getLog().info("userid: " + user + " resetted");
							/*
							 * write to file after password change
							 */
							Path source2 = Paths.get(pathUid);
							Path dest2 = Paths.get(pathUidH + fileDate + fileExt);
							if (Files.exists(source2)) {
								Files.copy(source2, dest2, StandardCopyOption.REPLACE_EXISTING);
							}
							File file2 = new File(pathUid);
							Files.setAttribute(source2, "dos:hidden", false, LinkOption.NOFOLLOW_LINKS);
							file2.setWritable(true);
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
							Files.setAttribute(source2, "dos:hidden", true, LinkOption.NOFOLLOW_LINKS);
							file2.setReadOnly();

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
					Console.console.setForeground(red2);
					Console.console.append("\nUser-Id : " + inpUser + " doesn't exists !!! \n Retry");
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
			cpyArea.setText(Console.console.getText());
			getMidLeftTopPanel().removeAll();
			getMidLeftTopPanel().add(combo);
			getMidLeftTopPanel().add(ok);
			getMidLeftTopPanel().revalidate();
			getMidLeftTopPanel().repaint();

			getMidRightPanel().removeAll();
			getMidRightPanel().setPreferredSize(new Dimension((int) (w * 0.56), (int) (h * 0.78)));
			getMidRightPanel().add(new ConsoleScrn(), "newPanel");			
			getMidRightPanel().revalidate();
			getMidRightPanel().repaint();
			
			Console.console.setText(cpyArea.getText());
			setScreen("default");

			break;
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

	public static void setJsp(JScrollPane jsp) {
		AuthorizationManager2.jsp = jsp;
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

	public static JLabel getUserText() {
		return userText;
	}

	public void setUserText(JLabel userText) {
		AuthorizationManager2.userText = userText;
	}

	public static JTextField getUserId() {
		return userId;
	}

	public void setUserId(JTextField userId) {
		AuthorizationManager2.userId = userId;
	}

	public static JLabel getiPwdTxt() {
		return iPwdTxt;
	}

	public void setiPwdTxt(JLabel iPwdTxt) {
		AuthorizationManager2.iPwdTxt = iPwdTxt;
	}

	public static JButton getOk2() {
		return ok2;
	}

	public void setOk2(JButton ok2) {
		AuthorizationManager2.ok2 = ok2;
	}

	public String getScreen() {
		return screen;
	}

	public static void setScreen(String screen) {
		AuthorizationManager2.screen = screen;
	}

	public static JButton getOk1() {
		return ok1;
	}

	public void setOk1(JButton ok1) {
		AuthorizationManager2.ok1 = ok1;
	}

	public static Font getFont16() {
		return font16;
	}

	public void setFont16(Font font16) {
		AuthorizationManager2.font16 = font16;
	}

	public static JButton getBack() {
		return back;
	}

	public void setBack(JButton back) {
		AuthorizationManager2.back = back;
	}

}
