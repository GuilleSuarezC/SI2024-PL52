package giis.demo.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import giis.demo.model.*;
import giis.demo.view.*;


public class InvoiceSendController {
	private InvoiceSendModel model;
    private InvoiceSendView view;

    public InvoiceSendController(InvoiceSendModel model, InvoiceSendView view) {
        this.model = model;
        this.view = view;

        
        List<String> eventNames = model.getEventNames();
        view.setEventOptions(eventNames.toArray(new String[0]));

        
        view.addLoadSponsorsListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSponsors();
            }
        });

        
        view.addGenerateButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateAndSendInvoice();
            }
        });
    }

    private void loadSponsors() {
        String selectedEvent = view.getSelectedEvent();
        if (selectedEvent == null) {
            JOptionPane.showMessageDialog(null, "Please select an event", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Object[]> sponsors = model.getSponsorsByEvent(selectedEvent);
        System.out.println("Sponsors for event " + selectedEvent + ": " + sponsors); 

        Object[][] sponsorData = new Object[sponsors.size()][4];  

        for (int i = 0; i < sponsors.size(); i++) {
            Object[] sponsor = sponsors.get(i);
            String sponsorName = (String) sponsor[0];  
            String email = (String) sponsor[1];
            String fiscalNumber = (String) sponsor[2];
            Double amount = (Double) sponsor[3];
            
            sponsorData[i] = new Object[] { sponsorName, email, fiscalNumber, amount };
        }

        view.setSponsorData(sponsorData);  
    }

    
    private void generateAndSendInvoice() {
        int selectedRow = view.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a sponsor", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sponsorName = view.getSelectedSponsor();
        String fiscalNumber = view.getSelectedFiscalNumber();
        String email = view.getSelectedEmail();
        String event = view.getSelectedEvent();
        Double amount = view.getAmount();
        

        boolean success = model.generateInvoice(sponsorName, fiscalNumber, email, event);
        if (success) {
            JOptionPane.showMessageDialog(null, "Invoice sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("=====================================");
            System.out.println("Invoice Simulator");
          //  System.out.println(" Date: " + view.getInvoiceDate());
            System.out.println(" Sponsor: " + sponsorName);
            System.out.println(" CIF/NIF: " + fiscalNumber);
            System.out.println(" Event: " + event);
            System.out.println(" Sending to: " + email);
            System.out.println(" Amount to pay: " + amount);
            System.out.println(" Invoice sent sucessfully ");
            System.out.println("=====================================");
        } else {
            JOptionPane.showMessageDialog(null, "Error sending invoice", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**private Date convertirFecha(String fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }**/
}
