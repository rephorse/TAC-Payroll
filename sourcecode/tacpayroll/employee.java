package tacpayroll;

/**
 * Interface of the program
 * Creates the frame, panes, buttons, textfields of the program for user interaction
 * 03/31/2019
 * 04/07/2019
 * Accepted by Arnold Low, Caleb Rigg, Tasso Klassen
 * Arguments: All variables or the employee id
 * Variables:(Are named and described below)
 * Holds all variables of the employee and can query and submit a specific employee
 * Shows an error if can't connect to database
 */

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class employee {
	//variables for an employee
private String firstname;
private String lastname;
private String seniority;
private String position;
private String department;

private int empid;
private int salary;
private int level;
private int posid;
private int depid;
private int payperiods;
private int taxid;

private Date dateofBirth;
private Date startemp;
private Date endemp;
private databaseHandler db;
private taxkey tax;


//constructor for an employee
public employee(int empid,String firstname, String lastname, int posid, Date dateofBirth, Date startemp, Date endemp,String position, String department, String seniority) {
	this.empid=empid;
	this.firstname=firstname;
	this.lastname=lastname;
	this.dateofBirth=dateofBirth;
	this.startemp=startemp;
	this.endemp=endemp;
	this.position=position;
	this.department=department;
	this.seniority=seniority;
}

/**
 * Constructor for an employee based on the empid of the employee, which then is queried from the database
 * @param empid as an int
 */
public employee(int empid){
	//String that holds the query for the database
	String query="select empid,empfirstname,emplastname,empdateofbirth, "
			+ "empstartdate,empenddate,Employee.level,payperiods,Employee.seniority, "
			+ "position.posname,Department.depname, SalaryKey.salary,SalaryKey.taxid,"
			+ "position.posid,Department.depid from Employee left join position on "
			+ "Employee.posid=position.posid left join Department on Employee.depid=Department.depid "
			+ "left join SalaryKey on Employee.level=SalaryKey.level and Employee.seniority=SalaryKey.seniority where empid="+empid+";";
	db = new databaseHandler(); //new database connection is initiated
	ResultSet set=db.getSData(query); //the data is queried
	//the data is loaded into the variables of the class. 
	try {
		while(set.next()) {
		this.empid=set.getInt("empid");
		this.firstname=set.getString("empfirstname");
		this.lastname=set.getString("emplastname");
		this.dateofBirth=set.getDate("empdateofbirth");
		this.startemp=set.getDate("empstartdate");
		this.endemp=set.getDate("empenddate");
		this.position=set.getString("posname");
		this.department=set.getString("depname");
		this.level=set.getInt("level");
		this.seniority=set.getString("seniority");
		this.salary=set.getInt("salary");
		this.payperiods = set.getInt("payperiods");
		this.taxid = set.getInt("taxid");
		this.posid=set.getInt("posid");
		this.depid=set.getInt("depid");
		}
	} catch (SQLException e1) {
		//If connection to the database was not successfull
		JOptionPane.showMessageDialog(null, "Database Failure", "Error", 0);
	}
	db.close();
}

/**
 * Different getter and setter methods to return the necessary variables from the classss
 */
public int getID() {
	return empid;
}
public String getFirstName() {
	return firstname;
}
public String getLastName() {
	return lastname;
}
public String getDateOfBirth() {
	return dateofBirth.toString(); //returns a string version of the date of birth
}
public String getPos() {
	return position;
}
public String getSeniority() {
	return seniority;
}
public int getPayPeriods() {
	return this.payperiods;
}
public int getLevel() {
	return level;
}

/**
 * @returns the full name of the employee
 */
public String getName() {
	return firstname + " " + lastname;
}
public int getSalary() {
	return salary;
}
public String getDep() {
	return department;
}
public int getDepId() {
	return depid;
}
public String getTaxSlip() {
	tax=new taxkey(this.taxid);
	return tax.computeTaxes(this.salary/this.payperiods); //computes and returns the taxes of the current employee
}

/**
 * Checks if the start date of the employee is there, if not return an empty string
 * @returns the start date of employment as a string
 */
public String getEmpStart() {
	if(startemp==null) {
		return "";
	}
	return startemp.toString();
}
public int getPosID() {
	return posid;
}

/**
 * updates the corresponding fields in the database for an employee
 * @param level(int)
 * @param seniority(int)
 * @param firstname(String)
 * @param lastname(String)
 * @param dateofBirth(String)
 * @param startemp(String)
 * @param endemp(String)
 */
public void updateDB(int level, String seniority, String firstname,String lastname, String dateofBirth, String startemp, String endemp) {

	StringBuilder ins = new StringBuilder();
	db = new databaseHandler();
	ins.append("update Employee set ");
	ins.append("level=\'");
	ins.append(level);
	ins.append("\',");
	ins.append("seniority=\'");
	ins.append(seniority);
	ins.append("\',");
	ins.append("empfirstname=\'");
	ins.append(firstname);
	ins.append("\',");
	ins.append("emplastname=\'");
	ins.append(lastname);
	ins.append("\',");
	ins.append("empdateofbirth=\'");
	ins.append(dateofBirth);
	ins.append("\',");
	ins.append("empstartdate=\'");
	ins.append(startemp);
	ins.append("\' where empid=\'");
	ins.append(this.empid);
	ins.append("\';");
	System.out.println(ins.toString());
	db.submitData(ins.toString());
	db.close();
}

/**
 * updates the department of an employee
 * @param depid(int)
 */
public void updateDepartment(int depid) {
	db = new databaseHandler();
	db.submitData("update Employee set depid=" + depid + " where empid=" + empid + ";");
	db.close();
}

/**
 * updates the position of an employee
 * @param posid(int)
 */
public void updatePosition(int posid) {
	db = new databaseHandler();
	db.submitData("update Employee set posid="+posid+" where empid=" + empid + ";");
	db.close();
}
}
