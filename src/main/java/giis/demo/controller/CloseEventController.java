package giis.demo.controller;

import giis.demo.model.*;
import giis.demo.util.SwingMain;
import giis.demo.util.SwingUtil;
import giis.demo.view.CloseEventView;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class CloseEventController {
    private CloseEventModel model;
    private CloseEventView view;
    private SwingMain loquesea;

    public CloseEventController(CloseEventModel model, CloseEventView view) {
        this.model = model;
        this.view = view;
        this.initView();
    }

    public void initView() {
        view.getFrmCloseEvent().setVisible(true);
    }

    public void initController(SwingMain swingMain) {
        // Cargar eventos al iniciar
        loquesea = swingMain;
        loadEvents();

        // Manejador del botón "Close Selected Event"
        view.getBtnCloseEvent().addActionListener(e -> closeSelectedEvent());
    }

    private void loadEvents() {
        // Obtener eventos originales
        List events = model.getEvents();
        
        // Obtener fecha actual del sistema
        String currentDate = loquesea.getFechaISO();

        // Lista para almacenar eventos con estados actualizados
        List<ConsultEventDTO> updatedEvents = new ArrayList<>();

        // Actualizar estados dinámicamente
        for (Object eventObj : events) {
            ConsultEventDTO event = (ConsultEventDTO) eventObj;
            
            // Determinar nuevo estado basado en fechas
            String newStatus = determineEventStatus(currentDate, 
                event.getEvent_date(), 
                event.getevent_endDate());
            
            // Crear una copia del evento con el nuevo estado
            ConsultEventDTO updatedEvent = new ConsultEventDTO();
            updatedEvent.setEvent_id(event.getEvent_id());
            updatedEvent.setEvent_name(event.getEvent_name());
            updatedEvent.setEvent_edition(event.getEvent_edition());
            updatedEvent.setEvent_date(event.getEvent_date());
            updatedEvent.setevent_endDate(event.getevent_endDate());
            updatedEvent.setEvent_status(newStatus);
            
            updatedEvents.add(updatedEvent);
        }

        // Establecer modelo de tabla con eventos actualizados
        view.getTblEvents().setModel(SwingUtil.getTableModelFromPojos(updatedEvents, 
            new String[]{"event_id", "event_name", "event_edition", "event_date", "event_endDate", "event_status"}));
        SwingUtil.autoAdjustColumns(view.getTblEvents());
        
        view.getTblEvents().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTblEvents().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTblEvents().getColumnModel().getColumn(0).setWidth(0);
    }

    private String determineEventStatus(String currentDate, 
                                        String eventStartDate, 
                                        String eventEndDate) {
        if (currentDate.compareTo(eventStartDate) < 0) {
            return "Planned";
        } else if (currentDate.compareTo(eventStartDate) >= 0 && 
                   currentDate.compareTo(eventEndDate) <= 0) {
            return "Ongoing";
        } else {
            return "Completed";
        }
    }

    private void closeSelectedEvent() {
        int selectedRow = view.getTblEvents().getSelectedRow();
        if (selectedRow == -1) {
            SwingUtil.showMessage("Select an event from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Obtener datos del evento
        int eventId = (Integer) view.getTblEvents().getModel().getValueAt(selectedRow, 0);
        String eventEndDate = (String) view.getTblEvents().getModel().getValueAt(selectedRow, 4);
        String currentDate = loquesea.getFechaISO();
        
        // Verificar condiciones ideales
        boolean isEventEnded = currentDate.compareTo(eventEndDate) >= 0;
        boolean isEventPaid = model.isEventPaid(eventId);
        
        // Si ya está en situación perfecta: Cerrar directamente
        if (isEventEnded && isEventPaid) {
            model.closeEvent(eventId);
            SwingUtil.showMessage("Event closed successfully (perfect conditions).", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadEvents();
            return;
        }
        
        // Si no está en situación perfecta: Mostrar advertencia y opción
        StringBuilder warningMessage = new StringBuilder("The event does not meet all closure conditions:\n");
        if (!isEventEnded) 
            warningMessage.append("- The event has not ended yet (current date < end date).\n");
        if (!isEventPaid) 
            warningMessage.append("- There are unpaid balances.\n\n");
        warningMessage.append("Do you want to close it anyway?");
        
        int option = JOptionPane.showConfirmDialog(
            view.getFrmCloseEvent(),
            warningMessage.toString(),
            "Confirm Closure",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (option == JOptionPane.YES_OPTION) {
            model.closeEvent(eventId);
            SwingUtil.showMessage("Event closed manually.", "Success", JOptionPane.INFORMATION_MESSAGE);
            loadEvents();
        }
    }
}