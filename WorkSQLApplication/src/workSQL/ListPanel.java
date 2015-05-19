package workSQL;

import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

// This is the Panel that contains represents the view of the
// Music Store

public class ListPanel extends JPanel {

	// These are the components
	private JButton		searchButton;
	private JButton		addButton;
	private JTextField  searchText;
	private JList		departmentList;
	public JTable		employeeList;
	public static final String[] columnNames = {
		"Employee ID", "First Name", "Last Name", "Office Location", "Phone Extension"
	};

	private Font UIFont = new Font("Times New Roman", Font.BOLD, 18); //Courier New


	// These are the get methods that are used to access the components
	public JButton getSearchButton() { return searchButton; }
	public JButton getAddButton() { return addButton; }
	public JList getdepartmentList() { return departmentList; }
	public JTable getEmployeeList() { return employeeList; }
	public JTextField getSearchText() { return searchText; }




	// This is the default constructor
	public ListPanel(){
		super();

		// Use a GridBagLayout (lotsa fun)
		GridBagLayout layout = new GridBagLayout();
		GridBagConstraints layoutConstraints = new GridBagConstraints();
		setLayout(layout);

		// Add the departmentList list
		departmentList = new JList();
		departmentList.setFont(UIFont);
		departmentList.setPrototypeCellValue("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		JScrollPane scrollPane = new JScrollPane( departmentList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 0;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 5;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 1.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);

		// Add the Add button
		searchText = new JTextField("");
		searchText.setFont(UIFont);

		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 1.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchText, layoutConstraints);
		add(searchText);

		// Add the Remove button
		searchButton = new JButton("Search");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 0;
		layoutConstraints.gridwidth = 1;
		layoutConstraints.gridheight = 1;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.EAST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(searchButton, layoutConstraints);
		add(searchButton);


		// Add the add button
		addButton = new JButton("Add");
		layoutConstraints.gridx = 2;
		layoutConstraints.gridy = 5;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.gridheight = 5;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.WEST;
		layoutConstraints.weightx = 0.0;
		layoutConstraints.weighty = 0.0;
		layout.setConstraints(addButton, layoutConstraints);
		add(addButton);

		
		// Add the employeeList list
		employeeList = new JTable();

		employeeList.setFont(UIFont);

		scrollPane = new JScrollPane( employeeList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		layoutConstraints.gridx = 1;
		layoutConstraints.gridy = 1;
		layoutConstraints.gridwidth = 2;
		layoutConstraints.gridheight = 4;
		layoutConstraints.fill = GridBagConstraints.BOTH;
		layoutConstraints.insets = new Insets(10, 10, 10, 10);
		layoutConstraints.anchor = GridBagConstraints.NORTHWEST;
		layoutConstraints.weightx = 2.0;
		layoutConstraints.weighty = 1.0;
		layout.setConstraints(scrollPane, layoutConstraints);
		add(scrollPane);


	}
}