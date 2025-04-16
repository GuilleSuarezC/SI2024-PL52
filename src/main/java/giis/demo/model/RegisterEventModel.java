package giis.demo.model;

import java.util.List;
import giis.demo.util.Database;
import giis.demo.util.DbUtil;

public class RegisterEventModel {
    private Database db = new Database();
    private RegisterEventDTO lastRegisteredEvent;

    
    private static final String SQL_GET_LAST_INSERTED_ID = "SELECT last_insert_rowid()";


    // Consulta para buscar eventos por nombre (case-insensitive)
    private static final String SQL_GET_EVENTS_BY_NAME = 
        "SELECT event_name, event_edition " +
        "FROM Event " +
        "WHERE LOWER(event_name) LIKE LOWER(?)"; // ? se reemplazará por "%nombre%"

    // Consulta para insertar un nuevo evento
    private static final String SQL_INSERT_EVENT = 
    	    "INSERT INTO Event (event_name, event_edition, event_date, event_endDate, event_status) " +
    	    "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_INSERT_SPONSORSHIP_LEVEL =
    	    "INSERT INTO SponsorshipLevel (level_name, level_price, event_id) VALUES (?, ?, ?)";


    /**
     * Obtiene eventos existentes cuyo nombre coincida con un patrón (case-insensitive).
     * @param namePattern Patrón de búsqueda (ej: "%conferencia%").
     */
    public List<ExistingEventDTO> getExistingEventsByName(String namePattern) {
        String searchPattern = "%" + namePattern + "%"; // Agrega wildcards para búsqueda parcial
        return db.executeQueryPojo(ExistingEventDTO.class, SQL_GET_EVENTS_BY_NAME, searchPattern);
    }

    /**
     * Registra un nuevo evento en la base de datos.
     * @param event Datos del evento a registrar.
     */
    public void registerEvent(RegisterEventDTO event) {
        db.executeUpdate(
            SQL_INSERT_EVENT,
            event.getEvent_name(),
            event.getEvent_edition(),
            event.getEvent_date(),
            event.getEvent_endDate(),
            event.getEvent_status()
        );
        lastRegisteredEvent = event; // Save the registered event
    }
    
    public void addSponsorshipLevel(String name, double price, int eventId) {
        db.executeUpdate(SQL_INSERT_SPONSORSHIP_LEVEL, name, price, eventId);
    }
    

    
    public RegisterEventDTO getLastRegisteredEvent() {
        return lastRegisteredEvent;
    }
    
    public void insertSponsorshipLevel(int eventId, String name, double amount) {
        String sql = "INSERT INTO SponsorshipLevel (event_id, level_name, level_price) VALUES (?, ?, ?)";
        db.executeUpdate(sql, eventId, name, amount);
    }
    
    




   

}