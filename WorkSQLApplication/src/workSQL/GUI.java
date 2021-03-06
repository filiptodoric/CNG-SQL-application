package workSQL;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import java.util.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class GUI extends JFrame implements DialogClient{

	public int GUI_DISPLAY_LIMIT = 100;
	int auto;
	boolean work = false;
	String employeeSelected = "";
	Connection databaseConnection;
	Statement stat;
	String refreshJTable = "select * from dbo.staff";
	ArrayList<Department> departmentList = new ArrayList<Department>();
	ArrayList<Employee> employeeList = new ArrayList<Employee>();

	private Employee    selectedEmployee; //employee currently selected in the GUI list
	private Department	selectedDepartment; //department currently selected in the GUI list

	private Employee employeeBeingEdited; //employee being edited in a dialog
	private Employee newEmployee;

	// Store the view that contains the components
	ListPanel 		view; //panel of GUI components for the main window
	GUI thisFrame;

	// Here are the component listeners
	ActionListener			theAddButtonListener;
	ActionListener			theSearchButtonListener;
	ListSelectionListener	employeeListSelectionListener;
	ListSelectionListener	departmentListSelectionListener;
	MouseListener			doubleClickEmployeeListListener;
	KeyListener             keyListener;


	// Here is the default constructor
	public GUI(String title, Connection aDB, Statement aStatement, ArrayList<Department> initialDepartments, ArrayList<Employee> initialEmployees, DefaultTableModel dtm) {
		super(title);
		databaseConnection = aDB;
		stat = aStatement;
		departmentList = initialDepartments;
		employeeList = initialEmployees;
		selectedDepartment = null;
		selectedEmployee = null;
		thisFrame = this;

		addWindowListener(
				new WindowAdapter() {
					public void windowClosing(WindowEvent e) {
						try {
							System.out.println("Closing Database Connection");
							databaseConnection.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						System.exit(0);
					}
				}
				);



		// Make the main window view panel
		add(view = new ListPanel(dtm));

		theSearchButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					search();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}};

			theAddButtonListener = new ActionListener()	{
				public void actionPerformed(ActionEvent event)	{
					int tempEmptyEmployee = 0;
					newEmployee = new Employee(tempEmptyEmployee, "" , "", "", "", "", -1); //TODO change from -1

					EmployeeDetailDialog addD 	 = new EmployeeDetailDialog(thisFrame, thisFrame, "Add a new employee", true, newEmployee, departmentList);
					addD.updateButton.setEnabled(false);
					addD.deleteButton.setEnabled(false);
					addD.setVisible(true);
				}
			};



			employeeListSelectionListener = new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					//selectEmployee();
				}};
				departmentListSelectionListener = new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent event) {
						selectDepartment();
						work = true;
					}};

					// Add a listener to allow double click selections from the list for editing
					doubleClickEmployeeListListener = new MouseAdapter() {
						public void mouseClicked(MouseEvent event) {
							if (event.getClickCount() == 2) {
								JTable theTable = (JTable) event.getSource();
								int index = theTable.rowAtPoint(event.getPoint());
								employeeBeingEdited = editEmployee();
								System.out.println("Double Click on: " + employeeBeingEdited);


								EmployeeDetailDialog dialog = new EmployeeDetailDialog(thisFrame, thisFrame, "Update an existing employee", true, employeeBeingEdited, departmentList);         
								dialog.lastNameField.setEnabled(true);
								dialog.addButton.setEnabled(false);
								dialog.setVisible(true);

							} 

						}};

						keyListener = new KeyListener() {

							@Override
							public void keyPressed(KeyEvent arg0) {

							}

							@Override
							public void keyReleased(KeyEvent arg0) {

							}

							@Override
							public void keyTyped(KeyEvent arg0) {

								int keyChar = arg0.getKeyChar();

								//if (keyChar == KeyEvent.VK_ENTER)  search();

							}};


							setDefaultCloseOperation(EXIT_ON_CLOSE);
							setSize(1024,768);

							// Start off with everything updated properly to reflect the model state
							update();
	}

	// Enable all listeners
	private void enableListeners() {
		view.getSearchButton().addActionListener(theSearchButtonListener);
		view.getdepartmentList().addListSelectionListener(departmentListSelectionListener);
		view.getEmployeeList().getSelectionModel().addListSelectionListener(employeeListSelectionListener);
		view.getEmployeeList().addMouseListener(doubleClickEmployeeListListener);
		view.getSearchText().addKeyListener(keyListener);
		view.getAddButton().addActionListener(theAddButtonListener);
	}

	// Disable all listeners
	private void disableListeners() {
		view.getSearchButton().removeActionListener(theSearchButtonListener);
		view.getdepartmentList().removeListSelectionListener(departmentListSelectionListener);
		view.getEmployeeList().getSelectionModel().removeListSelectionListener(employeeListSelectionListener);
		view.getEmployeeList().getSelectionModel().removeListSelectionListener(employeeListSelectionListener);
		view.getEmployeeList().removeMouseListener(doubleClickEmployeeListListener);
		view.getSearchText().removeKeyListener(keyListener);
		view.getAddButton().removeActionListener(theAddButtonListener);
	}


	// This is called when the user clicks the add button
	private void search() throws SQLException {

		String tempText = view.getSearchText().getText().trim();
		String sqlSearch;

		if(employeeSelected.length() < 2){
			sqlSearch = "select * from dbo.staff where officeLocation like '%" + tempText + "%' or FirstName like '%" + tempText + "%' or LastName like '%" + tempText + "%'";
			System.out.println(sqlSearch);}
		else{
			sqlSearch = "select * from dbo.staff where officeLocation like '%" + tempText + "%' and FirstName = '" + employeeSelected + "'";
		}

		try {
/* TODO
 * Change the above sqlsearch statement to also return the DeparmtentID
 * 
 */
			ResultSet rs = stat.executeQuery(sqlSearch);
			employeeSelected = "";
			ArrayList<Employee> employeeSearchResults = new ArrayList<Employee>();

			int count = 0;
			while (rs.next()){
				Employee employee = new Employee(
						rs.getInt("StaffID"),
						rs.getString("FirstName"),
						rs.getString("LastName"),
						rs.getString("phoneNumber"),
						rs.getString("officeLocation"),
						rs.getString("StaffPosition"),
						rs.getInt("DepmartmentID") 
						);
				employeeSearchResults.add(employee);
			}
			rs.close(); //close the query result table
			employeeList = employeeSearchResults;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("Search clicked");
		update();
		updateJTable(sqlSearch);
	}


	// This is called when the user clicks the edit button


	// This is called when the user selects a department from the list
	private void selectDepartment() {
		selectedDepartment = (Department)(view.getdepartmentList().getSelectedValue());
		employeeSelected = selectedDepartment.getDepartmentName();
		System.out.println("Department Selected: " + selectedDepartment);

		update();
	}
	// This is called when the user selects a employee from the list
	private void selectEmployee() {
		int row = view.getEmployeeList().getSelectedRow();
		selectedEmployee = employeeList.get(row);
		update();
	}

	public Employee editEmployee()	{
		int row = view.getEmployeeList().getSelectedRow();
		return selectedEmployee = employeeList.get(row);
	}
	
	// Update the remove button
	private void updateSearchButton() {
		view.getSearchButton().setEnabled(true);
	}



	// Update the list
	@SuppressWarnings("unchecked")
	private void updateList() {
		boolean		foundSelected = false;

		Department DepartmentArray[] = new Department[1]; //just to establish array type
		view.getdepartmentList().setListData(((Department []) departmentList.toArray(DepartmentArray)));


		if (selectedDepartment != null)
			view.getdepartmentList().setSelectedValue(selectedDepartment, true);
//				if (selectedEmployee != null)
//					view.getEmployeeList().setSelectedValue(selectedEmployee, true);
	}

	// Update the components
	private void update() {
		disableListeners();
		updateList();
		updateSearchButton();
		enableListeners();
	}
	

	public void updateJTable(String query) throws SQLException	{
		ResultSet work = stat.executeQuery(query);
		ResultSetMetaData rsmeta = work.getMetaData();
		int columns = rsmeta.getColumnCount();
		DefaultTableModel dtm = new DefaultTableModel();
		Vector column_name = new Vector();
		Vector data_rows = new Vector();

		for(int i = 1; i < columns; i++)	{
			column_name.addElement(rsmeta.getColumnName(i)); 
		}
		dtm.setColumnIdentifiers(column_name);

		while(work.next())	{
			data_rows = new Vector();
			for(int j = 1; j < columns; j++)	{
				data_rows.addElement(work.getString(j));
			}
			dtm.addRow(data_rows);
		}
		
		view.employeeList.setModel(dtm);
		view.employeeList.repaint();

	}

	@Override
	public void dialogFinished(DialogClient.operation requestedOperation) {


		if(requestedOperation == DialogClient.operation.UPDATE){
			//TO DO
			//update employee data in database
			int temp = employeeBeingEdited.getemployeeNumber();



			System.out.println("UPDATE: " + employeeBeingEdited );
			String phoneNumber 		= employeeBeingEdited.getphoneNumber();
			String officeLocation 	= employeeBeingEdited.getofficeLocation();
			String firstName 		= employeeBeingEdited.getFirstName();
			String lastName 		= employeeBeingEdited.getLastName();
			String staffPosition 	= employeeBeingEdited.getStaffPosition();
			/*
			Using PreparedStatements in order to prevent SQL injection
			 */
			try {
				PreparedStatement phoneNumberPrep = databaseConnection.prepareStatement("update dbo.staff set PhoneNumber =?  where StaffID=?");
				phoneNumberPrep.setString(1, phoneNumber);
				phoneNumberPrep.setInt(2, temp);
				phoneNumberPrep.execute();

				PreparedStatement officeLocationPrep = databaseConnection.prepareStatement("update dbo.staff set officeLocation =?  where StaffID=?");
				officeLocationPrep.setString(1, officeLocation);
				officeLocationPrep.setInt(2, temp);
				officeLocationPrep.execute();

				PreparedStatement firstNamePrep = databaseConnection.prepareStatement("update dbo.staff set FirstName =?  where StaffID=?");
				firstNamePrep.setString(1, firstName);
				firstNamePrep.setInt(2, temp);
				firstNamePrep.execute();
				
				PreparedStatement lastNamePrep = databaseConnection.prepareStatement("update dbo.staff set LastName =?  where StaffID=?");
				lastNamePrep.setString(1, lastName);
				lastNamePrep.setInt(2, temp);
				lastNamePrep.execute();
				
				PreparedStatement staffPositionPrep = databaseConnection.prepareStatement("update dbo.staff set StaffPosition =?  where StaffID=?");
				staffPositionPrep.setString(1, staffPosition);
				staffPositionPrep.setInt(2, temp);
				staffPositionPrep.execute();
				
				updateJTable(refreshJTable);
			} catch (SQLException e) {
				e.printStackTrace();
			}



		}



		if(requestedOperation == DialogClient.operation.ADD){
			//TO DO
			//update employee data in database



			String diack = "SELECT MAX(StaffID) FROM dbo.staff;";
			try {
				ResultSet rs = stat.executeQuery(diack);
				if(rs.next())	{
					auto =  ((Number) rs.getObject(1)).intValue();	
					System.out.println(auto);
				}
			} 
			catch (SQLException e1) {
				e1.printStackTrace();
			}


			String phoneNumberPrep 		= newEmployee.getphoneNumber();
			String officeLocationPrep 	= newEmployee.getofficeLocation();
			String firstNamePrep 		= newEmployee.getFirstName();
			String lastNamePrep 		= newEmployee.getLastName();
			String staffPositionPrep 	= newEmployee.getStaffPosition();
			System.out.println("ADD: " + newEmployee );

			/*
			Using PreparedStatements in order to prevent SQL injection
			 */
			try {
				PreparedStatement stmtInsert = databaseConnection.prepareStatement("INSERT INTO dbo.staff (PhoneNumber, FirstName, LastName, StaffPosition, OfficeLocation) VALUES (?,?,?,?,?);");
				stmtInsert.setString(1, phoneNumberPrep);
				stmtInsert.setString(2, firstNamePrep);
				stmtInsert.setString(3, lastNamePrep);
				stmtInsert.setString(4, staffPositionPrep);
				stmtInsert.setString(5, officeLocationPrep);
				stmtInsert.execute();
				updateJTable(refreshJTable);
				search();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


		else if(requestedOperation == DialogClient.operation.DELETE){
			//TO DO
			//delete employee from database

			int temp = employeeBeingEdited.getemployeeNumber();
			try {

				// PreparedStatement used to prevent SQL injection
				PreparedStatement stmt = databaseConnection.prepareStatement("delete from dbo.staff where StaffID=?");
				stmt.setInt(1, temp);
				stmt.execute();
				stmt = databaseConnection.prepareStatement("delete from dbo.Staff_Departments where StaffID=?");
				stmt.setInt(1, temp);
				stmt.execute();
				System.out.println("Deleting staff member with ID: " + temp);
				search();
				updateJTable(refreshJTable);

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		employeeBeingEdited = null;
		update();
	}

	@Override
	public void dialogCancelled() {

		employeeBeingEdited = null;
		update();
		try {
			updateJTable(refreshJTable);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}