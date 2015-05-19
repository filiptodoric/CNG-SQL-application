package workSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;



public class javaWithSQliteMain {
	public static void FillTable(JTable table, ResultSet set)
	{
		ResultSet rs = set;
		try
		{


			//To remove previously added rows
			while(table.getRowCount() > 0) 
			{
				((DefaultTableModel) table.getModel()).removeRow(0);
			}
			int columns = rs.getMetaData().getColumnCount();
			while(rs.next())
			{  
				Object[] row = new Object[columns];
				for (int i = 1; i <= columns; i++)
				{  
					row[i - 1] = rs.getObject(i);
				}
				((DefaultTableModel) table.getModel()).insertRow(rs.getRow()-1,row);
			}

			rs.close();

		}
		catch(SQLException e)
		{
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Carleton Universty Employee Database");
		GUI frame = null;


		//Connect to database
		try {
			String url = "jdbc:sqlserver://FILIPTODORICPC:1434;databaseName=Vodigi;integratedSecurity=true"; //;user=filip;password=snowman";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

			/*  Create connection to a database in the project home directory.
			 *  HARD CODED DATABASE NAME -- NEED TO CHANGE TO BE GENERAL
			 */
			Connection database = DriverManager.getConnection(url);
			Statement stat = database.createStatement();

			String sqlQueryString = "select * from dbo.departments;";
			System.out.println(sqlQueryString);

			ArrayList<Department> departmentList = new ArrayList<Department>();
			ResultSet rs = stat.executeQuery(sqlQueryString);
			while (rs.next()) {
				Department department = new Department(rs.getString("Name"), rs.getString("DepartmentID"));
				departmentList.add(department);
			}
			
			rs.close();

			sqlQueryString = "select * from dbo.staff;";
			rs = stat.executeQuery(sqlQueryString);
			System.out.println(sqlQueryString);
			

			ArrayList<Employee> employeeSearchResults = new ArrayList<Employee>();

			int DISPLAY_LIMIT = 100;
			int count = 0;
			while (rs.next() && count < DISPLAY_LIMIT){
				Employee employee = new Employee(
						rs.getInt("STaffID"),
						rs.getString("FirstName"),
						rs.getString("LastName"),
						rs.getString("PhoneNumber"),
						rs.getString("OfficeLocation")
						);
				employeeSearchResults.add(employee);
				count++;
			}
			

			Employee[] employeeArray = new Employee[1]; //just to establish array type
			employeeArray =  employeeSearchResults.toArray(employeeArray);

			//Create GUI with knowledge of database and initial query content
			frame =  new GUI("Carleton Universty Employee Database", database, stat, departmentList, employeeSearchResults); //create GUI frame with knowledge of the database
			FillTable(frame.view.employeeList, rs);
			//Leave it to GUI to close database
			//conn.close(); //close connection to database			
			rs.close(); //close the query result table

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
