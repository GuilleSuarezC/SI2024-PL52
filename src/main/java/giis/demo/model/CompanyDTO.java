package giis.demo.model;

public class CompanyDTO {
    private int company_id;
    private String company_name;
    
    // Constructor sin parámetros
    public CompanyDTO() {
    }

    public CompanyDTO(int companyId, String companyName) {
        this.company_id = companyId;
        this.company_name = companyName;
    }

    public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	// ¡Este método es clave!
    @Override
    public String toString() {
        return this.company_name; // Retorna directamente el nombre
    }
//	// Getters y Setters
//    public int getCompanyId() { return company_id; }
//    public String getCompanyName() { return company_name; }
}