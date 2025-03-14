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
        frmConsultEventStatus.setBounds(100, 100, 1000, 532);
        frmConsultEventStatus.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmConsultEventStatus.getContentPane().setLayout(new MigLayout("", "[457px][53px][67px][274px][116px]", "[14px][141px][14px][141px][141px]"));

        // Activities Table
        JLabel label = new JLabel("Activities:");
        frmConsultEventStatus.getContentPane().add(label, "cell 0 0,growx,aligny top");
        tblEvents = new JTable();
        JScrollPane spEvents = new JScrollPane(tblEvents);
        frmConsultEventStatus.getContentPane().add(spEvents, "cell 0 1,grow");

        // Sponsorships Table
        JLabel label_1 = new JLabel("Sponsorships:");
        frmConsultEventStatus.getContentPane().add(label_1, "cell 2 0 3 1,growx,aligny top");
        tblSponsorships = new JTable();
        JScrollPane spSponsorships = new JScrollPane(tblSponsorships);
        frmConsultEventStatus.getContentPane().add(spSponsorships, "cell 2 1 3 1,grow");

        // Expenses Table
        JLabel label_3 = new JLabel("Expenses:");
        frmConsultEventStatus.getContentPane().add(label_3, "cell 2 2 3 1,growx,aligny top");
        tblExpenses = new JTable();
        JScrollPane spExpenses = new JScrollPane(tblExpenses);
        frmConsultEventStatus.getContentPane().add(spExpenses, "cell 2 3 3 1,grow");
                        
                                // Income Table
                                JLabel label_2 = new JLabel("Income:");
                                frmConsultEventStatus.getContentPane().add(label_2, "cell 0 2,growx,aligny top");
                        tblIncome = new JTable();
                        JScrollPane spIncome = new JScrollPane(tblIncome);
                        frmConsultEventStatus.getContentPane().add(spIncome, "cell 0 3,grow");
                
                        // Total Summary Panel
                        JPanel pnlTotal = new JPanel(new MigLayout("", "[grow]", "[][]"));
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
                                                                        
                                                                                frmConsultEventStatus.getContentPane().add(pnlTotal, "cell 0 4,alignx left,aligny top");
                
                        // Exit Button
                        btnExit = new JButton("Exit");
                        frmConsultEventStatus.getContentPane().add(btnExit, "cell 4 4,growx,aligny bottom");
    
                        pnlTotal.add(pnlIncome, "growy, wrap");
                        pnlTotal.add(pnlExpenses, "growy");
                        
                        frmConsultEventStatus.setResizable(true); // Habilitar redimensionamiento
                        frmConsultEventStatus.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximizar al iniciar


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
