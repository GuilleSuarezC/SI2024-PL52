package giis.demo.controller;

import giis.demo.model.*;
import giis.demo.util.*;
import giis.demo.util.SwingMain;
import giis.demo.util.SwingUtil;
import giis.demo.view.LTAView;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;

public class LTAController {
    private LTAModel model;
    private LTAView view;
    private SwingMain loquesea;
    public List<CompanyDTO> companies;
    public List<LTAEventDTO> events;
    public List<LTASPLevelDTO> levels;

    public LTAController(LTAModel model, LTAView view) {
        this.model = model;
        this.view = view;
        companies = new ArrayList<CompanyDTO>();
        events = new ArrayList<LTAEventDTO>();
        levels = new ArrayList<LTASPLevelDTO>();
        this.initView();
    }

    public void initView() {
        view.getFrmCloseEvent().setVisible(true);
    }

    public void initController(SwingMain swingMain) {
        // Cargar eventos al iniciar
        loquesea = swingMain;
        loadCompaniesToComboBox();
        loadActiveEventsToTable();
        
        JTable jtable = view.getEventsTable();
	    jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    // Agregar un listener de selección a la tabla de eventos
	    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	        @Override
	        public void valueChanged(ListSelectionEvent e) {
	            if (!e.getValueIsAdjusting()) { // Para evitar llamadas dobles
	                
	            	int selectedRow = jtable.getSelectedRow();
	                
	                if (selectedRow != -1) { // Si hay una fila seleccionada
	                    if (events == null || events.isEmpty()) {
	                    	
	                        System.err.println("Error: eventList está vacía o no ha sido inicializada.");
	                        return;
	                    }
	                    int eventId = events.get(selectedRow).getEvent_id(); // Obtener el ID del evento
	                    loadSponsorshipLevels(eventId);
	                    
	                }
	            }
	        }
	    });  
    }
    
    private void loadSponsorshipLevels(int eventId) {
        List<LTASPLevelDTO> levels = model.getSponsorshipLevels(eventId);
        DefaultComboBoxModel<LTASPLevelDTO> comboModel = new DefaultComboBoxModel<>();
        levels.forEach(comboModel::addElement);
        view.getSpLevelComboBox().setModel(comboModel);
    }
    
    private void loadCompaniesToComboBox() {
        companies = model.getCompanies();
        DefaultComboBoxModel<CompanyDTO> comboModel = new DefaultComboBoxModel<>();
        companies.forEach(comboModel::addElement);
        view.getSponsorComboBox().setModel(comboModel); 
    }

    private void loadActiveEventsToTable() {
        List<LTAEventDTO> events = model.getActiveEvents();
        DefaultTableModel tableModel = (DefaultTableModel) view.getEventsTable().getModel();
        tableModel.setRowCount(0);

        for (LTAEventDTO event : events) {
        	this.events.add(event);
            tableModel.addRow(new Object[]{
                event.getEvent_id(),
                event.getEvent_name(),
                event.getEvent_edition(),
                event.getEvent_date(),
                event.getEvent_endDate(),
                event.getEvent_status()
            });
        }
        
        // Ocultar columnas ID (columna 0) y Estado (columna 5)
        JTable table = view.getEventsTable();
        table.getColumnModel().getColumn(0).setMinWidth(0); // ID
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        
        table.getColumnModel().getColumn(5).setMinWidth(0); // Estado
        table.getColumnModel().getColumn(5).setMaxWidth(0);
        table.getColumnModel().getColumn(5).setWidth(0);
    }
    
    

}