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
import java.time.format.DateTimeFormatter;
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

    public ReportIncomeExpensesController(ReportIncomeExpensesView v, ReportIncomeExpensesModel m, String fecha) {
        this.view = v;
        this.model = m;
        ActivitiesList = new ArrayList<ReportDTO>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate storedDate = LocalDate.parse(fecha, formatter);
        
        //Conseguir el primer dia del año y el del año siguiente
        LocalDate firstDayOfYear = storedDate.withDayOfYear(1);
        LocalDate firstDayOfNextYear = firstDayOfYear.plusYears(1);
        
        // Convertir LocalDate a String
        String startDateStr = firstDayOfYear.format(formatter);
        String endDateStr = firstDayOfNextYear.format(formatter);
        
        //loadActivities(startDate,endDate, "All");
        view.setStartDate(startDateStr);
        view.setEndDate(endDateStr);
        
        this.initView(startDateStr, endDateStr);
       
    }

    public void initController(SwingMain s) {
    	this.main=s;
    	view.getBtnApplyFilter().addActionListener(e -> SwingUtil.exceptionWrapper(() -> ApplyFilter()));
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
            new String[]{"event_id","event_name", "event_date", "event_endDate", "event_status", "total_sponsorship_income","total_other_income", "sponsorship_income_paid", "other_income_paid", "total_expenses", "paid_expenses","estimated_balance", "paid_balance"});
        
        view.getLstActivities().setModel(tableModel);
        view.getLstActivities().getColumnModel().getColumn(0).setMinWidth(0);
        view.getLstActivities().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getLstActivities().getColumnModel().getColumn(0).setWidth(0);

        SwingUtil.autoAdjustColumns(view.getLstActivities());
    }
    
    private void ApplyFilter() {
       if ((view.getStartDate() == "") || (view.getEndDate() == "")) {
    	   
           JOptionPane.showMessageDialog(view.getFrame(), "You must select a start date and an end date.", "Error", JOptionPane.ERROR_MESSAGE);
           return;
       }
       else {
	       String startDate = view.getStartDate();
	       String endDate = view.getEndDate();
	       String status = (String) view.getStatus().getSelectedItem();
	       this.loadActivities(startDate, endDate, status);
       }
    }
    
}