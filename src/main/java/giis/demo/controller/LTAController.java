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
    private boolean isEditing = false;
    private int editingRow = -1;

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
        view.getBtnRemove().addActionListener(e -> removeSelectedEvent());
        
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
        if (isEditing) {
            // Modo edición - Confirmar cambios
            LTASPLevelDTO newLevel = (LTASPLevelDTO) view.getSpLevelComboBox().getSelectedItem();
            if (newLevel == null) return;

            // Calcular diferencia de precios
            String oldLevelInfo = (String) view.getSummaryTable().getModel().getValueAt(editingRow, 2);
            double oldPrice = Double.parseDouble(oldLevelInfo.split(" - ")[1].replace("€", "").replace(",", "."));
            double delta = newLevel.getLevel_price() - oldPrice;

            // Validar nuevo total
            if (validateTotalFee(delta) == 1) return;

            // Actualizar fila
            String newLevelInfo = String.format("%s - %.2f€", newLevel.getLevel_name(), newLevel.getLevel_price());
            DefaultTableModel model = (DefaultTableModel) view.getSummaryTable().getModel();
            model.setValueAt(newLevelInfo, editingRow, 2);

            // Restaurar estado normal
            resetEditingState();
        } else {
            // Código para añadir nuevo evento
        	int selectedEventRow = view.getEventsTable().getSelectedRow();
            LTASPLevelDTO selectedLevel = (LTASPLevelDTO) view.getSpLevelComboBox().getSelectedItem();
            String totalFeeStr = view.gettFTotalFee();
            
            // 1. Validar que no esté vacío
            if (totalFeeStr.isEmpty()) {
                JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                    "Please insert the total fee first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // 2. Validar formato numérico
            try {
                double totalFee = Double.parseDouble(totalFeeStr.replace(",", "."));
                
                // 3. Validar que no sea negativo
                if (totalFee < 0) {
                    JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                        "Total Fee cannot be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                    "Invalid Total Fee. Use numbers (e.g., 8000 or 8000.00)", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (selectedEventRow == -1 || selectedLevel == null) {
                JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                    "Select an event & a sponsorship level.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener datos del evento
            LTAEventDTO event = events.get(selectedEventRow);
            String eventNameToAdd = event.getEvent_name();
            
            // Validar duplicados
            DefaultTableModel summaryModel = (DefaultTableModel) view.getSummaryTable().getModel();
            for (int i = 0; i < summaryModel.getRowCount(); i++) {
                String existingEventName = (String) summaryModel.getValueAt(i, 0);
                if (existingEventName.equalsIgnoreCase(eventNameToAdd)) {
                    JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                        "This event is already in the summary", "Duplicate Event", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Validar el delta (precio del nivel a añadir)
            if (validateTotalFee(selectedLevel.getLevel_price()) == 1) return;
            
            // Añadir fila al resumen
            String levelInfo = String.format("%s - %.2f€", selectedLevel.getLevel_name(), selectedLevel.getLevel_price());
            summaryModel.addRow(new Object[]{
                eventNameToAdd,
                event.getEvent_edition() != null ? event.getEvent_edition() : "",
                levelInfo
            });
        }
    }

    // Método auxiliar para resetear estado
    private void resetEditingState() {
        isEditing = false;
        editingRow = -1;
        view.getEventsTable().setEnabled(true);
        view.getBtnEdit().setEnabled(true);
        view.getBtnAddEvt_SPLvL().setText("Add Event & Sponsorship Level");
    }

    private void editSelectedEvent() {
        if (isEditing) {
            JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                "Finish current edit before editing another", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int selectedSummaryRow = view.getSummaryTable().getSelectedRow();
        if (selectedSummaryRow == -1) return;

        // Obtener datos de la fila
        DefaultTableModel summaryModel = (DefaultTableModel) view.getSummaryTable().getModel();
        String eventName = (String) summaryModel.getValueAt(selectedSummaryRow, 0);
        String levelInfo = (String) summaryModel.getValueAt(selectedSummaryRow, 2);
        String currentLevelName = levelInfo.split(" - ")[0];
        double oldPrice = Double.parseDouble(levelInfo.split(" - ")[1].replace("€", "").replace(",", "."));

        // Buscar evento y nivel
        LTAEventDTO targetEvent = events.stream()
            .filter(e -> e.getEvent_name().equals(eventName))
            .findFirst().orElse(null);
        
        if (targetEvent == null) return;

        // Configurar modo edición
        isEditing = true;
        editingRow = selectedSummaryRow;
        
        // Bloquear controles
        view.getEventsTable().setEnabled(false);
        view.getBtnEdit().setEnabled(false);
        view.getBtnAddEvt_SPLvL().setText("Confirm Edit");

        // Seleccionar evento y nivel
        int eventIndex = events.indexOf(targetEvent);
        view.getEventsTable().setRowSelectionInterval(eventIndex, eventIndex);
        loadSponsorshipLevels(targetEvent.getEvent_id());
        
        for (int i = 0; i < view.getSpLevelComboBox().getItemCount(); i++) {
            LTASPLevelDTO level = view.getSpLevelComboBox().getItemAt(i);
            if (level.getLevel_name().equals(currentLevelName)) {
                view.getSpLevelComboBox().setSelectedIndex(i);
                break;
            }
        }
    }

    private void removeSelectedEvent() {
        int selectedRow = view.getSummaryTable().getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view.getFrmCloseEvent(), 
                "Select an event from the summary to remove", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Eliminar fila
        DefaultTableModel model = (DefaultTableModel) view.getSummaryTable().getModel();
        model.removeRow(selectedRow);
        
        // Limpiar ComboBox de niveles de patrocinio
        view.getSpLevelComboBox().setModel(new DefaultComboBoxModel<>());
        
        // Actualizar tabla de eventos para que reaparezca el evento eliminado
        loadActiveEventsToTable();
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
                		"Updating this level would exceed the Total Fee by " + String.format("%.2f", (newSum - totalFee)) + "€", 
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