package giis.demo.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import giis.demo.model.*;
import giis.demo.model.EventDTO;
import giis.demo.util.SwingUtil;
import giis.demo.util.SwingMain;
import giis.demo.view.ConsultEventStatusView;

public class ConsultEventStatusController {
    private ConsultEventStatusModel model;
    private ConsultEventStatusView view;
    private List<ConsultEventDTO> eventList;
    private List<SponsorshipInfoDTO> sponsorList;
    private SwingMain loquesea;

    public ConsultEventStatusController(ConsultEventStatusModel model, ConsultEventStatusView view) {
    	this.eventList = new ArrayList<ConsultEventDTO>();
    	this.sponsorList = new ArrayList<SponsorshipInfoDTO>();
        this.model = model;
        this.view = view;
        this.initView();
    }

    /**
     * Inicializa la vista y carga los eventos en el comboBox.
     */
    public void initView() {
    	this.loadEvents();
        view.getFrame().setVisible(true);
    }

    /**
     * Inicializa los manejadores de eventos.
     */
    public void initController(SwingMain sm) {
    	loquesea = sm;
	    JTable jtable = view.getTblEvents();
	    jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    // Agregar un listener de selección a la tabla de eventos
	    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	        @Override
	        public void valueChanged(ListSelectionEvent e) {
	            if (!e.getValueIsAdjusting()) { // Para evitar llamadas dobles
	                
	            	int selectedRow = jtable.getSelectedRow();
	                
	                if (selectedRow != -1) { // Si hay una fila seleccionada
	                    if (eventList == null || eventList.isEmpty()) {
	                    	
	                        System.err.println("Error: eventList está vacía o no ha sido inicializada.");
	                        return;
	                    }
	                    int eventId = eventList.get(selectedRow).getEvent_id(); // Obtener el ID del evento
	                    showSponsors(eventId); // Llamar al método para mostrar patrocinios
	                }
	            }
	        }
	    });  	
    }

    /**
     * Carga la lista de eventos en la lista.
     */
    private void loadEvents() {
        List<ConsultEventDTO> events = model.getEvents();
        for(ConsultEventDTO event : events) {
        	this.eventList.add(event);
        }
        // Incluir event_id en el TableModel
        TableModel tableModel = SwingUtil.getTableModelFromPojos(eventList, 
            new String[] {"event_id", "event_name", "event_edition", "event_date", "event_duration", "event_status"});
        view.getTblEvents().setModel(tableModel);

        // Ocultar la columna event_id
        view.getTblEvents().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTblEvents().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTblEvents().getColumnModel().getColumn(0).setWidth(0);

        // Ajustar el ancho de las columnas
        SwingUtil.autoAdjustColumns(view.getTblEvents());
    }   
    
    public void showSponsors(int eventId) {
    	if(eventId < 0) return;
        // Obtener la lista de patrocinios asociados al evento
        List<SponsorshipInfoDTO> sponsors = model.getSponsorshipsByEventId(eventId);
        for (SponsorshipInfoDTO s : sponsors) {
        	if(s.getpayment_status() == null) {
        		s.setpayment_status("Estimated");
        	}
        	this.sponsorList.add(s);
        }
        TableModel tableModel = SwingUtil.getTableModelFromPojos(sponsors,new String[] {"sponsorship_name", "sponsorship_agreementDate", "payment_status", "event_fee"});
        view.getTblSponsorships().setModel(tableModel);
        SwingUtil.autoAdjustColumns(view.getTblSponsorships());
    }
}

