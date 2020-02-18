package tacpayroll;
/**
 * Interface of the program
 * Creates the frame, panes, buttons, textfields of the program for user interaction
 * 04/06/2019
 * 04/07/2019
 * Accepted by Arnold Low, Caleb Rigg, Tasso Klassen
 * Arguments: Tax ID
 * Variables:(Are named and described below)
 * Shows a frame with all options and functions discussed
 * Handles most bad input or if database is not available
 */
import java.sql.ResultSet;
import java.sql.SQLException;

public class taxkey {
	/**
	 * Variables for the taxkey class
	 */

private int taxid;
private int year;
private int startsalary;
private int endsalary;
private int income;
private int cpp;
private int ei;
private int qpp;
private databaseHandler db;

/**
 * Constructor based on a selected taxid
 * @param taxid(int)
 */
public taxkey(int taxid){
	String query="select * from TaxKey where taxid="+taxid+";"; //query to get the taxkey
	db = new databaseHandler(); //new connection to database
	ResultSet set=db.getSData(query); //executes query
	try {
		while(set.next()) {
		this.taxid=set.getInt("taxid");
		this.year=set.getInt("year");
		this.startsalary=set.getInt("startsalary");
		this.endsalary=set.getInt("endsalary");
		this.income=set.getInt("income");
		this.cpp=set.getInt("cpp");
		this.ei=set.getInt("ei");
		this.qpp=set.getInt("qpp");
		}
	} catch (SQLException e1) {
		e1.printStackTrace();
	}
	db.close();
}

/**
 * Queries the necessary information to compute the taxes for a salary. Generates a string with the pay information
 * @param Salary
 * @returns a String
 */
public String computeTaxes(int Salary) {
	int columns=4;
	int rows=2;
	this.income=income*Salary/100; //computes the income tax
	this.cpp=cpp*Salary/100; 		//computes the cpp
	this.ei=ei*Salary/100;			//computes the ei
	this.qpp=qpp*Salary/100;		//computes the qpp
	int netpay=Salary-income-ei-cpp-qpp; //computes net pay
	String[][] taxes=new String[columns][rows]; //Creates a String array with the information of the taxes
	taxes[0][0]= "Income Tax";
	taxes[1][0]= "CPP";
	taxes[2][0]= "EI";
	taxes[3][0]= "QPP";
	taxes[0][1]= Integer.toString(income);
	taxes[1][1]= Integer.toString(cpp);
	taxes[2][1]= Integer.toString(ei);
	taxes[3][1]= Integer.toString(qpp);
	return "Gross Pay = " + Salary + " | " + taxes[0][0] + " = " + taxes[0][1] + " | " + taxes[1][0]+ " = " +taxes[1][1] + "\n " 
			+"Net Pay = " + netpay + " | " + taxes[2][0] + " = " + taxes[2][1] + " | " + taxes[3][0]+ " = " +taxes[3][1] ;

}

}
