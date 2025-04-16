package giis.demo.controller;

import giis.demo.model.*;
import giis.demo.util.SwingUtil;
import giis.demo.view.ReOpenEventView;

import javax.swing.*;
import java.util.List;

public class ReOpenEventController {
    private ReOpenEventView view;
    private ReOpenEventModel modelr;

    public ReOpenEventController( ReOpenEventView view, ReOpenEventModel modelr) {
        this.view = view;
        this.modelr=modelr;
        initView();
    }

    public void initView() {
        view.getFrmReopenEvent().setVisible(true);
        loadClosedEvents();

        view.getBtnReopenEvent().addActionListener(e -> reopenSelectedEvent());
    }

    private void loadClosedEvents() {
        List<ConsultEventDTO> events = modelr.getClosedEvents();
        view.getTblClosedEvents().setModel(SwingUtil.getTableModelFromPojos(events, 
            new String[]{"event_id", "event_name", "event_edition", "event_date", "event_endDate", "event_status"}));
        SwingUtil.autoAdjustColumns(view.getTblClosedEvents());
    }

    private void reopenSelectedEvent() {
        int selectedRow = view.getTblClosedEvents().getSelectedRow();
        if (selectedRow == -1) {
            SwingUtil.showMessage("Select an event from the table.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int eventId = (Integer) view.getTblClosedEvents().getModel().getValueAt(selectedRow, 0);

        modelr.reopenEvent(eventId);
        SwingUtil.showMessage("Event reopened successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        loadClosedEvents(); 
    }
}
