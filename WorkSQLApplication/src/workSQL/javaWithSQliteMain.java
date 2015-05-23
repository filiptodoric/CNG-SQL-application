package workSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;



public class javaWithSQliteMain {


	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		System.out.println("Carleton Universty Employee Database");
		GUI frame = null;
		try {
			/*
			 * The following is used to create a connection with the database server. 
			 */
			String url = "jdbc:sqlserver://FILIPTODORICPC:1434;databaseName=Vodigi;integratedSecurity=true"; 
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			Connection database = DriverManager.getConnection(url);
			Statement stat = database.createStatement();

			/*
			 * The following is for creating a list of departments. 
			 */
			String departmentQuery = "select Name from dbo.departments;"; 
			System.out.println(departmentQuery);
			ArrayList<Department> departmentList = new ArrayList<Department>();
			
			ResultSet rs = stat.executeQuery(departmentQuery);
			while (rs.next()) {
				Department department = new Department(rs.getString("Name"));
				departmentList.add(department);
			}
			for (Department s : departmentList) {
		        System.out.println(s);
		    } 
			rs.close(); 


			/*
			 * The following is for creating a JTable of staff.
			 */
			
			String staffQuery = "select * from dbo.staff;";
			System.out.println(staffQuery);
			ResultSet work = stat.executeQuery(staffQuery);
			ArrayList<Employee> employeeSearchResults = new ArrayList<Employee>();
			
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


			work = stat.executeQuery(staffQuery);
			while (work.next()){
				Employee employee = new Employee(
						work.getInt("StaffID"),
						work.getString("FirstName"),
						work.getString("PhoneNumber"),
						work.getString("OfficeLocation")
						);
				employeeSearchResults.add(employee);
			}
			work.close(); 

			Employee[] employeeArray = new Employee[1]; 
			employeeArray =  employeeSearchResults.toArray(employeeArray);
//			for (Employee s : employeeSearchResults) {
//		        System.out.println(s);
//		    } 

			/*
			 * The following creates the GUI
			 */
			frame =  new GUI("Carleton Universty Employee Database", database, stat, departmentList, employeeSearchResults, dtm);
		


		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//make GUI visible
		frame.setVisible(true);




	}

}
