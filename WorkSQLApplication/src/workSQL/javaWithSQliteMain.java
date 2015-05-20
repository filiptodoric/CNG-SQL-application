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
		// TODO Auto-generated method stub
		System.out.println("Carleton Universty Employee Database");
		GUI frame = null;
		

		//Connect to database
        try {
        	//user=filip;password=snowman
        	String url = "jdbc:sqlserver://FILIPTODORICPC:1434;databaseName=Vodigi;integratedSecurity=true"; //;user=filip;password=snowman";
        	String username = "filip";
        	String password = "snowman";
        	
        	
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			
			//create connection to a database in the project home directory.
			//HARD CODED DATABASE NAME -- NEED TO CHANGE TO BE GENERAL
			System.out.println("i am here");
			Connection database = DriverManager.getConnection(url);// , username, password);
		       //create a statement object which will be used to relay a
		       //sql query to manipulate the database
		    Statement stat = database.createStatement();

                //Query database for initial contents for GUI at start up

	            String sqlQueryString = "select * from dbo.departments;"; // order by code asc
	            System.out.println("");
	            System.out.println(sqlQueryString);

	            ArrayList<Department> departmentList = new ArrayList<Department>();

		        ResultSet rs = stat.executeQuery(sqlQueryString);
		        while (rs.next()) {
		            Department department = new Department(rs.getString("Name"), rs.getString("DepartmentID"));
		            departmentList.add(department);
		        }
		        rs.close(); //close the query result table
		        


	            sqlQueryString = "select * from dbo.staff;";
	            String please = "select * from dbo.staff;";
		       
	            System.out.println("");
	            System.out.println(sqlQueryString);
	            
	            ArrayList<Employee> employeeSearchResults = new ArrayList<Employee>();
	            Vector<String> elements = new Vector<String>();
		        int DISPLAY_LIMIT = 100;
		        int count = 0;
		        
		        // new stuff
		        ResultSet work = stat.executeQuery(please);
		        //rs = stat.executeQuery(sqlQueryString);
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
		        
		        
		        rs = stat.executeQuery(sqlQueryString);
		        while (rs.next() && count < DISPLAY_LIMIT){
		            
		        	
		        	
		            Employee employee = new Employee(
		            		rs.getInt("StaffID"),
		            		rs.getString("FirstName"),
		            		rs.getString("PhoneNumber"),
		            		rs.getString("OfficeLocation")
		            		);
		            employeeSearchResults.add(employee);
	            count++;
		        }

		            

		        rs.close(); //close the query result table
		        
		        Employee[] employeeArray = new Employee[1]; //just to establish array type
		        employeeArray =  employeeSearchResults.toArray(employeeArray);
				
		        //Create GUI with knowledge of database and initial query content
		        frame =  new GUI("Carleton Universty Employee Database", database, stat, departmentList, employeeSearchResults, dtm); //create GUI frame with knowledge of the database
		        
		        //Leave it to GUI to close database
		        //conn.close(); //close connection to database			
												
			
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
