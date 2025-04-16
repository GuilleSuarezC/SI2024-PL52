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
    private JComboBox<String> balanceStatusComboBox;  // ComboBox for balance status
    private JButton clearFieldsButtonB;  // Botón para limpiar los campos
    private JTextField AmountFieldM;
    private JTextField DateFieldM;

    public RegIncomeExpensesView() {
        frame = new JFrame("Register Other Income/Expenses");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);

        // Panel superior con el combo de Evento
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBounds(0, 0, 986, 31);
        JLabel eventLabel = new JLabel("Select Event:");
        eventComboBox = new JComboBox<>();
        loadBalancesButton = new JButton("Load Balances");

        topPanel.add(eventLabel);
        topPanel.add(eventComboBox);
        topPanel.add(loadBalancesButton);

        // Tabla de balances
        String[] columnNames = {"Concept", "Description", "Amount", "Balance Status", "Balance ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 986, 269);

        // Ocultar la columna "Balance ID"
        table.getColumnModel().getColumn(4).setMaxWidth(0);
        table.getColumnModel().getColumn(4).setMinWidth(0);
        table.getColumnModel().getColumn(4).setPreferredWidth(0);

        // Panel de formulario y botones (Ajustado para que los campos estén debajo)
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 271, 482, 315);
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
        buttonPanel.setBounds(10, 622, 976, 41);
        addBalanceButton = new JButton("Add Income/expense");
        addBalanceButton.setBounds(81, 10, 129, 21);
        saveChangesButton = new JButton("Save Changes");
        saveChangesButton.setBounds(215, 10, 97, 21);
        clearFieldsButtonB = new JButton("Clear Fields");
        clearFieldsButtonB.setBounds(317, 10, 87, 21);
        buttonPanel.setLayout(null);

        buttonPanel.add(addBalanceButton);
        buttonPanel.add(saveChangesButton);
        buttonPanel.add(clearFieldsButtonB);

        // Crear un JSplitPane para dividir la vista en dos partes
        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(0, 31, 986, 590);
        mainPanel.setLayout(null);
        mainPanel.add(scrollPane);
        mainPanel.add(formPanel);
        
        JLabel lblNewLabel = new JLabel("Register a New Balance");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(117, 30, 251, 27);
        formPanel.add(lblNewLabel);
        frame.getContentPane().setLayout(null);

        // Agregar todo al frame
        frame.getContentPane().add(topPanel);
        frame.getContentPane().add(mainPanel);
        
        JPanel formPanel_1 = new JPanel();
        formPanel_1.setLayout(null);
        formPanel_1.setBounds(513, 271, 473, 315);
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
        btnRegisterMovement.setBounds(627, 10, 129, 21);
        buttonPanel.add(btnRegisterMovement);
        
        JButton clearFieldsButtonM = new JButton("Clear Fields");
        clearFieldsButtonM.setBounds(778, 10, 87, 21);
        buttonPanel.add(clearFieldsButtonM);

        frame.setVisible(true);

        // Agregar MouseListener a la tabla dentro del constructor
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cuando se haga clic en una fila, carga los datos del balance seleccionado en los campos de texto
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Recuperar los valores de la fila seleccionada
                    String concept = (String) tableModel.getValueAt(selectedRow, 0);
                    String description = (String) tableModel.getValueAt(selectedRow, 1);
                    double amount = (Double) tableModel.getValueAt(selectedRow, 2);
                    String balanceStatus = (String) tableModel.getValueAt(selectedRow, 3);
                    int balanceId = (int) tableModel.getValueAt(selectedRow, 4); // Recuperar balanceId

                    // Cargar los datos de la fila seleccionada en los campos de texto
                    conceptField.setText(concept);
                    descriptionField.setText(description);
                    amountField.setText(String.valueOf(amount));
                    balanceStatusComboBox.setSelectedItem(balanceStatus);

                    // No vaciar el campo de Date of Paid al seleccionar una fila
                    // dateOfPaidField.setText(""); // Ya no vaciamos este campo
                }
            }
        });

        // Listener para el botón de limpiar campos
        clearFieldsButtonB.addActionListener(e -> clearFields());
    }

    // Métodos para obtener los valores de los campos de texto
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

    public String getDateOfPaidField() {
        return dateOfPaidField.getText();
    }

    public JComboBox<String> getBalanceStatusComboBox() {
        return balanceStatusComboBox;
    }

    // Método para obtener los detalles del balance seleccionado en la tabla
    public BalanceDTO getSelectedBalanceDetails() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            showMessage("Please select a balance to edit.", "Selection Error");
            return null;
        }

        // Extraer los valores de la tabla
        int balanceId = (int) tableModel.getValueAt(selectedRow, 4);  // Asegúrate de que la columna 4 tenga balanceId
        String concept = (String) tableModel.getValueAt(selectedRow, 0);
        String description = (String) tableModel.getValueAt(selectedRow, 1);
        double amount = Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString());
        String balanceStatus = (String) tableModel.getValueAt(selectedRow, 3);
        String dateOfPaid = (String) tableModel.getValueAt(selectedRow, 3); // Asumiendo que tienes la fecha de pago en la tabla

        return new BalanceDTO(balanceId, concept, 0, amount, description, balanceStatus);
    }

    // Método para actualizar la fila de la tabla con los nuevos datos
    public void updateBalanceInTable(BalanceDTO balance) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(balance.getConcept(), selectedRow, 0);
            tableModel.setValueAt(balance.getDescription(), selectedRow, 1);
            tableModel.setValueAt(balance.getAmount(), selectedRow, 2);
            tableModel.setValueAt(balance.getBalanceStatus(), selectedRow, 3);
        }
    }

    // Actualizar la tabla de balances después de agregar un nuevo balance
    public void setBalanceData(List<BalanceDTO> balances) {
        tableModel.setRowCount(0); // Limpiar la tabla antes de llenarla

        for (BalanceDTO balance : balances) {
            tableModel.addRow(new Object[] {
                balance.getConcept(),
                balance.getDescription(),
                balance.getAmount(),
                balance.getBalanceStatus(),
                balance.getBalanceId()  // Agregar el balance_id para futuras actualizaciones
            });
        }
    }

    // Mostrar mensajes de error o éxito
    public void showMessage(String message, String title) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Limpiar los campos del formulario
    public void clearFields() {
        conceptField.setText("");
        descriptionField.setText("");
        amountField.setText("");
        dateOfPaidField.setText(""); // Mantener este campo vacío
        balanceStatusComboBox.setSelectedIndex(0);  // Resetear el ComboBox a la primera opción
    }
    
    public void clearFieldsM() {
        AmountFieldM.setText("");
        DateFieldM.setText(""); // Mantener este campo vacío
    }
  
    

    // Configurar el ComboBox de eventos
    public void setEventOptions(String[] events) {
        eventComboBox.setModel(new DefaultComboBoxModel<>(events));
    }

    // Obtener el evento seleccionado
    public String getSelectedEvent() {
        return (String) eventComboBox.getSelectedItem();
    }

    // Añadir listener al botón de "Guardar cambios"
    public void addSaveChangesListener(ActionListener listener) {
        saveChangesButton.addActionListener(listener);
    }

    // Añadir listener al botón de "Cargar balances"
    public void addLoadBalancesListener(ActionListener listener) {
        loadBalancesButton.addActionListener(listener);
    }

    // Añadir listener al botón de "Añadir balance"
    public void addAddBalanceListener(ActionListener listener) {
        addBalanceButton.addActionListener(listener);
    }
    
    public void addRegMovementListener(ActionListener listener) {
    	btnRegisterMovement.addActionListener(listener);
    }
    public JFrame getFrame() { return this.frame; }
}
