package ch.fhnw.edu.rental.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.DefaultComboBoxModel;

import ch.fhnw.edu.rental.data.DataSet;
import ch.fhnw.edu.rental.gui.about.AboutDialog;
import ch.fhnw.edu.rental.model.ChildrenPriceCategory;
import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.NewReleasePriceCategory;
import ch.fhnw.edu.rental.model.PriceCategory;
import ch.fhnw.edu.rental.model.RegularPriceCategory;
import ch.fhnw.edu.rental.model.Rental;
import ch.fhnw.edu.rental.model.User;

/**
 * The main GUI class of the application.
 */
public class MovieRentalView extends JFrame {

  /**
   * 
   */
  private static final long serialVersionUID = -825931747061397600L;

  /**
   * Error message when attempting to set look and feel.
   */
  public static final String LOOK_AND_FEEL_ERR_MSG = "Error attempting to set look and feel of the system";

  /**
   * Error message when attempting to initialize DB.
   */
  public static final String DATA_DASE_ERR_MSG = "Error attempting to initialize database";

  /**
   * the panel to manage users.
   */
  private UserPanel userPanel;

  /**
   * the panel to view rentals.
   */
  private RentalsPanel rentalsPanel;

  /**
   * Provide access to date formatter.
   * @return date formatter
   */
  public static DateFormat getDateFormatter() {
    return SDF;
  }

  /**
   * Creates new form MovieRentalApplication.
   */
  public MovieRentalView() {
    // set system look and feel of the application
    setLookAndFeel();

    // initialize rental database
    initializeDatabase();

    // initialize date settings
    TimeZone localTz = TimeZone.getDefault();
    SDF.setTimeZone(localTz);
    currDate = SDF.format(new Date(Calendar.getInstance().getTimeInMillis()));

    // initialize application components
    initComponents();
  }

  /**
   * Set the apps look and feel from the systems.
   */
  private void setLookAndFeel() {
    try {
      String ui = UIManager.getSystemLookAndFeelClassName();
      UIManager.setLookAndFeel(ui);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, LOOK_AND_FEEL_ERR_MSG + ":\n" + e.getLocalizedMessage());
    } // end of try/catch
  }

  /**
   * initialize database and object lists.
   */
  private void initializeDatabase() {
    try {
      dataSet = new DataSet(getDateFormatter());
      // fill lists
      userList = dataSet.getUserList();
      userListId = Integer.valueOf(userList.size());

      movieList = dataSet.getMovieList();
      movieListId = Integer.valueOf(movieList.size());

      rentalList = dataSet.getRentalList();
      rentalListId = Integer.valueOf(rentalList.size());
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, DATA_DASE_ERR_MSG + ":\n" + e.getLocalizedMessage()
        + "\nApplication is closed.");
      System.exit(JFrame.EXIT_ON_CLOSE);
    }
  }

  /**
   * none.
   */
  private void initComponents() {

    mainPane = new JTabbedPane();
    mainPanel = new JPanel();

    userIdForTextField = new JFormattedTextField();
    movieScrollPane = new JScrollPane();
    movieTable = new JTable();
    movieCRUDPanel = new JPanel();
    movieCRUDTable = new JTable();

    menuBar = new JMenuBar();
    resetMenuItem = new JMenuItem();
    exitMenuItem = new JMenuItem();
    aboutMenuItem = new JMenuItem();

    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setTitle("Software Construction Lab");
    setName("mainFrame");

    mainPane.setName("mainPane");
    mainPane.addChangeListener(new ChangeListener() {
      public void stateChanged(ChangeEvent evt) {
        mainPaneStateChanged(evt);
      }
    });

    // initialize the rent a movie panel
    addRentMoviePanel();

    addMoviePanel();

    // add a user panel
    addUserPanel();

    addRentalsPanel();

    initMenu();

    GroupLayout layout = new GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(
      mainPane, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE));
    layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(
      mainPane, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE));

    pack();
  } 

  /**
   * add the rental panel.
   */

  /**
   * add rent a movie panel.
   */
  private void addRentMoviePanel() {

    rentalDaysTextField = new JTextField();
    rentalDateTextField = new JTextField();
    firstNameTextField = new JTextField();
    surNameTextField = new JTextField();
    getUserButton = new JButton();
    saveRentalButton = new JButton();
    newUserCheckBox = new JCheckBox();
    clearAllButton = new JButton();


    initRentMoviePanel();
    setRentMoviePanelLayout();
    mainPane.insertTab("Rent Movie", null, mainPanel, "Rent Movie", RENT_MOVIE_TAB_INDEX);
  }

  /**
   * Add the movie panel.
   */
  private void addMoviePanel() {
    // initialize the movie panel
    JScrollPane movieCRUDScrollPane = new JScrollPane();
    initMoviePanel(movieCRUDScrollPane);
    setMoviePanleLayout(movieCRUDScrollPane);
    mainPane.insertTab("Movies", new ImageIcon(getClass().getResource(
        "/img/movieLogo.png")), movieCRUDPanel, "Movies", MOVIES_TAB_INDEX); // NOI18N
  }

  /**
   * Initialize the app's menu.
   */
  private void initMenu() {

    JMenu fileMenu = new JMenu();
    JSeparator menuSeparator = new JSeparator();
    JMenu helpMenu = new JMenu();

    menuBar.setName("menuBar");

    fileMenu.setText("File");
    fileMenu.setName("fileMenu");

    resetMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
      java.awt.event.InputEvent.CTRL_MASK));
    resetMenuItem.setText("Reset Application");
    resetMenuItem.setEnabled(false);
    resetMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        resetMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(resetMenuItem);
    fileMenu.add(menuSeparator);

    exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
      java.awt.event.InputEvent.CTRL_MASK));
    exitMenuItem.setText("Exit");
    exitMenuItem.setName("exitMenuItem");
    exitMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        exitMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    helpMenu.setText("Help");
    helpMenu.setName("helpMenu");

    aboutMenuItem.setText("About ...");
    aboutMenuItem.setName("aboutMenuItem");
    aboutMenuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        aboutMenuItemActionPerformed(evt);
      }
    });
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    setJMenuBar(menuBar);
  }

  /**
   * Initialize movie panel.
   * @param movieCRUDScrollPane the scroll pane
   */
  private void setMoviePanleLayout(JScrollPane movieCRUDScrollPane) {
    JLabel movieTitleMoviesLabel = new JLabel();
    JLabel releaseDateMoviesLabel = new JLabel();
    JLabel priceCatMoviesLabel = new JLabel();

    movieTitleMoviesLabel.setText("Movie Title:");
    releaseDateMoviesLabel.setText("Release Date:");
    priceCatMoviesLabel.setText("Price category:");

    GroupLayout movieCRUDPanelLayout = new GroupLayout(movieCRUDPanel);
    movieCRUDPanel.setLayout(movieCRUDPanelLayout);
    movieCRUDPanelLayout.setHorizontalGroup(movieCRUDPanelLayout.createParallelGroup(
      GroupLayout.Alignment.LEADING).addGroup(
        GroupLayout.Alignment.TRAILING,
        movieCRUDPanelLayout.createSequentialGroup().addContainerGap(149, Short.MAX_VALUE).addComponent(
          cancelMoviesButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(
            newMoviesButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(
              editMoviesButton).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(
                deleteMoviesButton).addGap(18, 18, 18).addComponent(saveMoviesButton).addContainerGap()).addGroup(
                  GroupLayout.Alignment.TRAILING,
                  movieCRUDPanelLayout.createSequentialGroup().addContainerGap().addGroup(
                    movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(
                      priceCatMoviesLabel).addComponent(releaseDateMoviesLabel).addComponent(movieTitleMoviesLabel))
                      .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
                        movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(
                          movieTitleMoviesTextField, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE).addGroup(
                            movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(releaseDateMoviesTextField, GroupLayout.Alignment.LEADING)
                            .addComponent(priceCatMoviesComboBox, GroupLayout.Alignment.LEADING,
                              GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                              GroupLayout.PREFERRED_SIZE))).addContainerGap()).addComponent(
                                movieCRUDScrollPane, GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE));
    movieCRUDPanelLayout.setVerticalGroup(movieCRUDPanelLayout.createParallelGroup(
      GroupLayout.Alignment.LEADING).addGroup(
        GroupLayout.Alignment.TRAILING,
        movieCRUDPanelLayout.createSequentialGroup().addComponent(movieCRUDScrollPane,
          GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE).addGap(18, 18, 18).addGroup(
            movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
              movieTitleMoviesLabel).addComponent(movieTitleMoviesTextField, GroupLayout.PREFERRED_SIZE,
                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                  LayoutStyle.ComponentPlacement.RELATED).addGroup(
                    movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
                      releaseDateMoviesLabel).addComponent(releaseDateMoviesTextField,
                        GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                        GroupLayout.PREFERRED_SIZE)).addPreferredGap(
                          LayoutStyle.ComponentPlacement.RELATED).addGroup(
                            movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
                              priceCatMoviesLabel).addComponent(priceCatMoviesComboBox, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18)
                                .addGroup(
                                  movieCRUDPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
                                    saveMoviesButton).addComponent(deleteMoviesButton).addComponent(editMoviesButton)
                                      .addComponent(newMoviesButton)
                                      .addComponent(cancelMoviesButton)).addContainerGap()));
  }

  /**
   * initialize movie panel.
   * @param movieCRUDScrollPane the scroll pane.
   */
  private void initMoviePanel(JScrollPane movieCRUDScrollPane) {

    saveMoviesButton = new JButton();
    newMoviesButton = new JButton();
    deleteMoviesButton = new JButton();
    editMoviesButton = new JButton();
    priceCatMoviesComboBox = new JComboBox<String>();
    releaseDateMoviesTextField = new JTextField();
    movieTitleMoviesTextField = new JTextField();
    cancelMoviesButton = new JButton();

    movieCRUDPanel.setName("moviesTab");

    movieCRUDTable
    .setModel(new DefaultTableModel(new Object[][] {{}, {}, {}, {} }, new String[] {}));
    movieCRUDTable.addMouseListener(new java.awt.event.MouseAdapter() {
      public void mouseClicked(java.awt.event.MouseEvent evt) {
        movieCRUDTableMouseClicked(evt);
      }
    });
    movieCRUDTable.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        movieCRUDTableKeyPressed(evt);
      }
    });
    movieCRUDScrollPane.setViewportView(movieCRUDTable);

    initSaveMovieButton();

    initNewMovieButton();

    initDeleteMovieButton();

    initEditMovieButton();

    initPriceCategoryField();

    initReleaseDateField();

    initMivieTitleField();

    initCancelButton();
  }

  /**
   *  initCancelButton.
   */
  private void initCancelButton() {
    cancelMoviesButton.setText("Cancel");
    cancelMoviesButton.setEnabled(false);
    cancelMoviesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        cancelMoviesButtonActionPerformed(evt);
      }
    });
  }

  /**
   *  init movie title. 
   */
  private void initMivieTitleField() {
    movieTitleMoviesTextField.setToolTipText("Please enter or re-enter the title of the movie.");
    movieTitleMoviesTextField.setEnabled(false);
    movieTitleMoviesTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        priceCatMoviesComboBoxKeyPressed(evt);
      }
    });
  }

  /**
   * init release date field.
   */
  private void initReleaseDateField() {
    releaseDateMoviesTextField.setText(currDate);
    releaseDateMoviesTextField.setToolTipText("Please enter the release date of the movie.");
    releaseDateMoviesTextField.setEnabled(false);
    releaseDateMoviesTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        priceCatMoviesComboBoxKeyPressed(evt);
      }
    });
  }

  /**
   * init PriceCategory field.
   */
  private void initPriceCategoryField() {
    priceCatMoviesComboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Children", "New Release",
    "Regular" }));
    priceCatMoviesComboBox.setToolTipText("Please select a price category.");
    priceCatMoviesComboBox.setEnabled(false);
    priceCatMoviesComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        priceCatMoviesComboBoxKeyPressed(evt);
      }
    });
  }

  /**
   * init edit movie button.
   */
  private void initEditMovieButton() {
    editMoviesButton.setText("Edit");
    editMoviesButton.setEnabled(false);
    editMoviesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        editMoviesButtonActionPerformed(evt);
      }
    });
  }

  /**
   * init delete movie button.
   */
  private void initDeleteMovieButton() {
    deleteMoviesButton.setText("Delete");
    deleteMoviesButton.setEnabled(false);
    deleteMoviesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        deleteMoviesButtonActionPerformed(evt);
      }
    });
  }

  /**
   * init new movie button.
   */
  private void initNewMovieButton() {
    newMoviesButton.setText("New ...");
    newMoviesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        newMoviesButtonActionPerformed(evt);
      }
    });
  }

  /**
   * init save movie button.
   */
  private void initSaveMovieButton() {
    saveMoviesButton.setText("Save");
    saveMoviesButton.setEnabled(false);
    saveMoviesButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveMoviesButtonActionPerformed(evt);
      }
    });
  }

  /**
   * Initialize rental movie layout pane.
   */
  private void setRentMoviePanelLayout() {
    JLabel surNameLabel = new JLabel();
    JLabel firstNameLabel = new JLabel();
    JLabel rentalDateLabel = new JLabel();
    JLabel rentalDaysLabel = new JLabel();
    JLabel userIdLabel = new JLabel();

    userIdLabel.setText("User ID:");
    surNameLabel.setText("Surname:");
    surNameLabel.setToolTipText("Please enter surname of the customer.");
    firstNameLabel.setText("First Name:");
    firstNameLabel.setToolTipText("Please enter first name of the customer.");
    rentalDateLabel.setText("Rental Date:");
    rentalDaysLabel.setText("Rental Days:");

    GroupLayout mainPanelLayout = new GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(
        GroupLayout.Alignment.TRAILING,
        mainPanelLayout.createSequentialGroup().addGap(175, 175, 175).addComponent(clearAllButton).addPreferredGap(
        LayoutStyle.ComponentPlacement.RELATED).addGap(18, 18, 18).addComponent(saveRentalButton)
        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addContainerGap()).addGroup(
        mainPanelLayout.createSequentialGroup().addContainerGap().addGroup(
        mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(
        rentalDaysLabel).addComponent(rentalDateLabel).addComponent(firstNameLabel).addComponent(
        surNameLabel)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(
        mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false).addGroup(
        mainPanelLayout.createSequentialGroup().addComponent(surNameTextField,
        GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE).addGap(18, 18, 18)
        .addComponent(userIdLabel).addPreferredGap(
        LayoutStyle.ComponentPlacement.RELATED).addComponent(userIdForTextField,
        GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        .addComponent(firstNameTextField).addComponent(rentalDateTextField).addComponent(
        rentalDaysTextField)).addGap(68, 68, 68).addComponent(newUserCheckBox).addPreferredGap(
        LayoutStyle.ComponentPlacement.RELATED).addComponent(getUserButton).addContainerGap(
        GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addComponent(movieScrollPane,
        GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE));
    
    mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
      .addGroup(
        GroupLayout.Alignment.TRAILING,
        mainPanelLayout.createSequentialGroup().addComponent(movieScrollPane, GroupLayout.DEFAULT_SIZE,
        314, Short.MAX_VALUE).addGap(18, 18, 18).addGroup(
        mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
        surNameLabel).addComponent(surNameTextField, GroupLayout.PREFERRED_SIZE,
        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(
        userIdLabel).addComponent(userIdForTextField, GroupLayout.PREFERRED_SIZE,
        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(
        getUserButton).addComponent(newUserCheckBox)).addPreferredGap(
        LayoutStyle.ComponentPlacement.RELATED).addGroup(
        mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
        firstNameLabel).addComponent(firstNameTextField, GroupLayout.PREFERRED_SIZE,
        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(
        LayoutStyle.ComponentPlacement.RELATED).addGroup(
        mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
        rentalDateLabel).addComponent(rentalDateTextField, GroupLayout.PREFERRED_SIZE,
        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addPreferredGap(
        LayoutStyle.ComponentPlacement.RELATED).addGroup(
        mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
        rentalDaysLabel).addComponent(rentalDaysTextField, GroupLayout.PREFERRED_SIZE,
        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(18, 18, 18).addGroup(
        mainPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(
        saveRentalButton).addComponent(clearAllButton)).addContainerGap()));
  }

  /**
   * adds the user panel.
   */
  private void addUserPanel() {
    userPanel = new UserPanel(this.userList, this.movieList, this.dataSet);

    mainPane.insertTab("Users", new ImageIcon(getClass().getResource(
        "/img/userLogo.png")), userPanel, "Users2", USERS_TAB_INDEX); // NOI18N

  }

  /**
   * adds the rentals panel.
   */
  private void addRentalsPanel() {
    rentalsPanel = new RentalsPanel(this.movieList, this.rentalList, this.dataSet);

    mainPane.insertTab("Rentals", new ImageIcon(getClass().getResource(
        "/img/rentalLogo.png")), rentalsPanel, "Rental2", RENTALS_TAB_INDEX); // NOI18N

  }

  // GEN-END:initComponents

  /**
   * initialize rent a movie panel.
   */
  private void initRentMoviePanel() {
    mainPanel.setName("Rent Movie");

    // initialize rental days
    initializeRentalDaysTextField();

    // initialize rental date
    initializeRentalDateTextField();

    initializeFirstNameTextField();

    // initialize surname field
    initializeSurnameTextField();

    // initialize get user field
    getUserButton.setText("get User");
    getUserButton.setName("getUserButton");
    getUserButton.setToolTipText("Gets the entered user from the database.");
    getUserButton.setEnabled(false);
    getUserButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        getUserButtonActionPerformed(evt);
      }
    });

    userIdForTextField.setEditable(false);
    userIdForTextField.setName("userIdForTextField");
    userIdForTextField.setToolTipText("Enter user id to find the user.");
    userIdForTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        userIdForTextFieldKeyPressed(evt);
      }
    });

    saveRentalButton.setText("Save");
    saveRentalButton.setName("saveRentalButton");
    saveRentalButton.setEnabled(false);
    saveRentalButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        saveRentalButtonActionPerformed(evt);
      }
    });

    newUserCheckBox.setText("new User");
    newUserCheckBox.setToolTipText("Check if the user is not stored or find in the database.");
    newUserCheckBox.setEnabled(false);
    newUserCheckBox.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        newUserCheckBoxActionPerformed(evt);
      }
    });

    clearAllButton.setText("Clear All");
    clearAllButton.setEnabled(false);
    clearAllButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        resetRentalActionForm(evt);
      }
    });

    movieTable.setName("movieTable");
    movieTable.setModel(new DefaultTableModel(dataSet.getMovieListAsObject(false, true),
      new String[] {"Movie ID", "Title", "Release Date", "Is Rented?", "Price Category" }) {
      private static final long serialVersionUID = -5376743898459692217L;
      private Class<?>[] types = new Class[] {Long.class, String.class, Object.class, Boolean.class, Object.class };
      public Class<?> getColumnClass(int columnIndex) {
        return types[columnIndex];
      }
    });
    movieScrollPane.setViewportView(movieTable);
  }

  /**
   * initialize surname text field.
   */
  private void initializeSurnameTextField() {
    surNameTextField.setEditable(true);
    surNameTextField.setName("surNameTextField");
    surNameTextField.setToolTipText("Please enter surname of the customer.");
    surNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        surNameTextFieldKeyPressed(evt);
      }
    });
  }

  /**
   * initialize first name text field.
   */
  private void initializeFirstNameTextField() {
    firstNameTextField.setEditable(true);
    firstNameTextField.setName("firstNameTextField");
    firstNameTextField.setToolTipText("Please enter first name of the customer.");
    firstNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        firstNameTextFieldKeyPressed(evt);
      }
    });
  }

  /**
   * initialize RentalDate text field.
   */
  private void initializeRentalDateTextField() {
    rentalDateTextField.setEditable(false);
    rentalDateTextField.setText(currDate);
    rentalDateTextField.setName("rentalDateTextField");
    rentalDateTextField.setToolTipText("Rental date is by default todays date.");
  }


  /**
   * initialize Rental Days text field.
   */
  private void initializeRentalDaysTextField() {
    rentalDaysTextField.setEditable(true);
    rentalDaysTextField.setName("rentalDaysTextField");
    rentalDaysTextField.setText("1");
    rentalDaysTextField.setToolTipText("Please enter how long the movie will be rented.");
    rentalDaysTextField.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyPressed(java.awt.event.KeyEvent evt) {
        rentalDaysTextFieldKeyPressed(evt);
      }
    });
  }

  /**
   * @param evt none.
   */
  private void userIdForTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
      resetRentalActionForm(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
      getUserButtonActionPerformed(null);
      rentalDaysTextField.requestFocus();
    }
  }

  /**
   * @param evt none.
   */
  private void firstNameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
      resetRentalActionForm(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
      rentalDaysTextField.requestFocus();
    }
  }

  /**
   * @param evt none.
   */
  private void priceCatMoviesComboBoxKeyPressed(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
      saveMoviesButtonActionPerformed(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
      saveMoviesButton.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
      cancelMoviesButtonActionPerformed(null);
    }
  }

  /**
   * @param evt none.
   */
  private void movieCRUDTableKeyPressed(java.awt.event.KeyEvent evt) {
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
        handleButtonsState();
        break;
      default:
        // do nothing
        break;
      }
    }
  }

  /**
   * sets the buttons state in the MoviePanel.
   */
  private void handleButtonsState() {
    if (deleteMoviesButton.isEnabled()) {
      deleteMoviesButton.requestFocus();
    } else {
      editMoviesButton.requestFocus();
    }
  }

  /**
   * @param evt none.
   */
  private void resetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
    try {
      dataSet = new DataSet(getDateFormatter());
      // fill lists
      userList = dataSet.getUserList();
      userListId = Integer.valueOf(userList.size());

      movieList = dataSet.getMovieList();
      movieListId = Integer.valueOf(movieList.size());

      rentalList = dataSet.getRentalList();
      rentalListId = Integer.valueOf(rentalList.size());

      resetMenuItem.setEnabled(false);
      mainPaneStateChanged(null);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Application couldn't be reset:\n" + e.getLocalizedMessage()
        + "\nApplication is closed.");
      System.exit(JFrame.EXIT_ON_CLOSE);
    }
  }

  /**
   * @param evt none.
   */
  private void cancelMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {
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

  /**
   * @param evt none.
   */
  private void editMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {
    int rowCount = movieCRUDTable.getSelectedRow();

    // fill text fields with information
    movieTitleMoviesTextField.setText(movieList.get(rowCount).getTitle());
    releaseDateMoviesTextField.setText(SDF.format(movieList.get(rowCount).getReleaseDate()));
    priceCatMoviesComboBox.setSelectedItem(movieList.get(rowCount).getPriceCategory().toString());

    movieTitleMoviesTextField.setEnabled(true);
    releaseDateMoviesTextField.setEnabled(true);
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

  /**
   * @param evt none.
   */
  private void deleteMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {
    if (movieCRUDTable.getRowCount() > 0 || movieCRUDTable.getSelectedRow() < 0) {
      return;
    }

    int rowCount = movieCRUDTable.getSelectedRow();
    boolean found = false;

    // if user is deleted, notify that there are still rentals
    for (int i = 0; i < rentalList.size(); i++) {
      if (rentalList.get(i).getMovie().getTitle().equals(movieList.get(rowCount).getTitle())) {
        found = true;
        break;
      }
    }

    if (!found) {
      movieList.remove(rowCount);
    } else {
      JOptionPane.showMessageDialog(null, "The movie \"" + movieList.get(rowCount).getTitle() + "\" is rented.\n"
          + "Movie can not be deleted.");
    }

    deleteMoviesButton.setEnabled(false);
    editMoviesButton.setEnabled(false);
    newMoviesButton.setEnabled(true);
    cancelMoviesButton.setEnabled(false);

    movieCRUDTable.requestFocus();

    resetMenuItem.setEnabled(true);
    mainPaneStateChanged(null);
  }

  /**
   * @param evt none.
   */
  private void movieCRUDTableMouseClicked(java.awt.event.MouseEvent evt) {
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

  /**
   * @param evt none.
   */
  private void saveMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // get all input fields
    String movieTitle = movieTitleMoviesTextField.getText();
    String releaseDate = releaseDateMoviesTextField.getText();
    Date date = new Date(Calendar.getInstance().getTimeInMillis());
    try {
      date = new Date(SDF.parse(releaseDate).getTime());
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, "Date format can not be parsed and is replaced" + " by the current date:\n"
          + e.getLocalizedMessage());
    }
    Object priceCatO = priceCatMoviesComboBox.getSelectedItem();
    PriceCategory priceCat;

    // convert combo box selection to price category
    if (priceCatO.equals(priceCatMoviesComboBox.getItemAt(1))) {
      priceCat = NewReleasePriceCategory.getInstance();
    } else if (priceCatO.equals(priceCatMoviesComboBox.getItemAt(2))) {
      priceCat = RegularPriceCategory.getInstance();
    } else {
      priceCat = ChildrenPriceCategory.getInstance();
    }

    if (!movieTitle.isEmpty()) {
      Movie currMovie = new Movie(movieTitle, date, priceCat);
      if (editMode) {
        int rowCount = movieCRUDTable.getSelectedRow();
        currMovie.setId(movieList.get(rowCount).getId());
        movieList.set(rowCount, currMovie);
        editMode = false;
      } else {
        currMovie.setId(movieListId + 1);
        movieList.add(currMovie);
        movieListId++;
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

      resetMenuItem.setEnabled(true);
      mainPaneStateChanged(null);
    } else {
      JOptionPane.showMessageDialog(null, "Movie title may not be empty.");
      movieTitleMoviesTextField.requestFocus();
      newMoviesButtonActionPerformed(null);
    }
  }

  /**
   * @param evt none.
   */
  private void newMoviesButtonActionPerformed(java.awt.event.ActionEvent evt) {
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
   * @param evt none.
   */
  private void mainPaneStateChanged(ChangeEvent evt) {
    // refresh data in tables when it is selected
    switch (mainPane.getSelectedIndex()) {
    case RENT_MOVIE_TAB_INDEX:
      switchToRentMovie();
      break;
    case MOVIES_TAB_INDEX:
      switchToMovies();
      break;

    case RENTALS_TAB_INDEX:
      switchToRentals();
      break;

    case USERS_TAB_INDEX:
      switchToUsers();
      break;

    default:
      JOptionPane.showMessageDialog(null, "Please select a tab.");
      break;
    }
  }

  /**
   * switch to user panel.
   */
  private void switchToUsers() {

    userPanel.updateView();
  }

  /**
   * switch to user panel.
   */
  private void switchToRentals() {

    rentalsPanel.updateView();
  }

  /**
   * Initialization of Movies tab when user switched to it.
   */
  private void switchToMovies() {
    //    dataSet.setMovieList(movieList);
    // set new model with new data
    movieCRUDTable.setModel(new DefaultTableModel(dataSet.getMovieListAsObject(), new String[] {"Movie ID", "Title",
      "Release Date", "Is Rented?", "Price Category" }) {
      private static final long serialVersionUID = -8807187529192958223L;
      private Class<?>[] types = new Class[] {java.lang.Integer.class, java.lang.String.class, java.lang.Object.class,
          java.lang.Boolean.class, java.lang.Object.class };
      private boolean[] canEdit = new boolean[] {false, false, false, false, false };

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

  }

  /**
   * Initialization of Rent Movie tab when user switched to it.
   */
  private void switchToRentMovie() {
    //      dataSet.setMovieList(movieList);
    // set new model with new data
    movieTable.setModel(new DefaultTableModel(dataSet.getMovieListAsObject(false, true), new String[] {"Movie ID",
      "Title", "Release Date", "Is Rented?", "Price Category" }) {
      private static final long serialVersionUID = 2456659513544091063L;
      private Class<?>[] types = new Class[] {java.lang.Long.class, java.lang.String.class, java.lang.Object.class,
          java.lang.Boolean.class, java.lang.Object.class };
      private boolean[] canEdit = new boolean[] {false, false, false, false, false };

      public Class<?> getColumnClass(int columnIndex) {
        return types[columnIndex];
      }

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
      }
    });

    surNameTextField.setEditable(true);
    surNameTextField.setText("");
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
  }

  /**
   * @param evt none.
   */
  private void rentalDaysTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == KeyEvent.VK_ENTER && getUserButton.isEnabled()) {
      saveRentalButtonActionPerformed(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
      saveRentalButton.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
      resetRentalActionForm(null);
    }
  }

  /**
   * @param evt none.
   */
  private void surNameTextFieldKeyPressed(java.awt.event.KeyEvent evt) {
    if (evt.getKeyCode() == KeyEvent.VK_ENTER && getUserButton.isEnabled()) {
      rentalDaysTextField.requestFocus();
      getUserButtonActionPerformed(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
      resetRentalActionForm(null);
    }
  }

  /**
   * @param evt none.
   */
  private void newUserCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
    // if new user is checked
    if (newUserCheckBox.isSelected()) {
      getUserButton.setEnabled(false);
      saveRentalButton.setEnabled(true);

      surNameTextField.setEditable(true);
      firstNameTextField.setEditable(true);
      firstNameTextField.setText("");
      userIdForTextField.setEditable(false);
      userIdForTextField.setText((userListId + 1) + "");

      if (surNameTextField.getText().length() > 0) {
        firstNameTextField.requestFocus();
      } else {
        surNameTextField.requestFocus();
      }
    } else {
      // new user is unchecked
      getUserButton.setEnabled(true);
      saveRentalButton.setEnabled(false);

      surNameTextField.setEditable(true);
      surNameTextField.setText("");
      firstNameTextField.setEditable(false);
      firstNameTextField.setText("");
      userIdForTextField.setEditable(true);
      userIdForTextField.setText("");
      userIdForTextField.requestFocus();
    }
  }

  // /**
  // * @param evt none.
  // */
  // private void rentMovieButtonActionPerformed(
  // java.awt.event.ActionEvent evt) {
  // if (!movieList.get(movieTable.getSelectedRow()).isRented()) {
  // surNameTextField.setEditable(true);
  // surNameTextField.setText("");
  // firstNameTextField.setEditable(false);
  // firstNameTextField.setText("");
  // rentalDaysTextField.setEditable(true);
  // userIdForTextField.setEditable(true);
  // userIdForTextField.setText("");
  //
  // newUserCheckBox.setSelected(false);
  // newUserCheckBox.setEnabled(true);
  //
  // getUserButton.setEnabled(true);
  // clearAllButton.setEnabled(true);
  // saveRentalButton.setEnabled(false);
  //
  // userIdForTextField.requestFocus();
  // } else {
  // JOptionPane.showMessageDialog(null, "The movie \""
  // + movieList.get(movieTable.getSelectedRow()).getTitle()
  // + "\" is rented.\n");
  // }
  // }

  /**
   * @param evt none.
   */
  private void saveRentalButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // get data from input fields
    String surName = surNameTextField.getText().trim();
    String firstName = firstNameTextField.getText().trim();
    String rentalDays = rentalDaysTextField.getText();

    IUser rentalUser;

    // check values in text fields
    if (isNameNoEmpty(surName, firstName)) {
      // create user if it is a new user
      if (newUserCheckBox.isSelected()) {
        rentalUser = new User(surName, firstName);
        rentalUser.setId(userListId + 1);
        userList.add(rentalUser);
        userListId++;
      } else {
        rentalUser = findUserInList();
      }

      // save rental in list
      int rowselection = movieTable.getSelectedRow();
      if (rowselection < 0) {
        JOptionPane.showMessageDialog(null, "Please select a movie to rent from the list.");
        return;
      } 

      try {
        Object movieObjId = movieTable.getValueAt(movieTable.getSelectedRow(), 0);
        String movieStrId = movieObjId.toString();
        int movieId = Integer.valueOf(movieStrId);

        Movie movie = dataSet.getMovieById(movieId);

        Rental rentalObj = new Rental(rentalUser, movie, Integer.valueOf(rentalDays));
        dataSet.updateMovie(movie);
        rentalObj.setId(rentalListId + 1);

        rentalList.add(rentalObj);
        rentalListId++;

        mainPaneStateChanged(null);

        // reset application
        movieTable.requestFocus();
        resetMenuItem.setEnabled(true);

        resetRentalActionForm(null);
      } catch (Exception e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
      }
    } else {
      JOptionPane.showMessageDialog(null, "Surname and first name may not be empty.");
      newUserCheckBoxActionPerformed(null);
    }
  }

  /**
   * Finds the user in the internal user list.
   * @return found user or null
   */
  private IUser findUserInList() {
    IUser rentalUser = null;

    for (int i = 0; i < userList.size(); i++) {
      if (userList.get(i).getId() == Integer.valueOf(userIdForTextField.getText()).longValue()) {
        rentalUser = userList.get(i);
      }
    }
    return rentalUser;
  }

  /**
   * Checks if the entered user name is not empty.
   * @param surName surname of user
   * @param firstName first name of user
   * @return true if non-empty
   */
  private boolean isNameNoEmpty(String surName, String firstName) {
    return !surName.isEmpty() || !firstName.isEmpty();
  }

  /**
   * @param evt none.
   */
  private void resetRentalActionForm(java.awt.event.ActionEvent evt) {
    surNameTextField.setEditable(false);
    surNameTextField.setText("");
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

  /**
   * @param evt none.
   */
  private void getUserButtonActionPerformed(java.awt.event.ActionEvent evt) {
    // get surname and/or user id as search parameter
    String name = surNameTextField.getText().toLowerCase();
    String userId = userIdForTextField.getText();
    int userCount = 0;

    IUser user = null;
    if (isNameValid(name)) {
      user = dataSet.getUserByName(name);
    } else if (isUserIdValid(userId)) {
      int uId = Integer.valueOf(userId).intValue();
      user = dataSet.getUserById(uId);
    } else {
      JOptionPane.showMessageDialog(null, "Please enter a new user or a valid " + "'User Id' or 'Surname'.");
      userIdForTextField.requestFocus();
      return;
    }

    if (user == null) {
      JOptionPane.showMessageDialog(null, "User with " + "'User Id' or 'Surname' not found.");
      userIdForTextField.requestFocus();
    }

    // check if multiple users were found
    if (userCount > 1) {
      JOptionPane.showMessageDialog(null, "Multiple users found. Please," + " additionally enter the user id.");
      userIdForTextField.setEditable(true);
      firstNameTextField.setText("");
      userIdForTextField.requestFocus();
    }

    if (user != null) {
      setViewStateForFoundUser(user);
      saveRentalButton.setEnabled(true);
    } 

  }

  /**
   * set the view state if user found.
   * @param currUser found user
   */
  private void setViewStateForFoundUser(IUser currUser) {
    surNameTextField.setText(currUser.getName());
    firstNameTextField.setText(currUser.getFirstName());
    userIdForTextField.setText(Integer.toString(currUser.getId()));

    surNameTextField.setEditable(false);
    firstNameTextField.setEditable(false);
    userIdForTextField.setEditable(false);
    newUserCheckBox.setEnabled(false);
  }

  /**
   * Checks if name is valid. 
   * @param name name to check
   * @return true if OK
   */
  private boolean isNameValid(String name) {
    return !name.isEmpty();
  }

  /**
   * Checks if userId is valid. 
   * @param userId userId to check
   * @return true if OK
   */
  private boolean isUserIdValid(String userId) {

    try {
      return !userId.isEmpty() && (Long.valueOf(userId).longValue() > 0)
          && Pattern.matches("\\d*", userId);
    } catch (Exception e) {
      return false;
    }

  }

  /**
   * @param evt none.
   */
  private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
    if (evt.getActionCommand().equals(exitMenuItem.getText())) {
      System.exit(JFrame.EXIT_ON_CLOSE);
    } // end of if
  }

  /**
   * @param evt none.
   */
  private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
    if (evt.getActionCommand().equals(aboutMenuItem.getText())) {
      AboutDialog about = new AboutDialog();
      about.setVisible(true);
    } // end of if
  }

  /** Tab index for <I>Rent Movie</I> tab. */
  private static final int RENT_MOVIE_TAB_INDEX = 0;
  /** Tab index for <I>Movies</I> tab. */
  private static final int MOVIES_TAB_INDEX = 1;
  /** Tab index for <I>Users</I> tab. */
  private static final int USERS_TAB_INDEX = 2;
  /** Tab index for <I>Rentals</I> tab. */
  private static final int RENTALS_TAB_INDEX = 3;

  /**
   * none.
   */
  private JMenuItem aboutMenuItem;
  /**
   * none.
   */
  private JButton cancelMoviesButton;
  /**
   * none.
   */
  private JButton clearAllButton;
  /**
   * none.
   */
  private JButton deleteMoviesButton;
  /**
   * none.
   */
  //  private JButton deleteRentalsButton;

  /**
   * none.
   */
  private JButton editMoviesButton;

  /**
   * none.
   */
  private JMenuItem exitMenuItem;
  /**
   * none.
   */
  private JTextField firstNameTextField;

  /**
   * none.
   */
  private JButton getUserButton;
  /**
   * none.
   */
  private JTabbedPane mainPane;
  /**
   * none.
   */
  private JPanel mainPanel;
  /**
   * none.
   */
  private JMenuBar menuBar;
  /**
   * none.
   */
  private JPanel movieCRUDPanel;
  /**
   * none.
   */
  private JTable movieCRUDTable;
  /**
   * none.
   */
  private JScrollPane movieScrollPane;
  /**
   * none.
   */
  private JTable movieTable;
  /**
   * none.
   */
  private JTextField movieTitleMoviesTextField;
  /**
   * none.
   */
  private JButton newMoviesButton;
  /**
   * none.
   */
  private JCheckBox newUserCheckBox;
  /**
   * none.
   */
  private JComboBox<String> priceCatMoviesComboBox;
  /**
   * none.
   */
  private JTextField releaseDateMoviesTextField;
  /**
   * none.
   */
  private JTextField rentalDateTextField;
  /**
   * none.
   */
  private JTextField rentalDaysTextField;
  /**
   * none.
   */
  private JMenuItem resetMenuItem;
  /**
   * none.
   */
  private JButton saveMoviesButton;
  /**
   * none.
   */
  private JButton saveRentalButton;
  /**
   * none.
   */
  private JTextField surNameTextField;
  /**
   * none.
   */
  private JFormattedTextField userIdForTextField;

  // End of variables declaration//GEN-END:variables
  /**
   * none.
   */
  private DataSet dataSet;
  /**
   * none.
   */
  private boolean editMode = false;
  /**
   * none.
   */
  private String currDate;
  /**
   * none.
   */
  private int movieListId = 0;
  /**
   * none.
   */
  private int userListId = 0;
  /**
   * none.
   */
  private int rentalListId = 0;
  /**
   * none.
   */
  private List<IUser> userList;
  /**
   * none.
   */
  private List<Movie> movieList;
  /**
   * none.
   */
  private List<Rental> rentalList;
  /**
   * none.
   */
  private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
}
