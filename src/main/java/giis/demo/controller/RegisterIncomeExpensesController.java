package giis.demo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import giis.demo.model.*;
import giis.demo.util.Util;
import giis.demo.view.RegIncomeExpensesView;

public class RegisterIncomeExpensesController {
    private RegisterIncomeExpensesModel model;
    private RegIncomeExpensesView view;
    private Date currentDate;

    public RegisterIncomeExpensesController(RegisterIncomeExpensesModel model, RegIncomeExpensesView view) {
        this.model = model;
        this.view = view;
        


        loadEvents();

        
        view.addLoadBalancesListener(e -> loadBalances());
        view.addAddBalanceListener(e -> addBalance());
        view.addSaveChangesListener(e -> saveChanges());  
    }


    private void loadEvents() {
        List<String> events = model.getEventNames();
        view.setEventOptions(events.toArray(new String[0]));
    }

    
    private void loadBalances() {
        String selectedEvent = view.getSelectedEvent();
        if (selectedEvent != null) {
            List<BalanceDTO> balances = model.getBalancesByEvent(selectedEvent);
            view.setBalanceData(balances);
        }
    }

    
    private void addBalance() {
        String concept = view.getConceptField();  
        String description = view.getDescriptionField();
        String amountText = view.getAmountField();
        String dateOfPaid = view.getDateOfPaidField();
        String balanceStatus = (String) view.getBalanceStatusComboBox().getSelectedItem();

        if (dateOfPaid != null && !dateOfPaid.isEmpty()) {
            if (!isValidDate(dateOfPaid)) {
                view.showMessage("Invalid date. Should be in format YYYY-MM-DD and can't be future.", "Date Error");
                return;
            }
        }	
        	

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            view.showMessage("Amount must be a valid number.", "Input Error");
            return;
        }

        String selectedEvent = view.getSelectedEvent();
        int eventId = model.getEventIdByName(selectedEvent);  

        
        model.addBalance(concept, eventId, amount, description, dateOfPaid, balanceStatus);
        
        
        loadBalances();

       
        view.clearFields();

        view.showMessage("Balance added successfully!", "Success");
    }



    
    private void saveChanges() {
        BalanceDTO balance = view.getSelectedBalanceDetails();  
        if (balance == null) return;  

        try {
            String concept = view.getConceptField();
            String description = view.getDescriptionField();
            double amount = Double.parseDouble(view.getAmountField());
            String dateOfPaid = view.getDateOfPaidField();
            String balanceStatus = (String) view.getBalanceStatusComboBox().getSelectedItem();
            
            if (dateOfPaid != null && !dateOfPaid.isEmpty()) {
                if (!isValidDate(dateOfPaid)) {
                    view.showMessage("Invalid date. Should be in format YYYY-MM-DD and can't be future.", "Date Error");
                    return;
                }
            }

            
            balance.setConcept(concept);
            balance.setDescription(description);
            balance.setAmount(amount);
            balance.setDateOfPaid(dateOfPaid);
            balance.setBalanceStatus(balanceStatus);

            
            boolean success = model.updateBalance(balance);
            if (success) {
                view.updateBalanceInTable(balance);
                view.showMessage("Balance updated successfully!", "Success");
            } else {
                view.showMessage("Error updating balance. Check database connection.", "Error");
            }

        } catch (NumberFormatException e) {
            view.showMessage("Amount must be a valid number.", "Input Error");
        } catch (Exception e) {
            e.printStackTrace();
            view.showMessage("Unexpected error: " + e.getMessage(), "Error");
        }
    }
    
    
    private boolean isValidDate(String date) {
        if (!Util.isValidISODate(date)) {
            return false;  
        }

        Date enteredDate = Util.isoStringToDate(date);
        Date currentDate = new Date();  

        
        if (enteredDate.after(currentDate)) {
            return false;
        }

        return true;  
    }




}
