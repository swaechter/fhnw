package ch.fhnw.edu.rental.gui;

import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;

import ch.fhnw.edu.rental.data.DataSet;
import ch.fhnw.edu.rental.model.IUser;
import ch.fhnw.edu.rental.model.Movie;
import ch.fhnw.edu.rental.model.Rental;

/**
 * The rentals overview panel.
 * 
 */
public class RentalsPanel extends JPanel {

    /**
     * none.
     */
    private List<Rental> rentalsList;
    
    /**
     * none.
     */
    private List<Movie> movieList;
    
    /**
     * 
     */
    private DataSet dataset;
    
    /**
     * none.
     */
    private JDialog getRentalsDialog;
  
    /**
     * none.
     */
    private JButton deleteRentalsButton;
    
    /**
     * none.
     */
    private JTable rentalsCRUDTable;
    
    /**
     * 
     */
    private static final long serialVersionUID = 8249303551023779145L;

    /**
     * 
     * @param movieList list of movies
     * @param rentalsList list of rentals
     * @param dataset reference to database
     */
    RentalsPanel(List<Movie> movieList, List<Rental> rentalsList, DataSet dataset) {

        this.movieList = movieList;
        this.rentalsList = rentalsList;
        this.dataset = dataset;
        
        initComponents();
    }

    /**
     * initialize the rental panel.
     */
    private void initComponents() {

        JScrollPane rentalsCRUDScrollPane = new JScrollPane();

        getRentalsDialog = new JDialog();
        
        rentalsCRUDTable = new JTable();
        deleteRentalsButton = new JButton();
        
        initRentalsPanel(rentalsCRUDScrollPane);
        setRentalPanelLayout(rentalsCRUDScrollPane);
    }

    /** initialize rental panel.
     * 
     * @param rentalsCRUDScrollPane scroll pane.
     */
    private void initRentalsPanel(JScrollPane rentalsCRUDScrollPane) {

        
        rentalsCRUDTable.setModel(new DefaultTableModel(new Object[][] {

        }, new String[] {"Rental ID", "Rental Days", "Rental Date", "Title", "Remaining Days", "Rental Fee" }) {
          private static final long serialVersionUID = -615724883768145787L;
          private Class<?>[] types = new Class[] {java.lang.Long.class, java.lang.Integer.class, java.lang.Object.class,
              java.lang.String.class, java.lang.Integer.class, java.lang.Double.class };
          private boolean[] canEdit = new boolean[] {false, false, false, false, false, false };

          public Class<?> getColumnClass(int columnIndex) {
            return types[columnIndex];
          }

          public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
          }
        });
        
        this.setName("rentalsTab");

        rentalsCRUDTable.setName("rentalsCRUDTable");
        
        rentalsCRUDTable.addMouseListener(new java.awt.event.MouseAdapter() {
          public void mouseClicked(java.awt.event.MouseEvent evt) {
            rentalsCRUDTableMouseClicked(evt);
          }
        });
        rentalsCRUDTable.addKeyListener(new java.awt.event.KeyAdapter() {
          public void keyPressed(java.awt.event.KeyEvent evt) {
            rentalsCRUDTableKeyPressed(evt);
          }
        });
        rentalsCRUDScrollPane.setViewportView(rentalsCRUDTable);

        deleteRentalsButton.setText("Delete");
        deleteRentalsButton.setEnabled(false);
        deleteRentalsButton.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent evt) {
            deleteRentalsButtonActionPerformed(evt);
          }
        });
    }
    

    /**
     * initialize the layout of the rental panel.
     * @param rentalsCRUDScrollPane scroll panel
     */
    private void setRentalPanelLayout(JScrollPane rentalsCRUDScrollPane) {
        
        GroupLayout getRentalsDialogLayout = new GroupLayout(getRentalsDialog.getContentPane());
        
        getRentalsDialog.getContentPane().setLayout(getRentalsDialogLayout);
        getRentalsDialogLayout.setHorizontalGroup(getRentalsDialogLayout.createParallelGroup(
            GroupLayout.Alignment.LEADING).addGroup(
            GroupLayout.Alignment.TRAILING,
            getRentalsDialogLayout.createSequentialGroup().addContainerGap()
                        .addContainerGap())
                        );
        getRentalsDialogLayout.setVerticalGroup(getRentalsDialogLayout.createParallelGroup(
            GroupLayout.Alignment.LEADING).addGroup(
            GroupLayout.Alignment.TRAILING,
            getRentalsDialogLayout.createSequentialGroup()
                .addContainerGap()
                ));
        
        GroupLayout rentalsCRUDPanelLayout = new GroupLayout(this);
        
        this.setLayout(rentalsCRUDPanelLayout);
        
        rentalsCRUDPanelLayout.setHorizontalGroup(rentalsCRUDPanelLayout.createParallelGroup(
            GroupLayout.Alignment.LEADING).addComponent(rentalsCRUDScrollPane,
            GroupLayout.DEFAULT_SIZE, 527, Short.MAX_VALUE).addGroup(
            GroupLayout.Alignment.TRAILING,
            rentalsCRUDPanelLayout.createSequentialGroup().addContainerGap(446, Short.MAX_VALUE).addComponent(
                deleteRentalsButton).addContainerGap()));
        
        rentalsCRUDPanelLayout.setVerticalGroup(rentalsCRUDPanelLayout.createParallelGroup(
            GroupLayout.Alignment.LEADING).addGroup(
            GroupLayout.Alignment.TRAILING,
            rentalsCRUDPanelLayout.createSequentialGroup().addComponent(rentalsCRUDScrollPane,
                GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE).addGap(18, 18, 18).addComponent(
                deleteRentalsButton).addContainerGap()));
    }

    /**
     * @param evt none.
     */
    private void rentalsCRUDTableMouseClicked(java.awt.event.MouseEvent evt) {
      if (rentalsCRUDTable.getRowCount() > 0) {
        deleteRentalsButton.setEnabled(true);
      } else {
        deleteRentalsButton.setEnabled(false);
      }
    }
    /**
     * @param evt none.
     */
    private void rentalsCRUDTableKeyPressed(java.awt.event.KeyEvent evt) {
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
    /**
     * @param evt none.
     */
    private void deleteRentalsButtonActionPerformed(java.awt.event.ActionEvent evt) {
      int selRow = rentalsCRUDTable.getSelectedRow();
      if (rentalsCRUDTable.getRowCount() <= 0 || selRow < 0) {
        return;
      }

      // update movie list
      setSelectedMovieToUnrented(selRow);
      removeRentalFromRentalsList(selRow);

      deleteRentalsButton.setEnabled(false);
      rentalsCRUDTable.requestFocus();

      updateView();
    }

    /**
     * Removes selected rental from rental list.
     * @param selectedRow selected rental
     */
    private void removeRentalFromRentalsList(int selectedRow) {
        IUser currUser = rentalsList.get(selectedRow).getUser();
          List<Rental> userRentalsList = currUser.getRentals();
          
          for (int i = 0; i < userRentalsList.size(); i++) {
            if (rentalsList.get(selectedRow).getId() == userRentalsList.get(i).getId()) {
                userRentalsList.remove(i);

              rentalsList.remove(selectedRow);
              // set new rentals
              currUser.setRentals(userRentalsList);
            }
          }
    }

    /**
     * sets the state of the no longer rented movie to not rented.
     * @param selectedRow the selected movie in the list
     */
    private void setSelectedMovieToUnrented(int selectedRow) {
        for (int i = 0; i < movieList.size(); i++) {
            if (movieList.get(i).getId() == rentalsList.get(selectedRow).getMovie().getId()) {
              movieList.get(i).setRented(false);
            }
          }
    }
    /**
     * Update the view.
     */
    public void updateView() {

        rentalsCRUDTable
        .setModel(new DefaultTableModel(dataset.getRentalListAsObject(), new String[] {
            "Rental ID", "Rental Days", "Rental Date", "Surname", "First name", "Title", "Remaining Days",
            "Rental fee" }) {
          private static final long serialVersionUID = 2849097606635036753L;
          private Class<?>[] types = new Class[] {java.lang.Integer.class, java.lang.Integer.class,
              java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class,
              java.lang.Integer.class, java.lang.Double.class };
          private boolean[] canEdit = new boolean[] {false, false, false, false, false, false, false, false };

          public Class<?> getColumnClass(int columnIndex) {
            return types[columnIndex];
          }

          public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit[columnIndex];
          }
        });

    deleteRentalsButton.setEnabled(false);

    }
}

