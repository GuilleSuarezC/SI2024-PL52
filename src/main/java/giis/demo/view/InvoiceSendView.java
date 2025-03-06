package giis.demo.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class InvoiceSendView {
    private JFrame frame;
    private JTable table;
    private JButton generateButton;
    private DefaultTableModel tableModel;
    private JComboBox<String> eventComboBox;
    private JButton loadSponsorsButton;
    JTextField invoiceDateField = new JTextField(10);

    public InvoiceSendView() {
        frame = new JFrame("Generate Invoices");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel superior con selección de evento
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel eventLabel = new JLabel("Select Event:");
        eventComboBox = new JComboBox<>();
        loadSponsorsButton = new JButton("Load Sponsors");

        topPanel.add(eventLabel);
        topPanel.add(eventComboBox);
        topPanel.add(loadSponsorsButton);

        // Crear la tabla con columna para el monto del pago
        String[] columnNames = {"Sponsor", "Email", "Fiscal Number", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        topPanel.add(new JLabel("Invoice Date:"));
        topPanel.add(invoiceDateField);

        // Botón para generar facturas
        generateButton = new JButton("Generate and Send Invoice");

        // Agregar elementos al frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(generateButton, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void setEventOptions(String[] events) {
        eventComboBox.removeAllItems();
        for (String event : events) {
            eventComboBox.addItem(event);
        }
    }

    public void setSponsorData(Object[][] data) {
        tableModel.setRowCount(0); // Limpiar la tabla antes de agregar datos
        for (Object[] row : data) {
            tableModel.addRow(new Object[] {
                row[0],  // Sponsor name
                row[1],  // Email
                row[2],  // Fiscal number
                row[3]   // Payment amount
            });
        }
    }


    public String getSelectedEvent() {
        return (String) eventComboBox.getSelectedItem();
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public String getSelectedSponsor() {
        int row = getSelectedRow();
        return row != -1 ? (String) table.getValueAt(row, 0) : null;
    }

    public String getInvoiceDate() {
        return invoiceDateField.getText();
    }

    public String getSelectedFiscalNumber() {
        int row = getSelectedRow();
        return row != -1 ? (String) table.getValueAt(row, 2) : null;
    }

    public String getSelectedEmail() {
        int row = getSelectedRow();
        return row != -1 ? (String) table.getValueAt(row, 1) : null;
    }
    
    public Double getAmount() {
        int row = getSelectedRow();
        return row != -1 ? (Double) table.getValueAt(row, 3) : null;
    }

    

    public void addLoadSponsorsListener(ActionListener listener) {
        loadSponsorsButton.addActionListener(listener);
    }

    public void addGenerateButtonListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }
}
