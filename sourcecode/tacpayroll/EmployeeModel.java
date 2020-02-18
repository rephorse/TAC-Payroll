package tacpayroll;

/**
 * Interface of the program
 * Creates the frame, panes, buttons, textfields of the program for user interaction
 * 04/06/2019
 * 04/07/2019
 * Accepted by Arnold Low, Caleb Rigg, Tasso Klassen
 * Arguments: None
 * Variables:(Are named and described below)
 * Creates a model for the list of employees that need to be displayed
 * Shows message if database can't be accessed
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class EmployeeModel extends AbstractTableModel implements TableModelListener{

	/**
	 * Creates a model to display all employees in the database
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames={"ID","First Name","Last Name","Department", "Position","Salary"}; //Names of columns
	private ArrayList<String[]> data; //holds the data for the model
	private String[] placeHolder; //holds the data while querying
	databaseHandler db; //database connector
	
	/**
	 * Constructor for the model queries the data and puts it in an arraylist.
	 */
	public EmployeeModel() {
		db = new databaseHandler();
		data = new ArrayList<String[]>(); //initializes the arraylist
		ResultSet databs = db.getSData("select empid,empfirstname, emplastname,Department.depname,position.posname,SalaryKey.salary from Employee left join Department on Employee.depid=Department.depid left join position on Employee.posid=position.posid left join SalaryKey on Employee.level=SalaryKey.level and Employee.seniority=SalaryKey.seniority order by empid;");
		//loads the data into the arraylist
		try {
			placeHolder = new String[columnNames.length];
			while(databs.next()) {
				placeHolder[0] = databs.getString("empid");
				placeHolder[1] = databs.getString("empfirstname");
				placeHolder[2] = databs.getString("emplastname");
				placeHolder[3] = databs.getString("depname");
				placeHolder[4] = databs.getString("posname");
				placeHolder[5] = databs.getString("salary");
				
				data.add(placeHolder.clone());//adds a clone of the 
			}
		} catch (SQLException e) {
			// If there is a problem with the sql query
			JOptionPane.showMessageDialog(null, "Database Failure", "Error", 0);
		}	
			db.close(); //closes the database connection
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		//makes a cell editable or not.
		return false;
	}
	@Override
	public String getColumnName(int index) {
		return columnNames[index];
	}
	@Override
	public int getColumnCount() {
		// returns the amount of columns based on the columns
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// returns the size of the table
		return data.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// returns the specific value of the table
		return data.get(rowIndex)[columnIndex];
		
	}
	
	@Override
	public void tableChanged(TableModelEvent arg0) {
		// does nothing
		
	}

}
