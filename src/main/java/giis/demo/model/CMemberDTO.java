package giis.demo.model;

public class CMemberDTO {
    private int member_id;
    private String member_name;
    private String member_email;
    private String member_phone;
    private int company_id;

    public CMemberDTO() {
        // Constructor vacío necesario para frameworks de mapeo
    }

    public CMemberDTO(int member_id, String member_name, String member_email, String member_phone, int company_id) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.member_email = member_email;
        this.member_phone = member_phone;
        this.company_id = company_id;
    }

    public int getMember_id() {
        return member_id;
    }

    public void setMember_id(int member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getMember_email() {
        return member_email;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return member_name + " (" + member_email + ", " + member_phone + ")";
    }
}

