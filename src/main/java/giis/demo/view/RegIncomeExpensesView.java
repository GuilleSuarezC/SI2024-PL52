package giis.demo.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import giis.demo.model.BalanceDTO;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegIncomeExpensesView {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> eventComboBox;
    private JTextField conceptField;
    private JTextField descriptionField;
    private JTextField amountField;
    private JTextField dateOfPaidField;
    private JButton loadBalancesButton;
    private JButton addBalanceButton;
    private JButton saveChangesButton;
    private JButton btnRegisterMovement;
    private JComboBox<String> balanceStatusComboBox;  
    private JButton clearFieldsButtonB;  
    private JTextField AmountFieldM;
    private JTextField DateFieldM;
    private JButton clearFieldsButtonM;

    public RegIncomeExpensesView() {
        frame = new JFrame("Register Other Income/Expenses");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBounds(0, 0, 986, 31);
        JLabel eventLabel = new JLabel("Select Event:");
        eventComboBox = new JComboBox<>();
        loadBalancesButton = new JButton("Load Balances");

        topPanel.add(eventLabel);
        topPanel.add(eventComboBox);
        topPanel.add(loadBalancesButton);

        String[] columnNames = {"Concept", "Description", "Amount", "Balance Status", "Balance ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 986, 269);

        table.getColumnModel().getColumn(4).setMaxWidth(0);
        table.getColumnModel().getColumn(4).setMinWidth(0);
        table.getColumnModel().getColumn(4).setPreferredWidth(0);

        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 271, 482, 291);
        formPanel.setLayout(null);
        JLabel lblConcept = new JLabel("Concept:");
        lblConcept.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblConcept.setBounds(117, 80, 161, 19);
        formPanel.add(lblConcept);
        conceptField = new JTextField(20);
        conceptField.setBounds(195, 81, 161, 19);
        formPanel.add(conceptField);
        JLabel lblDesctiption = new JLabel("Description:");
        lblDesctiption.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDesctiption.setBounds(102, 121, 161, 19);
        formPanel.add(lblDesctiption);
        descriptionField = new JTextField(20);
        descriptionField.setBounds(195, 122, 161, 19);
        formPanel.add(descriptionField);
        JLabel lblAmountB = new JLabel("Amount:");
        lblAmountB.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAmountB.setBounds(121, 163, 142, 19);
        formPanel.add(lblAmountB);
        amountField = new JTextField(20);
        amountField.setBounds(195, 164, 161, 19);
        formPanel.add(amountField);
        JLabel lblDateOfPaidB = new JLabel("Date of Paid:");
        lblDateOfPaidB.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDateOfPaidB.setBounds(100, 204, 191, 16);
        formPanel.add(lblDateOfPaidB);
        dateOfPaidField = new JTextField(20);
        dateOfPaidField.setBounds(195, 204, 161, 19);
        formPanel.add(dateOfPaidField);
        JLabel lblBalanceStatus = new JLabel("Balance Status:");
        lblBalanceStatus.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblBalanceStatus.setBounds(100, 247, 203, 19);
        formPanel.add(lblBalanceStatus);
        balanceStatusComboBox = new JComboBox<>(new String[] {"Estimated", "Paid"});
        balanceStatusComboBox.setBounds(213, 247, 90, 19);
        formPanel.add(balanceStatusComboBox);

        // Botones para guardar, agregar y limpiar
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(10, 602, 976, 61);
        addBalanceButton = new JButton("Add Income/expense");
        addBalanceButton.setBounds(10, 10, 166, 21);
        saveChangesButton = new JButton("Save Changes");
        saveChangesButton.setBounds(186, 10, 116, 21);
        clearFieldsButtonB = new JButton("Clear Fields");
        clearFieldsButtonB.setBounds(312, 10, 116, 21);
        buttonPanel.setLayout(null);

        buttonPanel.add(addBalanceButton);
        buttonPanel.add(saveChangesButton);
        buttonPanel.add(clearFieldsButtonB);

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 31, 986, 570);
        mainPanel.setLayout(null);
        mainPanel.add(scrollPane);
        mainPanel.add(formPanel);
        
        JLabel lblNewLabel = new JLabel("Register a New Balance");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(117, 30, 251, 27);
        formPanel.add(lblNewLabel);
        frame.getContentPane().setLayout(null);

        frame.getContentPane().add(topPanel);
        frame.getContentPane().add(mainPanel);
        
        JPanel formPanel_1 = new JPanel();
        formPanel_1.setLayout(null);
        formPanel_1.setBounds(513, 271, 473, 291);
        mainPanel.add(formPanel_1);
        
        JLabel lblAmountM = new JLabel("Amount:");
        lblAmountM.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblAmountM.setBounds(104, 117, 119, 19);
        formPanel_1.add(lblAmountM);
        
        AmountFieldM = new JTextField(20);
        AmountFieldM.setText("");
        AmountFieldM.setBounds(197, 118, 166, 19);
        formPanel_1.add(AmountFieldM);
        
        JLabel lblDateOfPaidM = new JLabel("Date of Paid:");
        lblDateOfPaidM.setFont(new Font("Tahoma", Font.PLAIN, 12));
        lblDateOfPaidM.setBounds(83, 171, 140, 17);
        formPanel_1.add(lblDateOfPaidM);
        
        DateFieldM = new JTextField(20);
        DateFieldM.setText("");
        DateFieldM.setBounds(197, 171, 166, 19);
        formPanel_1.add(DateFieldM);
        
        JLabel lblRegisterMovement = new JLabel("Register a Movement");
        lblRegisterMovement.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblRegisterMovement.setBounds(128, 26, 224, 30);
        formPanel_1.add(lblRegisterMovement);
        
        JSeparator separator = new JSeparator();
        separator.setBounds(489, 271, 24, 371);
        mainPanel.add(separator);
        separator.setOrientation(SwingConstants.VERTICAL);
        frame.getContentPane().add(buttonPanel);
        
        JSeparator separator_1 = new JSeparator();
        separator_1.setOrientation(SwingConstants.VERTICAL);
        separator_1.setBounds(479, -159, 24, 371);
        buttonPanel.add(separator_1);
        
        btnRegisterMovement = new JButton("Register Movement");
        btnRegisterMovement.setBounds(590, 10, 166, 21);
        buttonPanel.add(btnRegisterMovement);
        
        clearFieldsButtonM = new JButton("Clear Fields");
        clearFieldsButtonM.setBounds(778, 10, 116, 21);
        buttonPanel.add(clearFieldsButtonM);

        frame.setVisible(true);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String concept = (String) tableModel.getValueAt(selectedRow, 0);
                    String description = (String) tableModel.getValueAt(selectedRow, 1);
                    double amount = (Double) tableModel.getValueAt(selectedRow, 2);
                    String balanceStatus = (String) tableModel.getValueAt(selectedRow, 3);
                    int balanceId = (int) tableModel.getValueAt(selectedRow, 4); // Recuperar balanceId

                    conceptField.setText(concept);
                    descriptionField.setText(description);
                    amountField.setText(String.valueOf(amount));
                    balanceStatusComboBox.setSelectedItem(balanceStatus);

                    // dateOfPaidField.setText(""); 
                }
            }
        });

        clearFieldsButtonB.addActionListener(e -> clearFields());
    }

    public String getConceptField() {
        return conceptField.getText();
    }

    public String getDescriptionField() {
        return descriptionField.getText();
    }

    public String getAmountField() {
        return amountField.getText();
    }
    
    public String getAmountFieldM() {
    	return AmountFieldM.getText();
    }
    
    public String getDateM(){
    	return DateFieldM.getText();
    }
    
    public void setAmountFieldM(String amount) {
    	AmountFieldM.setText(amount);
    }
    
    public void setDateM(String date){
    	DateFieldM.setText(date);
    }

    public String getDateOfPaidField() {
        return dateOfPaidField.getText();
    }

    public JComboBox<String> getBalanceStatusComboBox() {
        return balanceStatusComboBox;
    }

    public BalanceDTO getSelectedBalanceDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a balance to edit.", "Selection Error");
            return null;
        }

        int balanceId = (int) tableModel.getValueAt(selectedRow, 4);  
        String concept = (String) tableModel.getValueAt(selectedRow, 0);
        String description = (String) tableModel.getValueAt(selectedRow, 1);
        double amount = Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString());
        String balanceStatus = (String) tableModel.getValueAt(selectedRow, 3);
        String dateOfPaid = (String) tableModel.getValueAt(selectedRow, 3); 

        return new BalanceDTO(balanceId, concept, 0, amount, description, balanceStatus);
    }

    public void updateBalanceInTable(BalanceDTO balance) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(balance.getConcept(), selectedRow, 0);
            tableModel.setValueAt(balance.getDescription(), selectedRow, 1);
            tableModel.setValueAt(balance.getAmount(), selectedRow, 2);
            tableModel.setValueAt(balance.getBalanceStatus(), selectedRow, 3);
        }
    }

    public void setBalanceData(List<BalanceDTO> balances) {
        tableModel.setRowCount(0); 

        for (BalanceDTO balance : balances) {
            tableModel.addRow(new Object[] {
                balance.getConcept(),
                balance.getDescription(),
                balance.getAmount(),
                balance.getBalanceStatus(),
                balance.getBalanceId()  
            });
        }
    }

    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public void clearFields() {
        conceptField.setText("");
        descriptionField.setText("");
        amountField.setText("");
        dateOfPaidField.setText("");
        balanceStatusComboBox.setSelectedIndex(0);  
    }
        
    
    public void clearFieldsM() {
        AmountFieldM.setText("");
        DateFieldM.setText(""); 
    }
    
    
    public JButton getClearFieldsButtonM() { return this.clearFieldsButtonM; }

    public void setEventOptions(String[] events) {
        eventComboBox.setModel(new DefaultComboBoxModel<>(events));
    }

    public String getSelectedEvent() {
        return (String) eventComboBox.getSelectedItem();
    }

    public void addSaveChangesListener(ActionListener listener) {
        saveChangesButton.addActionListener(listener);
    }

    public void addLoadBalancesListener(ActionListener listener) {
        loadBalancesButton.addActionListener(listener);
    }

    public void addAddBalanceListener(ActionListener listener) {
        addBalanceButton.addActionListener(listener);
    }
    
    public void addRegMovementListener(ActionListener listener) {
    	btnRegisterMovement.addActionListener(listener);
    }
    public JFrame getFrame() { return this.frame; }
}
