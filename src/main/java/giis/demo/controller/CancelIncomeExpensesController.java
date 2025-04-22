package giis.demo.controller;

import giis.demo.model.UpdatePaymentDTO;
import giis.demo.util.SwingMain;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import giis.demo.model.AgreementIncomeExpenseDTO;
import giis.demo.model.CancelIncomeExpenseModel;
import giis.demo.model.MovementDTO;
import giis.demo.model.PendingPaymentDTO;
import giis.demo.model.RegisterPaymentsModel;
import giis.demo.model.ReportDTO;
import giis.demo.view.CancelIncomeExpensesView;
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

public class CancelIncomeExpensesController {
    
    private CancelIncomeExpensesView view;
    private CancelIncomeExpenseModel model;
    private List<AgreementIncomeExpenseDTO> list;
    private AgreementIncomeExpenseDTO selected;
    private SwingMain main;

    public CancelIncomeExpensesController(CancelIncomeExpensesView v, CancelIncomeExpenseModel m, String fecha) {
        this.view = v;
        this.model = m;
        list = new ArrayList<AgreementIncomeExpenseDTO>();
        
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
    	view.getBtnCancelRegister().addActionListener(e -> SwingUtil.exceptionWrapper(() -> RegisterMovement()));
    	view.getLstActivities().getSelectionModel().addListSelectionListener(e -> 
	        SwingUtil.exceptionWrapper(() -> {
	            if (!e.getValueIsAdjusting()) {
	                int selectedRow = view.getLstActivities().getSelectedRow();
	                if (selectedRow != -1) {
	                    selectBalanceFromTable(selectedRow);
	                }
	            }
	        })
	    );
    	view.getBtnCancel().addActionListener(e -> SwingUtil.exceptionWrapper(() -> CancelBalance()));
        view.getBtnClearFields().addActionListener(e -> SwingUtil.exceptionWrapper(() -> clearSelection()));
    }
    
    public void initView(String startDate, String endDate) {
        this.loadBalances(startDate, endDate, "Sponsor Agreement");
        view.getFrame().setVisible(true);
    }

    private void loadBalances(String startDate, String endDate, String eventStatus) {
    	
    	if(eventStatus == "Sponsor Agreement")
    	{
    		List<AgreementIncomeExpenseDTO> balances = model.getAgreements(startDate, endDate);
            TableModel tableModel = SwingUtil.getTableModelFromPojos(balances,
                new String[]{"sponsorship_id","balance_id","event_id","sponsorship_name","sponsorship_agreementDate","amount","balance_status","event_name", "event_date", "event_endDate", "event_status"});
            
            view.getLstActivities().setModel(tableModel);
            view.getLstActivities().getColumnModel().getColumn(0).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(0).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(0).setWidth(0);
            
            view.getLstActivities().getColumnModel().getColumn(1).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(1).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(1).setWidth(0);
            
            view.getLstActivities().getColumnModel().getColumn(2).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(2).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(2).setWidth(0);

            SwingUtil.autoAdjustColumns(view.getLstActivities());
            
    	}else if(eventStatus == "Income"){
    		List<AgreementIncomeExpenseDTO> balances = model.getIncome(startDate, endDate);
            TableModel tableModel = SwingUtil.getTableModelFromPojos(balances,
                new String[]{"sponsorship_id","balance_id","event_id","concept","amount","balance_status","event_name", "event_date", "event_endDate", "event_status"});
            
            view.getLstActivities().setModel(tableModel);
            view.getLstActivities().getColumnModel().getColumn(0).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(0).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(0).setWidth(0);
            
            view.getLstActivities().getColumnModel().getColumn(1).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(1).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(1).setWidth(0);
            
            view.getLstActivities().getColumnModel().getColumn(2).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(2).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(2).setWidth(0);

            SwingUtil.autoAdjustColumns(view.getLstActivities());
            
    	}else if(eventStatus == "Expense"){
    		List<AgreementIncomeExpenseDTO> balances = model.getExpenses(startDate, endDate);
            TableModel tableModel = SwingUtil.getTableModelFromPojos(balances,
                new String[]{"sponsorship_id","balance_id","event_id","concept","amount","balance_status","event_name", "event_date", "event_endDate", "event_status"});
            
            view.getLstActivities().setModel(tableModel);
            view.getLstActivities().getColumnModel().getColumn(0).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(0).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(0).setWidth(0);
            
            view.getLstActivities().getColumnModel().getColumn(1).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(1).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(1).setWidth(0);
            
            view.getLstActivities().getColumnModel().getColumn(2).setMinWidth(0);
            view.getLstActivities().getColumnModel().getColumn(2).setMaxWidth(0);
            view.getLstActivities().getColumnModel().getColumn(2).setWidth(0);

            SwingUtil.autoAdjustColumns(view.getLstActivities());
    	}
    	
        
    }
    
    private void loadMovements(int balanceId)
    {
    	List<MovementDTO> movements = model.getMovements(balanceId);
        TableModel tableModel = SwingUtil.getTableModelFromPojos(movements,
            new String[]{"movement_id","movement_amount","movement_date"});
        
        view.getLstMovements().setModel(tableModel);
        view.getLstMovements().getColumnModel().getColumn(0).setMinWidth(0);
        view.getLstMovements().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getLstMovements().getColumnModel().getColumn(0).setWidth(0);

        SwingUtil.autoAdjustColumns(view.getLstMovements());
    }
    
    

    private void selectBalanceFromTable(int rowIndex) {
        JTable table = view.getLstActivities();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String type = (String) view.getType().getSelectedItem();
        if(type == "Sponsor Agreement")
        {
	        try {
	        	
	            selected = new AgreementIncomeExpenseDTO(
	                Integer.parseInt(table.getValueAt(rowIndex, 0).toString()),  	// sponsorshipId
	                Integer.parseInt(table.getValueAt(rowIndex, 1).toString()),  	// balanceId
	                Integer.parseInt(table.getValueAt(rowIndex, 2).toString()),  	// eventId
	                table.getValueAt(rowIndex, 3).toString(),                   	// sponsorshipName
	                table.getValueAt(rowIndex, 4).toString(), 						// agreementDate
	                Double.parseDouble(table.getValueAt(rowIndex, 5).toString()),	// amount
	                table.getValueAt(rowIndex, 6).toString(),                   	// balanceStatus
	                table.getValueAt(rowIndex, 7).toString(),                   	// eventName
	                table.getValueAt(rowIndex, 8).toString(),                   	// eventDate
	                table.getValueAt(rowIndex, 9).toString(),                   	// eventEndDate
	                table.getValueAt(rowIndex, 10).toString()                   	// eventStatus
	            );
	
	            view.setName(selected.getSponsorship_name());
	            view.setDate(selected.getSponsorship_agreementDate());
	            view.setAmount((double) selected.getAmount());
	            view.setEvent(selected.getEvent_name());
	            loadMovements(selected.getBalance_id());
	            
	        } catch (Exception e) {
	            System.out.println("Error selecting the balance: " + e.getMessage());
	        }
        }
        else if (type == "Income" || type == "Expense")
        {
        	try {
        		selected = new AgreementIncomeExpenseDTO(        				
	                Integer.parseInt(table.getValueAt(rowIndex, 0).toString()),  	// sponsorshipId
	                Integer.parseInt(table.getValueAt(rowIndex, 1).toString()),  	// balanceId
	                Integer.parseInt(table.getValueAt(rowIndex, 2).toString()),  	// eventId
	                table.getValueAt(rowIndex, 3).toString(),                   	// concept
	                Double.parseDouble(table.getValueAt(rowIndex, 4).toString()),	// amount
	                table.getValueAt(rowIndex, 5).toString(),                   	// balanceStatus
	                table.getValueAt(rowIndex, 6).toString(),                   	// eventName
	                table.getValueAt(rowIndex, 7).toString(),                   	// eventDate
	                table.getValueAt(rowIndex, 8).toString(),                   	// eventEndDate
	                table.getValueAt(rowIndex, 9).toString()                   		// eventStatus
	            );
	
	            view.setConcept(selected.getSponsorship_name());
	            view.setDate(selected.getEvent_date());
	            view.setAmount((double) selected.getAmount());
	            view.setEvent(selected.getEvent_name());
	            loadMovements(selected.getBalance_id());
	            
	        } catch (Exception e) {
	            System.out.println("Error selecting the balance: " + e.getMessage());
	        }
        }
    }
    
    private void CancelBalance()
    {
    	String type = (String) view.getType().getSelectedItem();
    	String text;
    	if(type == "Sponsor Agreement")
    		text = "a "+ type;
    	else
    		text = "an " + type;
    	
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "You must select "+ text+" to be able to make a registration.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
        	
        	int option = JOptionPane.showConfirmDialog(view.getFrame(),
        			"You are going to cancel the "+type+" selected, are you sure?",
                    "Amount Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                 if (option != JOptionPane.YES_OPTION) {
                     return; // Usuario eligió "Cancel"
                 }
                 
	         model.CancelBalance(selected.getBalance_id());                                
	         JOptionPane.showMessageDialog(view.getFrame(), "The "+type+" has been cancelled and the compensation movement had been registered correctly.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
	
	         // Refrescar la tabla y limpiar selección
	         loadBalances(view.getStartDate(), view.getEndDate(),(String)view.getType().getSelectedItem());
	         clearSelection();    
        }catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error canceling the "+type+": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void RegisterMovement() {
    	String type = (String) view.getType().getSelectedItem();
    	String text;
    	if(type == "Sponsor Agreement")
    		text = "a "+ type;
    	else
    		text = "an " + type;
    	
        if (selected == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "You must select "+ text+" to be able to make a registration.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Obtener la cantidad ingresada
            String amountStr = view.getAmount();
            if (amountStr.isEmpty()) {
                JOptionPane.showMessageDialog(view.getFrame(), "You must input a valid amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }            
            double amount = Double.parseDouble(amountStr);
            
            String paymentDateStr = view.getDate();
            if (paymentDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(view.getFrame(), "You must insert a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar que la fecha del pago no sea anterior a la fecha de la factura
                    
            if (!Util.isValidISODate(paymentDateStr)) {
            	JOptionPane.showMessageDialog(view.getFrame(), "INCORRECT FORMAT: YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            
            }
            
            // Verificar si la cantidad pagada es diferente de la esperada
            if (amount == 0)
            {
            	JOptionPane.showMessageDialog(view.getFrame(), "The amount paid cannot be 0", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            else{
                int option = JOptionPane.showConfirmDialog(view.getFrame(),
                        "You are going to make a compensation movement of "+amount+" to the "+type+" selected, are you sure?",
                        "Amount Warning",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                    if (option != JOptionPane.YES_OPTION) {
                        return; // Usuario eligió "Cancel"
                    }      
            } 
                                             
            
                        
            // Registrar el pago en la BD
            model.RegisterPayment(amount, paymentDateStr, selected.getBalance_id());    
            
            model.CancelBalance(selected.getBalance_id());                                
            JOptionPane.showMessageDialog(view.getFrame(), "The "+type+" has been cancelled and the compensation movement had been registered correctly.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Refrescar la tabla y limpiar selección
            loadBalances(view.getStartDate(), view.getEndDate(),(String)view.getType().getSelectedItem());
            clearSelection();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Input a numeric amount.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error registering the payment: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void clearSelection() {
        view.getLstActivities().clearSelection();
        view.getLstMovements().clearSelection();
        selected = null;
        view.setName(null);
        view.setDate(null);
        view.setAmount(0);
        view.setEvent(null);;
    }
    
    private void ApplyFilter() {
        if ((view.getStartDate() == "") || (view.getEndDate() == "")) {
     	   
            JOptionPane.showMessageDialog(view.getFrame(), "You must select a start date and an end date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {
 	       String startDate = view.getStartDate();
 	       String endDate = view.getEndDate();
 	       String type = (String) view.getType().getSelectedItem();
 	       this.loadBalances(startDate, endDate, type);
        }
     }
}