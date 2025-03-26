package giis.demo.model;

/**
 * DTO para representar un balance asociado a un patrocinio.
 * Incluye campos específicos de Sponsorship + estado de pago.
 */
public class SponsorshipBalanceDTO {
    private int balance_id;
    private String concept;      // Nombre del patrocinio (ej: "Tech Innovators")
    private int amount;          // Monto acordado (ej: 3000€)
    private String paymentStatus; // 'Paid'/'Unpaid'

    // Constructor vacío
    public SponsorshipBalanceDTO() {}

    // Getters y Setters
    public int getBalance_id() { return balance_id; }
    public void setBalance_id(int balance_id) { this.balance_id = balance_id; }

    public String getConcept() { return concept; }
    public void setConcept(String concept) { this.concept = concept; }

    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}