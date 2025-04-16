package giis.demo.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import giis.demo.controller.ManageGBMemberController;  // Importa el controlador
import giis.demo.model.GBMemberDTO;
import java.util.List;



public class ManageGBMemberView extends JFrame {
    private JTable memberTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, rankField;
    private JButton addButton, updateButton, deleteButton;
    private ManageGBMemberController controller; 

    public ManageGBMemberView() {
        setTitle("COIIPA Member Management");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Member Data"));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Rank:"));
        rankField = new JTextField();
        inputPanel.add(rankField);

        String[] columnNames = {"ID", "Name", "Rank"};
        tableModel = new DefaultTableModel(columnNames, 0);
        memberTable = new JTable(tableModel);
        memberTable.getColumnModel().getColumn(0).setMaxWidth(0);
        memberTable.getColumnModel().getColumn(0).setMinWidth(0);
        memberTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        JScrollPane tableScroll = new JScrollPane(memberTable);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        addButton.addActionListener(e -> controller.addMember());  
        updateButton.addActionListener(e -> controller.editMember());  
        deleteButton.addActionListener(e -> controller.deleteMember());  

        memberTable.getSelectionModel().addListSelectionListener(e -> fillFieldsFromSelectedRow());

        getContentPane().setLayout(new BorderLayout(20, 20));
        getContentPane().add(inputPanel, BorderLayout.NORTH);
        getContentPane().add(tableScroll, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void fillFieldsFromSelectedRow() {
        int row = memberTable.getSelectedRow();
        if (row >= 0) {
            nameField.setText((String) tableModel.getValueAt(row, 1));
            rankField.setText((String) tableModel.getValueAt(row, 2));
        }
    }

    public void setController(ManageGBMemberController controller) {
        this.controller = controller;  
    }

    public JTable getTblGBMembers() {
        return this.memberTable;
    }

    public JTextField getNameField() {
        return nameField;
    }

    public JTextField getRankField() {
        return rankField;
    }

    public void loadMembers(List<GBMemberDTO> members) {
        tableModel.setRowCount(0);
        for (GBMemberDTO member : members) {
            tableModel.addRow(new Object[]{member.getGb_id(), member.getGb_name(), member.getGb_rank()});
        }
    }

    public void clearFields() {
        nameField.setText("");
        rankField.setText("");
        memberTable.clearSelection();
    }
}
