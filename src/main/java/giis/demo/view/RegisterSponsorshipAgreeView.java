
package giis.demo.view;

import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import java.awt.Dimension;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.ScrollPaneConstants;

/**
 * Vista de la pantalla para registrar acuerdos de patrocinio.
 * Se ha generado siguiendo el patrón MVC, eliminando manejadores de eventos
 * y asegurando que la UI se pueda manipular desde el controlador.
 */
public class RegisterSponsorshipAgreeView {
    private JFrame frame;
    private JComboBox<Object> lstCompany;
    private JComboBox<Object> lstEvent;
    private JLabel lblFee;
    private JTextField txtAgreementDate;
    private JComboBox<Object> lstMiembrosGB;
    private JButton btnRegistrar;
    private JButton btnCancelar;
    private JTable lstContacts;
    private JTable lstSponsorshipLevels;
    private JButton btnAddLevel;
    private JTextField tFeventFee;
    private JLabel lbleuroFee;

    /**
     * Create the application.
     */
    public RegisterSponsorshipAgreeView() {
        initialize();
    }

    public void setComboBoxIndexes(int index) {
        lstCompany.setSelectedIndex(index);
        lstEvent.setSelectedIndex(index);
        lstMiembrosGB.setSelectedIndex(index);
//        lstContacts.removeAll();
//        lstContacts.revalidate();
//        lstContacts.repaint();
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

    	frame = new JFrame();
        frame.setTitle("Register Sponsorship Agreements");
        frame.setBounds(0, 0, 871, 645);
        frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][][][][][pref!][][pref!][pref!][]"));

        // Company
        JLabel lblCompany = new JLabel("Company:");
        frame.getContentPane().add(lblCompany, "cell 0 0,alignx left");
        lstCompany = new JComboBox<>();
        frame.getContentPane().add(lstCompany, "cell 0 0,growx");

        // Event
        JLabel lblEvent = new JLabel("Event:");
        frame.getContentPane().add(lblEvent, "cell 0 1,alignx left");
        lstEvent = new JComboBox<>();
        frame.getContentPane().add(lstEvent, "cell 0 1,growx");

        // Fee
        lblFee = new JLabel("Fee: ");
        frame.getContentPane().add(lblFee, "flowx,cell 0 2");
        

        // Agreement Date
        JLabel lblAgreementDate = new JLabel("Agreement Date (ISO):");
        frame.getContentPane().add(lblAgreementDate, "cell 0 3,alignx left");
        txtAgreementDate = new JTextField();
        txtAgreementDate.setName("txtAgreementDate");
        frame.getContentPane().add(txtAgreementDate, "cell 0 3,growx");

        // Governing Board Member
        JLabel lblMiembroGB = new JLabel("Member of the Governing Board:");
        frame.getContentPane().add(lblMiembroGB, "cell 0 4,alignx left");
        lstMiembrosGB = new JComboBox<>();
        frame.getContentPane().add(lstMiembrosGB, "cell 0 4,growx");

     // Contacts Table
        JLabel lblContacts = new JLabel("Contacts:");
        frame.getContentPane().add(lblContacts, "cell 0 5,alignx left,aligny center");
        JScrollPane contactsPanel = new JScrollPane();
        lstContacts = new JTable();
        lstContacts.setPreferredScrollableViewportSize(new Dimension(500, 60));
        contactsPanel.setViewportView(lstContacts);
        frame.getContentPane().add(contactsPanel, "cell 0 5,growx,height 200px::160px");

        // Sponsorship Levels
        JLabel lblSponsorshipLevels = new JLabel("Sponsorship Levels:");
        frame.getContentPane().add(lblSponsorshipLevels, "cell 0 6,alignx left,aligny center");
        
        JScrollPane levelsPanel = new JScrollPane();
        levelsPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        lstSponsorshipLevels = new JTable();
        lstSponsorshipLevels.setPreferredScrollableViewportSize(new Dimension(500, 60));
        levelsPanel.setViewportView(lstSponsorshipLevels);
        frame.getContentPane().add(levelsPanel, "cell 0 6 1 2,growx,height 200px::100px");
        
        lstSponsorshipLevels.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = lstSponsorshipLevels.getSelectedRow();
                if (selectedRow != -1 && lstSponsorshipLevels.getColumnCount() >= 2) {
                    Object value = lstSponsorshipLevels.getValueAt(selectedRow, 1); // columna 2 (índice 1)
                    if (value != null) {
                        tFeventFee.setText(value.toString());
                    }
                }
            }
        });

        
        // Botón Add Level debajo de la etiqueta y al lado de la tabla
        btnAddLevel = new JButton("Add Level");
        frame.getContentPane().add(btnAddLevel, "cell 0 6,aligny center");
        
        tFeventFee = new JTextField();
        frame.getContentPane().add(tFeventFee, "cell 0 2");
    	tFeventFee.setEditable(true);
    	
        tFeventFee.setColumns(10);
        
        lbleuroFee = new JLabel("€");
        frame.getContentPane().add(lbleuroFee, "cell 0 2,alignx left,aligny center");
        
                // Botones Register y Cancel debajo de la tabla
                btnRegistrar = new JButton("Register");
                frame.getContentPane().add(btnRegistrar, "flowx,cell 0 9,alignx right,aligny center");
                btnCancelar = new JButton("Cancel");
                frame.getContentPane().add(btnCancelar, "cell 0 9");
    }

    public String gettFeventFee() {
		return tFeventFee.getText();
	}

	public void settFeventFee(String tFeventFee) {
		this.tFeventFee.setText(tFeventFee);
	}

	// Getters para acceso desde el controlador
    public JFrame getFrame() { return this.frame; }
    public JComboBox<Object> getListaCompany() { return this.lstCompany; }
    public JComboBox<Object> getListaEvent() { return this.lstEvent; }
    public JLabel getLblFee() { return this.lblFee; }
    public String getAgreementDate() { return this.txtAgreementDate.getText(); }
    public void setAgreementDate(String fecha) { this.txtAgreementDate.setText(fecha); }
    public JComboBox<Object> getListaMiembrosGB() { return this.lstMiembrosGB; }
    public JTable getListaContacts() { return this.lstContacts; }
    public JButton getBtnRegistrar() { return this.btnRegistrar; }
    public JButton getBtnCancelar() { return this.btnCancelar; }
    public void setLlbFee(String fee) { this.lblFee.setText("Fee: "+fee);}

    public JTable getListaSponsorshipLevels() { 
        return this.lstSponsorshipLevels; 
    }
    public JButton getBtnAddLevel() { 
        return this.btnAddLevel; 
    }

    public JTable getListSponsorshipLevels() { 
        return this.lstSponsorshipLevels; 
    }



	
}