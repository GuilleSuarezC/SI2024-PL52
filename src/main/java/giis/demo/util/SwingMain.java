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
import giis.demo.view.RegisterSponsorshipAgreeView;


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
	private Date fechaISO;
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
		

		JButton btnRegisterSponsorship = new JButton("Register Sponsorships");

		btnRegisterSponsorship.setBounds(20, 175, 195, 23);

		btnRegisterSponsorship.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				RegisterSponsorshipAgreeController controller=new RegisterSponsorshipAgreeController(new RegisterSponsorshipAgreeModel(), new RegisterSponsorshipAgreeView());
				controller.initController(SwingMain.this);
			}
		});
		frame.getContentPane().add(btnRegisterSponsorship);
			

		JButton btnInicializarBaseDeDatos = new JButton("Inicializar Base de Datos en Blanco");
		btnInicializarBaseDeDatos.setBounds(0, 23, 197, 23);
		btnInicializarBaseDeDatos.addActionListener(new ActionListener() { //NOSONAR codigo autogenerado
			public void actionPerformed(ActionEvent e) {
				Database db=new Database();
				db.createDatabase(false);
			}
		});
		frame.getContentPane().add(btnInicializarBaseDeDatos);
			
		JButton btnCargarDatosIniciales = new JButton("Cargar Datos Iniciales para Pruebas");
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
		btnRegisterIncomeExpenses.setBounds(20, 143, 195, 23);
		btnRegisterIncomeExpenses.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterIncomeExpensesModel model = new RegisterIncomeExpensesModel();
				RegIncomeExpensesView view = new RegIncomeExpensesView();
				RegisterIncomeExpensesController controller = new RegisterIncomeExpensesController(model, view);
			}
		});
		frame.getContentPane().add(btnRegisterIncomeExpenses);
		
		JButton btnConsultEvt = new JButton("Consult Event Status");
		btnConsultEvt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConsultEventStatusController controller=new ConsultEventStatusController(new ConsultEventStatusModel(), new ConsultEventStatusView());
				controller.initController(SwingMain.this);
			}
		});
		btnConsultEvt.setBounds(20, 209, 195, 23);
		frame.getContentPane().add(btnConsultEvt);
		String fechaInit = "2025-04-01";
		this.fechaISO = Util.isoStringToDate(fechaInit);
		this.setTfChangeDate(fechaInit);

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
	public String getFechaISO() {
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
