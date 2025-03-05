package giis.demo.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import giis.demo.model.RegisterIncomeExpensesModel;
import giis.demo.util.Database;
import giis.demo.view.RegIncomeExpensesView;

public class RegisterIncomeExpensesController {
	private RegisterIncomeExpensesModel model;
    private RegIncomeExpensesView view;

    public RegisterIncomeExpensesController(RegisterIncomeExpensesModel model, RegIncomeExpensesView view) {
        this.model = model;
        this.view = view;

        
        Object[] events = model.getEventNames();
        view.setEventOptions(events);

        this.view.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerMovement();
            }
        });
    }

    private void registerMovement() {
        String source = view.getSource();
        String description = view.getDescription();
        double amount = view.getAmount();
        String dateOfPaid = view.getDateOfPaid();
        
        String selectedEvent = (String) view.getSelectedEvent();
        int eventId = model.getEventIdByName(selectedEvent);
        
        model.save(source, eventId, amount, description, dateOfPaid);
        
        JOptionPane.showMessageDialog(null, "Movement registered sucessfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private Date convertirFecha(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
	}