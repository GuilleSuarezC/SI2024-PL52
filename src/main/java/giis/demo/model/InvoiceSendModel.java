package giis.demo.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import giis.demo.util.Database;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class InvoiceSendModel {
	private Database db = new Database();
	
	private static final String EMAIL_SENDER = "UO288511@uniovi.es";  
    private static final String EMAIL_PASSWORD = "*****";      
    private static final String SMTP_HOST = "smtp.outlook.com";         
    private static final int SMTP_PORT = 587;


    public List<String> getEventNames() {
    	String sql = "SELECT event_name FROM Event";
        List<Object[]> results = db.executeQueryArray(sql);
        
        
        return results.stream().map(row -> (String) row[0]).collect(Collectors.toList());
    }


    public List<Object[]> getSponsorsByEvent(String eventName) {
    	String sql = "SELECT c.company_name, c.company_email, i.taxData_Fnumber, p.payment_amount "+
                "FROM Sponsorship s "+
                "JOIN Company c ON s.company_id = c.company_id "+  
                "JOIN Invoice i ON s.sponsorship_id = i.sponsorship_id "+
                "JOIN Payment p ON s.sponsorship_id = p.sponsorship_id "+
                "JOIN Event ev ON s.event_id = ev.event_id "+
                "WHERE ev.event_name = ?";

        
        


        
        List<Object[]> results = db.executeQueryArray(sql, eventName);
        
        return results;
    }

    public boolean generateInvoice(String sponsorName, String fiscalNumber, String email, String event) {
        try {
            String invoiceDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // Consulta corregida para obtener sponsorship_id
            String sponsorshipQuery = "SELECT s.sponsorship_id " +
                    "FROM Sponsorship s " +
                    "JOIN Company c ON s.company_id = c.company_id " +
                    "JOIN Event ev ON s.event_id = ev.event_id " +
                    "WHERE c.company_name = ? AND ev.event_name = ?";



            
            List<Object[]> sponsorshipResult = db.executeQueryArray(sponsorshipQuery, sponsorName, event);

            if (sponsorshipResult.isEmpty()) {
                System.out.println("Error: No se encontró un sponsorship_id para este patrocinador y evento.");
                return false;
            }

            int sponsorshipId = (int) sponsorshipResult.get(0)[0]; 

            // Inserción de la factura
            String sql = "INSERT INTO Invoice (taxData_name, taxData_Fnumber, invoice_date, sponsorship_id) " +
                         "VALUES (?, ?, ?, ?)";
            
            db.executeUpdate(sql, sponsorName, fiscalNumber, invoiceDate, sponsorshipId);

            System.out.println("Invoice sent to: " + email);

            //sendEmail(email, sponsorName, fiscalNumber, event, invoiceDate);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    
    
    

    
    


    


    

}




