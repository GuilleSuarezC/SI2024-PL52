package giis.demo.controller;

import giis.demo.model.ReportDTO;
import giis.demo.model.ReportIncomeExpensesModel;
import giis.demo.util.SwingMain;
import giis.demo.util.SwingUtil;
import giis.demo.util.Util;
import giis.demo.view.ReportIncomeExpensesView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class ReportIncomeExpensesController {
    
    private ReportIncomeExpensesView view;
    private ReportIncomeExpensesModel model;
    private List<ReportDTO> ActivitiesList;
    private SwingMain main;

    public ReportIncomeExpensesController(ReportIncomeExpensesView v, ReportIncomeExpensesModel m) {
        this.view = v;
        this.model = m;
        ActivitiesList = new ArrayList<ReportDTO>();
        Calendar calendar = Calendar.getInstance();
        String startDateStr = "2025-01-01";
        Date startDate = Util.isoStringToDate(startDateStr);//calendar.getTime(); // Fecha actual
        String endDateStr = "2026-01-01";
        // Sumar un año
        calendar.add(Calendar.YEAR, 1);
        Date endDate = Util.isoStringToDate(endDateStr);//calendar.getTime(); // Fecha dentro de un año
        //loadActivities(startDate,endDate, "All");
        this.initView(startDateStr, endDateStr);
       
    }

    public void initController(SwingMain s) {
    	this.main=s;
    	view.getLstActivities().getSelectionModel().addListSelectionListener(e -> 
	        SwingUtil.exceptionWrapper(() -> {
	            if (!e.getValueIsAdjusting()) {
	                int selectedRow = view.getLstActivities().getSelectedRow();
	                if (selectedRow != -1) {
	                    //selectPaymentFromTable(selectedRow);
	                }
	            }
	        })
	    );
    }
    
    public void initView(String startDate, String endDate) {
        this.loadActivities(startDate, endDate, "All");
        view.getFrame().setVisible(true);
    }

    private void loadActivities(String startDate, String endDate, String eventStatus) {
        List<ReportDTO> activities = model.getActivities(startDate, endDate, eventStatus);
        TableModel tableModel = SwingUtil.getTableModelFromPojos(activities,
            new String[]{"event_id","event_name", "event_date", "event_endDate", "event_status", "total_income", "paid_income", "total_expenses", "paid_expenses", "paid_balance"});
        
        view.getLstActivities().setModel(tableModel);
        view.getLstActivities().getColumnModel().getColumn(0).setMinWidth(0);
        view.getLstActivities().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getLstActivities().getColumnModel().getColumn(0).setWidth(0);

        SwingUtil.autoAdjustColumns(view.getLstActivities());
    }
/*
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
    /*
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
            }

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
    }*/



    private void clearSelection() {
        view.getLstActivities().clearSelection();
    } 
}