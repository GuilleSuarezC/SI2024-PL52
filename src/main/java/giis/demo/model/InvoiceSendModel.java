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
        String sql = "SELECT c.company_name, c.company_email, i.taxData_Fnumber, p.payment_amount " +
                     "FROM Sponsorship s " +
                     "JOIN Company c ON s.company_id = c.company_id " +
                     "JOIN Invoice i ON s.invoice_id = i.invoice_id " +
                     "JOIN Event e ON s.event_id = e.event_id " +
                     "JOIN Payment p ON s.sponsorship_id = p.sponsorship_id " + 
                     "WHERE e.event_name = ?";
        List<Object[]> results = db.executeQueryArray(sql, eventName);
        
       
        
        return results;
    }




    public boolean generateInvoice(String sponsorName, String fiscalNumber, String email, String event) {
        try {
            String invoiceDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());

            // Obtener sponsorship_id basado en el sponsorName y el evento
            String sponsorshipQuery = "SELECT s.sponsorship_id FROM Sponsorship s " +
                                      "JOIN Company c ON s.company_id = c.company_id " +
                                      "JOIN Event e ON s.event_id = e.event_id " +
                                      "WHERE c.company_name = ? AND e.event_name = ?";
            List<Object[]> sponsorshipResult = db.executeQueryArray(sponsorshipQuery, sponsorName, event);

            if (sponsorshipResult.isEmpty()) {
                System.out.println("Error: No se encontró un sponsorship_id para este patrocinador y evento.");
                return false;
            }

            int sponsorshipId = (int) sponsorshipResult.get(0)[0]; // Obtener el sponsorship_id

            // Insertar la factura con el sponsorship_id
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

    
    
    private void sendEmail(String recipientEmail, String sponsorName, String fiscalNumber, String event, String invoiceDate) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.ssl.trust", SMTP_HOST);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_SENDER, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_SENDER));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject("Invoice for Event Sponsorship");

            String emailBody = "Dear " + sponsorName + ",\n\n"
                    + "We are pleased to send you the invoice for your sponsorship in the event: " + event + ".\n\n"
                    + "Invoice Details:\n"
                    + "----------------------------------\n"
                    + "Invoice Date: " + invoiceDate + "\n"
                    + "Sponsor: " + sponsorName + "\n"
                    + "CIF/NIF: " + fiscalNumber + "\n"
                    + "Event: " + event + "\n"
                    + "----------------------------------\n\n"
                    + "Thank you for your support!\n\n"
                    + "Best regards,\n"
                    + "Event Management Team";

            message.setText(emailBody);

            Transport.send(message);
            System.out.println("Email sent successfully to: " + recipientEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    
    


    


    

}




