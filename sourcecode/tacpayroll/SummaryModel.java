package tacpayroll;
/**
 * Interface of the program
 * Creates the frame, panes, buttons, textfields of the program for user interaction
 * 04/06/2019
 * 04/07/2019
 * Accepted by Arnold Low, Caleb Rigg, Tasso Klassen
 * Arguments: None
 * Variables:(Are named and described below)
 * Shows a frame with all options and functions discussed
 *Creates a model for the table that shows the number of employees and salary per department
 */
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class SummaryModel extends AbstractTableModel implements TableModelListener{

	/**
	 * Variables for the Summary model
	 */
	private static final long serialVersionUID = 1L;
	private String[] columnNames={"Department","# of Employees","Total Salary"};
	private ArrayList<String[]> data;
	private String[] placeholder;
	databaseHandler db;
	
	/**
	 * Constructor for the model of summary the displays the number of employees per department and the total salary per department
	 */
	public SummaryModel() {
		db = new databaseHandler(); //new connection
		data = new ArrayList<String[]>(); //initializes the data array
		
		//gets the data for the rows from the database
		ResultSet databs = db.getSData("select depname, count(*) as \"#\",sum(salary) as \"Total Salary\" from Employee left join Department on Employee.depid=Department.depid left join SalaryKey on Employee.level=SalaryKey.level and Employee.seniority=SalaryKey.seniority group by Employee.depid;");
		
		//puts the data into the arrayList
		try {
			placeholder = new String[columnNames.length];
			while(databs.next()) {
				placeholder[0] = databs.getString("depname");
				placeholder[1] = Integer.toString(databs.getInt("#"));
				placeholder[2] = Integer.toString(databs.getInt("Total Salary"));
				data.add(placeholder.clone());
			}
		} catch (SQLException e) {
			// If connection was unsuccessfull
			JOptionPane.showMessageDialog(null, "Database Failure", "Error", 0);
		}	
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// Controls if a cell is editable
		return false;
	}
	
	@Override
	public int getColumnCount() {
		// amount of columns is dependent on the amount of columns
		return columnNames.length;
	}

	@Override
	public int getRowCount() {
		// gets the size of an array as a rowcount
		return data.size();
	}
	@Override
	public String getColumnName(int index) {
		return columnNames[index];
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// returns the data at the specified 2d index.
		return data.get(rowIndex)[columnIndex];
		
	}
	
	@Override
	public void tableChanged(TableModelEvent e) {
		//does nothing
		
	}

}
