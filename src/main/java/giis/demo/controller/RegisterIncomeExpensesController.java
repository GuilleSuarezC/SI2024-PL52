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
import giis.demo.view.RegisterIncomeExpensesView;

public class RegisterIncomeExpensesController {

	private RegisterIncomeExpensesModel model;
    private RegisterIncomeExpensesView view;

    public void US_Register_Income_Expense_Controller(RegisterIncomeExpensesModel model, RegisterIncomeExpensesView view) {
        this.model = model;
        this.view = view;

        this.view.getSubmitButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	registerMovement();
            }
        });
    }

    private void registerMovement() {
        model.setConcept(view.getConcept());
        model.setDescription(view.getDescription());
        model.setQestimated(view.getQEstimated());
        model.setQpaid(view.getQPaid());
        model.setDateOfPaid(convertirFecha(view.getDateOfPaid()));
        model.save();
        
        JOptionPane.showMessageDialog(null, "Movement Registered sucessfully", "Success", JOptionPane.INFORMATION_MESSAGE);
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
