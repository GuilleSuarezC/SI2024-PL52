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
        frame = new JFrame("Manage Balances");
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
        String[] columnNames = {"Concept", "Description", "Amount", "Date of Paid", "Balance Status", "Balance ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel de formulario y botones
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));  // Aumenté el número de filas

        formPanel.add(new JLabel("Concept:"));
        conceptField = new JTextField();
        formPanel.add(conceptField);

        formPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);

        formPanel.add(new JLabel("Amount:"));
        amountField = new JTextField();
        formPanel.add(amountField);

        formPanel.add(new JLabel("Date of Paid:"));
        dateOfPaidField = new JTextField();
        formPanel.add(dateOfPaidField);

        formPanel.add(new JLabel("Balance Status:"));
        balanceStatusComboBox = new JComboBox<>(new String[] {"Paid", "Overpaid", "Underpaid", "Unpaid"});
        formPanel.add(balanceStatusComboBox);

        // Botones
        addBalanceButton = new JButton("Add Balance");
        saveChangesButton = new JButton("Save Changes");
        clearFieldsButton = new JButton("Clear Fields");  // Nuevo botón para limpiar los campos
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addBalanceButton);
        buttonPanel.add(saveChangesButton);
        buttonPanel.add(clearFieldsButton);  // Añadir el botón al panel

        formPanel.add(buttonPanel);

        // Crear un JSplitPane para dividir la vista en dos partes
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, scrollPane);
        splitPane.setDividerLocation(400);
        splitPane.setResizeWeight(0.5);

        // Agregar componentes al frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(splitPane, BorderLayout.CENTER);
        frame.setVisible(true);

        // Agregar MouseListener a la tabla dentro del constructor
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Cuando se haga clic en una fila, carga los datos del balance seleccionado en los campos de texto
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String concept = (String) tableModel.getValueAt(selectedRow, 0);
                    String description = (String) tableModel.getValueAt(selectedRow, 1);
                    double amount = (Double) tableModel.getValueAt(selectedRow, 2);
                    String dateOfPaid = (String) tableModel.getValueAt(selectedRow, 3);
                    String balanceStatus = (String) tableModel.getValueAt(selectedRow, 4);

                    // Cargar los datos de la fila seleccionada en los campos de texto
                    conceptField.setText(concept);
                    descriptionField.setText(description);
                    amountField.setText(String.valueOf(amount));
                    dateOfPaidField.setText(dateOfPaid);
                    balanceStatusComboBox.setSelectedItem(balanceStatus);
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
        int balanceId = (int) tableModel.getValueAt(selectedRow, 5);  // Suponiendo que la columna 5 tiene el balance_id
        String concept = (String) tableModel.getValueAt(selectedRow, 0);
        String description = (String) tableModel.getValueAt(selectedRow, 1);
        double amount = Double.parseDouble(tableModel.getValueAt(selectedRow, 2).toString());
        String dateOfPaid = (String) tableModel.getValueAt(selectedRow, 3);
        String balanceStatus = (String) tableModel.getValueAt(selectedRow, 4);

        return new BalanceDTO(balanceId, concept, 0, amount, description, dateOfPaid, balanceStatus);
    }


    // Método para actualizar la fila de la tabla con los nuevos datos
    public void updateBalanceInTable(BalanceDTO balance) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(balance.getConcept(), selectedRow, 0);
            tableModel.setValueAt(balance.getDescription(), selectedRow, 1);
            tableModel.setValueAt(balance.getAmount(), selectedRow, 2);
            tableModel.setValueAt(balance.getDateOfPaid(), selectedRow, 3);
            tableModel.setValueAt(balance.getBalanceStatus(), selectedRow, 4);
        }
    }

    // Actualizar la tabla de balances después de agregar un nuevo balance
    public void setBalanceData(List<BalanceDTO> balances) {
        tableModel.setRowCount(0); // Limpiar la tabla antes de llenarla

        for (BalanceDTO balance : balances) {
            tableModel.addRow(new Object[]{
                balance.getConcept(),
                balance.getDescription(),
                balance.getAmount(),
                balance.getDateOfPaid(),
                balance.getBalanceStatus(),
                balance.getBalanceId()  // Agregar el balance_id para futuras actualizaciones
            });
        }
        table.getColumnModel().getColumn(5).setMinWidth(0);  // Ocultar columna balance_id
        table.getColumnModel().getColumn(5).setMaxWidth(0);  // Ocultar columna balance_id
        table.getColumnModel().getColumn(5).setWidth(0);     // Establecer el ancho de la columna a 0
        table.getTableHeader().getColumnModel().getColumn(5).setMaxWidth(0); // Evitar que se muestre en el encabezado
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
        dateOfPaidField.setText("");
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

