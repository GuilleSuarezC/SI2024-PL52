package giis.demo.model;

/**
 * DTO para representar ingresos/gastos NO asociados a patrocinios.
 */
public class OtherBalanceDTO {
    private int balance_id;
    private String concept;      // Concepto (ej: "Rent a building")
    private int amount;          // Monto (positivo=ingreso, negativo=gasto)
    private String paymentStatus; // 'Paid'/'Unpaid'

    // Constructor vacío
    public OtherBalanceDTO() {}

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