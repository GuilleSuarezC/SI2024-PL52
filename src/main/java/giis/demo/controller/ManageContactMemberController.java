package giis.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.model.CMemberDTO;
import giis.demo.model.CompanyDTO;
import giis.demo.model.ManageContactMemberModel;
import giis.demo.util.SwingUtil;

public class ManageContactMemberController {
    private ManageContactMemberModel model;

    public ManageContactMemberController() {
        this.model = new ManageContactMemberModel();
    }

    public List<CMemberDTO> getMembers(int companyId) {
        return model.getMembersByCompany(companyId);
    }

    public void handleCompanySelection(List<Integer> companyIds) {
        if (companyIds == null || companyIds.isEmpty()) {
            SwingUtil.showMessage("No companies available.", "Company not selected", JOptionPane.ERROR_MESSAGE);

            return;
        }

        int firstCompanyId = companyIds.get(0);
    }
    
    public void addMember(String name, String email, String phone, int companyId) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            SwingUtil.showMessage("All fields must be filled", "Missing data", JOptionPane.ERROR_MESSAGE);

            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            SwingUtil.showMessage("Invalid email format", "Incorrect Format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("\\d{9}")) {
            SwingUtil.showMessage("Invalid phone format should be 9 digits", "Incorrect Format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CMemberDTO member = new CMemberDTO();
        member.setMember_name(name);
        member.setMember_email(email);
        member.setMember_phone(phone);
        member.setCompany_id(companyId);
        
        model.addMember(member);
        SwingUtil.showMessage("Member added succesfully", "Success!", JOptionPane.INFORMATION_MESSAGE);

    }

    public void updateMember(int memberId, String name, String email, String phone) {
        if (memberId == -1) {
            SwingUtil.showMessage("Select member to edit", "Member not selected", JOptionPane.ERROR_MESSAGE);

            return;
        }

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            SwingUtil.showMessage("All fields must be filled", "Missing data", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            SwingUtil.showMessage("Invalid email format", "Incorrect Format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!phone.matches("\\d{9}")) {
            SwingUtil.showMessage("Invalid phone format should be 9 digits", "Incorrect Format", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CMemberDTO member = new CMemberDTO();
        member.setMember_id(memberId);
        member.setMember_name(name);
        member.setMember_email(email);
        member.setMember_phone(phone);
        
        model.updateMember(member);
        SwingUtil.showMessage("Member edited succesfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }


    public void deactivateMember(int memberId) {
        if (memberId == -1) {
            SwingUtil.showMessage("Select member to remove ", "Member not selected", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.deactivateMember(memberId);
        SwingUtil.showMessage("Member removed succesfully", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }

    public List<String> getCompanyNames() {
        List<CompanyDTO> companies = model.getAllCompanies();
        List<String> names = new ArrayList<>();
        for (CompanyDTO c : companies) {
            names.add(c.getCompany_name());
        }
        return names;
    }

    public List<Integer> getCompanyIds() {
        List<CompanyDTO> companies = model.getAllCompanies();
        List<Integer> ids = new ArrayList<>();
        for (CompanyDTO c : companies) {
            ids.add(c.getCompany_id());
        }
        return ids;
    }
}
