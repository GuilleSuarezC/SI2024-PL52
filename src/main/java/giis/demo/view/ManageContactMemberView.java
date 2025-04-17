package giis.demo.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import giis.demo.controller.ManageContactMemberController;
import giis.demo.model.CMemberDTO;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ManageContactMemberView extends JPanel {
    private ManageContactMemberController controller;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField nameField, emailField, phoneField;
    private JButton addBtn, updateBtn, removeBtn;
    private int selectedMemberId = -1;
    private int companyId;

    private JComboBox<String> companyComboBox;
    private List<Integer> companyIds;

    public ManageContactMemberView() {
        this.controller = new ManageContactMemberController();
        initComponents();
        loadCompanies(); 
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Select a company:"));
        companyComboBox = new JComboBox<>();
        topPanel.add(companyComboBox);
        add(topPanel, BorderLayout.NORTH);

        companyComboBox.addActionListener(e -> {
            int selectedIndex = companyComboBox.getSelectedIndex();
            if (selectedIndex >= 0) {
                companyId = companyIds.get(selectedIndex);
                loadMembers(); 
            }
        });

        String[] columnNames = {"ID", "Name", "Email", "Phone Number"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel formAndButtonPanel = new JPanel();
        formAndButtonPanel.setLayout(new BoxLayout(formAndButtonPanel, BoxLayout.Y_AXIS));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        nameField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Phone Number:"));
        formPanel.add(phoneField);

        formAndButtonPanel.add(formPanel); 

        JPanel buttonPanel = new JPanel();
        addBtn = new JButton("Add Member");
        updateBtn = new JButton("Save Changes");
        removeBtn = new JButton("Desassociate from Company");

        buttonPanel.add(addBtn);
        buttonPanel.add(updateBtn);
        buttonPanel.add(removeBtn);

        formAndButtonPanel.add(buttonPanel); 

        add(formAndButtonPanel, BorderLayout.SOUTH); 

        addBtn.addActionListener(e -> addMember());
        updateBtn.addActionListener(e -> updateMember());
        removeBtn.addActionListener(e -> removeMember());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    selectedMemberId = (int) tableModel.getValueAt(row, 0);
                    nameField.setText((String) tableModel.getValueAt(row, 1));
                    emailField.setText((String) tableModel.getValueAt(row, 2));
                    phoneField.setText((String) tableModel.getValueAt(row, 3));
                }
            }
        });
    }

    private void loadCompanies() {
        List<String> names = controller.getCompanyNames();
        companyIds = controller.getCompanyIds();

        companyComboBox.removeAllItems();
        for (String name : names) {
            companyComboBox.addItem(name);
        }

        controller.handleCompanySelection(companyIds);
    }

    private void loadMembers() {
        tableModel.setRowCount(0);
        List<CMemberDTO> members = controller.getMembers(companyId);
        for (CMemberDTO m : members) {
            tableModel.addRow(new Object[]{
                m.getMember_id(),
                m.getMember_name(),
                m.getMember_email(),
                m.getMember_phone()
            });
        }
    }

    private void addMember() {
        controller.addMember(
            nameField.getText(),
            emailField.getText(),
            phoneField.getText(),
            companyId
        );
        clearForm();
        loadMembers();
    }

    private void updateMember() {
        controller.updateMember(
            selectedMemberId,
            nameField.getText(),
            emailField.getText(),
            phoneField.getText()
        );
        clearForm();
        loadMembers();
    }

    private void removeMember() {
        controller.deactivateMember(selectedMemberId);
        clearForm();
        loadMembers();
    }
    
    private void clearForm() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        selectedMemberId = -1;
    }
}
