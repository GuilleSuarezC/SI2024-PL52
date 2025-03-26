package giis.demo.controller;

import giis.demo.model.UpdatePaymentDTO;
import giis.demo.util.SwingMain;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import giis.demo.model.PendingPaymentDTO;
import giis.demo.model.RegisterPaymentsModel;
import giis.demo.view.RegisterPaymentsView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

public class RegisterPaymentsController {
    
    private RegisterPaymentsView view;
    private RegisterPaymentsModel model;
    private List<PendingPaymentDTO> PendingPaymentsList;
    private PendingPaymentDTO selectedPayment;
    private SwingMain main;

    public RegisterPaymentsController(RegisterPaymentsView v, RegisterPaymentsModel m) {
        this.view = v;
        this.model = m;
        PendingPaymentsList = new ArrayList<PendingPaymentDTO>();
        this.initView();
        loadPendingPayments();
    }

    public void initController(SwingMain s) {
    	this.main=s;
    	view.getBtnRegistrar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> RegisterPayment()));
    	view.getLstPayments().getSelectionModel().addListSelectionListener(e -> 
	        SwingUtil.exceptionWrapper(() -> {
	            if (!e.getValueIsAdjusting()) {
	                int selectedRow = view.getLstPayments().getSelectedRow();
	                if (selectedRow != -1) {
	                    selectPaymentFromTable(selectedRow);
	                }
	            }
	        })
	    );
        view.getBtnCancelar().addActionListener(e -> SwingUtil.exceptionWrapper(() -> clearSelection()));
    }
    
    public void initView() {
        this.loadPendingPayments();
        view.getFrame().setVisible(true);
    }

    private void loadPendingPayments() {
        List<PendingPaymentDTO> payments = model.getPendingPayments();
        TableModel tableModel = SwingUtil.getTableModelFromPojos(payments,
            new String[]{"balance_id","sponsorship_name", "sponsorship_agreementDate", "amount", "event_name", "invoice_date", "invoice_id"});
        
        view.getLstPayments().setModel(tableModel);
        view.getLstPayments().getColumnModel().getColumn(0).setMinWidth(0);
        view.getLstPayments().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getLstPayments().getColumnModel().getColumn(0).setWidth(0);

        SwingUtil.autoAdjustColumns(view.getLstPayments());
    }

    private void selectPaymentFromTable(int rowIndex) {
        JTable table = view.getLstPayments();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
        	
            selectedPayment = new PendingPaymentDTO(
                Integer.parseInt(table.getValueAt(rowIndex, 0).toString()),  // sponsorshipId
                table.getValueAt(rowIndex, 1).toString(),                   // sponsorshipName
                table.getValueAt(rowIndex, 2).toString(), // agreementDate
                Double.parseDouble(table.getValueAt(rowIndex, 3).toString()), // amount
                table.getValueAt(rowIndex, 4).toString(),                   // eventName
                table.getValueAt(rowIndex, 5).toString(), // invoiceDate
                Integer.parseInt(table.getValueAt(rowIndex, 6).toString())  // invoiceId
            );

            view.setLblSponsorshipName(selectedPayment.getSponsorship_name());
            view.setLblAgreementDate(selectedPayment.getSponsorship_agreementDate());
            view.setLblAmount((int) selectedPayment.getAmount());
            view.setLblEventName(selectedPayment.getEvent_name());
            view.setLblInvoiceDate(selectedPayment.getInvoice_date());
            view.setLblInvoiceId(selectedPayment.getInvoice_id());
        } catch (Exception e) {
            System.out.println("Error al seleccionar el pago: " + e.getMessage());
        }
    }

/*
    private void RegisterPayment() {
        if (selectedPayment == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "Select a pending payment before register the payment.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String amountPaidStr = view.getAmountPaidField();
            if (amountPaidStr.isEmpty()) {
                JOptionPane.showMessageDialog(view.getFrame(), "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            double amountPaid = Double.parseDouble(amountPaidStr);
            
            String paymentDateStr = view.getPaymentDate();
            if (paymentDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(view.getFrame(), "Ingrese una fecha válida.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }       

            Date paymentDate = java.sql.Date.valueOf(paymentDateStr);

            model.RegisterPayment(amountPaid, paymentDate, selectedPayment.getSponsorship_id());

            JOptionPane.showMessageDialog(view.getFrame(), "Pago registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            
            loadPendingPayments();
            clearSelection();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Ingrese un monto numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error al registrar el pago: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }*/
    
    private void RegisterPayment() {
        if (selectedPayment == null) {
            JOptionPane.showMessageDialog(view.getFrame(), "Selects a pending payment before make a registration.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Obtener la cantidad ingresada
            String amountPaidStr = view.getAmountPaidField();
            if (amountPaidStr.isEmpty()) {
                JOptionPane.showMessageDialog(view.getFrame(), "Ingrese un monto válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double amountPaid = Double.parseDouble(amountPaidStr);
            double amount = selectedPayment.getAmount();
            if(amount != amountPaid) {
            	JOptionPane.showMessageDialog(view.getFrame(), "The amount Paid must be the same as the amount.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            //Validar que el amount pagado coincida con el balance en la BD
            /*double balanceAmount = model.getBalanceAmount(selectedPayment.getSponsorship_id());  // Método que obtiene el balance
            if (amountPaid != Math.abs(balanceAmount)) {
                JOptionPane.showMessageDialog(view.getFrame(), "El monto pagado debe ser igual al valor absoluto del balance: " + Math.abs(balanceAmount), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }*/

            // Obtener la fecha ingresada
            String paymentDateStr = view.getPaymentDate();
            if (paymentDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(view.getFrame(), "Insert a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Date paymentDate = Util.isoStringToDate(paymentDateStr);

            // Validar que la fecha del pago no sea anterior a la fecha de la factura
            
            String invoiceDateStr = selectedPayment.getInvoice_date();
            Date invoiceDate = Util.isoStringToDate(invoiceDateStr);
            if (!Util.isValidISODate(paymentDateStr)) {
            	JOptionPane.showMessageDialog(view.getFrame(), "INCORRECT FORMAT: YYYY-MM-DD", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            
            }
            if (paymentDate.before(invoiceDate)) {
                JOptionPane.showMessageDialog(view.getFrame(), "The payment date must be at the same day or after the invoice date: (" + invoiceDate + ").", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
                        
            // Registrar el pago en la BD
            model.RegisterPayment(amountPaid, paymentDate, selectedPayment.getBalance_id());            
            model.UpdateBalance(paymentDate, selectedPayment.getBalance_id());                                
            JOptionPane.showMessageDialog(view.getFrame(), "Pago registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Refrescar la tabla y limpiar selección
            loadPendingPayments();
            clearSelection();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Ingrese un monto numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view.getFrame(), "Error al registrar el pago: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    private void clearSelection() {
        view.getLstPayments().clearSelection();
        selectedPayment = null;
        view.setLblSponsorshipName("");
        view.setLblAgreementDate(null);
        view.setLblAmount(0);
        view.setLblInvoiceId(0);;
    } 
}