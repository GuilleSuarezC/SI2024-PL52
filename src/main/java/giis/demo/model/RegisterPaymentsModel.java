package giis.demo.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import giis.demo.util.Database;

public class RegisterPaymentsModel {
    private Database db;
    
    public RegisterPaymentsModel() {
        db = new Database();
    }
    
    // Obtiene los pagos pendientes
    public List<RegisterPaymentsDTO> getPendingPayments() {
        List<RegisterPaymentsDTO> payments = new ArrayList<>();
        String query = "SELECT s.sponsorship_id, p.payment_amount, s.sponsorship_agreementDate, s.invoice_id " +
                       "FROM Sponsorship s " +
                       "JOIN Payment p ON s.sponsorship_id = p.sponsorship_id " +
                       "WHERE p.payment_status = 'Unpaid' OR p.payment_status = 'Underpaid'";
        
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
            	payments.add(new RegisterPaymentsDTO(rs.getInt("sponsorship_id"), rs.getDouble("payment_amount"), rs.getDate("sponsorship_agreementDate"), rs.getInt("invoice_id")));              
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }
    
    // Actualiza el pago
    public void updatePayment(int sponsorshipId, double amountPaid, Date paymentDate) {
        String query = "UPDATE Payment SET payment_amount = ?, payment_date = ?, payment_status = ? " +
                       "WHERE sponsorship_id = ?";
        String status = amountPaid > 0 ? "Paid" : "Unpaid";
        
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setDouble(1, amountPaid);
            stmt.setDate(2, (java.sql.Date) paymentDate);
            stmt.setString(3, status);
            stmt.setInt(4, sponsorshipId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
