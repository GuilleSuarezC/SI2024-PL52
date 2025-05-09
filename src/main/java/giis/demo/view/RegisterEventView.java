package giis.demo.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import giis.demo.model.SponsorshipLevelDTO;
import giis.demo.util.SwingUtil;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * Vista de la pantalla para consultar el estado de las actividades.
 */
public class RegisterEventView {

    private JFrame frmRegisterEvents;
    private JButton btnExit;
    private JTextField tFeventName;
    private JTextField tFstartDate;
    private JTextField tFeventEdition;
    private JTextField tFendDate;
    private JButton btnRegisterEvent;
    private JButton btnAddSponsorshipLevels;
    private JTable sponsorshipLevelsTable;
    private DefaultTableModel sponsorshipLevelsTableModel;
    private JButton btnSave;


    private List<SponsorshipLevelDTO> sponsorshipLevels = new ArrayList<>(); // temporal


    
    /**
     * Create the application.
     */
    public RegisterEventView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmRegisterEvents = new JFrame();
        frmRegisterEvents.setTitle("Register New Events");
        frmRegisterEvents.setName("RegisterEventView");
        frmRegisterEvents.setBounds(150, 150, 900, 300);
        frmRegisterEvents.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmRegisterEvents.getContentPane().setLayout(null);
                
                        // Exit Button
                        btnExit = new JButton("Exit");
                        btnExit.setBounds(737, 215, 116, 23);
                        frmRegisterEvents.getContentPane().add(btnExit);
                        
                        JLabel lblName = new JLabel("Name of the event:");
                        lblName.setBounds(27, 28, 280, 14);
                        frmRegisterEvents.getContentPane().add(lblName);
                        
                        tFeventName = new JTextField();
                        tFeventName.setBounds(27, 44, 280, 20);
                        frmRegisterEvents.getContentPane().add(tFeventName);
                        tFeventName.setColumns(10);
                        
                        JLabel lblStartDate = new JLabel("Start date of the event");
                        lblStartDate.setBounds(27, 91, 280, 14);
                        frmRegisterEvents.getContentPane().add(lblStartDate);
                        
                        tFstartDate = new JTextField();
                        tFstartDate.setColumns(10);
                        tFstartDate.setBounds(27, 106, 280, 20);
                        frmRegisterEvents.getContentPane().add(tFstartDate);
                        
                        JLabel lblEdition = new JLabel("Name of the edition:");
                        lblEdition.setBounds(436, 28, 280, 14);
                        frmRegisterEvents.getContentPane().add(lblEdition);
                        
                        tFeventEdition = new JTextField();
                        tFeventEdition.setColumns(10);
                        tFeventEdition.setBounds(436, 44, 280, 20);
                        frmRegisterEvents.getContentPane().add(tFeventEdition);
                        
                        JLabel lblEndDate = new JLabel("End date of the event");
                        lblEndDate.setBounds(436, 91, 280, 14);
                        frmRegisterEvents.getContentPane().add(lblEndDate);
                        
                        tFendDate = new JTextField();
                        tFendDate.setColumns(10);
                        tFendDate.setBounds(436, 106, 280, 20);
                        frmRegisterEvents.getContentPane().add(tFendDate);
                        
                        
                        
                        JLabel lblEventStatus = new JLabel("Status of the event:");
                        lblEventStatus.setBounds(436, 155, 280, 14);
                        frmRegisterEvents.getContentPane().add(lblEventStatus);
                        
                        JLabel lblEventStatusPlaceholder = new JLabel("The event will be of type  \"Planned\", \"Ongoing\", \"Completed\".");
                        lblEventStatusPlaceholder.setBounds(436, 173, 424, 14);
                        frmRegisterEvents.getContentPane().add(lblEventStatusPlaceholder);
                        
                        btnRegisterEvent = new JButton("Register Event");
                        btnRegisterEvent.addActionListener(new ActionListener() {
                        	public void actionPerformed(ActionEvent e) {
                        	}
                        });
                        btnRegisterEvent.setBounds(436, 215, 116, 23);
                        frmRegisterEvents.getContentPane().add(btnRegisterEvent);
                        
                     
                        
                        frmRegisterEvents.setResizable(false); // Habilitar redimensionamiento

                     // Nueva sección para niveles de patrocinio
                        JPanel sponsorshipPanel = new JPanel(new BorderLayout());
                        sponsorshipPanel.setBorder(BorderFactory.createTitledBorder("Sponsorship Levels"));

                        JPanel inputPanel = new JPanel(new GridLayout(1, 4, 5, 5));
                        JTextField txtLevelName = new JTextField();
                        JTextField txtLevelAmount = new JTextField();
                        JButton btnAddLevel = new JButton("Add Level");

                        inputPanel.add(new JLabel("Name:"));
                        inputPanel.add(txtLevelName);
                        inputPanel.add(new JLabel("Amount:"));
                        inputPanel.add(txtLevelAmount);

                        
                        


    
    }

	public JFrame getFrmRegisterEvents() {
		return frmRegisterEvents;
	}

	public void setFrmRegisterEvents(JFrame frmRegisterEvents) {
		this.frmRegisterEvents = frmRegisterEvents;
	}

	public JButton getBtnExit() {
		return btnExit;
	}

	public void setBtnExit(JButton btnExit) {
		this.btnExit = btnExit;
	}

	public void setBtnRegisterEvent(JButton btnRegisterEvent) {
		this.btnRegisterEvent = btnRegisterEvent;
	}
	
	public JButton getRegisterEvent() {
		return btnRegisterEvent;
	}

	public JTextField gettFeventName() {
		return tFeventName;
	}

	public void settFeventName(JTextField tFeventName) {
		this.tFeventName = tFeventName;
	}

	public JTextField gettFstartDate() {
		return tFstartDate;
	}

	public void settFstartDate(JTextField tFstartDate) {
		this.tFstartDate = tFstartDate;
	}

	public JTextField gettFeventEdition() {
		return tFeventEdition;
	}

	public void settFeventEdition(JTextField tFeventEdition) {
		this.tFeventEdition = tFeventEdition;
	}

	public JTextField gettFendDate() {
		return tFendDate;
	}

	public void settFendDate(JTextField tFendDate) {
		this.tFendDate = tFendDate;
	}
	
	
	
	
	public void clearFields() {
		this.tFendDate.setText("");
		this.tFeventEdition.setText("");
		this.tFeventName.setText("");
		this.tFstartDate.setText("");

	}
	
	public List<SponsorshipLevelDTO> getSponsorshipLevels() {
		return sponsorshipLevels;
	}
	
	public JButton getBtnSave() {
	    return btnSave;
	}


}
