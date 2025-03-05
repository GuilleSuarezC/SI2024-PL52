package giis.demo.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import giis.demo.util.Database;

public class InvoiceSendModel {
	private Database db = new Database();

    // Obtener la lista de eventos
    public List<String> getEventNames() {
    	String sql = "SELECT event_name FROM Event";
        List<Object[]> results = db.executeQueryArray(sql);
        
        
        return results.stream().map(row -> (String) row[0]).collect(Collectors.toList());
    }

    // Obtener patrocinadores de un evento
    public List<Object[]> getSponsorsByEvent(String eventName) {
        String sql = "SELECT c.company_name, c.company_email, i.taxData_Fnumber " +
                     "FROM Sponsorship s " +
                     "JOIN Company c ON s.company_id = c.company_id " +
                     "JOIN Invoice i ON s.invoice_id = i.invoice_id " +
                     "JOIN Event e ON s.event_id = e.event_id " +
                     "WHERE e.event_name = ?";
        return db.executeQueryArray(sql, eventName);
    }

    // Generar y enviar factura
    public boolean generateInvoice(String sponsorName, String fiscalNumber, String email, String event) {
    	try {
            String invoiceDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String sql = "INSERT INTO Invoice (taxData_name, taxData_Fnumber, invoice_date) VALUES (?, ?, ?)";
            db.executeUpdate(sql, sponsorName, fiscalNumber, invoiceDate);

            // Simulación de envío de email
            System.out.println("Invoice sent to: " + email);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    


    


    

}




