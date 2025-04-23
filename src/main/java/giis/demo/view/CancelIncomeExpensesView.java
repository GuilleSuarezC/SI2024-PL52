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

import com.toedter.calendar.JDateChooser;

import giis.demo.util.Util;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;

/**
 * Vista de la pantalla para registrar acuerdos de patrocinio.
 * Se ha generado siguiendo el patrón MVC, eliminando manejadores de eventos
 * y asegurando que la UI se pueda manipular desde el controlador.
 */
public class CancelIncomeExpensesView {

    private JFrame frmCancelIncomeExpenses;
    private JTable lstActivities;
    private JLabel lblFilters;
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JComboBox typeCB;
    private JLabel lblEndDate_1;
    private JButton btnApplyFilter;
    private JButton btnClearFields;
    private JButton btnCancelAgreement;
    private JButton btnCancelRegister;
    private JTextField startDateChooser, endDateChooser;
    private JPanel SelectedPanel;
    private JLabel lblNewLabel;
    private JLabel lblName;
    private JLabel lblDate;
    private JLabel lblAmount;
    private JLabel lblEventName;
    private JLabel lblCompensationMovement;
    private JLabel lblCompensationAmount;
    private JTextField CompAmountField;
    private JTextField DateField;
    private JTable lstMovements;

    /**
     * Create the application.
     */
    public CancelIncomeExpensesView() {
        initialize();
    }
    

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmCancelIncomeExpenses = new JFrame();
        frmCancelIncomeExpenses.setResizable(false);
        frmCancelIncomeExpenses.setTitle("Cancel Income and Expenses");
        frmCancelIncomeExpenses.setName("SponsorshipView");
        frmCancelIncomeExpenses.setBounds(0, 0, 1010, 697);
        frmCancelIncomeExpenses.getContentPane().setLayout(new MigLayout("", "[875.00px,grow]", "[21px][88.00px][15px][235.00px][223.00px,grow]"));
        
        
        JLabel lblList = new JLabel("List of Agreements/Income/Expenses");
        lblList.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmCancelIncomeExpenses.getContentPane().add(lblList, "cell 0 2,grow");
        
        JScrollPane ListPanel = new JScrollPane();
        frmCancelIncomeExpenses.getContentPane().add(ListPanel, "cell 0 3,grow");
        
        lstActivities = new JTable();
        ListPanel.setViewportView(lstActivities);
        
        JPanel DetailsPanel = new JPanel();
        frmCancelIncomeExpenses.getContentPane().add(DetailsPanel, "cell 0 1,grow");
        DetailsPanel.setLayout(null);
        
        startDateChooser = new JTextField();
        startDateChooser.setBounds(74, 14, 131, 19);
        DetailsPanel.add(startDateChooser);
        startDateChooser.setColumns(10);          
        
        lblStartDate = new JLabel("Start Date: ");
        lblStartDate.setBounds(10, 10, 86, 23);
        DetailsPanel.add(lblStartDate);        
        
        endDateChooser = new JTextField();
        endDateChooser.setColumns(10);
        endDateChooser.setBounds(332, 14, 131, 19);
        DetailsPanel.add(endDateChooser);
        
        String[] status = {"Sponsor Agreement", "Income", "Expense"};
        typeCB = new JComboBox<>(status);
        typeCB.setToolTipText("Status");
        typeCB.setBounds(551, 12, 156, 21);
        DetailsPanel.add(typeCB);
        
        btnApplyFilter = new JButton("Apply Filter");
        btnApplyFilter.setBounds(10, 55, 117, 21);
        DetailsPanel.add(btnApplyFilter);
        
        JLabel lblType = new JLabel("Type:");
        lblType.setBounds(512, 14, 103, 13);
        DetailsPanel.add(lblType);
        
        lblEndDate_1 = new JLabel("End Date: ");
        lblEndDate_1.setBounds(274, 10, 86, 23);
        DetailsPanel.add(lblEndDate_1);
        
        lblFilters = new JLabel("Filters");
        lblFilters.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmCancelIncomeExpenses.getContentPane().add(lblFilters, "cell 0 0,grow");
        
        SelectedPanel = new JPanel();
        frmCancelIncomeExpenses.getContentPane().add(SelectedPanel, "cell 0 4,grow");
        SelectedPanel.setLayout(null);
        
        lblNewLabel = new JLabel("Selected");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblNewLabel.setBounds(10, 0, 125, 22);
        SelectedPanel.add(lblNewLabel);
        
        lblName = new JLabel("Sponsorship Agreement: ");
        lblName.setBounds(10, 32, 267, 22);
        SelectedPanel.add(lblName);
        
        lblDate = new JLabel("Date of the Agreement: ");
        lblDate.setBounds(10, 66, 267, 22);
        SelectedPanel.add(lblDate);
        
        lblAmount = new JLabel("Amount: ");
        lblAmount.setBounds(10, 98, 267, 22);
        SelectedPanel.add(lblAmount);
        
        lblEventName = new JLabel("Event name: ");
        lblEventName.setBounds(10, 130, 267, 22);
        SelectedPanel.add(lblEventName);
        
        lblCompensationMovement = new JLabel("Compensation Movement");
        lblCompensationMovement.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblCompensationMovement.setBounds(447, 0, 191, 22);
        SelectedPanel.add(lblCompensationMovement);
        
        lblCompensationAmount = new JLabel("Compensation Amount: ");
        lblCompensationAmount.setBounds(397, 32, 205, 22);
        SelectedPanel.add(lblCompensationAmount);
        
        CompAmountField = new JTextField();
        CompAmountField.setBounds(532, 34, 134, 19);
        SelectedPanel.add(CompAmountField);
        CompAmountField.setColumns(10);
        
        JLabel lblDateOfPaid = new JLabel("Date of Paid: ");
        lblDateOfPaid.setBounds(454, 66, 151, 22);
        SelectedPanel.add(lblDateOfPaid);
        
        DateField = new JTextField();
        DateField.setColumns(10);
        DateField.setBounds(532, 68, 134, 19);
        SelectedPanel.add(DateField);
        
        JLabel lblMovements = new JLabel("Movements");
        lblMovements.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblMovements.setBounds(803, 0, 125, 22);
        SelectedPanel.add(lblMovements);
        
        JScrollPane MovementslPanel = new JScrollPane();
        MovementslPanel.setBounds(713, 21, 259, 169);
        SelectedPanel.add(MovementslPanel);
        
        lstMovements = new JTable();
        MovementslPanel.setViewportView(lstMovements);
        
        btnClearFields = new JButton("Clear Fields");
        btnClearFields.setBounds(453, 214, 117, 21);
        SelectedPanel.add(btnClearFields);
        
        btnCancelAgreement = new JButton("Cancel Agreement");
        btnCancelAgreement.setBounds(580, 214, 161, 21);
        SelectedPanel.add(btnCancelAgreement);
        
        btnCancelRegister = new JButton("Cancel & Register Movement");
        btnCancelRegister.setBounds(751, 214, 221, 21);
        SelectedPanel.add(btnCancelRegister);
    }

    // Getters para acceso desde el controlador
    public JButton getBtnApplyFilter() { return this.btnApplyFilter; }
    public JButton getBtnClearFields() { return this.btnClearFields; }
    public JButton getBtnCancel() { return this.btnCancelAgreement; }
    public JButton getBtnCancelRegister() { return this.btnCancelRegister; }
    public JTable getLstActivities() {return this.lstActivities;}
    public JTable getLstMovements() {return this.lstMovements;}
    public JFrame getFrame() { return this.frmCancelIncomeExpenses; }
    public String getStartDate() {return this.startDateChooser.getText();}
    public String getEndDate() {return this.endDateChooser.getText();}
    public void setStartDate(String dateStr) {this.startDateChooser.setText(dateStr);}
    public void setEndDate(String dateStr) {this.endDateChooser.setText(dateStr);}
    public JComboBox<Object> getType() { return this.typeCB; }
    public void setName(String name) {this.lblName.setText("Sponsorship Name: "+name);}
    public void setConcept(String concept) {this.lblName.setText("Concept: " +concept);}
    public void setDate(String date) {this.lblDate.setText("Date: "+date);}
    public void setAmount(double amount) {this.lblAmount.setText("Amount: " + amount+"€");}
    public void setEvent(String event) {this.lblEventName.setText("Event Name: "+event);}
    public String getAmount() {return this.CompAmountField.getText();}
    public String getDate() {return this.DateField.getText();}
	public void setCompAmountField(String compAmountField) {CompAmountField.setText(compAmountField);}
	public void setDateField(String dateField) {DateField.setText(getAmount());}
    
}

