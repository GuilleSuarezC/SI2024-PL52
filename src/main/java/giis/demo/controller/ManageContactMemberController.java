package giis.demo.controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.model.CMemberDTO;
import giis.demo.model.CompanyDTO;
import giis.demo.model.ManageContactMemberModel;

public class ManageContactMemberController {
    private ManageContactMemberModel model;

    public ManageContactMemberController() {
        this.model = new ManageContactMemberModel();
    }

    public List<CMemberDTO> getMembers(int companyId) {
        return model.getMembersByCompany(companyId);
    }

    public void addMember(String name, String email, String phone, int companyId) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled.");
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Invalid email format.");
            return;
        }

        if (!phone.matches("\\d{9}")) {
            JOptionPane.showMessageDialog(null, "Invalid phone number format. It should be 9 digits.");
            return;
        }

        CMemberDTO member = new CMemberDTO();
        member.setMember_name(name);
        member.setMember_email(email);
        member.setMember_phone(phone);
        member.setCompany_id(companyId);
        
        model.addMember(member);
        JOptionPane.showMessageDialog(null, "Member added successfully.");
    }

    public void updateMember(int memberId, String name, String email, String phone) {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(null, "All fields must be filled.");
            return;
        }

        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "Invalid email format.");
            return;
        }

        if (!phone.matches("\\d{9}")) {
            JOptionPane.showMessageDialog(null, "Invalid phone number format. It should be 9 digits.");
            return;
        }

        CMemberDTO member = new CMemberDTO();
        member.setMember_id(memberId);
        member.setMember_name(name);
        member.setMember_email(email);
        member.setMember_phone(phone);
        
        model.updateMember(member);
        JOptionPane.showMessageDialog(null, "Member updated successfully.");
    }


    public void deactivateMember(int memberId) {
        model.deactivateMember(memberId);
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
