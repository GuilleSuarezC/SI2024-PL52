package giis.demo.model;

/**
 * DTO para almacenar la información de los patrocinios asociados a un evento.
 */
public class SponsorshipInfoDTO {
    private String sponsorship_name;
    private String sponsorship_agreementDate;
    private String payment_status;
    private int event_fee;

    // Constructor, getters y setters
    public SponsorshipInfoDTO() {}

    public SponsorshipInfoDTO(String sponsorship_name, String sponsorship_agreementDate, String payment_status, int event_fee) {
        this.sponsorship_name = sponsorship_name;
        this.sponsorship_agreementDate = sponsorship_agreementDate;
        this.payment_status = payment_status;
        this.event_fee = event_fee;
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

    public int getevent_fee() {
        return event_fee;
    }

    public void setevent_fee(int event_fee) {
        this.event_fee = event_fee;
    }
}