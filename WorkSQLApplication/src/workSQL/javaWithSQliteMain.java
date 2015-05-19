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



public class javaWithSQliteMain {

    
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Carleton Universty Employee Database");
		GUI frame = null;
		

		//Connect to database
        try {
        	//user=filip;password=snowman
        	String url = "jdbc:sqlserver://FILIPTODORICPC:1434;databaseName=master;integratedSecurity=true"; //;user=filip;password=snowman";
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

	            String sqlQueryString = "select * from department;"; // order by code asc
	            System.out.println("");
	            System.out.println(sqlQueryString);

	            ArrayList<Department> departmentList = new ArrayList<Department>();

		        ResultSet rs = stat.executeQuery(sqlQueryString);
		        while (rs.next()) {
		            Department department = new Department(rs.getString("departmentName"), rs.getString("departmentNumber"));
		            departmentList.add(department);
		        }
		        rs.close(); //close the query result table
		        


	            sqlQueryString = "select * from employees;";
		        rs = stat.executeQuery(sqlQueryString);
	            System.out.println("");
	            System.out.println(sqlQueryString);
	            
	            ArrayList<Employee> employeeSearchResults = new ArrayList<Employee>();

		        int DISPLAY_LIMIT = 100;
		        int count = 0;
		        while (rs.next() && count < DISPLAY_LIMIT){
		            
		            Employee employee = new Employee(
		            		rs.getInt("employeeNumber"),
		            		rs.getString("employeeName"),
		            		rs.getString("phoneNumber"),
		            		rs.getString("officeLocation")
		            		);
		            employeeSearchResults.add(employee);
	            count++;
		        }
		        
		        rs.close(); //close the query result table
		        
		        Employee[] employeeArray = new Employee[1]; //just to establish array type
		        employeeArray =  employeeSearchResults.toArray(employeeArray);
				
		        //Create GUI with knowledge of database and initial query content
		        frame =  new GUI("Carleton Universty Employee Database", database, stat, departmentList, employeeSearchResults); //create GUI frame with knowledge of the database
		        
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
