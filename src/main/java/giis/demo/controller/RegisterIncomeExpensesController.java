package giis.demo.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.model.*;
import giis.demo.util.SwingUtil;
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
        view.addRegMovementListener(e -> registerMovement());
        view.getClearFieldsButtonM().addActionListener(e -> SwingUtil.exceptionWrapper(() -> clearSelection()));
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
        System.out.println(balanceStatus);
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
        String dateOfPaid = view.getDateOfPaidField();  
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

    @SuppressWarnings("unused")
	private void registerMovement()
    {
    	BalanceDTO balance = view.getSelectedBalanceDetails();
        if (balance == null) {
            view.showMessage("Please select a balance to register the movement.", "Selection Error");
            return;
        }
        String amountText = view.getAmountFieldM();
        String dateOfPaid = view.getDateM();
        
        int balanceId = balance.getBalanceId();
        double amount;
        
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException e) {
            view.showMessage("Amount must be a valid number.", "Input Error");
            return;
        }

        if ((dateOfPaid == null || dateOfPaid.isEmpty())) {
            view.showMessage("Please enter a valid date of payment.", "Input Error");
            return;
        }

        if (!Util.isValidISODate(dateOfPaid)) {
            view.showMessage("Invalid date format. Use yyyy-MM-dd.", "Date Error");
            return;
        }
        if (amount < 0) {
            int option = JOptionPane.showConfirmDialog(view.getFrame(),
                    "The amount paid is negative, so this payment is an expense",
                    "Amount Warning",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

                if (option != JOptionPane.YES_OPTION) {
                    return; 
                }
        }else if (amount < balance.getAmount()) {
            int option = JOptionPane.showConfirmDialog(view.getFrame(),
                "The amount paid is LESS than the expected amount.\nDo you want to continue?",
                "Amount Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (option != JOptionPane.YES_OPTION) {
                return; 
            }
        } else if (balance.getAmount() < amount) {
            int option = JOptionPane.showConfirmDialog(view.getFrame(),
                "The amount paid is MORE than the expected amount.\nDo you want to continue?",
                "Amount Warning",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

            if (option != JOptionPane.YES_OPTION) {
                return; 
            }
        } else if (amount == balance.getAmount()) {
        	model.updateBalanceM(balanceId);
        }
        
        model.addMovement(amount, dateOfPaid, balanceId);

        loadBalances();
        view.clearFields();
        if (amount < 0)
        {
        	view.showMessage("Expense registered successfully!", "Success");
        }else {
        	view.showMessage("Income registered successfully!", "Success");
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
    
    private void clearSelection() {
    	view.setAmountFieldM("");
    	view.setDateM("");
        
    }




}
