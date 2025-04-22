package giis.demo.controller;

import giis.demo.model.UpdatePaymentDTO;
import giis.demo.util.SwingMain;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import giis.demo.model.AgreementIncomeExpenseDTO;
import giis.demo.model.CancelIncomeExpenseModel;
import giis.demo.model.EditEventDTO;
import giis.demo.model.EditEventModel;
import giis.demo.model.MovementDTO;
import giis.demo.model.PendingPaymentDTO;
import giis.demo.model.RegisterPaymentsModel;
import giis.demo.model.ReportDTO;
import giis.demo.view.CancelIncomeExpensesView;
import giis.demo.view.EditEventView;
import giis.demo.view.RegisterPaymentsView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class EditEventController {
    
    private EditEventView view;
    private EditEventModel model;
    private List<EditEventDTO> list;
    private EditEventDTO selected;
    private SwingMain main;
    private String fecha;

    public EditEventController(EditEventView v, EditEventModel m, String fecha) {
        this.view = v;
        this.model = m;
        list = new ArrayList<EditEventDTO>();
        this.fecha = fecha;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate storedDate = LocalDate.parse(fecha, formatter);
        
        //Conseguir el primer dia del año y el del año siguiente
        LocalDate firstDayOfYear = storedDate.withDayOfYear(1);
        LocalDate firstDayOfNextYear = firstDayOfYear.plusYears(1);
        
        // Convertir LocalDate a String
        String startDateStr = firstDayOfYear.format(formatter);
        String endDateStr = firstDayOfNextYear.format(formatter);
        
        //loadActivities(startDate,endDate, "All");
        view.setStartDate(startDateStr);
        view.setEndDate(endDateStr);
        
        this.initView(startDateStr, endDateStr);
    }

    public void initController(SwingMain s) {
    	this.main=s;
    	view.getBtnApplyFilter().addActionListener(e -> SwingUtil.exceptionWrapper(() -> ApplyFilter()));    	
    	view.getLstActivities().getSelectionModel().addListSelectionListener(e -> 
	        SwingUtil.exceptionWrapper(() -> {
	            if (!e.getValueIsAdjusting()) {
	                int selectedRow = view.getLstActivities().getSelectedRow();
	                if (selectedRow != -1) {
	                    selectEventFromTable(selectedRow);
	                }
	            }
	        })
	    );
    	view.getBtnRegisterNewSL().addActionListener(e -> SwingUtil.exceptionWrapper(() -> RegisterSponsorshipLevel()));
    	view.getBtnEditEvent().addActionListener(e -> SwingUtil.exceptionWrapper(() -> EditEvent()));
        view.getBtnClearFields().addActionListener(e -> SwingUtil.exceptionWrapper(() -> clearSelection()));
    }
    
    public void initView(String startDate, String endDate) {
        this.loadEvents(startDate, endDate);
        view.getFrame().setVisible(true);
    }

    private void loadEvents(String startDate, String endDate) {
    	
		List<EditEventDTO> events = model.getEvents(startDate, endDate);
        TableModel tableModel = SwingUtil.getTableModelFromPojos(events,
            new String[]{"event_id","event_name", "event_edition", "event_date", "event_endDate", "event_status"});
        
        view.getLstActivities().setModel(tableModel);
        view.getLstActivities().getColumnModel().getColumn(0).setMinWidth(0);
        view.getLstActivities().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getLstActivities().getColumnModel().getColumn(0).setWidth(0);

        SwingUtil.autoAdjustColumns(view.getLstActivities());
        
        
    }
    
    private void loadSponsorshipLevels(int eventId)
    {
    	List<EditEventDTO> levels = model.getSponsorshipLevels(eventId);
        TableModel tableModel = SwingUtil.getTableModelFromPojos(levels,
            new String[]{"level_id","level_name","level_price"});
        
        view.getLstSponsorshipLevels().setModel(tableModel);
        view.getLstSponsorshipLevels().getColumnModel().getColumn(0).setMinWidth(0);
        view.getLstSponsorshipLevels().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getLstSponsorshipLevels().getColumnModel().getColumn(0).setWidth(0);

        SwingUtil.autoAdjustColumns(view.getLstSponsorshipLevels());
    }
    
    

    private void selectEventFromTable(int rowIndex) {
    	
        JTable table = view.getLstActivities();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        try {
        	
            selected = new EditEventDTO(
                Integer.parseInt(table.getValueAt(rowIndex, 0).toString()),  	// eventId	           
                table.getValueAt(rowIndex, 1).toString(),                   	// eventName
                table.getValueAt(rowIndex, 2).toString(),						// eventEdition
                table.getValueAt(rowIndex, 3).toString(),                   	// eventDate
                table.getValueAt(rowIndex, 4).toString(),                   	// eventEndDate
                table.getValueAt(rowIndex, 5).toString()                   		// eventStatus
            );

            view.setName(selected.getEvent_name());
            view.setEdition(selected.getEvent_edition());
            view.setStartDateField(selected.getEvent_date());
            view.setEndDateField(selected.getEvent_endDate());
            view.setStatus(selected.getEvent_status());
            
        } catch (Exception e) {
            System.out.println("Error selecting the event: " + e.getMessage());
        }
        
        loadSponsorshipLevels(selected.getEvent_id());
    }
    
    
    private void EditEvent()
    {
    	
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "You must select an event to be able to make a edit it.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {        	
        	if (selected.getEvent_status().equals("Closed"))
        	{
        		SwingUtil.showMessage("Events that are already closed cannot be edited.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        	
        	if(view.getName().isEmpty())
        	{
        		JOptionPane.showMessageDialog(view.getFrame(), "You must input a valid name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}   	      
        	
        	if(view.getStartDateField().isEmpty())
        	{
        		JOptionPane.showMessageDialog(view.getFrame(), "You must input a start date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        	
        	if(view.getEndDateField().isEmpty())
        	{
        		JOptionPane.showMessageDialog(view.getFrame(), "You must input an end date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        	
        	
        	if (!Util.isValidISODate(view.getStartDateField()) || !Util.isValidISODate(view.getEndDateField())){
        		JOptionPane.showMessageDialog(view.getFrame(), "INCORRECT FORMAT: YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        	
        	Date startDate = Util.isoStringToDate(view.getStartDateField());
        	Date endDate = Util.isoStringToDate(view.getEndDateField());
        	Date todayDate = Util.isoStringToDate(fecha);
        	
        	if(endDate.before(startDate))
        	{
        		JOptionPane.showMessageDialog(view.getFrame(), "The end date cannot be before the start date", "Error", JOptionPane.ERROR_MESSAGE);
                return;
        	}
        	
        	
        	if(startDate.before(todayDate))
        	{
        		int option = JOptionPane.showConfirmDialog(view.getFrame(),
            			"You are going to change the date of the event to the past, are you sure?",
                        "Date Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                     if (option != JOptionPane.YES_OPTION) {
                         return; // Usuario eligió "Cancel"
                     }
        	}else { 
	        	int option = JOptionPane.showConfirmDialog(view.getFrame(),
	        			"You are going to edit the selected event, are you sure?",
	                    "Selection Warning",
	                    JOptionPane.YES_NO_OPTION,
	                    JOptionPane.WARNING_MESSAGE);
	
	                 if (option != JOptionPane.YES_OPTION) {
	                     return; // Usuario eligió "Cancel"
	                 }
        	}    
	         model.UpdateEvent(view.getName(), view.getEdition(), view.getStartDateField(), view.getEndDateField(), (String)view.getStatus().getSelectedItem(), selected.getEvent_id());                                
	         JOptionPane.showMessageDialog(view.getFrame(), "The event has been edited correctly.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	
	         // Refrescar la tabla y limpiar selección
	         loadEvents(view.getStartDate(), view.getEndDate());
	         clearSelection();    
        }catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error editing the event: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void RegisterSponsorshipLevel() {
    	
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "You must select an event to be able to register a sponsorship level.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }       

        try {        	
        	
            String levelName = JOptionPane.showInputDialog("Enter level name:");
            if (levelName == null || levelName.trim().isEmpty()) {
                SwingUtil.showMessage("Level name cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String priceStr = JOptionPane.showInputDialog("Enter level price:");
            if (priceStr == null || priceStr.trim().isEmpty()) {
                SwingUtil.showMessage("Price cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);

                if (price < 0) {
                    SwingUtil.showMessage("Price cannot be negative.", "Invalid Price", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Math.round(price * 100.0) != price * 100.0) {
                    SwingUtil.showMessage("Price must have at most two decimal places.", "Invalid Format", JOptionPane.ERROR_MESSAGE);
                    return;
                }

            } catch (NumberFormatException e) {
                SwingUtil.showMessage("Invalid price format.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            model.RegisterSponsorshipLevel(levelName, price, selected.getEvent_id());

            loadSponsorshipLevels(selected.getEvent_id());
                      
                                            
            JOptionPane.showMessageDialog(view.getFrame(), "The sponsorship level had been registered correctly.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Input a numeric amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error registering the payment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void clearSelection() {
        view.getLstActivities().clearSelection();
        view.getLstSponsorshipLevels().clearSelection();
        selected = null;
        view.setName("");
        view.setEdition("");
        view.setStartDateField("");
        view.setEndDateField("");
        view.setStatus("");
    }
    
    private void ApplyFilter() {
        if ((view.getStartDate() == "") || (view.getEndDate() == "")) {
     	   
            JOptionPane.showMessageDialog(view.getFrame(), "You must select a start date and an end date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
 	       String startDate = view.getStartDate();
 	       String endDate = view.getEndDate(); 	      
 	       this.loadEvents(startDate, endDate);
        }
     }
}