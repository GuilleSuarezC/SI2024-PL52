package giis.demo.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
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

    public ConsultEventStatusController(ConsultEventStatusModel model, ConsultEventStatusView view, SwingMain swingMain) {
        this.eventList = new ArrayList<ConsultEventDTO>();
        this.sponsorList = new ArrayList<SponsorshipInfoDTO>();
        this.paymentList = new ArrayList<PaymentDTO>();
        this.balanceList = new ArrayList<IncomeEntryDTO>();
        this.model = model;
        this.view = view;
        this.loquesea = swingMain;  // Set the SwingMain instance
        this.initView();
    }

    /**
     * Inicializa la vista y carga los eventos en el comboBox.
     */
    public void initView() {
        // Cargar eventos
	    loadEvents();
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
        view.getLblIncomeSummary().setText("Estimated: 0.00 €");
        view.getLblIncomePaid().setText("Paid: 0.00 €");
        view.getLblIncomeEstimated().setText("Estimated: 0.00 €");
        view.getLblExpensesSummary().setText("Paid: 0.00 €");
        //view.getLblExpensesPaid().setText("Paid: 0.00 €");
        //view.getLblExpensesEstimated().setText("Estimated: 0.00 €");
        
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
     * Carga la lista de eventos y actualiza su estado según la fecha actual.
     */
    private void loadEvents() {
        String currentDate = loquesea.getFechaISO(); // Obtener fecha actual del sistema

        List<ConsultEventDTO> events = model.getEvents();
        // Limpiar la lista de eventos previa si es necesario
        this.eventList.clear(); 

        for (ConsultEventDTO event : events) {
            // Determinar el estado basado en la fecha actual y las fechas de inicio y fin
            String status = determineEventStatus(currentDate, event.getEvent_date(), event.getevent_endDate());
            event.setEvent_status(status); // Actualizar el estado en el DTO
            this.eventList.add(event); // Agregar el evento con el nuevo estado a la lista
        }

        // Actualizar la tabla con los eventos
        TableModel tableModel = SwingUtil.getTableModelFromPojos(eventList, 
            new String[] {"event_id", "event_name", "event_edition", "event_date", "event_endDate", "event_status"});
        view.getTblEvents().setModel(tableModel);

        // Ocultar columna event_id y ajustar columnas
        view.getTblEvents().getColumnModel().getColumn(0).setMinWidth(0);
        view.getTblEvents().getColumnModel().getColumn(0).setMaxWidth(0);
        view.getTblEvents().getColumnModel().getColumn(0).setWidth(0);
        SwingUtil.autoAdjustColumns(view.getTblEvents());
    }

    /**
     * Determina el estado del evento comparando fechas.
     */
    private String determineEventStatus(String currentDate, String startDate, String endDate) {
        if (currentDate.compareTo(startDate) < 0) {
            return "Planned"; // Evento planeado
        } else if (currentDate.compareTo(endDate) > 0) {
            return "Completed"; // Evento completado
        } else {
            return "Ongoing"; // Evento en curso
        }
    }

    
    public void showSponsors(int eventId) {
        double total = 0;
        double paid = 0;
        if (eventId < 0) return;

        // Obtener Sponsors normales y LTAs
        List<SponsorshipInfoDTO> sponsorsNormal = model.getSponsorshipsByEventId(eventId);
        List<SponsorshipInfoDTO> ltaSponsors = model.getLTASponsorshipsByEventId(eventId);

        // Combinar ambas listas
        List<SponsorshipInfoDTO> allSponsors = new ArrayList<>();
        allSponsors.addAll(sponsorsNormal);
        allSponsors.addAll(ltaSponsors);

        // Calcular totales
        for (SponsorshipInfoDTO s : allSponsors) {
            if (s.getpayment_status() == null) {
                s.setpayment_status("Estimated");
            }
            sponsorList.add(s);
            total += s.getagreed_quantity(); // Sumar todos los event_fee (positivos)            
            paid += s.getAmount_paid();
            //if ("Paid".equals(s.getpayment_status())) {
            //    paid += s.getagreed_quantity();
            //}
        }

        // Actualizar la vista
        view.setLblSponsorshipsEstimated(total);
        view.setLblSponsorshipsPaid(paid);
        TableModel tableModel = SwingUtil.getTableModelFromPojos(allSponsors, 
            new String[] {"sponsorship_name", "sponsorship_agreementDate", "payment_status", "agreed_quantity", "amount_paid"});
        view.getTblSponsorships().setModel(tableModel);
        
        // Deshabilitar redimensionamiento de la columna sponsorship_name (columna 0)
        view.getTblSponsorships().getColumnModel().getColumn(0).setResizable(false);
        
        SwingUtil.autoAdjustColumns(view.getTblSponsorships());
    }
    
    private void calculateAndDisplayTotals(int eventId) {
        // Obtener todos los sponsorships (normales + LTAs)
        List<SponsorshipInfoDTO> sponsorsNormal = model.getSponsorshipsByEventId(eventId);
        List<SponsorshipInfoDTO> ltaSponsors = model.getLTASponsorshipsByEventId(eventId);
        List<OtherBalanceDTO> otherBalances = model.getOtherBalances(eventId);

        // Combinar sponsorships
        List<SponsorshipInfoDTO> allSponsors = new ArrayList<>();
        allSponsors.addAll(sponsorsNormal);
        allSponsors.addAll(ltaSponsors);

        // Inicializar acumuladores
        double totalPaidBalance = 0.0;
        double totalEstimatedBalance = 0.0;

        // Procesar Sponsorships (normales y LTAs)
        for (SponsorshipInfoDTO sponsor : allSponsors) {
            totalEstimatedBalance += sponsor.getagreed_quantity(); // Sumar al estimado
            if ("Paid".equals(sponsor.getpayment_status())) {
                totalPaidBalance += sponsor.getagreed_quantity(); // Sumar al pagado
            }
        }

        // Procesar Other Balances
        for (OtherBalanceDTO balance : otherBalances) {
            totalEstimatedBalance += balance.getAmount(); // Sumar al estimado
            totalPaidBalance += balance.getAmount_paid();
            //if ("Paid".equals(balance.getPaymentStatus())) {
            //    totalPaidBalance += balance.getAmount(); // Sumar al pagado
            //}
        }

        // Actualizar la vista
        view.getLblIncomeSummary().setText(String.format("Estimated Balance: %.2f €", totalEstimatedBalance));
        view.getLblExpensesSummary().setText(String.format("Paid Balance: %.2f €", totalPaidBalance));
    }

    public void showIncomeEntries(int eventId) {
        if (eventId < 0) return;
        double paidInc = 0;
        double totalInc = 0;
        double paidExp = 0;
        double totalExp = 0;
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
            entry.setAmount_paid(balance.getAmount_paid());
            
            if (balance.getAmount() > 0) {
                incomeList.add(entry);
                totalInc += balance.getAmount();
                paidInc += balance.getAmount_paid();                
                //if("Paid".equals(balance.getPaymentStatus()))
                	//paidInc += balance.getAmount();
                
            } else {
                expenseList.add(entry);
                totalExp += balance.getAmount();
                paidExp += balance.getAmount_paid();
                //paidAmountlst = model.getPaidAmount(balance.getBalance_id());
                //paidExp = balance.;
                //if("Paid".equals(balance.getPaymentStatus()))
                //	paidExp += balance.getAmount();
            }
        }

        // Asignar modelos a las tablas
        view.getTblIncome().setModel(SwingUtil.getTableModelFromPojos(incomeList, 
            new String[]{"name", "amount", "status", "amount_paid"}));
        view.getTblExpenses().setModel(SwingUtil.getTableModelFromPojos(expenseList, 
            new String[]{"name", "amount", "status", "amount_paid"}));
        view.setLblIncomeEstimated(totalInc);
        view.setLblIncomePaid(paidInc);
        view.setLblExpensesEstimated(totalExp);
        view.setLblExpensesPaid(paidExp);
        SwingUtil.autoAdjustColumns(view.getTblIncome());
        SwingUtil.autoAdjustColumns(view.getTblExpenses());
    }
    
   
    
//    private void calculateIncomeTotals(List<IncomeEntryDTO> incomeEntries) {
//        double totalIncome = 0.0;
//        double totalPaid = 0.0;
//        double totalEstimated = 0.0;
//
//        for (IncomeEntryDTO entry : incomeEntries) {
//            if (entry.getAmount() > 0) { // Solo ingresos (montos positivos)
//                totalIncome += entry.getAmount();
//                if ("Paid".equals(entry.getStatus())) {
//                    totalPaid += entry.getAmount();
//                } else if ("Estimated".equals(entry.getStatus())) {
//                    totalEstimated += entry.getAmount();
//                }
//            }
//        }
//        // Sumar los pagos de los sponsorships
//        for (SponsorshipInfoDTO sponsor : sponsorList) {
//            if (sponsor.getagreed_quantity() > 0) { // Solo montos positivos (ingresos)
//                totalIncome += sponsor.getagreed_quantity();
//                if ("Paid".equals(sponsor.getpayment_status())) {
//                    totalPaid += sponsor.getagreed_quantity();
//                } else if ("Estimated".equals(sponsor.getpayment_status())) {
//                    totalEstimated += sponsor.getagreed_quantity();
//                }
//            }
//        }
//        // Actualizar las etiquetas de Income
//        view.getLblIncomeSummary().setText(String.format("Income: %.2f €", totalIncome));
//        view.getLblIncomePaid().setText(String.format("Paid: %.2f €", totalPaid));
//        view.getLblIncomeEstimated().setText(String.format("Estimated: %.2f €", totalEstimated));
//    }
    
//    private void calculateExpensesTotals(List<IncomeEntryDTO> incomeEntries) {
//        double totalExpenses = 0.0;
//        double totalPaid = 0.0;
//        double totalEstimated = 0.0;
//
//        for (IncomeEntryDTO entry : incomeEntries) {
//            if (entry.getAmount() < 0) { // Solo gastos (montos negativos)
//                totalExpenses += entry.getAmount();
//                if ("Paid".equals(entry.getStatus())) {
//                    totalPaid += entry.getAmount();
//                } else if ("Estimated".equals(entry.getStatus())) {
//                    totalEstimated += entry.getAmount();
//                }
//            }
//        }
//
//        // Actualizar las etiquetas de Expenses
//        view.getLblExpensesSummary().setText(String.format("Expenses: %.2f €", totalExpenses));
//        //view.getLblExpensesPaid().setText(String.format("Paid: %.2f €", totalPaid));
//        //view.getLblExpensesEstimated().setText(String.format("Estimated: %.2f €", totalEstimated));
//    }
}

