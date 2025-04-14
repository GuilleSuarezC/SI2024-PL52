package giis.demo.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Vista de la pantalla para consultar el estado de las actividades.
 */
public class LTAView {

    private JFrame frmCloseEvent;
    private JTextField tFTotalFee;
    private JTextField tFStartDate;
    private JTextField tFEndDate;
    private JTable eventsTable;
    private JTable summaryTable;
    private JComboBox<String> sponsorComboBox;
    private JComboBox<String> spLevelComboBox;

    /**
     * Create the application.
     */
    public LTAView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmCloseEvent = new JFrame();
        frmCloseEvent.setTitle("Register a Long Term Agreement");
        frmCloseEvent.setBounds(100, 100, 858, 700);
        frmCloseEvent.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmCloseEvent.getContentPane().setLayout(null);
        
        JLabel lblSponsor = new JLabel("Sponsor:");
        lblSponsor.setBounds(10, 17, 72, 14);
        frmCloseEvent.getContentPane().add(lblSponsor);
        
        sponsorComboBox = new JComboBox();
        sponsorComboBox.setBounds(92, 13, 315, 22);
        frmCloseEvent.getContentPane().add(sponsorComboBox);
        
        JLabel lblTotalFee = new JLabel("Total Fee:");
        lblTotalFee.setBounds(629, 18, 63, 14);
        frmCloseEvent.getContentPane().add(lblTotalFee);
        
        tFTotalFee = new JTextField();
        tFTotalFee.setBounds(702, 15, 130, 20);
        frmCloseEvent.getContentPane().add(tFTotalFee);
        tFTotalFee.setColumns(10);
        
        JLabel lblStartDate = new JLabel("Start Date of the agreement:");
        lblStartDate.setBounds(10, 49, 184, 14);
        frmCloseEvent.getContentPane().add(lblStartDate);
        
        tFStartDate = new JTextField();
        tFStartDate.setBounds(204, 46, 203, 20);
        tFStartDate.setColumns(10);
        frmCloseEvent.getContentPane().add(tFStartDate);
        
        JLabel lblEndDate = new JLabel("End Date of the agreement:");
        lblEndDate.setBounds(532, 49, 160, 14);
        frmCloseEvent.getContentPane().add(lblEndDate);
        
        tFEndDate = new JTextField();
        tFEndDate.setBounds(702, 46, 130, 20);
        tFEndDate.setColumns(10);
        frmCloseEvent.getContentPane().add(tFEndDate);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(10, 74, 822, 2);
        frmCloseEvent.getContentPane().add(separator);
        
        JLabel lblEvents = new JLabel("Events:");
        lblEvents.setBounds(10, 171, 46, 14);
        frmCloseEvent.getContentPane().add(lblEvents);
        
        JScrollPane scrollPaneEvents = new JScrollPane();
        scrollPaneEvents.setBounds(57, 87, 775, 193);
        frmCloseEvent.getContentPane().add(scrollPaneEvents);
        
        eventsTable = new JTable();
        scrollPaneEvents.setViewportView(eventsTable);
        
        JLabel lblSPLvL = new JLabel("Sponsorship Level:");
        lblSPLvL.setBounds(10, 301, 174, 14);
        frmCloseEvent.getContentPane().add(lblSPLvL);
        
        spLevelComboBox = new JComboBox();
        spLevelComboBox.setBounds(10, 325, 174, 22);
        frmCloseEvent.getContentPane().add(spLevelComboBox);
        
        JButton btnAddEvt_SPLvL = new JButton("Add Event & Sponsorship Level");
        btnAddEvt_SPLvL.setBounds(594, 381, 238, 42);
        frmCloseEvent.getContentPane().add(btnAddEvt_SPLvL);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setBounds(10, 434, 822, 2);
        frmCloseEvent.getContentPane().add(separator_1);
        
        JLabel lblNewLabel_1 = new JLabel("List of Events - Summary:");
        lblNewLabel_1.setBounds(10, 447, 174, 14);
        frmCloseEvent.getContentPane().add(lblNewLabel_1);
        
        JScrollPane scrollPaneSummary = new JScrollPane();
        scrollPaneSummary.setBounds(10, 472, 822, 125);
        frmCloseEvent.getContentPane().add(scrollPaneSummary);
        
        summaryTable = new JTable();
        scrollPaneSummary.setViewportView(summaryTable);
        
        JButton btnEdit = new JButton("Edit Selected Event");
        btnEdit.setBounds(10, 608, 174, 42);
        frmCloseEvent.getContentPane().add(btnEdit);
        
        JButton btnRemove = new JButton("Remove Selected Event");
        btnRemove.setBounds(318, 608, 174, 42);
        frmCloseEvent.getContentPane().add(btnRemove);
        
        JButton btnRegisterAgreement = new JButton("Register Agreement");
        btnRegisterAgreement.setBounds(594, 608, 238, 42);
        frmCloseEvent.getContentPane().add(btnRegisterAgreement);
                        
        frmCloseEvent.setResizable(false);
    }

	public JFrame getFrmCloseEvent() {
		return frmCloseEvent;
	}

	public void setFrmCloseEvent(JFrame frmCloseEvent) {
		this.frmCloseEvent = frmCloseEvent;
	}

	public String gettFTotalFee() {
		return tFTotalFee.getText();
	}

	public void settFTotalFee(String tFTotalFee) {
		this.tFTotalFee.setText(tFTotalFee);
	}

	public String gettFStartDate() {
		return tFStartDate.getText();
	}

	public void settFStartDate(String tFStartDate) {
		this.tFStartDate.setText(tFStartDate);
	}

	public String gettFEndDate() {
		return tFEndDate.getText();
	}

	public void settFEndDate(String tFEndDate) {
		this.tFEndDate.setText(tFEndDate);
	}

	public JTable getEventsTable() {
		return eventsTable;
	}

	public void setEventsTable(JTable eventsTable) {
		this.eventsTable = eventsTable;
	}

	public JTable getSummaryTable() {
		return summaryTable;
	}

	public void setSummaryTable(JTable summaryTable) {
		this.summaryTable = summaryTable;
	}

	public JComboBox<String> getSponsorComboBox() {
		return sponsorComboBox;
	}

	public JComboBox<String> getSpLevelComboBox() {
		return spLevelComboBox;
	}
}