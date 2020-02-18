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
 * Handles most bad input or if database is not available
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLayeredPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.LineBorder;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.AbstractListModel;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.border.EtchedBorder;
import java.awt.Cursor;
import java.awt.Dimension;

public class mainInterface extends JFrame {

	/**
	 * The variables for the interface
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel addEmployeePane;	//Pane to add employee
	private JPanel employeeRecordsPane;	//Pane for employee records(not implemented)
	private JPanel salaryTablePane; //Pane for salary table (Not implemented)
	private JPanel allEmployeesPane; //Pane to display all employees
	private JPanel addPositionPane; //Pane to manage Position
	private JPanel addDepartmentPane;	//Pane to manage Department and change Empoyees department
	private JPanel printPaySlipPane;	//Pane to print payslip
	private JPanel employeeByDepPane;	//Pane to show # of employees per department
	private JPanel menuPane; 		//Manu to access different panes
	private JPanel welcomePane;	//Welcome screen with logo

	private JTable tblallEmployees;	//table to display all employees
	private JTable tblempByDep;	//table to show employees by department
	
	private JLayeredPane layeredPaneGeneral; //holds all panes
		
	//Textfields for add employee pane
	private JTextField txtEmpId;	
	private JTextField txtEmpFirstName;
	private JTextField txtEmpLastName;
	private JTextField txtEmpDateOfBirth;
	private JTextField txtEmpStartDate;
	private JTextField txtEmpEndDate;
	private JTextField txtEmpSeniority;
	private JTextField txtEmpLevel;

	//Textfields for add position Pane
	private JTextField txtposName;
	private JTextField txtposRole;
	private JTextField txtposPeriods;
	
	//Textfields for add department Pane
	private JTextField txtdepName;
	private JTextField txtdepempid;
	private JTextField txtdepid;
	private JTextField txtdephead;
	private JTextField txtdepcurrPosition;
	private JTextField txtdepempname;
	private JTextField txtdepdecurrdep;
	
	//Textfields to print the payslip
	private JTextField txtpaySempid;
	private JTextField txtpaySempname;
	private JTextField txtpaySsalary;
	private JTextField txtpaySdepartment;
	private JTextField txtpaySperiods;
	//Placeholder for an employee class
	private employee temp;
	//List for the different Panes,showint the departments and associated positions
	private JList<departments> departmentList;
	private JList<departments> departmentListAddE;
	private JList<departments> departmentListAddP;
	private JList<position> positionListAddE;
	private JList<position> positionListAddP;
	private JList<position> positionList;
	//A database connection handler
	private databaseHandler db;
	
	private ArrayList<departments> dep;
	//A model for the department list and position list
	private AbstractListModel<departments> departmentAbstractList;
	private AbstractListModel<position> positionAbstractlist;

	/**
	 * Create the frame.
	 */
	public mainInterface() {
		
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\tasso\\Pictures\\New-Payroll-Icon.png"));
		setTitle("TAC Payroll Systems");
		setResizable(false);
		
		this.db = new databaseHandler(); //creates a database handler that interacts with the database
		this.dep = db.getDData();	//gets the departemts from the database into an arraylist
		menuPane = new JPanel();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);				
		setBounds(100, 100, 1016, 724);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLayeredPane layeredPaneBase = new JLayeredPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(menuPane, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(layeredPaneBase, GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap(265, Short.MAX_VALUE)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(183))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(14)
							.addComponent(menuPane, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE)
							.addGap(3)))
					.addGap(14))
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(layeredPaneBase, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
					.addContainerGap())
		);
		/**
		 * Creates the layered panes that hold the different layers with the reports, etc.
		 */
		/**
		 * Creates the pane that holds the Table with salaries. Not implemented.
		 */
		this.layeredPaneGeneral = new JLayeredPane();
		layeredPaneGeneral.setBackground(Color.BLACK);
		layeredPaneBase.setLayer(layeredPaneGeneral, 3);
		layeredPaneGeneral.setBounds(0, 0, 787, 653);
		layeredPaneBase.add(layeredPaneGeneral);
		
		/**
		 * Creates a Pane for the Salarys Tables. Not implemented
		 */
		
		welcomePane = new JPanel();
		welcomePane.setBackground(Color.WHITE);
		layeredPaneGeneral.setLayer(welcomePane, 5);
		welcomePane.setBounds(0, 0, 773, 640);
		layeredPaneGeneral.add(welcomePane);
		welcomePane.setLayout(null);
		
		this.salaryTablePane = new JPanel();
		layeredPaneGeneral.setLayer(salaryTablePane, 0);
		salaryTablePane.setBounds(0, 0, 773, 640);
		layeredPaneGeneral.add(salaryTablePane);
		salaryTablePane.setLayout(null);
		
		/**
		 * Creates a Pane for the display of the employees Records
		 */
		this.employeeRecordsPane = new JPanel();
		layeredPaneGeneral.setLayer(employeeRecordsPane, 0);
		employeeRecordsPane.setBounds(0, 0, 773, 640);
		layeredPaneGeneral.add(employeeRecordsPane);
		
		/**
		 * Creates a Pane to display all empl
		 */
		this.allEmployeesPane = new JPanel();
		allEmployeesPane.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		layeredPaneGeneral.setLayer(allEmployeesPane, 0);
		allEmployeesPane.setBounds(0, 0, 775, 640);
		layeredPaneGeneral.add(allEmployeesPane);
		allEmployeesPane.setLayout(null);
		
		/**
		 * Creates a pane to add employees
		 */
		this.addEmployeePane = new JPanel();
		layeredPaneGeneral.setLayer(addEmployeePane, 0);
		addEmployeePane.setBounds(0, 0, 773, 640);
		layeredPaneGeneral.add(addEmployeePane);
		addEmployeePane.setLayout(null);
		
		/**
		 * Creates a pane to add a new position
		 */
		this.addPositionPane = new JPanel();
		layeredPaneGeneral.setLayer(addPositionPane, 0);
		addPositionPane.setBounds(0, 0, 773, 640);
		layeredPaneGeneral.add(addPositionPane);
		addPositionPane.setLayout(null);
		
		/**
		 * Creates a pane to add a department
		 */
		this.addDepartmentPane = new JPanel();
		addDepartmentPane.setBounds(0, 0, 773, 640);
		layeredPaneGeneral.add(addDepartmentPane);
		addDepartmentPane.setLayout(null);
		
		/**
		 * Creates pane to print the pay slip be employee
		 */
		this.printPaySlipPane = new JPanel();
		layeredPaneGeneral.setLayer(printPaySlipPane, 0);
		printPaySlipPane.setBounds(0, 0, 773, 640);
		layeredPaneGeneral.add(printPaySlipPane);
		printPaySlipPane.setLayout(null);
		
		/**
		 * Creates a pane to show the employees by department and the total of salaries
		 */
		this.employeeByDepPane = new JPanel();
		layeredPaneGeneral.setLayer(employeeByDepPane, 0);
		employeeByDepPane.setBounds(0, 0, 773, 640);
		layeredPaneGeneral.add(employeeByDepPane);
		employeeByDepPane.setLayout(null);
				
		welcomePane();
		addEmployee();	//Adds the labels, textfields, buttons and list needed
		addPosition();	//same as above
		addDepartment(); //same as above
		displayAllEmployees(); //same as above
		printPaySlip(); //same as above
		displayEmployeeBDepartment(); //same as above
		createMenu(); //creates the main menu to access all the panels
		menuPane.setLayout(null);
		contentPane.setLayout(gl_contentPane);
		//setVisibleLayer(9);
		
	}
	
	public void welcomePane() {
		
		JLabel lblWelcomeToPac = new JLabel("Welcome to TAC Payroll Systems");
		lblWelcomeToPac.setFont(new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 22));
		lblWelcomeToPac.setBounds(53, 63, 342, 41);
		welcomePane.add(lblWelcomeToPac);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(53, 117, 504, 16);
		welcomePane.add(separator);
		
		JLabel lblCreatedByArnold = new JLabel("Created by Arnold Low, Caleb Rigg and Tasso Klassen");
		lblCreatedByArnold.setBounds(53, 128, 332, 16);
		welcomePane.add(lblCreatedByArnold);
		try {
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("./payrollimage.png"));
		lblNewLabel.setBounds(51, 167, 381, 294);
		welcomePane.add(lblNewLabel);
		}catch(Exception e) {
			System.out.println("Image not found");
		}
	}
	/**
	 * Creates the necessary elements for the menu
	 * Author: Tasso Klassen
	 */
	public void createMenu() {
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setBounds(64, 13, 53, 25);
		menuPane.add(lblMenu);
		
		JLabel lblEmployeeManagementMenu = new JLabel("Employee Management");
		lblEmployeeManagementMenu.setBounds(12, 272, 138, 16);
		menuPane.add(lblEmployeeManagementMenu);
		
		/**
		 * Sets the position of the pane that displays all employees to top 
		 */
		JButton btnallEmployeesMenu = new JButton("All Employees");
		btnallEmployeesMenu.setForeground(Color.WHITE);
		btnallEmployeesMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				setVisibleLayer(3);
				tblallEmployees.setModel(new EmployeeModel());
				
			}
		});
		btnallEmployeesMenu.setBounds(12, 46, 148, 25);
		btnallEmployeesMenu.setBackground(Color.GRAY);
		btnallEmployeesMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		menuPane.add(btnallEmployeesMenu);
		
		/**
		 * Shows the pane to display the total salary cost 
		 */
		JButton btnTotalSalaryCostMenu = new JButton("Total Salary Cost");
		btnTotalSalaryCostMenu.setEnabled(false);
		btnTotalSalaryCostMenu.setBounds(12, 133, 148, 25);
		menuPane.add(btnTotalSalaryCostMenu);
		
		/**
		 * Sets the pane of the salary table to the top one. 
		 */
		JButton btnSalaryTableMenu = new JButton("Salary Table");
		btnSalaryTableMenu.setEnabled(false);
		btnSalaryTableMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setVisibleLayer(2);
			}
		});
		btnSalaryTableMenu.setBounds(12, 104, 148, 25);
		menuPane.add(btnSalaryTableMenu);
		
		/**
		 * Sets the pane of the records of the employees to 1
		 */
		JButton btnEmployeeRecordsMenu = new JButton("Employee Records");
		btnEmployeeRecordsMenu.setEnabled(false);
		btnEmployeeRecordsMenu.setBounds(12, 75, 148, 25);
		btnEmployeeRecordsMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleLayer(1);
			}
		});
		menuPane.add(btnEmployeeRecordsMenu);
		
		/**
		 * Sets the pane that shows the sum of employees per department to the top
		 */
		JButton btnEmployeeBDepMenu = new JButton("Employees by Dpt.");
		btnEmployeeBDepMenu.setForeground(Color.WHITE);
		btnEmployeeBDepMenu.setBackground(Color.GRAY);
		btnEmployeeBDepMenu.setBounds(12, 162, 148, 25);
		btnEmployeeBDepMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleLayer(8);
				tblempByDep.setModel(new SummaryModel());
			}
		});
		menuPane.add(btnEmployeeBDepMenu);
		
		/**
		 * Sets the pane to add employees to top
		 */
		JButton btnAddEmployeeMenu = new JButton("Add Employee");
		btnAddEmployeeMenu.setForeground(Color.WHITE);
		btnAddEmployeeMenu.setBackground(Color.GRAY);
		btnAddEmployeeMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleLayer(4);
				departmentListAddE.setModel(departmentModel());
			}
		});
		btnAddEmployeeMenu.setBounds(12, 301, 148, 25);
		menuPane.add(btnAddEmployeeMenu);
		
		/**
		 * Sets the pane to add a department to top
		 */
		JButton btnAddDepartmentMenu = new JButton("Manage Department");
		btnAddDepartmentMenu.setForeground(Color.WHITE);
		btnAddDepartmentMenu.setBackground(Color.GRAY);
		btnAddDepartmentMenu.setBounds(12, 330, 148, 25);
		btnAddDepartmentMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleLayer(6);
			}
		});
		menuPane.add(btnAddDepartmentMenu);
		
		/**
		 * Sets the pane to add a new position to top
		 */
		JButton btnAddPositionMenu = new JButton("Manage Position");
		btnAddPositionMenu.setForeground(Color.WHITE);
		btnAddPositionMenu.setBackground(Color.GRAY);
		btnAddPositionMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisibleLayer(5);
				departmentListAddP.setModel(departmentModel());

			}
		});
		btnAddPositionMenu.setBounds(12, 360, 148, 25);
		menuPane.add(btnAddPositionMenu);
		
		/**
		 * Creates the pane to print a pay slip
		 */
		JButton btnPrintPayslipMenu = new JButton("Print PaySlip");
		btnPrintPayslipMenu.setForeground(Color.WHITE);
		btnPrintPayslipMenu.setBackground(Color.GRAY);
		btnPrintPayslipMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisibleLayer(7);
			}
		});
		btnPrintPayslipMenu.setBounds(12, 192, 148, 25);
		menuPane.add(btnPrintPayslipMenu);

	}
	/**
	 * Creates the necessary elements to add a new employee or change an existing one.
	 */
	public void addEmployee() {
		
		/**
		 * Creates the textfields for the panel to add a new employee or change one.
		 */
		txtEmpId = new JTextField();
		txtEmpId.setText("");
		txtEmpId.setBounds(12, 169, 116, 22);
		addEmployeePane.add(txtEmpId);
		txtEmpId.setColumns(10);
		
		txtEmpFirstName = new JTextField();
		txtEmpFirstName.setText("");
		txtEmpFirstName.setBounds(173, 169, 116, 22);
		addEmployeePane.add(txtEmpFirstName);
		txtEmpFirstName.setColumns(10);
		
		txtEmpLastName = new JTextField();
		txtEmpLastName.setText("");
		txtEmpLastName.setBounds(338, 169, 116, 22);
		addEmployeePane.add(txtEmpLastName);
		txtEmpLastName.setColumns(10);
			
		txtEmpDateOfBirth = new JTextField();
		txtEmpDateOfBirth.setText("");
		txtEmpDateOfBirth.setBounds(502, 169, 116, 22);
		addEmployeePane.add(txtEmpDateOfBirth);
		txtEmpDateOfBirth.setColumns(10);
			
		txtEmpStartDate = new JTextField();
		txtEmpStartDate.setBounds(12, 492, 116, 22);
		addEmployeePane.add(txtEmpStartDate);
		txtEmpStartDate.setColumns(10);
		
		txtEmpEndDate = new JTextField();
		txtEmpEndDate.setBounds(173, 492, 116, 22);
		addEmployeePane.add(txtEmpEndDate);
		txtEmpEndDate.setColumns(10);
		
		txtEmpSeniority = new JTextField();
		txtEmpSeniority.setBounds(336, 492, 116, 22);
		addEmployeePane.add(txtEmpSeniority);
		txtEmpSeniority.setColumns(10);
		
		txtEmpLevel = new JTextField();
		txtEmpLevel.setBounds(502, 492, 116, 22);
		addEmployeePane.add(txtEmpLevel);
		txtEmpLevel.setColumns(10);
		
		/**
		 * Creates the labels for the different textfields.
		 */
		JLabel lblSeniority = new JLabel("Seniority");
		lblSeniority.setBounds(338, 461, 56, 16);
		addEmployeePane.add(lblSeniority);
		
		JLabel lblLevel = new JLabel("Level");
		lblLevel.setBounds(504, 461, 56, 16);
		addEmployeePane.add(lblLevel);
		
		JLabel lblEmpStartDate = new JLabel("Start Date");
		lblEmpStartDate.setBounds(11, 464, 71, 16);
		addEmployeePane.add(lblEmpStartDate);
		
		JLabel lblEmpEndDate = new JLabel("End Date");
		lblEmpEndDate.setBounds(173, 464, 56, 16);
		addEmployeePane.add(lblEmpEndDate);
		
		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setBounds(12, 276, 89, 16);
		addEmployeePane.add(lblDepartment);
		
		JLabel lblPosition = new JLabel("Position");
		lblPosition.setBounds(227, 276, 56, 16);
		addEmployeePane.add(lblPosition);
		
		JLabel lblEmpId = new JLabel("ID");
		lblEmpId.setBounds(12, 143, 56, 16);
		addEmployeePane.add(lblEmpId);
		
		JLabel lblEmpFirstName = new JLabel("First Name");
		lblEmpFirstName.setBounds(173, 140, 89, 16);
		addEmployeePane.add(lblEmpFirstName);
			
		JLabel lblEmpLastName = new JLabel("Last Name");
		lblEmpLastName.setBounds(338, 140, 83, 16);
		addEmployeePane.add(lblEmpLastName);
			
		JLabel lblEmpDateOfBirth = new JLabel("Date of birth");
		lblEmpDateOfBirth.setBounds(502, 143, 89, 16);
		addEmployeePane.add(lblEmpDateOfBirth);
		
		/**
		 * Creates a list of the current departments
		 */
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 305, 152, 90);
		addEmployeePane.add(scrollPane);
		departmentListAddE = new JList<departments>();
		scrollPane.setViewportView(departmentListAddE);
		departmentListAddE.setModel(departmentModel());
		departmentListAddE.setSelectedIndex(0);
		departmentListAddE.addListSelectionListener(new ListSelectionListener() {
				
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(departmentListAddE.isSelectionEmpty()) {
					departmentListAddE.setSelectedIndex(0);
				}
				System.out.println(departmentListAddE.getSelectedIndex());
				positionListAddE.removeAll();
				positionListAddE.setModel(positionModel(departmentListAddE.getSelectedValue().getdepID()));
			}
		});
		
		/**
		 * Creates a basic profile for the list of positions
		 */
	
		
		/**
		 * Creates the list with the positions based on the selected department. Sets the default selection to 0.
		 */
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(227, 305, 167, 90);
		addEmployeePane.add(scrollPane_1);
		positionListAddE = new JList<position>();
		positionListAddE.setModel(positionModel(departmentListAddE.getSelectedValue().getdepID()));
		scrollPane_1.setViewportView(positionListAddE);
		
		/**
		 * Submits the data entered into the fields into the database. If data is not valid, a dialog is displayed.
		 */
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db=new databaseHandler();
				String stat = "insert into Employee (empfirstname,emplastname,posid, depid, empstartdate,level,seniority,empdateofbirth) values (\'"
				+txtEmpFirstName.getText()+"\',\'"
				+txtEmpLastName.getText()+"\',"
				+positionListAddE.getSelectedValue().getID()+","
				+departmentListAddE.getSelectedValue().getdepID()+",\'"
				+txtEmpStartDate.getText()+"\',\'"
				+txtEmpLevel.getText()+"\',\'"
				+txtEmpSeniority.getText()+"\',\'"
				+txtEmpDateOfBirth.getText()+"\');";
				System.out.println(stat);
				db.submitData(stat);
				db.close();
			}
		});
		btnSubmit.setBounds(676, 602, 97, 25);
		addEmployeePane.add(btnSubmit);

		/**
		 * Queries the entered employee id from the field and if found, it fills out the fields. Catches error if the staff id is not valid and displays a message
		 */
		JButton btnSearchEmp = new JButton("Search");
		btnSearchEmp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				position t= new position();
				try {
				temp = new employee(Integer.parseInt(txtEmpId.getText()));
				t.query(temp.getPosID());
				txtEmpId.setText(Integer.toString(temp.getID()));
				txtEmpFirstName.setText(temp.getFirstName());
				txtEmpLastName.setText(temp.getLastName());
				txtEmpDateOfBirth.setText(temp.getDateOfBirth());
				txtEmpSeniority.setText(temp.getSeniority());
				txtEmpLevel.setText(Integer.toString(temp.getLevel()));
				txtEmpStartDate.setText(temp.getEmpStart());
				int i=0;
				for(int e=0;e<dep.size();e++) {
					if(temp.getDepId()==dep.get(e).getdepID()) {
						i=e;
					}
				}
				departmentListAddE.setSelectedValue(dep.get(i), true);
				} catch(NumberFormatException d) {
					JOptionPane.showMessageDialog(getParent(), "Please enter a valid Staff ID", "Error", 0);
				}
				catch(Exception d1) {
					d1.printStackTrace();
				}
				db.close();
			}
		});
		btnSearchEmp.setBounds(396, 602, 97, 25);
		addEmployeePane.add(btnSearchEmp);
			
		/**
		 * Updates the selected employee and displays a message if the empid doesn't contain any valid input.
		 */
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					temp = new employee(Integer.parseInt(txtEmpId.getText()));
					temp.updateDB(
							Integer.parseInt(txtEmpLevel.getText()),
							txtEmpSeniority.getText(),
							txtEmpFirstName.getText(),
							txtEmpLastName.getText(),
							txtEmpDateOfBirth.getText(),
							txtEmpStartDate.getText(),
							txtEmpEndDate.getText()	
							);
				} catch(Exception b) {
					JOptionPane.showMessageDialog(getRootPane(), "Wrong Input in Staff ID", "Error", 1);
				}
				
				
			}
		});
		btnUpdate.setBounds(535, 602, 97, 25);
		addEmployeePane.add(btnUpdate);
		
		JLabel lblAddEmployee_1 = new JLabel("Add Employee");
		lblAddEmployee_1.setBounds(12, 27, 116, 35);
		addEmployeePane.add(lblAddEmployee_1);
		
	}
	/**
	 * Creates the necessary elements to add a new position to the specified department
	 */
	public void addPosition() {
		/**
		 * Text field for the name of the selected position or a new position
		 */
		txtposName = new JTextField();
		txtposName.setBounds(12, 91, 116, 22);
		addPositionPane.add(txtposName);
		txtposName.setColumns(10);
		/**
		 * Text field for the role of the position
		 */
		txtposRole = new JTextField();
		txtposRole.setBounds(167, 91, 116, 22);
		addPositionPane.add(txtposRole);
		txtposRole.setColumns(10);
		/**
		 * Text field for the period of employment, if fulltime, parttime, or seasonal
		 */
		txtposPeriods = new JTextField();
		txtposPeriods.setBounds(340, 91, 116, 22);
		addPositionPane.add(txtposPeriods);
		txtposPeriods.setColumns(10);
		/**
		 * An extra panel for the description of the position
		 */
		JPanel PositionDescriptionCont = new JPanel();
		PositionDescriptionCont.setBorder(new TitledBorder(new LineBorder(new Color(171, 173, 179)), "Position Description", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		PositionDescriptionCont.setBounds(531, 62, 230, 262);
		addPositionPane.add(PositionDescriptionCont);
		PositionDescriptionCont.setLayout(null);
		/**
		 * Holds the description for the position
		 */
		JTextPane txtPositionDescription = new JTextPane();
		txtPositionDescription.setBounds(6, 18, 218, 231);
		PositionDescriptionCont.add(txtPositionDescription);
				
		/**
		 * submits the changes to the database.If one of the values is not valid, outpus a message showing that not all values are present
		 * Also closes the connection to database after use
		 */
		JButton add_position_btn = new JButton("Submit");
		add_position_btn.setBounds(664, 441, 97, 25);
		add_position_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db=new databaseHandler();
				String stat = "insert into position (depid,posname,posdes,posrole,posperiod) values (\'"
				+departmentListAddP.getSelectedValue().getdepID()+"\',\'"
				+txtposName.getText()+"\',\'"
				+txtPositionDescription.getText()+"\',\'"
				+txtposRole.getText()+"\',\'"
				+txtposPeriods.getText()
				+"\');";
				try{
					db.submitData(stat);
					positionListAddP.setModel(positionModel(departmentListAddP.getSelectedValue().getdepID()));
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(getRootPane(), "Please fill out all fields", "Error", 1);
				}
				db.close();
				
			}
		});
		addPositionPane.add(add_position_btn);
		
		/**
		 * Labels for the different textfields
		 */
		JLabel lblAddPosition = new JLabel("Add Position");
		lblAddPosition.setBounds(12, 25, 103, 16);
		addPositionPane.add(lblAddPosition);
		
		JLabel lblPositionName = new JLabel("Position Name");
		lblPositionName.setBounds(12, 62, 103, 16);
		addPositionPane.add(lblPositionName);
		
		JLabel lblPositionRole = new JLabel("Position Role");
		lblPositionRole.setBounds(166, 62, 97, 16);
		addPositionPane.add(lblPositionRole);
		
		JLabel lblPeriod = new JLabel("Period");
		lblPeriod.setBounds(340, 62, 82, 16);
		addPositionPane.add(lblPeriod);
		
		JLabel lblPosition = new JLabel("Position");
		lblPosition.setBounds(167, 205, 82, 16);
		addPositionPane.add(lblPosition);
		
		JLabel lblDepartments = new JLabel("Department");
		lblDepartments.setBounds(12, 205, 96, 16);
		addPositionPane.add(lblDepartments);
		
		/**
		 * Creates the list with the positions based on the selected department. Sets the default selection to 0. 
		 * Also adds a listener to update the model for the positionList so that the corresponding positions are displayed
		 */
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 234, 137, 90);
		addPositionPane.add(scrollPane);
		departmentListAddP = new JList<departments>();
		scrollPane.setViewportView(departmentListAddP);
		departmentListAddP.setModel(departmentModel());
		departmentListAddP.setSelectedIndex(0);
		departmentListAddP.addListSelectionListener(new ListSelectionListener() {
				
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				if(departmentListAddP.isSelectionEmpty()){
			departmentListAddP.setSelectedIndex(0);
		}
			positionListAddP.setModel(positionModel(departmentListAddP.getSelectedValue().getdepID()));
			}
		});
		
		/**
		 * A scrollable container to hold the list of positions, which are displayed for the currently selected department.
		 * The default selected value for department is set to 0
		 */
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(167, 234, 148, 90);
		addPositionPane.add(scrollPane_1);
		positionListAddP = new JList<position>();
		scrollPane_1.setViewportView(positionListAddP);
		positionListAddP.setModel(positionModel(departmentListAddP.getSelectedValue().getdepID()));
		positionListAddP.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				try {
				txtposName.setText(positionListAddP.getSelectedValue().getName());
				txtPositionDescription.setText(positionListAddP.getSelectedValue().posdes());
				txtposPeriods.setText(positionListAddP.getSelectedValue().getPeriod());
				txtposRole.setText(positionListAddP.getSelectedValue().getRole());
				}catch(Exception d) {
					
				}
				
			}
		});
		db.close();
		
	}
	
	/**
	 * Manages the department section. Here a department can be created, deleted
	 *  or the department of an employee can be changed.
	 */
	public void addDepartment() {
		JLabel lblAddDepartment = new JLabel("Add Department");
		lblAddDepartment.setBounds(12, 13, 93, 16);
		addDepartmentPane.add(lblAddDepartment);
		
		JLabel lblDepartmentName = new JLabel("Department Name");
		lblDepartmentName.setBounds(12, 84, 136, 16);
		addDepartmentPane.add(lblDepartmentName);
		
		txtdepName = new JTextField();
		txtdepName.setBounds(12, 112, 116, 22);
		addDepartmentPane.add(txtdepName);
		txtdepName.setColumns(10);
		
		JLabel lblDepartmentId = new JLabel("Department ID");
		lblDepartmentId.setBounds(160, 84, 116, 16);
		addDepartmentPane.add(lblDepartmentId);
		
		txtdepid = new JTextField();
		txtdepid.setBounds(160, 112, 116, 22);
		txtdepid.setEditable(false);
		addDepartmentPane.add(txtdepid);
		txtdepid.setColumns(10);
		
		txtdephead = new JTextField();
		txtdephead.setBounds(311, 112, 116, 22);
		addDepartmentPane.add(txtdephead);
		txtdephead.setColumns(10);
		
		JLabel lblDepartmentHead = new JLabel("Department Head");
		lblDepartmentHead.setBounds(312, 84, 115, 16);
		addDepartmentPane.add(lblDepartmentHead);
		
		JButton btnAddDepartment = new JButton("Add");
		btnAddDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				db=new databaseHandler();
				String insert="insert into Department (depname,dephead) values (\'";
				insert+=txtdepName.getText()+"\'," + txtdephead.getText()+");";
				System.out.println(insert);
				db.submitData(insert);
				db.close();	
				departmentList.setModel(departmentModel());	
			}
		});
		btnAddDepartment.setBounds(460, 111, 97, 25);
		addDepartmentPane.add(btnAddDepartment);
		
		txtdepempid = new JTextField();
		txtdepempid.setBounds(12, 360, 116, 22);
		addDepartmentPane.add(txtdepempid);
		txtdepempid.setColumns(10);
		
		JLabel lblStaffId_1 = new JLabel("Staff ID");
		lblStaffId_1.setBounds(12, 331, 56, 16);
		addDepartmentPane.add(lblStaffId_1);
		
		JButton btnSearchdepemp = new JButton("Search");
		btnSearchdepemp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				temp = new employee(Integer.parseInt(txtdepempid.getText()));
				txtdepdecurrdep.setText(temp.getDep());
				txtdepempname.setText(temp.getName());
				txtdepcurrPosition.setText(temp.getPos());
				} catch(NumberFormatException n1) {
					JOptionPane.showMessageDialog(getParent(), "Select an employee", "Error", 0);
				}
			}
		});
		btnSearchdepemp.setBounds(160, 359, 97, 25);
		addDepartmentPane.add(btnSearchdepemp);
		
		txtdepempname = new JTextField();
		txtdepempname.setEditable(false);
		txtdepempname.setBounds(12, 442, 116, 22);
		addDepartmentPane.add(txtdepempname);
		txtdepempname.setColumns(10);
		
		txtdepdecurrdep = new JTextField();
		txtdepdecurrdep.setEditable(false);
		txtdepdecurrdep.setBounds(160, 442, 116, 22);
		addDepartmentPane.add(txtdepdecurrdep);
		txtdepdecurrdep.setColumns(10);
		
		txtdepcurrPosition = new JTextField();
		txtdepcurrPosition.setEditable(false);
		txtdepcurrPosition.setBounds(297, 442, 116, 22);
		addDepartmentPane.add(txtdepcurrPosition);
		txtdepcurrPosition.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 537, 182, 90);
		addDepartmentPane.add(scrollPane);
		
		departmentList = new JList<departments>();
		scrollPane.setViewportView(departmentList);
		departmentList.setModel(departmentModel());
		departmentList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				try {
				txtdepid.setText(Integer.toString(departmentList.getSelectedValue().getdepID()));
				txtdepName.setText(departmentList.getSelectedValue().getName());
				txtdephead.setText(Integer.toString(departmentList.getSelectedValue().getdepHead()));
				positionList.setModel(positionModel(departmentList.getSelectedValue().getdepID()));
				}catch (Exception e2) {
					
				}
			}
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(220, 537, 160, 90);
		addDepartmentPane.add(scrollPane_1);
		positionList = new JList<position>();
		departmentList.setSelectedIndex(0);
		positionList.setModel(positionModel(departmentList.getSelectedValue().getdepID()));
		scrollPane_1.setViewportView(positionList);

		JButton btnChange = new JButton("Change");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(!departmentList.isSelectionEmpty()||!positionList.isSelectionEmpty()) {
						temp.updateDepartment(departmentList.getSelectedValue().getdepID());
						temp.updatePosition(positionList.getSelectedValue().getID());
						txtdepdecurrdep.setText(departmentList.getSelectedValue().getName());
						txtdepcurrPosition.setText(positionList.getSelectedValue().getName());
					}
					else {
						JOptionPane.showMessageDialog(getParent(), "Select a department", "Error", 1);
					}
				}catch(NullPointerException n) {
					JOptionPane.showMessageDialog(getParent(), "Select an employee", "Error", 0);
				}
			}
		});
		
		btnChange.setBounds(438, 441, 97, 25);
		addDepartmentPane.add(btnChange);
		
		JLabel lblName_1 = new JLabel("Name");
		lblName_1.setBounds(12, 420, 78, 16);
		addDepartmentPane.add(lblName_1);
		
		JLabel lblCurrentDepartment = new JLabel("Current Department");
		lblCurrentDepartment.setBounds(160, 420, 143, 16);
		addDepartmentPane.add(lblCurrentDepartment);
		
		JLabel lblNewDepartment = new JLabel("New Department");
		lblNewDepartment.setBounds(12, 517, 136, 16);
		addDepartmentPane.add(lblNewDepartment);
		
		JLabel lblNewPosition = new JLabel("New Position");
		lblNewPosition.setBounds(220, 517, 93, 16);
		addDepartmentPane.add(lblNewPosition);
		
	
		
		JLabel lblCurrentPosition = new JLabel("Current Position");
		lblCurrentPosition.setBounds(297, 420, 116, 16);
		addDepartmentPane.add(lblCurrentPosition);
		
		JButton btnDeleteDepartment = new JButton("Delete Department");
		btnDeleteDepartment.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(departmentList.getSelectedValue().deleteDep()) {
						departmentList.setModel(departmentModel());
					}
					else {
						JOptionPane.showMessageDialog(getParent(), "Can't delete positions. Still assigned to some employees", "Error", 0);
					}
					
				}catch(NullPointerException n) {
					JOptionPane.showMessageDialog(getParent(), "Select a department", "Error", 0);
				}
			}
		});
		btnDeleteDepartment.setBounds(428, 602, 151, 25);
		addDepartmentPane.add(btnDeleteDepartment);
		
		JButton btnDeletePostion = new JButton("Delete Postion");
		btnDeletePostion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if(positionList.getSelectedValue().deletePos()) {
						positionList.setModel(positionModel(departmentList.getSelectedValue().getdepID()));
					}
					else {
						JOptionPane.showMessageDialog(getParent(), "Position still assigned to an employee", "Error", 0);
					}
				}catch(NullPointerException n) {
					JOptionPane.showMessageDialog(getParent(), "Select a position", "Error", 0);
				}
			}
		});
		btnDeletePostion.setBounds(428, 566, 151, 25);
		addDepartmentPane.add(btnDeletePostion);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 198, 749, 31);
		addDepartmentPane.add(separator);
		
		JLabel lblChangeEmployeesDepartment = new JLabel("Change Employee's Department");
		lblChangeEmployeesDepartment.setBounds(12, 212, 301, 31);
		addDepartmentPane.add(lblChangeEmployeesDepartment);
		
	}
	
	/**
	 * Creates the Model for the department list so it knows what to display. 
	 * @returns the abstractlistmodel
	 */
	public AbstractListModel<departments> departmentModel(){
		departmentAbstractList =  new AbstractListModel<departments>()  {
			private static final long serialVersionUID = 1L;
			databaseHandler db=new databaseHandler();
			
			ArrayList<departments> dep = db.getDData();
			public int getSize() {
				return dep.size();
			}
			public departments getElementAt(int index) {
					return dep.get(index);
			}
		};
		return departmentAbstractList;
	}
	
	/**
	 * Takes the id of an department and queries the corresponding positions from the database.
	 * @param depid
	 * @returns AbstractListModel
	 */
	public AbstractListModel<position> positionModel(int depid){
		positionAbstractlist = new AbstractListModel<position>() {
			private static final long serialVersionUID = 1L;
			databaseHandler db=new databaseHandler();
			
			ArrayList<position> pos = db.getDynamicPData(depid);
			public int getSize() {
				return pos.size();
			}
			public position getElementAt(int index) {
					return pos.get(index);
			}
		};
		return positionAbstractlist;
	}
	
	/**
	 * Creates the table to display all the employees present
	 */
	public void displayAllEmployees() {
		
		JLabel lblAllEmployees = new JLabel("All Employees");
		lblAllEmployees.setBounds(12, 13, 205, 16);
		allEmployeesPane.add(lblAllEmployees);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 29, 751, 598);
		allEmployeesPane.add(scrollPane);
		tblallEmployees = new JTable();
		tblallEmployees.setFillsViewportHeight(true);
		tblallEmployees.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(tblallEmployees);
		tblallEmployees.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tblallEmployees.setModel(new EmployeeModel());
		}
	
	/**
	 * Creates the panel with the fields to print the payslip.
	 * No document is generated.
	 */
	public void printPaySlip() {
		
		/**
		 * All the labels to name the textfields and sections are created here
		 */
		JLabel lblPrintPaySlip = new JLabel("Print Pay Slip");
		lblPrintPaySlip.setBounds(12, 13, 82, 16);
		printPaySlipPane.add(lblPrintPaySlip);
		
		JLabel lblSearchByStaff = new JLabel("Search by Staff ID");
		lblSearchByStaff.setBounds(12, 113, 134, 16);
		printPaySlipPane.add(lblSearchByStaff);
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(12, 242, 56, 16);
		printPaySlipPane.add(lblName);
		
		JLabel lblSalary = new JLabel("Salary");
		lblSalary.setBounds(186, 242, 56, 16);
		printPaySlipPane.add(lblSalary);
		
		JLabel lblDepartment_1 = new JLabel("Department");
		lblDepartment_1.setBounds(359, 242, 104, 16);
		printPaySlipPane.add(lblDepartment_1);
		
		JLabel lblPaySlip = new JLabel("Pay Slip");
		lblPaySlip.setBounds(12, 437, 749, 16);
		printPaySlipPane.add(lblPaySlip);
		
		JLabel lblPayPeriods = new JLabel("Pay Periods");
		lblPayPeriods.setBounds(528, 242, 82, 16);
		printPaySlipPane.add(lblPayPeriods);
		/**
		 * All textfields that hold the necessary room to enter information are created here
		 * for the print pay slip Pane
		 */
		txtpaySempid = new JTextField();
		txtpaySempid.setBounds(12, 86, 116, 22);
		printPaySlipPane.add(txtpaySempid);
		txtpaySempid.setColumns(10);
			
		txtpaySempname = new JTextField();
		txtpaySempname.setEditable(false);
		txtpaySempname.setBounds(12, 211, 116, 22);
		printPaySlipPane.add(txtpaySempname);
		txtpaySempname.setColumns(10);
		
		txtpaySsalary = new JTextField();
		txtpaySsalary.setEditable(false);
		txtpaySsalary.setBounds(186, 211, 116, 22);
		printPaySlipPane.add(txtpaySsalary);
		txtpaySsalary.setColumns(10);
		
		txtpaySdepartment = new JTextField();
		txtpaySdepartment.setEditable(false);
		txtpaySdepartment.setBounds(359, 211, 116, 22);
		printPaySlipPane.add(txtpaySdepartment);
		txtpaySdepartment.setColumns(10);
		
		txtpaySperiods = new JTextField();
		txtpaySperiods.setEditable(false);
		txtpaySperiods.setBounds(528, 211, 116, 22);
		printPaySlipPane.add(txtpaySperiods);
		txtpaySperiods.setColumns(10);
		
		/**
		 * This is the area where the payslip is shown. 
		 */
		JTextArea paySlip = new JTextArea();
		paySlip.setFont(new Font("LuzSans-Book", Font.PLAIN, 20));
		paySlip.setEditable(false);
		paySlip.setBounds(12, 462, 749, 84);
		printPaySlipPane.add(paySlip);
		
		/**
		 * Searches for the entered staff id. 
		 */
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				temp = new employee(Integer.parseInt(txtpaySempid.getText()));
				//txtpaySempid.setText(Integer.toString(temp.getID()));
				txtpaySempname.setText(temp.getName());
				txtpaySsalary.setText(Integer.toString(temp.getSalary()));
				txtpaySdepartment.setText(temp.getDep());
				txtpaySperiods.setText(Integer.toString(temp.getPayPeriods()));
				paySlip.setText("");
			}
		});
		btnSearch.setBounds(165, 85, 97, 25);
		printPaySlipPane.add(btnSearch);
		
		/**
		 * gets the necessary information to show the payslip of the selected employee
		 */
		JButton btnPrint = new JButton("Print");
		btnPrint.setBounds(627, 559, 134, 25);
		btnPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			System.out.println(temp.getTaxSlip());
			paySlip.setText(temp.getTaxSlip());
			}
		});
		printPaySlipPane.add(btnPrint);
		
	}
	/**
	 * Shows the employees by department with the cost per department
	 */
	public void displayEmployeeBDepartment() {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 42, 749, 585);
		employeeByDepPane.add(scrollPane);
		tblempByDep = new JTable();
		tblempByDep.setIntercellSpacing(new Dimension(8, 8));
		tblempByDep.setFillsViewportHeight(true);
		tblempByDep.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		tblempByDep.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tblempByDep.setFont(new Font("Tahoma", Font.PLAIN, 17));
		tblempByDep.setRowHeight(25);
		scrollPane.setViewportView(tblempByDep);
		tblempByDep.setModel(new SummaryModel());
		
		JLabel lblEmployeesByDepartment = new JLabel("Employees by Department");
		lblEmployeesByDepartment.setBounds(12, 13, 349, 28);
		employeeByDepPane.add(lblEmployeesByDepartment);
	}
	
	/**
	 * Sets the selected layer as the top layer and makes it visible.
	 * @param top as an int
	 */
	public void setVisibleLayer(int top) {
		int welcomePane=0;
		int employee_records=0;
		int salary_table=0;
		int all_employees=0;
		int add_employee=0;
		int add_position=0;
		int add_department=0;
		int printPaySlip=0;
		int empByDep=0;
		switch(top) {
		case 1: employee_records=1; break;
		case 2: salary_table=1; break;
		case 3: all_employees=1; break;
		case 4: add_employee=1; break;
		case 5: add_position=1; break;
		case 6: add_department=1; break;
		case 7: printPaySlip=1; break;
		case 8: empByDep=1; break;
		case 9: welcomePane = 1; break;
		}
		layeredPaneGeneral.setLayer(this.welcomePane, welcomePane);
		layeredPaneGeneral.setLayer(this.employeeRecordsPane, employee_records);
		layeredPaneGeneral.setLayer(this.salaryTablePane, salary_table);
		layeredPaneGeneral.setLayer(this.allEmployeesPane, all_employees);
		layeredPaneGeneral.setLayer(this.addEmployeePane, add_employee);
		layeredPaneGeneral.setLayer(this.addPositionPane, add_position);
		layeredPaneGeneral.setLayer(this.addDepartmentPane, add_department);
		layeredPaneGeneral.setLayer(this.printPaySlipPane, printPaySlip);
		layeredPaneGeneral.setLayer(this.tblempByDep, empByDep);
	}
}


