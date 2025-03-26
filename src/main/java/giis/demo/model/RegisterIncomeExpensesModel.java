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
        String sql = "SELECT balance_id, concept, Balance.event_id, amount, description, dateOfPaid, balance_status " + 
                     "FROM Balance " + 
                     "JOIN Event ev ON Balance.event_id = ev.event_id " + 
                     "WHERE ev.event_name = ?";
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
            String dateOfPaid = (String) row[5];  
            String balanceStatus = (String) row[6];  

            return new BalanceDTO(balanceId, concept, eventId, amount, description, dateOfPaid, balanceStatus);
        }).collect(Collectors.toList());
    }

    
    public void addBalance(String concept, int eventId, double amount, String description, String dateOfPaid, String balanceStatus) {
        if (balanceStatus == null || balanceStatus.trim().isEmpty() || 
            !isValidBalanceStatus(balanceStatus)) {
            throw new IllegalArgumentException("Invalid balance status: " + balanceStatus);
        }

        
        String sql = "INSERT INTO Balance (concept, event_id, amount, description, dateOfPaid, balance_status) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        db.executeUpdate(sql, concept, eventId, amount, description, dateOfPaid, balanceStatus);

      
        String getLastInsertIdSQL = "SELECT last_insert_rowid()";
        List<Object[]> results = db.executeQueryArray(getLastInsertIdSQL);
        int balanceId = ((Number) results.get(0)[0]).intValue();

        
        if ("Paid".equals(balanceStatus)) {
            String movementSql = "INSERT INTO Movement (movement_amount, movement_date, balance_id) " +
                                 "VALUES (?, ?, ?)";
            db.executeUpdate(movementSql, amount, dateOfPaid, balanceId);
        }
    }




    private boolean isValidBalanceStatus(String balanceStatus) {
        return balanceStatus.equals("Paid") || balanceStatus.equals("Overpaid") ||
               balanceStatus.equals("Underpaid") || balanceStatus.equals("Unpaid");
    }

 
    public boolean updateBalance(BalanceDTO balance) {
        
        String sql = "UPDATE Balance SET concept = ?, amount = ?, description = ?, dateOfPaid = ?, balance_status = ? " +
                     "WHERE balance_id = ?";
        try {
            db.executeUpdate(sql, balance.getConcept(), balance.getAmount(), balance.getDescription(),
                             balance.getDateOfPaid(), balance.getBalanceStatus(), balance.getBalanceId());

           
            if ("Paid".equals(balance.getBalanceStatus())) {
                String movementSql = "INSERT INTO Movement (movement_amount, movement_date, balance_id) " +
                                     "VALUES (?, ?, ?)";
                db.executeUpdate(movementSql, balance.getAmount(), balance.getDateOfPaid(), balance.getBalanceId());
            }
            
            return true;
        } catch (UnexpectedException e) {
            e.printStackTrace();
            return false;
        }
    }


}
