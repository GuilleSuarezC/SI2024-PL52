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
        frame.setTitle("Registro de Acuerdos de Patrocinio");
        frame.setName("SponsorshipView");
        frame.setBounds(0, 0, 600, 362);
        frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[][][][][][][grow][]"));
        
        JLabel lblCompany = new JLabel("Company:");
        frame.getContentPane().add(lblCompany, "flowx,cell 0 0");
        
        lstCompany = new JComboBox<>();
        frame.getContentPane().add(lstCompany, "cell 0 0,growx");
        
        JLabel lblEvent = new JLabel("Event:");
        frame.getContentPane().add(lblEvent, "flowx,cell 0 1");
        
        lstEvent = new JComboBox<>();
        frame.getContentPane().add(lstEvent, "cell 0 1,growx");
        
        lblFee = new JLabel("Fee: ");
        frame.getContentPane().add(lblFee, "cell 0 2");
        
        JLabel lblAgreementDate = new JLabel("Agreement Date (ISO):");
        frame.getContentPane().add(lblAgreementDate, "flowx,cell 0 3");
        
        txtAgreementDate = new JTextField();
        txtAgreementDate.setName("txtAgreementDate");
        frame.getContentPane().add(txtAgreementDate, "cell 0 3,growx");
        txtAgreementDate.setColumns(10);
        
        JLabel lblMiembroGB = new JLabel("Member of the Governing Board:");
        frame.getContentPane().add(lblMiembroGB, "flowx,cell 0 4");
        
        lstMiembrosGB = new JComboBox<>();
        frame.getContentPane().add(lstMiembrosGB, "cell 0 4,growx");
        
        JLabel lblContacts = new JLabel("Contacts:");
        frame.getContentPane().add(lblContacts, "flowx,cell 0 5");
        JScrollPane contactsPanel = new JScrollPane();
        frame.getContentPane().add(contactsPanel, "cell 0 5,grow");
        
        lstContacts = new JTable();
        contactsPanel.setViewportView(lstContacts);
        
        btnRegistrar = new JButton("Register");
        frame.getContentPane().add(btnRegistrar, "flowx,cell 0 6");
        
        btnCancelar = new JButton("Cancel");
        frame.getContentPane().add(btnCancelar, "cell 0 6");
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
}

