package giis.demo.util;

import giis.demo.controller.*;
import giis.demo.model.*;
import giis.demo.tkrun.CarrerasController;
import giis.demo.tkrun.CarrerasModel;
import giis.demo.tkrun.CarrerasView;
import giis.demo.view.*;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import giis.demo.controller.RegisterSponsorshipAgreeController;
import giis.demo.model.RegisterSponsorshipAgreeModel;
import giis.demo.tkrun.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.util.Date;

/**
 * Punto de entrada principal que incluye botones para la ejecucion de las pantallas 
 * de las aplicaciones de ejemplo
 * y acciones de inicializacion de la base de datos.
 * No sigue MVC pues es solamente temporal para que durante el desarrollo se tenga posibilidad
 * de realizar acciones de inicializacion
 */
public class SwingMain {

	private JFrame frame;
	private JTextField tfChangeDate;
	private static Date fechaISO;
	private JLabel dateErrorLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { //NOSONAR codigo autogenerado
			public void run() {
				try {
					SwingMain window = new SwingMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace(); //NOSONAR codigo autogenerado
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SwingMain() {
		initialize();
	}

	public JTextField getTfChangeDate() {
		return tfChangeDate;
	}

	public void setTfChangeDate(String tfChangeDate) {
		this.tfChangeDate.setText(tfChangeDate);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Main");
		frame.setBounds(0, 0, 575, 426);
		frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);


		JButton btnRegisterSponsorship = new JButton("Register Sponsorship Agreements");

		btnRegisterSponsorship.setBounds(20, 175, 238, 23);

		btnRegisterSponsorship.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				RegisterSponsorshipAgreeController controller=new RegisterSponsorshipAgreeController(new RegisterSponsorshipAgreeModel(), new RegisterSponsorshipAgreeView());
				controller.initController(SwingMain.this);
			}
		});
		frame.getContentPane().add(btnRegisterSponsorship);
			

		JButton btnInicializarBaseDeDatos = new JButton("Initialize DB");
		btnInicializarBaseDeDatos.setBounds(0, 23, 197, 23);
		btnInicializarBaseDeDatos.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				Database db=new Database();
				db.createDatabase(false);
			}
		});
		frame.getContentPane().add(btnInicializarBaseDeDatos);
			
		JButton btnCargarDatosIniciales = new JButton("Load DB Data");
		btnCargarDatosIniciales.setBounds(0, 46, 205, 23);
		btnCargarDatosIniciales.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				Database db=new Database();
				db.createDatabase(false);
				db.loadDatabase();
			}
		});
		frame.getContentPane().add(btnCargarDatosIniciales);		
		
		JLabel changeDateLabel = new JLabel("Change the current date (Use YYYY-MM-DD format):");
		changeDateLabel.setBounds(10, 80, 350, 14);
		frame.getContentPane().add(changeDateLabel);
		
		tfChangeDate = new JTextField();
		tfChangeDate.setBounds(10, 96, 149, 20);
		frame.getContentPane().add(tfChangeDate);
		tfChangeDate.setColumns(10);
		
		JButton btnChangeDate = new JButton("Change Date");
		btnChangeDate.setBounds(163, 95, 132, 23);
		frame.getContentPane().add(btnChangeDate);
		
		dateErrorLabel = new JLabel("");
		dateErrorLabel.setBounds(10, 119, 248, 14);
		frame.getContentPane().add(dateErrorLabel);
		
		btnChangeDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registrarFecha();
			}
		});
		
		
		JButton btnRegisterIncomeExpenses = new JButton("Register Income/Expenses");
		btnRegisterIncomeExpenses.setBounds(20, 143, 238, 23);
		btnRegisterIncomeExpenses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterIncomeExpensesModel model = new RegisterIncomeExpensesModel();
				RegIncomeExpensesView view = new RegIncomeExpensesView();
				RegisterIncomeExpensesController controller = new RegisterIncomeExpensesController(model, view);
				
			}
		});
		frame.getContentPane().add(btnRegisterIncomeExpenses);
		

		//Fixed conflicts
		JButton btnSendInvoices = new JButton("Generate Invoices");
		btnSendInvoices.setBounds(20, 242, 238, 23);
		btnSendInvoices.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        String date = getFechaISO();

				InvoiceSendModel model = new InvoiceSendModel(date);
				InvoiceSendView view = new InvoiceSendView();
				InvoiceSendController controller = new InvoiceSendController(model, view);
			}
		});
		frame.getContentPane().add(btnSendInvoices);
		

		JButton btnConsultEvt = new JButton("Consult Event Status");
		btnConsultEvt.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        ConsultEventStatusController controller = new ConsultEventStatusController(
		            new ConsultEventStatusModel(), 
		            new ConsultEventStatusView(), 
		            SwingMain.this  // Pass the SwingMain instance
		        );
		        controller.initController(SwingMain.this);
		    }
		});
		btnConsultEvt.setBounds(20, 209, 238, 23);
		frame.getContentPane().add(btnConsultEvt);
		String fechaInit = "2025-04-01";
		this.fechaISO = Util.isoStringToDate(fechaInit);
		this.setTfChangeDate(fechaInit);
		
		
		JButton btnRegisterPayments = new JButton("Register Payments");
		btnRegisterPayments.setBounds(20, 275, 238, 23);
		frame.getContentPane().add(btnRegisterPayments);
		btnRegisterPayments.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				RegisterPaymentsController controller=new RegisterPaymentsController(new RegisterPaymentsView(), new RegisterPaymentsModel());
				controller.initController(SwingMain.this);
			}
		});
		frame.getContentPane().add(btnRegisterPayments);
		
		JButton btnRegisterEvent = new JButton("Register New Events");
		btnRegisterEvent.setBounds(301, 143, 248, 23);
		frame.getContentPane().add(btnRegisterEvent);
		btnRegisterEvent.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				RegisterEventController controller=new RegisterEventController(new RegisterEventModel(), new RegisterEventView());
				controller.initController(SwingMain.this);
			}
		});
		
		JButton btnReportIncExp = new JButton("Report Income & Expenses");
		btnReportIncExp.setBounds(20, 308, 238, 23);
		frame.getContentPane().add(btnReportIncExp);
		btnReportIncExp.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				String fecha = getFechaISO();
				//String fecha = tfChangeDate.getText();
				ReportIncomeExpensesController controller=new ReportIncomeExpensesController(new ReportIncomeExpensesView(), new ReportIncomeExpensesModel(), fecha);
				controller.initController(SwingMain.this);
			}
		});
		frame.getContentPane().add(btnReportIncExp);
		
		JButton btnCloseEvents = new JButton("Close Events");
		btnCloseEvents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CloseEventController controller=new CloseEventController(new CloseEventModel(), new CloseEventView());
				controller.initController(SwingMain.this);
			}
		});
		btnCloseEvents.setBounds(301, 175, 248, 23);
		frame.getContentPane().add(btnCloseEvents);
		
		JButton btnLTA = new JButton("Register Long Term Agreements");
		btnLTA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LTAController controller=new LTAController(new LTAModel(), new LTAView());
				controller.initController(SwingMain.this);
			}
		});
		btnLTA.setBounds(301, 209, 248, 23);
		frame.getContentPane().add(btnLTA);
		
		
		
		JButton btnCompanyMembers = new JButton("Manage Company Members");
		btnCompanyMembers.setBounds(301, 242, 248, 23);
		frame.getContentPane().add(btnCompanyMembers);

		btnCompanyMembers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame memberFrame = new JFrame("Company Members");
				memberFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				memberFrame.setSize(600, 400);
				memberFrame.setLocationRelativeTo(null);
				memberFrame.getContentPane().add(new ManageContactMemberView());
				memberFrame.setVisible(true);
			}
		});
		
		
		JButton btnReopenEvent = new JButton("Reopen Event");
		btnReopenEvent.setBounds(301, 275, 248, 23);
		frame.getContentPane().add(btnReopenEvent);

		btnReopenEvent.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        ReOpenEventModel modelr = new ReOpenEventModel();
		        ReOpenEventView view = new ReOpenEventView();
		        
		        ReOpenEventController controller = new ReOpenEventController( view, modelr);
		    }
		});
		
		JButton btnManageGBMembers = new JButton("Manage GB Members");
		btnManageGBMembers.setBounds(301, 308, 248, 23);
		frame.getContentPane().add(btnManageGBMembers);

		btnManageGBMembers.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        ManageGBMemberView view = new ManageGBMemberView();
		        ManageGBMemberModel model = new ManageGBMemberModel();
		        ManageGBMemberController controller = new ManageGBMemberController(view, model);
		        view.setVisible(true); // Mostramos la vista
		    }
		});
		
		JButton btnCancelIncomeExpense = new JButton("Cancel Income & Expenes");
		btnCancelIncomeExpense.setBounds(20, 341, 238, 23);
		
		btnCancelIncomeExpense.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				String fecha = tfChangeDate.getText();
				CancelIncomeExpensesController controller=new CancelIncomeExpensesController(new CancelIncomeExpensesView(), new CancelIncomeExpenseModel(), fecha);
				controller.initController(SwingMain.this);
			}
		});
		frame.getContentPane().add(btnCancelIncomeExpense);
		
		JButton btnEditEvent = new JButton("Edit Event");
		btnEditEvent.setBounds(301, 341, 250, 23);
		
		btnEditEvent.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				String fecha = getFechaISO();
				//String fecha = tfChangeDate.getText();
				EditEventController controller=new EditEventController(new EditEventView(), new EditEventModel(), fecha);
				controller.initController(SwingMain.this);
			}
		});		
		frame.getContentPane().add(btnEditEvent);



	}

	/**
	 * Validate & register date with format ISO 8601
	 */
	private void registrarFecha() {
	    String input = tfChangeDate.getText();
	    
	    if (Util.isValidISODate(input)) {
	        fechaISO = Util.isoStringToDate(input); // Convertimos el String a Date
	        dateErrorLabel.setText("Format is correct! Date: " + Util.dateToIsoString(fechaISO));
	    } else {
	        dateErrorLabel.setText("INCORRECT FORMAT: YYYY-MM-DD");
	    }
	}
	
	/**
	 * Public method to access registered date in ISO format
	 */
	public static String getFechaISO() {
		return (fechaISO != null) ? Util.dateToIsoString(fechaISO) : null;
	}

	/**
	 * Public method to access registered date as Date object
	 */
	public Date getFecha() {
		return fechaISO;
	}

	public JFrame getFrame() { return this.frame; }
}
