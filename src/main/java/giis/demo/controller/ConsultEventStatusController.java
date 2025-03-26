package giis.demo.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import giis.demo.model.*;
import giis.demo.model.EventDTO;
import giis.demo.util.SwingUtil;
import giis.demo.util.SwingMain;
import giis.demo.view.ConsultEventStatusView;

public class ConsultEventStatusController {
    private ConsultEventStatusModel model;
    private ConsultEventStatusView view;
    private List<ConsultEventDTO> eventList;
    private List<SponsorshipInfoDTO> sponsorList;
    private List<PaymentDTO> paymentList;
    private List<IncomeEntryDTO> balanceList;
    private SwingMain loquesea;

    public ConsultEventStatusController(ConsultEventStatusModel model, ConsultEventStatusView view) {
    	this.eventList = new ArrayList<ConsultEventDTO>();
    	this.sponsorList = new ArrayList<SponsorshipInfoDTO>();
    	this.paymentList = new ArrayList<PaymentDTO>();
    	this.balanceList = new ArrayList<IncomeEntryDTO>();
        this.model = model;
        this.view = view;
        this.initView();
    }

    /**
     * Inicializa la vista y carga los eventos en el comboBox.
     */
    public void initView() {
    	this.loadEvents();
        view.getFrame().setVisible(true);
    }

    /**
     * Inicializa los manejadores de eventos.
     */
    public void initController(SwingMain sm) {
    	loquesea = sm;
	    JTable jtable = view.getTblEvents();
	    jtable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

	    // Agregar un listener de selección a la tabla de eventos
	    jtable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
	        @Override
	        public void valueChanged(ListSelectionEvent e) {
	            if (!e.getValueIsAdjusting()) { // Para evitar llamadas dobles
	            	clearTables();
	                
	            	int selectedRow = jtable.getSelectedRow();
	                
	                if (selectedRow != -1) { // Si hay una fila seleccionada
	                    if (eventList == null || eventList.isEmpty()) {
	                    	
	                        System.err.println("Error: eventList está vacía o no ha sido inicializada.");
	                        return;
	                    }
	                    int eventId = eventList.get(selectedRow).getEvent_id(); // Obtener el ID del evento
	                    showSponsors(eventId); // Llamar al método para mostrar patrocinios
	                    showIncomeEntries(eventId);
	                    
	                    // Calcular y actualizar los totales de Income y Expenses
	                    calculateAndDisplayTotals(eventId);
	                }
	            }
	        }
	    });  
	    
	    // Agregar un ActionListener al botón Exit
	    view.getBtnExit().addActionListener(e -> {
	        // Cerrar la ventana actual
	        view.getFrame().dispose();
	    });
    }

    private void clearTables() {
        // Limpiar la tabla de sponsorships
        view.getTblSponsorships().setModel(new DefaultTableModel());

        // Limpiar la tabla de income
        view.getTblIncome().setModel(new DefaultTableModel());

        // Limpiar la tabla de expenses
        view.getTblExpenses().setModel(new DefaultTableModel());

        // Limpiar los totales
        view.getLblIncomeSummary().setText("Income: 0.00 €");
        view.getLblIncomePaid().setText("Paid: 0.00 €");
        view.getLblIncomeEstimated().setText("Estimated: 0.00 €");
        view.getLblExpensesSummary().setText("Expenses: 0.00 €");
        view.getLblExpensesPaid().setText("Paid: 0.00 €");
        view.getLblExpensesEstimated().setText("Estimated: 0.00 €");
        
        // Limpiar las listas de datos
        sponsorList.clear();
        paymentList.clear();
        balanceList.clear();

        // Opcional: Ajustar el ancho de las columnas después de limpiar
        SwingUtil.autoAdjustColumns(view.getTblSponsorships());
        SwingUtil.autoAdjustColumns(view.getTblIncome());
        SwingUtil.autoAdjustColumns(view.getTblExpenses());
    }
    
    /**
     * Carga la lista de eventos en la lista.
     */
    private void loadEvents() {
        List<ConsultEventDTO> events = model.getEvents();
        for(ConsultEventDTO event : events) {
        	this.eventList.add(event);
        }
        // Incluir event_id en el TableModel
        TableModel tableModel = SwingUtil.getTableModelFromPojos(eventList, 
            new String[] {"event_id", "event_name", "event_edition", "event_date", "event_endDate", "event_status"});
        view.getTblEvents().setModel(tableModel);

        // Ocultar la columna event_id
        view.getTblEvents().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTblEvents().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTblEvents().getColumnModel().getColumn(0).setWidth(0);

        // Ajustar el ancho de las columnas
        SwingUtil.autoAdjustColumns(view.getTblEvents());
    }   
    
    public void showSponsors(int eventId) {
    	if(eventId < 0) return;
        // Obtener la lista de patrocinios asociados al evento
        List<SponsorshipInfoDTO> sponsors = model.getSponsorshipsByEventId(eventId);
        for (SponsorshipInfoDTO s : sponsors) {
        	if(s.getpayment_status() == null) {
        		s.setpayment_status("Estimated");
        	}
        	this.sponsorList.add(s);
        }
        TableModel tableModel = SwingUtil.getTableModelFromPojos(sponsors,new String[] {"sponsorship_name", "sponsorship_agreementDate", "payment_status", "event_fee"});
        view.getTblSponsorships().setModel(tableModel);
        SwingUtil.autoAdjustColumns(view.getTblSponsorships());
    }
    
    private void calculateAndDisplayTotals(int eventId) {
        // Obtener datos
        List<SponsorshipInfoDTO> sponsors = model.getSponsorshipsByEventId(eventId);
        List<OtherBalanceDTO> otherBalances = model.getOtherBalances(eventId);
        
        // Inicializar totales
        double incomePaid = 0.0, incomeEstimated = 0.0;
        double expensePaid = 0.0, expenseEstimated = 0.0;

        // Procesar Sponsorships (solo ingresos)
        for (SponsorshipInfoDTO sponsor : sponsors) {
            if ("Paid".equals(sponsor.getpayment_status())) {
                incomePaid += sponsor.getevent_fee();
            } else { // "Unpaid" se considera "Estimated"
                incomeEstimated += sponsor.getevent_fee();
            }
        }

        // Procesar Other Balances (ingresos y gastos)
        for (OtherBalanceDTO balance : otherBalances) {
            if (balance.getAmount() > 0) { // Ingreso
                if ("Paid".equals(balance.getPaymentStatus())) {
                    incomePaid += balance.getAmount();
                } else {
                    incomeEstimated += balance.getAmount();
                }
            } else { // Gasto
                if ("Paid".equals(balance.getPaymentStatus())) {
                    expensePaid += balance.getAmount(); // Monto negativo
                } else {
                    expenseEstimated += balance.getAmount(); // Monto negativo
                }
            }
        }

        // Calcular totales
        double totalIncome = incomePaid + incomeEstimated;
        double totalExpenses = expensePaid + expenseEstimated;

        // Actualizar la vista (usar Math.abs() para gastos)
        view.getLblIncomeSummary().setText(String.format("Income: %.2f €", totalIncome));
        view.getLblIncomePaid().setText(String.format("Paid: %.2f €", incomePaid));
        view.getLblIncomeEstimated().setText(String.format("Estimated: %.2f €", incomeEstimated));
        
        view.getLblExpensesSummary().setText(String.format("Expenses: %.2f €", Math.abs(totalExpenses)));
        view.getLblExpensesPaid().setText(String.format("Paid: %.2f €", Math.abs(expensePaid)));
        view.getLblExpensesEstimated().setText(String.format("Estimated: %.2f €", Math.abs(expenseEstimated)));
    }

    public void showIncomeEntries(int eventId) {
        if (eventId < 0) return;

        // Obtener balances de Otras Fuentes (NO Sponsorships)
        List<OtherBalanceDTO> otherBalances = model.getOtherBalances(eventId);

        // Separar en ingresos y gastos
        List<IncomeEntryDTO> incomeList = new ArrayList<>();
        List<IncomeEntryDTO> expenseList = new ArrayList<>();
        
        for (OtherBalanceDTO balance : otherBalances) {
            IncomeEntryDTO entry = new IncomeEntryDTO();
            entry.setName(balance.getConcept());
            entry.setAmount(balance.getAmount());
            entry.setStatus(balance.getPaymentStatus());
            
            if (balance.getAmount() > 0) {
                incomeList.add(entry);
            } else {
                expenseList.add(entry);
            }
        }

        // Asignar modelos a las tablas
        view.getTblIncome().setModel(SwingUtil.getTableModelFromPojos(incomeList, 
            new String[]{"name", "amount", "status"}));
        view.getTblExpenses().setModel(SwingUtil.getTableModelFromPojos(expenseList, 
            new String[]{"name", "amount", "status"}));

        SwingUtil.autoAdjustColumns(view.getTblIncome());
        SwingUtil.autoAdjustColumns(view.getTblExpenses());
    }
    
    private void calculateIncomeTotals(List<IncomeEntryDTO> incomeEntries) {
        double totalIncome = 0.0;
        double totalPaid = 0.0;
        double totalEstimated = 0.0;

        for (IncomeEntryDTO entry : incomeEntries) {
            if (entry.getAmount() > 0) { // Solo ingresos (montos positivos)
                totalIncome += entry.getAmount();
                if ("Paid".equals(entry.getStatus())) {
                    totalPaid += entry.getAmount();
                } else if ("Estimated".equals(entry.getStatus())) {
                    totalEstimated += entry.getAmount();
                }
            }
        }
        // Sumar los pagos de los sponsorships
        for (SponsorshipInfoDTO sponsor : sponsorList) {
            if (sponsor.getevent_fee() > 0) { // Solo montos positivos (ingresos)
                totalIncome += sponsor.getevent_fee();
                if ("Paid".equals(sponsor.getpayment_status())) {
                    totalPaid += sponsor.getevent_fee();
                } else if ("Estimated".equals(sponsor.getpayment_status())) {
                    totalEstimated += sponsor.getevent_fee();
                }
            }
        }
        // Actualizar las etiquetas de Income
        view.getLblIncomeSummary().setText(String.format("Income: %.2f €", totalIncome));
        view.getLblIncomePaid().setText(String.format("Paid: %.2f €", totalPaid));
        view.getLblIncomeEstimated().setText(String.format("Estimated: %.2f €", totalEstimated));
    }
    
    private void calculateExpensesTotals(List<IncomeEntryDTO> incomeEntries) {
        double totalExpenses = 0.0;
        double totalPaid = 0.0;
        double totalEstimated = 0.0;

        for (IncomeEntryDTO entry : incomeEntries) {
            if (entry.getAmount() < 0) { // Solo gastos (montos negativos)
                totalExpenses += entry.getAmount();
                if ("Paid".equals(entry.getStatus())) {
                    totalPaid += entry.getAmount();
                } else if ("Estimated".equals(entry.getStatus())) {
                    totalEstimated += entry.getAmount();
                }
            }
        }

        // Actualizar las etiquetas de Expenses
        view.getLblExpensesSummary().setText(String.format("Expenses: %.2f €", totalExpenses));
        view.getLblExpensesPaid().setText(String.format("Paid: %.2f €", totalPaid));
        view.getLblExpensesEstimated().setText(String.format("Estimated: %.2f €", totalEstimated));
    }
}

