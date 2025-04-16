package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;

public class ManageGBMemberModel {
    private Database db = new Database();

    public List<GBMemberDTO> getAllMembers() {
        String sql = "SELECT gb_id AS gb_id, gb_name AS gb_name, gb_rank AS gb_rank FROM COIIPA_GBMember";
        return db.executeQueryPojo(GBMemberDTO.class, sql);
    }

    public GBMemberDTO getMemberById(int id) {
        String sql = "SELECT gb_id AS gb_id, gb_name AS gb_name, gb_rank AS gb_rank FROM COIIPA_GBMember WHERE gb_id = ?";
        List<GBMemberDTO> result = db.executeQueryPojo(GBMemberDTO.class, sql, id);
        return result.isEmpty() ? null : result.get(0);
    }

    public int addMember(String name, String rank) {
        String sql = "INSERT INTO COIIPA_GBMember (gb_name, gb_rank) VALUES (?, ?)";
        return db.executeUpdateAndGetKey(sql, name, rank);
    }

    public void updateMember(int id, String name, String rank) {
        String sql = "UPDATE COIIPA_GBMember SET gb_name = ?, gb_rank = ? WHERE gb_id = ?";
        db.executeUpdate(sql, name, rank, id);
    }

    public void deleteMember(int id) {
        String sql = "DELETE FROM COIIPA_GBMember WHERE gb_id = ?";
        db.executeUpdate(sql, id);
    }

    public void addMember(GBMemberDTO member) {
        addMember(member.getGb_name(), member.getGb_rank());
    }

    public void updateMember(GBMemberDTO member) {
        updateMember(member.getGb_id(), member.getGb_name(), member.getGb_rank());
    }
}
