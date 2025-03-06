package giis.demo.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RegisterPaymentsView {
    private JFrame frame;
    private JTable table;
    private JTextField tfAmountPaid;
    private JTextField tfPaymentDate;
    private JButton btnSave;
    private JButton btnCancel;
    private DefaultTableModel tableModel;
    private JPanel AddPaymentsPanel;
    private JButton btnNewButton;

    public RegisterPaymentsView() {
        frame = new JFrame("Register Payments");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        // Tabla de pagos
        tableModel = new DefaultTableModel(new Object[]{"Sponsorship ID", "Amount", "Agreement Date", "Invoice ID"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane, BorderLayout.NORTH);

        // Panel de detalles
        JPanel detailsPanel = new JPanel(new GridLayout(3, 2));
        detailsPanel.add(new JLabel("Amount Paid:"));
        tfAmountPaid = new JTextField();
        detailsPanel.add(tfAmountPaid);
        
        detailsPanel.add(new JLabel("Payment Date (YYYY-MM-DD):"));
        tfPaymentDate = new JTextField();
        detailsPanel.add(tfPaymentDate);
        
        frame.getContentPane().add(detailsPanel, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel();
        btnSave = new JButton("Save Payment");
        btnCancel = new JButton("Cancel Payment");
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        AddPaymentsPanel = new JPanel();
        frame.getContentPane().add(AddPaymentsPanel, BorderLayout.WEST);
        
        btnNewButton = new JButton("New button");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        	}
        });
        AddPaymentsPanel.add(btnNewButton);
    }

    public void showView() {
        frame.setVisible(true);
    }

    public void setTableData(List<Object[]> data) {
        tableModel.setRowCount(0); // Limpiar tabla
        for (Object[] row : data) {
            tableModel.addRow(row);
        }
    }

    public JFrame getFrame() { return this.frame; }
    public JTable getTable() { return table; }
    public String getTfAmountPaid() { return tfAmountPaid.getText(); }
    public String getTfPaymentDate() { return tfPaymentDate.getText(); }
    public JButton getBtnSave() { return btnSave; }
    public JButton getBtnCancel() { return btnCancel; }
}
