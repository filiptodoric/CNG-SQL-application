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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GUI extends JFrame implements DialogClient{

	public int GUI_DISPLAY_LIMIT = 100;
	int auto;
	boolean work = false;
	String employeeSelected = "";
	Connection databaseConnection;
	Statement stat;

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
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.exit(0);
					}
				}
				);



		// Make the main window view panel
		add(view = new ListPanel(dtm));

		// Add a listener for the add button
		theSearchButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				search();
			}};

			theAddButtonListener = new ActionListener()	{
				public void actionPerformed(ActionEvent event)	{
					int hold = 0;
					newEmployee = new Employee(hold, "" , "", "");

					EmployeeDetailDialog addD 	 = new EmployeeDetailDialog(thisFrame, thisFrame, "Add a new employee", true, newEmployee);
					addD.updateButton.setEnabled(false);
					addD.deleteButton.setEnabled(false);
					addD.employeeNumberField.setEnabled(false);
					addD.setVisible(true);
				}
			};



			employeeListSelectionListener = new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent event) {
					selectEmployee();
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
								JList theList = (JList) event.getSource();
								int index = theList.locationToIndex(event.getPoint());
								employeeBeingEdited = (Employee) theList.getModel().getElementAt(index);
								System.out.println("Double Click on: " + employeeBeingEdited);


								EmployeeDetailDialog dialog = new EmployeeDetailDialog(thisFrame, thisFrame, "Update an existing employee", true, employeeBeingEdited);         
								dialog.employeeNumberField.setEnabled(false);
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

								if (keyChar == KeyEvent.VK_ENTER)  search();

							}};


							setDefaultCloseOperation(EXIT_ON_CLOSE);
							setSize(600,300);

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
		//view.getEmployeeList().removeListSelectionListener(employeeListSelectionListener);
		view.getEmployeeList().getSelectionModel().removeListSelectionListener(employeeListSelectionListener);
		view.getEmployeeList().removeMouseListener(doubleClickEmployeeListListener);
		view.getSearchText().removeKeyListener(keyListener);
		view.getAddButton().removeActionListener(theAddButtonListener);
	}


	// This is called when the user clicks the add button
	private void search() {

		String tempText = view.getSearchText().getText().trim();
		String sqlSearch;

		if(employeeSelected.length() < 2){
			sqlSearch = "select * from employees where officeLocation like '%" + tempText + "%'";
			System.out.println(sqlSearch);}
		else{
			sqlSearch = "select * from employees where officeLocation like '%" + tempText + "%' and employeeName = '" + employeeSelected + "'";
		}

		try {

			ResultSet rs = stat.executeQuery(sqlSearch);
			employeeSelected = "";
			ArrayList<Employee> employeeSearchResults = new ArrayList<Employee>();

			int count = 0;
			while (rs.next()){
				Employee employee = new Employee(
						rs.getInt("employeeNumber"),
						rs.getString("employeeName"),
						rs.getString("phoneNumber"),
						rs.getString("officeLocation")
						);
				employeeSearchResults.add(employee);
			}
			rs.close(); //close the query result table
			employeeList = employeeSearchResults;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Search clicked");
		update();
	}


	// This is called when the user clicks the edit button


	// This is called when the user selects a department from the list
	private void selectDepartment() {
		selectedDepartment = (Department)(view.getdepartmentList().getSelectedValue());
		employeeSelected = selectedDepartment.getBookCode();
		System.out.println("Department Selected: " + selectedDepartment);

		update();
	}
	// This is called when the user selects a employee from the list
	private void selectEmployee() {
		int row = view.getEmployeeList().getSelectedRow();
		selectedEmployee = employeeList.get(row);
		System.out.println("Employee Selected: " + selectedEmployee);
		update();
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

//		Employee employeeArray[] = new Employee[1]; //just to establish array type
//		view.getEmployeeList().setListData(((Employee []) employeeList.toArray(employeeArray)));

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

	@Override
	public void dialogFinished(DialogClient.operation requestedOperation) {
		// TODO 


		if(requestedOperation == DialogClient.operation.UPDATE){
			//TO DO
			//update employee data in database
			int temp = employeeBeingEdited.getemployeeNumber();



			System.out.println("UPDATE: " + employeeBeingEdited );
			String tempPage = employeeBeingEdited.getphoneNumber();
			String tempTitle = employeeBeingEdited.getofficeLocation();
			String tempBookCode = employeeBeingEdited.getemployeeName();

			/*
			Using PreparedStatements in order to prevent SQL injection
			 */
			try {
				PreparedStatement stmtPAGE = databaseConnection.prepareStatement("update employees set phoneNumber =?  where employeeNumber=?");
				stmtPAGE.setString(1, tempPage);
				stmtPAGE.setInt(2, temp);
				stmtPAGE.execute();

				PreparedStatement stmtTitle = databaseConnection.prepareStatement("update employees set officeLocation =?  where employeeNumber=?");
				stmtTitle.setString(1, tempTitle);
				stmtTitle.setInt(2, temp);
				stmtTitle.execute();

				PreparedStatement stmtBookCode = databaseConnection.prepareStatement("update employees set employeeName =?  where employeeNumber=?");
				stmtBookCode.setString(1, tempBookCode);
				stmtBookCode.setInt(2, temp);
				stmtBookCode.execute();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}



		if(requestedOperation == DialogClient.operation.ADD){
			//TO DO
			//update employee data in database



			String diack = "SELECT MAX(employeeNumber) FROM employees;";
			try {
				ResultSet rs = stat.executeQuery(diack);
				if(rs.next())	{
					auto =  ((Number) rs.getObject(1)).intValue();	
					System.out.println(auto);
				}
			} 
			catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			int temp = newEmployee.getemployeeNumber();	
			temp = auto + 1;
			String tempPage = newEmployee.getphoneNumber();
			String tempTitle = newEmployee.getofficeLocation();
			String tempBookCode = newEmployee.getemployeeName();
			System.out.println("ADD: " + newEmployee );

			/*
			Using PreparedStatements in order to prevent SQL injection
			 */
			try {
				PreparedStatement stmtInsert = databaseConnection.prepareStatement("INSERT INTO employees (employeeNumber, phoneNumber, employeeName, officeLocation) VALUES (?,?,?,?);");
				stmtInsert.setInt(1, temp + 1);
				stmtInsert.setString(2, tempPage);
				stmtInsert.setString(3, tempBookCode);
				stmtInsert.setString(4, tempTitle);
				stmtInsert.execute();
				//sqlAdd = "INSERT INTO orders (orderNum, price, customer, description) VALUES" + temp + "," +

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}



		}





		else if(requestedOperation == DialogClient.operation.DELETE){
			//TO DO
			//delete employee from database

			int temp = employeeBeingEdited.getemployeeNumber();
			try {

				// PreparedStatement used to prevent SQL injection
				PreparedStatement stmt = databaseConnection.prepareStatement("delete from employees where employeeNumber=?");
				stmt.setInt(1, temp);
				stmt.execute();
				//stat.execute(sql);
				search();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("DELETE: " + employeeBeingEdited );

		}
		employeeBeingEdited = null;
		update();
	}

	@Override
	public void dialogCancelled() {

		employeeBeingEdited = null;
		update();

	}

}