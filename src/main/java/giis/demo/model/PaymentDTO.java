package giis.demo.model;

import java.util.Date;

/**
 * DTO para representar un registro de la tabla Payment.
 * Los nombres de los atributos coinciden exactamente con los nombres de las columnas en la base de datos.
 */
public class PaymentDTO {
    private int payment_id;           // payment_id
    private double payment_amount;   // payment_amount
    private String payment_date;       // payment_date
    private String payment_status;   // payment_status
    private int sponsorship_id;      // sponsorship_id

    // Constructor vacío
    public PaymentDTO() {}

    // Constructor con todos los campos
    public PaymentDTO(int payment_id, double payment_amount, String payment_date, String payment_status, int sponsorship_id) {
        this.payment_id = payment_id;
        this.payment_amount = payment_amount;
        this.payment_date = payment_date;
        this.payment_status = payment_status;
        this.sponsorship_id = sponsorship_id;
    }

    // Getters y Setters
    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public double getPayment_amount() {
        return payment_amount;
    }

    public void setPayment_amount(double payment_amount) {
        this.payment_amount = payment_amount;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public int getSponsorship_id() {
        return sponsorship_id;
    }

    public void setSponsorship_id(int sponsorship_id) {
        this.sponsorship_id = sponsorship_id;
    }

    // Método toString para facilitar la depuración
    @Override
    public String toString() {
        return "PaymentDTO{" +
                "payment_id=" + payment_id +
                ", payment_amount=" + payment_amount +
                ", payment_date=" + payment_date +
                ", payment_status='" + payment_status + '\'' +
                ", sponsorship_id=" + sponsorship_id +
                '}';
    }
}
