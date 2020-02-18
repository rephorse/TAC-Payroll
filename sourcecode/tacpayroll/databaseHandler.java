package tacpayroll;

/**
 * Interface of the program
 * Creates the frame, panes, buttons, textfields of the program for user interaction
 * 04/06/2019
 * 04/07/2019
 * Accepted by Arnold Low, Caleb Rigg, Tasso Klassen
 * Arguments: None
 * Variables:(Are named and described below)
 * Connects to an embedded database
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class databaseHandler {

private Connection connect; //holds the connection to database
private boolean success; //to check if connection was successfull
private Statement statement;	//holds and executes statement
private ResultSet resultSet;	//holds results queried from database
private ArrayList<departments> department;		//holds list of departments
private ArrayList<position> position;		//holds list of positions
/**
 * Constructor for the databasehandler;
 */
public databaseHandler(){

	 // Trys to establish a connection and throws an error if not possible
	try {
	//connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll?user=root&password=test");
	this.connect = DriverManager.getConnection("jdbc:h2:./payrolldatabase"); //embeded database, no need for password or user
	this.statement = connect.createStatement();
	success=true;
}catch(Exception e) {
	success=false;
}}

/**
 * True if able to connect to database, false if not
 * @return boolean
 */
public boolean getSuccess() {
	return this.success;
}

/**
 * queries the submitted string to database as a resultset
 * @param String query
 * @returns ResultSet
 */
public ResultSet getSData(String query) {
	try {
		resultSet = statement.executeQuery(query);
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return resultSet;
}

/**
 * Gets the list of current departments in the database as an arraylist.
 * @return arrayList<departments>
 */
public ArrayList<departments> getDData(){
	try {
		this.resultSet = statement.executeQuery("select * from Department"); //executes query
		this.department = new ArrayList<>(); //new arraylist
		while(resultSet.next()) {
			department.add(new departments(resultSet.getInt("depid"),resultSet.getString("depname"),resultSet.getInt("dephead")));
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return department;
}

/**
 * queries the position based on the departments id
 * @param depid
 * @return Arraylist<position>
 */
public ArrayList<position> getDynamicPData(int depid){
	try {
		this.position = new ArrayList<>();
		resultSet = statement.executeQuery("select posid, posname, posdes, depname,position.depid,posrole,posperiod from position left join Department on position.depid=Department.depid where position.depid="+depid+";");
		while(resultSet.next()) {
				position.add(new position(resultSet.getInt("posid"),resultSet.getString("posname"),resultSet.getString("posdes"),resultSet.getString("depname"),resultSet.getInt("depid"),resultSet.getString("posrole"),resultSet.getString("posperiod")));
		}
	}
	catch(Exception e) {
		e.printStackTrace();
	}
	return position;
}

/**
 * used to submit data to the database and returns true if submitted
 * @param insert as a String
 * @returns boolean
 */
public boolean submitData(String insert) {
	try {
		statement.executeUpdate(insert);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		return false;
	}
	return true;
}

/**
 * Creates the tabke if not exists
 * @return
 */
public int createTable() {
	String taxkey = "CREATE TABLE IF NOT EXISTS TaxKey (\r\n" + 
			"Taxid INT NOT NULL AUTO_INCREMENT,\r\n" + 
			"Year YEAR,\r\n" + 
			"Startsalary int,\r\n" + 
			"Endsalary int,\r\n" + 
			"Income INT,\r\n" + 
			"CPP INT,\r\n" + 
			"EI INT,\r\n" + 
			"QPP INT,\r\n" + 
			"PRIMARY KEY (Taxid)\r\n" + 
			");";
	String salarykey = "CREATE TABLE IF NOT EXISTS SalaryKey(\r\n" + 
			"Level INT NOT NULL,\r\n" + 
			"Seniority CHAR NOT NULL,\r\n" + 
			"taxid int NOT NULL,\r\n" + 
			"Salary INT,\r\n" + 
			"Payperiods INT,\r\n" + 
			"PRIMARY KEY (Level, Seniority),\r\n" + 
			"Foreign Key (taxid) references taxkey (taxid)\r\n" + 
			");";
	String department = "CREATE TABLE IF NOT EXISTS Department(\r\n" + 
			"DepID INT NOT NULL AUTO_INCREMENT,\r\n" + 
			"DepName VARCHAR(30) NOT NULL,\r\n" + 
			"DepHead INT,\r\n" + 
			"PRIMARY KEY (DepID)\r\n" + 
			");";
	String position = "CREATE TABLE IF NOT EXISTS Position (\r\n" + 
			"PosID INT NOT NULL AUTO_INCREMENT,\r\n" + 
			"PosName VARCHAR(30) NOT NULL,\r\n" + 
			"PosDes VARCHAR(255),\r\n" + 
			"depid INT,\r\n" + 
			"posrole VARCHAR(255),\r\n" +
			"posperiod VARCHAR(255),\r\n" +
			"PRIMARY KEY (PosID),\r\n" + 
			"FOREIGN KEY(depid) REFERENCES Department(depid)\r\n" + 
			");";
	String employee = "CREATE TABLE IF NOT EXISTS Employee(\r\n" + 
			"EmpID INT NOT NULL AUTO_INCREMENT,\r\n" + 
			"DepID INT,\r\n" + 
			"PosID INT,\r\n" + 
			"level int,\r\n" + 
			"seniority char,\r\n" + 
			"EmpFirstName VARCHAR(30) NOT NULL,\r\n" + 
			"EmpLastName VARCHAR(30) NOT NULL,\r\n" + 
			"EmpDateOfBirth DATE,\r\n" + 
			"EmpStartDate DATE,\r\n" + 
			"EmpEndDate DATE,\r\n" + 
			"PRIMARY KEY(EmpID),\r\n" + 
			"FOREIGN KEY(DepID) REFERENCES Department (DepID),\r\n" + 
			"FOREIGN KEY(PosID) REFERENCES Position (PosID),\r\n" + 
			"FOREIGN KEY (level,seniority) REFERENCES salarykey(level,seniority)\r\n" + 
			");";
	String salaryHistory = "CREATE TABLE IF NOT EXISTS SalaryHistory(\r\n" + 
			"EmpID INT,\r\n" + 
			"YearID YEAR,\r\n" + 
			"PosID INT,\r\n" + 
			"StartDate DATE,\r\n" + 
			"Salary INT,\r\n" + 
			"PRIMARY KEY (YearID),\r\n" + 
			"FOREIGN KEY (EmpID) REFERENCES Employee (EmpID),\r\n" + 
			"FOREIGN KEY (PosID) REFERENCES Position (PosID)\r\n" + 
			");";
	try {
		statement.executeUpdate(taxkey);
		statement.executeUpdate(salarykey);
		statement.executeUpdate(department);
		statement.executeUpdate(position);
		statement.executeUpdate(employee);
		statement.executeUpdate(salaryHistory);
		
		//statement.executeUpdate(insert);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return 0;
	
}

/**
 * Closes the connection to the database to remove clutter
 */
public void close() {
	try {
		connect.close();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
