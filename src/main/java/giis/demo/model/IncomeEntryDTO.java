package giis.demo.model;

/**
 * DTO para representar una entrada individual en la tabla de ingresos.
 */
public class IncomeEntryDTO {
    private String name;     // Nombre del movimiento (concept)
    private int amount;     // Monto del movimiento (amount)
    private String status;  // Estado del movimiento (Paid o Estimated)

    // Constructor vacío
    public IncomeEntryDTO() {}

    // Constructor con todos los campos
    public IncomeEntryDTO(String name, int amount, String status) {
        this.name = name;
        this.amount = amount;
        this.status = status;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Método toString para facilitar la depuración
    @Override
    public String toString() {
        return "IncomeEntryDTO{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                '}';
    }
}