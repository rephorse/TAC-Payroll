package tacpayroll;

/**
 * Interface of the program
 * Creates the frame, panes, buttons, textfields of the program for user interaction
 * 03/31/2019
 * 04/07/2019
 * Accepted by Arnold Low, Caleb Rigg, Tasso Klassen
 * Arguments: depid, depname, dephead
 * Variables:(Are named and described below)
 * Holds the information for an department and queries a specific department is needed
 * Informs if adding a department was successfull or not
 */

public class departments {

	private int depid;	//id of the department
	private int dephead; //head of department
	
	private String depname; //name of the department

	private databaseHandler db; //database connection
	
	/**
	 * Constructor for an department
	 * @param depid(int)
	 * @param depname(String)
	 * @param dephead(int)
	 */
	public departments(int depid, String depname, int dephead) {
		this.depid=depid;
		this.depname=depname;
		this.dephead=dephead;
	}
	
	/**
	 * Getter methods for the class
	 */
	public String getName() {
		return this.depname;
	}
	public int getdepID() {
		return depid;
	}
	public int getdepHead() {
		return dephead;
	}
	
	/**
	 * Setter Method for the class
	 * @param depname
	 */
	public void setName(String depname) {
		this.depname=depname;
	}
	public void setdepID(int depid) {
		this.depid=depid;
	}
	public void setdepHead(int dephead) {
		this.dephead=dephead;
	}
	
	/**
	 * the toString override method for the department class
	 */
	public String toString() {
		return depname;
	}
	
	/**
	 * Deletes a Department from the Database. If the department has positions associated with it, those are eliminated as well
	 * If the deletion is not possible, the method returns false
	 * @return boolean
	 */
	public boolean deleteDep() {
		db = new databaseHandler(); //new connection to database
		position placeholder = new position();//to delete associated positions
		if(placeholder.deleteDep(this.depid)&&db.submitData("delete from department where depid="+this.depid+";")){
			db.close();
			return true;
		}
		else {
			db.close();
			return false;
		}
		
	}
}

