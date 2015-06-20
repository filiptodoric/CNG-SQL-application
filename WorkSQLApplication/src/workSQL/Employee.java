package workSQL;

public class Employee {
	/*
	 * This class represents a employee that appears in a department database
	 */
	
	private int    employeeNumber; //database table primary key id
	private String firstName; //string name of employee
	private String lastName;
	private String phoneNumber; //phone number of the employee
	private String officeLocation; //string location of office
	private String staffPosition;
	
	public Employee(int empNum, String aName, String lName, String aNum, String aOffice, String aPosition){
		employeeNumber = empNum;
		firstName = aName;
		lastName = lName;
		phoneNumber = aNum;
		officeLocation = aOffice;
		staffPosition = aPosition;
	}
	
	public int getemployeeNumber() 		{return employeeNumber;}
	public String getFirstName() 		{return firstName;}
	public String getLastName() 		{return lastName;}
	public String getphoneNumber() 		{return phoneNumber;}
	public String getofficeLocation() 	{return officeLocation;}
	public String getStaffPosition()	{ return staffPosition;	}
	
	public void setemployeeNumber(int empNum)		{employeeNumber = empNum;}
	public void setofficeLocation(String aOffice) 	{officeLocation = aOffice;}
	public void setFirstName(String aName) 			{firstName = aName;}
	public void setLastName(String lName) 			{lastName = lName;}
	public void setphoneNumber(String aNum) 		{phoneNumber = aNum;}
	public void setStaffPosition(String aPosition)	{staffPosition = aPosition;}
	
	
}
