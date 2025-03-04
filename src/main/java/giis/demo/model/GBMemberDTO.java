package giis.demo.model;

/**
 * DTO para representar a los miembros del consejo de administración (Governing Board).
 */
public class GBMemberDTO {
    private int gb_id;       // ID del miembro del consejo
    private String gb_name;  // Nombre del miembro del consejo
    private String gb_rank;  // Rango o cargo del miembro del consejo

    public GBMemberDTO() {}
    
    // Constructor
    public GBMemberDTO(int gb_id, String gb_name, String gb_rank) {
        this.gb_id = gb_id;
        this.gb_name = gb_name;
        this.gb_rank = gb_rank;
    }

//    // Getters y Setters
//    public int getGbId() {
//        return gb_id;
//    }
//
//    public void setGbId(int gbId) {
//        this.gb_id = gbId;
//    }
//
//    public String getGbName() {
//        return gb_name;
//    }
//
//    public void setGbName(String gbName) {
//        this.gb_name = gbName;
//    }
//
//    public String getGbRank() {
//        return gb_rank;
//    }
//
//    public void setGbRank(String gbRank) {
//        this.gb_rank = gbRank;
//    }

    // Método toString para mostrar el nombre en el JComboBox
    @Override
    public String toString() {
        return gb_name + " (" + gb_rank + ")";
    }

	public int getGb_id() {
		return gb_id;
	}

	public void setGb_id(int gb_id) {
		this.gb_id = gb_id;
	}

	public String getGb_name() {
		return gb_name;
	}

	public void setGb_name(String gb_name) {
		this.gb_name = gb_name;
	}

	public String getGb_rank() {
		return gb_rank;
	}

	public void setGb_rank(String gb_rank) {
		this.gb_rank = gb_rank;
	}
}