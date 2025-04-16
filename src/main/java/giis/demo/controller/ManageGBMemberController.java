package giis.demo.controller;

import giis.demo.model.GBMemberDTO;
import giis.demo.model.ManageGBMemberModel;
import giis.demo.view.ManageGBMemberView;
import giis.demo.util.SwingUtil;
import javax.swing.*;
import java.util.List;

public class ManageGBMemberController {
    private ManageGBMemberView view;
    private ManageGBMemberModel model;

    public ManageGBMemberController(ManageGBMemberView view, ManageGBMemberModel model) {
        this.view = view;
        this.model = model;
        view.setController(this);  
        loadTable();
    }

    private void loadTable() {
        List<GBMemberDTO> members = model.getAllMembers();
        view.loadMembers(members);  
    }

    public void addMember() {
        JTextField nameField = view.getNameField();
        JTextField rankField = view.getRankField();

        String name = nameField.getText().trim();
        String rank = rankField.getText().trim();

        if (name.isEmpty() || rank.isEmpty()) {
            String missingFields = "";
            if (name.isEmpty()) missingFields += "Name ";
            if (rank.isEmpty()) missingFields += "Rank";
            SwingUtil.showMessage("Please fill in the following fields: " + missingFields, "Empty fields", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.addMember(name, rank);  
        loadTable();  
        view.clearFields();  
        SwingUtil.showMessage("Member added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void editMember() {
        JTable table = view.getTblGBMembers();
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            SwingUtil.showMessage("Select a member to edit", "Select a member", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        String name = view.getNameField().getText().trim();
        String rank = view.getRankField().getText().trim();

        if (name.isEmpty() || rank.isEmpty()) {
            String missingFields = "";
            if (name.isEmpty()) missingFields += "Name ";
            if (rank.isEmpty()) missingFields += "Rank";
            SwingUtil.showMessage("Please fill in the following fields: " + missingFields, "Empty fields", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.updateMember(id, name, rank);  
        loadTable(); 
        view.clearFields();  
        SwingUtil.showMessage("Member edited successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void deleteMember() {
        JTable table = view.getTblGBMembers();
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            SwingUtil.showMessage("Select a member to delete", "Select a member", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this member?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            model.deleteMember(id);  
            loadTable();  
            view.clearFields();  
            SwingUtil.showMessage("Member deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
