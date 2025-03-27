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
    /**
     * Inicializa la vista y carga los eventos en el comboBox.
     */
    public void initView() {
    	this.loadEvents();
        view.getFrmCloseEvent().setVisible(true);
    }


    public void initController(SwingMain swingMain) {
        // Cargar eventos al iniciar
        loadEvents();
        loquesea = swingMain;
        // Manejador del botón "Close Selected Event"
        view.getBtnCloseEvent().addActionListener(e -> closeSelectedEvent());
    }

    private void loadEvents() {
        List<ConsultEventDTO> events = model.getEvents();
        view.getTblEvents().setModel(SwingUtil.getTableModelFromPojos(events, 
            new String[]{"event_id", "event_name", "event_edition", "event_date", "event_endDate", "event_status"}));
        SwingUtil.autoAdjustColumns(view.getTblEvents());
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

    private boolean checkEventEndDate(String eventEndDate) {
        String currentDate = loquesea.getFechaISO(); // Fecha actual del sistema desde SwingMain
        // Comparación de fechas en formato ISO (YYYY-MM-DD)
        return currentDate.compareTo(eventEndDate) >= 0; // true si currentDate >= eventEndDate
    }
}