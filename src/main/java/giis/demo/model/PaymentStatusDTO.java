package giis.demo.model;

//DTO para el resultado de verificación de pagos
public class PaymentStatusDTO {
    private boolean all_paid;

    public boolean isAllPaid() { return all_paid; }
    public void setAll_paid(boolean allPaid) { this.all_paid = allPaid; }
}