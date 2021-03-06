package workSQL;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.*;

public class EmployeeDetailDialog extends JDialog {

	private Employee theEmployee;
	
	DialogClient theDialogClient;

	// These are the components of the dialog box
	private JLabel					aLabel; //reuseable label variable
	
	public JTextField				officeLocationField; 
	public JTextField				firstNameField; 
	public JTextField				phoneNumberField; 
	public JTextField				lastNameField;
	public JTextField				staffPositionField;
	
	public JButton					updateButton;
	public JButton					deleteButton;
	private JButton					cancelButton;
	public JButton					addButton;

	public ArrayList<Department>	departments;
	public JComboBox 				departmentDropdown;
	
	Font UIFont = new Font("Times New Roman", Font.BOLD, 16);
	


	public EmployeeDetailDialog(Frame owner, DialogClient aClient, String title, boolean modal, Employee aEmployee, ArrayList<Department> aDepartments){
		super(owner,title,modal);

		//Store the client and model variables
		theDialogClient = aClient;
		theEmployee = aEmployee;
		departments = aDepartments;

		// Put all the components onto the window and given them initial values
		buildDialogWindow(theEmployee, aDepartments);

		// Add listeners for the Ok and Cancel buttons as well as window closing
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				updateButtonClicked();
			}});
		
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				deleteButtonClicked();
			}});
		
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				addButtonClicked();
			}});

		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event){
				cancelButtonClicked();
			}});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				cancelButtonClicked();
			}});

		setSize(400, 275);
		
	}

	// This code adds the necessary components to the interface
	private void buildDialogWindow(Employee theEmployee, ArrayList departments) {
		
   		GridBagLayout layout = new GridBagLayout();
        GridBagConstraints lc = new GridBagConstraints();
        getContentPane().setLayout(layout);

 
        lc.anchor = GridBagConstraints.EAST;
        lc.insets = new Insets(5, 5, 5, 5);

        aLabel = new JLabel("First Name");
        lc.gridx = 0; lc.gridy = 0;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);
        
        aLabel = new JLabel("Last Name");
        lc.gridx = 0; lc.gridy = 1;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);
        
        aLabel = new JLabel("Staff Position");
        lc.gridx = 0; lc.gridy = 2;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);
        
        aLabel = new JLabel("Office Location");
        lc.gridx = 0; lc.gridy = 3;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("Phone Number");
        lc.gridx = 0; lc.gridy = 4;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("Department");
        lc.gridx = 0; lc.gridy = 5;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);
        
        aLabel = new JLabel("  "); //blank label
        lc.gridx = 0; lc.gridy = 5;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

        aLabel = new JLabel("  "); //blank label
        lc.gridx = 1; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(aLabel, lc);
        getContentPane().add(aLabel);

		firstNameField = new JTextField(theEmployee.getFirstName());
		firstNameField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 0;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(firstNameField, lc);
   		getContentPane().add(firstNameField);
   		
		lastNameField = new JTextField(""+ theEmployee.getLastName());
		lastNameField.setFont(UIFont);
		lc.gridx = 1; lc.gridy = 1;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(lastNameField, lc);
   		getContentPane().add(lastNameField);
   		
		staffPositionField = new JTextField(""+ theEmployee.getStaffPosition());
		staffPositionField.setFont(UIFont);
		lc.gridx = 1; lc.gridy = 2;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(staffPositionField, lc);
   		getContentPane().add(staffPositionField);
        
		officeLocationField = new JTextField(theEmployee.getofficeLocation());
		officeLocationField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 3;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(officeLocationField, lc);
   		getContentPane().add(officeLocationField);
   		
		phoneNumberField = new JTextField(""+ theEmployee.getphoneNumber());
		phoneNumberField.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 4;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(phoneNumberField, lc);
   		getContentPane().add(phoneNumberField);
        
   		Object[] array = departments.toArray();
   		departmentDropdown = new JComboBox(array);
   		departmentDropdown.setSelectedIndex(theEmployee.getDepartmentNumber() - 1);
   		departmentDropdown.setFont(UIFont);
        lc.gridx = 1; lc.gridy = 5;
        lc.gridwidth = 3; lc.gridheight = 1;
        lc.fill = GridBagConstraints.BOTH;
        lc.weightx = 1.0; lc.weighty = 0.0;
        layout.setConstraints(departmentDropdown, lc);
   		getContentPane().add(departmentDropdown);
        



		// Add the Update button
		updateButton = new JButton("UPDATE");
        lc.gridx = 1; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(updateButton, lc);
   		getContentPane().add(updateButton);
        
   	// Add the ADD button
   		addButton = new JButton("ADD");
   	    lc.gridx = 0; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
   	    layout.setConstraints(addButton, lc);
   		getContentPane().add(addButton);
   	        
   		
		// Add the Delete button
		deleteButton = new JButton("DELETE");
        lc.gridx = 2; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(deleteButton, lc);
   		getContentPane().add(deleteButton);

   		// Add the Cancel button
		cancelButton = new JButton("CANCEL");
        lc.gridx = 3; lc.gridy = 6;
        lc.gridwidth = 1; lc.gridheight = 1;
        lc.weightx = 0.0; lc.weighty = 0.0;
        layout.setConstraints(cancelButton, lc);
   		getContentPane().add(cancelButton);
		
		
	}


	private void updateButtonClicked(){
		
		theEmployee.setofficeLocation(officeLocationField.getText());
		theEmployee.setFirstName(firstNameField.getText());
		theEmployee.setLastName(lastNameField.getText());
		theEmployee.setphoneNumber(phoneNumberField.getText());
		theEmployee.setStaffPosition(staffPositionField.getText());
		
		//Inform the dialog client that the dialog finished
		
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.UPDATE);
		
		//Make the dialog go away
		
		dispose();


	}
	
	private void addButtonClicked()	{
		theEmployee.setofficeLocation(officeLocationField.getText());
		theEmployee.setFirstName(firstNameField.getText());
		theEmployee.setLastName(lastNameField.getText());
		theEmployee.setphoneNumber(phoneNumberField.getText());
		theEmployee.setStaffPosition(staffPositionField.getText());
		
		
		//Inform the dialog client that the dialog finished
		
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.ADD); //CHANGE TO ADD
		
		//Make the dialog go away
		
		dispose();
		
	}
	
	private void deleteButtonClicked(){
		theEmployee.setofficeLocation(officeLocationField.getText());
		theEmployee.setFirstName(firstNameField.getText());
		theEmployee.setLastName(lastNameField.getText());
		theEmployee.setphoneNumber(phoneNumberField.getText());
		theEmployee.setStaffPosition(staffPositionField.getText());
		
		//Inform the dialog client that the dialog finished
		
		if (theDialogClient != null) theDialogClient.dialogFinished(DialogClient.operation.DELETE);
		
		//Make the dialog go away
		
		dispose();


	}

	private void cancelButtonClicked(){
		
		//Inform the dialog client that the dialog finished
		
		if (theDialogClient != null) theDialogClient.dialogCancelled();

		//Make the dialog go away

		dispose();



	}
}