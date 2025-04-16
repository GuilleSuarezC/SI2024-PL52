package giis.demo.model;

/**
 * DTO para almacenar la información de los patrocinios asociados a un evento.
 */
public class SponsorshipInfoDTO {
    private String sponsorship_name;
    private String sponsorship_agreementDate;
    private String payment_status;
    private int agreed_quantity;

    // Constructor, getters y setters
    public SponsorshipInfoDTO() {}

    public SponsorshipInfoDTO(String sponsorship_name, String sponsorship_agreementDate, String payment_status, int agreed_quantity) {
        this.sponsorship_name = sponsorship_name;
        this.sponsorship_agreementDate = sponsorship_agreementDate;
        this.payment_status = payment_status;
        this.agreed_quantity = agreed_quantity;
    }

    public String getsponsorship_name() {
        return sponsorship_name;
    }

    public void setsponsorship_name(String sponsorship_name) {
        this.sponsorship_name = sponsorship_name;
    }

    public String getsponsorship_agreementDate() {
        return sponsorship_agreementDate;
    }

    public void setsponsorship_agreementDate(String sponsorship_agreementDate) {
        this.sponsorship_agreementDate = sponsorship_agreementDate;
    }

    public String getpayment_status() {
        return payment_status;
    }

    public void setpayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public int getagreed_quantity() {
        return agreed_quantity;
    }

    public void setagreed_quantity(int agreed_quantity) {
        this.agreed_quantity = agreed_quantity;
    }
}