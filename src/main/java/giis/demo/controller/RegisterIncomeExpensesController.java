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

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            view.showMessage("Amount must be a valid number.", "Input Error");
            return;
        }

        if ("Paid".equals(balanceStatus) && (dateOfPaid == null || dateOfPaid.isEmpty())) {
            view.showMessage("Please enter a valid date of payment.", "Input Error");
            return;
        }

        if ("Paid".equals(balanceStatus) && !Util.isValidISODate(dateOfPaid)) {
            view.showMessage("Invalid date format. Use yyyy-MM-dd.", "Date Error");
            return;
        }

        String selectedEvent = view.getSelectedEvent();
        int eventId = model.getEventIdByName(selectedEvent);  

        // Pasar la fecha de pago al modelo:
        model.addBalance(concept, eventId, amount, description, balanceStatus, dateOfPaid);

        loadBalances();
        view.clearFields();
        view.showMessage("Other income/expense added successfully!", "Success");
    }




    
    private void saveChanges() {
    	BalanceDTO balance = view.getSelectedBalanceDetails();
        if (balance == null) {
            view.showMessage("Please select a balance to update.", "Selection Error");
            return;
        }

        String concept = view.getConceptField();  
        String description = view.getDescriptionField();
        String amountText = view.getAmountField();
        String dateOfPaid = view.getDateOfPaidField();  // ⬅️ Usamos esta fecha
        String balanceStatus = (String) view.getBalanceStatusComboBox().getSelectedItem();
        String selectedEvent = view.getSelectedEvent();

        int eventId = model.getEventIdByName(selectedEvent);
        double amount;

        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            view.showMessage("Amount must be a valid number.", "Input Error");
            return;
        }

        if ("Paid".equals(balanceStatus) && (dateOfPaid == null || dateOfPaid.isEmpty())) {
            view.showMessage("Please enter a valid date of payment.", "Input Error");
            return;
        }

        if (!Util.isValidISODate(dateOfPaid)) {
            view.showMessage("Invalid date format. Use yyyy-MM-dd.", "Date Error");
            return;
        }

        balance.setConcept(concept);
        balance.setEventId(eventId);
        balance.setAmount(amount);
        balance.setDescription(description);
        balance.setBalanceStatus(balanceStatus);

        // ⬇️ Pasamos la fecha de pago al modelo
        model.updateBalance(balance, dateOfPaid);

        loadBalances();
        view.clearFields();
        view.showMessage("Income/Expense updated successfully!", "Success");
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
