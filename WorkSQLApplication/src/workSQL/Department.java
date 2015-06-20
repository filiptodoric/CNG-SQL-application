package workSQL;

public class Department {
	/*
	 * This class represents a Department
	 */
	
	private String departmentName; //string code for department
	//private String departmentNumber;
	public Department(String aDepartment){
		departmentName = aDepartment;
		//departmentNumber = aDepartmentNumber;
	}
	
	public String getDepartmentName() {return departmentName;}
	//public String getdepartmentNumber() {return departmentNumber;}
	
	public String toString(){
		return "" + departmentName + " "; //+  title;
	}

}
