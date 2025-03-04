package giis.demo.model;

public class SponsorshipDTO {
    private int sponsorshipId;
    private String sponsorshipName;
    private String agreementDate;

    public SponsorshipDTO() {}
    
    public SponsorshipDTO(int sponsorshipId, String sponsorshipName, String agreementDate) {
        this.sponsorshipId = sponsorshipId;
        this.sponsorshipName = sponsorshipName;
        this.agreementDate = agreementDate;
    }

	public int getSponsorshipId() {
		return sponsorshipId;
	}

	public void setSponsorshipId(int sponsorshipId) {
		this.sponsorshipId = sponsorshipId;
	}

	public String getSponsorshipName() {
		return sponsorshipName;
	}

	public void setSponsorshipName(String sponsorshipName) {
		this.sponsorshipName = sponsorshipName;
	}

	public String getAgreementDate() {
		return agreementDate;
	}

	public void setAgreementDate(String agreementDate) {
		this.agreementDate = agreementDate;
	}

//    // Getters y Setters
//    public int getSponsorshipId() { return sponsorshipId; }
//    public String getSponsorshipName() { return sponsorshipName; }
//    public String getAgreementDate() { return agreementDate; }
}