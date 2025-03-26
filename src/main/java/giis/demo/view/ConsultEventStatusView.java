package giis.demo.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

/**
 * Vista de la pantalla para consultar el estado de las actividades.
 */
public class ConsultEventStatusView {

    private JFrame frmConsultEventStatus;
    private JTable tblEvents, tblSponsorships, tblIncome, tblExpenses;
    private JLabel lblIncomeSummary, lblIncomeEstimated, lblIncomePaid;
    private JLabel lblExpensesSummary, lblExpensesEstimated, lblExpensesPaid;
    private JButton btnExit;

    /**
     * Create the application.
     */
    public ConsultEventStatusView() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmConsultEventStatus = new JFrame();
        frmConsultEventStatus.setTitle("Consult Event Status");
        frmConsultEventStatus.setName("ConsultEventStatusView");
        frmConsultEventStatus.setBounds(100, 100, 1025, 694);
        frmConsultEventStatus.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmConsultEventStatus.getContentPane().setLayout(null);

        // Activities Table
        JLabel label = new JLabel("Activities:");
        label.setBounds(7, 7, 457, 14);
        frmConsultEventStatus.getContentPane().add(label);
        tblEvents = new JTable();
        JScrollPane spEvents = new JScrollPane(tblEvents);
        spEvents.setBounds(7, 28, 457, 218);
        frmConsultEventStatus.getContentPane().add(spEvents);

        // Sponsorships Table
        JLabel label_1 = new JLabel("Sponsorships:");
        label_1.setBounds(531, 7, 439, 14);
        frmConsultEventStatus.getContentPane().add(label_1);
        tblSponsorships = new JTable();
        JScrollPane spSponsorships = new JScrollPane(tblSponsorships);
        spSponsorships.setBounds(531, 28, 471, 218);
        frmConsultEventStatus.getContentPane().add(spSponsorships);

        // Expenses Table
        JLabel label_3 = new JLabel("Expenses:");
        label_3.setBounds(531, 257, 471, 14);
        frmConsultEventStatus.getContentPane().add(label_3);
        tblExpenses = new JTable();
        JScrollPane spExpenses = new JScrollPane(tblExpenses);
        spExpenses.setBounds(531, 278, 471, 218);
        frmConsultEventStatus.getContentPane().add(spExpenses);
                        
                                // Income Table
                                JLabel label_2 = new JLabel("Income:");
                                label_2.setBounds(7, 257, 457, 14);
                                frmConsultEventStatus.getContentPane().add(label_2);
                        tblIncome = new JTable();
                        JScrollPane spIncome = new JScrollPane(tblIncome);
                        spIncome.setBounds(7, 278, 457, 218);
                        frmConsultEventStatus.getContentPane().add(spIncome);
                
                        // Total Summary Panel
                        JPanel pnlTotal = new JPanel(new MigLayout("", "[grow]", "[][]"));
                        pnlTotal.setBounds(7, 507, 276, 141);
                        pnlTotal.setBorder(BorderFactory.createTitledBorder("Total"));
                        
                                // Agrupar Income
                                JPanel pnlIncome = new JPanel(new MigLayout("", "[grow]", "[][]"));
                                lblIncomeSummary = new JLabel("Income: 0.00");
                                pnlIncome.add(lblIncomeSummary, "wrap");
                                
                                        lblIncomeEstimated = new JLabel("Estimated: 0.00");
                                        pnlIncome.add(lblIncomeEstimated);
                                        lblIncomePaid = new JLabel("Paid: 0.00");
                                        pnlIncome.add(lblIncomePaid);
                                        
                                                pnlTotal.add(pnlIncome, "growy, wrap");
                                                
                                                        // Agrupar Expenses
                                                        JPanel pnlExpenses = new JPanel(new MigLayout("", "[grow]", "[][]"));
                                                        lblExpensesSummary = new JLabel("Expenses: 0.00");
                                                        pnlExpenses.add(lblExpensesSummary, "wrap");
                                                        
                                                                lblExpensesEstimated = new JLabel("Estimated: 0.00");
                                                                pnlExpenses.add(lblExpensesEstimated);
                                                                lblExpensesPaid = new JLabel("Paid: 0.00");
                                                                pnlExpenses.add(lblExpensesPaid);
                                                                
                                                                        pnlTotal.add(pnlExpenses, "growy");
                                                                        
                                                                                frmConsultEventStatus.getContentPane().add(pnlTotal);
                
                        // Exit Button
                        btnExit = new JButton("Exit");
                        btnExit.setBounds(880, 625, 116, 23);
                        frmConsultEventStatus.getContentPane().add(btnExit);
    
                        pnlTotal.add(pnlIncome, "growy, wrap");
                        pnlTotal.add(pnlExpenses, "growy");
                        
                        frmConsultEventStatus.setResizable(false); // Habilitar redimensionamiento


    }


    public JFrame getFrame() {
        return frmConsultEventStatus;
    }

    public JTable getTblEvents() {
        return tblEvents;
    }

    public JTable getTblSponsorships() {
        return tblSponsorships;
    }

    public JTable getTblIncome() {
        return tblIncome;
    }

    public JTable getTblExpenses() {
        return tblExpenses;
    }

    public JLabel getLblIncomeSummary() {
        return lblIncomeSummary;
    }

    public JLabel getLblIncomeEstimated() {
        return lblIncomeEstimated;
    }

    public JLabel getLblIncomePaid() {
        return lblIncomePaid;
    }

    public JLabel getLblExpensesSummary() {
        return lblExpensesSummary;
    }

    public JLabel getLblExpensesEstimated() {
        return lblExpensesEstimated;
    }

    public JLabel getLblExpensesPaid() {
        return lblExpensesPaid;
    }

    public JButton getBtnExit() {
        return btnExit;
    }

	public void setTblEvents(JTable tblEvents) {
		this.tblEvents = tblEvents;
	}

	public void setTblSponsorships(JTable tblSponsorships) {
		this.tblSponsorships = tblSponsorships;
	}

	public void setTblIncome(JTable tblIncome) {
		this.tblIncome = tblIncome;
	}

	public void setTblExpenses(JTable tblExpenses) {
		this.tblExpenses = tblExpenses;
	}

	public void setBtnExit(JButton btnExit) {
		this.btnExit = btnExit;
	}
}
