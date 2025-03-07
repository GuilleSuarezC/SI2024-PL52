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
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Vista de la pantalla para registrar acuerdos de patrocinio.
 * Se ha generado siguiendo el patrón MVC, eliminando manejadores de eventos
 * y asegurando que la UI se pueda manipular desde el controlador.
 */
public class RegisterPaymentsView {

    private JFrame frmRegisterPaymentsView;
    private JTable lstPayments;
    private JTextField AmountPaidField;
    private JTextField PaymentDateField;
    private JLabel lblSelectedPayment;
    private JPanel Btnpanel;
    private JButton btnSave;
    private JButton btnCancel;
    private JLabel lblSponsorshipID;
    private JLabel lblSponsorshipName;
    private JLabel lblAmount;
    private JLabel lblAgreementDate;
    private JLabel lblInoviceId;
    private JLabel lblAmountPaid;
    private JLabel lblPaymentDate;

    /**
     * Create the application.
     */
    public RegisterPaymentsView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmRegisterPaymentsView = new JFrame();
        frmRegisterPaymentsView.setTitle("Register Payments ");
        frmRegisterPaymentsView.setName("SponsorshipView");
        frmRegisterPaymentsView.setBounds(0, 0, 600, 528);
        frmRegisterPaymentsView.getContentPane().setLayout(new MigLayout("", "[586px]", "[15px][193px][21px][183px][56px]"));
        
        JLabel lblPendingPayments = new JLabel("Pending Payments");
        lblPendingPayments.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmRegisterPaymentsView.getContentPane().add(lblPendingPayments, "cell 0 0,grow");
        
        JScrollPane PendingPaymentsPanel = new JScrollPane();
        frmRegisterPaymentsView.getContentPane().add(PendingPaymentsPanel, "cell 0 1,grow");
        
        lstPayments = new JTable();
        PendingPaymentsPanel.setColumnHeaderView(lstPayments);
        
        JPanel DetailsPanel = new JPanel();
        frmRegisterPaymentsView.getContentPane().add(DetailsPanel, "cell 0 3,grow");
        DetailsPanel.setLayout(null);
        
        lblSponsorshipID = new JLabel("Sponsorship ID: ");
        lblSponsorshipID.setBounds(10, 10, 232, 23);
        DetailsPanel.add(lblSponsorshipID);
        
        lblSponsorshipName = new JLabel("Sponsorship Name: ");
        lblSponsorshipName.setBounds(10, 43, 232, 23);
        DetailsPanel.add(lblSponsorshipName);
        
        lblAmount = new JLabel("Amount to pay: ");
        lblAmount.setBounds(10, 79, 232, 23);
        DetailsPanel.add(lblAmount);
        
        lblAgreementDate = new JLabel("Agreement Date:  ");
        lblAgreementDate.setBounds(10, 112, 232, 23);
        DetailsPanel.add(lblAgreementDate);
        
        lblInoviceId = new JLabel("Inovice ID:  ");
        lblInoviceId.setBounds(10, 145, 232, 23);
        DetailsPanel.add(lblInoviceId);
        
        lblAmountPaid = new JLabel("Amount Paid: ");
        lblAmountPaid.setBounds(357, 63, 72, 23);
        DetailsPanel.add(lblAmountPaid);
        
        lblPaymentDate = new JLabel("Payment Date: ");
        lblPaymentDate.setBounds(357, 96, 72, 23);
        DetailsPanel.add(lblPaymentDate);
        
        AmountPaidField = new JTextField();
        AmountPaidField.setBounds(428, 65, 131, 19);
        DetailsPanel.add(AmountPaidField);
        AmountPaidField.setColumns(10);
        
        PaymentDateField = new JTextField();
        PaymentDateField.setColumns(10);
        PaymentDateField.setBounds(428, 98, 131, 19);
        DetailsPanel.add(PaymentDateField);
        
        lblSelectedPayment = new JLabel("Selected Payment");
        lblSelectedPayment.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmRegisterPaymentsView.getContentPane().add(lblSelectedPayment, "cell 0 2,grow");
        
        Btnpanel = new JPanel();
        frmRegisterPaymentsView.getContentPane().add(Btnpanel, "cell 0 4,grow");
        Btnpanel.setLayout(null);
        
        btnSave = new JButton("Save");
        btnSave.setBounds(154, 10, 85, 21);
        Btnpanel.add(btnSave);
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnCancel.setBounds(323, 10, 85, 21);
        Btnpanel.add(btnCancel);
    }

    // Getters para acceso desde el controlador
    public JFrame getFrame() { return this.frmRegisterPaymentsView; }
    public JButton getBtnRegistrar() { return this.btnSave; }
    public JButton getBtnCancelar() { return this.btnCancel; }
    public void setLblAmount(int amount) {this.lblAmount.setText("Amount to pay: " + amount);}
    public void setLblSponsorshipName(String sponsorshipName) {this.lblSponsorshipName.setText("Sponsorship Name: " + sponsorshipName);}
    public void setLblSponsorshipID(int sponsorshipId) {this.lblSponsorshipID.setText("Sponsorship Id: " + sponsorshipId);}
    public void setLblInvoiceId(String invoiceId) {this.lblInoviceId.setText("Invoice Id: " + invoiceId);}
    public void setLblAgreementDate(String date) {this.lblAgreementDate.setText("Agreement Date: " + date);}
    public String getAmountPaidField() {return this.AmountPaidField.getText();}
    public String getPaymentDate() {return this.PaymentDateField.getText();}
}

