package workSQL;

public class Department {
	/*
	 * This class represents a Department
	 */
	
	private String departmentName; //string code for department
	
	public Department(String aDepartment, String aDepartmentNumber){
		departmentName = aDepartment;
		//departmentNumber = aDepartmentNumber;
	}
	
	public String getBookCode() {return departmentName;}
	//public String getdepartmentNumber() {return departmentNumber;}
	
	public String toString(){
		return "" + departmentName + " "; //+  title;
	}

}
