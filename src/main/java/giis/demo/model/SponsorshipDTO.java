package giis.demo.model;

public class SponsorshipDTO {
    private int sponsorshipId;
    private String sponsorshipName;
    private String sponsorship_agreementDate;

    public SponsorshipDTO() {}
    
    public SponsorshipDTO(int sponsorshipId, String sponsorshipName, String sponsorship_agreementDate) {
        this.sponsorshipId = sponsorshipId;
        this.sponsorshipName = sponsorshipName;
        this.sponsorship_agreementDate = sponsorship_agreementDate;
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

	public String getSponsorship_agreementDate() {
		return sponsorship_agreementDate;
	}

	public void setSponsorship_agreementDate(String sponsorship_agreementDate) {
		this.sponsorship_agreementDate = sponsorship_agreementDate;
	}

//    // Getters y Setters
//    public int getSponsorshipId() { return sponsorshipId; }
//    public String getSponsorshipName() { return sponsorshipName; }
//    public String getAgreementDate() { return agreementDate; }
}