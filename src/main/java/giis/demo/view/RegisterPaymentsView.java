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
import java.util.Date;
import java.awt.event.ActionEvent;
import javax.swing.ScrollPaneConstants;

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
    private JLabel lblEventName;
    private JLabel lblSponsorshipName;
    private JLabel lblAmount;
    private JLabel lblAgreementDate;
    private JLabel lblInvoiceIdentificator;
    private JLabel lblAmountPaid;
    private JLabel lblPaymentDate;
    private JLabel lblInvoiceDate;
    private JLabel lblAmountAlreadyPaid;

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
        frmRegisterPaymentsView.setBounds(0, 0, 842, 547);
        frmRegisterPaymentsView.getContentPane().setLayout(new MigLayout("", "[774.00px]", "[15px][193px][21px][183px][56px]"));
        
        JLabel lblPendingPayments = new JLabel("Pending Payments");
        lblPendingPayments.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmRegisterPaymentsView.getContentPane().add(lblPendingPayments, "cell 0 0,grow");
        
        JScrollPane PendingPaymentsPanel = new JScrollPane();
        frmRegisterPaymentsView.getContentPane().add(PendingPaymentsPanel, "cell 0 1,grow");
        
        lstPayments = new JTable();
        PendingPaymentsPanel.setViewportView(lstPayments);
        
        JPanel DetailsPanel = new JPanel();
        frmRegisterPaymentsView.getContentPane().add(DetailsPanel, "cell 0 3,grow");
        DetailsPanel.setLayout(null);
        
        lblEventName = new JLabel("Event Name: ");
        lblEventName.setBounds(10, 85, 232, 23);
        DetailsPanel.add(lblEventName);
        
        lblSponsorshipName = new JLabel("Sponsorship Name: ");
        lblSponsorshipName.setBounds(10, 10, 232, 23);
        DetailsPanel.add(lblSponsorshipName);
        
        lblAmount = new JLabel("Amount to pay: ");
        lblAmount.setBounds(10, 35, 232, 23);
        DetailsPanel.add(lblAmount);
        
        lblAgreementDate = new JLabel("Agreement Date:  ");
        lblAgreementDate.setBounds(10, 60, 232, 23);
        DetailsPanel.add(lblAgreementDate);
        
        lblInvoiceIdentificator = new JLabel("Invoice Identificator:  ");
        lblInvoiceIdentificator.setBounds(10, 110, 232, 23);
        DetailsPanel.add(lblInvoiceIdentificator);
        
        lblAmountPaid = new JLabel("Amount Paid: ");
        lblAmountPaid.setBounds(343, 63, 86, 23);
        DetailsPanel.add(lblAmountPaid);
        
        lblPaymentDate = new JLabel("Payment Date: ");
        lblPaymentDate.setBounds(343, 96, 86, 23);
        DetailsPanel.add(lblPaymentDate);
        
        AmountPaidField = new JTextField();
        AmountPaidField.setBounds(428, 65, 131, 19);
        DetailsPanel.add(AmountPaidField);
        AmountPaidField.setColumns(10);
        
        PaymentDateField = new JTextField();
        PaymentDateField.setColumns(10);
        PaymentDateField.setBounds(428, 98, 131, 19);
        DetailsPanel.add(PaymentDateField);
        
        lblInvoiceDate = new JLabel("Invoice Date:  ");
        lblInvoiceDate.setBounds(10, 135, 232, 23);
        DetailsPanel.add(lblInvoiceDate);
        
        lblAmountAlreadyPaid = new JLabel("Amount Already Paid: ");
        lblAmountAlreadyPaid.setBounds(365, 35, 165, 23);
        DetailsPanel.add(lblAmountAlreadyPaid);
        
        lblSelectedPayment = new JLabel("Selected Payment");
        lblSelectedPayment.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmRegisterPaymentsView.getContentPane().add(lblSelectedPayment, "cell 0 2,grow");
        
        Btnpanel = new JPanel();
        frmRegisterPaymentsView.getContentPane().add(Btnpanel, "cell 0 4,grow");
        Btnpanel.setLayout(null);
        
        btnSave = new JButton("Save");
        btnSave.setBounds(491, 25, 85, 21);
        Btnpanel.add(btnSave);
        
        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        btnCancel.setBounds(396, 25, 85, 21);
        Btnpanel.add(btnCancel);
    }

    // Getters para acceso desde el controlador
    public JTable getLstPayments() {return this.lstPayments;}
    public JFrame getFrame() { return this.frmRegisterPaymentsView; }
    public JButton getBtnRegistrar() { return this.btnSave; }
    public JButton getBtnCancelar() { return this.btnCancel; }
    public void setLblAmount(int amount) {this.lblAmount.setText("Amount to pay: " + amount+"€");}
    public void setLblSponsorshipName(String sponsorshipName) {this.lblSponsorshipName.setText("Sponsorship Name: " + sponsorshipName);}
    public void setLblSponsorshipID(int sponsorshipId) {this.lblEventName.setText("Sponsorship Id: " + sponsorshipId);}
    public void setLblFiscalNumber(String invoiceIdentificator) {this.lblInvoiceIdentificator.setText("Invoice Identificator: " + invoiceIdentificator);}
    public void setLblAgreementDate(String date) {this.lblAgreementDate.setText("Agreement Date: " + date);}
    public void setLblEventName(String eventName) {this.lblEventName.setText("Event name: " + eventName);}
    public void setLblInvoiceDate(String invoiceDate) {this.lblInvoiceDate.setText("Invoice Date: " + invoiceDate);}
    public void setLblAmountAlreadyPaid(double amountAlreadyPaid) {this.lblAmountAlreadyPaid.setText("Amount Already Paid: " + amountAlreadyPaid);}
    public String getAmountPaidField() {return this.AmountPaidField.getText();}
    public String getPaymentDate() {return this.PaymentDateField.getText();}
}
