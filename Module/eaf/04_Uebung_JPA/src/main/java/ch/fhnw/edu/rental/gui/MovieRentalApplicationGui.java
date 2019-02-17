package ch.fhnw.edu.rental.gui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import ch.fhnw.edu.rental.gui.BusinessLogic.UserVisitor;

public class MovieRentalApplicationGui extends JFrame {

	private static final long serialVersionUID = -222656902828639843L;
	public static final String LOOK_AND_FEEL_ERR_MSG = "Error attempting to set look and feel of the system";
	public static final String DATA_DASE_ERR_MSG = "Error attempting to initialize database";
	private static final String ABOUT_ERR_MSG = "Error attempting to launch web browser.";

	public MovieRentalApplicationGui(BusinessLogic logic) {
		// set system look and feel of the application
		try {
			String ui = UIManager.getSystemLookAndFeelClassName();
			UIManager.setLookAndFeel(ui);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, LOOK_AND_FEEL_ERR_MSG + ":\n" + e.getLocalizedMessage());
		}

		// initialize rental database
		try {
			services = logic;
			mappers = new MovieRentalMappers(services);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,
					DATA_DASE_ERR_MSG + ":\n" + e.getLocalizedMessage() + "\nApplication is closed.");
			System.exit(JFrame.EXIT_ON_CLOSE);
		}

		// initialize application components
		initComponents();
	}

	private void initComponents() {
		aboutDialog = new JDialog();
		JLabel headingLabel = new JLabel();
		JLabel headingTwoLabel = new JLabel();
		JLabel descriptionLabelAbout = new JLabel();
		JLabel productLabel = new JLabel();
		JLabel vendorLabel = new JLabel();
		JLabel authorsLabel = new JLabel();
		JLabel homepageLabel = new JLabel();
		JLabel versionNrLabel = new JLabel();
		JLabel vendorTextLabel = new JLabel();
		JLabel authorsTextLabel = new JLabel();
		JLabel homepageTextLabel = new JLabel();
		JButton cancelAboutButton = new JButton();
		aboutSeparator = new JSeparator();
		getRentalsDialog = new JDialog();
		getRentalsScrollPane = new JScrollPane();
		getRentalsTable = new JTable();
		closeGetRentalsButton = new JButton();
		deleteGetRentalsButton = new JButton();
		JLabel userGetRentalsLabel = new JLabel();
		userGetRentalsTextLabel = new JLabel();
		mainPane = new JTabbedPane();
		mainPanel = new JPanel();
		JLabel lastNameLabel = new JLabel();
		JLabel firstNameLabel = new JLabel();
		JLabel rentalDateLabel = new JLabel();
		JLabel rentalDaysLabel = new JLabel();
		rentalDaysTextField = new JTextField();
		rentalDateTextField = new JTextField();
		firstNameTextField = new JTextField();
		lastNameTextField = new JTextField();
		getUserButton = new JButton();
		saveRentalButton = new JButton();
		newUserCheckBox = new JCheckBox();
		JLabel userIdLabel = new JLabel();
		clearAllButton = new JButton();
		userIdForTextField = new JFormattedTextField();
		movieScrollPane = new JScrollPane();
		movieTable = new JTable();
		movieCRUDPanel = new JPanel();
		JScrollPane movieCRUDScrollPane = new JScrollPane();
		movieCRUDTable = new JTable();
		saveMoviesButton = new JButton();
		newMoviesButton = new JButton();
		deleteMoviesButton = new JButton();
		editMoviesButton = new JButton();
		JLabel movieTitleMoviesLabel = new JLabel();
		JLabel releaseDateMoviesLabel = new JLabel();
		JLabel priceCatMoviesLabel = new JLabel();
		priceCatMoviesComboBox = new JComboBox<>();
		releaseDateMoviesTextField = new JTextField();
		movieTitleMoviesTextField = new JTextField();
		cancelMoviesButton = new JButton();
		usersCRUDPanel = new JPanel();
		JScrollPane usersCRUDScrollPane = new JScrollPane();
		usersCRUDTable = new JTable();
		saveUsersButton = new JButton();
		deleteUsersButton = new JButton();
		editUsersButton = new JButton();
		newUsersButton = new JButton();
		JLabel firstNameUsersLabel = new JLabel();
		cancelUsersButton = new JButton();
		firstNameUsersTextField = new JTextField();
		JLabel lastNameUsersLabel = new JLabel();
		lastNameUsersTextField = new JTextField();
		getRentalsButton = new JButton();
		rentalsCRUDPanel = new JPanel();
		JScrollPane rentalsCRUDScrollPane = new JScrollPane();
		rentalsCRUDTable = new JTable();
		deleteRentalsButton = new JButton();
		menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu();
		exitMenuItem = new JMenuItem();
		JMenu helpMenu = new JMenu();
		aboutMenuItem = new JMenuItem();
		refreshButton1 = new JButton();
		refreshButton2 = new JButton();
		refreshButton3 = new JButton();
		refreshButton4 = new JButton();

		refreshButton1.setText("Refresh");
		refreshButton1.setEnabled(true);
		refreshButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// refreshButtonActionPerformed(evt); FIXME add refresh method
			}
		});
		refreshButton2.setText("Refresh");
		refreshButton2.setEnabled(true);
		refreshButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// refreshButtonActionPerformed(evt); FIXME add refresh method
			}
		});
		refreshButton3.setText("Refresh");
		refreshButton3.setEnabled(true);
		refreshButton3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// refreshButtonActionPerformed(evt); FIXME add refresh method
			}
		});
		refreshButton4.setText("Refresh");
		refreshButton4.setEnabled(true);
		refreshButton4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				// refreshButtonActionPerformed(evt); FIXME add refresh method
			}
		});

		aboutDialog.setTitle("About ...");
		aboutDialog.setMinimumSize(new Dimension(460, 265));
		aboutDialog.setResizable(false);
		aboutDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				aboutDialogWindowClosing(evt);
			}
		});
		headingLabel.setFont(new Font("Tahoma", 1, 16));
		headingLabel.setText("EAF Project");

		descriptionLabelAbout.setText(
				"<html>A movie rental application for the " + "\"Enterprise Application Framework\" lab at the "
						+ "University of Applied Sciences " + "Northwestern Switzerland brought to you by the IMVS.");

		productLabel.setFont(new Font("Tahoma", 1, 11));
		productLabel.setText("Product Version:");

		vendorLabel.setFont(new Font("Tahoma", 1, 11));
		vendorLabel.setText("Vendor:");

		authorsLabel.setFont(new Font("Tahoma", 1, 11));
		authorsLabel.setText("Authors(s):");

		homepageLabel.setFont(new Font("Tahoma", 1, 11));
		homepageLabel.setText("Homepage:");

		versionNrLabel.setText("1.0");

		vendorTextLabel.setText("University of Applied Sciences Northwestern Switzerland");

		authorsTextLabel.setText("Dominik Gruntz | J\u00fcrg Luthiger");

		homepageTextLabel.setText("http://www.fhnw.ch/technik/imvs");
		homepageTextLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				homepageTextLabelMouseClicked(evt);
			}
		});

		cancelAboutButton.setText("Close");
		cancelAboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				cancelAboutButtonActionPerformed(evt);
			}
		});

		GroupLayout aboutDialogLayout = new GroupLayout(aboutDialog.getContentPane());
		aboutDialog.getContentPane().setLayout(aboutDialogLayout);
		aboutDialogLayout.setHorizontalGroup(aboutDialogLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(aboutDialogLayout.createSequentialGroup().addContainerGap()
						.addGroup(aboutDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(aboutDialogLayout.createSequentialGroup()
										.addComponent(descriptionLabelAbout, GroupLayout.DEFAULT_SIZE, 421,
												Short.MAX_VALUE)
										.addContainerGap())
								.addGroup(aboutDialogLayout.createSequentialGroup()
										.addComponent(headingTwoLabel, GroupLayout.DEFAULT_SIZE, 61,
												Short.MAX_VALUE)
										.addGap(370, 370, 370))
								.addComponent(headingLabel)
								.addGroup(aboutDialogLayout.createSequentialGroup().addComponent(productLabel)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 313,
												Short.MAX_VALUE)
										.addComponent(versionNrLabel).addContainerGap())
								.addGroup(aboutDialogLayout.createSequentialGroup().addComponent(vendorLabel)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 108,
												Short.MAX_VALUE)
										.addComponent(vendorTextLabel).addContainerGap())
								.addGroup(aboutDialogLayout.createSequentialGroup().addComponent(authorsLabel)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 115,
												Short.MAX_VALUE)
										.addComponent(authorsTextLabel).addContainerGap())
								.addGroup(aboutDialogLayout.createSequentialGroup().addComponent(homepageLabel)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 258,
												Short.MAX_VALUE)
										.addComponent(homepageTextLabel).addContainerGap())
								.addGroup(GroupLayout.Alignment.TRAILING,
										aboutDialogLayout.createSequentialGroup().addComponent(cancelAboutButton)
												.addContainerGap())))
				.addGroup(aboutDialogLayout.createSequentialGroup()
						.addComponent(aboutSeparator, GroupLayout.DEFAULT_SIZE, 431, Short.MAX_VALUE)
						.addContainerGap()));
		aboutDialogLayout.setVerticalGroup(aboutDialogLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(aboutDialogLayout.createSequentialGroup()
						.addGroup(aboutDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addGroup(aboutDialogLayout.createSequentialGroup().addContainerGap()
										.addComponent(headingLabel).addGap(18, 18, 18).addComponent(headingTwoLabel)))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(descriptionLabelAbout).addGap(18, 18, 18)
						.addGroup(aboutDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(productLabel).addComponent(versionNrLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(aboutDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(vendorLabel).addComponent(vendorTextLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(aboutDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(authorsLabel).addComponent(authorsTextLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(aboutDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(homepageLabel).addComponent(homepageTextLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
								GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(cancelAboutButton).addGap(1, 1, 1).addComponent(aboutSeparator,
								GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)));

		getRentalsDialog.setTitle("Rental Overview");
		getRentalsDialog.setMinimumSize(new Dimension(600, 200));
		getRentalsDialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				getRentalsDialogWindowClosing(evt);
			}
		});

		getRentalsTable.setModel(new DefaultTableModel(new Object[][] {

		}, new String[] { "Rental ID", "Rental Days", "Rental Date", "Title", "Remaining Days", "Rental Fee" }) {
			private static final long serialVersionUID = -615724883768145787L;
			private Class<?>[] types = new Class[] { java.lang.Long.class, java.lang.Integer.class,
					java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class };
			private boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});
		getRentalsTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				getRentalsTableMouseClicked(evt);
			}
		});
		getRentalsTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				getRentalsTableKeyPressed(evt);
			}
		});
		getRentalsScrollPane.setViewportView(getRentalsTable);

		closeGetRentalsButton.setText("Close");
		closeGetRentalsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				closeGetRentalsButtonActionPerformed(evt);
			}
		});

		deleteGetRentalsButton.setText("Delete");
		deleteGetRentalsButton.setEnabled(false);
		deleteGetRentalsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				deleteGetRentalsButtonActionPerformed(evt);
			}
		});

		userGetRentalsLabel.setFont(new Font("Tahoma", 1, 11));
		userGetRentalsLabel.setText("User:");

		GroupLayout getRentalsDialogLayout = new GroupLayout(getRentalsDialog.getContentPane());
		getRentalsDialog.getContentPane().setLayout(getRentalsDialogLayout);
		getRentalsDialogLayout.setHorizontalGroup(getRentalsDialogLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, getRentalsDialogLayout.createSequentialGroup()
						.addContainerGap().addComponent(userGetRentalsLabel).addGap(18, 18, 18)
						.addComponent(userGetRentalsTextLabel)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
						.addComponent(deleteGetRentalsButton)
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(closeGetRentalsButton).addContainerGap())
				.addComponent(getRentalsScrollPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE));
		getRentalsDialogLayout.setVerticalGroup(getRentalsDialogLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, getRentalsDialogLayout.createSequentialGroup()
						.addComponent(getRentalsScrollPane, GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addGroup(getRentalsDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(closeGetRentalsButton).addComponent(deleteGetRentalsButton)
								.addComponent(userGetRentalsLabel).addComponent(userGetRentalsTextLabel))
						.addContainerGap()));

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("EAF Lab");
		setName("mainFrame");

		mainPane.setName("mainPane");
		mainPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evt) {
				mainPaneStateChanged(evt);
			}
		});

		initRentMoviePanel(lastNameLabel, firstNameLabel, rentalDateLabel, rentalDaysLabel, userIdLabel);

		GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
		mainPanel.setLayout(mainPanelLayout);
		mainPanelLayout
				.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(GroupLayout.Alignment.TRAILING,
								mainPanelLayout.createSequentialGroup().addContainerGap().addComponent(refreshButton1)
										.addGap(290, 290, 290).addComponent(clearAllButton)
										.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
										.addGap(18, 18, 18).addComponent(saveRentalButton).addContainerGap())
						.addGroup(mainPanelLayout.createSequentialGroup().addContainerGap()
								.addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
										.addComponent(rentalDaysLabel).addComponent(rentalDateLabel)
										.addComponent(firstNameLabel).addComponent(lastNameLabel))
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(mainPanelLayout
										.createParallelGroup(GroupLayout.Alignment.LEADING, false)
										.addGroup(mainPanelLayout.createSequentialGroup()
												.addComponent(lastNameTextField, GroupLayout.PREFERRED_SIZE,
														125, GroupLayout.PREFERRED_SIZE)
												.addGap(18, 18, 18).addComponent(userIdLabel)
												.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(userIdForTextField,
														GroupLayout.PREFERRED_SIZE, 40,
														GroupLayout.PREFERRED_SIZE))
										.addComponent(firstNameTextField).addComponent(rentalDateTextField)
										.addComponent(rentalDaysTextField))
								.addGap(68, 68, 68).addComponent(newUserCheckBox)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(getUserButton)
								.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(movieScrollPane, GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE));
		mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
						.addComponent(movieScrollPane, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lastNameLabel)
								.addComponent(lastNameTextField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(userIdLabel)
								.addComponent(userIdForTextField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(getUserButton).addComponent(newUserCheckBox))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(firstNameLabel).addComponent(firstNameTextField,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(rentalDateLabel).addComponent(rentalDateTextField,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(rentalDaysLabel).addComponent(rentalDaysTextField,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(saveRentalButton).addComponent(clearAllButton)
								.addComponent(refreshButton1))
						.addContainerGap()));

		mainPane.addTab("Rent Movie", mainPanel);

		movieCRUDPanel.setName("moviesTab");

		movieCRUDTable
				.setModel(new DefaultTableModel(new Object[][] { {}, {}, {}, {} }, new String[] {

		}));
		movieCRUDTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				movieCRUDTableMouseClicked(evt);
			}
		});
		movieCRUDTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				movieCRUDTableKeyPressed(evt);
			}
		});
		movieCRUDScrollPane.setViewportView(movieCRUDTable);

		saveMoviesButton.setText("Save");
		saveMoviesButton.setEnabled(false);
		saveMoviesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				saveMoviesButtonActionPerformed(evt);
			}
		});

		newMoviesButton.setText("New ...");
		newMoviesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				newMoviesButtonActionPerformed(evt);
			}
		});

		deleteMoviesButton.setText("Delete");
		deleteMoviesButton.setEnabled(false);
		deleteMoviesButton.addActionListener(e -> deleteMoviesButtonActionPerformed(e));

		editMoviesButton.setText("Edit");
		editMoviesButton.setEnabled(false);
		editMoviesButton.addActionListener(e -> editMoviesButtonActionPerformed(e));

		movieTitleMoviesLabel.setText("Movie Title:");

		releaseDateMoviesLabel.setText("Release Date:");

		priceCatMoviesLabel.setText("Price category:");

		priceCatMoviesComboBox.setModel(
				new DefaultComboBoxModel<String>(new String[] { "Children", "New Release", "Regular" }));
		priceCatMoviesComboBox.setToolTipText("Please select a price category.");
		priceCatMoviesComboBox.setEnabled(false);
		priceCatMoviesComboBox.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				priceCatMoviesComboBoxKeyPressed(evt);
			}
		});

		releaseDateMoviesTextField.setText(currDate);
		releaseDateMoviesTextField.setToolTipText("Please enter the release date of the movie.");
		releaseDateMoviesTextField.setEnabled(false);
		releaseDateMoviesTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				priceCatMoviesComboBoxKeyPressed(evt);
			}
		});

		movieTitleMoviesTextField.setToolTipText("Please enter or re-enter the title of the movie.");
		movieTitleMoviesTextField.setEnabled(false);
		movieTitleMoviesTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				priceCatMoviesComboBoxKeyPressed(evt);
			}
		});

		cancelMoviesButton.setText("Cancel");
		cancelMoviesButton.setEnabled(false);
		cancelMoviesButton.addActionListener(e-> cancelMoviesButtonActionPerformed(e));

		GroupLayout movieCRUDPanelLayout = new GroupLayout(movieCRUDPanel);
		movieCRUDPanel.setLayout(movieCRUDPanelLayout);
		movieCRUDPanelLayout.setHorizontalGroup(movieCRUDPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING,
						movieCRUDPanelLayout.createSequentialGroup().addContainerGap().addComponent(refreshButton2)
								.addContainerGap(149, Short.MAX_VALUE).addComponent(cancelMoviesButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(newMoviesButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(editMoviesButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(deleteMoviesButton).addGap(18, 18, 18).addComponent(saveMoviesButton)
								.addContainerGap())
				.addGroup(GroupLayout.Alignment.TRAILING, movieCRUDPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(priceCatMoviesLabel).addComponent(releaseDateMoviesLabel)
								.addComponent(movieTitleMoviesLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(movieTitleMoviesTextField, GroupLayout.DEFAULT_SIZE, 412,
										Short.MAX_VALUE)
								.addGroup(movieCRUDPanelLayout
										.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
										.addComponent(releaseDateMoviesTextField,
												GroupLayout.Alignment.LEADING)
										.addComponent(priceCatMoviesComboBox, GroupLayout.Alignment.LEADING,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)))
						.addContainerGap())
				.addComponent(movieCRUDScrollPane, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE));
		movieCRUDPanelLayout.setVerticalGroup(movieCRUDPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, movieCRUDPanelLayout.createSequentialGroup()
						.addComponent(movieCRUDScrollPane, GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addGroup(movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(movieTitleMoviesLabel).addComponent(movieTitleMoviesTextField,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(releaseDateMoviesLabel).addComponent(releaseDateMoviesTextField,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(priceCatMoviesLabel).addComponent(priceCatMoviesComboBox,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(saveMoviesButton).addComponent(deleteMoviesButton)
								.addComponent(editMoviesButton).addComponent(newMoviesButton)
								.addComponent(cancelMoviesButton).addComponent(refreshButton2))
						.addContainerGap()));

		mainPane.addTab("Movies",
				new ImageIcon(getClass().getResource("/ch/fhnw/edu/rental/gui/resources/movieLogo.png")),
				movieCRUDPanel); // NOI18N

		usersCRUDPanel.setName("usersTab");

		usersCRUDTable
				.setModel(new DefaultTableModel(new Object[][] { {}, {}, {}, {} }, new String[] {

		}));
		usersCRUDTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				usersCRUDTableMouseClicked(evt);
			}
		});
		usersCRUDTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				usersCRUDTableKeyPressed(evt);
			}
		});
		usersCRUDScrollPane.setViewportView(usersCRUDTable);

		saveUsersButton.setText("Save");
		saveUsersButton.setEnabled(false);
		saveUsersButton.addActionListener(e -> saveUsersButtonActionPerformed(e));

		deleteUsersButton.setText("Delete");
		deleteUsersButton.setEnabled(false);
		deleteUsersButton.addActionListener(e -> deleteUsersButtonActionPerformed(e));

		editUsersButton.setText("Edit");
		editUsersButton.setEnabled(false);
		editUsersButton.addActionListener(e -> editUsersButtonActionPerformed(e));

		newUsersButton.setText("New ...");
		newUsersButton.addActionListener(e -> newUsersButtonActionPerformed(e));

		firstNameUsersLabel.setText("First name:");

		cancelUsersButton.setText("Cancel");
		cancelUsersButton.setEnabled(false);
		cancelUsersButton.addActionListener(e -> cancelUsersButtonActionPerformed(e));

		firstNameUsersTextField.setEnabled(false);
		firstNameUsersTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				firstNameUsersTextFieldKeyPressed(evt);
			}
		});

		lastNameUsersLabel.setText("Last name:");

		lastNameUsersTextField.setEnabled(false);
		lastNameUsersTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				lastNameUsersTextFieldKeyPressed(evt);
			}
		});

		getRentalsButton.setText("Get Rentals");
		getRentalsButton.setToolTipText("Get rentals of the selected user.");
		getRentalsButton.setEnabled(false);
		getRentalsButton.addActionListener(e -> getRentalsButtonActionPerformed(e));

		GroupLayout usersCRUDPanelLayout = new GroupLayout(usersCRUDPanel);
		usersCRUDPanel.setLayout(usersCRUDPanelLayout);
		usersCRUDPanelLayout.setHorizontalGroup(usersCRUDPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING,
						usersCRUDPanelLayout.createSequentialGroup().addContainerGap().addComponent(refreshButton3)
								.addContainerGap(149, Short.MAX_VALUE).addComponent(cancelUsersButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(newUsersButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(editUsersButton)
								.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(deleteUsersButton).addGap(18, 18, 18).addComponent(saveUsersButton)
								.addContainerGap())
				.addGroup(GroupLayout.Alignment.TRAILING, usersCRUDPanelLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(usersCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(firstNameUsersLabel).addComponent(lastNameUsersLabel))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(usersCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(lastNameUsersTextField, GroupLayout.Alignment.LEADING,
										GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
								.addComponent(firstNameUsersTextField, GroupLayout.Alignment.LEADING,
										GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
						.addGap(53, 53, 53).addComponent(getRentalsButton).addContainerGap())
				.addComponent(usersCRUDScrollPane, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE));
		usersCRUDPanelLayout.setVerticalGroup(usersCRUDPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, usersCRUDPanelLayout.createSequentialGroup()
						.addComponent(usersCRUDScrollPane, GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
						.addGap(18, 18, 18)
						.addGroup(usersCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(lastNameUsersLabel)
								.addComponent(lastNameUsersTextField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(getRentalsButton))
						.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(usersCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(firstNameUsersLabel).addComponent(firstNameUsersTextField,
										GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addGap(18, 18, 18)
						.addGroup(usersCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(saveUsersButton).addComponent(deleteUsersButton)
								.addComponent(editUsersButton).addComponent(newUsersButton)
								.addComponent(cancelUsersButton).addComponent(refreshButton3))
						.addContainerGap()));

		mainPane.addTab("Users",
				new ImageIcon(getClass().getResource("/ch/fhnw/edu/rental/gui/resources/userLogo.png")),
				usersCRUDPanel); // NOI18N

		rentalsCRUDPanel.setName("rentalsTab");

		rentalsCRUDTable
				.setModel(new DefaultTableModel(new Object[][] { {}, {}, {}, {} }, new String[] {

		}));
		rentalsCRUDTable.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				rentalsCRUDTableMouseClicked(evt);
			}
		});
		rentalsCRUDTable.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				rentalsCRUDTableKeyPressed(evt);
			}
		});
		rentalsCRUDScrollPane.setViewportView(rentalsCRUDTable);

		deleteRentalsButton.setText("Delete");
		deleteRentalsButton.setEnabled(false);
		deleteRentalsButton.addActionListener(e -> deleteRentalsButtonActionPerformed(e));

		GroupLayout rentalsCRUDPanelLayout = new GroupLayout(rentalsCRUDPanel);
		rentalsCRUDPanel.setLayout(rentalsCRUDPanelLayout);
		rentalsCRUDPanelLayout.setHorizontalGroup(rentalsCRUDPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(rentalsCRUDScrollPane, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE)
				.addGroup(GroupLayout.Alignment.TRAILING, rentalsCRUDPanelLayout.createSequentialGroup()
						.addContainerGap(149, Short.MAX_VALUE).addComponent(deleteRentalsButton).addContainerGap()));
		rentalsCRUDPanelLayout.setVerticalGroup(rentalsCRUDPanelLayout
				.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(GroupLayout.Alignment.TRAILING, rentalsCRUDPanelLayout.createSequentialGroup()
						.addComponent(rentalsCRUDScrollPane, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE)
						.addGap(18, 18, 18).addComponent(deleteRentalsButton).addContainerGap()));

		mainPane.addTab("Rentals",
				new ImageIcon(getClass().getResource("/ch/fhnw/edu/rental/gui/resources/rentalLogo.png")),
				rentalsCRUDPanel); // NOI18N

		menuBar.setName("menuBar");

		fileMenu.setText("File");
		fileMenu.setName("fileMenu");

		exitMenuItem.setAccelerator(
				KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		exitMenuItem.setText("Exit");
		exitMenuItem.setName("exitMenuItem");
		exitMenuItem.addActionListener(e -> exitMenuItemActionPerformed(e));
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);

		helpMenu.setText("Help");
		helpMenu.setName("helpMenu");

		aboutMenuItem.setText("About ...");
		aboutMenuItem.setName("aboutMenuItem");
		aboutMenuItem.addActionListener(e -> aboutMenuItemActionPerformed(e));
		helpMenu.add(aboutMenuItem);

		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(mainPane, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(mainPane, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE));

		pack();
	} 

	private void initRentMoviePanel(JLabel lastNameLabel, JLabel firstNameLabel,
			JLabel rentalDateLabel, JLabel rentalDaysLabel, JLabel userIdLabel) {
		mainPanel.setName("rentMovieTab");

		lastNameLabel.setText("Last Name:");
		lastNameLabel.setToolTipText("Please enter the last name of the customer.");

		firstNameLabel.setText("First Name:");
		firstNameLabel.setToolTipText("Please enter the first name of the customer.");

		rentalDateLabel.setText("Rental Date:");

		rentalDaysLabel.setText("Rental Days:");

		rentalDaysTextField.setEditable(true);
		rentalDaysTextField.setText("1");
		rentalDaysTextField.setToolTipText("Please enter how long the movie will be rented.");
		rentalDaysTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				rentalDaysTextFieldKeyPressed(evt);
			}
		});

		rentalDateTextField.setEditable(false);
		rentalDateTextField.setText(currDate);
		rentalDateTextField.setToolTipText("Rental date is by default todays date.");

		firstNameTextField.setEditable(true);
		firstNameTextField.setToolTipText("Please enter first name of the customer.");
		firstNameTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				firstNameTextFieldKeyPressed(evt);
			}
		});

		lastNameTextField.setEditable(true);
		lastNameTextField.setToolTipText("Please enter last name of the customer.");
		lastNameTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				lastNameTextFieldKeyPressed(evt);
			}
		});

		getUserButton.setText("get User");
		getUserButton.setToolTipText("Gets the entered user from the database.");
		getUserButton.setEnabled(false);
		getUserButton.addActionListener(e -> getUserButtonActionPerformed(e));

		saveRentalButton.setText("Save");
		saveRentalButton.setEnabled(false);
		saveRentalButton.addActionListener(e -> saveRentalButtonActionPerformed(e));

		newUserCheckBox.setText("new User");
		newUserCheckBox.setToolTipText("Check if the user is not stored or find in the database.");
		newUserCheckBox.setEnabled(false);
		newUserCheckBox.addActionListener(e -> newUserCheckBoxActionPerformed(e));

		userIdLabel.setText("User ID:");

		clearAllButton.setText("Clear All");
		clearAllButton.setEnabled(false);
		clearAllButton.addActionListener(e -> resetRentalActionForm(e));

		userIdForTextField.setEditable(false);
		userIdForTextField.setToolTipText("Enter user id to find the user.");
		userIdForTextField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent evt) {
				userIdForTextFieldKeyPressed(evt);
			}
		});

		movieTable.setModel(new DefaultTableModel(mappers.getMovieListAsObject(false),
				new String[] { "Movie ID", "Title", "Release Date", "Is Rented?", "Price Category" }) {
			private static final long serialVersionUID = -5376743898459692217L;
			private Class<?>[] types = new Class[] { java.lang.Long.class, java.lang.String.class,
					java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		movieTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		movieScrollPane.setViewportView(movieTable);
	}

	private void userIdForTextFieldKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetRentalActionForm(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			getUserButtonActionPerformed(null);
			rentalDaysTextField.requestFocus();
		}
	}

	private void lastNameUsersTextFieldKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			firstNameUsersTextField.requestFocus();
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelUsersButtonActionPerformed(null);
		}
	}

	private void firstNameTextFieldKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetRentalActionForm(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			rentalDaysTextField.requestFocus();
		}
	}

	private void priceCatMoviesComboBoxKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			saveMoviesButtonActionPerformed(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			saveMoviesButton.requestFocus();
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelMoviesButtonActionPerformed(null);
		}
	}

	private void firstNameUsersTextFieldKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
			saveUsersButtonActionPerformed(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			saveUsersButton.requestFocus();
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			cancelUsersButtonActionPerformed(null);
		}
	}

	private void getRentalsTableKeyPressed(KeyEvent evt) {
		switch (evt.getKeyCode()) {
		case KeyEvent.VK_UP:
			getRentalsTableMouseClicked(null);
			break;
		case KeyEvent.VK_DOWN:
			getRentalsTableMouseClicked(null);
			break;
		case KeyEvent.VK_ESCAPE:
			getRentalsButtonActionPerformed(null);
			break;
		case KeyEvent.VK_TAB:
			closeGetRentalsButton.requestFocus();
			break;
		default:
			// do nothing
			break;
		}
	}

	private void getRentalsDialogWindowClosing(WindowEvent evt) {
		this.setEnabled(true);
	}

	private void aboutDialogWindowClosing(WindowEvent evt) {
		this.setEnabled(true);
	}

	// Method which is called when getRentals is called on a selected user.
	// The only operations provided by this dialog is delete (and close)
	private void deleteGetRentalsButtonActionPerformed(ActionEvent evt) {
		// get id of the selected rental
		int selRow = getRentalsTable.getSelectedRow();
		Long rentalId = (Long) getRentalsTable.getModel().getValueAt(selRow, 0);

		services.removeRental(rentalId);

		deleteGetRentalsButton.setEnabled(false);
		getRentalsTable.requestFocus();

		getRentalsButtonActionPerformed(null);
	}

	private void getRentalsTableMouseClicked(MouseEvent evt) {
		deleteGetRentalsButton.setEnabled(true);
	}

	private void closeGetRentalsButtonActionPerformed(ActionEvent evt) {
		getRentalsDialog.setVisible(false);

		editUsersButton.setEnabled(false);
		deleteUsersButton.setEnabled(false);
		getRentalsButton.setEnabled(false);

		this.setEnabled(true);
		this.requestFocus();
	}

	private void getRentalsButtonActionPerformed(ActionEvent evt) {
		getRentalsDialog.setVisible(true);
		deleteGetRentalsButton.setEnabled(false);

		Long userId = (Long) usersCRUDTable.getModel().getValueAt(usersCRUDTable.getSelectedRow(), 0);
		String lastName = services.getUserLastName(userId);
		String firstName = services.getUserFirstName(userId);
		userGetRentalsTextLabel.setText(firstName + " " + lastName);

		// fill table
		getRentalsTable.setModel(new DefaultTableModel(mappers.getRentalListAsObjectForUser(userId),
				new String[] { "Rental ID", "Rental Days", "Rental Date", "Title", "Remaining Days", "Rental Fee" }) {
			private static final long serialVersionUID = 3969122560547437541L;
			private Class<?>[] types = new Class[] { java.lang.Long.class, java.lang.Integer.class,
					java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class };
			private boolean[] canEdit = new boolean[] { false, false, false, false, false, false };

			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return canEdit[columnIndex];
			}
		});

		this.setEnabled(false);
	}

	private void homepageTextLabelMouseClicked(MouseEvent evt) {
		try {
			Desktop.getDesktop().browse(new URI("http://www.fhnw.ch"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, ABOUT_ERR_MSG + ":\n" + e.getLocalizedMessage());
		}
	}

	private void cancelAboutButtonActionPerformed(ActionEvent evt) {
		aboutDialog.setVisible(false);
		this.setEnabled(true);
		this.requestFocus();
	}

	private void movieCRUDTableKeyPressed(KeyEvent evt) {
		if (movieCRUDTable.getRowCount() > 0) {
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_UP:
				movieCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DOWN:
				movieCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DELETE:
				deleteMoviesButtonActionPerformed(null);
				break;
			case KeyEvent.VK_ESCAPE:
				cancelMoviesButtonActionPerformed(null);
				break;
			case KeyEvent.VK_TAB:
				if (deleteMoviesButton.isEnabled()) {
					deleteMoviesButton.requestFocus();
				} else {
					editMoviesButton.requestFocus();
				}
				break;
			default:
				// do nothing
				break;
			}
		}
	}

	private void usersCRUDTableKeyPressed(KeyEvent evt) {
		if (usersCRUDTable.getRowCount() > 0) {
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_UP:
				usersCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DOWN:
				usersCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DELETE:
				deleteUsersButtonActionPerformed(null);
				break;
			case KeyEvent.VK_ESCAPE:
				cancelUsersButtonActionPerformed(null);
				break;
			default:
				// do nothing
				break;
			}
		}
	}

	private void rentalsCRUDTableKeyPressed(KeyEvent evt) {
		if (rentalsCRUDTable.getRowCount() > 0) {
			switch (evt.getKeyCode()) {
			case KeyEvent.VK_DOWN:
				rentalsCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_UP:
				rentalsCRUDTableMouseClicked(null);
				break;
			case KeyEvent.VK_DELETE:
				deleteRentalsButtonActionPerformed(null);
				break;
			case KeyEvent.VK_ESCAPE:
				deleteRentalsButton.setEnabled(false);
				mainPaneStateChanged(null);
				break;
			case KeyEvent.VK_TAB:
				deleteRentalsButton.requestFocus();
				break;
			default:
				// do nothing
				break;
			}
		}
	}

	private void editUsersButtonActionPerformed(ActionEvent evt) {
		Long userId = (Long) usersCRUDTable.getModel().getValueAt(usersCRUDTable.getSelectedRow(), 0);
		String lastName = services.getUserLastName(userId);
		String firstName = services.getUserFirstName(userId);

		lastNameUsersTextField.setText(lastName);
		firstNameUsersTextField.setText(firstName);

		lastNameUsersTextField.setEnabled(true);
		firstNameUsersTextField.setEnabled(true);

		newUsersButton.setEnabled(false);
		deleteUsersButton.setEnabled(false);
		editUsersButton.setEnabled(false);
		cancelUsersButton.setEnabled(true);
		saveUsersButton.setEnabled(true);
		getRentalsButton.setEnabled(false);

		this.editMode = true;
	}

	private void deleteUsersButtonActionPerformed(ActionEvent evt) {
		Long userId = (Long) usersCRUDTable.getModel().getValueAt(usersCRUDTable.getSelectedRow(), 0);
		String lastName = services.getUserLastName(userId);
		String firstName = services.getUserFirstName(userId);

		if (services.getUserRentalsSize(userId) == 0) {
			try {
				services.deleteUser(userId);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
			}
		} else {
			JOptionPane.showMessageDialog(null, "There are still rentals from \"" + firstName + " " + lastName + "\".\n"
					+ "User can not be deleted.");
		}

		deleteUsersButton.setEnabled(false);
		editUsersButton.setEnabled(false);
		newUsersButton.setEnabled(true);
		cancelUsersButton.setEnabled(false);
		getRentalsButton.setEnabled(false);

		usersCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void cancelUsersButtonActionPerformed(ActionEvent evt) {
		cancelUsersButton.setEnabled(false);
		newUsersButton.setEnabled(true);
		deleteUsersButton.setEnabled(false);
		editUsersButton.setEnabled(false);
		saveUsersButton.setEnabled(false);
		getRentalsButton.setEnabled(false);

		lastNameUsersTextField.setEnabled(false);
		lastNameUsersTextField.setText("");
		firstNameUsersTextField.setEnabled(false);
		firstNameUsersTextField.setText("");

		usersCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void usersCRUDTableMouseClicked(MouseEvent evt) {
		if (usersCRUDTable.getRowCount() > 0) {
			editUsersButton.setEnabled(true);
			deleteUsersButton.setEnabled(true);
			saveUsersButton.setEnabled(false);
			cancelUsersButton.setEnabled(false);
			newUsersButton.setEnabled(true);
			getRentalsButton.setEnabled(true);

			lastNameUsersTextField.setEnabled(false);
			lastNameUsersTextField.setText("");
			firstNameUsersTextField.setEnabled(false);
			firstNameUsersTextField.setText("");
		} else {
			cancelUsersButtonActionPerformed(null);
		}

	}

	private void saveUsersButtonActionPerformed(ActionEvent evt) {
		String lastName = lastNameUsersTextField.getText();
		String firstName = firstNameUsersTextField.getText();

		if (!lastName.isEmpty()) {
			if (editMode) {
				// update user
				Long userId = (Long) usersCRUDTable.getModel().getValueAt(usersCRUDTable.getSelectedRow(), 0);
				services.updateUser(userId, lastName, firstName);
				editMode = false;
			} else {
				// new user
				services.createUser(lastName, firstName);
			}

			lastNameUsersTextField.setEnabled(false);
			firstNameUsersTextField.setEnabled(false);

			newUsersButton.setEnabled(true);
			saveUsersButton.setEnabled(false);
			cancelUsersButton.setEnabled(false);
			deleteUsersButton.setEnabled(false);
			editUsersButton.setEnabled(false);
			getRentalsButton.setEnabled(false);

			mainPaneStateChanged(null);
		} else {
			JOptionPane.showMessageDialog(null, "Last name must not be empty.");
			lastNameTextField.requestFocus();
			newUsersButtonActionPerformed(null);
		}
	}

	private void newUsersButtonActionPerformed(ActionEvent evt) {
		lastNameUsersTextField.setEnabled(true);
		lastNameUsersTextField.setText("");
		firstNameUsersTextField.setEnabled(true);
		firstNameUsersTextField.setText("");

		newUsersButton.setEnabled(false);
		cancelUsersButton.setEnabled(true);
		saveUsersButton.setEnabled(true);
		editUsersButton.setEnabled(false);
		deleteUsersButton.setEnabled(false);
		getRentalsButton.setEnabled(false);

		lastNameUsersTextField.requestFocus();
	}

	private void cancelMoviesButtonActionPerformed(ActionEvent evt) {
		// reset buttons
		newMoviesButton.setEnabled(true);
		editMoviesButton.setEnabled(false);
		deleteMoviesButton.setEnabled(false);
		saveMoviesButton.setEnabled(false);
		cancelMoviesButton.setEnabled(false);

		// clear all text fields
		movieTitleMoviesTextField.setEnabled(false);
		movieTitleMoviesTextField.setText("");
		releaseDateMoviesTextField.setEnabled(false);
		releaseDateMoviesTextField.setText("");

		priceCatMoviesComboBox.setEnabled(false);

		movieCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void editMoviesButtonActionPerformed(ActionEvent evt) {
		int rowCount = movieCRUDTable.getSelectedRow();
		Long movieId = (Long) movieCRUDTable.getModel().getValueAt(rowCount, 0);
		String title = services.getMovieTitle(movieId);
		String priceCategory = services.getMoviePriceCategory(movieId);
		LocalDate releaseDate = services.getMovieReleaseDate(movieId);

		// fill text fields with information
		movieTitleMoviesTextField.setText(title);
		releaseDateMoviesTextField.setText(releaseDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		priceCatMoviesComboBox.setSelectedItem(priceCategory);

		movieTitleMoviesTextField.setEnabled(false);
		releaseDateMoviesTextField.setEnabled(false);
		priceCatMoviesComboBox.setEnabled(true);

		saveMoviesButton.setEnabled(true);
		cancelMoviesButton.setEnabled(true);
		deleteMoviesButton.setEnabled(false);
		newMoviesButton.setEnabled(false);
		editMoviesButton.setEnabled(false);

		movieTitleMoviesTextField.requestFocus();

		// set edit mode
		this.editMode = true;
	}

	private void rentalsCRUDTableMouseClicked(MouseEvent evt) {
		if (rentalsCRUDTable.getRowCount() > 0) {
			deleteRentalsButton.setEnabled(true);
		} else {
			deleteRentalsButton.setEnabled(false);
		}
	}

	// called when a rental is deleted on the rentals tab
	private void deleteRentalsButtonActionPerformed(ActionEvent evt) {
		int selRow = rentalsCRUDTable.getSelectedRow();
		Long rentalId = (Long) rentalsCRUDTable.getModel().getValueAt(selRow, 0);

		services.removeRental(rentalId);

		deleteRentalsButton.setEnabled(false);
		rentalsCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void deleteMoviesButtonActionPerformed(ActionEvent evt) {
		int rowCount = movieCRUDTable.getSelectedRow();
		Long movieId = (Long) movieCRUDTable.getModel().getValueAt(rowCount, 0);
		String title = services.getMovieTitle(movieId);
		boolean isRented = services.getMovieIsRented(movieId);

		if (!isRented) {
			services.deleteMovie(movieId);
		} else {
			JOptionPane.showMessageDialog(null,
					"The movie \"" + title + "\" is rented.\n" + "Movie can not be deleted.");
		}

		deleteMoviesButton.setEnabled(false);
		editMoviesButton.setEnabled(false);
		newMoviesButton.setEnabled(true);
		cancelMoviesButton.setEnabled(false);

		movieCRUDTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private void movieCRUDTableMouseClicked(MouseEvent evt) {
		if (movieCRUDTable.getRowCount() > 0) {
			deleteMoviesButton.setEnabled(true);
			editMoviesButton.setEnabled(true);
			newMoviesButton.setEnabled(true);
			saveMoviesButton.setEnabled(false);
			cancelMoviesButton.setEnabled(false);

			movieTitleMoviesTextField.setEnabled(false);
			movieTitleMoviesTextField.setText("");
			releaseDateMoviesTextField.setEnabled(false);
			releaseDateMoviesTextField.setText("");

			priceCatMoviesComboBox.setEnabled(false);
		} else {
			cancelMoviesButtonActionPerformed(null);
		}
	}

	private void saveMoviesButtonActionPerformed(ActionEvent evt) {
		String movieTitle = movieTitleMoviesTextField.getText();
		String releaseDate = releaseDateMoviesTextField.getText();
		LocalDate date = LocalDate.now();
		try {
			date = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Date format can not be parsed and is replaced"
					+ " by the current date:\n" + e.getLocalizedMessage());
		}

		if (!movieTitle.isEmpty()) {
			String category = (String) priceCatMoviesComboBox.getSelectedItem();
			if (editMode) {
				// update movie
				int rowCount = movieCRUDTable.getSelectedRow();
				Long movieId = (Long) movieCRUDTable.getModel().getValueAt(rowCount, 0);
				services.updateMovie(movieId, movieTitle, date, category);
				editMode = false;
			} else {
				// new movie
				services.createMovie(movieTitle, date, category);
			}

			// reset buttons and text fields
			movieTitleMoviesTextField.setEnabled(false);
			releaseDateMoviesTextField.setEnabled(false);
			priceCatMoviesComboBox.setEnabled(false);

			newMoviesButton.setEnabled(true);
			saveMoviesButton.setEnabled(false);
			cancelMoviesButton.setEnabled(false);
			editMoviesButton.setEnabled(false);
			deleteMoviesButton.setEnabled(false);

			mainPaneStateChanged(null);
		} else {
			JOptionPane.showMessageDialog(null, "Movie title must not be empty.");
			movieTitleMoviesTextField.requestFocus();
			newMoviesButtonActionPerformed(null);
		}
	}

	private void newMoviesButtonActionPerformed(ActionEvent evt) {
		// set all text fields enabled
		movieTitleMoviesTextField.setEnabled(true);
		movieTitleMoviesTextField.setText("");
		releaseDateMoviesTextField.setEnabled(true);
		releaseDateMoviesTextField.setText(currDate);
		priceCatMoviesComboBox.setEnabled(true);

		newMoviesButton.setEnabled(false);
		cancelMoviesButton.setEnabled(true);
		saveMoviesButton.setEnabled(true);

		movieTitleMoviesTextField.requestFocus();
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void mainPaneStateChanged(ChangeEvent evt) {
		// refresh data in tables when it is selected
		switch (mainPane.getSelectedIndex()) {
		case 0:
			// Rent Movie Tab
			// set new model with new data
			movieTable.setModel(new DefaultTableModel(mappers.getMovieListAsObject(false),
					new String[] { "Movie ID", "Title", "Release Date", "Is Rented?", "Price Category" }) {
				private static final long serialVersionUID = 2456659513544091063L;
				private Class<?>[] types = new Class[] { java.lang.Long.class, java.lang.String.class,
						java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class };
				private boolean[] canEdit = new boolean[] { false, false, false, false, false };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			lastNameTextField.setEditable(true);
			lastNameTextField.setText("");
			firstNameTextField.setEditable(true);
			firstNameTextField.setText("");
			rentalDateTextField.setEditable(false);
			rentalDateTextField.setText(currDate);
			rentalDaysTextField.setEditable(true);
			rentalDaysTextField.setText("1");
			userIdForTextField.setEditable(true);
			userIdForTextField.setText("");

			newUserCheckBox.setSelected(false);
			newUserCheckBox.setEnabled(true);

			getUserButton.setEnabled(true);
			saveRentalButton.setEnabled(false);
			clearAllButton.setEnabled(true);

			break;
		case 1:
			// Movies Tab
			// set new model with new data
			movieCRUDTable.setModel(new DefaultTableModel(mappers.getMovieListAsObject(),
					new String[] { "Movie ID", "Title", "Release Date", "Is Rented?", "Price Category" }) {
				private static final long serialVersionUID = -8807187529192958223L;
				private Class<?>[] types = new Class[] { java.lang.Long.class, java.lang.String.class,
						java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class };
				private boolean[] canEdit = new boolean[] { false, false, false, false, false };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			newMoviesButton.setEnabled(true);
			editMoviesButton.setEnabled(false);
			deleteMoviesButton.setEnabled(false);
			saveMoviesButton.setEnabled(false);
			cancelMoviesButton.setEnabled(false);

			movieTitleMoviesTextField.setEnabled(false);
			movieTitleMoviesTextField.setText("");
			releaseDateMoviesTextField.setEnabled(false);
			releaseDateMoviesTextField.setText("");

			priceCatMoviesComboBox.setEnabled(false);

			break;

		case 2:
			// Users Tab
			usersCRUDTable.setModel(new DefaultTableModel(mappers.getUserListAsObject(),
					new String[] { "User ID", "Last name", "First name" }) {
				private static final long serialVersionUID = 8362335490919602337L;
				private Class<?>[] types = new Class[] { java.lang.Long.class, java.lang.String.class,
						java.lang.String.class };
				private boolean[] canEdit = new boolean[] { false, false, false };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			cancelUsersButton.setEnabled(false);
			newUsersButton.setEnabled(true);
			deleteUsersButton.setEnabled(false);
			editUsersButton.setEnabled(false);
			saveUsersButton.setEnabled(false);
			getRentalsButton.setEnabled(false);

			lastNameUsersTextField.setEnabled(false);
			lastNameUsersTextField.setText("");
			firstNameUsersTextField.setEnabled(false);
			firstNameUsersTextField.setText("");

			break;

		case 3:
			// Rentals Tab
			rentalsCRUDTable.setModel(new DefaultTableModel(mappers.getRentalListAsObject(),
					new String[] { "Rental ID", "Rental Days", "Rental Date", "Last name", "First name", "Title",
							"Remaining Days", "Rental fee" }) {
				private static final long serialVersionUID = 2849097606635036753L;
				private Class<?>[] types = new Class[] { java.lang.Long.class, java.lang.Integer.class,
						java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
						java.lang.Integer.class, java.lang.Double.class };
				private boolean[] canEdit = new boolean[] { false, false, false, false, false, false, false, false };

				public Class<?> getColumnClass(int columnIndex) {
					return types[columnIndex];
				}

				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return canEdit[columnIndex];
				}
			});

			deleteRentalsButton.setEnabled(false);

			break;

		default:
			JOptionPane.showMessageDialog(null, "Please select a tab.");
			break;
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void rentalDaysTextFieldKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER && getUserButton.isEnabled()) {
			saveRentalButtonActionPerformed(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
			saveRentalButton.requestFocus();
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetRentalActionForm(null);
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void lastNameTextFieldKeyPressed(KeyEvent evt) {
		if (evt.getKeyCode() == KeyEvent.VK_ENTER && getUserButton.isEnabled()) {
			rentalDaysTextField.requestFocus();
			getUserButtonActionPerformed(null);
		} else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
			resetRentalActionForm(null);
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void newUserCheckBoxActionPerformed(ActionEvent evt) {
		// if new user is checked
		if (newUserCheckBox.isSelected()) {
			getUserButton.setEnabled(false);
			saveRentalButton.setEnabled(true);

			lastNameTextField.setEditable(true);
			firstNameTextField.setEditable(true);
			firstNameTextField.setText("");
			userIdForTextField.setEditable(false);
			userIdForTextField.setText("");

			if (lastNameTextField.getText().length() > 0) {
				firstNameTextField.requestFocus();
			} else {
				lastNameTextField.requestFocus();
			}
		} else {
			// new user is unchecked
			getUserButton.setEnabled(true);
			saveRentalButton.setEnabled(false);

			lastNameTextField.setEditable(true);
			lastNameTextField.setText("");
			firstNameTextField.setEditable(false);
			firstNameTextField.setText("");
			userIdForTextField.setEditable(true);
			userIdForTextField.setText("");
			userIdForTextField.requestFocus();
		}
	}

	private void saveRentalButtonActionPerformed(ActionEvent evt) {
		// get data from input fields
		String lastName = lastNameTextField.getText().trim();
		String firstName = firstNameTextField.getText().trim();
		String rentalDays = rentalDaysTextField.getText();

		// check selection in movie table
		if (movieTable.getSelectedRow() < 0) {
			JOptionPane.showMessageDialog(null, "select a movie to rent");
			return;
		}
		// check values in text fields
		if (!lastName.isEmpty() || !firstName.isEmpty()) {
			Long userId;
			if (newUserCheckBox.isSelected()) {
				userId = services.createUser(lastName, firstName);
			} else {
				userId = Long.parseLong(userIdForTextField.getText());
			}

			// save rental in list
			Long movieId = (Long) movieTable.getValueAt(movieTable.getSelectedRow(), 0);

			services.createRental(movieId, userId, Integer.valueOf(rentalDays));

			mainPaneStateChanged(null);

			// reset application
			movieTable.requestFocus();

			resetRentalActionForm(null);
		} else {
			JOptionPane.showMessageDialog(null, "first and last name must not be empty.");
			newUserCheckBoxActionPerformed(null);
		}
	}

	/**
	 * @param evt
	 *            none.
	 */
	private void resetRentalActionForm(ActionEvent evt) {
		lastNameTextField.setEditable(false);
		lastNameTextField.setText("");
		firstNameTextField.setEditable(false);
		firstNameTextField.setText("");
		rentalDateTextField.setEditable(false);
		rentalDateTextField.setText(currDate);
		rentalDaysTextField.setEditable(false);
		rentalDaysTextField.setText("1");
		userIdForTextField.setEditable(false);
		userIdForTextField.setText("");

		newUserCheckBox.setSelected(false);
		newUserCheckBox.setEnabled(false);

		getUserButton.setEnabled(false);
		saveRentalButton.setEnabled(false);
		clearAllButton.setEnabled(false);

		movieTable.requestFocus();

		mainPaneStateChanged(null);
	}

	private boolean found = false;
	private boolean nameDoNotMatchId = false;
	private int userCount = 0;

	private void getUserButtonActionPerformed(ActionEvent evt) {
		// get last name and/or user id as search parameter
		final String name = lastNameTextField.getText().toLowerCase();
		String userId = userIdForTextField.getText();
		nameDoNotMatchId = false;
		found = false;
		userCount = 0;

		// name is not empty
		if (!name.isEmpty() && userId.isEmpty()) {
			// search for user in list
			services.visitUsers(new UserVisitor() {
				@Override
				public void visit(Long id, String lastName, String firstName) {
					if (lastName.toLowerCase().equals(name)) {
						lastNameTextField.setText(lastName);
						firstNameTextField.setText(firstName);
						userIdForTextField.setText(id.toString());

						lastNameTextField.setEditable(false);
						firstNameTextField.setEditable(false);
						userIdForTextField.setEditable(false);
						newUserCheckBox.setEnabled(false);

						found = true;
						userCount++;
					}
				}
			});
		} else if (!userId.isEmpty() && name.isEmpty() && Pattern.matches("\\d*", userId)) {
			final Long uId = Long.valueOf(userId).longValue();
			// search for user in list
			services.visitUsers(new UserVisitor() {
				@Override
				public void visit(Long id, String lastName, String firstName) {
					if (!found && id.equals(uId)) {
						lastNameTextField.setText(lastName);
						firstNameTextField.setText(firstName);
						userIdForTextField.setText(id.toString());

						lastNameTextField.setEditable(false);
						firstNameTextField.setEditable(false);
						userIdForTextField.setEditable(false);
						newUserCheckBox.setEnabled(false);

						found = true;
					}
				}
			});
		} else if (!name.isEmpty() && !userId.isEmpty() && Pattern.matches("\\d*", userId)) {
			final Long uId = Long.valueOf(userId).longValue();
			services.visitUsers(new UserVisitor() {
				@Override
				public void visit(Long id, String lastName, String firstName) {
					if (!found && id.equals(uId) && lastName.toLowerCase().equals(name)) {
						lastNameTextField.setText(lastName);
						firstNameTextField.setText(firstName);
						userIdForTextField.setText(id.toString());

						lastNameTextField.setEditable(false);
						firstNameTextField.setEditable(false);
						userIdForTextField.setEditable(false);
						newUserCheckBox.setEnabled(false);

						found = true;
						nameDoNotMatchId = false;
					} else {
						nameDoNotMatchId = true;
						found = false;
					}
				}
			});
		} else {
			found = true;
			JOptionPane.showMessageDialog(null, "Please enter a new user or a valid " + "'User Id' or 'Last name'.");
			userIdForTextField.requestFocus();
		}

		// check found value
		if (!found && !nameDoNotMatchId) {
			JOptionPane.showMessageDialog(null, "User not found in database.");
			lastNameTextField.requestFocus();
		}
		// name do not match user id
		if (nameDoNotMatchId) {
			JOptionPane.showMessageDialog(null, "User name do not match user id.");
			userIdForTextField.requestFocus();
		}
		// check if multiple users were found
		if (userCount > 1) {
			JOptionPane.showMessageDialog(null, "Multiple users found. Please," + " additionally enter the user id.");
			userIdForTextField.setEditable(true);
			firstNameTextField.setText("");
			userIdForTextField.requestFocus();
		}

		// activate save button
		if (!lastNameTextField.isEditable() && !firstNameTextField.isEditable() && !userIdForTextField.isEditable()) {
			saveRentalButton.setEnabled(true);
		}
	}

	private void exitMenuItemActionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals(exitMenuItem.getText())) {
			System.exit(JFrame.EXIT_ON_CLOSE);
		}
	}

	private void aboutMenuItemActionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals(aboutMenuItem.getText())) {
			aboutDialog.setVisible(true);
			this.setEnabled(false);
		}
	}

	private JButton refreshButton1;
	private JButton refreshButton2;
	private JButton refreshButton3;
	private JButton refreshButton4;

	private JDialog aboutDialog;
	private JMenuItem aboutMenuItem;
	private JSeparator aboutSeparator;
	private JButton cancelMoviesButton;
	private JButton cancelUsersButton;
	private JButton clearAllButton;
	private JButton closeGetRentalsButton;
	private JButton deleteGetRentalsButton;
	private JButton deleteMoviesButton;
	private JButton deleteRentalsButton;
	private JButton deleteUsersButton;
	private JButton editMoviesButton;
	private JButton editUsersButton;
	private JMenuItem exitMenuItem;
	private JTextField firstNameTextField;
	private JTextField firstNameUsersTextField;
	private JButton getRentalsButton;
	private JDialog getRentalsDialog;
	private JScrollPane getRentalsScrollPane;
	private JTable getRentalsTable;
	private JButton getUserButton;
	private JTabbedPane mainPane;
	private JPanel mainPanel;
	private JMenuBar menuBar;
	private JPanel movieCRUDPanel;
	private JTable movieCRUDTable;
	private JScrollPane movieScrollPane;
	private JTable movieTable;
	private JTextField movieTitleMoviesTextField;
	private JButton newMoviesButton;
	private JCheckBox newUserCheckBox;
	private JButton newUsersButton;
	private JComboBox<String> priceCatMoviesComboBox;
	private JTextField releaseDateMoviesTextField;
	private JTextField rentalDateTextField;
	private JTextField rentalDaysTextField;
	private JPanel rentalsCRUDPanel;
	private JTable rentalsCRUDTable;
	private JButton saveMoviesButton;
	private JButton saveRentalButton;
	private JButton saveUsersButton;
	private JTextField lastNameTextField;
	private JTextField lastNameUsersTextField;
	private JLabel userGetRentalsTextLabel;
	private JFormattedTextField userIdForTextField;
	private JPanel usersCRUDPanel;
	private JTable usersCRUDTable;

	public BusinessLogic services;
	private MovieRentalMappers mappers;

	private boolean editMode = false;

	private String currDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
}
