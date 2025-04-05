package giis.demo.controller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import giis.demo.model.*;
import giis.demo.util.SwingMain;
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
        String date= SwingMain.getFechaISO();
        int year = Integer.parseInt(date.substring(0, 4));  
        model = new InvoiceSendModel(date);
    }

    private void loadSponsors() {
        String selectedEvent = view.getSelectedEvent();
        if (selectedEvent == null) {
            JOptionPane.showMessageDialog(null, "Please select an event", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Object[]> sponsors = model.getSponsorsByEvent(selectedEvent);
        System.out.println("Sponsors for event " + selectedEvent + ": " + sponsors); 

        Object[][] sponsorData = new Object[sponsors.size()][5];  

        for (int i = 0; i < sponsors.size(); i++) {
            Object[] sponsor = sponsors.get(i);
            sponsorData[i] = new Object[]{
                sponsor[0], 
                sponsor[1], 
                sponsor[2], 
                sponsor[3], 
                sponsor[4]  
            };
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
        String address = view.getSelectedAddress(); 
        String email = view.getSelectedEmail();
        String event = view.getSelectedEvent();
        Double amount = view.getAmount();
        String ad= view.getSelectedAddress();
        boolean advance = view.isAdvanceInvoiceSelected();
        

        boolean success = model.generateInvoice(sponsorName, fiscalNumber, address, email, event, advance);
        if (success) {
            JOptionPane.showMessageDialog(null, "Invoice sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            System.out.println("=====================================");
            System.out.println("Invoice Simulator");
          //  System.out.println(" Date: " + view.getInvoiceDate());
            System.out.println(" Sponsor: " + sponsorName);
            System.out.println(" CIF/NIF: " + fiscalNumber);
            System.out.println(" Event: " + event);
            System.out.println(" Sending to: " + email);
            System.out.println(" Address: " + ad);
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
