package giis.demo.model;

import java.util.List;
import java.util.stream.Collectors;
import giis.demo.util.Database;
import giis.demo.util.UnexpectedException;

public class RegisterIncomeExpensesModel {
    private Database db = new Database();

   
    public List<String> getEventNames() {
        String sql = "SELECT DISTINCT event_name FROM Event";
        List<Object[]> results = db.executeQueryArray(sql);
        return results.stream().map(row -> (String) row[0]).collect(Collectors.toList());
    }

   
    public int getEventIdByName(String eventName) {
        String sql = "SELECT event_id FROM Event WHERE event_name = ?";
        List<Object[]> results = db.executeQueryArray(sql, eventName);
        if (results.isEmpty()) {
            throw new UnexpectedException("Event not found: " + eventName);
        }
        return (int) results.get(0)[0];
    }


    public List<BalanceDTO> getBalancesByEvent(String eventName) {
    	String sql = "SELECT b.balance_id, b.concept, b.event_id, b.amount, b.description, b.balance_status " + 
                "FROM Balance b " + 
                "JOIN Event ev ON b.event_id = ev.event_id " +
                "LEFT JOIN Sponsorship s ON b.balance_id = s.balance_id " + 
                "WHERE ev.event_name = ? " +
                "AND s.sponsorship_id IS NULL";

        List<Object[]> results = db.executeQueryArray(sql, eventName);
        
        return results.stream().map(row -> {
            int balanceId = (int) row[0];  
            String concept = (String) row[1];  
            int eventId = (int) row[2];  
            Object amountObject = row[3];
            double amount = 0.0;
            if (amountObject instanceof Double) {
                amount = (Double) amountObject;
            } else if (amountObject instanceof Integer) {
                amount = ((Integer) amountObject).doubleValue();
            }

            String description = (String) row[4];  
            String balanceStatus = (String) row[5];  

            return new BalanceDTO(balanceId, concept, eventId, amount, description, balanceStatus);
        }).collect(Collectors.toList());
    }

    
    public void addBalance(String concept, int eventId, double amount, String description, String balanceStatus, String dateOfPaid) {
        if (balanceStatus == null || balanceStatus.trim().isEmpty() || 
            !isValidBalanceStatus(balanceStatus)) {
            throw new IllegalArgumentException("Invalid balance status: " + balanceStatus);
        }

        String sql = "INSERT INTO Balance (concept, event_id, amount, description, balance_status) " +
                "VALUES (?, ?, ?, ?, ?)";
        db.executeUpdate(sql, concept, eventId, amount, description, balanceStatus);

        String getLastInsertIdSQL = "SELECT MAX(balance_id) FROM Balance";
        List<Object[]> results = db.executeQueryArray(getLastInsertIdSQL);
        int balanceId = ((Number) results.get(0)[0]).intValue();

        if ("Paid".equals(balanceStatus)) {
            String movementSql = "INSERT INTO Movement (movement_amount, movement_date, balance_id) " +
                                 "VALUES (?, ?, ?)";
            db.executeUpdate(movementSql, amount, dateOfPaid, balanceId);
        }
    }
    
    public void addMovement(double amount, String balanceStatus, int balanceId) {
    	String sql = "INSERT INTO Movement (movement_amount, movement_date, balance_id) VALUES (?, ?, ?)";
    	db.executeUpdate(sql,amount, balanceStatus, balanceId);
    }

    public void updateBalanceM(int balanceId)
    {
    	 String updateSql = "UPDATE Balance SET balance_status = 'Paid' WHERE balance_id = ?";
         db.executeUpdate(updateSql,balanceId);
    }



    private boolean isValidBalanceStatus(String balanceStatus) {
        return balanceStatus.equals("Paid") || balanceStatus.equals("Estimated") ;
    }

 
    public void updateBalance(BalanceDTO balance, String dateOfPaid) {
        // Obtener el estado anterior
        String sqlGetStatus = "SELECT balance_status FROM Balance WHERE balance_id = ?";
        List<Object[]> result = db.executeQueryArray(sqlGetStatus, balance.getBalanceId());

        if (result.isEmpty()) {
            throw new IllegalArgumentException("Balance ID not found: " + balance.getBalanceId());
        }

        String previousStatus = (String) result.get(0)[0];

        // Actualizar el balance
        String updateSql = "UPDATE Balance SET concept = ?, event_id = ?, amount = ?, description = ?, balance_status = ? WHERE balance_id = ?";
        db.executeUpdate(updateSql, balance.getConcept(), balance.getEventId(), balance.getAmount(),
                         balance.getDescription(), balance.getBalanceStatus(), balance.getBalanceId());

        // Si cambia de Estimated a Paid, registrar movimiento
        if ("Estimated".equals(previousStatus) && "Paid".equals(balance.getBalanceStatus())) {
            String insertMovementSql = "INSERT INTO Movement (movement_amount, movement_date, balance_id) " +
                                       "VALUES (?, ?, ?)";
            db.executeUpdate(insertMovementSql, balance.getAmount(), dateOfPaid, balance.getBalanceId());
        }
    }





}
