package giis.demo.view;

import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Vista de la pantalla para consultar el estado de las actividades.
 */
public class ConsultEventStatusView {

    private JFrame frmConsultEventStatus;
    private JTable tblEvents, tblSponsorships, tblIncome, tblExpenses;
    private JLabel lblBalanceEstimated, lblIncomeEstimated, lblIncomePaid;
    private JLabel lblBalancePaid, lblExpensesEstimated, lblExpensesPaid;
    private JLabel lblSponsorshipsEstimated, lblSponsorshipsPaid;
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
        frmConsultEventStatus.setBounds(100, 100, 1088, 694);
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
        spSponsorships.setBounds(531, 28, 533, 218);
        frmConsultEventStatus.getContentPane().add(spSponsorships);
        
        lblSponsorshipsEstimated = new JLabel("Estimated Total: ");
        lblSponsorshipsEstimated.setBounds(531, 250, 200, 14);
        frmConsultEventStatus.getContentPane().add(lblSponsorshipsEstimated);
        
        lblSponsorshipsPaid = new JLabel("Paid Total: ");
        lblSponsorshipsPaid.setBounds(731, 250, 200, 14);
        frmConsultEventStatus.getContentPane().add(lblSponsorshipsPaid);

        // Expenses Table
        JLabel label_3 = new JLabel("Expenses:");
        label_3.setBounds(531, 275, 471, 14);
        frmConsultEventStatus.getContentPane().add(label_3);
        tblExpenses = new JTable();
        JScrollPane spExpenses = new JScrollPane(tblExpenses);
        spExpenses.setBounds(531, 296, 533, 218);
        frmConsultEventStatus.getContentPane().add(spExpenses);
        
        lblExpensesEstimated = new JLabel("Estimated Total: ");
        lblExpensesPaid = new JLabel("Paid Total: ");
        
        lblExpensesEstimated.setBounds(531, 518, 200, 14);
        lblExpensesPaid.setBounds(731, 518, 200, 14);
        
        frmConsultEventStatus.getContentPane().add(lblExpensesEstimated);
        frmConsultEventStatus.getContentPane().add(lblExpensesPaid);

        // Income Table
        JLabel label_2 = new JLabel("Income:");
        label_2.setBounds(7, 275, 457, 14);
        frmConsultEventStatus.getContentPane().add(label_2);
        tblIncome = new JTable();
        JScrollPane spIncome = new JScrollPane(tblIncome);
        spIncome.setBounds(7, 296, 457, 218);
        frmConsultEventStatus.getContentPane().add(spIncome);
        
        lblIncomeEstimated = new JLabel("Estimated Total: ");
        lblIncomePaid = new JLabel("Paid Total: ");
        
        lblIncomeEstimated.setBounds(7, 518, 200, 14);
        lblIncomePaid.setBounds(207, 518, 200, 14);
        
        frmConsultEventStatus.getContentPane().add(lblIncomeEstimated);
        frmConsultEventStatus.getContentPane().add(lblIncomePaid);
        
        // Total Summary Panel
        JPanel pnlTotal = new JPanel(new MigLayout("", "[grow][grow]", "[][]"));
        pnlTotal.setBounds(7, 566, 500, 78);
        pnlTotal.setBorder(new TitledBorder(null, "Balance", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        // Income Summary
        JPanel pnlIncome = new JPanel(new MigLayout("", "[grow]", "[][]"));
        lblBalanceEstimated = new JLabel("Estimated:");
        pnlIncome.add(lblBalanceEstimated, "wrap");
        //pnlIncome.add(lblIncomeEstimated);
        //pnlIncome.add(lblIncomePaid);
        
        // Expenses Summary
        JPanel pnlExpenses = new JPanel(new MigLayout("", "[grow]", "[][]"));
        lblBalancePaid = new JLabel("Paid:");
        pnlExpenses.add(lblBalancePaid, "wrap");
        //pnlExpenses.add(lblExpensesEstimated);
        //pnlExpenses.add(lblExpensesPaid);
        
        pnlTotal.add(pnlIncome, "grow");
        pnlTotal.add(pnlExpenses, "grow");
        frmConsultEventStatus.getContentPane().add(pnlTotal);
        
        // Exit Button
        btnExit = new JButton("Exit");
        btnExit.setBounds(948, 624, 116, 23);
        frmConsultEventStatus.getContentPane().add(btnExit);
        
        frmConsultEventStatus.setResizable(false);
    }

    public JFrame getFrame() {
        return frmConsultEventStatus;
    }

	public JFrame getFrmConsultEventStatus() {
		return frmConsultEventStatus;
	}

	public void setFrmConsultEventStatus(JFrame frmConsultEventStatus) {
		this.frmConsultEventStatus = frmConsultEventStatus;
	}

	public JTable getTblEvents() {
		return tblEvents;
	}

	public void setTblEvents(JTable tblEvents) {
		this.tblEvents = tblEvents;
	}

	public JTable getTblSponsorships() {
		return tblSponsorships;
	}

	public void setTblSponsorships(JTable tblSponsorships) {
		this.tblSponsorships = tblSponsorships;
	}

	public JTable getTblIncome() {
		return tblIncome;
	}

	public void setTblIncome(JTable tblIncome) {
		this.tblIncome = tblIncome;
	}

	public JTable getTblExpenses() {
		return tblExpenses;
	}

	public void setTblExpenses(JTable tblExpenses) {
		this.tblExpenses = tblExpenses;
	}

	public JLabel getLblIncomeSummary() {
		return lblBalanceEstimated;
	}

	public void setLblIncomeSummary(JLabel lblIncomeSummary) {
		this.lblBalanceEstimated = lblIncomeSummary;
	}

	public JLabel getLblIncomeEstimated() {
		return lblIncomeEstimated;
	}

	public void setLblIncomeEstimated(double totalInc) {
		this.lblIncomeEstimated.setText("Estimated Total: "+totalInc+" €");
	}

	public JLabel getLblIncomePaid() {
		return lblIncomePaid;
	}

	public void setLblIncomePaid(double paidInc) {
		this.lblIncomePaid.setText("Paid Total: "+paidInc+" €");
	}

	public JLabel getLblExpensesSummary() {
		return lblBalancePaid;
	}

	public void setLblExpensesSummary(JLabel lblExpensesSummary) {
		this.lblBalancePaid = lblExpensesSummary;
	}

	public JLabel getLblExpensesEstimated() {
		return lblExpensesEstimated;
	}

	public void setLblExpensesEstimated(double totalExp) {
		this.lblExpensesEstimated.setText("Estimated Total: "+totalExp+" €");
	}

	public JLabel getLblExpensesPaid() {
		return lblExpensesPaid;
	}

	public void setLblExpensesPaid(double num) {
		this.lblExpensesPaid.setText("Paid Total: "+num+" €");
	}

	public JButton getBtnExit() {
		return btnExit;
	}

	public void setBtnExit(JButton btnExit) {
		this.btnExit = btnExit;
	}

	public JLabel getLblBalanceEstimated() {
		return lblBalanceEstimated;
	}

	public void setLblBalanceEstimated(JLabel lblBalanceEstimated) {
		this.lblBalanceEstimated = lblBalanceEstimated;
	}

	public JLabel getLblBalancePaid() {
		return lblBalancePaid;
	}

	public void setLblBalancePaid(JLabel lblBalancePaid) {
		this.lblBalancePaid = lblBalancePaid;
	}
	
	public void setLblSponsorshipsEstimated(double total) {
		this.lblSponsorshipsEstimated.setText("Estimated Total: "+total + " €");
	}
	
	public void setLblSponsorshipsPaid(double num) {
		this.lblSponsorshipsPaid.setText("Paid Total: "+num + " €");
	}
}
