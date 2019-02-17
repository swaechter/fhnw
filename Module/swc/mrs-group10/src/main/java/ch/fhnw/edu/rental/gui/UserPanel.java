package ch.fhnw.edu.rental.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import ch.fhnw.edu.rental.data.DataSet;
import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.User;

/**
 * The user management panel.
 * 
 */
public class UserPanel extends JPanel {

  /**
     * 
     */
  private static final long serialVersionUID = -1268216160093322555L;

  /**
   * none.
   */
  private int userListId = 0;

  /**
   * none.
   */
  private boolean editMode = false;

  /**
   * none.
   */
  private List<IUser> userList;
  /**
   * none.
   */
  private List<Movie> movieList;

  /**
   * the save button.
   */
  private JButton saveUsersButton;
  /**
   * none.
   */
  private JTextField surNameUsersTextField;

  /**
   * none.
   */
  private JTable usersCRUDTable;

  /**
   * none.
   */
  private JButton deleteUsersButton;

  /**
   * none.
   */
  private JButton editUsersButton;

  /**
   * none.
   */
  private JButton cancelUsersButton;

  /**
   * none.
   */
  private JButton newUsersButton;

  /**
   * none.
   */
  private JTextField firstNameUsersTextField;

  /**
   * The dataset to the database.
   */
  private DataSet dataset;

  /**
   * 
   * @param userList
   *          list if users
   * @param movieList
   *          list of movies
   * @param dataset
   *          reference to database
   */
  UserPanel(List<IUser> userList, List<Movie> movieList, DataSet dataset) {
    this.userList = userList;
    this.movieList = movieList;
    this.dataset = dataset;

    initComponents();
  }

  /**
   * init the gui components.
   */
  void initComponents() {

    JScrollPane usersCRUDScrollPane = new JScrollPane();
    usersCRUDTable = new JTable();
    saveUsersButton = new JButton();
    deleteUsersButton = new JButton();
    editUsersButton = new JButton();
    newUsersButton = new JButton();
    cancelUsersButton = new JButton();
    firstNameUsersTextField = new JTextField();
    surNameUsersTextField = new JTextField();

    this.setName("usersTab2");

    createUserTable(usersCRUDScrollPane);

    saveUsersButton.setText("Save");
    saveUsersButton.setEnabled(false);
    saveUsersButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        saveUsersButtonActionPerformed(evt);
      }
    });

    deleteUsersButton.setText("Delete");
    deleteUsersButton.setEnabled(false);
    deleteUsersButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        deleteUsersButtonActionPerformed(evt);
      }
    });

    editUsersButton.setText("Edit");
    editUsersButton.setEnabled(false);
    editUsersButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        editUsersButtonActionPerformed(evt);
      }
    });

    newUsersButton.setText("New ...");
    newUsersButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        newUsersButtonActionPerformed(evt);
      }
    });

    cancelUsersButton.setText("Cancel");
    cancelUsersButton.setEnabled(false);
    cancelUsersButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        cancelUsersButtonActionPerformed(evt);
      }
    });

    firstNameUsersTextField.setEnabled(false);
    firstNameUsersTextField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        firstNameUsersTextFieldKeyPressed(evt);
      }
    });

    surNameUsersTextField.setEnabled(false);
    surNameUsersTextField.addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent evt) {
        surNameUsersTextFieldKeyPressed(evt);
      }
    });

    initializeLayout(usersCRUDScrollPane);

  }

  /**
   * create and initialize user table.
   * 
   * @param usersCRUDScrollPane
   *          the user pane
   */
  private void createUserTable(JScrollPane usersCRUDScrollPane) {
    MouseAdapter ma = new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent evt) {
        usersCRUDTableMouseClicked(evt);
      }
    };
    KeyAdapter ka = new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent evt) {
        usersCRUDTableKeyPressed(evt);
      }
    };
    usersCRUDTable.setModel(createTableModel());
    usersCRUDTable.addMouseListener(ma);
    usersCRUDTable.addKeyListener(ka);
    usersCRUDScrollPane.setViewportView(usersCRUDTable);
  }

  /**
   * Initialize the layout of the panel.
   * 
   * @param usersCRUDScrollPane
   *          the user panel.
   */
  private void initializeLayout(JScrollPane usersCRUDScrollPane) {

    JLabel firstNameUsersLabel = new JLabel();
    JLabel surNameUsersLabel = new JLabel();

    GroupLayout usersCRUDPanelLayout = new GroupLayout(this);
    this.setLayout(usersCRUDPanelLayout);

    firstNameUsersLabel.setText("First Name:");
    surNameUsersLabel.setText("Surname:");
    usersCRUDPanelLayout.setHorizontalGroup(usersCRUDPanelLayout
        .createParallelGroup(GroupLayout.Alignment.LEADING)
        .addGroup(
            GroupLayout.Alignment.TRAILING,
            usersCRUDPanelLayout.createSequentialGroup()
                .addContainerGap(149, Short.MAX_VALUE)
                .addComponent(cancelUsersButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(newUsersButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editUsersButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(deleteUsersButton).addGap(18, 18, 18)
                .addComponent(saveUsersButton).addContainerGap())
        .addGroup(
            GroupLayout.Alignment.TRAILING,
            usersCRUDPanelLayout
                .createSequentialGroup()
                .addContainerGap()
                .addGroup(
                    usersCRUDPanelLayout
                        .createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(firstNameUsersLabel)
                        .addComponent(surNameUsersLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(
                    usersCRUDPanelLayout
                        .createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(surNameUsersTextField,
                            GroupLayout.Alignment.LEADING,
                            GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
                        .addComponent(firstNameUsersTextField,
                            GroupLayout.Alignment.LEADING,
                            GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE))
                .addGap(53, 53, 53).addContainerGap())
        .addComponent(usersCRUDScrollPane, GroupLayout.DEFAULT_SIZE, 527,
            Short.MAX_VALUE));

    usersCRUDPanelLayout.setVerticalGroup(usersCRUDPanelLayout
        .createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
            GroupLayout.Alignment.TRAILING,
            usersCRUDPanelLayout
                .createSequentialGroup()
                .addComponent(usersCRUDScrollPane, GroupLayout.DEFAULT_SIZE,
                    372, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(
                    usersCRUDPanelLayout
                        .createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(surNameUsersLabel)
                        .addComponent(surNameUsersTextField,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(
                    usersCRUDPanelLayout
                        .createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(firstNameUsersLabel)
                        .addComponent(firstNameUsersTextField,
                            GroupLayout.PREFERRED_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(
                    usersCRUDPanelLayout
                        .createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(saveUsersButton)
                        .addComponent(deleteUsersButton)
                        .addComponent(editUsersButton)
                        .addComponent(newUsersButton)
                        .addComponent(cancelUsersButton)).addContainerGap()));
  }

  /**
   * @param evt
   *          none.
   */
  private void editUsersButtonActionPerformed(ActionEvent evt) {
    int rowCount = usersCRUDTable.getSelectedRow();

    surNameUsersTextField.setText(userList.get(rowCount).getName());
    firstNameUsersTextField.setText(userList.get(rowCount).getFirstName());

    surNameUsersTextField.setEnabled(true);
    firstNameUsersTextField.setEnabled(true);

    newUsersButton.setEnabled(false);
    deleteUsersButton.setEnabled(false);
    editUsersButton.setEnabled(false);
    cancelUsersButton.setEnabled(true);
    saveUsersButton.setEnabled(true);

    this.editMode = true;
  }

  /**
   * @param evt
   *          none.
   */
  private void deleteUsersButtonActionPerformed(ActionEvent evt) {
    int rowCount = usersCRUDTable.getSelectedRow();

    User user = (User) userList.get(rowCount);

    if (!user.hasRentals()) {
      userList.remove(rowCount);
    } else {
      JOptionPane.showMessageDialog(null, "There are still rentals from \""
          + userList.get(rowCount).getFirstName() + " "
          + userList.get(rowCount).getName() + "\".\n"
          + "User can not be deleted.");
    }

    deleteUsersButton.setEnabled(false);
    editUsersButton.setEnabled(false);
    newUsersButton.setEnabled(true);
    cancelUsersButton.setEnabled(false);

    usersCRUDTable.requestFocus();

    updateView();
  }

  /**
   * @param evt
   *          none.
   */
  private void cancelUsersButtonActionPerformed(ActionEvent evt) {
    cancelUsersButton.setEnabled(false);
    newUsersButton.setEnabled(true);
    deleteUsersButton.setEnabled(false);
    editUsersButton.setEnabled(false);
    saveUsersButton.setEnabled(false);

    surNameUsersTextField.setEnabled(false);
    surNameUsersTextField.setText("");
    
    firstNameUsersTextField.setEnabled(false);
    firstNameUsersTextField.setText("");

    usersCRUDTable.requestFocus();
  }

  /**
   * @param evt
   *          none.
   */
  private void usersCRUDTableMouseClicked(MouseEvent evt) {
    if (usersCRUDTable.getRowCount() > 0) {
      editUsersButton.setEnabled(true);
      deleteUsersButton.setEnabled(true);
      saveUsersButton.setEnabled(false);
      cancelUsersButton.setEnabled(false);
      newUsersButton.setEnabled(true);

      surNameUsersTextField.setEnabled(false);
      surNameUsersTextField.setText("");
      firstNameUsersTextField.setEnabled(false);
      firstNameUsersTextField.setText("");
    } else {
      cancelUsersButtonActionPerformed(null);
    }

  }

  /**
   * @param evt
   *          none.
   */
  private void saveUsersButtonActionPerformed(ActionEvent evt) {
    String surName = surNameUsersTextField.getText();
    String firstName = firstNameUsersTextField.getText();

    if (!surName.isEmpty()) {
      User currUser = new User(surName, firstName);
      if (editMode) {
        int rowCount = usersCRUDTable.getSelectedRow();
        currUser.setId(movieList.get(rowCount).getId());
        userList.set(rowCount, currUser);

        // also update rentals
        dataset.updateRentalsWithUser(currUser);

        editMode = false;
      } else {
        currUser.setId(userListId + 1);
        userList.add(currUser);
        userListId++;
      }

      surNameUsersTextField.setEnabled(false);
      firstNameUsersTextField.setEnabled(false);

      newUsersButton.setEnabled(true);
      saveUsersButton.setEnabled(false);
      cancelUsersButton.setEnabled(false);
      deleteUsersButton.setEnabled(false);
      editUsersButton.setEnabled(false);

      updateView();
    } else {
      JOptionPane.showMessageDialog(null, "Surname may not be empty.");
      // surNameTextField.requestFocus();
      newUsersButtonActionPerformed(null);
    }
  }

  /**
   * @param evt
   *          none.
   */
  private void newUsersButtonActionPerformed(ActionEvent evt) {
    surNameUsersTextField.setEnabled(true);
    surNameUsersTextField.setText("");
    firstNameUsersTextField.setEnabled(true);
    firstNameUsersTextField.setText("");

    newUsersButton.setEnabled(false);
    cancelUsersButton.setEnabled(true);
    saveUsersButton.setEnabled(true);
    editUsersButton.setEnabled(false);
    deleteUsersButton.setEnabled(false);

    surNameUsersTextField.requestFocus();
  }

  /**
   * @param evt
   *          none.
   */
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

  /**
   * @param evt
   *          none.
   */
  private void firstNameUsersTextFieldKeyPressed(KeyEvent evt) {
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
      saveUsersButtonActionPerformed(null);
    } else if (evt.getKeyCode() == KeyEvent.VK_TAB) {
      saveUsersButton.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
      cancelUsersButtonActionPerformed(null);
    }
  }

  /**
   * @param evt
   *          none.
   */
  private void surNameUsersTextFieldKeyPressed(KeyEvent evt) {
    if (evt.getKeyCode() == KeyEvent.VK_TAB) {
      firstNameUsersTextField.requestFocus();
    } else if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
      cancelUsersButtonActionPerformed(null);
    }
  }
  
  /**
   * Update the view.
   */
  public void updateView() {

    usersCRUDTable.setModel(createTableModel());

    cancelUsersButton.setEnabled(false);
    newUsersButton.setEnabled(true);
    deleteUsersButton.setEnabled(false);
    editUsersButton.setEnabled(false);
    saveUsersButton.setEnabled(false);

    surNameUsersTextField.setEnabled(false);
    surNameUsersTextField.setText("");
    firstNameUsersTextField.setEnabled(false);
    firstNameUsersTextField.setText("");

  }
  
  /**
   * Factory Method for table model.
   * 
   * @return a DefaultTableModel
   */
  private TableModel createTableModel() {
    return new UserTableModel(dataset.getUserListAsObject());
  }

}

/**
 * Table model for user table.
 */
class UserTableModel extends DefaultTableModel {
  /**
     * 
     */
    private static final long serialVersionUID = 1L;
/**
   * Ctor.
   * @param data the data to display in the table.
   */
  UserTableModel(Object[][] data) {
    super(data, new String[] {"User ID", "Surname", "First name"});
  }
  
  /**
   * Column types.
   */
  private Class<?>[] types = new Class[] {Integer.class, String.class, String.class};
  /** 
   * Column editability.
   */
  private boolean[] canEdit = new boolean[] {false, false, false};
  /**
   * @param columnIndex a column.
   * @return type of a column.
   */
  public Class<?> getColumnClass(int columnIndex) {
    return types[columnIndex];
  }

  /**
   * @param rowIndex cell's row.
   * @param columnIndex cell's column.
   * @return whether a cell is editable or not.
   */
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return canEdit[columnIndex];
  }
 
}
