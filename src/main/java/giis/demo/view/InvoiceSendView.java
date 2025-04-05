package giis.demo.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
    private JCheckBox advanceInvoiceCheckBox;

    //JTextField invoiceDateField = new JTextField(10);

    public InvoiceSendView() {
        frame = new JFrame("Generate Invoices");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

     
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel eventLabel = new JLabel("Select Event:");
        eventComboBox = new JComboBox<>();
        loadSponsorsButton = new JButton("Load Sponsors");

        topPanel.add(eventLabel);
        topPanel.add(eventComboBox);
        topPanel.add(loadSponsorsButton);

        
        String[] columnNames = {"Sponsor", "Email", "Fiscal Number", "Amount", "Address"};  
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        //topPanel.add(new JLabel("Invoice Date:"));
        //topPanel.add(invoiceDateField);

       
        generateButton = new JButton("Generate and Send Invoice");
        
        advanceInvoiceCheckBox = new JCheckBox("Generate invoice in advance");
        topPanel.add(advanceInvoiceCheckBox);
        
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
        tableModel.setRowCount(0);  

        for (Object[] row : data) {
            tableModel.addRow(new Object[] {
                row[0],  
                row[1],  
                row[2],  
                row[3],  
                row[4]   
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

  //  public String getInvoiceDate() {
  //      return invoiceDateField.getText();
  //  }

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
        Object value = table.getValueAt(row, 3);  

        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return 0.0;  
    }


    public String getSelectedAddress() {
        int row = getSelectedRow();
        return row != -1 ? (String) table.getValueAt(row, 4) : null;  
    }

    

    public void addLoadSponsorsListener(ActionListener listener) {
        loadSponsorsButton.addActionListener(listener);
    }

    public void addGenerateButtonListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }
    
    public boolean isAdvanceInvoiceSelected() {
        return advanceInvoiceCheckBox.isSelected();
    }
}
