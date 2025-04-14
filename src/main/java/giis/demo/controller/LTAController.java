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
        
        view.getBtnAddEvt_SPLvL().addActionListener(e -> addEventToSummary());
        view.getBtnEdit().addActionListener(e -> editSelectedEvent());

        
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
	                    	
	                        System.err.println("Error: eventList is empty or has not been initialized.");
	                        return;
	                    }
	                    int eventId = events.get(selectedRow).getEvent_id(); // Obtener el ID del evento
	                    loadSponsorshipLevels(eventId);
	                    
	                }
	            }
	        }
	    });  
    }
    
//    // Método para añadir filas a la tabla de resumen
//    private void addEventToSummary() {
//        // Obtener selecciones
//        int selectedEventRow = view.getEventsTable().getSelectedRow();
//        LTASPLevelDTO selectedLevel = (LTASPLevelDTO) view.getSpLevelComboBox().getSelectedItem();
//        
//        // Validar selecciones
//        if (selectedEventRow == -1 || selectedLevel == null) {
//            JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
//                "¡Selecciona un evento y un nivel de patrocinio!", "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
//        
//        // Obtener datos
//        LTAEventDTO event = events.get(selectedEventRow);
//        String levelInfo = selectedLevel.getLevel_name() + " - " + selectedLevel.getLevel_price() + "€";
//        
//        // Añadir a la tabla
//        DefaultTableModel model = (DefaultTableModel) view.getSummaryTable().getModel();
//        model.addRow(new Object[]{
//            event.getEvent_name(),
//            event.getEvent_edition() != null ? event.getEvent_edition() : "", // Edición (o vacío)
//            levelInfo  // Formato: "Nombre - Precio€"
//        });
//        
//        // Actualizar Total Fee (sumar el precio del nivel)
//        updateTotalFee(selectedLevel.getLevel_price());
//    }

    // Método auxiliar para actualizar el total
    private void updateTotalFee(double amount) {
        try {
            double current = Double.parseDouble(view.gettFTotalFee().replace(",", ".")); // Manejar decimales
            view.settFTotalFee(String.format("%.2f", current + amount));
        } catch (NumberFormatException e) {
            view.settFTotalFee(String.format("%.2f", amount)); // Si estaba vacío, inicia con este valor
        }
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
                (event.getEvent_edition() != null) ? event.getEvent_edition() : "",
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
    
    // Método para añadir eventos
    private void addEventToSummary() {
        int selectedEventRow = view.getEventsTable().getSelectedRow();
        LTASPLevelDTO selectedLevel = (LTASPLevelDTO) view.getSpLevelComboBox().getSelectedItem();
        
        // Validaciones básicas
        if (view.gettFTotalFee().isEmpty()) {
            JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                "Please insert the total fee first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (selectedEventRow == -1 || selectedLevel == null) {
            JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                "Select an event & a sponsorship level.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Validar el delta (precio del nivel a añadir)
        if (validateTotalFee(selectedLevel.getLevel_price()) == 1) {
            return; // No añadir si hay error
        }
        
        // Añadir fila al resumen
        LTAEventDTO event = events.get(selectedEventRow);
        String levelInfo = String.format("%s - %.2f€", selectedLevel.getLevel_name(), selectedLevel.getLevel_price());
        
        DefaultTableModel model = (DefaultTableModel) view.getSummaryTable().getModel();
        model.addRow(new Object[]{
            event.getEvent_name(),
            event.getEvent_edition() != null ? event.getEvent_edition() : "",
            levelInfo
        });
    }

    // Método para editar evento en el resumen
    private void editSelectedEvent() {
        int selectedSummaryRow = view.getSummaryTable().getSelectedRow();
        if (selectedSummaryRow == -1) return;

        // Obtener datos de la fila seleccionada
        DefaultTableModel summaryModel = (DefaultTableModel) view.getSummaryTable().getModel();
        String eventName = (String) summaryModel.getValueAt(selectedSummaryRow, 0);
        String levelInfo = (String) summaryModel.getValueAt(selectedSummaryRow, 2);
        String currentLevelName = levelInfo.split(" - ")[0]; // Extraer nombre del nivel

        // Buscar el evento en la lista de eventos
        LTAEventDTO targetEvent = events.stream()
            .filter(e -> e.getEvent_name().equals(eventName))
            .findFirst()
            .orElse(null);

        if (targetEvent == null) return;

        // Seleccionar el evento en la tabla superior
        int eventIndex = events.indexOf(targetEvent);
        view.getEventsTable().setRowSelectionInterval(eventIndex, eventIndex);

        // Cargar niveles del evento
        loadSponsorshipLevels(targetEvent.getEvent_id());

        // Preseleccionar el nivel en el ComboBox
        for (int i = 0; i < view.getSpLevelComboBox().getItemCount(); i++) {
            LTASPLevelDTO level = view.getSpLevelComboBox().getItemAt(i);
            if (level.getLevel_name().equals(currentLevelName)) {
                view.getSpLevelComboBox().setSelectedIndex(i);
                break;
            }
        }

        try {
            // Resto del código para eliminar la fila...
            String priceStr = levelInfo.split(" - ")[1]
                                .replace("€", "")
                                .replace(",", ".");
            double oldPrice = Double.parseDouble(priceStr);
            
            if (validateTotalFee(-oldPrice) == 1) return;
            
            summaryModel.removeRow(selectedSummaryRow);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                "Error processing sponsorship level", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para validar que la suma de niveles ≤ Total Fee
    private int validateTotalFee(double delta) {
        try {
            // Convertir Total Fee a double (manejar comas y puntos)
            String totalFeeStr = view.gettFTotalFee().replace(",", ".");
            double totalFee = Double.parseDouble(totalFeeStr);
            
            double currentSum = calculateSelectedLevelsSum();
            double newSum = currentSum + delta;
            
            // Tolerancia para errores de precisión flotante
            final double TOLERANCE = 0.001; 
            if (newSum - totalFee > TOLERANCE) {
                JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                    "The addition of the selected levels (" + String.format("%.2f", newSum) + "€) exceeds the Total Fee (" + String.format("%.2f", totalFee) + "€)", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return 1; 
            }
            return 0; // Válido
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                "Invalid Total Fee. Use numbers (e.g., 8000 or 8000.00)", "Error", JOptionPane.ERROR_MESSAGE);
            return 1;
        }
    }

    // Método auxiliar para calcular la suma de los niveles
    private double calculateSelectedLevelsSum() {
        DefaultTableModel model = (DefaultTableModel) view.getSummaryTable().getModel();
        double sum = 0.0;
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String levelInfo = (String) model.getValueAt(i, 2);
            // Extraer y convertir el precio (manejar comas y puntos)
            String priceStr = levelInfo.split(" - ")[1].replace("€", "").replace(",", ".");
            double price = Double.parseDouble(priceStr);
            sum += price;
        }
        
        return sum;
    }

    
    private void resetForm() {
        view.getSponsorComboBox().setSelectedIndex(-1);
        view.getSpLevelComboBox().setSelectedIndex(-1);
        view.settFStartDate("");
        view.settFEndDate("");
        view.settFTotalFee("");
        //selectedEvents.clear();
        ((DefaultTableModel) view.getSummaryTable().getModel()).setRowCount(0);
    }

}