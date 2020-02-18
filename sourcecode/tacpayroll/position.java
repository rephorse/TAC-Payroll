package tacpayroll;

/**
 * Interface of the program
 * Creates the frame, panes, buttons, textfields of the program for user interaction
 * 03/31/2019
 * 04/07/2019
 * Accepted by Arnold Low, Caleb Rigg, Tasso Klassen
 * Arguments: all variables, or nothing
 * Variables:(Are named and described below)
 * Shows a frame with all options and functions discussed
 * Holds information of a position and can update or delete a position
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class position {
	
private int posid; //id of position
private int depid; //associated id of department

private String posname; //name of position
private String posdes;	//description of position
private String depname;	//name of department
private String posrole;	//role of position
private String posperiod;	//period, if full time, part time, etc

private databaseHandler db;	//connection to database

/**
 * Constructor for the position
 * @param posid(int)
 * @param posname(String)
 * @param posdes(String)
 * @param depname(String)
 * @param depid(int)
 * @param posrole(String)
 * @param posperiod(String)
 */
public position(int posid,String posname, String posdes, String depname, int depid,String posrole,String posperiod) {
	this.posid=posid;
	this.posname=posname;
	this.posdes=posdes;
	this.depname=depname;
	this.posrole=posrole;
	this.posperiod=posperiod;
}

//Empty constructor method for a position
public position() {
	posid=0;
}

/**
 * Getter and Setter methods
 */
public String getName() {
	return posname;
}
public String getDepartment() {
	return depname;
}
public int getDepID() {
	return depid;
}
public String posdes() {
	return posdes;
}
public int getID() {
	return posid;
}
public String toString() {
	return posname;
}
public String getRole() {
	return posrole;
}
public String getPeriod() {
	return posperiod;
}

/**
 * Gets the position with a unique position id
 * @param posid(int)
 */
public void query(int posid) {
	//query
	String query="select posname,posdes, depname,position.depid,posrole,posperiod from position "
			+ "left join Department on position.depid=Department.depid where posid="+posid+";";
	db = new databaseHandler(); //connects to database
	ResultSet set=db.getSData(query); //executes query and stores in set
	try {
		this.posid=posid;
		while(set.next()) {
		this.posname=set.getString("posname");
		this.posdes=set.getString("posdes");
		this.depid=set.getInt("depid");
		this.depname=set.getString("depname");
		this.posrole=set.getString("posrole");
		this.posperiod=set.getString("posperiod");
		
		}
	} catch (SQLException e1) {
		// Shows error if query is not successfull
		JOptionPane.showMessageDialog(null, "Database Failure", "Error", 0);
	}
	db.close(); //closes the database
}

/**
 * Deletes the currently selected position from the database
 * @return boolean true if successful or false if not
 */
public boolean deletePos() {
	db = new databaseHandler(); //connects to database
	if(db.submitData("delete from position where posid="+this.posid+";")) {
		db.close();
		return true;
	}
	else {
		db.close();
		return false;
	}
}

/**
 * Deletes all positions for one department
 * @param depid(int)
 * @returns false if not successful
 */
public boolean deleteDep(int depid) {
	db = new databaseHandler();
	return db.submitData("delete from position where depid="+depid+";");
}
}
