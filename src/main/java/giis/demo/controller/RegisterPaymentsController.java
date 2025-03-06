package giis.demo.controller;

import giis.demo.model.RegisterPaymentsDTO;
import giis.demo.model.RegisterPaymentsModel;
import giis.demo.view.RegisterPaymentsView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class RegisterPaymentsController {
    private RegisterPaymentsView view;
    private RegisterPaymentsModel model;

    public RegisterPaymentsController(RegisterPaymentsView view, RegisterPaymentsModel model) {
        this.view = view;
        this.model = model;
        initController();
        model.getPendingPayments();
    }

    public void initController() {
        view.getBtnSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registrarPago();
            }
        });
        view.getFrame().setVisible(true);
    }
    
    

    private void registrarPago() {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            double amount = Double.parseDouble(view.getTfAmountPaid());
            Date date = sdf.parse(view.getTfPaymentDate());
            int invoiceId = 1; // TODO: Get invoice ID from selection

            RegisterPaymentsDTO payment = new RegisterPaymentsDTO(invoiceId, amount, date);
            model.updatePayment(payment.getSponsorshipId(), payment.getAmountPaid(), payment.getPaymentDate());

            JOptionPane.showMessageDialog(null, "Payment registered successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid amount format.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
