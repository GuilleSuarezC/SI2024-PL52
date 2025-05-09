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
public class ReportIncomeExpensesView {

    private JFrame frmReportIncomeExpenses;
    private JTable lstActivities;
    private JLabel lblFilters;
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JComboBox activityStatusCB;
    private JLabel lblEndDate_1;
    private JButton btnApplyFilter;
    private JTextField startDateChooser, endDateChooser;

    /**
     * Create the application.
     */
    public ReportIncomeExpensesView() {
        initialize();
    }
    

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmReportIncomeExpenses = new JFrame();
        frmReportIncomeExpenses.setResizable(false);
        frmReportIncomeExpenses.setTitle("Report Income and Expenses");
        frmReportIncomeExpenses.setName("SponsorshipView");
        frmReportIncomeExpenses.setBounds(0, 0, 1462, 537);
        frmReportIncomeExpenses.getContentPane().setLayout(new MigLayout("", "[1431px]", "[21px][88px][15px][319px]"));
        
     // Crear los selectores de fecha
        
        
        JLabel lblReport = new JLabel("Report of Activities");
        lblReport.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmReportIncomeExpenses.getContentPane().add(lblReport, "cell 0 2,growx,aligny top");
        
        JScrollPane ReportsPanel = new JScrollPane();
        frmReportIncomeExpenses.getContentPane().add(ReportsPanel, "cell 0 3,grow");
        
        lstActivities = new JTable();
        ReportsPanel.setViewportView(lstActivities);
        
        JPanel DetailsPanel = new JPanel();
        frmReportIncomeExpenses.getContentPane().add(DetailsPanel, "cell 0 1,grow");
        DetailsPanel.setLayout(null);
        
        startDateChooser = new JTextField();
        startDateChooser.setBounds(66, 14, 131, 19);
        DetailsPanel.add(startDateChooser);
        startDateChooser.setColumns(10);          
        
        lblStartDate = new JLabel("Start Date: ");
        lblStartDate.setBounds(10, 10, 86, 23);
        DetailsPanel.add(lblStartDate);        
        
        endDateChooser = new JTextField();
        endDateChooser.setColumns(10);
        endDateChooser.setBounds(326, 14, 131, 19);
        DetailsPanel.add(endDateChooser);
        
        String[] status = {"All", "Planned", "Ongoing", "Completed", "Closed"};
        activityStatusCB = new JComboBox<>(status);
        activityStatusCB.setToolTipText("Status");
        activityStatusCB.setBounds(646, 12, 131, 21);
        DetailsPanel.add(activityStatusCB);
        
        btnApplyFilter = new JButton("Apply Filter");
        btnApplyFilter.setBounds(10, 55, 117, 21);
        DetailsPanel.add(btnApplyFilter);
        
        JLabel lblStatus = new JLabel("Status of the Activities:");
        lblStatus.setBounds(508, 14, 182, 13);
        DetailsPanel.add(lblStatus);
        
        lblEndDate_1 = new JLabel("End Date: ");
        lblEndDate_1.setBounds(274, 10, 86, 23);
        DetailsPanel.add(lblEndDate_1);
        
        lblFilters = new JLabel("Filters");
        lblFilters.setFont(new Font("Tahoma", Font.PLAIN, 12));
        frmReportIncomeExpenses.getContentPane().add(lblFilters, "cell 0 0,grow");
    }

    // Getters para acceso desde el controlador
    public JButton getBtnApplyFilter() { return this.btnApplyFilter; }
    public JTable getLstActivities() {return this.lstActivities;}
    public JFrame getFrame() { return this.frmReportIncomeExpenses; }
    public String getStartDate() {return this.startDateChooser.getText();}
    public String getEndDate() {return this.endDateChooser.getText();}
    public void setStartDate(String dateStr) {this.startDateChooser.setText(dateStr);}
    public void setEndDate(String dateStr) {this.endDateChooser.setText(dateStr);}
    public JComboBox<Object> getStatus() { return this.activityStatusCB; }
}

