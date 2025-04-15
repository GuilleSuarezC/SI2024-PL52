package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;

public class ManageContactMemberModel {
    private Database db = new Database();

    public List<CMemberDTO> getMembersByCompany(int companyId) {
        String sql = "SELECT * FROM Member WHERE company_id = ?";
        return db.executeQueryPojo(CMemberDTO.class, sql, companyId);
    }

    public void addMember(CMemberDTO member) {
        String sql = "INSERT INTO Member (member_name, member_email, member_phone, company_id) VALUES (?, ?, ?, ?)";
        db.executeUpdate(sql, member.getMember_name(), member.getMember_email(), member.getMember_phone(), member.getCompany_id());
    }

    public void updateMember(CMemberDTO member) {
        String sql = "UPDATE Member SET member_name = ?, member_email = ?, member_phone = ? WHERE member_id = ?";
        db.executeUpdate(sql, member.getMember_name(), member.getMember_email(), member.getMember_phone(), member.getMember_id());
    }

    public void deactivateMember(int memberId) {
        String sql = "UPDATE Member SET company_id = NULL WHERE member_id = ?";
        db.executeUpdate(sql, memberId);
    }

    public CMemberDTO getMemberById(int memberId) {
        String sql = "SELECT * FROM Member WHERE member_id = ?";
        List<CMemberDTO> result = db.executeQueryPojo(CMemberDTO.class, sql, memberId);
        return result.isEmpty() ? null : result.get(0);
    }

    public List<CompanyDTO> getAllCompanies() {
        String sql = "SELECT * FROM Company"; 
        return db.executeQueryPojo(CompanyDTO.class, sql);
    }
}
