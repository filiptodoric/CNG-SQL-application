package workSQL;

public class Employee {
	/*
	 * This class represents a employee that appears in a department database
	 */
	
	private int employeeNumber; //database table primary key id
	private String employeeFirstName; //string name of employee
	private String employeeLastName;
	private String phoneNumber; //phone number of the employee
	private String officeLocation; //string location of office
	
	public Employee(int empNum, String fName, String lName, String aNum, String aOffice){
		employeeNumber = empNum;
		employeeFirstName = fName;
		employeeLastName = lName;
		phoneNumber = aNum;
		officeLocation = aOffice;
	}
	
	public int getemployeeNumber() {return employeeNumber;}
	public String getemployeeFirstName() {return employeeFirstName;}
	public String getemployeeLastName() {return employeeLastName;}
	public String getphoneNumber() {return phoneNumber;}
	public String getofficeLocation() {return officeLocation;}
	
	public void setemployeeNumber(int empNum)	{employeeNumber = empNum;}
	public void setofficeLocation(String aOffice) {officeLocation = aOffice;}
	public void setemployeeFirstName(String fName) {employeeLastName = fName;}
	public void setemployeeLastName(String lName) {employeeLastName = lName;}
	public void setphoneNumber(String aNum) {phoneNumber = aNum;}
	
	public String toString(){
		
		String keyIndent = "";
		int max_key_digits = 11;
		for(int i=0; i< max_key_digits-(""+employeeNumber).length(); i++ ) keyIndent = keyIndent + " ";
		
		String pageIndent = "";
		int max_page_digits = 10;
		for(int i=0; i< max_page_digits-(""+phoneNumber).length(); i++ ) pageIndent = pageIndent + " ";
		

		String nameIndent = "";
		int max_name_digits = 20;
		//for(int i=0; i< max_name_digits-(""+employeeName).length(); i++ ) nameIndent = nameIndent + " ";
		
		return "" + employeeNumber + keyIndent + employeeFirstName + nameIndent + employeeLastName + phoneNumber + pageIndent + officeLocation;
	}

}
