package giis.demo.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;

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
        frmConsultEventStatus.setBounds(100, 100, 800, 514);
        frmConsultEventStatus.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frmConsultEventStatus.getContentPane().setLayout(new MigLayout("", "[grow][grow][grow][grow][grow]", "[14px][109px][14px][94px][141px][23px][]"));

        // Activities Table
        JLabel label = new JLabel("Activities:");
        frmConsultEventStatus.getContentPane().add(label, "cell 0 0, growx");
        tblEvents = new JTable();
        JScrollPane spEvents = new JScrollPane(tblEvents);
        spEvents.setPreferredSize(new Dimension(300, 80));
        frmConsultEventStatus.getContentPane().add(spEvents, "cell 0 1, growx");

        // Sponsorships Table
        JLabel label_1 = new JLabel("Sponsorships:");
        frmConsultEventStatus.getContentPane().add(label_1, "cell 1 0, growx");
        tblSponsorships = new JTable();
        JScrollPane spSponsorships = new JScrollPane(tblSponsorships);
        spSponsorships.setPreferredSize(new Dimension(300, 80));
        frmConsultEventStatus.getContentPane().add(spSponsorships, "cell 1 1 3 1, growx");

        // Income Table
        JLabel label_2 = new JLabel("Income:");
        frmConsultEventStatus.getContentPane().add(label_2, "cell 0 2, growx, aligny top");
        tblIncome = new JTable();
        JScrollPane spIncome = new JScrollPane(tblIncome);
        spIncome.setPreferredSize(new Dimension(300, 60));
        frmConsultEventStatus.getContentPane().add(spIncome, "cell 0 3,grow");

        // Expenses Table
        JLabel label_3 = new JLabel("Expenses:");
        frmConsultEventStatus.getContentPane().add(label_3, "cell 1 2, growx, aligny top");
        tblExpenses = new JTable();
        JScrollPane spExpenses = new JScrollPane(tblExpenses);
        spExpenses.setPreferredSize(new Dimension(300, 60));
        frmConsultEventStatus.getContentPane().add(spExpenses, "cell 1 3 3 1,grow");

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

        frmConsultEventStatus.getContentPane().add(pnlTotal, "cell 0 4 5 1,growx,aligny bottom");
        
                // Exit Button
                btnExit = new JButton("Exit");
                frmConsultEventStatus.getContentPane().add(btnExit, "cell 4 6,alignx left,aligny top");
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
