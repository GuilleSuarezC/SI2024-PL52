package giis.demo.model;

/**
 * Clase que representa un pago en el sistema.
 */
public class PaymentEntity {

	public enum Status{PAID, OVERPAID, UNDERPAID, UNPAID};
	
    private int id;
    private double amount;
    private String date;
    private Status status;
    

    /**
     * Constructor con parámetros.
     */
    public PaymentEntity(int id, double amount, String date, Status status2) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.status = status2;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    /**
     * Representación en cadena del objeto Payment.
     */
    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", amount=" + amount +
                ", date='" + date + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
