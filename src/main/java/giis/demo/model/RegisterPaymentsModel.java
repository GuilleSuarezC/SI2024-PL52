package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.Util;
import giis.demo.util.ApplicationException;

/**
 * Modelo para gestionar los acuerdos de patrocinio.
 */
public class RegisterPaymentsModel {
    private Database db = new Database();
    
    private static final String SQL_GET_PENDING_PAYMENTS = "SELECT s.sponsorship_name, "
    		+ "s.sponsorship_agreementDate, e.event_fee,"
    		+ " p.invoice_id  FROM Sponsorship s LEFT JOIN Sponsorship"
    
}

