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
    private JComboBox<String> balanceStatusComboBox;  // ComboBox for balance status
    private JButton clearFieldsButton;  // Botón para limpiar los campos

    public RegIncomeExpensesView() {
        frame = new JFrame("Register Other Income/Expenses");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.getContentPane().setLayout(new BorderLayout());

        // Panel superior con el combo de Evento
        JPanel topPanel = new JPanel(new FlowLayout());
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

        // Ocultar la columna "Balance ID"
        table.getColumnModel().getColumn(4).setMaxWidth(0);
        table.getColumnModel().getColumn(4).setMinWidth(0);
        table.getColumnModel().getColumn(4).setPreferredWidth(0);

        // Panel de formulario y botones (Ajustado para que los campos estén debajo)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());  // Usamos GridBagLayout para una disposición flexible
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        // Añadir los campos de texto
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Concept:"), gbc);

        gbc.gridx = 1;
        conceptField = new JTextField(20);
        formPanel.add(conceptField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Amount:"), gbc);

        gbc.gridx = 1;
        amountField = new JTextField(20);
        formPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Date of Paid:"), gbc);

        gbc.gridx = 1;
        dateOfPaidField = new JTextField(20);
        formPanel.add(dateOfPaidField, gbc); // Hacer editable

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Balance Status:"), gbc);

        gbc.gridx = 1;
        balanceStatusComboBox = new JComboBox<>(new String[] {"Estimated", "Paid"});
        formPanel.add(balanceStatusComboBox, gbc);

        // Botones para guardar, agregar y limpiar
        JPanel buttonPanel = new JPanel();
        addBalanceButton = new JButton("Add Income/expense");
        saveChangesButton = new JButton("Save Changes");
        clearFieldsButton = new JButton("Clear Fields");

        buttonPanel.add(addBalanceButton);
        buttonPanel.add(saveChangesButton);
        buttonPanel.add(clearFieldsButton);

        // Crear un JSplitPane para dividir la vista en dos partes
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(formPanel, BorderLayout.SOUTH);

        // Agregar todo al frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

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
        clearFieldsButton.addActionListener(e -> clearFields());
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
}
